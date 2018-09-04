package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.common.mybatis.support.Entity;
import com.haiercash.pluslink.capital.common.mybatis.support.QueryParam;
import com.haiercash.pluslink.capital.manager.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/message/common")
public class CommonController extends BaseController {
    @Autowired
    private CommonService commonService;

    public CommonController() {
        super("83");
    }

    @RequestMapping(value="/select", method = RequestMethod.POST)
    public Map<String, Object> select(@RequestBody QueryParam queryParam) {
        return super.success(commonService.selectByFilter(queryParam));
    }

    @RequestMapping(value="/auto/init", method = RequestMethod.POST)
    public Map<String, Object> initForAuto(@RequestBody QueryParam queryParam) {
        return super.success(commonService.initForCommon(queryParam));
    }

    @RequestMapping(value="/auto/query", method = RequestMethod.POST)
    public Map<String, Object> queryForAuto(@RequestBody QueryParam queryParam) {
        return super.success(commonService.selectForCommon(queryParam));
    }

    @RequestMapping(value="/select/query", method = RequestMethod.POST)
    public Map<String, Object> queryForSelect(@RequestBody QueryParam queryParam) {
        return super.success(commonService.selectForCommon(queryParam));
    }

    @RequestMapping(value="/table/select", method = RequestMethod.POST)
    public Map<String, Object> selectForTable(@RequestBody QueryParam queryParam) {
        return super.success(commonService.selectByFilter(queryParam));
    }

    @RequestMapping(value="/table/page", method = RequestMethod.POST)
    public Map<String, Object> page(@RequestBody QueryParam queryParam) throws InterruptedException {
        return super.success(commonService.pageByFilter(queryParam));
    }

    @RequestMapping(value="/form/insert", method = RequestMethod.POST)
    public Map<String, Object> insert(@RequestBody Entity entity) {
        return super.success(commonService.insert(entity));
    }

    @RequestMapping(value="/form/update", method = RequestMethod.POST)
    public Map<String, Object> update(@RequestBody Entity entity) {
        return super.success(commonService.update(entity));
    }

    @RequestMapping(value="/table/deleteByIds", method = RequestMethod.POST)
    public Map<String, Object> deleteByIds(@RequestBody Entity entity) {
        return super.success(commonService.deleteByIds(entity));
    }

    @RequestMapping(value="/tree/query", method = RequestMethod.POST)
    public Map<String, Object> queryForTree(@RequestBody QueryParam queryParam) {
        return super.success(commonService.selectByFilter(queryParam));
    }
}
