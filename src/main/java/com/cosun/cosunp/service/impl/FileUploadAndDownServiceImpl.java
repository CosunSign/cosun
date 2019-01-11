package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.MathUtil;
import com.cosun.cosunp.tool.PinYinUtil;
import com.cosun.cosunp.tool.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:37
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:37
 */
@Service
public class FileUploadAndDownServiceImpl implements IFileUploadAndDownServ {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private FileUploadAndDownMapper fileUploadAndDownMapper;

    @Transactional
    @Override
    public void saveOrUpdateFilePrivilege(Integer selectuser, Integer filesId, String privileflag) {
        fileUploadAndDownMapper.saveOrUpdateFilePrivilege(selectuser, filesId, privileflag);
        fileUploadAndDownMapper.saveOrUpdateFileUrlPrivilege(filesId, privileflag);
    }

    @Override
    public List<UserInfo> findAllUser() {
        return userInfoMapper.findAllUser();
    }


//    @Transactional
//    @Override
//    public DownloadView addFileFoldersData(DownloadView view,List<MultipartFile> files,UserInfo userInfo){
//        if (files == null || files.size() <= 0) { // -1 //空文件返回-1
//            view.setFlag("0");
//        }
//        String orginname = "";//原始文件名
//        String deskName = "";//程序自定义文件名
//        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
//        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
//        FileManFileInfo fileManFileInfo;
//        FilemanUrl filemanUrl;
//        FilemanRight filemanRight;
//
//        //存文件的人与文件信息
//        fileManFileInfo = new FileManFileInfo();
//        fileManFileInfo.setCreateTime(new Date());
//        fileManFileInfo.setCreateUser(userInfo.getUserName());
//        fileManFileInfo.setUserName(userInfo.getUserName());
//        fileManFileInfo.setuId(userInfo.getuId());
//        fileManFileInfo.setFileName(view.getFileName());
//        fileManFileInfo.setExtInfo1(view.getSalor());
//        fileManFileInfo.setOrderNum(view.getOrderNo());
//        fileManFileInfo.setProjectName(view.getProjectName());
//        fileManFileInfo.setTotalFilesNum(files.size());
//        fileManFileInfo.setRemark(view.getRemark());
//        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
//        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);
//
//
//        for (int i = 0; i < files.size(); i++) {
//            try {
//                deskName = FileUtil.uploadFile(files.get(i), userInfo.getUserName());
//                orginname = files.get(i).getOriginalFilename();
//
//
//                //存储文件路径
//                filemanUrl = new FilemanUrl();
//                filemanUrl.setOrginName(orginname);
//                filemanUrl.setUserName(userInfo.getUserName());
//                filemanUrl.setOpRight("1,2,3");
//                filemanUrl.setLogur1(deskName);
//                filemanUrl.setFileInfoId(fileManFileInfo.getId());
//                filemanUrls.add(filemanUrl);
//                fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);
//
//                //存储文件权限
//                filemanRight = new FilemanRight();
//                filemanRight.setCreateTime(view.getCreateTime());
//                filemanRight.setCreateUser(userInfo.getUserName());
//                filemanRight.setFileName(orginname);
//                filemanRight.setuId(userInfo.getuId());
//                filemanRight.setUserName(userInfo.getUserName());
//                filemanRight.setFileUrlId(filemanUrl.getId());
//                filemanRight.setFileInfoId(fileManFileInfo.getId());
//                filemanRight.setOpRight("1,2,3");
//                filemanRights.add(filemanRight);
//                fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
//                view.setFlag("1");
//
//            } catch (Exception e) {
//                view.setFlag("-1");
//                e.printStackTrace();
//            }
//        }
//        return view;
//    }


    @Override
    @Transactional
    public DownloadView findIsExistFiles(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo){
        boolean flag = false;//判断是否存的是旧项目新增新文件   true代表是以前存过一次的
        FilemanUrl filemanUrl = null;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        String message = "";
        String oldPath = "";
        int isExistnum = 0;
        List<FilemanUrl> urlolds;
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(),view.getOrderNo(),view.getSalor());
        //代表上传的是前一次上传的订单
            for (MultipartFile file : fileArray) {
                filemanUrl = fileUploadAndDownMapper.findIsExistFile(file.getOriginalFilename());
                if (filemanUrl == null) {
                    //为空 可以存服务器
                    newFileArray.add(file);
                } else {
                    //不为空
                    message += file.getOriginalFilename() + ",<br>";
                    oldPath = filemanUrl.getLogur1();
                    isExistnum++;
                    flag = true;//代表此次存的文件已前存过一次，没有的文件存在本文件夹里面

                }
            }
        if(fileManFileInfo.size()<=0 && !flag && newFileArray.size()>0){//代表上传的为全新文件
            view = this.addFilesData(view, fileArray, userInfo);
        } else if ( fileManFileInfo.size()>0&&newFileArray.size()>0) {//代表此次存的文件已前存过一次，此次fileARrray收集的是以前存过一次，现在新增的文件
            view.setExistFileMessage(message);
            view.setIsExistNum(isExistnum);
            if(oldPath=="" || oldPath==null){
                urlolds =  fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
                if(urlolds.size()>0) {
                    view = addOldOrderNoNewFiles(view, newFileArray, userInfo, urlolds.get(0).getLogur1(), fileManFileInfo);
                    view.setFlag("1");
                }
            }else {
                view = addOldOrderNoNewFiles(view, newFileArray, userInfo, oldPath, fileManFileInfo);
            }
        } else if(fileManFileInfo.size()>0 && flag&& newFileArray.size()<=0){//代表全为已上传文件
            view.setFlag("-10");
        }else if(flag && fileManFileInfo.size()<=0 && newFileArray.size()==0){
            view.setFlag("-6");
            view.setOrderNoMessage("您本次存储的订单编号等信息"+view.getOrderNo()+","+view.getSalor()+","+view.getUserName()+","+"与上次存储的信息不符，请您核对后再上传!");
        }

