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
        boolean flag = true;//true代表以前存过  //false代表以前没存过
        FilemanUrl filemanUrl = null;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();//存放以前存过,本次可以覆盖的文件
        String message = "";
        String oldPath = "";//存放之前拿以前存过的路径
        int isNoExistNum = 0;//此变量代表看有没有新文件,新文件不受理
        List<FilemanUrl> urlolds = null;//存放以前的文件的相关老文件
        int isexsitNum = 0;
        //取出存在的文件的文件头信息
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        if (fileManFileInfo.size() > 0) {
            urlolds = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
        }
        for (MultipartFile file : fileArray) {
            //根据文件名看以前有没有存过
            filemanUrl = fileUploadAndDownMapper.findIsExistFile(file.getOriginalFilename());
            if (filemanUrl != null) {
                //i不为空 可以存服务器 进行覆盖
                oldPath = filemanUrl.getLogur1();
                newFileArray.add(file);
                isexsitNum++;
            } else {
                //为空
                message += file.getOriginalFilename() + ",";
                isNoExistNum++;
                flag = false;//代表此次存的文件以前没存过,属新文件

            }
        }
        //以下开始进行判断
        if (fileManFileInfo.size() > 0 && newFileArray.size() > 0 && flag) {//代表上传的全为已存在的文件
            this.updateFilesData(fileManFileInfo, view, newFileArray, userInfo);
            view.setFlag("-17");//文件全部受理成功
            //代表上传的文件头与文件存在于服务器中,但是有个别新文件
        } else if (fileManFileInfo.size() > 0 && newFileArray.size() > 0 && !flag) {
            view.setExistFileMessage(message);
            view.setIsExistNum(isNoExistNum);
            updateFilesData(fileManFileInfo, view, newFileArray, userInfo);
            view.setFlag("-12");//代表存储成功,但有个别新文件,返回界面告知未存的新文件个数与名
            //以下逻辑为代表文件头信息存在,但更新的文件全为新文件,不受理
        } else if (fileManFileInfo.size() > 0 && newFileArray.size() <= 0 && !flag) {//代表全为未上传过的文件
            view.setFlag("-13");//代表全为未上传过的文件,但头信息有
            //代表文件头为空,但文件名服务器有,指示用户文件头信息填写有误或其它
        } else if (fileManFileInfo.size() <= 0 && newFileArray.size() > 0) {
            view.setFlag("-15");
            view.setIsExistNum(isexsitNum);
            view.setOrderNoMessage( view.getOrderNo() + "," + view.getSalor() + "," + view.getUserName() + "," );
            //没有头信息,也没有任何文件信息,提示用户来错了地方
        } else if (fileManFileInfo.size() <= 0 && newFileArray.size() <= 0) {
            view.setFlag("-16"); //全为全新文件,去上传页面处理
        }
        return view;
    }

    //以下功能为文件上传功能,只对新的文件进行存储
    @Override
    @Transactional
    public DownloadView findIsExistFiles(List<MultipartFile> fileArray, DownloadView view, UserInfo userInfo) {
        boolean flag = false;//判断是否存的是旧项目新增新文件   true代表是以前存过一次的
        FilemanUrl filemanUrl = null;
        List<MultipartFile> newFileArray = new ArrayList<MultipartFile>();
        String message = "";
        String oldPath = "";
        int isExistnum = 0;
        List<FilemanUrl> urlolds;
        List<FileManFileInfo> fileManFileInfo = fileUploadAndDownMapper.isSameOrderNoandOtherMessage(view.getUserName(), view.getOrderNo(), view.getSalor());
        //代表上传的是前一次上传的订单
        for (MultipartFile file : fileArray) {
            filemanUrl = fileUploadAndDownMapper.findIsExistFile(file.getOriginalFilename());
            if (filemanUrl == null) {
                //为空 可以存服务器
                newFileArray.add(file);
            } else {
                //不为空
                message += file.getOriginalFilename() + ",<br>";
                oldPath = filemanUrl.getLogur1();
                isExistnum++;
                flag = true;//代表此次存的文件已前存过一次，没有的文件存在本文件夹里面

            }
        }
        if (fileManFileInfo.size() <= 0 && !flag && newFileArray.size() > 0) {//代表上传的为全新文件
            view = this.addFilesData(view, fileArray, userInfo);
        } else if (fileManFileInfo.size() > 0 && newFileArray.size() > 0) {//代表此次存的文件已前存过一次，此次fileARrray收集的是以前存过一次，现在新增的文件
            view.setExistFileMessage(message);
            view.setIsExistNum(isExistnum);
            if (oldPath == "" || oldPath == null) {
                urlolds = fileUploadAndDownMapper.findFileUrlByFileInFoData(fileManFileInfo.get(0).getId());
                if (urlolds.size() > 0) {
                    view = addOldOrderNoNewFiles(view, newFileArray, userInfo, urlolds.get(0).getLogur1(), fileManFileInfo);
                    view.setFlag("1");
                }
            } else {
                view = addOldOrderNoNewFiles(view, newFileArray, userInfo, oldPath, fileManFileInfo);
            }
        } else if (fileManFileInfo.size() > 0 && flag && newFileArray.size() <= 0) {//代表全为已上传文件
            view.setFlag("-10");
        } else if (flag && fileManFileInfo.size() <= 0 && newFileArray.size() == 0) {
            view.setFlag("-6");
            view.setOrderNoMessage("您本次存储的订单编号等信息" + view.getOrderNo() + "," + view.getSalor() + "," + view.getUserName() + "," + "与上次存储的信息不符，请您核对后再上传!");
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
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg

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
                filemanUrl = fileUploadAndDownMapper.findIsExistFile(file.getOriginalFilename());
                if (filemanUrl != null) {
                    FileUtil.modifyUpdateFileByUrl(file, userInfo, view, filemanUrl.getLogur1());//覆盖文件操作
                    //取老文件信息


                    filemanUrl.setUpTime(new Date());
                    filemanUrl.setSingleFileUpdateNum(filemanUrl.getSingleFileUpdateNum()+1);
                    filemanUrl.setModifyReason(view.getModifyReason());
                    fileUploadAndDownMapper.updateFileUrlById(filemanUrl.getUpTime(), filemanUrl.getSingleFileUpdateNum(), filemanUrl.getModifyReason(), filemanUrl.getId());
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
        //首先做文件订单信息核 对
        //不为空，即可以向URL表和RIGHT表插入数据，并向服务器存入新增文件
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
            filemanUrl.setOpRight("1,2,3,4");
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
            filemanRight.setOpRight("1,2,3,4");
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
                filemanUrl.setUserName(userInfo.getUserName());
                filemanUrl.setOpRight("1,2,3,4");
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
                filemanRight.setOpRight("1,2,3,4");
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
