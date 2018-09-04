package com.haiercash.pluslink.capital.processer.server.service;

import com.google.common.collect.Lists;
import com.haiercash.pluslink.capital.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FtpUploadServiceTest extends BaseTest {

    @Autowired
    private FtpUploadService ftpUploadService;

    @Test
    public void upload() throws Exception {
        //String path = DateUtils.format(new Date(), DateUtils.PATTERN_YYYY_MM_DD);
        //InputStream inputStream = new FileInputStream(new File("/Users/xiaobin/work/libs/ccuploader.jar"));
        //ftpUploadService.uploadFile(path, "ccuploader.jar", inputStream);
        List<String> filePaths = Lists.newArrayList();
        filePaths.add("/Users/xiaobin/work/libs/ccuploader.jar");
        filePaths.add("/Users/xiaobin/work/settings_keyMap.jar");
        ftpUploadService.uploadFile(filePaths, "demo.zip");
    }

}