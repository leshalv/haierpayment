package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 放款记录表
 *
 * @author xiaobin
 * @create 2018-07-25 下午2:31
 **/
@Entity
@Getter
@Setter
@Table(name = "PL_PROCESSER_MAKE_LOANS")
public class ProcessMakeLoans extends BaseModel {

    /**
     * 消金业务编号
     */
    private String applSeq;

    /**
     * 资产Id
     */
    private String assetsSplitId;

    /**
     * 资产拆分明细Id
     */
    private String assetsSplitItemId;

    /**
     * 工行交互消息Id
     */
    private String orgCorpMsgId;

    /**
     * 申请时间
     */
    private String applyDt;

    /**
     * 放款状态
     */
    private String status;


}
