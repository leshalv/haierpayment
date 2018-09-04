package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.manager.data.AgencyType;
import com.haiercash.pluslink.capital.manager.data.AgencyTypeDetail;
import com.haiercash.pluslink.capital.manager.service.AgencyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * > 合作机构类别配置
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 14:29
 */
@RestController
@RequestMapping("/api/message/agencyType")
public class AgencyTypeController extends BaseController{
    public AgencyTypeController() {
        super("pl00022 ");
    }
    @Autowired
    private AgencyTypeService agencyTypeService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/getAgencyTypeList")
    public Map<String, Object> getAgencyType() {
        logger.info("查询机构类别数据");
        List<AgencyType> agencyTypeList = agencyTypeService.getAgencyTypeList();
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("data",agencyTypeList);
        return restMap;
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping("/addAgencyType")
    public int addAgencyType(@RequestBody AgencyType agencyType) {
        Map<String, Object> user = super.getUser();
        return agencyTypeService.addAgencyType(agencyType, super.getUser());
    }
    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateAgencyType")
    public int updateAgencyType(@RequestBody AgencyType agencyType) {
        return agencyTypeService.updateAgencyType(agencyType, super.getUser());
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delAgencyType")
    public int delAgencyType(List<AgencyType> agencyTypes) {
        return agencyTypeService.delAgencyType(agencyTypes, super.getUser());
    }
    /**
     * 详情
     * @return
     */
    @PostMapping("/getAgencyTypeDetail")
    public Map<String, Object> getAgencyTypeDetail(String agencyTypeId) {
        List<AgencyTypeDetail> agencyTypeDetail = agencyTypeService.getAgencyTypeDetail(agencyTypeId);
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("data",agencyTypeDetail);
        return restMap;
    }

}
