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

import static com.cosun.cosunp.tool.StringUtil.afterString;
import static com.cosun.cosunp.tool.StringUtil.subAfterString;

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
    public void saveOrUpdateFilePrivilege(List<String> userList, Integer fileurlid, String privileflag, UserInfo info, String oprighter) {
        FilemanRight fr = null;
        DownloadView view = null;
        List<UserInfo> uis = null;
        FileManFileInfo fileManFileInfo = null;
        fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
        if (userList.size() > 1) {//存的是多个用户
                for (String user : userList) {
                    if (fr != null) {
                        fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                        view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(),fileManFileInfo.getExtInfo1(),fileManFileInfo.getOrderNum(),Integer.valueOf(user));
                        if(view!=null) {
                            if(privileflag==""){
                                fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                            }else {
                                fileUploadAndDownMapper.updateFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date());
                            }
                        } else{
                            if(privileflag!="") {
                                fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                            }
                        }
                    } else {
                        fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                        if(privileflag!="") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(user), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                    }
                }


        } else {//一个或者选择了公司所有人员
            if (userList.get(0) == "0") { //代表公司所有的人
                uis = fileUploadAndDownMapper.findAllUserInfo();
                for (UserInfo us : uis) {
                    fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                    view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(),fileManFileInfo.getExtInfo1(),fileManFileInfo.getOrderNum(),us.getuId());
                    if(view!=null) {
                        if(privileflag==""){
                            fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                        }else {
                            fileUploadAndDownMapper.updateFileRightPrivileg(us.getuId(), fileurlid, privileflag, info.getUserName(), new Date());
                        }
                    }else{
                        if(privileflag!="") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(us.getUserActor(), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                        }
                }
            } else {//代表只选择单个用户
                fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                if (fr != null) {
                    fileManFileInfo = fileUploadAndDownMapper.getFileManFileInfoByInfoId(fr.getFileInfoId());
                    view = fileUploadAndDownMapper.findFielRightFileByUidOrderNoSalorFileName(fr.getFileName(),fileManFileInfo.getExtInfo1(),fileManFileInfo.getOrderNum(),Integer.valueOf(userList.get(0)));
                    if(view!=null) {
                        if(privileflag==""){
                            fileUploadAndDownMapper.deleteFileRightPrivileg(view.getFileRightId());
                        }else {
                            fileUploadAndDownMapper.updateFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date());
                        }
                    }else{
                        if(privileflag!="") {
                            fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                        }
                    }
                } else {
                    fr = fileUploadAndDownMapper.findFileRightById(fileurlid);
                    if(privileflag!="") {
                        fileUploadAndDownMapper.saveFileRightPrivileg(Integer.valueOf(userList.get(0)), fileurlid, privileflag, info.getUserName(), new Date(), fr.getFileInfoId(), fr.getFileName());
                    }
                }
            }

        }

    }

    @Override
    public List<UserInfo> findAllUser() {
        return userInfoMapper.findAllUser();
    }

    @Override
    public List<DownloadView> findFileUrlDatabyOrderNoandSalorandUserName(DownloadView view) {
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
    public DownloadView findIsExistFilesforUpdate(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) {
        boolean isAllExistFile = true;//true代表文件以前全部存过
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) { //代表文件夹存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }
            for (MultipartFile file : fileArray) {
                if (strnames.contains(file.getOriginalFilename())) {//查看是不是每个文件服务器都有老文件
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
    public DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo, String engineer, String yearmoth, String salor, String orderNo) {
        boolean isAllNewFile = true; //全为新文件即为真
        String isExistFilesName = "";
        int exsitNum = 0;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        UserInfo ui = fileUploadAndDownMapper.getUserInfoByUid(Integer.valueOf(engineer));
        //根据业务员订单号设计师看有没有文件夹
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(ui.getUserName(),orderNo,salor );
        if (fileManFileInfo.size() > 0) {  //有文件夹
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }
            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(),"/"))) { //数据库里的URL有现在要上传的名字 不受理
                    isExistFilesName += file.getOriginalFilename() + "===";//取旧文件的名字
                    isAllNewFile = false;
                    exsitNum++;
                } else {
                    newFileArray.add(file);
                }
            }
            if (isAllNewFile) { //全为新文件
                view = addOldOrderNoNewFilesByFolder(view, newFileArray, ui, oldFileUrls.get(0).getLogur1(), fileManFileInfo, engineer, yearmoth, salor, orderNo);
                view.setFlag("1");
            } else {
                view.setFlag("-3");//代表有部分已存在的文件
                view.setIsExistNum(exsitNum);
                view.setExistFileMessage(isExistFilesName);//向页面回显文件信息
                return view;
            }

        } else {//如果没有文件夹,直接当成新文件全部存.
            view = this.addFilesDatabyFolder(view, fileArray, ui, engineer, yearmoth, salor, orderNo);
            view.setFlag("1");//代表全为新文件,且无文件夹,存储成功
        }

        return view;
    }

    @Transactional
    @Override
   public DownloadView findIsExistFilesFolderforUpdate(List<MultipartFile> fileArray,DownloadView view,UserInfo userInfo,String engineer,String yearmoth,String salor,String orderNo){
        boolean isAllExistFile = true;//true代表文件以前全部存过
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        UserInfo ui = fileUploadAndDownMapper.getUserInfoByUid(Integer.valueOf(engineer));
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(ui.getUserName(),orderNo,salor );;
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) { //代表文件夹存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }
            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(),"/"))) {//查看是不是每个文件服务器都有老文件
                    newFileArray.add(file);
                } else {
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += file.getOriginalFilename() + "===";
                }
            }
            if (isAllExistFile) {
                updateFilesDataFolder(fileManFileInfo, view, newFileArray, ui,engineer,yearmoth,salor,orderNo);
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


    @Transactional
    public  void updateFilesDataFolder(List<FileManFileInfo> fileManFileInfo,DownloadView view,List<MultipartFile> fileArray,UserInfo userInfo,String engineer,String yearmoth,String salor,String orderNo){
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
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(subAfterString(file.getOriginalFilename(),"/"), fileManFileInfo.get(0).getId());
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
    public DownloadView addFilesDatabyFolder(DownloadView view, List<MultipartFile> files, UserInfo userInfo, String engineer, String yearmoth, String salor, String orderNo) {
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
        fileManFileInfo.setExtInfo1(salor);
        fileManFileInfo.setOrderNum(orderNo);
        fileManFileInfo.setProjectName(view.getProjectName());
        fileManFileInfo.setTotalFilesNum(files.size());
        fileManFileInfo.setRemark(view.getRemark());
        fileManFileInfo.setFiledescribtion(view.getFiledescribtion());
        fileUploadAndDownMapper.addfileManFileDataByUpload(fileManFileInfo);


        for (int i = 0; i < files.size(); i++) {
            try {
                deskName = FileUtil.uploadFileFolder(files.get(i), engineer, yearmoth, salor, orderNo, randomNum);
                orginname = subAfterString(files.get(i).getOriginalFilename(), "/");

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
    public DownloadView addOldOrderNoNewFilesByFolder(DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo, String oldPath, List<FileManFileInfo> fileManFileInfos, String engineer, String yearmoth, String salor, String orderNo) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        String oldsPath = StringUtil.subMyString(oldPath, "\\");
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
            orginname = subAfterString(file.getOriginalFilename(), "/");
            deskName = oldsPath + orginname;
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
    public DownloadView findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) {
        boolean isAllNewFile = true; //全为新文件即为真
        String isExistFilesName = "";
        int exsitNum = 0;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        //根据业务员订单号设计师看有没有文件夹
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {  //有文件夹
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
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
                view = addOldOrderNoNewFiles(view, newFileArray, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
                view.setFlag("1");
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
    public void updateFilesData(List<FileManFileInfo> fileManFileInfo, DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) {
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
    public DownloadView addOldOrderNoNewFiles(DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo, String oldPath, List<FileManFileInfo> fileManFileInfos) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        String oldsPath = StringUtil.subMyString(oldPath, "\\");
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
    public List<DownloadView> findAllFileUrlByCondition(Integer uid,int currentPageTotalNum,int PageSize){
        return fileUploadAndDownMapper.findAllFileUrlByCondition(uid,currentPageTotalNum,PageSize);
    }

    @Override
    public List<String> findAllUrlByParamThree(String salor,Integer engineer,String orderno) {
        return fileUploadAndDownMapper.findAllUrlByParamThree(salor,engineer,orderno);
    }


    @Override
    public int findAllUploadFileCountByParaCondition(DownloadView view) {
        return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
    }

    @Override
    public List<DownloadView> findAllFilesByCondParam(DownloadView view) {
        return fileUploadAndDownMapper.findAllFilesByCondParam(view);
    }

    @Override
    public int findAllFilesByCondParamCount(DownloadView view) {
        return fileUploadAndDownMapper.findAllFilesByCondParamCount(view);
    }
}
