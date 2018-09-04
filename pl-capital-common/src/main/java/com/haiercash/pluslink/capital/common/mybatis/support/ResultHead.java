package com.haiercash.pluslink.capital.common.mybatis.support;

import java.io.Serializable;

public class ResultHead implements Serializable {
    private static final long serialVersionUID = 1L;

    private String retFlag;
    private String retMsg;
    private String showMsg;

    public ResultHead() {
    }

    public ResultHead(String retFlag, String retMsg) {
        this.retFlag = retFlag;
        this.retMsg = retMsg;
    }

    public ResultHead(String retFlag, String retMsg, String showMsg) {
        this.retFlag = retFlag;
        this.retMsg = retMsg;
        this.showMsg = showMsg;
    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }
}
