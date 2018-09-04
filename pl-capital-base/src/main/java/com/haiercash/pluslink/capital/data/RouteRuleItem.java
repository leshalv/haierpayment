package com.haiercash.pluslink.capital.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
  * 路由规则明细表实体类
  * @author WDY
  * @date 20180619
  * @rmk 继承RouteRule 查询时部分联合查询
  */
@Getter
@Setter
public class RouteRuleItem extends RouteRule{

  /**路由规则ID:主表主键**/
  private String fdMainId;
  /**路由规则编号:主表路由规则编号**/
  private String fdNum;
  /**业务类型:P-产品;C-渠道;S-标签;**/
  private String fdType;
  /**规则内容:业务类型对应参数值**/
  private String ruleContent;

  public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("RouteRuleItem [id=");
      builder.append(id);
      builder.append(", fdMainId=");
      builder.append(fdMainId);
      builder.append(", fdNum=");
      builder.append(fdNum);
      builder.append(", fdType=");
      builder.append(fdType);
      builder.append(", ruleContent=");
      builder.append(ruleContent);
      builder.append(", mateRule=");
      builder.append(super.getMateRule());
      builder.append(", priority=");
      builder.append(super.getPriority());
      builder.append(", channelId=");
      builder.append(super.getChannelId());
      builder.append(", channelProductId=");
      builder.append(super.getChannelProductId());
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
