package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/20 0020 下午 6:32
 * @Modified By:
 * @Modified-date:2018/12/20 0020 下午 6:32
 */
public interface IFileUploadAndDownServ {

    boolean checkFileisSame(DownloadView view, UserInfo userInfo, List<MultipartFile> fileArray) throws Exception;

    List<DownloadView> findAllUploadFileByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception;

    List<DownloadView> findAllUrlByParamThreeNew2(DownloadView view) throws Exception;
    DownloadView findMessageByOrderNo(String orderNo) throws Exception;

    boolean checkFileUpdateRight(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    List<DownloadView> findAllUploadFileByUserId(Integer uid) throws Exception;

    DownloadView addFilesData(DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) throws Exception;

    int isSameFolderNameorFileNameMethod(UserInfo userInfo, DownloadView view, List<MultipartFile> fileArray) throws Exception;

    List<UserInfo> findAllUser() throws Exception;

    List<DownloadView> findAllFileUrlByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception;

    void saveOrUpdateFilePrivilege(List<DownloadView> views,List<String> privilegeusers,UserInfo info) throws Exception;

    int findAllUploadFileCountByUserId(Integer uId) throws Exception;

    List<FilemanUrl> findAllUrlByOrderNo(String orderNo) throws Exception;

    FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUid(Integer urlId,Integer fileInfoId,Integer uId) throws Exception;

    List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) throws Exception;

    List<String> findAllOrderNum(int currentPageTotalNum,int pageSize) throws Exception;

    int findAllOrderNumCount() throws Exception;

    List<String> findAllUrlByOrderNo2(String orderNo) throws Exception;

    int findAllUploadFileCountByParaCondition(DownloadView view) throws Exception;

    DownloadView findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception;

    boolean isFolderNameForEngDateOrderNoSalor(List<MultipartFile> files) throws Exception;

    List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) throws Exception;

    List<DownloadView> findAllUrlByOrderNoAndUid(String orderNo,Integer uId) throws Exception;

    List<DownloadView> findAllFilesByCondParam(DownloadView view) throws Exception;

    int findAllFilesByCondParamCount(DownloadView view) throws Exception;

    DownloadView findOrderNobyOrderNo(String orderNo) throws Exception;

    List<String> findAllUrlByParamThree(String salor, Integer engineer, String orderno) throws Exception;

    int findAllUrlByParamThreeNew2Count(DownloadView view) throws Exception;

    List<String> findAllUrlByParamThreeNew(DownloadView view) throws Exception;

    List<DownloadView> findAllUrlByParamManyOrNo(DownloadView view) throws Exception;

    int findAllUrlByParamManyOrNoCount(DownloadView view) throws Exception;

}
