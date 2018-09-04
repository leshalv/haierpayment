package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 放款请求
 *
 * @author xiaobin
 * @create 2018-07-25 上午10:32
 **/
@Getter
@Setter
public class MakeLoansRequest implements Serializable {

    @ApiModelProperty("消息id")
    @NotBlank(message = "消息id不能为空")
    private String corpMsgId;

    /**
     * 贷款业务序号
     */
    @ApiModelProperty("贷款业务序号")
    @NotBlank(message = "贷款业务序号不能为空")
    private String loanBusino;
    /**
     * 资方客户协议号
     */
    @ApiModelProperty("资方客户协议号")
    @NotBlank(message = "资方客户协议号不能为空")
    private String cinoMemno;
    /**
     * 消金客户ID
     */
    @ApiModelProperty("消金客户ID")
    @NotBlank(message = "消金客户ID不能为空")
    private String cooprUserId;
    /**
     * 合作方贷款申请日期
     */
    @ApiModelProperty("合作方贷款申请日期")
    @NotBlank(message = "合作方贷款申请日期不能为空")
    private String corpLoanAppdt;
    /**
     * 拆分前金额
     * 申请贷款总金额
     */
    @ApiModelProperty("申请贷款总金额")
    @NotNull(message = "申请贷款总金额不能为空")
    private BigDecimal promAmtCust;
    /**
     * 拆分后金额
     * 申请贷款金额
     */
    @ApiModelProperty("申请贷款金额")
    @NotNull(message = "申请贷款金额不能为空")
    private BigDecimal appLoanAmt;
    /**
     * 贷款分期期数
     */
    @ApiModelProperty("贷款分期期数")
    @NotNull(message = "贷款分期期数不能为空")
    private Integer loanInstlNum;

    /**
     * 账号户名
     */
    @ApiModelProperty("账号户名")
    @NotBlank(message = "账号户名不能为空")
    private String accnm;
    /**
     * 客户手机号
     */
    @ApiModelProperty("客户手机号")
    @NotBlank(message = "客户手机号不能为空")
    private String mvblno;

    /**
     * 消费订单号
     */
    @ApiModelProperty("消费订单号")
    private String consOdrno;
    /**
     * 消费商品
     */
    @ApiModelProperty("消费商品")
    private String consComd;
    /**
     * 商户
     */
    @ApiModelProperty("商户")
    private String mer;
    /**
     * 商品价格
     */
    @ApiModelProperty("商品价格")
    private BigDecimal comdPrice;
    /**
     * 保险单号
     */
    @ApiModelProperty("保险单号")
    private String insrOdrno;
    /**
     * 放款银行名称
     */
    @ApiModelProperty("放款银行名称")
    private String bankName;
    /**
     * 放款账号或卡号
     */
    @ApiModelProperty("放款账号或卡号")
    private String rlesAcc;


    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField1;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField2;

    //扩展字段
    @ApiModelProperty("扩展字段")
    private String extendField3;
}
