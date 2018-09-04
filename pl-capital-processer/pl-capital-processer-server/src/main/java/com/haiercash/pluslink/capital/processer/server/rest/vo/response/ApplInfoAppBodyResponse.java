package com.haiercash.pluslink.capital.processer.server.rest.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lzh
 * @Title: ApplInfoAppBodyResponse 放款详情for app
 * @ProjectName pl-capital
 * @Description: TODO
 * @date 2018/7/1819:09
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplInfoAppBodyResponse implements Serializable {
    //返回报文体
    //申请人信息列表
    private ApplInfoAppBodyApptListResponse apptList;

    //申请期限
    @JsonProperty("apply_tnr")
    private String applyTnr;

    //申请日期
    @JsonProperty("apply_dt")
    private String applyDt;

    //商品列表
    private  ApplInfoAppBodyGoodsListResponse goodsList;
}
