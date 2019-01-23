package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.MathUtil;
import com.cosun.cosunp.tool.PinYinUtil;
import com.cosun.cosunp.tool.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cosun.cosunp.tool.StringUtil.*;

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
    public void saveOrUpdateFilePrivilege(List<String> userList, Integer fileurlid, String privileflag, UserInfo info, String oprighter) throws Exception {
        FilemanRight fr = null;
        DownloadView view = null;
        List<UserInfo> uis = null;
        FileManFileInfo fileManFileInfo = null;
        fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
        if (userList.size() > 1) {//存的是多个用户
            for (String user : userList) {
                if (fr != null) {
                    fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                    view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(), fileManFileInfo.getExtInfo1(), fileManFileInfo.getOrderNum(), Integer.valueOf(user));
                    if (view != null) {
                        if (privileflag == "") {
                            fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                        } else {
                            fileUploadAndDownMapper.updateFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date());
                        }
                    } else {
                        if (privileflag != "") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                    }
                } else {
                    fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                    if (privileflag != "") {
                        fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                    }
                }
            }


        } else {//一个或者选择了公司所有人员
            if (userList.get(0) == "0") { //代表公司所有的人
                uis = fileUploadAndDownMapper.findAllUserInfo();
                for (UserInfo us : uis) {
                    fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                    view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(), fileManFileInfo.getExtInfo1(), fileManFileInfo.getOrderNum(), us.getuId());
                    if (view != null) {
                        if (privileflag == "") {
                            fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                        } else {
                            fileUploadAndDownMapper.updateFileRightPrivileg(us.getuId(), fileurlid, privileflag, info.getUserName(), new Date());
                        }
                    } else {
                        if (privileflag != "") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(us.getUserActor(), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                    }
                }
            } else {//代表只选择单个用户
                fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                if (fr != null) {
                    fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                    view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(), fileManFileInfo.getExtInfo1(), fileManFileInfo.getOrderNum(), Integer.valueOf(userList.get(0)));
                    if (view != null) {
                        if (privileflag == "") {
                            fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                        } else {
                            fileUploadAndDownMapper.updateFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date());
                        }
                    } else {
                        if (privileflag != "") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                    }
                } else {
                    fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                    if (privileflag != "") {
                        fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                    }
                }
            }

        }

    }

    @Override
    public List<UserInfo> findAllUser() throws Exception {
        return userInfoMapper.findAllUser();
    }

    @Override
    public List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findFileUrlDatabyOrderNoandSalorandUserName(view.getUserName(), view.getSalor(), view.getOrderNo());
    }


