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
            for (File fileIndex : files) { //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath());
                } else { //如果文件是普通文件，则将文件句柄放入集合中
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

    public static void modifyUpdateFileByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) {
        File targetFile = new File(oldPath);
        //：判断目录是否存在   不存在：创建目录
        if (targetFile.exists()) {
            //：通过输出流将文件写入硬盘文件夹并关闭流
            BufferedOutputStream stream = null;
            try {
                stream = new BufferedOutputStream(new FileOutputStream(oldPath));
                stream.write(file.getBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
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

    public static void modifyUpdateFileFolderByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) {
        File targetFile = new File(oldPath);
        //：判断目录是否存在   不存在：创建目录
        if (targetFile.exists()) {
            //：通过输出流将文件写入硬盘文件夹并关闭流
            BufferedOutputStream stream = null;
            try {
                stream = new BufferedOutputStream(new FileOutputStream(oldPath));
                stream.write(file.getBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //根据文件夹上传 找到数据库老路径存文件
    public static void uploadFileFolderByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) {
        String fileName = file.getOriginalFilename().replaceAll("/", "\\\\");
        File targetFile = new File(oldPath);
        //：判断目录是否存在   不存在：创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(oldPath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
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

    public static void uploadFileByUrl(MultipartFile file, UserInfo userInfo, DownloadView view, String oldPath) {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(oldPath);
        //：判断目录是否存在   不存在：创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(oldPath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 第一步：判断文件是否为空   true：返回提示为空信息   false：执行第二步
     * 第二步：判断目录是否存在   不存在：创建目录
     * 第三部：通过输出流将文件写入硬盘文件夹并关闭流  fileName.substring(0,fileName.lastIndexOf("."))+"_CY"+
     *
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file, UserInfo userInfo, DownloadView view, String randomnum) {
        String fileName = file.getOriginalFilename();
        String salorpinyinPinYin = view.getSalor();
        String filePath = "F:\\" + userInfo.getuId() + "\\" + formateString(new Date()) + "\\" + salorpinyinPinYin + "\\"
                + randomnum + "\\" + view.getOrderNo() + "\\";
        File targetFile = new File(filePath);
        //：判断目录是否存在   不存在：创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath + fileName;
    }


    //上传文件夹内的文件（单个）
    public static String uploadFileFolder(MultipartFile file, DownloadView view, String randomnum) {
        String fileName = file.getOriginalFilename().replaceAll("/", "\\\\");
        int index = fileName.lastIndexOf("\\");
        String centerPath = fileName.substring(0,index);
        fileName = fileName.substring(index+1,fileName.length());
        String filePath = "F:\\" + view.getUserName() + "\\" + formateString(new Date()) + "\\" + view.getSalor() + "\\"
                + randomnum + "\\" + view.getOrderNo() + "\\"+centerPath+"\\";
        File targetFile = new File(filePath);
        //：判断目录是否存在   不存在：创建目录
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath + fileName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath + fileName;
    }

    /**
     * 判断上传的文件大小
     *
     * @param :multipartFile:上传的文件
     * @param size:                限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     */
    public static boolean checkFileSize(List<MultipartFile> multipartFiles, int size, String unit) {
        long len = 0;  //上传文件的大小, 单位为字节.
        for (MultipartFile file : multipartFiles) {
            len += file.getSize();
        }
        //准备接收换算后文件大小的容器
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
        //如果上传文件大于限定的容量
        if (fileSize > size) {
            return false;
        }
        return true;
    }


    /**
     * 判断上传的文件大小
     *
     * @param :multipartFile:上传的文件
     * @param :                限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     */
    public static double getFileSize(List<MultipartFile> multipartFiles, String unit) {
        long len = 0;  //上传文件的大小, 单位为字节.
        for (MultipartFile file : multipartFiles) {
            len += file.getSize();
        }
        //准备接收换算后文件大小的容器
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
        //如果上传文件大于限定的容量

       return fileSize;
    }


    public static double getFileSizeFrSingle(MultipartFile multipartFiles, String unit) {
        long len = multipartFiles.getSize();

        //准备接收换算后文件大小的容器
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
        //如果上传文件大于限定的容量

        return fileSize;
    }

    /**
     * 判断要下载的文件大小
     *
     * @param :multipartFile:上传的文件
     * @param size:                限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     */
    public static boolean checkDownloadFileSize(List<File> files, int size, String unit) {
        long len = 0;  //上传文件的大小, 单位为字节.
        for (File file : files) {
            len += file.length();
        }
        //准备接收换算后checkFileSize文件大小的容器
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
        //如果上传文件大于限定的容量
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
    public static boolean checkDownloadFileSize(File file, int size, String unit) {
        long len = file.length();
       //上传文件的大小, 单位为字节.

        //准备接收换算后文件大小的容器
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
        //如果上传文件大于限定的容量
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    public static Cookie addCookie(String cookieName,String cookieValue) {
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24);
       return cookie;
    }

}
