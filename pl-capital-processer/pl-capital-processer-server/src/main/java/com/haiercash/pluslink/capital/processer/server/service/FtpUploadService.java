package com.haiercash.pluslink.capital.processer.server.service;

import cn.jbinfo.cloud.core.utils.DateUtils;
import cn.jbinfo.cloud.core.utils.IdGen;
import cn.jbinfo.common.utils.StreamUtils;
import com.haiercash.pluslink.capital.processer.server.utils.InputStreamZipper;
import jodd.io.FileNameUtil;
import jodd.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaobin
 * @create 2018-08-20 下午7:08
 **/
@Component
@Slf4j
public class FtpUploadService {

    @Value("${ftp.upload.host}")
    private String ftpHost;

    @Value("${ftp.upload.userName}")
    private String ftpUserName;

    @Value("${ftp.upload.password}")
    private String ftpPassword;

    @Value("${ftp.upload.port}")
    private String ftpPort;

    @Value("${ftp.upload.mainPath}")
    private String ftpMainPath;


    private FTPClient getFTPClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, NumberUtils.toInt(ftpPort, 21));// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                System.out.println("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                System.out.println("FTP连接成功。");
            }
        } catch (SocketException e) {
            log.error("FTP的IP地址可能错误，请正确配置:", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("FTP的端口错误，请正确配置:", e);
        }
        return ftpClient;
    }

    public boolean uploadFile(String ftpPath, String fileName, InputStream input) {
        boolean success = false;
        FTPClient ftpClient = null;
        try {
            int reply;
            ftpClient = getFTPClient();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return success;
            }
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpMainPath);
            ftpClient.makeDirectory(ftpPath);
            ftpClient.changeWorkingDirectory(ftpMainPath + "/" + ftpPath);
            ftpClient.storeFile(fileName, input);
            input.close();
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public String uploadFile(List<String> filePaths, String fileName) throws Exception {
        String ftpPath = DateUtils.format(new Date(), "yyyyMMdd");

        List<InputStreamZipper.InputStreamEntry> fileEntrys = new ArrayList<>();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream stream = new FileInputStream(file);
                fileEntrys.add(new InputStreamZipper.InputStreamEntry("", "", stream, FileNameUtil.getName(filePath)));
            }
        }
        if (CollectionUtils.isEmpty(fileEntrys))
            return null;
        String folder = System.getProperty("java.io.tmpdir");

        String tmp = folder + File.separator + IdGen.uuid();
        File file = new File(tmp);
        FileUtil.mkdirs(file);

        String path = tmp + File.separator + IdGen.uuid();
        OutputStream outputStream = new FileOutputStream(path);
        InputStreamZipper.zip(outputStream, fileEntrys, "UTF-8");
        InputStream inputStream = StreamUtils.getFileInputStream(path);
        if (uploadFile(ftpPath, fileName, inputStream)) {
            return ftpMainPath + "/" + ftpPath + "/" + fileName;
        }
        return null;
    }


}
