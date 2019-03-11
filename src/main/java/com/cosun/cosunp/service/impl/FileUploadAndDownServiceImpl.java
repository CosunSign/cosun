package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FileUploadAndDownMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.singleton.ProgressSingleton;
import com.cosun.cosunp.tool.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.*;

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
    public void saveOrUpdateFilePrivilege(List<DownloadView> views, List<String> privilegeusers, UserInfo in) throws Exception {
        DownloadView view = null;
        List<UserInfo> uis = null;
        FileManFileInfo info = null;
        FilemanRight right = null;
        FilemanUrl url = null;
        List<FilemanUrl> urls = null;
        for (DownloadView vi : views) {
            info = fileUploadAndDownMapper.getFileInfoByOrderNo(vi.getOrderNo());
            for (String operrighter : privilegeusers) {
                if (vi.getFolderOrFileName().contains(".")) {//代表是文件名
                    right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileNameAndUid(info.getId(), vi.getFolderOrFileName(), Integer.valueOf(operrighter));
                    //如果RIGHT为空,代表是RIGHT表中无数据,需要新增一条数据
                    if (right == null) {
                        //url = fileUploadAndDownMapper.getFileManUrlByFileInfoIdandFileName(right.getId(),vi.getFolderOrFileName());
                        if (vi.getOpRight().trim().length() > 0) {
                            right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileNameAndUid2(info.getId(), vi.getFolderOrFileName());
                            right.setOpRight(vi.getOpRight());
                            right.setuId(Integer.valueOf(operrighter));
                            fileUploadAndDownMapper.updateFileRight0OprightById(right.getId());
                            fileUploadAndDownMapper.saveFileManRightBySampleRightBean(right);
                        }
                    } else {//不为空 判断权限值与现权限值是否相同,相同不理会,不同,update操作\
                        if (vi.getOpRight() == null || vi.getOpRight() == "") {
                            fileUploadAndDownMapper.updateFileRightNullOprightById(right.getId());
                            fileUploadAndDownMapper.deleteFileRightPrivileg(right.getId());
                        } else {
                            if (vi.getOpRight() != "" && !right.getOpRight().equals(vi.getOpRight())) {
                                right.setOpRight(vi.getOpRight());
                                right.setUpdateUser(in.getUserName());
                                right.setUpdateTime(new Date());
                                fileUploadAndDownMapper.upDateFileRightOprightById(right.getId(), right.getOpRight(), right.getUpdateUser(), right.getUpdateTime());
                            }
                        }

                    }
                } else {//代表是文件夹
                    urls = fileUploadAndDownMapper.getFileManUrlByFileInfoId(info.getId());
                    for (FilemanUrl u : urls) {
                        if (u.getLogur1().indexOf("/" + vi.getFolderOrFileName() + "/") > -1) {//查询地址中有无选中的文件夹
                            right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileRightIdandUid(info.getId(), u.getId(), Integer.valueOf(operrighter));
                            if (right == null) {
                                if (vi.getOpRight().trim().length() > 0) {
                                    right = fileUploadAndDownMapper.getFileRightByFileInfoIdAndFileUrlId(info.getId(), u.getId());
                                    right.setOpRight(vi.getOpRight());
                                    right.setuId(Integer.valueOf(operrighter));
                                    fileUploadAndDownMapper.updateFileRight0OprightById(right.getId());
                                    fileUploadAndDownMapper.saveFileManRightBySampleRightBean(right);
                                }
                            } else {//不为空 判断权限值与现权限值是否相同,相同不理会,不同,update操作\
                                if (vi.getOpRight() == null || vi.getOpRight() == "") {
                                    fileUploadAndDownMapper.updateFileRightNullOprightById(right.getId());
                                    fileUploadAndDownMapper.deleteFileRightPrivileg(right.getId());
                                } else {
                                    if (vi.getOpRight() != "" && !right.getOpRight().equals(vi.getOpRight())) {
                                        right.setOpRight(vi.getOpRight());
                                        right.setUpdateUser(in.getUserName());
                                        right.setUpdateTime(new Date());
                                        fileUploadAndDownMapper.upDateFileRightOprightById(right.getId(), right.getOpRight(), right.getUpdateUser(), right.getUpdateTime());
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public List<UserInfo> findAllUser() throws Exception {
        return userInfoMapper.findAllUser();
    }

    @Override
    @Transactional
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
     * 功能描述:
     *
     * @auther: homey Wong
     * @date: 2019/2/22 0022 下午 7:34
     * @param:
     * @return:
     * @describtion
     */
    @Transactional
    public List<DownloadView> findAllUrlByOrderNoAndUid(String orderNo, Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllUrlByOrderNoAndUid(orderNo, uId);
    }


    @Transactional
    @Override
    public String checkIsExistFilesforUpdate(String pathName, DownloadView view, UserInfo info) throws Exception {
        String[] names = null;
        if (pathName.contains(",")) {
            names = pathName.split(",");
        } else {
            names[0] = pathName;
        }
        String returnMessage = "OK";
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
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }

            for (int i = 0; i < names.length; i++) {
                if (!strnames.contains(subAfterString(names[i], "/"))) {//查看文件夹下的文件是否完全一样
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += subAfterString(names[i], "/") + "===";
                }
            }
            if (!isAllExistFile) {
               returnMessage = "您本次要更新的文件里,有部分文件系统中不存在,共有"+isNoExsitFileNum+"个，分别是:"+noExistFileNames;
               return returnMessage;
            }

        } else {//代表文件夹不存在,直接不受理
           returnMessage = "您本次要更新的为新订单，请去上传中心";
        }

        return returnMessage;
    }


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
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }

            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/"))) {//查看文件夹下的文件是否完全一样
                    newFileArray.add(file);
                }
            }
            if (isAllExistFile) {
                updateFilesData(fileManFileInfo, view, newFileArray, userInfo);
                view.setFlag("-18");//代表文件全部更新成功
            }

        } else {//代表文件夹不存在,直接不受理
            view.setFlag("-11");//代表文件夹不存在,去上传页面
            return view;
        }

        return view;
    }

    public List<Employee> findAllSalor() throws Exception {
        return fileUploadAndDownMapper.findAllSalor();
    }

    /**
     * 功能描述:文件夹名验证
     *
     * @auther: homey Wong
     * @date: 2019/2/27 0027 上午 10:13
     * @param:
     * @return:
     * @describtion
     */
    @Transactional
    public boolean isFolderNameForEngDateOrderNoSalor(String filePathName) throws Exception {
        String[] pathName = null;
        if (filePathName.contains(",")) {
            pathName = filePathName.split(",");
        } else {
            pathName[0] = filePathName;
        }
        boolean isFolderNameForEngDateOrderNoSalor = true;
        List<String> salorNames = fileUploadAndDownMapper.getAllSalorNames();
        List<String> designers = fileUploadAndDownMapper.getAllDesigners();
        Pattern orderNoRegex = Pattern.compile("^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$");
        Pattern dataRegex = Pattern.compile("^2019[0|1][0-9]$");
        String[] foldersName = null;
        if (pathName != null) {
            for (int i = 0; i < pathName.length; i++) {
                foldersName = pathName[i].split("/");
                for (String strname : foldersName) {
                    if (salorNames.contains(strname) || designers.contains(strname)) {
                        isFolderNameForEngDateOrderNoSalor = false;
                        return isFolderNameForEngDateOrderNoSalor;
                    }
                    Matcher match = orderNoRegex.matcher(strname);
                    Matcher match1 = dataRegex.matcher(strname);
                    if (match.matches() || match1.matches()) {
                        isFolderNameForEngDateOrderNoSalor = false;
                        return isFolderNameForEngDateOrderNoSalor;
                    }
                }
            }
        }
        return isFolderNameForEngDateOrderNoSalor;
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
    @Transactional
    public DownloadView findIsExistFilesFolder(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) throws Exception {
        boolean isAllNewFile = true; //全为新文件即为真
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        //根据业务员订单号设计师看有没有文件夹
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {  //看四层基本层次结构存不存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            if (isAllNewFile) { //全为新文件
                view = addOldOrderNoNewFilesByFolder(view, fileArray, userInfo, oldFileUrls.get(0).getLogur1(), fileManFileInfo);
                view.setFlag("1");
            }
        } else {//如果没有文件夹,直接当成新文件全部存.
            view = this.addFilesDatabyFolder(view, fileArray, userInfo);
            view.setFlag("1");//代表全为新文件,且无文件夹,存储成功

        }

        return view;
    }

    @Override
    @Transactional
    public String checkIsExistFilesFolderforUpdate(String pathName, DownloadView view, UserInfo info) throws Exception {
        String returnMessage = "OK";
        String[] urlPaths = null;
        if (pathName.contains(",")) {
            urlPaths = pathName.split(",");
        } else {
            urlPaths[0] = pathName;
        }
        boolean isAllExistFile = true;//true代表文件以前全部存过
        String noExistFileNames = "";
        int isNoExsitFileNum = 0;
        List<String> strnames = new ArrayList<String>();
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        List<String> centerUrls = new ArrayList<String>();
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        if (fileManFileInfo.size() > 0) { //代表前四级文件夹存在
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                strnames.add(fu.getOrginName());
            }

            for (FilemanUrl url : oldFileUrls) {
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }


            for (int i = 0; i < urlPaths.length; i++) {
                if (!strnames.contains(subAfterString(urlPaths[i], "/")) || !centerUrls.contains(urlPaths[i])) {//查看文件夹下的文件是否完全一样
                    isNoExsitFileNum++;
                    isAllExistFile = false;
                    noExistFileNames += urlPaths[i] + "===";
                }
            }

            if (!isAllExistFile) {
                returnMessage = "您本次更新的文件夹内有些许新文件，共有"+isNoExsitFileNum+"个，分别是:"+noExistFileNames+"或者您本次更新的文件夹结构与上次上传的文件夹结构不一致，请检查!";
                return returnMessage;
            }

        } else {//代表前四级文件夹不存在,直接不受理
            returnMessage = "您本次要更新的文件夹结构不对，请按上次上传的文件夹层次结构来!";
            return returnMessage;
        }

        return returnMessage;
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
                Integer pointindex = StringUtils.ordinalIndexOf(url.getLogur1(), "/", 5);
                String afterFourLevel = url.getLogur1().substring(pointindex + 1, url.getLogur1().length());
                centerUrls.add(afterFourLevel);
            }


            for (MultipartFile file : fileArray) {
                if (strnames.contains(subAfterString(file.getOriginalFilename(), "/")) && centerUrls.contains(file.getOriginalFilename())) {//查看文件夹下的文件是否完全一样
                    newFileArray.add(file);
                }
            }

            if (isAllExistFile) {//代表文件夹与文件全是以前存在过的
                updateFilesDataFolder(fileManFileInfo, view, newFileArray, userInfo);
                view.setFlag("-18");//代表文件全部更新成功
            }

        } else {//代表前四级文件夹不存在,直接不受理
            view.setFlag("-11");//代表文件夹不存在,去上传页面
            return view;
        }

        return view;
    }

    @Transactional
    public String checkFileUpdateRight(String pathName, DownloadView view, UserInfo userInfo) throws Exception {
        String[] pathNames = null;
        if (pathName.contains(",")) {
            pathNames = pathName.split(",");
        } else {
            pathNames[0] = pathName;
        }
        FilemanRight right = null;
        String fileName = null;
        FilemanUrl url = null;
        String returnMessage = "OK";
        for (int i = 0; i < pathNames.length; i++) {
            fileName = subAfterString(pathNames[i], "/");
            right = fileUploadAndDownMapper.getFileRightByOrderNoUidfileName(view.getOrderNo(), fileName, userInfo.getuId());
            if (right == null) {//如果是空代表没有权限，接着往下查URL里有没有文此文件
                url = fileUploadAndDownMapper.getFileUrlByOrderNoSo(view.getOrderNo(), view.getSalor(), userInfo.getuId(), fileName);
                if (url == null) {
                    returnMessage = "您本次要更新的文件里有新文件，应去上传区进行上传，如:"+fileName+"系统里就不存在";
                } else {
                    returnMessage = "您没有权限，如:"+fileName+"就没有权限!";
                }

            } else {
                if (!right.getOpRight().contains("2")) {
                    returnMessage = "您没有权限，如:"+fileName+"就没有权限!";
                }
            }
        }
        return returnMessage;
    }


    @Transactional
    public void updateFilesDataFolder(List<FileManFileInfo> fileManFileInfo, DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) throws Exception {
        FileManFileInfo ffi = null;
        FilemanUrl filemanUrl;
        String fileName = null;
        int index;
        String centerPath = null;
        String filePath = null;
        Integer pointindex = 0;
        String oldsPath = null;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(view.getUserName());
            //修改头信息
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (MultipartFile file : fileArray) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(subAfterString(file.getOriginalFilename(), "/"), fileManFileInfo.get(0).getId());
                pointindex = StringUtils.ordinalIndexOf(filemanUrl.getLogur1(), "/", 5);
                oldsPath = filemanUrl.getLogur1().substring(0, pointindex + 1);
                if (filemanUrl != null) {
                    // FileUtil.modifyUpdateFileFolderByUrl(file, userInfo, view, filemanUrl.getLogur1());//覆盖文件操作

                    fileName = file.getOriginalFilename();
                    index = fileName.lastIndexOf("/");
                    centerPath = fileName.substring(0, index);
                    fileName = fileName.substring(index + 1, fileName.length());
                    filePath = oldsPath + centerPath;
                    FtpUtils.uploadFile(filePath, fileName, file.getInputStream());


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

        String ftpUrl = null;
        String fileName = null;
        int index;
        String centerPath = null;
        String filePath = null;
        //文件进度长度
        for (int i = 0; i < files.size(); i++) {
            try {
                fileName = files.get(i).getOriginalFilename();
                index = fileName.lastIndexOf("/");
                centerPath = fileName.substring(0, index);
                fileName = fileName.substring(index + 1, fileName.length());
                filePath = view.getUserName() + "/" + formateString(new Date()) + "/" + view.getSalor() + "/"
                        + randomNum + "/" + view.getOrderNo() + "/" + centerPath;
                FtpUtils.uploadFile(filePath, fileName, files.get(i).getInputStream());
                deskName = filePath + "/" + fileName;
                orginname = subAfterString(deskName, "/");
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
        Integer pointindex = StringUtils.ordinalIndexOf(oldPath, "/", 5);
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
        String fileName = null;
        int index;
        String centerPath = null;
        String filePath = null;
        fileUploadAndDownMapper.updateFileManFileInfo(fileManFileInfo.getTotalFilesNum(), fileManFileInfo.getUpdateCount(), fileManFileInfo.getUpdateTime(), fileManFileInfo.getId());
        for (MultipartFile file : fileArray) {
            fileName = file.getOriginalFilename();
            index = fileName.lastIndexOf("/");
            centerPath = fileName.substring(0, index);
            fileName = fileName.substring(index + 1, fileName.length());
            filePath = oldsPath + centerPath;
            FtpUtils.uploadFile(filePath, fileName, file.getInputStream());
            deskName = filePath + "/" + fileName;
            orginname = subAfterString(deskName, "/");
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
            //filemanRight.setuId(userInfo.getuId());
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
        List<FilemanUrl> oldFileUrls = new ArrayList<FilemanUrl>();
        //根据业务员订单号设计师看有没有文件夹
        List<String> urlStr = new ArrayList<String>();
        //根据业务员订单号设计师看有没有文件夹
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        String pointFolder = null;
        boolean f = true;
        if (fileManFileInfo.size() > 0) {  //有文件夹
            oldFileUrls = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
            for (FilemanUrl fu : oldFileUrls) {
                if(view.getSaveFolderName()!=null) {
                    if (view.getSaveFolderName() != "" && f) {
                        if (fu.getLogur1().contains(view.getSaveFolderName())) {
                            pointFolder = fu.getLogur1();
                            f = false;
                        }
                    }
                }
                urlStr.add(fu.getLogur1());
            }

            if (pointFolder != null && pointFolder.length() > 0 && !view.getSaveFolderName().equals("")) {//代表为用户指定目录
                int lia = pointFolder.indexOf(view.getSaveFolderName());
                pointFolder = pointFolder.substring(0, lia + view.getSaveFolderName().length() + 1);
                view = addOFilesByPointFile(view, fileArray, userInfo, pointFolder, fileManFileInfo);
                view.setFlag("1");
            } else {
                view = addOldOrderNoNewFiles(view, fileArray, userInfo, urlStr, fileManFileInfo);
                view.setFlag("1");
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
    public void updateFilesData(List<FileManFileInfo> fileManFileInfo, DownloadView view, List<MultipartFile> fileArray, UserInfo userInfo) throws Exception {
        FileManFileInfo ffi = null;
        FilemanUrl filemanUrl;
        if (fileManFileInfo != null && fileManFileInfo.size() > 0) {
            ffi = fileManFileInfo.get(0);
            ffi.setUpdateCount(ffi.getUpdateCount() + 1);
            ffi.setUpdateTime(new Date());
            ffi.setUpdateUser(view.getUserName());
            int pointindex;
            String oldsPath = null;
            //修改头信息
            fileUploadAndDownMapper.updateFileManFileInfo2(ffi.getUpdateCount(), ffi.getUpdateTime(), ffi.getUpdateUser(), ffi.getId());
            for (MultipartFile file : fileArray) {
                filemanUrl = fileUploadAndDownMapper.findFileUrlByFileInFoDataAndFileName(file.getOriginalFilename(), fileManFileInfo.get(0).getId());
                if (filemanUrl != null) {
                    //FileUtil.modifyUpdateFileByUrl(file, userInfo, view, filemanUrl.getLogur1());//覆盖文件操作
                    pointindex = StringUtils.ordinalIndexOf(filemanUrl.getLogur1(), "/", 5);
                    oldsPath = filemanUrl.getLogur1().substring(0, pointindex + 1);
                    FtpUtils.uploadFile(oldsPath, file.getOriginalFilename(), file.getInputStream());
                    //取老文件信息


                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum() + 1);
                    filemanUrl.setUpdateuser(userInfo.getUserName());
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(new Date(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId(), filemanUrl.getUpdateuser());
                }
            }
        }

    }

    @Override
    @Transactional
    public int findAllFileUrlByConditionCount(Integer uId) throws Exception {
        return fileUploadAndDownMapper.findAllFileUrlByConditionCount(uId);
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
            // FileUtil.uploadFileByUrl(file, userInfo, view, pointpath);
            try {
                FtpUtils.uploadFile(pointpath, orginname, file.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            // filemanRight.setuId(userInfo.getuId());
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
        pointindex = StringUtils.ordinalIndexOf(oldPath, "/", 5);
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
            //FileUtil.uploadFileByUrl(file, userInfo, view, oldsPath);
            FtpUtils.uploadFile(oldsPath, file.getOriginalFilename(), file.getInputStream());
            deskName = oldsPath + file.getOriginalFilename();
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
            // filemanRight.setuId(userInfo.getuId());
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
                //deskName = FileUtil.uploadFile(files.get(i), userInfo, view, randomNum);
                FtpUtils.uploadFile(userInfo.getUserName() + "/" + formateString(new Date()) + "/" + view.getSalor() + "/"
                        + randomNum + "/" + view.getOrderNo() + "/", files.get(i).getOriginalFilename(), files.get(i).getInputStream());
                deskName = userInfo.getUserName() + "/" + formateString(new Date()) + "/" + view.getSalor() + "/"
                        + randomNum + "/" + view.getOrderNo() + "/" + files.get(i).getOriginalFilename();
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
                // filemanRight.setuId(userInfo.getuId());
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
    @Transactional
    public List<DownloadView> findAllUploadFileByUserId(Integer uid) throws Exception {
        List<DownloadView> list = fileUploadAndDownMapper.findAllUploadFileByUserId(uid);
        return list;
    }

    /**
     * 功能描述:根据订单号获取相应订单信息
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 上午 11:27
     * @param:
     * @return:
     * @describtion
     */
    @Override
    @Transactional
    public DownloadView findMessageByOrderNo(String orderNo) throws Exception {
        return fileUploadAndDownMapper.findMessageByOrderNo(orderNo);
    }

    /**
     * 功能描述:根据条件查询
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 下午 3:11
     * @param:
     * @return:
     * @describtion
     */
    @Override
    @Transactional
    public List<DownloadView> findAllUrlByParamThreeNew2(DownloadView view) throws Exception {
        String linshiId = view.getLinshiId();
        if (linshiId == null || linshiId.trim().length() <= 0) {
            view.setuId(0);
        } else {
            view.setuId(Integer.valueOf(linshiId.trim()));
        }
        return fileUploadAndDownMapper.findAllUrlByParamThreeNew2(view);
    }

        /**
         * @author:homey Wong
         * @Date: 2018.12.21
         */
        @Override
        @Transactional
        public List<DownloadView> findAllUploadFileByCondition (Integer uid,int currentPageTotalNum, int PageSize) throws
        Exception {
            return fileUploadAndDownMapper.findAllUploadFileByCondition(uid, currentPageTotalNum, PageSize);

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
        @Transactional
        public int findAllUploadFileCountByUserId (Integer uId) throws Exception {
            return fileUploadAndDownMapper.findAllUploadFileCountByUserId(uId);

        }

        @Override
        @Transactional
        public List<FilemanUrl> findAllUrlByOrderNo (String orderNo) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByOrderNo(orderNo);
        }

        @Override
        @Transactional
        public FilemanRight getFileRightByUrlIdAndFileInfoIdAnaUid (Integer urlId, Integer fileInfoId, Integer uId) throws
        Exception {
            return fileUploadAndDownMapper.getFileRightByUrlIdAndFileInfoIdAnaUid(urlId, fileInfoId, uId);
        }

        @Override
        @Transactional
        public List<String> findAllUrlByOrderNo2 (String orderNo) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByOrderNo2(orderNo);
        }

        @Override
        @Transactional
        public List<DownloadView> findAllUploadFileByParaCondition (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUploadFileByParaCondition(view);
        }

        @Override
        @Transactional
        public List<DownloadView> findAllFileUrlByCondition (Integer uid,int currentPageTotalNum, int PageSize) throws
        Exception {
            return fileUploadAndDownMapper.findAllFileUrlByCondition(uid, currentPageTotalNum, PageSize);
        }

        @Override
        @Transactional
        public List<String> findAllUrlByParamThree (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByParamThree(view);
        }

        @Override
        @Transactional
        public List<String> findAllUrlByParamThreeNew (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByParamThreeNew(view);
        }

        @Override
        @Transactional
        public int findAllUploadFileCountByParaCondition (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUploadFileCountByParaCondition(view);
        }

        @Override
        @Transactional
        public List<DownloadView> findAllFilesByCondParam (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllFilesByCondParam(view);
        }

        @Override
        @Transactional
        public int findAllFilesByCondParamCount (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllFilesByCondParamCount(view);
        }


        @Override
        public FileManFileInfo getFileInfoByOrderNo (String orderNo) throws Exception {
            return fileUploadAndDownMapper.getFileInfoByOrderNo(orderNo);
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
        @Transactional
        public String checkFileisSame (DownloadView view, UserInfo userInfo, String filePathName){
            //第一步，查看上传文件们的是否有重复
            String[] names = null;
            if (filePathName.contains(",")) {
                names = filePathName.split(",");
            } else {
                names[0] = filePathName;
            }
            List<String> originNames = new ArrayList<String>();
            String isSameFileUpload = "OK";//代表没有
            for (int i = 0; i < names.length; i++) {
                if (originNames.contains(names[i])) {
                    isSameFileUpload = "您要上传的文件文件名有重复,如:"+names[i];//有，即刻返回
                    return isSameFileUpload;
                } else {
                    originNames.add(names[0]);
                }

            }
            //第二步，查看数据库里与现在要上传的文件们是否有重复

            List<String> urls = fileUploadAndDownMapper.findAllFileUrlNameByCondition(userInfo.getuId(), view.getSalor(), view.getOrderNo());

            for (String s : originNames) {
                if (urls.contains(s)) {
                    isSameFileUpload = "同一订单下您的文件已上传过,如:"+s;//有重复,即刻返回
                    return isSameFileUpload;
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
        @Transactional
        public String isSameFolderNameorFileNameMethod (UserInfo userInfo, DownloadView view, String filePathName){
            String[] paths = null;
            if (filePathName.contains(",")) {
                paths = filePathName.split(",");
            } else {
                paths[0] = filePathName;
            }
            String isSameFileUploadFolderName = "OK";//代表没有重复
            List<String> originFNames = new ArrayList<String>();
            List<String[]> foldernameLists = new ArrayList<String[]>();
            String afterstr = null;
            String[] splitArray = null;
            List<String> urlss = fileUploadAndDownMapper.findAllFileUrlNameByCondition(userInfo.getuId(), view.getSalor(), view.getOrderNo());
            for (int ii = 0; ii < paths.length; ii++) {
                if (paths[ii].contains(",")) {
                    isSameFileUploadFolderName = "文件夹或文件名不能包含,号!如:"+paths[ii];//表示上传文件夹中,存在
                    return isSameFileUploadFolderName;
                } else {
                    String centerurl = StringUtil.subMyString(paths[ii], "/");
                    //第一步,查看上传文件夹下的文件名是否相同
                    if (originFNames.contains(subAfterString(paths[ii], "/"))) {
                        isSameFileUploadFolderName = "文件夹内有重复文件名文件！如:"+subAfterString(paths[ii], "/");//表示上传文件夹中就有相同文件名字存在
                        return isSameFileUploadFolderName;
                    } else {
                        if(!subAfterString(paths[ii], "/").contains(".")){
                            isSameFileUploadFolderName = "文件夹内的文件必须要有扩展名，如:"+subAfterString(paths[ii], "/")+"没有扩展名";//有重复,即刻返回
                            return isSameFileUploadFolderName;
                        }
                        //第二步,上传的文件夹下的文件名与数据库的文件名是否重复
                        originFNames.add(subAfterString(paths[ii], "/"));
                        if (urlss.contains(subAfterString(paths[ii], "/"))) {
                            isSameFileUploadFolderName = "您订单下的文件夹内文件与系统中已存在的文件重复!如:"+subAfterString(paths[ii], "/");//有重复,即刻返回
                            return isSameFileUploadFolderName;
                        }
                    }
                    //第三步,查看上传的文件夹下的文件夹名是否有重复//自比较？？？？？？可以优化成不断插入子目录，循环判断
                    if (centerurl != null) {
                        if (centerurl.contains("/")) {
                            afterstr = centerurl.replaceAll("/", "\\\\");
                            splitArray = afterstr.split("\\\\");
                            foldernameLists.add(splitArray);
                        }
                    }
                }
            }

            //自单个路径比较
            String[] aa = null;
            List<String> singleFolder = new ArrayList<String>();
            for (int a = 0; a < foldernameLists.size(); a++) {
                aa = foldernameLists.get(a);
                for (int b = 0; b < aa.length; b++) {
                    if (singleFolder.contains(aa[b])) {
                        isSameFileUploadFolderName = "您的文件夹内有重复文件夹名,如:"+aa[b];
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
                                        isSameFileUploadFolderName = "您的文件夹有重复的文件夹名,如:"+centerfolderone1[k];//代表同一层次是一样,但往前追溯有些层次不一样,视为重命名文件夹
                                        return isSameFileUploadFolderName;
                                    }
                                }
                            } else {
                                if (centerfolderone1[k].equals(centerfoldertwo2[f])) {
                                    {//如果不是同一层次的文件夹名字一样.即视重复
                                        isSameFileUploadFolderName = "您的文件夹有重复的文件夹名,如:"+centerfolderone1[k];//有重复,即刻返回
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
                pointindex = StringUtils.ordinalIndexOf(urladdr, "/", 5);
                if (pointindex > 0) {
                    olderCenterstr = urladdr.substring(0, pointindex + 1);
                    centerstr = urladdr.substring(pointindex + 1, urladdr.length());
                    centerstr = subMyString(centerstr, "/");
                    if(centerstr!=null && centerstr!="") {
                        if (centerstr.contains("/")) {
                            mysqlcenterurls.add(centerstr.split("/"));
                        }
                    }
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
                                        isSameFileUploadFolderName = "您订单下的的文件夹名与系统中的文件夹名重复,如:"+centerfolderone[k];//代表同一层次是一样,但往前追溯有些层次不一样,视为重命名文件夹
                                        return isSameFileUploadFolderName;
                                    }
                                }
                            } else {
                                if (centerfolderone[k].equals(centerfoldertwo[f])) {
                                    {//如果不是同一层次的文件夹名字一样.即视重复
                                        isSameFileUploadFolderName = "您订单下的的文件夹名与系统中的文件夹名重复,如:"+centerfoldertwo[f];//有重复,即刻返回
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

        @Transactional
        public List<String> findAllOrderNum ( int currentPageTotalNum, int pageSize) throws Exception {
            return fileUploadAndDownMapper.findAllOrderNum(currentPageTotalNum, pageSize);
        }

        @Transactional
        public int findAllOrderNumCount () throws Exception {
            return fileUploadAndDownMapper.findAllOrderNumCount();
        }

        @Transactional
        public List<DownloadView> findAllUrlByParamManyOrNo (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByParamManyOrNo(view);
        }

        @Transactional
        public int findAllUrlByParamManyOrNoCount (DownloadView view) throws Exception {
            return fileUploadAndDownMapper.findAllUrlByParamManyOrNoCount(view);
        }

        @Transactional
        public int findAllUrlByParamThreeNew2Count (DownloadView view) throws Exception {
            String linshiId = view.getLinshiId();
            if (linshiId == null || linshiId.trim().length() <= 0) {
                view.setuId(0);
            } else {
                view.setuId(Integer.valueOf(linshiId.trim()));
            }
            return fileUploadAndDownMapper.findAllUrlByParamThreeNew2Count(view);
        }

        @Transactional
        public DownloadView findMessageByOrderNoandUid(String orderNo,String linshiId) throws Exception {
            Integer uId = 0;
            if (linshiId == null || linshiId.trim().length() <= 0) {
                uId = 0;
            } else {
                uId = Integer.valueOf(linshiId.trim());
            }
            return fileUploadAndDownMapper.findMessageByOrderNoandUid(orderNo,uId);
        }

        @Transactional
        public List<FilemanUrl> findAllUrlByOrderNoAndUid(String orderNo,String linshiId) throws  Exception {
            Integer uId = 0;
            if (linshiId == null || linshiId.trim().length() <= 0) {
                uId = 0;
            } else {
                uId = Integer.valueOf(linshiId.trim());
            }
            return fileUploadAndDownMapper.findAllUrlByOrderNoAndUid2(orderNo,uId);
        }



    @Transactional
        public DownloadView findOrderNobyOrderNo (String orderNo) throws Exception {
            return fileUploadAndDownMapper.findOrderNobyOrderNo(orderNo);
        }

    }
