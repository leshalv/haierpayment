
package com.haiercash.pluslink.capital.processer.server.rest.client;

import com.haiercash.pluslink.capital.processer.server.rest.feign.annotation.FeignApi;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PgwAccountRecordRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.request.PgwQueryByCrmNoRequest;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PgwAccountRecordResponse;
import com.haiercash.pluslink.capital.processer.server.rest.vo.response.PgwQueryByCrmNoResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @Auther: yu jianwei
 * @Date: 2018/7/25 14:24
 * @Description:
 */

@FeignClient(name = "${rest.api.va}", path = "/api/va/account")
public interface PgwVaClient {
    @FeignApi("通用入账接口")
    @PostMapping(value = "/record/accountRecord", produces = "application/json; charset=UTF-8")
    PgwAccountRecordResponse accountRecord(@RequestBody PgwAccountRecordRequest pgwAccountRecordRequest);
    @FeignApi("通用入账接口(批量)")
    @PostMapping(value = "/record/batchAccountRecord", produces = "application/json; charset=UTF-8")
    PgwAccountRecordResponse batchAccountRecord(@RequestBody PgwAccountRecordRequest pgwAccountRecordRequest);

    @FeignApi("查询账户信息")
    @PostMapping(value = "/queryByCrmNoAndType", produces = "application/json; charset=UTF-8")
    PgwQueryByCrmNoResponse queryByCrmNoAndType(@RequestBody PgwQueryByCrmNoRequest pgwQueryByCrmNoRequest);
}

