package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


    @Transactional
    @Override
    public DownloadView addFileFoldersData(DownloadView view,List<MultipartFile> files,UserInfo userInfo){
        if (files == null || files.size() <= 0) { // -1 //空文件返回-1
            view.setFlag("0");
        }
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
                deskName = FileUtil.uploadFile(files.get(i), userInfo.getUserName());
                orginname = files.get(i).getOriginalFilename();


                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1,2,3");
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
                filemanRight.setOpRight("1,2,3");
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

    @Transactional
    @Override
    public DownloadView addFilesData(DownloadView view, MultipartFile[] files, UserInfo userInfo) {
        if (files == null || files.length <= 0) { // -1 //空文件返回-1
            view.setFlag("0");
        }
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
        fileManFileInfo.setTotalFilesNum(files.length);
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);



        for (int i = 0; i < files.length; i++) {
            try {
                deskName = FileUtil.uploadFile(files[i], userInfo.getUserName());
                orginname = files[i].getOriginalFilename();


                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1,2,3");
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
                filemanRight.setOpRight("1,2,3");
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
