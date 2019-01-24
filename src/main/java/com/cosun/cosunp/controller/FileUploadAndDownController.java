package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.IOUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.util.*;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/fileupdown")
public class FileUploadAndDownController {

    @Autowired
    private IUserInfoServ userInfoServ;

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;


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
//        UserInfo userInfo = (UserInfo) session.getAttribute("account");
//        if (view.getEngineer() == null) {
//            view.setEngineer(userInfo.getuId().toString());
//        }
//        List<String> urls = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
//        List<String> newurls = new ArrayList<String>();
//        int pointindex = 0;
//        for (String str : urls) {
//            pointindex = StringUtils.ordinalIndexOf(str, "\\", 5);
//            newurls.add(str.substring(pointindex + 1, str.length()));
//        }
//        List<String> folderOrFiles = new ArrayList<String>();
//        List<String[]> strarray = new ArrayList<String[]>();
//        List<String> norepeatFoldorFile = new ArrayList<String>();
//
//
//        for (String str : newurls) {
//            strarray.add(str.replaceAll("\\\\", "/").split("/"));
//        }
//        for (String[] stra : strarray) {
//            folderOrFiles.add(stra[0]);
//        }
//        for (String s : folderOrFiles) {
//            if (!norepeatFoldorFile.contains(s)) {
//                norepeatFoldorFile.add(s);
//            }
//        }
//        String str = null;
//        if (norepeatFoldorFile != null) {
//            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
//            try {
//                str = x.writeValueAsString(norepeatFoldorFile);
//
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            findIsExistFilesFolderresponse.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print(str); //返回前端ajax
//        } catch (IOExcdownloadbyqueryconditioneption e) {
//            e.printStackTrace();
//        }
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
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
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
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllFileUrlByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByUserId(userInfo.getuId());
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        mav.addObject("view", view);
        mav.addObject("downloadViewList", downloadViewList);
        mav.addObject("userInfos", userInfos);
        return mav;
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
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer lastIndex = null;
        String foldername = view.getFolderName();
        Integer index = null;

