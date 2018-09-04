package com.haiercash.pluslink.capital.processer.server.rest.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author xiaobin
 * @create 2018-07-19 上午10:38
 **/
@Getter
@Setter
public class CreditQueryRequestVo {

    /**
     * 查询类型
     * <p>
     * 01：普通查询
     * 02：授信申请查询
     */
    @NotBlank(message = "查询类型不能为空")
    private String qryType;


    /**
     * 资方客户协议号
     */
    private String cinoMemno;

    /**
     * 原消金消息ID
     * 如果查询类型为：02，必须送达
     */
    private String orgCorpMsgId;

    /**
     * 合作方客户id
     */
    @ApiModelProperty("合作方客户id")
    @NotBlank(message = "合作方客户id不能为空")
    private String cooprUserId;
}
