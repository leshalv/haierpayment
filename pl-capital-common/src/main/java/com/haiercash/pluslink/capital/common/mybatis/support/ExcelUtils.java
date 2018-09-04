package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.DateUtils;
import com.haiercash.pluslink.capital.common.utils.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lihua on 2016/7/16.
 */
public final class ExcelUtils {
    public static InputStream getInputStream(String fileName) throws IOException {
        if (!StringUtils.isEmpty(fileName)) {
            if (fileName.startsWith("classpath:")) {
                ResourceLoader loader = new DefaultResourceLoader();
                return loader.getResource(fileName).getInputStream();
            } else {
                return new FileInputStream(fileName);
            }
        }
        return null;
    }
    public static String filePath(String excelPath) {
        File file = new File(excelPath + File.separator + DateUtils.formatDate(new Date(), "yyyy_MM"));

        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath() + File.separator;
    }

    public static void exportFile(HttpServletRequest request, HttpServletResponse response, String templatePath,
                                  String templateFileName, String exportFileName, Map<String, Object> varMap) throws IOException {
        Context context = new Context(varMap);
        response.setContentType(FileUtils.getContentType(exportFileName));
        response.setHeader("content-disposition", "attachment; filename=\"" + FileUtils.encodeFileName(request.getHeader("USER-AGENT"), exportFileName) + "\"");
        ServletOutputStream os = response.getOutputStream();
        InputStream is = getInputStream(templatePath + File.separator + templateFileName);
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
    }

    public static String exportFile(String templatePath, String templateFileName, String exportFileName, Map<String, Object> varMap) throws IOException {
        Context context = new Context(varMap);
        File file = FileUtils.getTempFile(exportFileName);
        OutputStream os = new FileOutputStream(file);
        InputStream is = getInputStream(templatePath + File.separator + templateFileName);
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        return file.getAbsolutePath();
    }

    public static Map<String, Object> exportFile(String templatePath, String templateFileName, String exportFileName, String originName, Map<String, Object> varMap) throws IOException {
        exportFile(templatePath, templateFileName, exportFileName, varMap);
        Context context = new Context(varMap);
        File file = FileUtils.getTempFile(exportFileName);
        OutputStream os = new FileOutputStream(file);
        InputStream is = getInputStream(templatePath + File.separator + templateFileName);
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("fileName", exportFileName);
        resultMap.put("originName", originName);
        return resultMap;
    }

    public static void importFile(String templatePath, String templateXmlName, InputStream inputStream, Map<String, Object> varMap) throws IOException, SAXException, InvalidFormatException {
        InputStream is = getInputStream(templatePath + File.separator + templateXmlName);
        XLSReader reader = ReaderBuilder.buildFromXML(is);
        reader.read(inputStream, varMap);
        is.close();
        inputStream.close();
    }
}
