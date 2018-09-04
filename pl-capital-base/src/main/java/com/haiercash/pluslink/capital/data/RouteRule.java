package com.haiercash.pluslink.capital.data;

import cn.jbinfo.cloud.core.model.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
  * 路由规则表实体类
  * @author WDY
  * @date 20180619
  * @rmk
  */
@Getter
@Setter
public class RouteRule extends BaseModel {

  /**路由规则编号:LY+主键**/
  private String fdNum;
  /**匹配规则:P-产品;C-渠道;S-标签;PC-产品+渠道;PS-产品+标签;CS-渠道+标签;PCS-产品+渠道+标签;**/
  private String mateRule;
  /**优先级:优先级数值越大优先级越高**/
  private BigDecimal priority;
  /**资金渠道**/
  private String channelId;
  /**渠道产品**/
  private String channelProductId;

  public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("RouteRule [id=");
      builder.append(id);
      builder.append(", fdNum=");
      builder.append(fdNum);
      builder.append(", mateRule=");
      builder.append(mateRule);
      builder.append(", priority=");
      builder.append(priority);
      builder.append(", channelId=");
      builder.append(channelId);
      builder.append(", channelProductId=");
      builder.append(channelProductId);
      builder.append(", createDate=");
      builder.append(super.getCreateDate());
      builder.append(", updateDate=");
      builder.append(super.getUpdateDate());
      builder.append(", createBy=");
      builder.append(super.getCreateBy());
      builder.append(", updateBy=");
      builder.append(super.getUpdateBy());
      builder.append(", delFlag=");
      builder.append(super.getDelFlag());
      builder.append("]");
      return builder.toString();
  }
}
