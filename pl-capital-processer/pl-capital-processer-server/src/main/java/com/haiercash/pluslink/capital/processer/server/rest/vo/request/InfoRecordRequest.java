package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 信息补录
 * @author xiaobin
 * @create 2018-08-20 下午9:02
 **/
@Getter
@Setter
public class InfoRecordRequest {

    /**
     * 合作方客户id
     */
    @ApiModelProperty("合作方客户id")
    @NotBlank(message = "合作方客户id不能为空")
    private String cooprUserId;

    /**
     * 资方贷款协议号
     */
    @ApiModelProperty("资方贷款协议号")
    @NotBlank(message = "资方贷款协议号不能为空")
    private String loanMemno;
    /**
     * 资方客户协议号
     */
    @ApiModelProperty("资方客户协议号")
    @NotBlank(message = "资方客户协议号不能为空")
    private String cinoMemno;

    /**
     * 原贷款业务序号
     * 资方借据号
     */
    @ApiModelProperty("原贷款业务序号")
    @NotBlank(message = "原贷款业务序号不能为空")
    private String orgLoanBusino;

    /**
     * 业务信息
     */
    @ApiModelProperty("业务信息")
    @NotBlank(message = "业务信息不能为空")
    private String busiInfo;

    /**
     * 文件资料
     */
    @ApiModelProperty("文件资料")
    private String files;
}
