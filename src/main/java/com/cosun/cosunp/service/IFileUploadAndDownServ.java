package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;

import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:32
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:32
 */
public interface IFileUploadAndDownServ {

    void addFileDataByUpload(FilemanRight filemanRight, FilemanUrl filemanUrl, FileManFileInfo fileManFileInfo);

    List<DownloadView> findAllUploadFileByCondition(Integer uid,int currentPageTotalNum,int PageSize);
    List<DownloadView> findAllUploadFileByUserId(Integer uid);

    List<UserInfo> findAllUser();

    void saveOrUpdateFilePrivilege(Integer selectuser,Integer filesId,String privileflag);

    int findAllUploadFileCountByUserId(Integer uId);

    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view);

    int findAllUploadFileCountByParaCondition(DownloadView view);
}
