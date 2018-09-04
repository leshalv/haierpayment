package com.haiercash.pluslink.capital.common.resp;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 返回报文头
 *
 * @author keliang.jiang
 * @date 2018/1/26
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseHead implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private String retFlag;

    /**
     * 返回描述
     */
    private String retMsg;

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

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
