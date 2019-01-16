package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:32
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:32
 */
public interface IFileUploadAndDownServ {

    List<DownloadView> findAllUploadFileByCondition(Integer uid,int currentPageTotalNum,int PageSize);
    List<DownloadView> findAllUploadFileByUserId(Integer uid);
    DownloadView addFilesData(DownloadView view,List<MultipartFile> fileArray,UserInfo userInfo);

    List<UserInfo> findAllUser();


    void saveOrUpdateFilePrivilege(List<String> userList,Integer filesId,String privileflag,UserInfo info,String oprighter);

    int findAllUploadFileCountByUserId(Integer uId);

    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view);

    int findAllUploadFileCountByParaCondition(DownloadView view);
    DownloadView findIsExistFiles(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo);
    DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo,String engineer,String yearmoth,String salor,String orderNo);
    DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo,String engineer,String yearmoth,String salor,String orderNo);

    DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo);
    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view);
    List<DownloadView> findAllFilesByCondParam(DownloadView view);
    int findAllFilesByCondParamCount(DownloadView view);
}
