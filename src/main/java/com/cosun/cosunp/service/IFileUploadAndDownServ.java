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

    List<DownloadView> findAllUploadFileByCondition(Integer uid,int currentPageTotalNum,int PageSize) throws Exception;
    List<DownloadView> findAllUploadFileByUserId(Integer uid) throws  Exception;
    DownloadView addFilesData(DownloadView view,List<MultipartFile> fileArray,UserInfo userInfo) throws  Exception;

    List<UserInfo> findAllUser() throws  Exception;

    List<DownloadView> findAllFileUrlByCondition(Integer uid,int currentPageTotalNum,int PageSize) throws Exception;


    void saveOrUpdateFilePrivilege(List<String> userList,Integer filesId,String privileflag,UserInfo info,String oprighter) throws Exception;

    int findAllUploadFileCountByUserId(Integer uId) throws Exception;

    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) throws Exception;

    int findAllUploadFileCountByParaCondition(DownloadView view) throws Exception;
    DownloadView findIsExistFiles(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo) throws Exception;
    DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo) throws Exception;
    DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo) throws Exception;
    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) throws Exception;
    List<DownloadView> findAllFilesByCondParam(DownloadView view) throws Exception;
    int findAllFilesByCondParamCount(DownloadView view) throws Exception;
    List<String> findAllUrlByParamThree(String salor,Integer engineer,String orderno) throws  Exception;
}
