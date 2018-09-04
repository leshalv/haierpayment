package com.haiercash.pluslink.capital.router.client.response;

import com.haiercash.pluslink.capital.router.client.response.list.CooperationPeriodResponse;
import com.haiercash.pluslink.capital.router.client.response.list.RepaymentInfoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 资金方综合信息查询接口(入参参数)
 * @author WDY
 * @date 2018-07-13
 * @rmk
 */
@Getter
@Setter
@ApiModel("资金方综合信息查询接口(返回)")
public class GeneralInfoResponse implements Serializable{

    @ApiModelProperty("合作机构编码")
    @NotBlank(message = "合作机构编码不能为空")
    private String agencyId;

    @ApiModelProperty("合作项目编码")
    @NotBlank(message = "合作项目编码不能为空")
    private String projectId;

    @ApiModelProperty("业务每日开始时间")
    @NotBlank(message = "业务每日开始时间不能为空")
    private String busiDayStartTime;

    @ApiModelProperty("业务每日结束时间")
    @NotBlank(message = "业务每日结束时间不能为空")
    private String busiDayEndTime;

    @ApiModelProperty("代偿时间")
    @NotBlank(message = "代偿时间不能为空")
    private String compensatoryTime;

    @ApiModelProperty("是否担保")
    @NotBlank(message = "是否担保不能为空")
    private String isAssure;

    @ApiModelProperty("担保机构编码")
    @NotBlank(message = "担保机构编码不能为空")
    private String colralId;

    @ApiModelProperty("项目生效时间")
    @NotBlank(message = "项目生效时间不能为空")
    private String effectTime;

    @ApiModelProperty("消金收益率")
    @NotNull(message = "消金收益率不能为空")
    private BigDecimal cashYieldRate;

    @ApiModelProperty("合作机构收益率")
    @NotNull(message = "合作机构收益率不能为空")
    private BigDecimal agencyYieldRate;

    @ApiModelProperty("用户额度区间开始")
    @NotNull(message = "用户额度区间开始不能为空")
    private BigDecimal custLimitStart;

    @ApiModelProperty("用户额度区间结束")
    @NotNull(message = "用户额度区间结束不能为空")
    private BigDecimal custLimitEnd;

    @ApiModelProperty("用户贷款区间开始")
    @NotNull(message = "用户贷款区间开始不能为空")
    private BigDecimal custLoanStart;

    @ApiModelProperty("用户贷款区间结束")
    @NotNull(message = "用户贷款区间结束不能为空")
    private BigDecimal custLoanEnd;

    @ApiModelProperty("用户性别维度")
    @NotBlank(message = "用户性别维度不能为空")
    private String custSexDimension;

    @ApiModelProperty("用户年龄区间开始")
    @NotBlank(message = "用户年龄区间开始不能为空")
    private String custAgeStart;

    @ApiModelProperty("用户年龄区间结束")
    @NotBlank(message = "用户年龄区间结束不能为空")
    private String custAgeEnd;

    @ApiModelProperty("用户首次用信距今天数")
    @NotBlank(message = "用户首次用信距今天数不能为空")
    private String custFirstCreditAgo;

    @ApiModelProperty("期限服务费率")
    @NotNull(message = "期限服务费率不能为空")
    private BigDecimal termCharge;

    @ApiModelProperty("还款方式")
    @NotNull(message = "还款方式不能为空")
    private List<RepaymentInfoResponse> repaymentInfo;

    @ApiModelProperty("支持期限")
    @NotNull(message = "支持期限不能为空")
    private List<CooperationPeriodResponse> cooperationPeriod;
}
