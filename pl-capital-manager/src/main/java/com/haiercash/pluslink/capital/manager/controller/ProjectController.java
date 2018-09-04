package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.data.BankInfo;
import com.haiercash.pluslink.capital.data.CooperationProject;
import com.haiercash.pluslink.capital.manager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * > 合作项目
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/7/17 17:05
 */
@RestController
@RequestMapping("/api/message/project")
public class ProjectController extends BaseController{
    public ProjectController() {
        super("pl0005");
    }

    @Autowired
    private ProjectService projectService;

    @PostMapping("/getBankList")
    public List<BankInfo> getProjectDetail(){
        return projectService.getBankList();
    }
    /**
     * 新增
     * @return
     */
    @RequestMapping("/addProject")
    public int addProject(@RequestBody CooperationProject cooperationProject) {
        return projectService.addProject(cooperationProject, super.getUser());
    }
    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateProject")
    public int updateProject(@RequestBody CooperationProject cooperationProject) {
        return projectService.updateProject(cooperationProject, super.getUser());
    }

    /**
     * 删除
     * @return
     */
    @PostMapping("/delProject")
    public int delProject(List<CooperationProject> projects) {
        return projectService.delProject(projects, super.getUser());
    }

}