//    /**
//     * 功能描述:上传文件夹
//     * @auther: homey Wong
//     * @date: 2019/1/16 0016 上午 11:45
//     * @param:
//     * @return:
//     * @describtion
//     */
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

    /**
     * 功能描述:文件修改功能,即更新覆盖
     *
     * @auther: homey Wong
     * @date: 2019/1/11 0011 上午 9:37
     * @param:
     * @return:
     * @describtion
     */
    @Override
    @Transactional
    public DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllExistFile = true;//true代表文件以前全部存过
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<String> centerUrls = new ArrayList<String>();
        if (fileManFileInfo.size() > 0) { //代表文件夹存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "\\", 6);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }

            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/"))) {//查看文件夹下的文件是否完全一样
                    newFileArray.add(file);
                } else {
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += file.getOriginalFilename() + "===";
                }
            }
            if (isAllExistFile) {
                updateFilesData(fileManFileInfo, view, newFileArray, userInfo);
                view.setFlag("-18");//代表文件全部更新成功
            } else {
                view.setIsExistNum(isNoExsitFileNum);
                view.setFlag("-12");//代表为新文件,
                view.setNoExsitFileMessage(noExistFileNames);//返回信息,告知哪些是新文件
                return view;
            }

        } else {//代表文件夹不存在,直接不受理
            view.setFlag("-11");//代表文件夹不存在,去上传页面
            return view;
        }

        return view;
    }


    /**
     * 功能描述:文件夹上传功能 根据上传的文件夹
     *
     * @auther: homey Wong
     * @date: 2019/1/16 0016 下午 2:40
     * @param:
     * @return:
     * @describtion
     */
    public DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllNewFile = true; //全为新文件即为真
        String isExistFilesName = "";
        int exsitNum = 0;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        //根据业务员订单号设计师看有没有文件夹
        List<String> strurl = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {  //看四层基本层次结构存不存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strurl.add(fu.getOrginName());
            }
            for (MultipartFile file : fileArray) {
                if (strurl.contains(subAfterString(file.getOriginalFilename(), "/"))) { //数据库里的URL有现在要上传的名字与路径 不受理
                    isExistFilesName += file.getOriginalFilename() + "===";//取旧文件上传过来含带文件夹包的的名字
                    isAllNewFile = false;
                    exsitNum++;
                } else {
                    newFileArray.add(file);
                }
            }
            if (isAllNewFile) { //全为新文件
                view = addOldOrderNoNewFilesByFolder(view, newFileArray, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
                view.setFlag("1");
            } else {
                view.setFlag("-3");//代表有部分已存在的文件
                view.setIsExistNum(exsitNum);
                view.setExistFileMessage(isExistFilesName);//向页面回显文件信息
                return view;
            }

        } else {//如果没有文件夹,直接当成新文件全部存.
            view = this.addFilesDatabyFolder(view, fileArray, userInfo);
            view.setFlag("1");//代表全为新文件,且无文件夹,存储成功
        }

        return view;
    }

    @Transactional
    @Override
    public DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllExistFile = true;//true代表文件以前全部存过
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        ;
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> centerUrls = new ArrayList<String>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) { //代表前四级文件夹存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "\\", 6);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }


            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/")) && centerUrls.contains(file.getOriginalFilename().replaceAll("/", "\\\\"))) {//查看文件夹下的文件是否完全一样
                    newFileArray.add(file);
                } else {
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += file.getOriginalFilename() + "===";
                }
            }

            if (isAllExistFile) {//代表文件夹与文件全是以前存在过的
                updateFilesDataFolder(fileManFileInfo, view, newFileArray, userInfo);
                view.setFlag("-18");//代表文件全部更新成功
            } else {
                view.setIsExistNum(isNoExsitFileNum);
                view.setFlag("-12");//代表为新文件,
                view.setNoExsitFileMessage(noExistFileNames);//返回信息,告知哪些是新文件
                return view;
            }

        } else {//代表前四级文件夹不存在,直接不受理
            view.setFlag("-11");//代表文件夹不存在,去上传页面
            return view;
        }

        return view;
    }


    @Transactional
    public void updateFilesDataFolder(List<FileManFileInfo> fileManFileInfo, DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) throws Exception {
        FileManFileInfo ffi = null;
        FilemanUrl filemanUrl;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(view.getUserName());
            //修改头信息
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (MultipartFile file : fileArray) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(subAfterString(file.getOriginalFilename(), "/"), fileManFileInfo.get(0).getId());
                if (filemanUrl != null) {
                    FileUtil.modifyUpdateFileFolderByUrl(file, userInfo, view, filemanUrl.getLogur1());//覆盖文件操作
                    //取老文件信息
                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum() + 1);
                    filemanUrl.setUpdateuser(userInfo.getUserName());
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(new Date(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId(), filemanUrl.getUpdateuser());
                }
            }
        }

    }

    @Transactional
    public DownloadView addFilesDatabyFolder(DownloadView view, List<MultipartFile> files, UserInfo userInfo) throws Exception {
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
        String name1 = null;
        //存文件的人与文件信息
        fileManFileInfo = new FileManFileInfo();
        fileManFileInfo.setCreateTime(new Date());
        fileManFileInfo.setCreateUser(userInfo.getUserName());
        fileManFileInfo.setUserName(userInfo.getUserName());
        fileManFileInfo.setuId(userInfo.getuId());
        fileManFileInfo.setExtInfo1(view.getSalor());
        fileManFileInfo.setOrderNum(view.getOrderNo());
        fileManFileInfo.setProjectName(view.getProjectName());
        fileManFileInfo.setTotalFilesNum(files.size());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);


        for (int i = 0; i < files.size(); i++) {
            try {
                deskName = FileUtil.uploadFileFolder(files.get(i), view, randomNum);
                //  name1 = files.get(i).getOriginalFilename().replaceAll("/", "\\\\");
                orginname = subAfterString(deskName, "\\");
                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setuId(view.getuId());
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1");
                filemanUrl.setLogur1(deskName);
                filemanUrl.setUpTime(new Date());
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


    //上传的是文件夹,根据以前的路径存文件
    @Transactional
    public DownloadView addOldOrderNoNewFilesByFolder(DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo, String oldPath, List<FileManFileInfo> fileManFileInfos) throws Exception {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        Integer pointindex = StringUtils.ordinalIndexOf(oldPath, "\\", 6);
        String oldsPath = oldPath.substring(0, pointindex + 1);
        String orginname = "";//原始文件名
        String deskName = "";//程序自定义文件名
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        String name1 = null;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + fileArray.size());
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setUpdateTime(new Date());

        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId());
        for (MultipartFile file : fileArray) {
            //  name1 = file.getOriginalFilename().replaceAll("/", "\\\\");
            deskName = oldsPath + file.getOriginalFilename();
            orginname = subAfterString(deskName, "\\");
            FileUtil.uploadFileFolderByUrl(file, userInfo, view, oldsPath);
            //存储文件路径
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(orginname);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(deskName);
            filemanUrl.setuId(view.getuId());
            filemanUrl.setUpTime(new Date());
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

            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }
        //为空 代表文件服务器中没有，但所填写的订单信息与数据库不匹配，此时中止行为，反回界面提示

        return view;
    }


    //以下功能为文件上传功能,只对新的文件进行存储
    @Override
    @Transactional
    public DownloadView findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllNewFile = true; //全为新文件即为真
        String isExistFilesName = "";
        int exsitNum = 0;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        //根据业务员订单号设计师看有没有文件夹
        List<String> strnames = new ArrayList<String>();
        List<String> urlStr = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        String pointFolder = null;
        boolean f = true;
        if (fileManFileInfo.size() > 0) {  //有文件夹
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                if (view.getSaveFolderName() != "" && f) {
                    if (fu.getLogur1().contains(view.getSaveFolderName())) {
                        pointFolder = fu.getLogur1();
                        f = false;
                    }
                }
                strnames.add(fu.getOrginName());
                urlStr.add(fu.getLogur1());
            }
            for (MultipartFile file : fileArray) {
                if (strnames.contains(file.getOriginalFilename())) { //数据库里的URL有现在要上传的名字 不受理
                    isExistFilesName += file.getOriginalFilename() + "===";//取旧文件的名字
                    isAllNewFile = false;
                    exsitNum++;
                } else {
                    newFileArray.add(file);
                }
            }
            if (isAllNewFile) { //全为新文件
                if (pointFolder != null && pointFolder.length() > 0&&!view.getSaveFolderName().equals("")) {//代表为用户指定目录
                    int lia = pointFolder.indexOf(view.getSaveFolderName());
                    pointFolder = pointFolder.substring(0, lia + view.getSaveFolderName().length() + 1);
                    view = addOFilesByPointFile(view, newFileArray, userInfo, pointFolder, fileManFileInfo);
                    view.setFlag("1");
                } else {
                    view = addOldOrderNoNewFiles(view, newFileArray, userInfo, urlStr, fileManFileInfo);
                    view.setFlag("1");
                }

            } else {
                view.setFlag("-3");//代表有部分已存在的文件
                view.setIsExistNum(exsitNum);
                view.setExistFileMessage(isExistFilesName);//向页面回显文件信息
                return view;
            }

        } else {//如果没有文件夹,直接当成新文件全部存.
            view = this.addFilesData(view, fileArray, userInfo);
            view.setFlag("1");//代表全为新文件,且无文件夹,存储成功
        }

        return view;
    }

    //以下功能为将文件上传到制定目录下胡功能,只对新的文件进行存储
