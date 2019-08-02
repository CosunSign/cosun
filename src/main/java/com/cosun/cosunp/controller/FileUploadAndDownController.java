package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.aspose.cad.Color;
import com.aspose.cad.Image;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/fileupdown")
public class FileUploadAndDownController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IUserInfoServ userInfoServ;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;

    private static Logger logger = LogManager.getLogger(FileUploadAndDownController.class);

    @ResponseBody
    @RequestMapping("/touploadpage")
    public ModelAndView toUploadPage() throws Exception {
        ModelAndView view = new ModelAndView("uploadpage");
        return view;
    }

    /**
     * 功能描述:文件查看按条件查询
     *
     * @auther: homey Wong
     * @date: 2019/1/17 0017 上午 9:53
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/browserbyquerycondition", method = RequestMethod.POST)
    public void browserbyQuerycondition(@RequestBody DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setuId(Integer.valueOf(view.getEngineer()));
        List<DownloadView> dataList = fileUploadAndDownServ.findAllUploadFileByParaCondition(view);
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByParaCondition(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        if (dataList != null && dataList.size() > 0) {
            dataList.get(0).setMaxPage(maxPage);
            dataList.get(0).setRecordCount(recordCount);
            dataList.get(0).setUserName(userInfo.getUserName());
            dataList.get(0).setPassword(userInfo.getUserPwd());
            dataList.get(0).setCurrentPage(view.getCurrentPage());
        }
        String str1 = null;
        if (dataList != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(dataList);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str1); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 功能描述:文件查找
     *
     * @auther: homey Wong
     * @date: 2019/1/17 0017 上午 9:23
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping("/tobroserfilepage")
    public ModelAndView tobroserfilepage(HttpSession session, int currentPage) throws Exception {
        ModelAndView mav = new ModelAndView("broserfilepage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUserOnlyDesigner();
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllFileUrlByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
        int recordCount = fileUploadAndDownServ.findAllFileUrlByConditionCount(userInfo.getuId());
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        mav.addObject("view", view);
        mav.addObject("downloadViewList", downloadViewList);
        mav.addObject("userInfos", userInfos);
        mav.addObject("employees", employees);
        mav.addObject("extensionList", extensionList);
        return mav;
    }

    /**
     * 功能描述:双击图标出现清单更改记录
     *
     * @auther: homey Wong
     * @date: 2019/3/26 0026 上午 9:37
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/showUpdateDownItemByIcon")
    public void showUpdateDownItemByIcon(@RequestBody(required = true) DownloadView view, HttpServletRequest request,
                                         HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<ShowUpdateDownRecord> recordsAll = new ArrayList<ShowUpdateDownRecord>();
        List<ShowUpdateDownRecord> recordsUpdate = fileUploadAndDownServ.getFileModifyRecordByFolOrFilAndOrderNo(view);
        List<ShowUpdateDownRecord> recordsDown = fileUploadAndDownServ.getFileDownRecordByFolOrdilAndOrderNo(view);
        recordsAll.addAll(recordsUpdate);
        recordsAll.addAll(recordsDown);
        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(recordsAll);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * 功能描述:双击查询文件更新下载记录
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 下午 2:40
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/showUpdateDownItem")
    public void showUpdateDownItem(@RequestBody(required = true) DownloadView view, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<ShowUpdateDownRecord> recordsAll = new ArrayList<ShowUpdateDownRecord>();
        List<ShowUpdateDownRecord> records = fileUploadAndDownServ.getFileModifyRecordByUrlId(view.getFileUrlId());
        List<ShowUpdateDownRecord> records2 = fileUploadAndDownServ.getFileDownRecordByUrlId(view.getFileUrlId());
        recordsAll.addAll(records);
        recordsAll.addAll(records2);
        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(recordsAll);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 功能描述:文件返回上一级
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 下午 2:40
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/findBackFoldersByQueryParam3New")
    public void findBackFoldersByQueryParam3New(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> norepeatFoldorFile = null;
        List<String> exts = fileUploadAndDownServ.findAllExtension();
        List<FilemanUrl> urls = null;
        FilemanRight right = null;
        List<String> folderOrFiles = null;
        Integer lastIndex = null;
        String foldername = view.getFolderName();
        Integer index = null;
        DownloadView vi = null;
        List<DownloadView> views = null;
        List<DownloadView> viewss = null;
        DownloadView vvv = null;
        boolean flag = true;
        String allOprights = "";
        String tempFolFileName = null;
        FilemanRight right1 = null;
        int maxPage = 0;
        //根据当前文件夹或文件名查找上一级文件夹名,如上一级文件夹名是以ORDERNO类形的,即开启limit查询
        String upFolderName = null;
        // if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {
        folderOrFiles = new ArrayList<String>();
        views = new ArrayList<DownloadView>();
        norepeatFoldorFile = new ArrayList<String>();
        // urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
        urls = fileUploadAndDownServ.findAllUrlByOrderNoAndUid(view.getOrderNo(), view.getLinshiId());

        //取上二级文件夹名 由下找下一层文件夹或文件
        String extName = StringUtil.subAfterString(foldername, ".");
        if (!foldername.contains(".") || !exts.contains(extName.toLowerCase())) {
            for (FilemanUrl u : urls) {
                vi = fileUploadAndDownServ.findMessageByOrderNoandUid(view.getOrderNo(), view.getLinshiId());
                lastIndex = u.getLogur1().indexOf("/" + view.getFolderName() + "/");
                if (lastIndex != -1) {
                    String linshi1 = u.getLogur1().substring(0, lastIndex);
                    upFolderName = StringUtil.subAfterString(linshi1, "/");
                    int linshilastIndex = linshi1.lastIndexOf("/");
                    String linshi2 = linshi1.substring(0, linshilastIndex);
                    foldername = StringUtil.subAfterString(linshi2, "/");
                }
                index = u.getLogur1().indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = u.getLogur1().indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = u.getLogur1().length();
                }
                if (index != -1) {
                    String linshiId = view.getLinshiId();
                    if (linshiId == null || linshiId.trim().length() <= 0) {
                        view.setuId(0);
                    } else {
                        view.setuId(Integer.valueOf(linshiId.trim()));
                    }
                    right = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUidBack(u.getId(), u.getFileInfoId(), view.getuId());
                    // folderOrFiles.add(u.getLogur1().substring(index + 2 + foldername.length(), lastIndex));
                    tempFolFileName = u.getLogur1().substring(index + 2 + foldername.length(), lastIndex);

                }


                for (DownloadView v : views) {
                    if (v.getFolderOrFileName() != null) {
                        if (v.getFolderOrFileName().equals(tempFolFileName)) {
                            flag = false;
                        }
                    }
                }
                if (flag && tempFolFileName != null) {
                    vvv = new DownloadView();
                    vi.setFolderOrFileName(tempFolFileName);
                    allOprights = "";

                    for (FilemanUrl uu : urls) {
                        if (uu.getLogur1().contains(tempFolFileName)) {
                            right1 = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(uu.getId(), uu.getFileInfoId(), view.getuId());
                            if (right1 != null && right1.getOpRight() != null) {
                                if (right1.getOpRight().contains("2") && !allOprights.contains("2")) {
                                    allOprights += "2,";
                                }
                                if (right1.getOpRight().contains("3") && !allOprights.contains("3")) {
                                    allOprights += "3,";
                                }
                                if (right1.getOpRight().contains("4") && !allOprights.contains("4")) {
                                    allOprights += "4,";
                                }
                                if (allOprights != "") {
                                    allOprights = allOprights.substring(0, allOprights.length());//去掉最后一个,
                                }
                            }
                        }
                    }
                    if (right != null && right.getOpRight() != null) {
                        extName = StringUtil.subAfterString(tempFolFileName, ".");
                        if (tempFolFileName.contains(".") && exts.contains(extName.toLowerCase())) {
                            vi.setOpRight(right.getOpRight());
                        } else {
                            vi.setOpRight(allOprights);
                        }

                        if (right.getuId() != null) {
                            vi.setOprighter(right.getuId().toString());
                        } else {
                            if (view.getLinshiId() != null && view.getLinshiId().trim().length() > 0) {
                                vi.setOprighter(view.getLinshiId());
                                vi.setOpRight(allOprights);
                            } else {
                                vi.setOprighter("");
                            }
                        }
                    } else {
                        vi.setOpRight(allOprights);
                    }

                    vvv = vi;
                    views.add(vvv);
                }
                flag = true;
            }

        } else {
            String filefoldername = null;
            String backFolderName = null;
            for (FilemanUrl u : urls) {
                vi = fileUploadAndDownServ.findMessageByOrderNoandUid(view.getOrderNo(), view.getLinshiId());
                if (u.getLogur1().contains(foldername)) {
                    filefoldername = u.getLogur1();
                }
            }

            if (filefoldername != null) {
                lastIndex = filefoldername.indexOf("/" + view.getFolderName());
                if (lastIndex > 0) {
                    String linshi1 = filefoldername.substring(0, lastIndex);
                    upFolderName = StringUtil.subAfterString(linshi1, "/");
                    int linshilastIndex = linshi1.lastIndexOf("/");
                    String linshi2 = linshi1.substring(0, linshilastIndex);
                    foldername = StringUtil.subAfterString(linshi2, "/");
                }
            }

            for (FilemanUrl u : urls) {
                vi = fileUploadAndDownServ.findMessageByOrderNoandUid(view.getOrderNo(), view.getLinshiId());
                if (u.getLogur1().contains(foldername)) {
                    filefoldername = u.getLogur1();
                }
                index = u.getLogur1().indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = u.getLogur1().indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = u.getLogur1().length();
                }
                if (index != -1) {
                    String linshiId = view.getLinshiId();
                    if (linshiId == null || linshiId.trim().length() <= 0) {
                        view.setuId(0);
                    } else {
                        view.setuId(Integer.valueOf(linshiId.trim()));
                    }
                    folderOrFiles.add(u.getLogur1().substring(index + 2 + foldername.length(), lastIndex));
                    right = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUidBack(u.getId(), u.getFileInfoId(), view.getuId());
                    // folderOrFiles.add(u.getLogur1().substring(index + 2 + foldername.length(), lastIndex));
                    tempFolFileName = u.getLogur1().substring(index + 2 + foldername.length(), lastIndex);
                }

                for (DownloadView v : views) {
                    if (v.getFolderOrFileName() != null) {
                        if (v.getFolderOrFileName().equals(tempFolFileName)) {
                            flag = false;
                        }
                    }
                }
                if (flag && tempFolFileName != null) {
                    vvv = new DownloadView();
                    vi.setFolderOrFileName(tempFolFileName);
                    allOprights = "";
                    for (FilemanUrl uu : urls) {
                        if (uu.getLogur1().contains(tempFolFileName)) {
                            right1 = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(uu.getId(), uu.getFileInfoId(), view.getuId());
                            if (right1 != null && right1.getOpRight() != null) {
                                if (right1.getOpRight().contains("2") && !allOprights.contains("2")) {
                                    allOprights += "2,";
                                }
                                if (right1.getOpRight().contains("3") && !allOprights.contains("3")) {
                                    allOprights += "3,";
                                }
                                if (right1.getOpRight().contains("4") && !allOprights.contains("4")) {
                                    allOprights += "4,";
                                }
                                if (allOprights != "") {
                                    allOprights = allOprights.substring(0, allOprights.length());//去掉最后一个,
                                }
                            }
                        }
                    }
                    if (right != null && right.getOpRight() != null) {
                        extName = StringUtil.subAfterString(tempFolFileName, ".");
                        if (tempFolFileName.contains(".") && exts.contains(extName.toLowerCase())) {
                            vi.setOpRight(right.getOpRight());
                        } else {
                            vi.setOpRight(allOprights);
                        }

                        if (right.getuId() != null) {
                            vi.setOprighter(right.getuId().toString());
                        } else {
                            if (view.getLinshiId() != null && view.getLinshiId().trim().length() > 0) {
                                vi.setOprighter(view.getLinshiId());
                                vi.setOpRight(allOprights);
                            } else {
                                vi.setOprighter("");
                            }
                        }
                    } else {
                        vi.setOpRight(allOprights);
                    }
                    vvv = vi;
                    views.add(vvv);
                }
                flag = true;
            }
        }


        if (views.size() > 0) {

            viewss = new ArrayList<DownloadView>();
            String exName;
            for (DownloadView vii : views) {
                exName = StringUtil.subAfterString(vii.getFolderOrFileName(), ".");
                if (vii.getFolderOrFileName() != null) {
                    if (!vii.getFolderOrFileName().contains(".") || !exts.contains(exName.toLowerCase())) {
                        viewss.add(vii);
                    }
                }
            }
            for (DownloadView vii : views) {
                exName = StringUtil.subAfterString(vii.getFolderOrFileName(), ".");
                if (vii.getFolderOrFileName() != null) {
                    if (vii.getFolderOrFileName().contains(".") && exts.contains(exName.toLowerCase())) {
                        viewss.add(vii);
                    }
                }
            }

            view.setPageSize(12);
            maxPage = viewss.size() % view.getPageSize() == 0 ? viewss.size() / view.getPageSize() : viewss.size() / view.getPageSize() + 1;
            viewss.get(0).setMaxPage(maxPage);//总页数
            viewss.get(0).setCurrentPage(1);//当前页 默认1
            viewss.get(0).setRecordCount(views.size());//总数

        }


        String str = null;
        if (viewss != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(viewss);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }


    /**
     * 功能描述:
     *
     * @auther: homey Wong
     * @date: 2019/1/22 0022 下午 6:37
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/findBackFoldersByQueryParam3")
    public void findBackFoldersByQueryParam3(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer lastIndex = null;
        String foldername = view.getFolderName();
        Integer index = null;

        //取上二级文件夹名 由下找下一层文件夹或文件
        if (!foldername.contains(".")) {
            for (String s : urls) {
                lastIndex = s.indexOf("/" + view.getFolderName() + "/");
                if (lastIndex != -1) {
                    String linshi1 = s.substring(0, lastIndex);
                    int linshilastIndex = linshi1.lastIndexOf("/");
                    String linshi2 = linshi1.substring(0, linshilastIndex);
                    foldername = StringUtil.subAfterString(linshi2, "/");
                    break;
                }
            }
            for (String str : urls) {
                index = str.indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = str.indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = str.length();
                }
                if (index != -1) {
                    folderOrFiles.add(str.substring(index + 2 + foldername.length(), lastIndex));
                }
            }


        } else {

            String filefoldername = null;
            String backFolderName = null;
            for (String str : urls) {
                if (str.contains(foldername)) {
                    filefoldername = str;
                }
            }

            if (filefoldername != null) {
                lastIndex = filefoldername.indexOf("/" + view.getFolderName());
                if (lastIndex > 0) {
                    String linshi1 = filefoldername.substring(0, lastIndex);
                    int linshilastIndex = linshi1.lastIndexOf("/");
                    String linshi2 = linshi1.substring(0, linshilastIndex);
                    foldername = StringUtil.subAfterString(linshi2, "/");
                }
            }

            for (String str : urls) {
                index = str.indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = str.indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = str.length();
                }
                if (index != -1) {
                    folderOrFiles.add(str.substring(index + 2 + foldername.length(), lastIndex));
                }
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }
        List<String> names = new ArrayList<String>();
        for (String s : norepeatFoldorFile) {
            if (!s.contains(".")) {
                names.add(s);
            }
        }
        for (String s : norepeatFoldorFile) {
            if (s.contains(".")) {
                names.add(s);
            }
        }


        names.add(view.getOrderNo());
        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(names);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }


    /**
     * 功能描述:文件夹退回上一级
     *
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 5:21
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/findBackFoldersByQueryParam")
    public void findBackFoldersByQueryParam(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view);
        //List<String> urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer lastIndex = null;
        String foldername = view.getFolderName();
        Integer index = null;

        //取上二级文件夹名 由下找下一层文件夹或文件
        if (!foldername.contains(".")) {
            for (String s : urls) {
                lastIndex = s.indexOf("/" + view.getFolderName() + "/");
                if (lastIndex != -1) {
                    String linshi1 = s.substring(0, lastIndex);
                    int linshilastIndex = linshi1.lastIndexOf("/");
                    String linshi2 = linshi1.substring(0, linshilastIndex);
                    foldername = StringUtil.subAfterString(linshi2, "/");
                    break;
                }
            }
            for (String str : urls) {
                index = str.indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = str.indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = str.length();
                }
                if (index != -1) {
                    folderOrFiles.add(str.substring(index + 2 + foldername.length(), lastIndex));
                }
            }


        } else {

            String filefoldername = null;
            String backFolderName = null;
            for (String str : urls) {
                if (str.contains(foldername)) {
                    filefoldername = str;
                }
            }

            if (filefoldername != null) {
                lastIndex = filefoldername.indexOf("/" + view.getFolderName());
                String linshi1 = filefoldername.substring(0, lastIndex);
                int linshilastIndex = linshi1.lastIndexOf("/");
                String linshi2 = linshi1.substring(0, linshilastIndex);
                foldername = StringUtil.subAfterString(linshi2, "/");
            }

            for (String str : urls) {
                index = str.indexOf("/" + foldername + "/");//字符串第一次出现的位置
                lastIndex = str.indexOf("/", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
                if (lastIndex == -1) {
                    lastIndex = str.length();
                }
                if (index != -1) {
                    folderOrFiles.add(str.substring(index + 2 + foldername.length(), lastIndex));
                }
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }

        List<String> names = new ArrayList<String>();
        for (String s : norepeatFoldorFile) {
            if (!s.contains(".")) {
                names.add(s);
            }
        }
        for (String s : norepeatFoldorFile) {
            if (s.contains(".")) {
                names.add(s);
            }
        }

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(names);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
                throw e;
            }
        }
    }


    /**
     * 功能描述:文件下载的双击文件夹任务
     *
     * @auther: homey Wong
     * @date: 2019/1/22 0022 下午 4:25
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value = "/findNextFoldersByQueryParam2")
    public void findNextFoldersByQueryParam2(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view);
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> norepeatFoFiPAIXU = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("/" + view.getFolderName() + "/");//字符串第一次出现的位置
            lastIndex = s.indexOf("/", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
            if (lastIndex == -1) {
                lastIndex = s.length();
            }
            if (index != -1) {
                folderOrFiles.add(s.substring(index + 2 + view.getFolderName().length(), lastIndex));
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }


        for (String s : norepeatFoldorFile) {
            if (!s.contains(".")) {
                norepeatFoFiPAIXU.add(s);
            }
        }
        for (String s : norepeatFoldorFile) {
            if (s.contains(".")) {
                norepeatFoFiPAIXU.add(s);
            }
        }

        norepeatFoFiPAIXU.add(view.getOrderNo());
        String str = null;
        if (norepeatFoFiPAIXU != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoFiPAIXU);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }


    /**
     * 功能描述:文件下载的双击文件夹任务
     *
     * @auther: homey Wong
     * @date: 2019/2/19  20:20      增加伪分页技术2-25
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value = "/findNextFoldersByQueryParam5DownView")
    public void findNextFoldersByQueryParam5DownView(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<FilemanUrl> urls = null;
        List<String> norepeatFoldorFile = null;
        List<String> exts = fileUploadAndDownServ.findAllExtension();
        List<String> folderOrFiles = null;
        Integer index = null;
        Integer lastIndex = null;
        DownloadView vi = null;
        FilemanRight right = null;
        FilemanRight right1 = null;
        List<DownloadView> views = null;
        List<DownloadView> viewss = null;
        view.setPageSize(12);
        boolean flag = true;
        int maxPage;
        String allOprights = "";
        String tempFolOrFileName = null;
        // if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {//有管理权限才进行如下操作
        views = new ArrayList<DownloadView>();
        urls = fileUploadAndDownServ.findAllUrlByOrderNoAndUid(view.getOrderNo(), view.getLinshiId());
        norepeatFoldorFile = new ArrayList<String>();
        folderOrFiles = new ArrayList<String>();
        for (FilemanUrl u : urls) {
            vi = fileUploadAndDownServ.findMessageByOrderNoandUid(view.getOrderNo(), view.getLinshiId());
            index = u.getLogur1().indexOf("/" + view.getFolderName() + "/");//字符串第一次出现的位置
            lastIndex = u.getLogur1().indexOf("/", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
            if (lastIndex == -1) {
                lastIndex = u.getLogur1().length();
            }
            if (index != -1) {
                String linshiId = view.getLinshiId();
                if (linshiId == null || linshiId.trim().length() <= 0) {
                    view.setuId(0);
                } else {
                    view.setuId(Integer.valueOf(linshiId.trim()));
                }
                right = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(u.getId(), u.getFileInfoId(), view.getuId());
                tempFolOrFileName = (u.getLogur1().substring(index + 2 + view.getFolderName().length(), lastIndex));
                for (DownloadView v : views) {
                    if (v.getFolderOrFileName().equals(tempFolOrFileName)) {
                        flag = false;
                    }
                }
                if (flag) {
                    vi.setFolderOrFileName(tempFolOrFileName);
                    String extName = "";
                    if (tempFolOrFileName.contains(".")) {
                        extName = StringUtil.subAfterString(tempFolOrFileName, ".");
                    }
                    if (!tempFolOrFileName.contains(".") || !exts.contains(extName.toLowerCase())) { //文件夹
                        allOprights = "";
                        for (FilemanUrl uu : urls) {
                            if (uu.getLogur1().contains("/" + tempFolOrFileName + "/"))
                                right1 = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(uu.getId(), uu.getFileInfoId(), view.getuId());
                            if (right1 != null && right1.getOpRight() != null) {
                                if (right1.getOpRight().contains("2") && !allOprights.contains("2")) {
                                    allOprights += "2,";
                                }
                                if (right1.getOpRight().contains("3") && !allOprights.contains("3")) {
                                    allOprights += "3,";
                                }
                                if (right1.getOpRight().contains("4") && !allOprights.contains("4")) {
                                    allOprights += "4,";
                                }
                                if (allOprights != "") {
                                    allOprights = allOprights.substring(0, allOprights.length());//去掉最后一个,
                                }
                            }
                        }
                    } else { //文件
                        allOprights = "";
                        for (FilemanUrl uu : urls) {
                            if (uu.getLogur1().contains(tempFolOrFileName)) {
                                right1 = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(uu.getId(), uu.getFileInfoId(), view.getuId());
                                if (right1 != null && right1.getOpRight() != null) {
                                    if (right1.getOpRight().contains("2") && !allOprights.contains("2")) {
                                        allOprights += "2,";
                                    }
                                    if (right1.getOpRight().contains("3") && !allOprights.contains("3")) {
                                        allOprights += "3,";
                                    }
                                    if (right1.getOpRight().contains("4") && !allOprights.contains("4")) {
                                        allOprights += "4,";
                                    }
                                    if (allOprights != "") {
                                        allOprights = allOprights.substring(0, allOprights.length());//去掉最后一个,
                                    }
                                }
                            }
                        }
                    }
                    if (right != null && right.getOpRight() != null) {
                        if (tempFolOrFileName.contains(".") && exts.contains(extName.toLowerCase())) {
                            vi.setOpRight(right.getOpRight());
                        } else {
                            vi.setOpRight(allOprights);
                        }

                        if (right.getuId() != null) {
                            vi.setOprighter(right.getuId().toString());
                        } else {
                            vi.setOprighter("");
                        }
                    } else {
                        vi.setOpRight(allOprights);
                    }
                    views.add(vi);
                }
                flag = true;
            }
        }
        //}
        if (views.size() > 0) {
            viewss = new ArrayList<DownloadView>();
            String extName = "";
            for (DownloadView vii : views) {
                extName = StringUtil.subAfterString(vii.getFolderOrFileName(), ".");
                if (!vii.getFolderOrFileName().contains(".") || !exts.contains(extName.toLowerCase())) {
                    viewss.add(vii);
                }
            }
            for (DownloadView vii : views) {
                extName = StringUtil.subAfterString(vii.getFolderOrFileName(), ".");
                if (vii.getFolderOrFileName().contains(".") && exts.contains(extName.toLowerCase())) {
                    viewss.add(vii);
                }
            }
            maxPage = viewss.size() % view.getPageSize() == 0 ? viewss.size() / view.getPageSize() : viewss.size() / view.getPageSize() + 1;
            viewss.get(0).setMaxPage(maxPage);//总页数
            viewss.get(0).setCurrentPage(view.getCurrentPage());//当前页 默认1
            viewss.get(0).setRecordCount(viewss.size());//总数
        }

        String str = null;
        if (viewss != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(viewss);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
                throw e;
            }
        }

    }


    /**
     * 功能描述:文件下载的双击文件夹任务
     *
     * @auther: homey Wong
     * @date: 2019/2/19  20:20
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value = "/findNextFoldersByQueryParam5")
    public void findNextFoldersByQueryParam5(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByOrderNo2(view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("/" + view.getFolderName() + "/");//字符串第一次出现的位置
            lastIndex = s.indexOf("/", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
            if (lastIndex == -1) {
                lastIndex = s.length();
            }
            if (index != -1) {
                folderOrFiles.add(s.substring(index + 2 + view.getFolderName().length(), lastIndex));
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }


        norepeatFoldorFile.add(view.getOrderNo());
        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }

    /**
     * 功能描述:按当前文件级查找下一级文件夹
     *
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 4:33
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value = "/findNextFoldersByQueryParam")
    public void findNextFoldersByQueryParam(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        // List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("/" + view.getFolderName() + "/");//字符串第一次出现的位置
            lastIndex = s.indexOf("/", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
            if (lastIndex == -1) {
                lastIndex = s.length();
            }
            if (index != -1) {
                folderOrFiles.add(s.substring(index + 2 + view.getFolderName().length(), lastIndex));
            }
        }

        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }
        List<String> names = new ArrayList<String>();
        for (String s : norepeatFoldorFile) {
            if (!s.contains(".")) {
                names.add(s);
            }
        }
        for (String s : norepeatFoldorFile) {
            if (s.contains(".")) {
                names.add(s);
            }
        }

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(names);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * 功能描述:按条件查询初始化文件夹
     *
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 4:33
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/findFoldersByQueryParam")
    public void showFolderByParamThree(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> folderOrFiles = new ArrayList<String>();
        List<String[]> strarray = new ArrayList<String[]>();
        List<String> norepeatFoldorFile = new ArrayList<String>();
        if (view.getuId() != null) {
            view.setEngineer(view.getuId().toString());
        }

        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
        List<String> newurls = new ArrayList<String>();
        int pointindex = 0;
        for (String str : urls) {
            pointindex = StringUtils.ordinalIndexOf(str, "/", 4);
            newurls.add(str.substring(pointindex + 1, str.length()));
        }

        for (String str : newurls) {
            //strarray.add(str.replaceAll("\\\\", "/").split("/"));
            strarray.add(str.split("/"));
        }
        for (String[] stra : strarray) {
            folderOrFiles.add(stra[0]);
        }
        for (String s : folderOrFiles) {
            if (!norepeatFoldorFile.contains(s)) {
                norepeatFoldorFile.add(s);
            }
        }

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }

    /**
     * 功能描述:根据输入的订单号，检查是否重复。（数据库里的订单必须保证独一无二，后继操作才能按程序设计走）
     *
     * @auther: homey Wong
     * @date: 2019/2/25 0025 下午 12:05
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value = "/isSingleNORepeatOrderNo", method = RequestMethod.POST)
    public void isSingleNORepeatOrderNo(@RequestBody(required = true) DownloadView view, HttpServletResponse response) throws Exception {
        List<DownloadView> views = new ArrayList<DownloadView>();
        DownloadView vi = fileUploadAndDownServ.findOrderNobyOrderNo(view.getOrderNo());
        if (vi != null) {
            view.setFlag("-159");
        }
        views.add(view);
        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(views);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 功能描述:按条件查询  管理模块
     *
     * @auther: homey Wong
     * @date: 2019/2/20 0020 下午 2:56
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/findFoldersByQueryParamNew")
    public void showFolderByParamThreeNew(@RequestBody(required = true) DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<String> folderOrFiles = null;
        List<String[]> strarray = null;
        List<String> norepeatFoldorFile = null;
        List<DownloadView> views = null;
        DownloadView vi = null;
        List<String> urls = null;
        List<String> newurls = null;
        List<DownloadView> viewsNew = null;
        int pointindex = 0;
        int maxPage = 0;
        int recordCount = 0;
        //if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {
        view.setPageSize(12);
        viewsNew = new ArrayList<DownloadView>();
        views = new ArrayList<DownloadView>();
        folderOrFiles = new ArrayList<String>();
        strarray = new ArrayList<String[]>();
        norepeatFoldorFile = new ArrayList<String>();
        //if (view.getCurrentPageTotalNum() >= 0) {
        views = fileUploadAndDownServ.findAllUrlByParamThreeNew2(view);
        recordCount = fileUploadAndDownServ.findAllUrlByParamThreeNew2Count(view);
        // }
        maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        newurls = new ArrayList<String>();
        for (DownloadView v : views) {
            pointindex = StringUtils.ordinalIndexOf(v.getUrlAddr(), "/", 4);
            String[] b = v.getUrlAddr().substring(pointindex + 1, v.getUrlAddr().length()).split("/");
            v.setFolderOrFileName(b[0]);
            viewsNew.add(v);
        }
        if (viewsNew.size() > 0) {
            viewsNew.get(0).setMaxPage(maxPage);
            viewsNew.get(0).setRecordCount(recordCount);
            viewsNew.get(0).setUserName(userInfo.getUserName());
            viewsNew.get(0).setPassword(userInfo.getUserPwd());
            viewsNew.get(0).setCurrentPage(view.getCurrentPage());
        }

        // }
        String str = null;
        if (viewsNew != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(viewsNew);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.22
     */

    @ResponseBody
    @RequestMapping(value = "/updateandsaveprivilegestatus")
    public void updateAndSavePrivilegeStatus(@RequestBody(required = true) AjaxBean bean, HttpServletResponse response, HttpSession session) throws Exception {
        UserInfo info = (UserInfo) session.getAttribute("account");
        DownloadView viewHtml = bean.getView();
        DownloadView view = null;
        viewHtml.setPageSize(12);
        List<String> privilelist = bean.getPrivilegestrs();//权限
        List<String> privilegeusers = bean.getPrivilegeusers();//用户
        String oprighter = viewHtml.getOprighter();//权限被修改者 空为所有人
        List<DownloadView> views = null;//集装所有权限设置 订单编号 权限的相应文件夹或文件名  被权限者  被权限者的权限标记 1上传 2.更新 3.下载  4删除
        String orderNo = null;
        String fileOrFolderName = null;
        String opright = null;
        int index;
        int recordCount = 0;
        int maxPage = 0;
        try {
            if (info.getUserActor() == 2 || info.getUserActor() == 1) {
                views = new ArrayList<DownloadView>();
                String tempStr = null;
                for (int i = 0; i < privilelist.size(); i++) {
                    if (privilelist.get(i).contains(":")) {//代表有权限标记
                        index = privilelist.get(i).indexOf(":");
                        tempStr = privilelist.get(i).substring(0, index);
                        orderNo = tempStr.substring(0, 17);
                        fileOrFolderName = tempStr.substring(17, index);
                        opright = privilelist.get(i).substring(index + 1, privilelist.get(i).length());
                    } else {
                        orderNo = privilelist.get(i).substring(0, 17);
                        fileOrFolderName = privilelist.get(i).substring(17, privilelist.get(i).length());
                        opright = "";
                    }
                    view = new DownloadView();
                    view.setOrderNo(orderNo);
                    view.setFolderOrFileName(fileOrFolderName);
                    view.setOpRight(opright);
                    views.add(view);
                }

                if (views.size() > 0)
                    fileUploadAndDownServ.saveOrUpdateFilePrivilege(views, privilegeusers, info);

                views = fileUploadAndDownServ.findAllUrlByParamThreeNew2(viewHtml);
                recordCount = fileUploadAndDownServ.findAllUrlByParamThreeNew2Count(viewHtml);
                maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
                List<DownloadView> viewsNew = new ArrayList<DownloadView>();
                int pointindex;
                for (DownloadView v : views) {
                    pointindex = StringUtils.ordinalIndexOf(v.getUrlAddr(), "/", 4);
                    String[] b = v.getUrlAddr().substring(pointindex + 1, v.getUrlAddr().length()).split("/");
                    v.setFolderOrFileName(b[0]);
                    viewsNew.add(v);

                }
                //int recordCount = fileUploadAndDownServ.findAllFilesByCondParamCount(view);
                // int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        if (views != null && views.size() > 0) {
            views.get(0).setMaxPage(maxPage);
            views.get(0).setRecordCount(recordCount);
            views.get(0).setCurrentPage(view.getCurrentPage());
            views.get(0).setPageSize(12);
        }

        String str1 = null;
        if (views != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(views);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str1); //返回前端ajax
            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }

    @RequestMapping(value = "/showcookies")
    public ModelAndView showcookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        String cookievalue = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("abc")) {
                    cookievalue = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        return null;
    }

    /**
     * 功能描述:删除
     *
     * @auther: homey Wong
     * @date: 2019/3/22  上午 9:39
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/deleteFileBySelect")
    public void deleteFileBySelect(@RequestBody(required = true) String checkval, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        List<DownloadView> vs = JSON.parseArray(checkval, DownloadView.class);
        UserInfo info = (UserInfo) session.getAttribute("account");
        List<DownloadView> views = new ArrayList<DownloadView>();
        List<DownloadView> deleteViews = new ArrayList<DownloadView>();
        File file = null;
        List<File> files = new ArrayList<File>();
        String flag = "OK";

        //获取所有URL(DOWNLOADVIEW)集装,查看有没有权限 //集装所有URL
        boolean isDeleteRight = true;
        int index = 0;
        a:
        for (DownloadView view : vs) {
            views = fileUploadAndDownServ.findAllUrlByOrderNoAndUid1(view.getOrderNo(), info.getuId());
            for (DownloadView vi : views) {
                if (view.getFolderOrFileName().contains(".")) {//代表是文件
                    index = vi.getUrlAddr().indexOf(view.getFolderOrFileName());
                    if (index > 0) {
                        if (vi.getOpRight() == null || !vi.getOpRight().contains("4")) {
                            isDeleteRight = false;
                            break a;
                        }
                        file = new File(vi.getUrlAddr());
                        files.add(file);
                        deleteViews.add(vi);
                    }
                } else {//代表是文件夹
                    index = vi.getUrlAddr().indexOf("/" + view.getFolderOrFileName() + "/");
                    if (index > 0) {
                        if (vi.getOpRight() == null) {
                            isDeleteRight = false;
                            break a;
                        } else {
                            if (!vi.getOpRight().contains("4")) {
                                isDeleteRight = false;
                                break a;
                            }
                        }
                        file = new File(vi.getUrlAddr());
                        files.add(file);
                        deleteViews.add(vi);
                    }
                }
            }
            if (views == null || views.size() == 0) {
                flag = "-258";
            }

        }
        //返回

        if (isDeleteRight) {
            boolean isLarge = FileUtil.checkDownloadFileSize(files, 2, "G");
            if (!isLarge) {
                flag = "-1";
            }
            if (deleteViews.size() > 200) {//代表文件超过200个
                flag = "-369";
            }
            if (flag.equals("OK")) {
                fileUploadAndDownServ.deleteByHeadIdAndItemId(deleteViews, files);
            }

        } else {
            flag = "-258";
        }

        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(flag);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody  //高速压缩下载  2019.6.20
    @RequestMapping(value = "/downloadfileorfolderforzipCheck")
    public void downloadfileorfolderforzipCheck(@RequestBody(required = true) String checkval, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        List<DownloadView> vs = JSON.parseArray(checkval, DownloadView.class);
        UserInfo info = (UserInfo) session.getAttribute("account");
        List<DownloadView> views = new ArrayList<DownloadView>();
        List<String> exts = fileUploadAndDownServ.findAllExtension();
        List<String> urls = new ArrayList<String>();
        DownloadView vie = new DownloadView();
        File file = null;
        List<File> files = new ArrayList<File>();
        List<FilemanDownRecord> records = new ArrayList<FilemanDownRecord>();
        FilemanDownRecord record = null;
        //获取所有URL(DOWNLOADVIEW)集装,查看有没有权限 //集装所有URL
        boolean isDownRight = true;
        int index = 0;
        String extName = "";
        a:
        for (DownloadView view : vs) {
            views = fileUploadAndDownServ.findAllUrlByOrderNoAndUid1(view.getOrderNo(), info.getuId());
            if (view.getFolderOrFileName().contains(".")) {
                extName = StringUtil.subAfterString(view.getFolderOrFileName(), ".");
            }
            for (DownloadView vi : views) {
                if (view.getFolderOrFileName().contains(".") && exts.contains(extName.toLowerCase())) {//代表是文件
                    index = vi.getUrlAddr().indexOf(view.getFolderOrFileName());
                    if (index > 0) {
                        if (!vi.getOpRight().contains("3")) {
                            isDownRight = false;
                            break a;
                        }
                        urls.add(this.finalDirPath + vi.getUrlAddr());
                        record = new FilemanDownRecord();
                        record.setDownDate(new Date());
                        record.setDownFullName(info.getUserName());
                        record.setDownUid(info.getuId());
                        record.setFileName(vi.getFileName());
                        record.setFileurlid(vi.getFileUrlId());
                        record.setOrderNum(vi.getOrderNo());
                        record.setProjectName(vi.getProjectName());
                        record.setIpAddr(CusAccessObjectUtil.getIpAddress(request));
                        record.setIpName("");
                        file = new File(vi.getUrlAddr());
                        records.add(record);
                        files.add(file);
                    }
                } else {//代表是文件夹
                    index = vi.getUrlAddr().indexOf("/" + view.getFolderOrFileName() + "/");
                    if (index > 0) {
                        if (vi.getOpRight() == null || vi.getOpRight() == "") {
                            isDownRight = false;
                            break a;
                        } else {
                            if (!vi.getOpRight().contains("3")) {
                                isDownRight = false;
                                break a;
                            }
                        }
                        urls.add(this.finalDirPath + vi.getUrlAddr());
                        file = new File(vi.getUrlAddr());
                        record = new FilemanDownRecord();
                        record.setDownDate(new Date());
                        record.setDownFullName(info.getUserName());
                        record.setDownUid(info.getuId());
                        record.setFileName(vi.getFileName());
                        record.setFileurlid(vi.getFileUrlId());
                        record.setOrderNum(vi.getOrderNo());
                        record.setProjectName(vi.getProjectName());
                        record.setIpAddr(CusAccessObjectUtil.getIpAddress(request));
                        record.setIpName("");
                        file = new File(vi.getUrlAddr());
                        records.add(record);
                        files.add(file);
                    }
                }
            }
            if (views == null || views.size() == 0) {
                vie.setFlag("-258");
                isDownRight = false;
                break a;
            }
        }
        //返回

        if (isDownRight) {
            boolean isLarge = FileUtil.checkDownloadFileSize(files, 2, "G");
            if (!isLarge) {
                vie.setFlag("-1");//文件太大
            }
            if (urls.size() > 200) {//代表文件超过200个
                vie.setFlag("-369");
            }
            if (isLarge && urls.size() <= 200) {
                fileUploadAndDownServ.saveFileDownRecords(records);
            }

        } else {
            vie.setFlag("-258");
        }
        if (vie.getFlag() != null) {
            urls.add(vie.getFlag());
        }
        String str = null;
        if (urls != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(urls);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }


    @ResponseBody  //高速压缩下载  2019.6.20
    @RequestMapping(value = "/listenDownload")
    public void listenDownload() throws Exception {

    }

    @ResponseBody  //高速压缩下载  2019.6.20
    @RequestMapping(value = "/downloadfileorfolderforzip")
    public void downloadfileorfolderforzip(String urlsStr, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            System.out.println("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("downloadstatus")) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:" + cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        String[] urlss = urlsStr.split("\\*");
        List<String> urls = new ArrayList<String>();
        for (String str : urlss) {
            urls.add(str);
        }
        String filename = new Date().getTime() + ".zip";
        final File result = new File(this.finalDirPath + "linshi/" + filename);
        new HighEffiCompressZipTest().createZipFile(urls, result);
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
            byte[] buff = new byte[1024];
            outputStream = response.getOutputStream();
            FileInputStream fis = new FileInputStream(result);
            bufferedInputStream = new BufferedInputStream(fis);
            int num = bufferedInputStream.read(buff);
            Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
            cookie.setMaxAge(5 * 60);// 设置为30min
            cookie.setPath("/");
            response.addCookie(cookie);
            while (num != -1) {
                outputStream.write(buff, 0, num);
                outputStream.flush();
                num = bufferedInputStream.read(buff);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
    }


    /**
     * 功能描述:跳转管理页面
     *
     * @auther: homey Wong
     * @date: 2018/12/27 0027 上午 10:07
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/toprivilmanagepage", method = RequestMethod.GET)
    public ModelAndView toPrivilManagePage(HttpSession session, int currentPage, int pageSize) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView modelAndView = new ModelAndView("privilegemanagepage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        view.setPageSize(pageSize);
        view.setType(userInfo.getType());
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUserOnlyDesigner();
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        List<DownloadView> views = new ArrayList<DownloadView>();
        int recordCount;
        int maxPage;

        //查看所有人的权限
        //  if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {
        views = fileUploadAndDownServ.findAllUploadFileByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
        //查看所有人权限总数
        recordCount = fileUploadAndDownServ.findAllUploadFileCountByUserId(userInfo.getuId());
        maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        view.setCurrentPage(view.getCurrentPage());
        //  } else {
        //view.setFlag("-1");//代表没有权限
        // }
        view.setFullName(userInfo.getFullName());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("extensionList", extensionList);
        modelAndView.addObject("views", views);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/findfileurlbyconditionparam", method = RequestMethod.POST)
    public void findFileUrlByConditionParam(@RequestBody DownloadView view, HttpSession
            session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<DownloadView> dataList = fileUploadAndDownServ.findAllFilesByCondParam(view);
        int recordCount = fileUploadAndDownServ.findAllFilesByCondParamCount(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        if (dataList.size() > 0) {
            dataList.get(0).setMaxPage(maxPage);
            dataList.get(0).setRecordCount(recordCount);
            dataList.get(0).setUserName(userInfo.getUserName());
            dataList.get(0).setPassword(userInfo.getUserPwd());
            dataList.get(0).setCurrentPage(view.getCurrentPage());
        }
        String str1 = null;
        if (dataList != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(dataList);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str1); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21 18:18
     * 跳转到主页面
     */
    @ResponseBody
    @RequestMapping(value = "/tomainpage", method = RequestMethod.GET)
    public ModelAndView goPrivilegeManagePage(String flag, HttpServletRequest
            request, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView("uploadpage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setFlag(flag);
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("extensionList", extensionList);
        return modelAndView;
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21  15:21
     * 下载任务
     */
    @ResponseBody
    @RequestMapping("/downloadfile")
    public void downloadFile(HttpServletResponse resp, String urlname, String filename)
            throws IOException {
        String filePath = urlname;
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
        byte[] buff = new byte[1024];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();

            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fis);
            int num = bufferedInputStream.read(buff);
            while (num != -1) {
                outputStream.write(buff, 0, num);
                outputStream.flush();
                num = bufferedInputStream.read(buff);
            }
        } catch (IOException e) {
            logger.debug(e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
        }
    }


    /**
     * @author:homey Wong
     * @date:2018.12.21 12/29 做分页
     */
    //默认下载清单(所有文件夹)
    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView toFileDownPage(int currentPage, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("downloadpage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");//查看权限用
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUserOnlyDesigner();
        List<String> orderNumFolders = fileUploadAndDownServ.findAllOrderNum(view.getCurrentPageTotalNum(), view.getPageSize());
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        int recordCount = fileUploadAndDownServ.findAllOrderNumCount();
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        mav.addObject("orderNumFolders", orderNumFolders);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        mav.addObject("view", view);
        mav.addObject("employees", employees);
        mav.addObject("userInfos", userInfos);
        mav.addObject("extensionList", extensionList);
        return mav;
    }


    /**
     * 功能描述:删除主页面
     *
     * @auther: homey Wong
     * @date: 2019/3/22  上午 8:59
     * @param:
     * @return:
     * @describtion
     */
    //默认下载清单(所有文件夹)
    @ResponseBody
    @RequestMapping(value = "/todeletepage", method = RequestMethod.GET)
    public ModelAndView toDeletePage(int currentPage, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("deletepage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");//查看权限用
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUserOnlyDesigner();
        List<String> orderNumFolders = fileUploadAndDownServ.findAllOrderNum(view.getCurrentPageTotalNum(), view.getPageSize());
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        int recordCount = fileUploadAndDownServ.findAllOrderNumCount();
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        mav.addObject("orderNumFolders", orderNumFolders);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        mav.addObject("view", view);
        mav.addObject("employees", employees);
        mav.addObject("userInfos", userInfos);
        mav.addObject("extensionList", extensionList);
        return mav;
    }


    /**
     * 功能描述:根据订单编号业务员名和设计师查询出URL信息
     *
     * @auther: homey Wong
     * @date: 2019/1/14 0014 上午 9:19
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/showfileurldiv", method = RequestMethod.POST)
    public void showFileUrlDiv(@RequestBody DownloadView view, HttpSession session, HttpServletResponse
            response) throws
            Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setuId(userInfo.getuId());
        List<DownloadView> fileUrlList = fileUploadAndDownServ.findFileUrlDatabyOrderNoandSalorandUserName(view);
        String str1 = null;
        if (fileUrlList.size() > 0) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(fileUrlList);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str1); //返回前端ajax
            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }

    @RequestMapping(value = "/checkupdatemessagefolder", method = RequestMethod.POST)
    public void checkUpdateMessageFolder(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setuId(userInfo.getuId());
        String returnMessage = "OK";
        returnMessage = fileUploadAndDownServ.checkFileUpdateRight(view.getFilePathName(), view, userInfo);
        if (returnMessage.equals("OK")) {//有权限进行下一步操作
            returnMessage = fileUploadAndDownServ.checkFileisSame1(view, userInfo, view.getFilePathName());
            if (returnMessage.equals("OK")) {
                returnMessage = fileUploadAndDownServ.checkIsExistFilesFolderforUpdate(view.getFilePathName(), view, userInfo);
            }
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnMessage);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @RequestMapping(value = "/checkupdatemessage", method = RequestMethod.POST)
    public void checkUpdateMessage(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        String returnMessage = "OK";
        returnMessage = fileUploadAndDownServ.checkFileUpdateRight(view.getFilePathName(), view, userInfo);
        if (returnMessage.equals("OK")) {
            returnMessage = fileUploadAndDownServ.checkFileisSame1(view, userInfo, view.getFilePathName());
            if (returnMessage.equals("OK")) {
                returnMessage = fileUploadAndDownServ.checkIsExistFilesforUpdate(view.getFilePathName(), view, userInfo);
            }
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnMessage);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @RequestMapping(value = "/checkmessagefolder")
    public void checkMessageFolder(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        FileManFileInfo info = null;
        FolderUpdateList ful = new FolderUpdateList();
        List<FolderUpdateList> fuls = new ArrayList<FolderUpdateList>();
        if (userInfo.getUseruploadright() == 1) {
            boolean isFolderNameForEngDateOrderNoSalor = true;
            isFolderNameForEngDateOrderNoSalor = fileUploadAndDownServ.isFolderNameForEngDateOrderNoSalor(view.getFilePathName());//文件夹名验证，文件夹名不能有订单，业务员，设计师命名
            if (isFolderNameForEngDateOrderNoSalor) {
                info = fileUploadAndDownServ.getFileInfoByOrderNo(view.getOrderNo());
                if (info != null) {
                    if (info.getExtInfo1().equals(view.getSalor()) && info.getuId().equals(view.getuId())) {//查看订单编号有没有被占用  原始设计师与现设计师相同，原业务员与现业务员相同，则可存储，否则不可存储
                        //0710修改
                        ful = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, view.getFilePathName());//同一订单下文件夹重名验证
                    } else {
                        ful.setIsSameFileUploadFolderName("您输入的订单编号系统中已存在，请另换一个订单号!" +
                                "或检查您填写的订单信息!");

                    }
                } else {
                    //0710修改
                    ful = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, view.getFilePathName());//同一订单下文件夹重名验证
                }
            } else {
                ful.setIsSameFileUploadFolderName("您的文件夹名有问题,不能以设计师名," +
                        "订单编号,业务员,日期作为命名!");
            }
        } else {
            ful.setIsSameFileUploadFolderName("您没有上传权限!");
        }
        List<String> allNewPath = new ArrayList<String>();
        String[] arr;

        if (ful != null && ful.getUrlAfterUpdateForNoRepeat() != null && ful.getUrlAfterUpdateForNoRepeat().size() > 0) {
            for (int i = 0; i < ful.getUrlAfterUpdateForNoRepeat().size(); i++) {
                arr = ful.getUrlAfterUpdateForNoRepeat().get(i);
                allNewPath.add(StringUtils.join(arr, "/").replace(",", "*"));
            }
        }
        ful.setAllPath(allNewPath);
        fuls.add(ful);
        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(fuls);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    @RequestMapping(value = "/checkmessage")
    public void checkMessage(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        FolderUpdateList ful = new FolderUpdateList();
        List<FolderUpdateList> fuls = new ArrayList<FolderUpdateList>();
        boolean isExsitFileName = false;
        FileManFileInfo info = null;
        if (userInfo.getUseruploadright() == 1) {//有无权限
            ful = fileUploadAndDownServ.checkFileisSame(view, userInfo, view.getFilePathName());//判断是否有重名的文件名
            //上传的文件或文件夹有无重复，有无存在
            if (ful.getIsSameFileUploadFolderName().equals("OK")) {
                info = fileUploadAndDownServ.getFileInfoByOrderNo(view.getOrderNo());
                if (info != null) {
                    if (!info.getExtInfo1().equals(view.getSalor())) {
                        ful.setIsSameFileUploadFolderName("您输入的订单系统中已存在，请另换一个订单号!或检查您填写的订单信息!");
                    }
                }
            }

        } else {
            ful.setIsSameFileUploadFolderName("您没有上传权限，如需要请向上级申请!");
        }

        fuls.add(ful);
        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(fuls);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 功能描述:按条件查询需下载的文件
     *
     * @auther: homey Wong
     * @date: 2019/1/2 0002 下午 5:27
     * @param:
     * @return:findFoldersByQueryParamNew
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/downloadbyquerycondition", method = RequestMethod.POST)
    public void downloadByQueryCondition(@RequestBody DownloadView view, HttpSession
            session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<DownloadView> views = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
        int recordCount = fileUploadAndDownServ.findAllUrlByParamManyOrNoCount(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        if (views != null && views.size() > 0) {
            views.get(0).setMaxPage(maxPage);
            views.get(0).setRecordCount(recordCount);
            views.get(0).setUserName(userInfo.getUserName());
            views.get(0).setPassword(userInfo.getUserPwd());
            views.get(0).setCurrentPage(view.getCurrentPage());
            views.get(0).setPassword(userInfo.getUserPwd());
            views.get(0).setCurrentPage(view.getCurrentPage());
        }

        String str = null;
        if (views.size() > 0) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(views);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
    }


    /**
     * 功能描述:
     *
     * @auther: homey Wong
     * @date: 2019/3/22  上午 11:30
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/deletebyquerycondition", method = RequestMethod.POST)
    public void deletebyquerycondition(@RequestBody DownloadView view, HttpSession
            session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<DownloadView> views = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
        int recordCount = fileUploadAndDownServ.findAllUrlByParamManyOrNoCount(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        if (views != null && views.size() > 0) {
            views.get(0).setMaxPage(maxPage);
            views.get(0).setRecordCount(recordCount);
            views.get(0).setUserName(userInfo.getUserName());
            views.get(0).setPassword(userInfo.getUserPwd());
            views.get(0).setCurrentPage(view.getCurrentPage());
            views.get(0).setPassword(userInfo.getUserPwd());
            views.get(0).setCurrentPage(view.getCurrentPage());
        }

        String str = null;
        if (views.size() > 0) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(views);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(str); //返回前端ajax
            } catch (Exception e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }

    /**
     * @author:homey Wong
     * @date:2018.12.20
     */
    //多文件上传
    @ResponseBody
    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    public ModelAndView toFileUpAndDownPage(HttpSession session, @ModelAttribute(value = "view") DownloadView
            view,
                                            @RequestParam("file") MultipartFile[] files, Model model) throws Exception {
        List<MultipartFile> fileArray = new ArrayList<MultipartFile>();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
//        if (userInfo.getUseruploadright() == 1) {
//            for (MultipartFile mfile : files) {
//                fileArray.add(mfile);
//            }
        //boolean isFileLarge = FileUtil.checkFileSize(fileArray, 1024, "M");//判断文件是否超过限制大小
//            boolean isExsitFileName = fileUploadAndDownServ.checkFileisSame(view, userInfo, null);//判断是否有重名的文件名
//            if (!isExsitFileName) {//没超过并没有重复的名字并且单次上传不超过200个文件
//                view = fileUploadAndDownServ.findIsExistFiles(fileArray, view, userInfo);
//                //  view = fileUploadAndDownServ.addFilesData(view, fileArray, userInfo);
//            } else {
////                if (!isFileLarge) {
////                    view.setFlag("-2");//超过
////                }
//                if (isExsitFileName) {
//                    view.setFlag("-222");//有重复的名字
//                }
////                if (fileArray.size() >= 200) {
//////                    view.setFlag("-369");
//////                }
//            }
//
//        } else {
//            view.setFlag("-258");
//        }
        return new ModelAndView("uploadpage");

    }

    /**
     * 功能描述:跳转到更新页面
     * @auther: homey Wong
     * @date: 2019/1/11 0011 上午 9:18
     * @param:
     * @return:
     * @describtion
     */
    /**
     * @author:homey Wong
     * @Date: 2018.12.21 18:18
     * 跳转到主页面
     */
    @ResponseBody
    @RequestMapping(value = "/tomodifypage", method = RequestMethod.GET)
    public ModelAndView toModifyPage(HttpSession session, String flag, HttpServletRequest
            request) throws
            Exception {
        ModelAndView modelAndView = new ModelAndView("modifypage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<Employee> employees = fileUploadAndDownServ.findAllSalorByDeptName();
        List<String> extensionLists = fileUploadAndDownServ.findAllExtension();
        JSONArray extensionList = JSONArray.fromObject(extensionLists.toArray());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        view.setFlag(flag);
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("extensionList", extensionList);
        return modelAndView;
    }

    /**
     * 功能描述:文件更新修改 即覆盖
     *
     * @auther: homey Wong
     * @date: 2019/1/11 0011 上午 9:01
     * @param:
     * @return:
     * @describtion
     */
    //文件更新
    @ResponseBody
    @RequestMapping(value = "/modifypage1", method = RequestMethod.POST)
    public ModelAndView modifyPage(HttpSession session, @ModelAttribute(value = "view") DownloadView view,
                                   @RequestParam("file") MultipartFile[] files, Model model) throws Exception {
        try {
            List<MultipartFile> fileArray = new ArrayList<MultipartFile>();
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setuId(userInfo.getuId());
            for (MultipartFile mfile : files) {
                fileArray.add(mfile);
            }
//            boolean isAllFileUpdateRight = fileUploadAndDownServ.checkFileUpdateRight(null, view, userInfo);
//            if (isAllFileUpdateRight) {
//                boolean isFileLarge = FileUtil.checkFileSize(fileArray, 1024, "M");//判断文件是否超过限制大小
//                if (isFileLarge) {//没超过
//                    view = fileUploadAndDownServ.findIsExistFilesforUpdate(fileArray, view, userInfo);
//                } else {
//                    view.setFlag("-2");//超过
//                }
//            } else {
//                view.setFlag("-258");
//            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
        return new ModelAndView("modifypage");

    }


    /**
     * 功能描述:文件夹更新修改 即覆盖
     *
     * @auther: homey Wong
     * @date: 2019/1-16 12:10
     * @param:
     * @return:
     * @describtion
     */
    //文件夹更新
    @ResponseBody
    @RequestMapping(value = "/modifypagefolder1", method = RequestMethod.POST)
    public ModelAndView modifyPageFolder(HttpServletRequest
                                                 request, @ModelAttribute(value = "view") DownloadView
                                                 view, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        //查看要更新的所有文件有没有权限
//        boolean isAllFileUpdateRight = fileUploadAndDownServ.checkFileUpdateRight(null, view, userInfo);
//        if (isAllFileUpdateRight) {
//            boolean isFileLarge = FileUtil.checkFileSize(files, 1024, "M");
//            if (isFileLarge) {
//                view = fileUploadAndDownServ.findIsExistFilesFolderforUpdate(files, view, userInfo);
//            } else {
//                view.setFlag("-2");
//            }
//        } else {
//            view.setFlag("-258");
//        }

        return new ModelAndView("modifypage");


    }

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "/checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        if (processingObj == null) {
            return new ResultVo(ResultStatus.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing) {
            return new ResultVo(ResultStatus.IS_HAVE, value);
        } else {
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            List<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
            return new ResultVo<>(ResultStatus.ING_HAVE, missChunkList);
        }
    }

    @RequestMapping(value = "/saveFolderMessage", method = RequestMethod.POST)
    @ResponseBody
    public void saveFolderMessage(DownloadView view, HttpSession session) throws Exception {
        try {
             Runtime.getRuntime().exec("chmod 755 -R /opt/ftpserver");
            logger.debug("没发生错误");
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            view.setFilePathName(view.getFilePathName().replace("*", ","));
            view.setFilePathNames(view.getFilePathNames().replace("*", ","));
            fileUploadAndDownServ.saveFolderMessage(view, userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @RequestMapping(value = "/saveFolderMessageUpdate", method = RequestMethod.POST)
    @ResponseBody
    public void saveFolderMessageUpdate(DownloadView view, HttpSession session) throws Exception {
        try {
            Runtime.getRuntime().exec("chmod 755 -R /opt/ftpserver");
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            view.setFilePathName(view.getFilePathName().replace("*", ","));
            view.setFilePathNames(view.getFilePathNames().replace("*", ","));
            fileUploadAndDownServ.saveFolderMessageUpdate(view, userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @RequestMapping(value = "/saveFileMessage", method = RequestMethod.POST)
    @ResponseBody
    public void saveFileMessage(DownloadView view, HttpSession session) throws Exception {
        try {
            Runtime.getRuntime().exec("chmod 755 -R /opt/ftpserver");
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            view.setFilePathName(view.getFilePathName().replace("*", ","));
            view.setFilePathNames(view.getFilePathNames().replace("*", ","));
            fileUploadAndDownServ.saveFileMessage(view, userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @RequestMapping(value = "/saveFileMessageUpdate", method = RequestMethod.POST)
    @ResponseBody
    public void saveFileMessageUpdate(DownloadView view, HttpSession session) throws Exception {
        try {
             Runtime.getRuntime().exec("chmod 755 -R /opt/ftpserver");
            view.setFilePathName(view.getFilePathName().replace("*", ","));
            view.setFilePathNames(view.getFilePathNames().replace("*", ","));
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            fileUploadAndDownServ.saveFileMessageUpdate(view, userInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadFile(MultipartFileParam param, HttpServletRequest request, HttpSession session) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        DownloadView view = new DownloadView();
        if (isMultipart) {
            logger.info("上传文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                view = fileUploadAndDownServ.uploadFileByMappedByteBuffer1(param, userInfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件上传失败。{}", param.toString());
                throw e;
            }
            logger.info("上传文件end。");
        }
        return ResponseEntity.ok().body("上传成功。");
    }

    /**
     * 更新文件夹
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyfolder1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity modifyfolder1(MultipartFileParam param, HttpServletRequest request, HttpSession session) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        DownloadView view = new DownloadView();
        if (isMultipart) {
            logger.info("更新文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                fileUploadAndDownServ.modifyFolderByMappedByteBuffer(param, userInfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件更新失败。{}", param.toString());
                throw e;
            }
            logger.info("更新文件end。");
        }
        return ResponseEntity.ok().body("更新成功。");
    }


    /**
     * 更新文件夹
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyfile1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity modifyfile1(MultipartFileParam param, HttpServletRequest request, HttpSession session) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        DownloadView view = new DownloadView();
        if (isMultipart) {
            logger.info("更新文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                fileUploadAndDownServ.modifyFileByMappedByteBuffer(param, userInfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件更新失败。{}", param.toString());
                throw e;
            }
            logger.info("更新文件end。");
        }
        return ResponseEntity.ok().body("更新成功。");
    }


    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadfolder1", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity fileUpload(MultipartFileParam param, HttpServletRequest request, HttpSession session) throws Exception {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        DownloadView view = new DownloadView();
        if (isMultipart) {
            logger.info("上传文件start。");
            try {
                // 方法1
                //storageService.uploadFileRandomAccessFile(param);
                // 方法2 这个更快点
                view = fileUploadAndDownServ.uploadFileByMappedByteBuffer(param, userInfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("文件上传失败。{}", param.toString());
                throw e;
            }
            logger.info("上传文件end。");
        }
        return ResponseEntity.ok().body("上传成功。");
    }

    /**
     * 功能描述:  文件夹上传
     *
     * @auther: homey Wong
     * @date: 2019/1/9 0009 下午 12:08
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/uploadfolder2", method = RequestMethod.POST)
    public ModelAndView saveFolderFiles(HttpServletRequest
                                                request, @ModelAttribute(value = "view") DownloadView
                                                view, HttpSession session) throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate rest = new RestTemplate(requestFactory);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        FolderUpdateList ful;
        if (userInfo.getUseruploadright() == 1) {
            MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
            List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
            // boolean isFileLarge = FileUtil.checkFileSize(files, 1024, "M");
            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setuId(userInfo.getuId());
            ful = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, null);//同一订单下文件夹重名验证
            String isSameFolderNameorFileName = ful.getIsSameFileUploadFolderName();
            boolean isFolderNameForEngDateOrderNoSalor = fileUploadAndDownServ.isFolderNameForEngDateOrderNoSalor(null);
        }
        return new ModelAndView("uploadpage");
    }


    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void pdfStreamHandler(@RequestParam("fileNameandOrderNum") String fileNameandOrderNum, HttpServletRequest request, HttpServletResponse response) {
        //PDF文件地址
        File file = null;
        InputStream inStream = null;
        FilemanUrl url = null;
        fileNameandOrderNum = fileNameandOrderNum.replace("*", "&");
        try {
            String[] splitA = fileNameandOrderNum.split(":");
            String fileName = splitA[0].trim();
            String orderNo = splitA[1].trim();
            int index = fileName.lastIndexOf(".");
            String filenamecenter = fileName.substring(0, index);
            String fileType = fileName.substring(index + 1, fileName.length());
            if (fileName.endsWith(".docx") || fileName.endsWith(".DOCX") ||
                    fileName.endsWith(".doc") || fileName.endsWith(".DOC") ||
                    fileName.endsWith(".xls") || fileName.endsWith(".XLS") ||
                    fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX") ||
                    fileName.endsWith(".ppt") || fileName.endsWith(".PPT") ||
                    fileName.endsWith(".pptx") || fileName.endsWith(".PPTX")) {
                url = fileUploadAndDownServ.getUrlByFileNameAndOrderNo(orderNo, fileName);
                file = new File(finalDirPath + url.getLogur1());
                InputStream inputStream = new FileInputStream(file);
                inStream = WordToPDF.getPdfStream(fileType, inputStream);
            } else if (fileName.endsWith(".pdf") || fileName.endsWith(".PDF") ||
                    fileName.endsWith(".pdfx") || fileName.endsWith(".PDFX")) {
                url = fileUploadAndDownServ.getUrlByFileNameAndOrderNo(orderNo, fileName);
                file = new File(finalDirPath + url.getLogur1());
                inStream = new FileInputStream(file);
            } else if (fileName.endsWith(".dwg") || fileName.endsWith(".DWG")) {
                url = fileUploadAndDownServ.getUrlByFileNameAndOrderNo(orderNo, fileName);
                String srcFile = finalDirPath + url.getLogur1();
                com.aspose.cad.Image objImage = com.aspose.cad.Image.load(srcFile);
                // Create an instance of CadRasterizationOptions and set its various properties
                CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
                rasterizationOptions.setBackgroundColor(Color.getWhite());
                rasterizationOptions.setPageWidth(1600);
                rasterizationOptions.setPageHeight(1600);
                PdfOptions pdfOptions = new PdfOptions();
                pdfOptions.setVectorRasterizationOptions(rasterizationOptions);
                objImage.save(finalDirPath + "linshi/" + filenamecenter + ".pdf", pdfOptions);
                file = new File(finalDirPath + "linshi/" + filenamecenter + ".pdf");
                inStream = new FileInputStream(file);
            } else if (fileName.endsWith(".dxf") || fileName.endsWith(".DXF")) {
                url = fileUploadAndDownServ.getUrlByFileNameAndOrderNo(orderNo, fileName);
                String srcFile = finalDirPath + url.getLogur1();
                Image image = Image.load(srcFile);
                CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
                rasterizationOptions.setBackgroundColor(Color.getWhite());
                rasterizationOptions.setPageWidth(1600);
                rasterizationOptions.setPageHeight(1600);
                PdfOptions pdfOptions = new PdfOptions();
                pdfOptions.setVectorRasterizationOptions(rasterizationOptions);
                image.save(finalDirPath + "linshi/" + filenamecenter + ".pdf", pdfOptions);
                file = new File(finalDirPath + "linshi/" + filenamecenter + ".pdf");
                inStream = new FileInputStream(file);
            }
            if (file.exists()) {
                byte[] data = null;
                data = new byte[inStream.available()];
                inStream.read(data);
                response.getOutputStream().write(data);
                inStream.close();
            } else {
            }
        } catch (
                Exception e) {
            System.out.println("pdf文件处理异常：" + e);
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showImage")
    public void showImage(String fileName, String orderNo, HttpServletResponse response) throws Exception {
        fileName = fileName.replace("*", "&");
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        FilemanUrl fu = fileUploadAndDownServ.getUrlByFileNameAndOrderNo(orderNo, fileName);
        FileInputStream fis = new FileInputStream(finalDirPath + fu.getLogur1());
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
    }
}


