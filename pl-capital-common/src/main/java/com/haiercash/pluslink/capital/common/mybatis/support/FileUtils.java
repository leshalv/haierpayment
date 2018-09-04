package com.haiercash.pluslink.capital.common.mybatis.support;

import com.haiercash.pluslink.capital.common.utils.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件操作类
 * User: huxiaoxue
 * Date: 15-7-13
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {


    /**
     * 写文本文件内容
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @param encoding        编码方式 例如 GBK 或者 UTF-8
     * @return 文本文件完整绝对路径及文件名
     */
    public static String writeText(String filePathAndName, String fileContent,
                                   String encoding) {
        if (encoding.equals(""))
            encoding = "UTF-8";
        filePathAndName = filePathAndName.replace(File.separator, "/");
        PrintWriter myFile = null;
        try {
            File myFilePath = new File(filePathAndName);
            if (!myFilePath.exists()) {
                createFolders(filePathAndName.substring(0, filePathAndName
                        .lastIndexOf("/")));
                myFilePath.createNewFile();
            }

            myFile = new PrintWriter(myFilePath, encoding);
            myFile.print(fileContent);
            myFile.flush();

        } catch (Exception e) {
            filePathAndName = "";
        } finally {
            if (myFile != null)
                myFile.close();
        }
        return filePathAndName;
    }

    /**
     * 追加写文本文件内容
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @param fileContent     文本文件内容
     * @param encoding        编码方式 例如 GBK 或者 UTF-8 默认UTF-8
     * @return 文本文件完整绝对路径及文件名
     */
    public static String appendWriteText(String filePathAndName,
                                         String fileContent, String encoding) {

        if (encoding.equals(""))
            encoding = "UTF-8";
        filePathAndName = filePathAndName.replace(File.separator, "/");
        FileWriter myFile = null;
        try {
            File myFilePath = new File(filePathAndName);
            if (!myFilePath.exists()) {
                return writeText(filePathAndName, fileContent, encoding);
            }
            myFile = new FileWriter(filePathAndName, true);
            myFile.write(fileContent);
            myFile.flush();
        } catch (Exception e) {
            filePathAndName = "";
        } finally {
            try {
                if (myFile != null)
                    myFile.close();
            } catch (IOException e) {
                filePathAndName = "";
            }
        }
        return filePathAndName;
    }

    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式 GBK UTF-8
     * @return 返回文本文件的内容
     */
    public static String readText(String filePathAndName, String encoding) {
        if (encoding == null)
            encoding = "UTF-8";
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st;
        FileInputStream fs;
        InputStreamReader isr;
        try {
            fs = new FileInputStream(filePathAndName);
            isr = new InputStreamReader(fs, encoding);

            BufferedReader br = new BufferedReader(isr);
            try {
                String data;
                while ((data = br.readLine()) != null) {
                    str.append(data + " ");
                }
            } catch (Exception e) {
                str.append(e.toString());
            } finally {
                if (fs != null)
                    fs.close();
                if (isr != null)
                    isr.close();
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

    /**
     * 写二进制文件
     *
     * @param inputStream 二进制流
     * @param newpath     保存路径
     * @param newfilename 保存的文件名
     * @return 保存的文件名
     */
    public static String writeFile(InputStream inputStream, String newpath,
                                   String newfilename) {
        FileOutputStream outStream = null;
        try {
            newpath = newpath.replace(File.separator, "/");
            if (newpath.endsWith("/")) {
                newpath = newpath.substring(0, newpath.length() - 1);
            }
            writeText(newpath + "/" + newfilename, "", "");
            outStream = new FileOutputStream(newpath + "/" + newfilename);
            byte[] buffer = new byte[1024 * 5];
            int byteread;
            while ((byteread = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, byteread);
                outStream.flush();
            }
        } catch (Exception ex) {
            newfilename = "";
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                newfilename = "";
            }
        }
        return newfilename;
    }

    /**
     * 写二进制文件
     *
     * @param byteA       二进数组
     * @param newpath     保存路径
     * @param newfilename 保存的文件名
     * @return 保存的文件名
     */
    public static String writeFile(byte[] byteA, String newpath,
                                   String newfilename) {
        FileOutputStream outStream = null;
        try {
            newpath = newpath.replace(File.separator, "/");
            if (newpath.endsWith("/")) {
                newpath = newpath.substring(0, newpath.length() - 1);
            }
            writeText(newpath + "/" + newfilename, "", "");
            outStream = new FileOutputStream(newpath + "/" + newfilename);
            outStream.write(byteA);
            outStream.flush();
        } catch (Exception ex) {
            newfilename = "";
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (IOException e) {
                newfilename = "";
            }
        }
        return newfilename;
    }

    /**
     * 读取二进制文件
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @return 二进制流
     */
    public static byte[] readFile(String filePathAndName) {
        byte[] byteA = null;
        try {
            File file = new File(filePathAndName);
            if (file.exists()) {
                InputStream inputStream = new FileInputStream(file);
                inputStream.read(byteA);
                inputStream.close();
            }
        } catch (Exception ex) {
        }
        return byteA;
    }

    /**
     * 多级目录创建
     *
     * @param multiFolderPath 要创建的目录路径
     * @return 创建好的目录路径
     */
    public static String createFolders(String multiFolderPath) {
        String cuttentFolder = "";
        try {
            multiFolderPath = multiFolderPath.replace(File.separator, "/");
            String[] multi = multiFolderPath.split("/");
            if (multi.length == 0)
                return "";
            for (int i = 0; i < multi.length; i++) {
                cuttentFolder += multi[i] + "/";
                createFolder(cuttentFolder);
            }
        } catch (Exception e) {
            cuttentFolder = "";
        }
        return cuttentFolder;
    }

    /**
     * 新建目录
     *
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    private static String createFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists())
                myFilePath.mkdir();

        } catch (Exception e) {
            folderPath = "";
        }
        return folderPath;
    }

    /**
     * 删除文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true 遭遇异常返回false
     */
    public static boolean deleteFile(String filePathAndName) {
        boolean ret = false;
        try {
            File myDelFile = new File(filePathAndName);
            if (myDelFile.isFile() && myDelFile.exists()) {
                myDelFile.delete();
                ret = true;
            } else {
                ret = false;
            }
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param filePath 文件夹完整绝对路径
     * @return 成功删除返回true 遭遇异常返回false
     */
    public static boolean deleteAllFile(String filePath) {
        boolean ret = false;
        filePath = filePath.replace(File.separator, "/");
        File file = new File(filePath);
        if (!file.exists()) {
            return ret;
        }
        if (!file.isDirectory()) {
            return ret;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (filePath.endsWith("/")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
            temp = new File(filePath + File.separator + tempList[i]);
            if (temp.isFile()) {
                temp.delete();
                ret = true;
            } else if (temp.isDirectory()) {
                deleteAllFile(filePath + "/" + tempList[i]);// 先删除文件夹里面的文件
                deleteFolder(filePath + "/" + tempList[i]);// 再删除空文件夹
                ret = true;
            }
        }
        return ret;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     * @return 成功删除返回true 遭遇异常返回false
     */
    public static boolean deleteFolder(String folderPath) {
        boolean ret = false;
        try {
            deleteAllFile(folderPath);// 删除完里面所有内容
            File myFilePath = new File(folderPath);
            myFilePath.delete(); // 删除空文件夹
            ret = true;
        } catch (Exception e) {
            ret = false;
        }
        return ret;
    }

    /**
     * 复制单个文件
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return 成功复制返回true 遭遇异常返回false
     */
    public static boolean copyFile(String oldPathFile, String newPathFile) {
        boolean ret = false;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { // 文件存在时
                writeText(newPathFile, "", "");
                inputStream = new FileInputStream(oldPathFile); // 读入原文件
                outputStream = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inputStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    outputStream.write(buffer, 0, byteread);
                    outputStream.flush();
                }
                ret = true;
            }
        } catch (Exception e) {
            ret = false;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ex) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 复制目录
     *
     * @param oldPath      准备拷贝的目录
     * @param newPath      指定绝对路径的新目录
     * @param isCopyFolder 是否复制根目录 true 为复制根目录 false 为不复制根目录，只复制根目录下的所有文件以及文件夹
     * @return 成功复制返回true 遭遇异常返回false
     */
    public static boolean copyFolder(String oldPath, String newPath,
                                     boolean isCopyFolder) {
        boolean ret = false;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            createFolders(newPath);
            oldPath = oldPath.replace(File.separator, "/");
            File file = new File(oldPath);
            String[] files = file.list();
            File temp = null;
            String oldForder;
            for (int i = 0; i < files.length; i++) {
                if (oldPath.endsWith("/")) {
                    oldPath = oldPath.substring(0, oldPath.length() - 1);
                }
                oldForder = oldPath.substring(oldPath.lastIndexOf("/") + 1);
                temp = new File(oldPath + File.separator + files[i]);
                if (temp.isFile()) {
                    inputStream = new FileInputStream(temp);
                    if (isCopyFolder) {
                        createFolders(newPath + "/" + oldForder);
                        outputStream = new FileOutputStream(newPath + "/"
                                + oldForder + "/" + (temp.getName()).toString());
                    } else {
                        outputStream = new FileOutputStream(newPath + "/"
                                + (temp.getName()).toString());
                    }
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = inputStream.read(b)) != -1) {
                        outputStream.write(b, 0, len);
                        outputStream.flush();
                    }
                    ret = true;
                } else if (temp.isDirectory()) {// 如果是子文件夹
                    if (isCopyFolder) {
                        copyFolder(oldPath + "/" + files[i], newPath + "/"
                                + oldForder + "/" + files[i], isCopyFolder);
                    } else {
                        copyFolder(oldPath + "/" + files[i], newPath + "/"
                                + files[i], isCopyFolder);
                    }
                    ret = true;
                }
            }
        } catch (Exception e) {
            ret = false;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (Exception ex) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 移动文件
     *
     * @param oldPath 准备移动的文件
     * @param newPath 移动到新绝对路径带文件名
     * @return 成功移动返回true 遭遇异常返回false
     */
    public static boolean moveFile(String oldPath, String newPath) {
        boolean ret = false;
        if (copyFile(oldPath, newPath)) {
            if (deleteFile(oldPath))
                ret = true;
        }
        return ret;
    }

    /**
     * 移动目录
     *
     * @param oldPath      准备移动的目录
     * @param newPath      移动到指定绝对路径的新目录
     * @param isMoveFolder 是否移动根目录 true 为移动根目录 false 为不移动根目录，只移动根目录下的所有文件以及文件夹
     * @return 成功移动返回true 遭遇异常返回false
     */
    public static boolean moveFolder(String oldPath, String newPath,
                                     boolean isMoveFolder) {
        boolean ret = false;
        if (copyFolder(oldPath, newPath, isMoveFolder)) {
            if (deleteFolder(oldPath))
                ret = true;
        }
        return ret;
    }

    /**
     * 搜索文件夹
     *
     * @param folderPath 文件夹路径
     * @param folderName 要搜索的文件夹名称
     * @return 返回文件夹路径
     */
    public static String searchFolders(String folderPath, String folderName) {
        folderPath = folderPath.replace(File.separator, "/");
        try {
            File tempfile = new File(folderPath);
            String tempPath;
            if (!tempfile.exists()) return "";
            if (tempfile.isDirectory()) {
                File[] fList = tempfile.listFiles();
                for (int i = 0; i < fList.length; i++) {
                    if (fList[i].isDirectory()) {
                        if (folderPath.endsWith("/"))
                            folderPath = folderPath.substring(0, folderPath.length() - 1);
                        tempPath = folderPath + "/" + fList[i].getName();
                        if ((fList[i].getName()).equals(folderName)) {
                            return tempPath;
                        } else {
                            tempPath = searchFolders(folderPath + "/" + fList[i].getName(), folderName);
                            if (!tempPath.equals("")) return tempPath;
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    /**
     * 搜索文件
     *
     * @param folderPath 文件夹路径
     * @param fileName   要搜索的文件名称
     * @return 返回文件路径
     */
    public static String searchFile(String folderPath, String fileName) {
        folderPath = folderPath.replace(File.separator, "/");
        try {
            File tempfile = new File(folderPath);
            String tempPath;
            if (!tempfile.exists()) return "";
            if (tempfile.isFile()) {
                if (tempfile.getName().equals(fileName)) return folderPath;
            }
            if (tempfile.isDirectory()) {
                File[] fList = tempfile.listFiles();
                for (int i = 0; i < fList.length; i++) {
                    if (folderPath.endsWith("/"))
                        folderPath = folderPath.substring(0, folderPath.length() - 1);
                    tempPath = searchFile(folderPath + "/" + fList[i].getName(), fileName);
                    if (!tempPath.equals("")) return tempPath;
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }
//
//    /**
//     * 测试方法
//     *
//     * @param args
//     */
//    public static void main(String args[]) {
//        System.out.println(FileUtils.searchFile("C:/1/2/1.bmp", "1.bmp"));
//        // 写文本文件
//        System.out.println(FileUtils.writeText("c:\\1.txt", "123txt浣犲ソ", "utf8"));
//        System.out
//                .println(FileUtils.writeText("c:/1\\2/3.txt", "123txt你好", "GBK"));
//        //结尾追加文本
//        System.out.println(FileUtils.appendWriteText("c:/1\\2/3.txt", "这里是追加的信息", "GBK"));
//
//        // 读文本文件
//        System.out.println(FileUtils.readText("c:\\1.txt", "utf8"));
//        System.out.println(FileUtils.readText("c:/1\\2/3.txt", "GBK"));
//
//        // 读二进制文件
//        System.out.println(FileUtils.readFile("c:\\the_flex_show.jpg"));
//
//        // 写二进制文件
//        System.out.println(FileUtils.writeFile(FileUtils
//                .readFile("c:\\the_flex_show.jpg"), "c:", "flex.jpg"));
//        System.out.println(FileUtils.writeFile(FileUtils
//                .readFile("c:\\the_flex_show.jpg"), "c:\\", "flex.jpg"));
//        System.out.println(FileUtils.writeFile(FileUtils
//                .readFile("c:\\the_flex_show.jpg"), "c:\\1/2\\", "flex.jpg"));
//
//        // 多级目录创建
//        System.out.println(FileUtils.createFolders("c:"));
//        System.out.println(FileUtils.createFolders("c:\\"));
//        System.out.println(FileUtils.createFolders("c:\\2\\3/4"));
//        System.out.println(FileUtils.createFolders("c:\\2\\3/4/"));
//        System.out.println(FileUtils.createFolders("c:\\2\\3/4/1.txt"));
//
//        // 删除文件
//        System.out.println(FileUtils.deleteFile("c:\\1"));
//        System.out.println(FileUtils.deleteFile("c:/1.txt"));
//        System.out.println(FileUtils.deleteFile("c:/1/2/3.txt"));
//        System.out.println(FileUtils.deleteFile("c:\\1\\2\\flex.jpg"));
//
//        // 删除所有文件
//        System.out.println(FileUtils.deleteAllFile("c:/1/2/"));
//        System.out.println(FileUtils.deleteAllFile("c:\\1"));
//
//        // 删除文件夹
//        System.out.println(FileUtils.deleteFolder("c:/2"));
//
//        // 拷贝文件
//        System.out.println(FileUtils.copyFile("c:\\the_flex_show.jpg",
//                "c:\\flex/the_flex_show.jpg"));
//
//        // 拷贝文件夹
//        System.out.println(FileUtils.copyFolder("c:\\flex", "C:/1", true));
//        System.out.println(FileUtils.copyFolder("c:\\flex", "C:/1", false));
//
//        // 移动文件
//        System.out.println(FileUtils.moveFile("c:\\the_flex_show.jpg",
//                "C:/1/2/111.jpg"));
//        System.out.println(FileUtils.moveFolder("C:/1/flex", "C:/1/2/", false));
//        System.out.println(FileUtils.moveFolder("C:/1/flex", "C:/1/2/", true));
//
//        // 搜索文件夹
//        System.out.println(FileUtils.searchFolders("C:\\1", "2"));
//        // 搜索文件
//        System.out.println(FileUtils.searchFile("C:/1/2/1.bmp", "1.bmp"));
//    }

    public static String getContentType(String fileName) {
        String extName = getExtName(fileName);
        if (org.springframework.util.StringUtils.isEmpty(extName)) {
            return "";
        }
        String contentType = "";
        if (extName.equals("png")) {
            contentType = "image/png";
        } else if (extName.equals("jpeg") || extName.equals("jpg")) {
            contentType = "text/jpeg";
        } else if (extName.equals("gif")) {
            contentType = "text/gif";
        } else if (extName.equals("bmp")) {
            contentType = "text/bmp";
        } else if (extName.equals("zip")) {
            contentType = "application/zip";
        } else if (extName.equals("rar")) {
            contentType = "application/x-rar-compressed";
        } else if (extName.equals("exe")) {
            contentType = "application/x-msdownload";
        } else if (extName.equals("pdf")) {
            contentType = "application/pdf";
        } else if (extName.equals("doc")) {
            contentType = "application/msword";
        } else if (extName.equals("docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (extName.equals("xls")) {
            contentType = "application/vnd.ms-excel";
        } else if (extName.equals("xlsx")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (extName.equals("ppt")) {
            contentType = "application/vnd.ms-powerpoint";
        } else if (extName.equals("pptx")) {
            contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else if (extName.equals("apk")) {
            contentType = "application/vnd.android.package-archive";
        } else {
            contentType = "application/x-msdownload";
        }
        return contentType;
    }

    public static String getExtNameByContentType(String contentType) {
        if (org.springframework.util.StringUtils.isEmpty(contentType)) {
            return "";
        }
        if (contentType.contains("application/vnd.ms-excel")) {
            return ".xls";
        }
        if (contentType.contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return ".xlsx";
        }
        return "";
    }

    public static String getExtName(String fileName) {
        if (org.springframework.util.StringUtils.isEmpty(fileName) || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getFileName(String fileName) {
   /*     if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));*/
        return fileName;
    }

    public static String encodeFileName(String userAgent, String fileName) {
        fileName = getFileName(fileName);
        if (!org.springframework.util.StringUtils.isEmpty(userAgent) && !org.springframework.util.StringUtils.isEmpty(fileName)) {
            if (userAgent.indexOf("Windows") == -1 || userAgent.indexOf("MSIE") != -1 || userAgent.indexOf("Trident") != -1 || userAgent.indexOf("Edge") != -1) {
                try {
                    return URLEncoder.encode(fileName, "UTF8");
                } catch (UnsupportedEncodingException e) {
                    return fileName;
                }
            } else if (userAgent.indexOf("Mozilla") != -1) {
                try {
                    return "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8")))) + "?=";
                } catch (UnsupportedEncodingException e) {
                    return fileName;
                }
            }
        }
        return fileName;
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        download(request, response, new FileInputStream(file), file.getName());
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        download(request, response, getTempFile(fileName));
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String downloadFileWithPath, String originFileName) throws IOException {
        if (!StringUtils.isEmpty(downloadFileWithPath)) {
            if (downloadFileWithPath.indexOf(File.separator) > 0) {
                download(request, response, new FileInputStream(downloadFileWithPath), originFileName);
            } else {
                download(request, response, new FileInputStream(getTempFile(downloadFileWithPath)), originFileName);
            }
        }
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, InputStream in, String fileName) throws IOException {
        response.setContentType(getContentType(fileName));
        response.setHeader("content-disposition", "attachment; filename=\"" + encodeFileName(request.getHeader("USER-AGENT"), fileName) + "\"");
        ServletOutputStream os = response.getOutputStream();
        int readLength;
        byte[] buf = new byte[4096];
        while (((readLength = in.read(buf)) != -1)) {
            os.write(buf, 0, readLength);
        }
        os.close();
        in.close();
    }

    public static File getTempFile(String fileName) {
        return new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
    }

    public static Integer save(InputStream in, OutputStream os) throws IOException {
        Integer fileSize = 0;
        int readLength = 0;
        byte[] buf = new byte[4096];
        while (((readLength = in.read(buf)) != -1)) {
            os.write(buf, 0, readLength);
            fileSize += readLength;
        }
        in.close();
        os.close();
        return fileSize;
    }
}