        return view;
    }


    @Transactional
    public DownloadView addOldOrderNoNewFiles(DownloadView view,List<MultipartFile> fileArray,UserInfo userInfo, String oldPath,List<FileManFileInfo> fileManFileInfos ){
      //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        String oldsPath = StringUtil.subMyString(oldPath,"\\");
        String orginname = "";//原始文件名
        String deskName = "";//程序自定义文件名
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        //首先做文件订单信息核 对
        //不为空，即可以向URL表和RIGHT表插入数据，并向服务器存入新增文件
            fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum()+fileArray.size());
            fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount()+1);
            fileManFileInfo.setUpdateTime(new Date());
            fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(),fileManFileInfo.getUpdateCount(),fileManFileInfo.getUpdateTime(),fileManFileInfo.getId());
            for (MultipartFile file : fileArray) {
                deskName = oldsPath+orginname;
                FileUtil.uploadFileByUrl(file, userInfo,view,oldPath);
                orginname = file.getOriginalFilename();

                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1,2,3,4");
                filemanUrl.setLogur1(deskName);
                filemanUrl.setFileInfoId(fileManFileInfo.getId());
                filemanUrls.add(filemanUrl);
                fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

                //存储文件权限
                filemanRight = new FilemanRight();
                filemanRight.setCreateTime(view.getCreateTime());
                filemanRight.setCreateUser(userInfo.getUserName());
                filemanRight.setFileName(orginname);
                filemanRight.setuId(userInfo.getuId());
                filemanRight.setUserName(userInfo.getUserName());
                filemanRight.setFileUrlId(filemanUrl.getId());
                filemanRight.setFileInfoId(fileManFileInfo.getId());
                filemanRight.setOpRight("1,2,3,4");
                filemanRights.add(filemanRight);
                fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
            }
        //为空 代表文件服务器中没有，但所填写的订单信息与数据库不匹配，此时中止行为，反回界面提示


      return view;

    }


    @Transactional
    @Override
    public DownloadView addFilesData(DownloadView view, List<MultipartFile> files, UserInfo userInfo) {
        if (files == null || files.size() <= 0) { // //空文件返回 0
            view.setFlag("0");
        }
        String randomNum = MathUtil.getRandom620(8);
        String orginname = "";//原始文件名
        String deskName = "";//程序自定义文件名
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FileManFileInfo fileManFileInfo;
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;

        //存文件的人与文件信息
        fileManFileInfo = new FileManFileInfo();
        fileManFileInfo.setCreateTime(new Date());
        fileManFileInfo.setCreateUser(userInfo.getUserName());
        fileManFileInfo.setUserName(userInfo.getUserName());
        fileManFileInfo.setuId(userInfo.getuId());
        fileManFileInfo.setFileName(view.getFileName());
        fileManFileInfo.setExtInfo1(view.getSalor());
        fileManFileInfo.setOrderNum(view.getOrderNo());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileManFileInfo.setTotalFilesNum(files.size());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);



        for (int i = 0; i < files.size(); i++) {
            try {
                deskName = FileUtil.uploadFile(files.get(i), userInfo,view,randomNum);
                orginname = files.get(i).getOriginalFilename();


                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1,2,3,4");
                filemanUrl.setLogur1(deskName);
                filemanUrl.setFileInfoId(fileManFileInfo.getId());
                filemanUrls.add(filemanUrl);
                fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);

                //存储文件权限
                filemanRight = new FilemanRight();
                filemanRight.setCreateTime(view.getCreateTime());
                filemanRight.setCreateUser(userInfo.getUserName());
                filemanRight.setFileName(orginname);
                filemanRight.setuId(userInfo.getuId());
                filemanRight.setUserName(userInfo.getUserName());
                filemanRight.setFileUrlId(filemanUrl.getId());
                filemanRight.setFileInfoId(fileManFileInfo.getId());
                filemanRight.setOpRight("1,2,3,4");
                filemanRights.add(filemanRight);
                fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
                view.setFlag("1");

            } catch (Exception e) {
                view.setFlag("-1");
                e.printStackTrace();
            }
        }

        return view;
}



    /**
     * @author:homey Wong
     * @Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByUserId(Integer uid) {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByUserId(uid);
        return list;
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByCondition(Integer uid, int currentPageTotalNum, int PageSize) {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByCondition(uid, currentPageTotalNum, PageSize);
        return list;
    }

    /**
     * 功能描述:分页
     *
     * @auther: homey Wong
     * @date: 2018/12/29 0029 上午 9:47
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public int findAllUploadFileCountByUserId(Integer uId) {
        return fileUploadAndDownMapper.findAllUploadFileCountByUserId(uId);

    }

    @Override
    public List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) {
        return fileUploadAndDownMapper.findAllUploadFileByParaCondition(view);
    }

    @Override
    public int findAllUploadFileCountByParaCondition(DownloadView view) {
        return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
    }
}
