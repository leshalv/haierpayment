package com.haiercash.pluslink.capital.processer.server.pvm;

/**
 * 流程业务常量值
 *
 * @author xiaobin
 * @create 2018-07-27 下午2:25
 **/
public interface PVM {

    /**
     * 授信申请流程--启动器
     */
    String CREDIT_APPLY_BUS = "creditApplyBus";

    /**
     * creditApplyBacking
     */
    String CREDIT_APPLY_BACK_ING = "creditApplyBackingBus";

    /**
     * 授信申请--上下文
     */
    String CREDIT_APPLY_CONTEXT = "creditApplyContext";

    /**
     * 授信回调上下文
     */
    String CREDIT_BACK_CONTEXT = "creditBackContext";

    /**
     * 放款回调--启动器
     */
    String LOAN_BACK_BUS = "loanBackBus";

    /**
     * 放款回调--启动器
     *
     */
    String PAY_MENT_GATEWAY_BUS = "paymentGatewayBackBus";

    /**
     * 放款回调--上下文
     */
    String LOAN_BACK_CONTEXT = "loanBackContext";

    /**
     * 通过
     */
    String PASS = "pass";

    /**
     * 不通过
     */
    String NOT_PASS = "notPass";


    /**
     * 授信异常
     */
    String FAIL = "fail";

    /**
     * 等待
     */
    String WAIT = "wait";

    /**
     * 额度不足
     */
    String QUOTA_NOT_PASS = "quotaNotPass";

    /**
     * 额度充足
     */
    String QUOTA_PASS = "quotaPass";

    /**
     * 授信回调检测
     */
    String APPROVAL_BACK_CHECK = "approvalBackCheck";

    /**
     * 本地无额度
     */
    String NO_QUOTA = "noQuota";

    String TARGET = "target";


}
