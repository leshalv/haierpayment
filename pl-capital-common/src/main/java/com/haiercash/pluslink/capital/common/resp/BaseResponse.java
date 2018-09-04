package com.haiercash.pluslink.capital.common.resp;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author keliang.jiang
 * @date 2018/1/26
 */
public class BaseResponse {

    /**
     * 返回报文头
     */
    private BaseResponseHead head;

    public BaseResponseHead getHead() {
        return head;
    }
    public void setHead(BaseResponseHead head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
