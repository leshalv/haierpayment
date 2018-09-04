package com.haiercash.pluslink.capital.processer.server.pvm;

import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import cn.jbinfo.cloud.core.utils.SpringContextHolder;
import cn.jbinfo.integration.busflow.bus.Bus;
import cn.jbinfo.integration.busflow.bus.BusFactory;
import cn.jbinfo.integration.busflow.context.BusContext;
import cn.jbinfo.integration.busflow.context.MapBusContext;
import cn.jbinfo.integration.busflow.exception.MaxPathException;
import com.google.common.collect.Lists;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.data.ProcesserFlowLog;
import com.haiercash.pluslink.capital.enums.ReturnCode;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.HandlerManager;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditApplContext;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.CreditBackContext;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.LoanBackContext;
import com.haiercash.pluslink.capital.processer.server.pvm.handler.context.PaymentGatewayBackContext;
import com.haiercash.pluslink.capital.processer.server.pvm.log.HandlerLog;
import com.haiercash.pluslink.capital.processer.server.service.ProcesserFlowLogService;
import com.netflix.client.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-08-03 上午9:08
 **/
@Slf4j
@Component
@Scope("prototype")
public class FlowWorkerServer {


    /**
     * 授信申请
     */
    public BusContext creditApply(CreditApplContext creditApplContext, Object obj) {
        log.info(">>>>>>>>>>>>>>>启动授信申请<<<<<<<<<<<<<<<<");
        Bus creditApplyBus = BusFactory.createNewBus(PVM.CREDIT_APPLY_BUS);
        creditApplyBus.putContext(PVM.CREDIT_APPLY_CONTEXT, creditApplContext);
        creditApplyBus.putContext(PVM.TARGET, obj);
        BusContext busContext = creditApplyBus.run();
        doBusiness(creditApplyBus);
        return busContext;
    }

    /**
     * 授信实时返回结果检测
     *
     * @return
     */
    public BusContext creditApplyBacking(CreditBackContext creditBackContext, Object obj) {
        Bus creditApplyBus = BusFactory.createNewBus(PVM.CREDIT_APPLY_BACK_ING);
        creditApplyBus.putContext(PVM.CREDIT_BACK_CONTEXT, creditBackContext);
        creditApplyBus.putContext(PVM.TARGET, obj);
        BusContext busContext = creditApplyBus.run();
        doBusiness(creditApplyBus);
        return busContext;
    }

    /**
     * 授信回调
     *
     * @param creditBackContext 上下文
     */
    public BusContext creditApplyBack(CreditBackContext creditBackContext, Object obj) {
        log.info(">>>>>>>>>>>>>>>启动授信回调流程<<<<<<<<<<<<<<<<");
        Bus creditApplyBackBus = BusFactory.createNewBus("creditApplyBackBus");
        creditApplyBackBus.putContext(PVM.TARGET, obj);
        creditApplyBackBus.putContext(PVM.CREDIT_BACK_CONTEXT, creditBackContext);
        BusContext busContext = creditApplyBackBus.run();
        doBusiness(creditApplyBackBus);
        return busContext;
    }

    /**
     * 前置放款回调事件
     *
     * @param loanBackContext 上下文
     * @return
     */
    public BusContext loanBack(LoanBackContext loanBackContext, Object obj) {
        log.info(">>>>>>>>>>>>>>>启动前置放款回调事件流程<<<<<<<<<<<<<<<<");
        Bus loanBackBus = BusFactory.createNewBus(PVM.LOAN_BACK_BUS);
        loanBackBus.putContext(PVM.LOAN_BACK_CONTEXT, loanBackContext);
        loanBackBus.putContext(PVM.TARGET, obj);
        BusContext busContext = loanBackBus.run();
        doBusiness(loanBackBus);
        return busContext;
    }

    /**
     * 支付网关回调
     *
     * @param paymentGatewayBackContext 上下文
     * @return
     */
    public BusContext paymentGatewayBackHandler(PaymentGatewayBackContext paymentGatewayBackContext, Object obj) {
        Bus loanBackBus = BusFactory.createNewBus(PVM.PAY_MENT_GATEWAY_BUS);
        loanBackBus.putContext("paymentGatewayBackContext", paymentGatewayBackContext);
        loanBackBus.putContext(PVM.TARGET, obj);
        BusContext busContext = loanBackBus.run();
        doBusiness(loanBackBus);
        return busContext;
    }


    private void doBusiness(Bus bus) {
        MapBusContext mapBusContext = (MapBusContext) bus.getBusContext();
        Exception exception = mapBusContext.getException();
        String isError = "0";
        String exceptionInfo = "";
        if (exception != null) {
            isError = "1";
            exceptionInfo = exception.getMessage();
        }
        LinkedList<HandlerLog> flowLogLinkedList = HandlerManager.get(bus.getBusContext());
        if (CollectionUtils.isNotEmpty((flowLogLinkedList))) {
            flowLogLinkedList.getLast().setIsError(isError);
            flowLogLinkedList.getLast().setException(exceptionInfo);
            ProcesserFlowLogService processerFlowLogService = SpringContextHolder.getBean(ProcesserFlowLogService.class);

            List<ProcesserFlowLog> flowLogList = Lists.newArrayList();
            Integer index = 0;
            String applSeq = ObjectUtils.identityToString(bus.getBusContext().getValue("applSeq"));
            if (StringUtils.isNotBlank(applSeq)) {
                index = processerFlowLogService.selectByApplSeq(applSeq);
            }
            for (HandlerLog log : flowLogLinkedList) {
                ProcesserFlowLog flowLog = log.build();
                flowLog.setFdIndex(index + 1);
                flowLogList.add(flowLog);
                index++;
            }
            try {
                processerFlowLogService.insertProcesserFlowList(flowLogList);
            } catch (Exception ex) {
                log.error("插入流程日志异常：", ex);
            }
        }

        if (exception != null) {
            log.error("处理异常：", exception);
            if (exception instanceof ClientException) {
                throw new SystemException(CommonReturnCodeEnum.DATA_COMM_EXCEPTION.getCode(), "链接服务异常", ApiContextManager.getSerNo(), exception);
            } else if (exception instanceof SystemException) {
                throw (SystemException) exception;
            } else if (exception instanceof MaxPathException) {
                throw new SystemException(ReturnCode.un_known.getCode(), exception.getMessage(), ApiContextManager.getSerNo(), exception);
            } else if (exception instanceof MessageHandlingException) {
                throw new SystemException(ReturnCode.un_known.getCode(), "链接MQ异常", ApiContextManager.getSerNo(), exception);
            } else {
                throw new SystemException(ReturnCode.un_known.getCode(), exception.getMessage(), ApiContextManager.getSerNo(), exception);
            }
        }


    }
}
