package com.cosun.cosunp.tool;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.cosun.cosunp.tool.StringUtil.formateString;

/**
 * @author:homey Wong
 * @date:2019/1/8 0008 下午 7:11
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FileUtil {

    /**
     * 第一步：判断文件是否为空   true：返回提示为空信息   false：执行第二步
     * 第二步：判断目录是否存在   不存在：创建目录
     * 第三部：通过输出流将文件写入硬盘文件夹并关闭流  fileName.substring(0,fileName.lastIndexOf("."))+"_CY"+
     * @param file
     * @return
     */
    public static String uploadFile(MultipartFile file,String Username){
        String firstCharUpCase = PinYinUtil.toFirstCharUpCase(Username);
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String userNamePinYin = PinYinUtil.toPinyin(Username);
        String filePath = "F:\\file\\"+userNamePinYin+"\\";
        File targetFile = new File(filePath);
        String deskName = formateString(new Date())+firstCharUpCase+MathUtil.getRandom620(5)+ext;
        //：判断目录是否存在   不存在：创建目录
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        //：通过输出流将文件写入硬盘文件夹并关闭流
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(filePath+deskName));
            stream.write(file.getBytes());
            stream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath+deskName;
    }

    /**
     * 判断文件大小
     *
     * @param :multipartFile:上传的文件
     * @param size: 限制大小
     * @param unit:限制单位（B,K,M,G)
     * @return boolean:是否大于
     */
    public static boolean checkFileSize(List<MultipartFile> multipartFiles, int size, String unit) {
        long len = 0;  //上传文件的大小, 单位为字节.
        for(MultipartFile file : multipartFiles){
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

}
