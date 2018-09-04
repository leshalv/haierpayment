package com.haiercash.pluslink.capital.common.mybatis.support;


/**
 * Created by lihua on 2016/7/16.
 */
public class FileDetail {
    /**
     * 原始文件名
     */
    private String originName;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileExt;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 文件路径
     */
    private String filePath;

    private String asyncId;

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAsyncId() {
        return asyncId;
    }

    public void setAsyncId(String asyncId) {
        this.asyncId = asyncId;
    }
}