//    @Override
//    @Transactional
//    public bool findIsExistFilesForSpecifiedFolder(List<MultipartFile> fileArray, String stSpecifiedFolder) throws Exception {
//        boolean isAllNewFile = true; //全为新文件即为真
//        String isExistFilesName = "";
//        int exsitNum = 0;
//        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
//        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
//        //根据业务员订单号设计师看有没有文件夹
//        List<String> strnames = new ArrayList<String>();
//        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
//        if (fileManFileInfo.size() > 0) {  //有文件夹
//            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
//            for (FilemanUrl fu : oldFileUrls) {
//                strnames.add(fu.getOrginName());
//            }
//            for (MultipartFile file : fileArray) {
//                if (strnames.contains(file.getOriginalFilename())) { //数据库里的URL有现在要上传的名字 不受理
//                    isExistFilesName += file.getOriginalFilename() + "===";//取旧文件的名字
//                    isAllNewFile = false;
//                    exsitNum++;
//                } else {
//                    newFileArray.add(file);
//                }
//            }
//            if (isAllNewFile) { //全为新文件
//                view = addOldOrderNoNewFiles(view, newFileArray, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
//                view.setFlag("1");
//            } else {
//                view.setFlag("-3");//代表有部分已存在的文件
//                view.setIsExistNum(exsitNum);
//                view.setExistFileMessage(isExistFilesName);//向页面回显文件信息
//                return view;
//            }
//
//        } else {//如果没有文件夹,直接当成新文件全部存.
//            view = this.addFilesData(view, fileArray, userInfo);
//            view.setFlag("1");//代表全为新文件,且无文件夹,存储成功
//        }
//
//        return view;
//    }


    /**
     * 功能描述:文件更新处理
     *
     * @auther: homey Wong
     * @date: 2019/1/11 0011 上午 10:11
     * @param:
     * @return:
     * @describtion
     */
    @Transactional
    public void updateFilesData(List<FileManFileInfo> fileManFileInfo, DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) throws Exception {
        FileManFileInfo ffi = null;
        FilemanUrl filemanUrl;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(view.getUserName());
            //修改头信息
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (MultipartFile file : fileArray) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(file.getOriginalFilename(), fileManFileInfo.get(0).getId());
                if (filemanUrl != null) {
                    FileUtil.modifyUpdateFileByUrl(file, userInfo, view, filemanUrl.getLogur1());//覆盖文件操作
                    //取老文件信息


                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum() + 1);
                    filemanUrl.setUpdateuser(userInfo.getUserName());
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(new Date(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId(), filemanUrl.getUpdateuser());
                }
            }
        }

    }

    @Transactional
    public DownloadView addOFilesByPointFile(DownloadView view, List<MultipartFile> fileArray,
                                             UserInfo userInfo, String pointpath,
                                             List<FileManFileInfo> fileManFileInfos) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\按客户指定目录保存文件

        String orginname = "";//原始文件名
        String deskName = "";//程序自定义文件名
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + fileArray.size());
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setUpdateTime(new Date());
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId());
        for (MultipartFile file : fileArray) {
            orginname = file.getOriginalFilename();
            deskName = pointpath + orginname;
            FileUtil.uploadFileByUrl(file, userInfo, view, pointpath);
            //存储文件路径
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(orginname);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(deskName);
            filemanUrl.setuId(view.getuId());
            filemanUrl.setUpTime(new Date());
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

            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }
        return view;
    }

    @Transactional
    public DownloadView addOldOrderNoNewFiles(DownloadView view, List<MultipartFile> fileArray,
                                              UserInfo userInfo, List<String> oldPaths,
                                              List<FileManFileInfo> fileManFileInfos) throws Exception {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        String oldPath = null;
        Integer pointindex = null;
        String oldsPath = null;
        oldPath = oldPaths.get(0);
        pointindex = StringUtils.ordinalIndexOf(oldPath, "\\", 6);
        oldsPath = oldPath.substring(0, pointindex + 1);
        String orginname = "";//原始文件名
        String deskName = "";//程序自定义文件名
        List<FilemanUrl> filemanUrls = new ArrayList<FilemanUrl>();
        List<FilemanRight> filemanRights = new ArrayList<FilemanRight>();
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        FileManFileInfo fileManFileInfo = fileManFileInfos.get(0);
        fileManFileInfo.setTotalFilesNum(fileManFileInfo.getTotalFilesNum() + fileArray.size());
        fileManFileInfo.setUpdateCount(fileManFileInfo.getUpdateCount() + 1);
        fileManFileInfo.setUpdateTime(new Date());
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId());
        for (MultipartFile file : fileArray) {
            orginname = file.getOriginalFilename();
            deskName = oldsPath + orginname;
            FileUtil.uploadFileByUrl(file, userInfo, view, oldsPath);
            //存储文件路径
            filemanUrl = new FilemanUrl();
            filemanUrl.setOrginName(orginname);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setOpRight("1");
            filemanUrl.setLogur1(deskName);
            filemanUrl.setuId(view.getuId());
            filemanUrl.setUpTime(new Date());
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

            filemanRights.add(filemanRight);
            fileUploadAndDownMapper.addFilemanRightDataByUpload(filemanRight);
        }
        //为空 代表文件服务器中没有，但所填写的订单信息与数据库不匹配，此时中止行为，反回界面提示


        return view;

    }


    @Transactional
    @Override
    public DownloadView addFilesData(DownloadView view, List<MultipartFile> files, UserInfo userInfo) throws Exception {
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
                deskName = FileUtil.uploadFile(files.get(i), userInfo, view, randomNum);
                orginname = files.get(i).getOriginalFilename();


                //存储文件路径
                filemanUrl = new FilemanUrl();
                filemanUrl.setOrginName(orginname);
                filemanUrl.setuId(view.getuId());
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1");
                filemanUrl.setLogur1(deskName);
                filemanUrl.setUpTime(new Date());
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
    public List<DownloadView> findAllUploadFileByUserId(Integer uid) throws Exception {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByUserId(uid);
        return list;
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21
     */
    @Override
    public List<DownloadView> findAllUploadFileByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception {
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
    public int findAllUploadFileCountByUserId(Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileCountByUserId(uId);

    }

    @Override
    public List<DownloadView> findAllUploadFileByParaCondition(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileByParaCondition(view);
    }

    @Override
    public List<DownloadView> findAllFileUrlByCondition(Integer uid, int currentPageTotalNum, int PageSize) throws Exception {
        return fileUploadAndDownMapper.findAllFileUrlByCondition(uid, currentPageTotalNum, PageSize);
    }

    @Override
    public List<String> findAllUrlByParamThree(String salor, Integer engineer, String orderno) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByParamThree(salor, engineer, orderno);
    }


    @Override
    public int findAllUploadFileCountByParaCondition(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
    }

    @Override
    public List<DownloadView> findAllFilesByCondParam(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllFilesByCondParam(view);
    }

    @Override
    public int findAllFilesByCondParamCount(DownloadView view) throws Exception {
        return fileUploadAndDownMapper.findAllFilesByCondParamCount(view);
    }

    /**
     * 功能描述:文件上传查看名是否有重复
     *
     * @auther: homey Wong
     * @date: 2019/1/18 0018 下午 4:31
     * @param:
     * @return:
     * @describtion
     */
    @Override
    public boolean checkFileisSame(DownloadView view, UserInfo userInfo, List<MultipartFile> fileArray) {
        //第一步，查看上传文件们的是否有重复
        List<String> originNames = new ArrayList<String>();
        boolean isSameFileUpload = false;//代表没有
        for (MultipartFile file : fileArray) {
            if (originNames.contains(file.getOriginalFilename())) {
                isSameFileUpload = true;//有，即刻返回
                return isSameFileUpload;
            } else {
                originNames.add(file.getOriginalFilename());
            }

        }
        //第二步，查看数据库里与现在要上传的文件们是否有重复

        List<String> urls = fileUploadAndDownMapper.findAllFileUrlNameByCondition(userInfo.getuId(), view.getSalor(), view.getOrderNo());

        for (String s : originNames) {
            if (urls.contains(s)) {
                isSameFileUpload = true;//有重复,即刻返回
            }
        }
        return isSameFileUpload;

    }

    /**
     * 功能描述:查看要上传的文件夹有没重名和文件夹下的文件名有无重名
     *
     * @auther: homey Wong
     * @date: 2019/1/18 0018 下午 4:45
     * @param:
     * @return:
     * @describtion
     */

    @Override
    public int isSameFolderNameorFileNameMethod(UserInfo userInfo, DownloadView view, List<MultipartFile> fileArray) {
        int isSameFileUploadFolderName = 0;//代表没有
        //首先第一步

        //第一步,查看上传文件夹下的文件名是否相同
        List<String> originFNames = new ArrayList<String>();
        //boolean isSameFileUploadFolderName = false;//代表没有

        for (MultipartFile file : fileArray) {
            if (originFNames.contains(subAfterString(file.getOriginalFilename(), "/"))) {
                //isSameFileUploadFolderName = true;//有，即刻返回
                isSameFileUploadFolderName = -1;
                return isSameFileUploadFolderName;//表示上传文件夹中就有相同文件名字存在
            } else {
                originFNames.add(subAfterString(file.getOriginalFilename(), "/"));
            }
        }
        //判断根据当前输入信息所创建的文件夹之前不存在，全部为新时，只需做第一步判断之后就可以全部开始保存，不用做后续检查

        //第二步,上传的文件夹下的文件名与数据库的文件名是否重复
        List<String> urlss = fileUploadAndDownMapper.findAllFileUrlNameByCondition(userInfo.getuId(), view.getSalor(), view.getOrderNo());
        for (String str : originFNames) {
            if (urlss.contains(str)) {
                isSameFileUploadFolderName = -2;//有重复,即刻返回
                return isSameFileUploadFolderName;
            }
        }

        //第三步,查看上传的文件夹下的文件夹名是否有重复//自比较？？？？？？可以优化成不断插入子目录，循环判断
        List<String[]> foldernameLists = new ArrayList<String[]>();
        String afterstr = null;
        String[] splitArray = null;
        for (int i = 0; i < fileArray.size(); i++) {
            String centerurl = StringUtil.subMyString(fileArray.get(i).getOriginalFilename(), "/");
            if (centerurl.contains("/")) {
                afterstr = centerurl.replaceAll("/", "\\\\");
                splitArray = afterstr.split("\\\\");
                foldernameLists.add(splitArray);
            }
        }
        //领导写的代码，重点
//        String[] centerfolderone03w = null;//取数据用
//        String[] centerfolderone13w = null;//存数据用
//        int difFolderNameCnt=0;
//        for (int i = 0; i < foldernameLists.size(); i++) {
//            centerfolderone03w = foldernameLists.get(i);
//            for (int j = 0; j < centerfolderone03w.length; j++) {
//                boolean isFound = false;
//                for (int k=0;k<centerfolderone13w.length;k++) {
//                    if (centerfolderone13w[k]==centerfolderone03w[j]) {
//                        isFound=true;
//                    }
//                }
//                if (isFound==false) {
//                    centerfolderone13w[difFolderNameCnt] = centerfolderone03w[j];
//                    difFolderNameCnt++;
//                }
//            }
//        }

        //自单个路径比较
        String[] aa = null;
        List<String> singleFolder = new ArrayList<String>();
        for (int a = 0; a < foldernameLists.size(); a++) {
            aa = foldernameLists.get(a);
            for (int b = 0; b < aa.length; b++) {
                if (singleFolder.contains(aa[b])) {
                    isSameFileUploadFolderName = -6;
                    return isSameFileUploadFolderName;
                }
                //进入里面后首先第一步,文件夹名不能与业务员名,订单名,设计师名不能出现与当前的日期如201901
                String nowDate = formateString(new Date());
                if (singleFolder.equals(nowDate) || singleFolder.equals(view.getOrderNo()) ||
                        singleFolder.equals(view.getSalor()) || singleFolder.equals(view.getuId())) {
                    isSameFileUploadFolderName = -8;
                    return isSameFileUploadFolderName;
                }
                singleFolder.add(aa[b]);
            }
            singleFolder.clear();
        }

        //以下为文件夹交叉排除重复
        String[] centerfolderone1 = null;
        String[] centerfoldertwo2 = null;
        //查找数据文件夹与上传过来的文件夹有没有重名
        for (int i = 0; i < foldernameLists.size(); i++) {
            centerfolderone1 = foldernameLists.get(i);
            for (int j = 1; j < foldernameLists.size(); j++) {
                centerfoldertwo2 = foldernameLists.get(j);
                for (int k = 0; k < centerfolderone1.length; k++) {
                    for (int f = 0; f < centerfoldertwo2.length; f++) {
                        //判断同一层次的文件夹是否相等,还要判断如果前面所有的文件夹层次也一模一样,即可视为一个文件夹,否则重命名
                        if (k == f && centerfolderone1[k].equals(centerfoldertwo2[f])) {
                            for (int o = 0; o <= k; o++) {
                                if (!centerfolderone1[o].equals(centerfoldertwo2[o])) {
                                    isSameFileUploadFolderName = -5;//代表同一层次是一样,但往前追溯有些层次不一样,视为重命名文件夹
                                    return isSameFileUploadFolderName;
                                }
                            }
                        } else {
                            if (centerfolderone1[k].equals(centerfoldertwo2[f])) {
                                {//如果不是同一层次的文件夹名字一样.即视重复
                                    isSameFileUploadFolderName = -5;//有重复,即刻返回
                                    return isSameFileUploadFolderName;

                                }
                            }
                        }
                    }
                }
            }
        }


        //第四步,查看上传的文件夹名与数据库的文件夹名是否重复
        List<String> urls = fileUploadAndDownMapper.findAllFileUrlLogursByOrderNoandSalorUserName(userInfo.getuId(), view.getSalor(), view.getOrderNo());
        List<String[]> mysqlcenterurls = new ArrayList<String[]>();
        Integer pointindex = null;
        String olderCenterstr = null;
        String centerstr = null;
        for (String urladdr : urls) {
            pointindex = StringUtils.ordinalIndexOf(urladdr, "\\", 6);
            olderCenterstr = urladdr.substring(0, pointindex + 1);
            centerstr = StringUtil.subMyString(urladdr, "\\");
            if (centerstr.contains("\\")) {
                mysqlcenterurls.add(centerstr.split("\\\\"));
            }
        }


        String[] centerfolderone = null;
        String[] centerfoldertwo = null;
        mysqlcenterurls.addAll(foldernameLists);//集合为数据库的文件夹名与上传过来的文件夹名
        //查找数据文件夹与上传过来的文件夹有没有重名
        for (int i = 0; i < mysqlcenterurls.size(); i++) {
            centerfolderone = mysqlcenterurls.get(i);
            for (int j = 1; j < mysqlcenterurls.size(); j++) {
                centerfoldertwo = mysqlcenterurls.get(j);
                for (int k = 0; k < centerfolderone.length; k++) {
                    for (int f = 0; f < centerfoldertwo.length; f++) {
                        //判断同一层次的文件夹是否相等,还要判断如果前面所有的文件夹层次也一模一样,即可视为一个文件夹,否则重命名
                        if (k == f && centerfolderone[k].equals(centerfoldertwo[f])) {
                            for (int o = 0; o <= k; o++) {
                                if (!centerfolderone[o].equals(centerfoldertwo[o])) {
                                    isSameFileUploadFolderName = -5;//代表同一层次是一样,但往前追溯有些层次不一样,视为重命名文件夹
                                    return isSameFileUploadFolderName;
                                }
                            }
                        } else {
                            if (centerfolderone[k].equals(centerfoldertwo[f])) {
                                {//如果不是同一层次的文件夹名字一样.即视重复
                                    isSameFileUploadFolderName = -5;//有重复,即刻返回
                                    return isSameFileUploadFolderName;

                                }
                            }
                        }
                    }
                }
            }

        }
        return isSameFileUploadFolderName;

    }


    public List<String> findAllOrderNum() throws Exception{
        return fileUploadAndDownMapper.findAllOrderNum();
    }

    public List<String> findAllUrlByParamManyOrNo(DownloadView view) throws Exception{
        return  fileUploadAndDownMapper.findAllUrlByParamManyOrNo(view);
    }

}
