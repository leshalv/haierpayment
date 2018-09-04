package com.haiercash.pluslink.capital.processer.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: yu jianwei
 * @Date: 2018/7/31 15:08
 * @Description:
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "common.pgwAccount")
public class AccountIdConfig {
    /**
     * 辅助工行户
     */
    private String silverDeposit;
    /**
     * 其他应收款/第三方平台（辅助工行）
     */
    private String otherReceivables;
    /**
     * 应付账款贷款/联合合作-（辅助工行)
     */
    private String theMiddle;
    /**
     * 机构账户
     */
    private String agencyAccount;
    /**
     * 拆分查询合作是否redis开关,true先查redis split-switch: true
     */
    private String splitSwitch;
}
