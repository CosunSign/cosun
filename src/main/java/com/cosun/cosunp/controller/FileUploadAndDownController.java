package com.cosun.cosunp.controller;

import com.alibaba.fastjson.JSON;
import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
@RequestMapping("/fileupdown")
public class FileUploadAndDownController {

    @Autowired
    private IUserInfoServ userInfoServ;

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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
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
        List<Employee> employees = fileUploadAndDownServ.findAllSalor();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
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
        return mav;
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
        String tempFolFileName = null;
        int maxPage = 0;
        //根据当前文件夹或文件名查找上一级文件夹名,如上一级文件夹名是以ORDERNO类形的,即开启limit查询
        String upFolderName = null;
        if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {
            folderOrFiles = new ArrayList<String>();
            views = new ArrayList<DownloadView>();
            norepeatFoldorFile = new ArrayList<String>();
            // urls = fileUploadAndDownServ.findAllUrlByParamThreeNew(view);
            urls = fileUploadAndDownServ.findAllUrlByOrderNoAndUid(view.getOrderNo(), view.getLinshiId());

            //取上二级文件夹名 由下找下一层文件夹或文件
            if (!foldername.contains(".")) {
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
                        right = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(u.getId(), u.getFileInfoId(), view.getuId());
                        // folderOrFiles.add(u.getLogur1().substring(index + 2 + foldername.length(), lastIndex));
                        tempFolFileName = u.getLogur1().substring(index + 2 + foldername.length(), lastIndex);

                    }


                    for (DownloadView v : views) {
                        if (v.getFolderOrFileName() != null) {
                            if (v.getFolderOrFileName().contains(tempFolFileName)) {
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        vvv = new DownloadView();
                        vi.setFolderOrFileName(tempFolFileName);
                        if (right != null) {
                            if (right.getOpRight() != null) {
                                vi.setOpRight(right.getOpRight());
                                if (right.getuId() != null) {
                                    vi.setOprighter(right.getuId().toString());
                                } else {
                                    vi.setOprighter("");
                                }
                            } else {
                                vi.setOpRight("");
                            }
                        } else {
                            vi.setOpRight("");
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
                        right = fileUploadAndDownServ.getFileRightByUrlIdAndFileInfoIdAnaUid(u.getId(), u.getFileInfoId(), view.getuId());
                        // folderOrFiles.add(u.getLogur1().substring(index + 2 + foldername.length(), lastIndex));
                        tempFolFileName = u.getLogur1().substring(index + 2 + foldername.length(), lastIndex);
                    }

                    for (DownloadView v : views) {
                        if (v.getFolderOrFileName() != null) {
                            if (v.getFolderOrFileName().contains(tempFolFileName)) {
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        vvv = new DownloadView();
                        vi.setFolderOrFileName(tempFolFileName);
                        if (right != null) {
                            if (right.getOpRight() != null) {
                                vi.setOpRight(right.getOpRight());
                                if (right.getuId() != null) {
                                    vi.setOprighter(right.getuId().toString());
                                } else {
                                    vi.setOprighter("");
                                }
                            } else {
                                vi.setOpRight("");
                            }
                        } else {
                            vi.setOpRight("");
                        }
                        vvv = vi;
                        views.add(vvv);
                    }
                    flag = true;
                }
            }
        }

        if (views.size() > 0) {

            viewss = new ArrayList<DownloadView>();
            for (DownloadView vii : views) {
                if (vii.getFolderOrFileName() != null) {
                    if (!vii.getFolderOrFileName().contains(".")) {
                        viewss.add(vii);
                    }
                }
            }
            for (DownloadView vii : views) {
                if (vii.getFolderOrFileName() != null) {
                    if (vii.getFolderOrFileName().contains(".")) {
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
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

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.debug(e.getMessage());
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
        if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {//有管理权限才进行如下操作
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
                        if (v.getFolderOrFileName().contains(tempFolOrFileName)) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        vi.setFolderOrFileName(tempFolOrFileName);
                        if (!tempFolOrFileName.contains(".")) {
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
                        }
                        if (right != null && right.getOpRight() != null) {
                            if (tempFolOrFileName.contains(".")) {
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
                            vi.setOpRight("");
                        }
                        views.add(vi);
                    }
                    flag = true;
                }
            }
        }
        if (views.size() > 0) {
            viewss = new ArrayList<DownloadView>();
            for (DownloadView vii : views) {
                if (!vii.getFolderOrFileName().contains(".")) {
                    viewss.add(vii);
                }
            }
            for (DownloadView vii : views) {
                if (vii.getFolderOrFileName().contains(".")) {
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

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
        if (userInfo.getUserActor() == 2 || userInfo.getUserActor() == 1) {
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
        }
        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
        views = fileUploadAndDownServ.findAllUrlByParamThreeNew2(view);
        recordCount = fileUploadAndDownServ.findAllUrlByParamThreeNew2Count(view);
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
        List<String> privilelist = bean.getPrivilegestrs();//权限
        List<String> privilegeusers = bean.getPrivilegeusers();//用户
        String oprighter = viewHtml.getOprighter();//权限被修改者 空为所有人
        List<DownloadView> views = null;//集装所有权限设置 订单编号 权限的相应文件夹或文件名  被权限者  被权限者的权限标记 1上传 2.更新 3.下载  4删除
        String orderNo = null;
        String fileOrFolderName = null;
        String opright = null;
        int index;
        if (info.getUserActor() == 2 || info.getUserActor() == 1) {
            views = new ArrayList<DownloadView>();
            String tempStr = null;
            for (int i = 0; i < privilelist.size(); i++) {
                if (privilelist.get(i).contains(",")) {//代表有权限标记
                    index = privilelist.get(i).indexOf(",");
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

            fileUploadAndDownServ.saveOrUpdateFilePrivilege(views, privilegeusers, info);

            views = fileUploadAndDownServ.findAllUrlByParamThreeNew2(viewHtml);
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

        String str1 = null;
        if (views != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(views);

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/showcookies")
    public ModelAndView showcookies(HttpServletRequest request, HttpServletResponse response) {
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
     * 功能描述:文件下载批量ZIP
     *
     * @auther: homey Wong
     * @date: 2019/1/23 0023 下午 3:33
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody  //调用迅雷接口方法代码  02-15 12：00
    @RequestMapping(value = "/downloadfileorfolderforzip")
    public void downloadFileOrFolderForZip(@RequestBody(required = true) String checkval, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        List<DownloadView> vs = JSON.parseArray(checkval, DownloadView.class);
        UserInfo info = (UserInfo) session.getAttribute("account");
        List<DownloadView> views = new ArrayList<DownloadView>();
        List<String> urls = new ArrayList<String>();
        DownloadView vie = new DownloadView();
        File file = null;
        List<File> files = new ArrayList<File>();

        //获取所有URL(DOWNLOADVIEW)集装,查看有没有权限 //集装所有URL
        boolean isDownRight = true;
        int index = 0;
        a:
        for (DownloadView view : vs) {
            views = fileUploadAndDownServ.findAllUrlByOrderNoAndUid(view.getOrderNo(), info.getuId());
            for (DownloadView vi : views) {
                if (view.getFolderOrFileName().contains(",")) {//代表是文件
                    index = vi.getUrlAddr().indexOf(view.getFolderOrFileName());
                    if (index > 0) {
                        if (!vi.getOpRight().contains("3")) {
                            isDownRight = false;
                            break a;
                        }
                        urls.add(vi.getUrlAddr());
                        file = new File(vi.getUrlAddr());
                        files.add(file);
                    }
                } else {//代表是文件夹
                    index = vi.getUrlAddr().indexOf("/" + view.getFolderOrFileName() + "/");
                    if (index > 0) {
                        if (vi.getOpRight() == null) {
                            isDownRight = false;
                            break a;
                        } else {
                            if (!vi.getOpRight().contains("3")) {
                                isDownRight = false;
                                break a;
                            }
                        }
                        urls.add(vi.getUrlAddr());
                        file = new File(vi.getUrlAddr());
                        files.add(file);
                    }
                }
            }
            if (views == null || views.size() == 0) {
                vie.setFlag("-258");
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

        } else {
            vie.setFlag("-258");
        }

        urls.add(vie.getFlag());
        String str = null;
        if (urls != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(urls);

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }


//    @ResponseBody
//    @RequestMapping(value = "/downloadfileorfolderforzip")
//    public ModelAndView downloadFileOrFolderForZip(String orderno, String check_val, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
//        ModelAndView mav = new ModelAndView("mainindex");
//        Cookie[] cookies = request.getCookies();
//        // 迭代查找并清除Cookie
//        for (Cookie cookie : cookies) {
//            if ("ccc".equals(cookie.getName())) {
//                cookie.setValue(null);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//        try {
//            UserInfo info = (UserInfo) session.getAttribute("account");
//            String[] fileFoldersName = null;
//            String singfileName = null;
//            if (check_val.contains(",")) {
//                fileFoldersName = check_val.split(",");//文件或文件夹名
//            } else {
//                singfileName = check_val;
//            }
//            //step1 根据订单号和所勾选要下载的文件或文件夹名，找到URL
//            DownloadView view = new DownloadView();
//            view.setOrderNo(orderno);
//            if (orderno == "" || orderno.trim().length() == 0) {
//                view.setOrderNoMessage(check_val);
//            }
//            List<String> urlsAll = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
//            List<File> fileList = new ArrayList<File>();
//            File file = null;
//            int index = 0;
//            if (fileFoldersName != null) {
//                for (int i = 0; i < fileFoldersName.length; i++) {
//                    for (int j = 0; j < urlsAll.size(); j++) {
//                        if (fileFoldersName[i].contains(".")) {//代表是文件
//                            index = urlsAll.get(j).indexOf(fileFoldersName[i]);
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        } else {//以下为文件夹
//                            index = urlsAll.get(j).indexOf("\\" + fileFoldersName[i] + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (singfileName != null) {
//                    if (!singfileName.contains(",")) {
//                        for (int a = 0; a < urlsAll.size(); a++) {
//                            index = urlsAll.get(a).indexOf("\\" + singfileName + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(a));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            }
//            boolean isLarge = FileUtil.checkDownloadFileSize(fileList, 2, "G");
//            if (!isLarge) {
//                view.setFlag("-1");//文件太大
//                mav.addObject("view", view);
//                return mav;
//            }
//            if (fileList.size() > 200) {//代表文件超过200个
//                view.setFlag("-2");
//                mav.addObject("view", view);
//                view.setFlag("-369");
//                mav.addObject("view", view);
//                return mav;
//            }
//            if (fileList.size() == 0) {//单个文件下载,不压缩
//                response.setHeader("content-type", "application/octet-stream");
//                Cookie cookie = new Cookie("ccc", "111");
//                cookie.setPath("/");
//                cookie.setMaxAge(3600 * 24);
//                response.addCookie(cookie);
//                response.setContentType("application/octet-stream");
//                response.setHeader("Content-Disposition", "attachment;filename=" + new String(singfileName.replaceAll(" ", "").getBytes(), "iso-8859-1"));
//                view.setFlag("-369");
//                byte[] buff = new byte[1024];
//                BufferedInputStream bufferedInputStream = null;
//                OutputStream outputStream = null;
//                try {
//                    outputStream = response.getOutputStream();
//
//                    file = new File(urlsAll.get(0));
//                    FileInputStream fis = new FileInputStream(file);
//                    bufferedInputStream = new BufferedInputStream(fis);
//                    int num = bufferedInputStream.read(buff);
//                    while (num != -1) {
//                        outputStream.write(buff, 0, num);
//                        outputStream.flush();
//                        num = bufferedInputStream.read(buff);
//                    }
//                    outputStream.close();
//                    bufferedInputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e.getMessage());
//                } finally {
//                    if (outputStream != null) {
//                        outputStream.close();
//                    }
//                    if (bufferedInputStream != null) {
//                        bufferedInputStream.close();
//                    }
//                }
//            } else {
//                //step2 压缩
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
//                if (orderno == "" || orderno.trim().length() == 0) {
//                    orderno = check_val.replaceAll(",", "");
//                }
//                String zipName = orderno + ".zip";
//                String outFilePath = request.getSession().getServletContext().getRealPath("/");
//                File fileZip = new File(outFilePath + zipName);
//                FileOutputStream outStream = new FileOutputStream(fileZip);
//                ZipOutputStream toClient = new ZipOutputStream(outStream);
//                view.setFlag("-369");
//                try {
//                    IOUtil.zipFile(fileList, toClient);
//                    if (toClient != null) {
//                        toClient.close();
//                    }
//                    if (outStream != null) {
//                        outStream.close();
//                    }
//                    //step3 返回消息 完成
//                    IOUtil.downloadFile(fileZip, response, true);
//                    //单个文件下载
//                    /**
//                     for (int i = 0; i < fileList.size(); i++) {
//                     String curpath = fileList.get(i).getPath();//获取文件路径
//                     iconNameList.add(curpath.substring(curpath.lastIndexOf("\\") + 1));//将文件名加入数组
//
//                     String fileName = new String(filecomplaintpath.getBytes("UTF-8"),"iso8859-1");
//                     headers.setContentDispositionFormData("attachment", fileName);
//                     return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filecomplaintpath)),
//                     headers, HttpStatus.OK);
//                     }
//                     **/
//
//                } catch (Exception e) {
//                    System.out.println("系统异常,请从新录入!");
//                    e.printStackTrace();
//                } finally {
//                    if (toClient != null) {
//                        toClient.close();
//                    }
//                    if (outStream != null) {
//                        outStream.close();
//                    }
//                }
//            }
//            mav.addObject("view", view);
//            return mav;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            return mav;
//        }
//    }


//    //多线程分段下载
//    @ResponseBody
//    @RequestMapping(value = "/downloadfileorfolderforzip", method = RequestMethod.GET)
//    public ResponseEntity<String> downloadFileOrFolderForZip(String orderno, String check_val, HttpServletResponse response, HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        // 迭代查找并清除Cookie
//        for (Cookie cookie : cookies) {
//            if ("ccc".equals(cookie.getName())) {
//                cookie.setValue(null);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//        try {
//            UserInfo info = (UserInfo) session.getAttribute("account");
//            String[] fileFoldersName = null;
//            String singfileName = null;
//            if (check_val.contains(",")) {
//                fileFoldersName = check_val.split(",");//文件或文件夹名
//            } else {
//                singfileName = check_val;
//            }
//            //step1 根据订单号和所勾选要下载的文件或文件夹名，找到URL
//            DownloadView view = new DownloadView();
//            view.setOrderNo(orderno);
//            if (orderno == "" || orderno.trim().length() == 0) {
//                view.setOrderNoMessage(check_val);
//            }
//            List<String> urlsAll = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
//            List<File> fileList = new ArrayList<File>();
//            File file = null;
//            int index = 0;
//            if (fileFoldersName != null) {
//                for (int i = 0; i < fileFoldersName.length; i++) {
//                    for (int j = 0; j < urlsAll.size(); j++) {
//                        if (fileFoldersName[i].contains(".")) {//代表是文件
//                            index = urlsAll.get(j).indexOf(fileFoldersName[i]);
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        } else {//以下为文件夹
//                            index = urlsAll.get(j).indexOf("\\" + fileFoldersName[i] + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (singfileName != null) {
//                    if (!singfileName.contains(",")) {
//                        for (int a = 0; a < urlsAll.size(); a++) {
//                            index = urlsAll.get(a).indexOf("\\" + singfileName + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(a));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            }
//            boolean isLarge = FileUtil.checkDownloadFileSize(fileList, 2, "G");
//            if (!isLarge) {
//                view.setFlag("-1");//文件太大
//            }
//            if (fileList.size() > 200) {//代表文件超过200个
//                view.setFlag("-369");
//            }
//            if (fileList.size() == 0) {//单个文件下载,不压缩
//                response.setHeader("content-type", "application/octet-stream");
//                Cookie cookie = new Cookie("ccc", "111");
//                cookie.setPath("/");
//                cookie.setMaxAge(3600 * 24);
//                response.addCookie(cookie);
//                response.setContentType("application/octet-stream");
//                response.setHeader("Content-Disposition", "attachment;filename=" + new String(singfileName.replaceAll(" ", "").getBytes(), "iso-8859-1"));
//                view.setFlag("-369");
//                byte[] buff = new byte[1024];
//                BufferedInputStream bufferedInputStream = null;
//                OutputStream outputStream = null;
//                try {
//                    outputStream = response.getOutputStream();
//
//                    file = new File(urlsAll.get(0));
//                    FileInputStream fis = new FileInputStream(file);
//                    bufferedInputStream = new BufferedInputStream(fis);
//                    int num = bufferedInputStream.read(buff);
//                    while (num != -1) {
//                        outputStream.write(buff, 0, num);
//                        outputStream.flush();
//                        num = bufferedInputStream.read(buff);
//                    }
//                    outputStream.close();
//                    bufferedInputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e.getMessage());
//                } finally {
//                    if (outputStream != null) {
//                        outputStream.close();
//                    }
//                    if (bufferedInputStream != null) {
//                        bufferedInputStream.close();
//                    }
//                }
//            } else {
//                //step2 压缩
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
//                if (orderno == "" || orderno.trim().length() == 0) {
//                    orderno = check_val.replaceAll(",", "");
//                }
//                String zipName = orderno + ".zip";
//                String outFilePath = request.getSession().getServletContext().getRealPath("/");
//                File fileZip = new File(outFilePath + zipName);
//                FileOutputStream outStream = new FileOutputStream(fileZip);
//                ZipOutputStream toClient = new ZipOutputStream(outStream);
//                view.setFlag("-369");
//                try {
//                    IOUtil.zipFile(fileList, toClient);
//                    if (toClient != null) {
//                        toClient.close();
//                    }
//                    if (outStream != null) {
//                        outStream.close();
//                    }
//                    //step3 返回消息 完成
//                    InputStream inputStream = null;
//                    ServletOutputStream out = null;
//                    String odexName = "file.apk";
//                    try {
//                        int fSize = Integer.parseInt(String.valueOf(file.length()));
//                        response.setCharacterEncoding("utf-8");
//                        response.setContentType("application/x-download");
//                        response.setHeader("Accept-Ranges", "bytes");
//                        response.setHeader("Content-Length", String.valueOf(fSize));
//                        response.setHeader("Content-Disposition", "attachment;fileName=" + zipName);
//                        inputStream = new FileInputStream(outFilePath+zipName);
//                        long pos = 0;
//                        if (null != request.getHeader("Range")) { // 断点续传
//                            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//                            try {
//                                pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
//                            } catch (NumberFormatException e) {
//                                pos = 0;
//                            }
//                        }
//                        out = response.getOutputStream();
//                        response.setHeader("Content-Range", new StringBuffer("bytes ").append(pos + "").append("-").append((fSize - 1) + "").append("/").append(fSize + "").toString());
//                        inputStream.skip(pos);
//                        byte[] buffer = new byte[1024 * 10];
//                        int length = 0;
//                        while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
//                            out.write(buffer, 0, length);
//                            Thread.sleep(100);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        try {
//                            if (null != out) out.flush();
//                            if (null != out) out.close();
//                            if (null != inputStream) inputStream.close();
//                        } catch (IOException e) {
//                        }
//                    }
//
//                    IOUtil.downloadFileByCut(fileZip, true);
//                } catch (Exception e) {
//                    System.out.println("系统异常,请从新录入!");
//                    e.printStackTrace();
//                } finally {
//                    if (toClient != null) {
//                        toClient.close();
//                    }
//                    if (outStream != null) {
//                        outStream.close();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity(null, HttpStatus.OK);
//    }


//    @ResponseBody   原始下载方法  压缩--下载
//    @RequestMapping(value = "/downloadfileorfolderforzip")
//    public ModelAndView downloadFileOrFolderForZip(String orderno, String check_val, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
//        ModelAndView mav = new ModelAndView("mainindex");
//        Cookie[] cookies = request.getCookies();
//        // 迭代查找并清除Cookie
//        for (Cookie cookie: cookies) {
//            if ("ccc".equals(cookie.getName())) {
//                cookie.setValue(null);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//        try {
//            UserInfo info = (UserInfo) session.getAttribute("account");
//            String[] fileFoldersName = null;
//            String singfileName = null;
//            if (check_val.contains(",")) {
//                fileFoldersName = check_val.split(",");//文件或文件夹名
//            } else {
//                singfileName = check_val;
//            }
//            //step1 根据订单号和所勾选要下载的文件或文件夹名，找到URL
//            DownloadView view = new DownloadView();
//            view.setOrderNo(orderno);
//            if (orderno == "" || orderno.trim().length() == 0) {
//                view.setOrderNoMessage(check_val);
//            }
//            List<String> urlsAll = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
//            List<File> fileList = new ArrayList<File>();
//            File file = null;
//            int index = 0;
//            if (fileFoldersName != null) {
//                for (int i = 0; i < fileFoldersName.length; i++) {
//                    for (int j = 0; j < urlsAll.size(); j++) {
//                        if (fileFoldersName[i].contains(".")) {//代表是文件
//                            index = urlsAll.get(j).indexOf(fileFoldersName[i]);
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        } else {//以下为文件夹
//                            index = urlsAll.get(j).indexOf("\\" + fileFoldersName[i] + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(j));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (singfileName != null) {
//                    if (!singfileName.contains(",")) {
//                        for (int a = 0; a < urlsAll.size(); a++) {
//                            index = urlsAll.get(a).indexOf("\\" + singfileName + "\\");
//                            if (index > 0) {
//                                file = new File(urlsAll.get(a));
//                                fileList.add(file);
//                            }
//                        }
//                    }
//                }
//            }
//            boolean isLarge = FileUtil.checkDownloadFileSize(fileList, 2, "G");
//            if (!isLarge) {
//                view.setFlag("-1");//文件太大
//                mav.addObject("view", view);
//                return mav;
//            }
//            if (fileList.size() > 200) {//代表文件超过200个
//                view.setFlag("-2");
//                mav.addObject("view", view);
//                view.setFlag("-369");
//                mav.addObject("view", view);
//                return mav;
//            }
//            if (fileList.size() == 0) {//单个文件下载,不压缩
//                response.setHeader("content-type", "application/octet-stream");
//                Cookie cookie = new Cookie("ccc", "111");
//                cookie.setPath("/");
//                cookie.setMaxAge(3600 * 24);
//                response.addCookie(cookie);
//                response.setContentType("application/octet-stream");
//                response.setHeader("Content-Disposition", "attachment;filename=" + new String(singfileName.replaceAll(" ", "").getBytes(), "iso-8859-1"));
//                view.setFlag("-369");
//                byte[] buff = new byte[1024];
//                BufferedInputStream bufferedInputStream = null;
//                OutputStream outputStream = null;
//                try {
//                    outputStream = response.getOutputStream();
//
//                    file = new File(urlsAll.get(0));
//                    FileInputStream fis = new FileInputStream(file);
//                    bufferedInputStream = new BufferedInputStream(fis);
//                    int num = bufferedInputStream.read(buff);
//                    while (num != -1) {
//                        outputStream.write(buff, 0, num);
//                        outputStream.flush();
//                        num = bufferedInputStream.read(buff);
//                    }
//                    outputStream.close();
//                    bufferedInputStream.close();
//                } catch (IOException e) {
//                    throw new RuntimeException(e.getMessage());
//                } finally {
//                    if (outputStream != null) {
//                        outputStream.close();
//                    }
//                    if (bufferedInputStream != null) {
//                        bufferedInputStream.close();
//                    }
//                }
//            } else {
//                //step2 压缩
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
//                if (orderno == "" || orderno.trim().length() == 0) {
//                    orderno = check_val.replaceAll(",", "");
//                }
//                String zipName = orderno + ".zip";
//                String outFilePath = request.getSession().getServletContext().getRealPath("/");
//                File fileZip = new File(outFilePath + zipName);
//                FileOutputStream outStream = new FileOutputStream(fileZip);
//                ZipOutputStream toClient = new ZipOutputStream(outStream);
//                view.setFlag("-369");
//                try {
//                    IOUtil.zipFile(fileList, toClient);
//                    if(toClient!=null) {
//                        toClient.close();
//                    }
//                    if(outStream!=null) {
//                        outStream.close();
//                    }
//                    //step3 返回消息 完成
//                    IOUtil.downloadFile(fileZip, response, true);
//                    //单个文件下载
//                    /**
//                     for (int i = 0; i < fileList.size(); i++) {
//                     String curpath = fileList.get(i).getPath();//获取文件路径
//                     iconNameList.add(curpath.substring(curpath.lastIndexOf("\\") + 1));//将文件名加入数组
//
//                     String fileName = new String(filecomplaintpath.getBytes("UTF-8"),"iso8859-1");
//                     headers.setContentDispositionFormData("attachment", fileName);
//                     return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filecomplaintpath)),
//                     headers, HttpStatus.OK);
//                     }
//                     **/
//
//                } catch (Exception e) {
//                    System.out.println("系统异常,请从新录入!");
//                    e.printStackTrace();
//                } finally {
//                    if (toClient != null) {
//                        toClient.close();
//                    }
//                    if (outStream != null) {
//                        outStream.close();
//                    }
//                }
//            }
//            mav.addObject("view", view);
//            return mav;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            return mav;
//        }
//    }

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
    @RequestMapping(value = "toprivilmanagepage", method = RequestMethod.GET)
    public ModelAndView toPrivilManagePage(HttpSession session, int currentPage, int pageSize) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView modelAndView = new ModelAndView("privilegemanagepage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        view.setPageSize(pageSize);
        view.setType(userInfo.getType());
        List<Employee> employees = fileUploadAndDownServ.findAllSalor();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21 18:18
     * 跳转到主页面
     */
    @ResponseBody
    @RequestMapping(value = "/tomainpage", method = RequestMethod.GET)
    public ModelAndView goPrivilegeManagePage(String userName, String password, String flag,
                                              int currentPage, HttpServletRequest
                                                      request, HttpServletResponse response, HttpSession session) throws Exception {
        ModelAndView modelAndView = new ModelAndView("uploadpage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setFlag(flag);
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<Employee> employees = fileUploadAndDownServ.findAllSalor();
        List<DownloadView> downloadViews = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViews", downloadViews);
        modelAndView.addObject("employees", employees);
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
        List<Employee> employees = fileUploadAndDownServ.findAllSalor();
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<String> orderNumFolders = fileUploadAndDownServ.findAllOrderNum(view.getCurrentPageTotalNum(), view.getPageSize());
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
        return mav;
    }
//    @ResponseBody
//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    public ModelAndView toFileDownPage(String userName, String password, int currentPage, HttpServletResponse response) throws Exception {
//        ModelAndView mav = new ModelAndView("downloadpage");
//        DownloadView view = new DownloadView();
//        view.setCurrentPage(currentPage);
//        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, password);
//        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllUploadFileByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
//        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByUserId(userInfo.getuId());
//        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
//        view.setMaxPage(maxPage);
//        view.setRecordCount(recordCount);
//        view.setUserName(userInfo.getUserName());
//        view.setPassword(userInfo.getUserPwd());
//        mav.addObject("view", view);
//        mav.addObject("downloadViewList", downloadViewList);
//        return mav;
//    }


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

            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
            returnMessage = fileUploadAndDownServ.checkIsExistFilesFolderforUpdate(view.getFilePathName(), view, userInfo);
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnMessage);

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
            returnMessage = fileUploadAndDownServ.checkIsExistFilesforUpdate(view.getFilePathName(), view, userInfo);
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnMessage);

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/checkmessagefolder", method = RequestMethod.POST)
    public void checkMessageFolder(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        FileManFileInfo info = null;
        String returnmessage = "OK";
        if (userInfo.getUseruploadright() == 1) {
            boolean isFolderNameForEngDateOrderNoSalor = true;
            isFolderNameForEngDateOrderNoSalor = fileUploadAndDownServ.isFolderNameForEngDateOrderNoSalor(view.getFilePathName());//文件夹名验证，文件夹名不能有订单，业务员，设计师命名
            if (isFolderNameForEngDateOrderNoSalor) {
                info = fileUploadAndDownServ.getFileInfoByOrderNo(view.getOrderNo());
                if (info != null) {
                    if (!info.getExtInfo1().equals(view.getSalor())) {//查看订单编号有没有被占用
                        returnmessage = "您输入的订单编号系统中已存在，请另换一个订单号!或检查您填写的订单信息!";
                    }
                }
                returnmessage = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, view.getFilePathName());//同一订单下文件夹重名验证

            }
            if (!isFolderNameForEngDateOrderNoSalor) {
                returnmessage = "您的文件夹名有问题,不能以设计师名,订单编号,业务员,日期作为命名!";
            }
        } else {
            returnmessage = "您没有上传权限!";
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnmessage);

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/checkmessage", method = RequestMethod.POST)
    public void checkMessage(DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        boolean isExsitFileName = false;
        FileManFileInfo info = null;
        String returnMessage = "OK";
        if (userInfo.getUseruploadright() == 1) {//有无权限
            returnMessage = fileUploadAndDownServ.checkFileisSame(view, userInfo, view.getFilePathName());//判断是否有重名的文件名
            //上传的文件或文件夹有无重复，有无存在
            if (returnMessage.equals("OK")) {
                info = fileUploadAndDownServ.getFileInfoByOrderNo(view.getOrderNo());
                if (info != null) {
                    if (!info.getExtInfo1().equals(view.getSalor())) {
                        returnMessage = "您输入的订单系统中已存在，请另换一个订单号!或检查您填写的订单信息!";
                    }
                }
            }

        } else {
                returnMessage = "您没有上传权限，如需要请向上级申请!";
        }

        String str1 = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(returnMessage);

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
            } catch (JsonProcessingException e) {
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
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
    public ModelAndView toModifyPage(HttpSession session, String userName, String password, int currentPage, String flag, HttpServletRequest
            request) throws
            Exception {
        ModelAndView modelAndView = new ModelAndView("modifypage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<Employee> employees = fileUploadAndDownServ.findAllSalor();
        List<DownloadView> downloadViews = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setFullName(userInfo.getFullName());
        view.setFlag(flag);
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViews", downloadViews);
        modelAndView.addObject("employees", employees);
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
     * 功能描述:  文件夹上传
     *
     * @auther: homey Wong
     * @date: 2019/1/9 0009 下午 12:08
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/uploadfolder1", method = RequestMethod.POST)
    public ModelAndView saveFolderFiles(HttpServletRequest
                                                request, @ModelAttribute(value = "view") DownloadView
                                                view, HttpSession session) throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate rest = new RestTemplate(requestFactory);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        if (userInfo.getUseruploadright() == 1) {
            MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
            List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
            // boolean isFileLarge = FileUtil.checkFileSize(files, 1024, "M");
            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setuId(userInfo.getuId());
            String isSameFolderNameorFileName = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, null);//同一订单下文件夹重名验证
            boolean isFolderNameForEngDateOrderNoSalor = fileUploadAndDownServ.isFolderNameForEngDateOrderNoSalor(null);

//            if (isFolderNameForEngDateOrderNoSalor && isSameFolderNameorFileName == 0) {
//                //if (isFileLarge && isSameFolderNameorFileName == 0) {
//                view = fileUploadAndDownServ.findIsExistFilesFolder(files, view, userInfo);
//            } else {
////                if (files.size() >= 200) {
////                    view.setFlag("-369");
////                }
////                if (!isFileLarge) {
////                    view.setFlag("-2");
////                } else
//                if (isSameFolderNameorFileName == -1) {
//                    view.setFlag("-9999");//代表上传的文件中有同名
//                }
//                if (isSameFolderNameorFileName == -2) {
//                    view.setFlag("-666");//代表上传的文件与数据库对应的订单有重名
//                }
//                if (isSameFolderNameorFileName == -3) {
//                    view.setFlag("-777");//代表上传过来的文件夹有重名
//                }
//                if (isSameFolderNameorFileName == -4) {
//                    view.setFlag("-888");//代表上传过来的文件夹与数据库的文件夹发生重名
//                }
//                if (isSameFolderNameorFileName == -5) {
//                    view.setFlag("-123");
//                }
//                if (isSameFolderNameorFileName == -6) {
//                    view.setFlag("-789");
//                }
//                if (isSameFolderNameorFileName == -7) {
//                    view.setFlag("-678");
//                }
//                if (isSameFolderNameorFileName == -8) {
//                    view.setFlag("-987");
//                }
//                if (!isFolderNameForEngDateOrderNoSalor) {
//                    view.setFlag("-162");//代表文件夹结构中有订单名，日期，业务员，设计师
//                }
//            }
//        } else {
//            view.setFlag("-258");//代表没有权限
//        }
        }
        return new ModelAndView("uploadpage");
    }
}


