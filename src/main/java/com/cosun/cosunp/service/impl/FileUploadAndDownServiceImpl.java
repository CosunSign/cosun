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


}
