package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.data.CooperationAgency;
import com.haiercash.pluslink.capital.manager.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * > 合作机构配置
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/12 14:29
 */
@RestController
@RequestMapping("/api/message/agency")
public class AgencyController extends BaseController{
    public AgencyController() {
        super("pl0002");
    }

    @Autowired
    private AgencyService agencyService;

    /**
     * 查询
     * @return
     */
    /*@RequestMapping("/getAgencyList")
    public Map<String, Object> getAgency() {
        logger.info("查询机构类别数据");
        List<AgencyType> agencyTypeList = agencyTypeService.getAgencyTypeList();
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("data",agencyTypeList);
        return restMap;
    }*/

    /**
     * 新增
     * @return
     */
    @RequestMapping("/addAgency")
    public int addAgency(@RequestBody CooperationAgency cooperationAgency) {
        return agencyService.addAgency(cooperationAgency, super.getUser());
    }
    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateAgency")
    public int updateAgency(@RequestBody CooperationAgency cooperationAgency) {
        return agencyService.updateAgency(cooperationAgency, super.getUser());
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delAgency")
    public int delAgency(List<CooperationAgency> agencys) {
        return agencyService.delAgency(agencys, super.getUser());
    }
    /**
     * 详情
     * @return
     */
    /*@PostMapping("/getAgencyDetail")
    public Map<String, Object> getAgencyDetail(String agencyTypeId) {
        List<AgencyTypeDetail> agencyTypeDetail = agencyService.getAgencyDetail(agencyTypeId);
        Map<String, Object> restMap = new HashMap<>();
        restMap.put("data",agencyTypeDetail);
        return restMap;
    }*/

}
