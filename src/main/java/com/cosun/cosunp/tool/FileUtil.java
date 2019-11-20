package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.FilemanUrl;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cosun.cosunp.tool.StringUtil.formateString;
import static com.cosun.cosunp.tool.StringUtil.subAfterString;

/**
 * @author:homey Wong
 * @date:2019/1/8 0008 下午 7:11
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FileUtil {


    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;

    public static ArrayList<File> getFiles(String path) throws Exception { //目标集合fileList
        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else {
                    fileList.add(fileIndex);
                }
            }
        }
        return fileList;
    }


    /**
     * 功能描述:文件覆盖操作
     *
     * @auther: homey Wong
     * @date: 2019/1/11 0011 上午 10:32
     * @param:
     * @return:
     * @describtion
     */

    public static void modifyUpdateFileByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) throws Exception {
        File targetFile = new File(oldPath);
        if (targetFile.exists()) {
            BufferedOutputStream stream = null;
            try {
                stream = new BufferedOutputStream(new FileOutputStream(oldPath));
                stream.write(file.getBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

    }


    /**
     * 功能描述:文件夹覆盖操作
     *
     * @auther: homey Wong
     * @date: 2019/1/16 0011 上午 10:32
     * @param:
     * @return:
     * @describtion
     */

    public static void modifyUpdateFileFolderByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) throws Exception {
        File targetFile = new File(oldPath);
        if (targetFile.exists()) {
            BufferedOutputStream stream = null;
            try {
                stream = new BufferedOutputStream(new FileOutputStream(oldPath));
                stream.write(file.getBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

    }

    public static void uploadFileFolderByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) throws Exception {
        String fileName = file.getOriginalFilename().replaceAll("/", "\\\\");
        File targetFile = new File(oldPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(oldPath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    /**
     * 功能描述:根据已存在的路径存文件
     *
     * @auther: homey Wong
     * @date: 2019/1/10 0010 下午 8:39
     * @param:
     * @return:
     * @describtion
     */

    public static void uploadFileByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) throws Exception {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(oldPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(oldPath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    public static void uploadFileForRules(MultipartFile file, String descDir) throws Exception {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(descDir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(descDir + fileName));
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    public static void uploadFileForEmployeeAppend(MultipartFile file, String descDir) throws Exception {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(descDir);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(descDir + fileName));
            stream.write(file.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }


    public static String uploadOrderAppend(String descUrl, String engName, String orderNo, MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String filePath = descUrl + "order" + "\\" + engName + "\\" + orderNo + "\\";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return filePath + fileName;
    }


    public static String uploadFile(MultipartFile file, UserInfo userInfo, DownloadView view, String randomnum) throws Exception {
        String fileName = file.getOriginalFilename();
        String salorpinyinPinYin = view.getSalor();
        String filePath = "F:\\" + userInfo.getuId() + "\\" + formateString(new Date()) + "\\" + salorpinyinPinYin + "\\"
                + randomnum + "\\" + view.getOrderNo() + "\\";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return filePath + fileName;
    }


    public static String uploadFileFolder(MultipartFile file, DownloadView view, String randomnum) throws Exception {
        String fileName = file.getOriginalFilename().replaceAll("/", "\\\\");
        int index = fileName.lastIndexOf("\\");
        String centerPath = fileName.substring(0, index);
        fileName = fileName.substring(index + 1, fileName.length());
        String filePath = "F:\\" + view.getUserName() + "\\" + formateString(new Date()) + "\\" + view.getSalor() + "\\"
                + randomnum + "\\" + view.getOrderNo() + "\\" + centerPath + "\\";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return filePath + fileName;
    }


    public static boolean checkFileSize(List<MultipartFile> multipartFiles, int size, String unit) throws Exception {
        long len = 0;
        for (MultipartFile file : multipartFiles) {
            len += file.getSize();
        }
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }


    public static double getFileSize(List<MultipartFile> multipartFiles, String unit) throws Exception {
        long len = 0;
        for (MultipartFile file : multipartFiles) {
            len += file.getSize();
        }
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }

        return fileSize;
    }


    public static double getFileSizeFrSingle(MultipartFile multipartFiles, String unit) throws Exception {
        long len = multipartFiles.getSize();

        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }

        return fileSize;
    }


    public static boolean checkDownloadFileSize(List<File> files, int size, String unit) throws Exception {
        long len = 0;
        for (File file : files) {
            len += file.length();
        }
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }


    /**
     * 判断要下载的文件大小
     *
     * @param :multipartFile:上传的文件
     * @param size:                限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     */
    public static boolean checkDownloadFileSize(File file, int size, String unit) throws Exception {
        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    public static Cookie addCookie(String cookieName, String cookieValue) throws Exception {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24);
        return cookie;
    }


    public static void delFolderIFNoFiles(String folderPath) throws Exception {
        File file = new File(folderPath);//
        File[] list = file.listFiles();
        if (list == null || list.length == 0) {
            delFolder(folderPath);
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径 ,"Z:/xuyun/save"
     */
    public static void delFolder(String folderPath) throws Exception {
        try {
            delAllFile(folderPath);
            File myFilePath = new File(folderPath);
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径 ,"Z:/xuyun/save"
     */
    public static boolean delAllFile(String path) throws Exception {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


    public static void delFile(String filePath) throws Exception {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists() && file.isFile())
                file.delete();
        }
    }

    public static void delFolderNew(String folderPath) {
        try {
            delAllFileNew(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delAllFileNew(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFileNew(path + "/" + tempList[i]);
                delFolderNew(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


}
