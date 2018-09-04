package com.haiercash.pluslink.capital.manager.controller;

import com.haiercash.pluslink.capital.common.mybatis.support.ExcelUtils;
import com.haiercash.pluslink.capital.common.mybatis.support.FileDetail;
import com.haiercash.pluslink.capital.common.mybatis.support.FileUtils;
import com.haiercash.pluslink.capital.common.utils.RestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhanggaowei
 * @date 2018/4/21
 */
@Slf4j
@RestController
@RequestMapping("/api/message/excel")
public class ExcelController extends BaseController {
    public ExcelController() {
        super("20");
    }
    public Log logger = LogFactory.getLog(getClass());

    @Value("${common.excel.uploadPath:}")
    private String uploadPath;
    @Value("${common.excel.templatePath:}")
    private String templatePath;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam(name = "name") String name, HttpServletRequest httpServletRequest) throws IOException {
        String fileExt = name.substring(name.lastIndexOf(".") + 1);
        String fileName = RestUtils.getGuid() + "." + fileExt;
        String filePath = ExcelUtils.filePath(uploadPath);
        Integer fileSize = FileUtils.save(httpServletRequest.getInputStream(), new FileOutputStream(filePath + fileName));

//        fos.write(requestEntity.getBody());
        FileDetail fileDetail = new FileDetail();
        fileDetail.setOriginName(name);
        fileDetail.setFileName(fileName);
        fileDetail.setFileExt(fileExt);
        fileDetail.setFilePath(filePath);
        fileDetail.setFileSize(fileSize);
//        fileDetail.setFileSize(requestEntity.getBody().length);
        return super.success(fileDetail);
    }

    @RequestMapping(value="/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "fileName") String fileName,
                         @RequestParam(name = "originName", required = false) String originName) throws IOException {
        if (!StringUtils.isEmpty(originName)) {
            FileUtils.download(request, response, fileName, originName);
        } else {
            FileUtils.download(request, response, fileName);
        }
    }

    @RequestMapping(value="/template", method = RequestMethod.GET)
    public void template(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        FileUtils.download(request, response, ExcelUtils.getInputStream(templatePath + File.separator + fileName), fileName);
    }
}