        //取上二级文件夹名 由下找下一层文件夹或文件
        if (!foldername.contains(".")) {
            for (String s : urls) {
                lastIndex = s.indexOf("\\" + view.getFolderName() + "\\");
                String linshi1 = s.substring(0, lastIndex);
                int linshilastIndex = linshi1.lastIndexOf("\\");
                String linshi2 = linshi1.substring(0, linshilastIndex);
                foldername = StringUtil.subAfterString(linshi2, "\\");
                break;
            }
            for (String str : urls) {
                index = str.indexOf("\\" + foldername + "\\");//字符串第一次出现的位置
                lastIndex = str.indexOf("\\", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
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
                lastIndex = filefoldername.indexOf("\\" + view.getFolderName());
                String linshi1 = filefoldername.substring(0, lastIndex);
                int linshilastIndex = linshi1.lastIndexOf("\\");
                String linshi2 = linshi1.substring(0, linshilastIndex);
                foldername = StringUtil.subAfterString(linshi2, "\\");
            }

            for (String str : urls) {
                index = str.indexOf("\\" + foldername + "\\");//字符串第一次出现的位置
                lastIndex = str.indexOf("\\", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
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

        norepeatFoldorFile.add(view.getOrderNo());
        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
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
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer lastIndex = null;
        String foldername = view.getFolderName();
        Integer index = null;

        //取上二级文件夹名 由下找下一层文件夹或文件
        if (!foldername.contains(".")) {
            for (String s : urls) {
                lastIndex = s.indexOf("\\" + view.getFolderName() + "\\");
                String linshi1 = s.substring(0, lastIndex);
                int linshilastIndex = linshi1.lastIndexOf("\\");
                String linshi2 = linshi1.substring(0, linshilastIndex);
                foldername = StringUtil.subAfterString(linshi2, "\\");
                break;
            }
            for (String str : urls) {
                index = str.indexOf("\\" + foldername + "\\");//字符串第一次出现的位置
                lastIndex = str.indexOf("\\", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
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
                lastIndex = filefoldername.indexOf("\\" + view.getFolderName());
                String linshi1 = filefoldername.substring(0, lastIndex);
                int linshilastIndex = linshi1.lastIndexOf("\\");
                String linshi2 = linshi1.substring(0, linshilastIndex);
                foldername = StringUtil.subAfterString(linshi2, "\\");
            }

            for (String str : urls) {
                index = str.indexOf("\\" + foldername + "\\");//字符串第一次出现的位置
                lastIndex = str.indexOf("\\", index + 2 + foldername.length());//取第一次出现的位置开始的第一个文件夹或文件位置
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
        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
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
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("\\" + view.getFolderName() + "\\");//字符串第一次出现的位置
            lastIndex = s.indexOf("\\", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
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
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
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
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for (String s : urls) {
            index = s.indexOf("\\" + view.getFolderName() + "\\");//字符串第一次出现的位置
            lastIndex = s.indexOf("\\", index + 2 + view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
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

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str = x.writeValueAsString(norepeatFoldorFile);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
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
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(), Integer.valueOf(view.getEngineer()), view.getOrderNo());
        List<String> newurls = new ArrayList<String>();
        int pointindex = 0;
        for (String str : urls) {
            pointindex = StringUtils.ordinalIndexOf(str, "\\", 5);
            newurls.add(str.substring(pointindex + 1, str.length()));
        }
        List<String> folderOrFiles = new ArrayList<String>();
        List<String[]> strarray = new ArrayList<String[]>();
        List<String> norepeatFoldorFile = new ArrayList<String>();


        for (String str : newurls) {
            strarray.add(str.replaceAll("\\\\", "/").split("/"));
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

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
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
        DownloadView view = bean.getView();
        List<String> privilelist = bean.getPrivilegestrs();//权限
        List<String> privilegeusers = bean.getPrivilegeusers();//用户
        String oprighter = view.getOprighter();//权限被修改者 空为所有人 否则只能单个人选
        //权限  1代表查看  2代表修改  3代表删除
        //转换成List类型
        String[] filesId = null;
        for (int i = 0; i < privilelist.size(); i++) {
            filesId = privilelist.get(i).split(",");
            if (filesId.length > 1) {
                String privileflag = StringUtil.afterString(privilelist.get(i), ",");
                fileUploadAndDownServ.saveOrUpdateFilePrivilege(privilegeusers, Integer.parseInt(filesId[0]), privileflag, info, oprighter);
            } else {
                fileUploadAndDownServ.saveOrUpdateFilePrivilege(privilegeusers, Integer.parseInt(filesId[0]), "", info, oprighter);
            }

        }

        List<DownloadView> dataList = fileUploadAndDownServ.findAllFilesByCondParam(view);
        int recordCount = fileUploadAndDownServ.findAllFilesByCondParamCount(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        if (dataList.size() > 0) {
            dataList.get(0).setMaxPage(maxPage);
            dataList.get(0).setRecordCount(recordCount);
            dataList.get(0).setUserName(info.getUserName());
            dataList.get(0).setPassword(info.getUserPwd());
            dataList.get(0).setCurrentPage(view.getCurrentPage());
        }
        String str1 = null;
        if (dataList != null) {
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            try {
                str1 = x.writeValueAsString(dataList);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @RequestMapping(value = "/showcookies")
//    public ModelAndView showcookies(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        String cookievalue = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("abc")) {
//                    cookievalue = cookie.getValue();
//                }
//            }
//        }
//
//        return null;
//    }

    /**
     * 功能描述:文件下载批量ZIP
     *
     * @auther: homey Wong
     * @date: 2019/1/23 0023 下午 3:33
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/downloadfileorfolderforzip",method = RequestMethod.GET)
    public ModelAndView downloadFileOrFolderForZip(String orderno, String check_val, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws Exception {
        UserInfo info = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("downloadpage");
        String[] fileFoldersName = null;
        String singfileName = null;
        if (check_val.contains(",")) {
            fileFoldersName = check_val.split(",");//文件或文件夹名
        } else {
            singfileName = check_val;
        }
        //step1 根据订单号和所勾选要下载的文件或文件夹名，找到URL
        DownloadView view = new DownloadView();
        view.setOrderNo(orderno);
        if (orderno == "" || orderno.trim().length() == 0) {
            view.setOrderNoMessage(check_val);
        }
        List<String> urlsAll = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
        List<File> fileList = new ArrayList<File>();
        File file = null;
        int index = 0;
        if (fileFoldersName != null) {
            for (int i = 0; i < fileFoldersName.length; i++) {
                for (int j = 0; j < urlsAll.size(); j++) {
                    if (fileFoldersName[i].contains(".")) {//代表是文件
                        index = urlsAll.get(j).indexOf(fileFoldersName[i]);
                        if (index > 0) {
                            file = new File(urlsAll.get(j));
                            fileList.add(file);
                        }
                    } else {//以下为文件夹
                        index = urlsAll.get(j).indexOf("\\" + fileFoldersName[i] + "\\");
                        if (index > 0) {
                            file = new File(urlsAll.get(j));
                            fileList.add(file);
                        }
                    }
                }
            }
        } else {
            if (singfileName != null) {
                if (!singfileName.contains(",")) {
                    for (int a = 0; a < urlsAll.size(); a++) {
                        index = urlsAll.get(a).indexOf("\\" + singfileName + "\\");
                        if (index > 0) {
                            file = new File(urlsAll.get(a));
                            fileList.add(file);
                        }
                    }
                }
            }
        }
        boolean isLarge = FileUtil.checkDownloadFileSize(fileList, 2, "G");
        if (!isLarge) {
            view.setFlag("-1");//文件太大
            mav.addObject("view", view);
            return mav;
        }
        if (fileList.size() > 200) {//代表文件超过200个
            view.setFlag("-2");
            mav.addObject("view", view);
            view.setFlag("-369");
            mav.addObject("view", view);
            return mav;
        }
        if (fileList.size() == 0) {//单个文件下载,不压缩
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(singfileName.replaceAll(" ", "").getBytes(), "iso-8859-1"));
            byte[] buff = new byte[1024];
            BufferedInputStream bufferedInputStream = null;
            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();

                file = new File(urlsAll.get(0));
                FileInputStream fis = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fis);
                int num = bufferedInputStream.read(buff);
                while (num != -1) {
                    outputStream.write(buff, 0, num);
                    outputStream.flush();
                    num = bufferedInputStream.read(buff);
                }
                outputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                outputStream.close();
                bufferedInputStream.close();
            }
        } else {
            //step2 压缩
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ArrayList<String> iconNameList = new ArrayList<String>();//返回文件名数组
            if (orderno == "" || orderno.trim().length() == 0) {
                orderno = check_val.replaceAll(",", "");
            }
            String zipName = orderno + ".zip";
            String outFilePath = request.getSession().getServletContext().getRealPath("/");
            File fileZip = new File(outFilePath + zipName);
            FileOutputStream outStream = new FileOutputStream(fileZip);
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            try {
                IOUtil.zipFile(fileList, toClient);
                toClient.close();
                outStream.close();
                //step3 返回消息 完成
                IOUtil.downloadFile(fileZip, response, true);
                //单个文件下载
                /**
                 for (int i = 0; i < fileList.size(); i++) {
                 String curpath = fileList.get(i).getPath();//获取文件路径
                 iconNameList.add(curpath.substring(curpath.lastIndexOf("\\") + 1));//将文件名加入数组

                 String fileName = new String(filecomplaintpath.getBytes("UTF-8"),"iso8859-1");
                 headers.setContentDispositionFormData("attachment", fileName);
                 return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filecomplaintpath)),
                 headers, HttpStatus.OK);
                 }
                 **/

            } catch (Exception e) {
                System.out.println("系统异常,请从新录入!");
                toClient.close();
                outStream.close();
                e.printStackTrace();
            }
        }
        Cookie cookie = new Cookie("abc", "123");
        cookie.setPath("/");
        cookie.setMaxAge(3600*24);
        response.addCookie(cookie);
        view.setFlag("-369");
        mav.addObject("view", view);
        return mav;
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
    @RequestMapping(value = "toprivilmanagepage", method = RequestMethod.GET)
    public ModelAndView toPrivilManagePage(HttpSession session, int currentPage) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView modelAndView = new ModelAndView("privilegemanagepage");
        DownloadView view = new DownloadView();
        view.setType(userInfo.getType());
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());

        //查看所有人的权限
        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllUploadFileByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
        //查看所有人权限总数
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByUserId(userInfo.getuId());
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViewList", downloadViewList);

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
                e.printStackTrace();
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
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
    public ModelAndView goPrivilegeManagePage(String userName, String password, int currentPage, HttpServletRequest
            request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("uploadpage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, password);
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<DownloadView> downloadViews = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViews", downloadViews);
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
    public ModelAndView toFileDownPage(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("downloadpage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");//查看权限用
        List<String> orderNumFolders = fileUploadAndDownServ.findAllOrderNum();
        mav.addObject("orderNumFolders", orderNumFolders);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        mav.addObject("view", view);
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
    public void showFileUrlDiv(@RequestBody DownloadView view, HttpSession session, HttpServletResponse response) throws
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
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能描述:按条件查询需下载的文件
     *
     * @auther: homey Wong
     * @date: 2019/1/2 0002 下午 5:27
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/downloadbyquerycondition", method = RequestMethod.POST)
    public void downloadByQueryCondition(@RequestBody DownloadView view, HttpSession session, HttpServletResponse
            response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        if (view.getEngineer() == null) {
            view.setEngineer(userInfo.getuId().toString());
        }
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamManyOrNo(view);
        List<String> newurls = new ArrayList<String>();
        int pointindex = 0;
        for (String str : urls) {
            pointindex = StringUtils.ordinalIndexOf(str, "\\", 5);
            newurls.add(str.substring(pointindex + 1, str.length()));
        }
        List<String> folderOrFiles = new ArrayList<String>();
        List<String[]> strarray = new ArrayList<String[]>();
        List<String> norepeatFoldorFile = new ArrayList<String>();


        for (String str : newurls) {
            strarray.add(str.replaceAll("\\\\", "/").split("/"));
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

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
        }
//        UserInfo userInfo = (UserInfo) session.getAttribute("account");
//        List<DownloadView> dataList = fileUploadAndDownServ.findAllUploadFileByParaCondition(view);
//        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByParaCondition(view);
//        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
//        if (dataList != null && dataList.size() > 0) {
//            dataList.get(0).setMaxPage(maxPage);
//            dataList.get(0).setRecordCount(recordCount);
//            dataList.get(0).setUserName(userInfo.getUserName());
//            dataList.get(0).setPassword(userInfo.getUserPwd());
//            dataList.get(0).setCurrentPage(view.getCurrentPage());
//        }
//        String str1 = null;
//        if (dataList != null) {
//            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
//            try {
//                str1 = x.writeValueAsString(dataList);
//
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print(str1); //返回前端ajax
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    /**
     * @author:homey Wong
     * @date:2018.12.20
     */
    //多文件上传
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView toFileUpAndDownPage(HttpSession session, @ModelAttribute(value = "view") DownloadView view,
                                            @RequestParam("file") MultipartFile[] files, Model model) throws Exception {
        List<MultipartFile> fileArray = new ArrayList<MultipartFile>();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        for (MultipartFile mfile : files) {
            fileArray.add(mfile);
        }
        boolean isFileLarge = FileUtil.checkFileSize(fileArray, 800, "M");//判断文件是否超过限制大小
        boolean isExsitFileName = fileUploadAndDownServ.checkFileisSame(view, userInfo, fileArray);//判断是否有重名的文件名
        if (isFileLarge && !isExsitFileName) {//没超过并没有重复的名字
            view = fileUploadAndDownServ.findIsExistFiles(fileArray, view, userInfo);
            //  view = fileUploadAndDownServ.addFilesData(view, fileArray, userInfo);
        } else {
            if (!isFileLarge) {
                view.setFlag("-2");//超过
            }
            if (isExsitFileName) {
                view.setFlag("-222");//有重复的名字
            }
        }

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
    public ModelAndView toModifyPage(String userName, String password, int currentPage, HttpServletRequest request) throws
            Exception {
        ModelAndView modelAndView = new ModelAndView("modifypage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, password);
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<DownloadView> downloadViews = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViews", downloadViews);
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
    @RequestMapping(value = "/modifypage", method = RequestMethod.POST)
    public ModelAndView modifyPage(HttpSession session, @ModelAttribute(value = "view") DownloadView view,
                                   @RequestParam("file") MultipartFile[] files, Model model) throws Exception {
        List<MultipartFile> fileArray = new ArrayList<MultipartFile>();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        for (MultipartFile mfile : files) {
            fileArray.add(mfile);
        }
        boolean isFileLarge = FileUtil.checkFileSize(fileArray, 800, "M");//判断文件是否超过限制大小
        if (isFileLarge) {//没超过
            view = fileUploadAndDownServ.findIsExistFilesforUpdate(fileArray, view, userInfo);
            //  view = fileUploadAndDownServ.addFilesData(view, fileArray, userInfo);
        } else {
            view.setFlag("-2");//超过
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
    @RequestMapping(value = "/modifypagefolder", method = RequestMethod.POST)
    public ModelAndView modifyPageFolder(HttpServletRequest request, @ModelAttribute(value = "view") DownloadView
            view, HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
        boolean isFileLarge = FileUtil.checkFileSize(files, 800, "M");

        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());

//        List<UserInfo> uis = fileUploadAndDownServ.findAllUser();//查找所有设计师
//        List<Integer> enginers = new ArrayList<Integer>();
//        for(UserInfo fo : uis) {
//            enginers.add(fo.getuId());
//        }
//        List<String> salors = StringUtil.getAllSalors();//业务员全部名单
//

//
//        String yearMonth = "^2019[0|1][0-9]$";//年月正则
//        String orderNo = "^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$";//订单编号正则
//        Pattern yearMonthpattern = Pattern.compile(yearMonth);
//        Pattern orderNopattern = Pattern.compile(orderNo);
//        boolean isRightUrl = true;//为true上传的文件夹符合四层并命名正确
//        String[] urls = null;
//        String orginurl = null;
//        for(MultipartFile file : files) {
//            orginurl = file.getOriginalFilename();
//            urls = orginurl.split("/");
//            System.out.println(urls.length);
//            if(urls.length>=5){//表示是由标准的四层文件夹组成
//                System.out.println(urls.length);
//                if(!enginers.contains(Integer.valueOf(urls[0]))){
//                    isRightUrl = false;
//                    view.setFlag("-33");//该标识代表设计师上传的文件夹第一层不符合标准，
//                    return new ModelAndView("uploadpage");
//                }
//                Matcher matcher = yearMonthpattern.matcher(urls[1]);
//                boolean isRight = matcher.find();
//                if(!isRight){
//                    isRightUrl = false;
//                    view.setFlag("-66");//该标识代表设计师上传的文件夹第二层不符合标准，
//                    return new ModelAndView("uploadpage");
//                }
//                if(!salors.contains(urls[2])){
//                    isRightUrl = false;
//                    view.setFlag("-99");//该标识代表设计师上传的文件夹第三层不符合标准，
//                    return new ModelAndView("uploadpage");
//                }
//                Matcher matcher1 = orderNopattern.matcher(urls[3]);
//                boolean isRight1 = matcher1.find();
//                if(!isRight1){
//                    isRightUrl = false;
//                    view.setFlag("-100");//该标识代表设计师上传的文件夹第四层不符合标准，
//                    return new ModelAndView("uploadpage");
//                }
//                if(!urls[4].contains(".")){
//                    isRightUrl = false;
//                    view.setFlag("-101");//该标识代表设计师上传的文件夹第5层不是文件，
//                    return new ModelAndView("uploadpage");
//                }
//
//            }else{
//                isRightUrl = false;
//                view.setFlag("-333");//该标识代表设计师上传的文件夹不符合标准，
//                return new ModelAndView("uploadpage");
//            }
//        }
        if (isFileLarge) {
            view = fileUploadAndDownServ.findIsExistFilesFolderforUpdate(files, view, userInfo);
        } else {
            view.setFlag("-2");
        }


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
    @RequestMapping(value = "/uploadfolder", method = RequestMethod.POST)
    public ModelAndView saveFolderFiles(HttpServletRequest request, @ModelAttribute(value = "view") DownloadView
            view, HttpSession session) throws Exception {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        RestTemplate rest = new RestTemplate(requestFactory);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
        boolean isFileLarge = FileUtil.checkFileSize(files, 800, "M");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());
        int isSameFolderNameorFileName = fileUploadAndDownServ.isSameFolderNameorFileNameMethod(userInfo, view, files);

        if (isFileLarge && isSameFolderNameorFileName == 0) {
            view = fileUploadAndDownServ.findIsExistFilesFolder(files, view, userInfo);
        } else {
            if (!isFileLarge) {
                view.setFlag("-2");
            } else if (isSameFolderNameorFileName == -1) {
                view.setFlag("-9999");//代表上传的文件中有同名
            }
            if (isSameFolderNameorFileName == -2) {
                view.setFlag("-666");//代表上传的文件与数据库对应的订单有重名
            }
            if (isSameFolderNameorFileName == -3) {
                view.setFlag("-777");//代表上传过来的文件夹有重名
            }
            if (isSameFolderNameorFileName == -4) {
                view.setFlag("-888");//代表上传过来的文件夹与数据库的文件夹发生重名
            }
            if (isSameFolderNameorFileName == -5) {
                view.setFlag("-123");
            }
            if (isSameFolderNameorFileName == -6) {
                view.setFlag("-789");
            }
            if (isSameFolderNameorFileName == -7) {
                view.setFlag("-678");
            }
            if (isSameFolderNameorFileName == -8) {
                view.setFlag("-987");
            }
        }
        return new ModelAndView("uploadpage");


//        List<UserInfo> uis = fileUploadAndDownServ.findAllUser();//查找所有设计师
//        List<Integer> enginers = new ArrayList<Integer>();
//        for(UserInfo fo : uis) {
//            enginers.add(fo.getuId());
//        }
//        List<String> salors = StringUtil.getAllSalors();//业务员全部名单
//        String yearMonth = "^2019[0|1][0-9]$";//年月正则
//        String orderNo = "^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$";//订单编号正则
//        Pattern yearMonthpattern = Pattern.compile(yearMonth);
//        Pattern orderNopattern = Pattern.compile(orderNo);
//        boolean isRightUrl = true;//为true上传的文件夹符合四层并命名正确
//        String[] urls = null;
//        String orginurl = null;
//        for(MultipartFile file : files) {
//        orginurl = file.getOriginalFilename();
//        urls = orginurl.split("/");
//            System.out.println(urls.length);
//        if(urls.length>=5){//表示是由标准的四层文件夹组成
//            System.out.println(urls.length);
//            if(!enginers.contains(Integer.valueOf(urls[0]))){
//                isRightUrl = false;
//                view.setFlag("-33");//该标识代表设计师上传的文件夹第一层不符合标准，
//                return new ModelAndView("uploadpage");
//            }
//            Matcher matcher = yearMonthpattern.matcher(urls[1]);
//            boolean isRight = matcher.find();
//            if(!isRight){
//                isRightUrl = false;
//                view.setFlag("-66");//该标识代表设计师上传的文件夹第二层不符合标准，
//                return new ModelAndView("uploadpage");
//            }
//            if(!salors.contains(urls[2])){
//                isRightUrl = false;
//                view.setFlag("-99");//该标识代表设计师上传的文件夹第三层不符合标准，
//                return new ModelAndView("uploadpage");
//            }
//            Matcher matcher1 = orderNopattern.matcher(urls[3]);
//            boolean isRight1 = matcher1.find();
//            if(!isRight1){
//                isRightUrl = false;
//                view.setFlag("-100");//该标识代表设计师上传的文件夹第四层不符合标准，
//                return new ModelAndView("uploadpage");
//            }
//            if(!urls[4].contains(".")){
//                isRightUrl = false;
//                view.setFlag("-101");//该标识代表设计师上传的文件夹第5层不是文件，
//                return new ModelAndView("uploadpage");
//            }
//
//        }else{
//            isRightUrl = false;
//            view.setFlag("-333");//该标识代表设计师上传的文件夹不符合标准，
//            return new ModelAndView("uploadpage");
//        }
//        }
    }
}


