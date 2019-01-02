package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveOrUpdateFilePrivilege(Integer selectuser,Integer filesId,String privileflag){
        fileUploadAndDownMapper.saveOrUpdateFilePrivilege(selectuser,filesId,privileflag);
        fileUploadAndDownMapper.saveOrUpdateFileUrlPrivilege(filesId,privileflag);
    }

    public List<UserInfo> findAllUser(){
        return userInfoMapper.findAllUser();
    }

    @Transactional //添加事物
    public void addFileDataByUpload(FilemanRight filemanRight, FilemanUrl filemanUrl, FileManFileInfo fileManFile){
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFile);
        filemanUrl.setFileInfoId(fileManFile.getId());
        filemanRight.setFileInfoId(fileManFile.getId());
        fileUploadAndDownMapper.addfilemanUrlByUpload(filemanUrl);
        fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);

    }


    /**
     *@author:homey Wong
     *@Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByUserId(Integer uid) {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByUserId(uid);
        return list;
    }

    /**
      *@author:homey Wong
      *@Date: 2018.12.21
      */
    @Override
    public List<DownloadView> findAllUploadFileByCondition(Integer uid,int currentPageTotalNum,int PageSize) {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByCondition(uid,currentPageTotalNum,PageSize);
        return list;
    }

    /**
     * 功能描述:分页
     * @auther: homey Wong
     * @date: 2018/12/29 0029 上午 9:47
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public int findAllUploadFileCountByUserId(Integer uId) {
        return  fileUploadAndDownMapper.findAllUploadFileCountByUserId(uId);

    }

    @Override
    public List<DownloadView> findAllUploadFileByParaCondition(DownloadView view){
        return fileUploadAndDownMapper.findAllUploadFileByParaCondition(view);
    }

    @Override
    public int findAllUploadFileCountByParaCondition(DownloadView view){
        return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
    }
}
