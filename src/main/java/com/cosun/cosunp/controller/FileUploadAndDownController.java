package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

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
     * @author:homey Wong
     * @Date: 2018.12.22
     */
    @ResponseBody
    @RequestMapping("/updateandsaveprivilegestatus")
    public void updateAndSavePrivilegeStatus(@RequestBody String[] privilelist, HttpServletResponse response) throws Exception {
        //权限  1代表查看  2代表修改  3代表取消
        String[] filesId;
        //转换成List类型
        String selectuser = privilelist[0];
        for (int i = 1; i < privilelist.length; i++) {
            filesId = privilelist[i].split(",");
            if (filesId.length > 1) {
                String privileflag = StringUtil.afterString(privilelist[i], ",");
                fileUploadAndDownServ.saveOrUpdateFilePrivilege(Integer.parseInt(selectuser), Integer.parseInt(filesId[0]), privileflag);
            } else {
                fileUploadAndDownServ.saveOrUpdateFilePrivilege(Integer.parseInt(selectuser), Integer.parseInt(filesId[0]), "");
            }
        }
        //AJAX页面返回数据
        List<DownloadView> dataList = fileUploadAndDownServ.findAllUploadFileByUserId(Integer.parseInt(selectuser));
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
    public ModelAndView toPrivilManagePage(String userName, String password) {
        ModelAndView modelAndView = new ModelAndView("privilegemanagepage");
        DownloadView view = new DownloadView();
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, password);
        List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        modelAndView.addObject("view", view);
        modelAndView.addObject("userInfos", userInfos);
        modelAndView.addObject("downloadViewList", downloadViewList);
        return modelAndView;
    }

    /**
     * @author:homey Wong
     * @Date: 2018.12.21 18:18
     * 跳转到主页面
     */
    @ResponseBody
    @RequestMapping(value = "/tomainpage", method = RequestMethod.GET)
    public ModelAndView goPrivilegeManagePage(String userName, String password, int currentPage, HttpServletRequest request) {
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
    //下载清单
    @ResponseBody
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView toFileDownPage(String userName, String password, int currentPage, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("downloadpage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, password);
        List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllUploadFileByCondition(userInfo.getuId(), view.getCurrentPageTotalNum(), view.getPageSize());
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByUserId(userInfo.getuId());
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
        view.setMaxPage(maxPage);
        view.setRecordCount(recordCount);
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        mav.addObject("view", view);
        mav.addObject("downloadViewList", downloadViewList);
        return mav;
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
    public void downloadByQueryCondition(@RequestBody DownloadView view, HttpSession session,HttpServletResponse response) throws Exception {
        view.setCurrentPage(view.getCurrentPage());
        UserInfo userInfo =  (UserInfo) session.getAttribute("account");
        view.setuId(userInfo.getuId());
        List<DownloadView> dataList = fileUploadAndDownServ.findAllUploadFileByParaCondition(view);
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByParaCondition(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
       if(dataList!=null && dataList.size()>0) {
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
     * @date:2018.12.20
     */
    //多文件上传
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView toFileUpAndDownPage(HttpSession session,@ModelAttribute(value = "view") DownloadView view,
                                            @RequestParam("file") MultipartFile[] files, Model model) throws Exception {
        List<MultipartFile> fileArray = new ArrayList<MultipartFile>();
        UserInfo userInfo =(UserInfo) session.getAttribute("account");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        for(MultipartFile mfile : files) {
            fileArray.add(mfile);
        }
        boolean isFileLarge = FileUtil.checkFileSize(fileArray,20,"M");
        if(isFileLarge) {
            view = fileUploadAndDownServ.addFilesData(view, files, userInfo);
        }else{
            view.setFlag("-2");
            return new ModelAndView("uploadpage");
        }

        return new ModelAndView("uploadpage");

    }

    /**
     * 功能描述:  文件夹上传
     * @auther: homey Wong
     * @date: 2019/1/9 0009 下午 12:08
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value = "/uploadfolder", method = RequestMethod.POST)
    public ModelAndView saveFolderFiles(HttpServletRequest request,@ModelAttribute(value = "view") DownloadView view,HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
        boolean isFileLarge = FileUtil.checkFileSize(files,20,"M");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        if(isFileLarge) {
            view = fileUploadAndDownServ.addFileFoldersData(view, files, userInfo);
        }else{
            view.setFlag("-2");
            return new ModelAndView("uploadpage");
        }
        return new ModelAndView("uploadpage");
    }
}


