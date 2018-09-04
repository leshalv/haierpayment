package com.haiercash.pluslink.capital.data;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.haiercash.pluslink.capital.enums.dictionary.PL0109Enum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


/**
 * 消息发送请求实体
 *
 * @author keliang.jiang
 * @date 2017/12/26
 */
@Entity
@Getter
@Setter
@Table(name = "msg_request")
public class MsgRequest {

    /**
     * 请求号
     */
    private String requestNo;

    /**
     * 系统标识
     */
    private String institution;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 细分业务类型
     */
    private String subBizType;

    /**
     * 业务ID
     */
    private String bizId;


    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 渠道分类
     */
    private String channel;

    /**
     * 派发渠道号
     */
    private String channelNo;

    /**
     * 派发方式ID
     */
    private String sendConfigId;


    /**
     * 推送消息类型
     */
    private String pushMsgType;

    /**
     * 动作类型
     */
    private int actionType;

    /**
     * 动作代码
     */
    private String actionValue;

    /**
     * 业务数据体
     */
    private String bizContent;

    /**
     * 是否使用模板
     */
    private PL0109Enum useTmpl;

    /**
     * 消息标题
     */
    private String msgTitle;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 发送用户
     */
    private String userId;

    /**
     * 自定义字段
     */
    private String custom;
//
    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 关联用户列表
     */
/*    @Transient
    private List<MsgRequestUser> users;*/


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
