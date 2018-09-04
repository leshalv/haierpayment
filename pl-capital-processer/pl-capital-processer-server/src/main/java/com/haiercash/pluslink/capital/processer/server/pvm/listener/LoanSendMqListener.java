package com.haiercash.pluslink.capital.processer.server.pvm.listener;

import cn.jbinfo.api.context.ApiContextManager;
import cn.jbinfo.api.exception.SystemException;
import com.haiercash.pluslink.capital.processer.server.enums.CommonReturnCodeEnum;
import com.haiercash.pluslink.capital.processer.server.pvm.event.LoanApplicationEvent;
import com.haiercash.pluslink.capital.processer.server.stream.StreamSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 放款申请监听器
 *
 * @author lzh
 * @Title: LoanListener
 * @ProjectName pl-capital
 * @Description: 处理中心放款接口监听类
 * @date 2018/7/915:24
 */
@Component
@Slf4j
public class LoanSendMqListener implements SmartApplicationListener {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private StreamSender streamSender;

    /**
     * 支持的event 类
     *
     * @param eventType
     * @return
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == LoanApplicationEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        try {
            //转换事件类型
            LoanApplicationEvent loanApplicationEvent = (LoanApplicationEvent) applicationEvent;
            streamSender.outputAsstesSplitItem(loanApplicationEvent.toString());
//            rabbitTemplate.convertAndSend(queueConfig.plLendingExchange, queueConfig.plLendingRoutingKey, loanApplicationEvent.getMessage());
            log.info("业务编号{}，通知拆分，LoanProduSuccess:{}", loanApplicationEvent.getAssetsSplit().getSubBusinessType(), loanApplicationEvent.getMessage());
        } catch (Exception ex) {
            throw new SystemException(CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getCode(), CommonReturnCodeEnum.DATA_INSERT_EXCEPTION.getDesc(), ApiContextManager.getSerNo(), ex);
        }
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
