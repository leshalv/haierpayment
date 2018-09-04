package com.haiercash.pluslink.capital.manager.service;

import com.haiercash.pluslink.capital.common.ribbon.BaseRestClient;
import com.haiercash.pluslink.capital.common.utils.RestUtils;
//import com.haiercash.pluslink.capital.ribbon.BaseRestClient;
//import com.haiercash.pluslink.capital.ribbon.BaseRestClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaowei
 * @date 2018/5/30
 */
@Service
@ConfigurationProperties (
        prefix = "app.portal.info"
)
public class PortalService extends BaseRestClient {
    private String queryPortalUser;

    public PortalService() {
    }

    public String getUsers(String roleAudit) {
        String url = this.serviceID + this.queryPortalUser + "?appId=HCMessage&roleAudits=" + roleAudit;
        Map<String, Object> map = this.restGetMap(url, HttpStatus.OK.value());
        if(RestUtils.isSuccess(map) && map.containsKey("body")) {
            List<Map<String, Object>> userList = (List)map.get("body");
            if(userList != null && userList.size() > 0) {
                String users = "|";

                Map aliasMap;
                for(Iterator var6 = userList.iterator(); var6.hasNext(); users = users + aliasMap.get("userAlias") + "|") {
                    Map<String, Object> userMap = (Map)var6.next();
                    aliasMap = (Map)userMap.get("user");
                }

                return users;
            }
        }

        return null;
    }

    public String getQueryPortalUser() {
        return this.queryPortalUser;
    }

    public void setQueryPortalUser(String queryPortalUser) {
        this.queryPortalUser = queryPortalUser;
    }
}
