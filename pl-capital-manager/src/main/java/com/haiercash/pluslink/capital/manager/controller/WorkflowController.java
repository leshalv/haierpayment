package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.common.mybatis.support.QueryColumn;
import com.haiercash.pluslink.capital.common.mybatis.support.QueryParam;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import com.haiercash.pluslink.capital.enums.ReturnCode;
import com.haiercash.pluslink.capital.manager.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工作流控制层
 *
 * @author zhanggaowei
 * @date 2018/5/30
 */
@Slf4j
@RestController
@RequestMapping("/api/message")
public class WorkflowController extends BaseController {
    public WorkflowController() {
        super("20");
    }

    @Autowired
    private WorkflowService workflowService;

    /**
     * 开启工作流
     */
    @RequestMapping("/approveStart/{workflowKey}/{businessKey}/{businessUser}")
    public Map<String, Object> startWorkflow(@PathVariable String workflowKey, @PathVariable String businessKey, @PathVariable String businessUser, @RequestParam("callback") String callback) throws Exception {
        String result = workflowService.startWorkflow(workflowKey, businessKey, callback, businessUser);
        return this.success(result);
    }

    @RequestMapping("/approveRecord")
    public Map<String, Object> approveRecord(@RequestBody QueryParam queryParam) throws Exception {
        QueryColumn queryColumn = queryParam.getInitParamList().get(0);
        return super.success(workflowService.queryApprovalList((String) queryColumn.getValue(), queryParam.getOffset(), queryParam.getLimit()));
    }
    //查询审批记录
    @RequestMapping("/approveHistory/{businessKey}")
    public Map<String, Object> approveHistory(@PathVariable String businessKey) throws Exception {
        if (StringUtils.isBlank(businessKey)) {
            return super.fail(ReturnCode.msg_param_lack.getCode(), "业务主键不能为空");
        }
        return super.success(workflowService.queryApprovalList(businessKey, 0, 100));
    }

    //判断用户审批权限
    @RequestMapping("/approveAuthority/{workflowKey}")
    public Map<String, Object> approveAuthority(@PathVariable String workflowKey, @RequestParam("businessKey") String businessKey, @RequestParam(name = "opUser", required = false) String opUser) throws Exception {
        if (StringUtils.isEmpty(opUser))
            opUser = (String) super.getUser("userAlias");
        return super.success(workflowService.approveAuthority(workflowKey, businessKey, opUser));
    }

    //审批
    @RequestMapping("/approveExecute/{workflowKey}/{type}")
    public Map<String, Object> approveExecute(@PathVariable String workflowKey, @PathVariable boolean type,
                                              @RequestParam("businessKey") String businessKey, @RequestParam("reason") String reason,
                                              @RequestParam(name = "opUser", required = false) String opUser) throws Exception {
        if (StringUtils.isEmpty(opUser))
            opUser = (String) super.getUser("userAlias");
        if (ReturnCode.msg_approve_role_not_exists.getCode().equals(workflowService.approveExecute(workflowKey, businessKey, reason, opUser, type)))
            return this.fail(ReturnCode.msg_approve_role_not_exists.getCode(), ReturnCode.msg_approve_role_not_exists.getDesc());
        return this.success();
    }
    //批量判断用户权限
    @RequestMapping("/batchAuthority/{workflowKey}")
    public Map<String, Object> batchAuthority(@PathVariable String workflowKey, @RequestBody List<String> keyList) throws Exception {
        String user = (String) super.getUser("userAlias");
        logger.info("工作流查询当前用户是否有对批量明细的审批权限，工作流标识workflowKey："+workflowKey +"；当前操作人："+user);
        for (String businessKey : keyList) {
            logger.info("工作流查询当前用户对业务主键为："+businessKey + "的审批权限开始");
            if (!workflowService.approveAuthority(workflowKey, businessKey, user)) {
                logger.info("当前用户无操作"+businessKey +"的审批权限，操作人为：" +user);
                return super.success(false);
            }
            logger.info("工作流查询当前用户对业务主键为："+businessKey + "的审批权限完成，当前用户有审批权限");
        }
        logger.info("工作流查询当前用户是否有对批量中明细的审批权限完成");
        return super.success(true);
    }
    //批量审批
    @RequestMapping("/batchExecute/{workflowKey}/{type}")
    public Map<String, Object> batchExecute(@PathVariable String workflowKey, @PathVariable boolean type,
                                            @RequestParam("reason") String reason, @RequestBody List<String> keyList) throws Exception {
        String user = (String) super.getUser("userAlias");
        Date startTime = new Date();//发起任务起始时间
        logger.info("工作流批量审批执行开始，工作流标识workflowKey：" + workflowKey + "；当前操作人：" + user + "；本次审批结果type：" + type + "；本次批量审批条数共:" + keyList.size() + "；本次批次标记：" + startTime.getTime());
        for (String businessKey : keyList) {
            logger.info("工作流执行审批业务主键为：" + businessKey + "；批次标识：" + startTime.getTime());
            if (ReturnCode.msg_approve_role_not_exists.getCode().equals(workflowService.approveExecute(workflowKey, businessKey, reason, user, type))) {
                return super.fail(ReturnCode.msg_approve_role_not_exists.getCode(), ReturnCode.msg_approve_role_not_exists.getDesc());
            }
        }
        return super.success();
    }
}
