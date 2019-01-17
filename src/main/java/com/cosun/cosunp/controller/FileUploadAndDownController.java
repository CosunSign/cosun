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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @auther: homey Wong
     * @date: 2019/1/17 0017 上午 9:23
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping("/tobroserfilepage")
    public ModelAndView tobroserfilepage(HttpSession session, int currentPage) throws  Exception{
        ModelAndView mav = new ModelAndView("broserfilepage");
        DownloadView view = new DownloadView();
        view.setCurrentPage(currentPage);
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
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
        mav.addObject("userInfos",userInfos);
        return mav;
    }


    /**
     * 功能描述:文件夹退回
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 5:21
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value="/findBackFoldersByQueryParam")
    public void findBackFoldersByQueryParam(@RequestBody(required = true) DownloadView view,HttpSession session,HttpServletResponse response) throws Exception{
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(),Integer.valueOf(view.getEngineer()),view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer lastIndex = null;
        Integer preindex = null;
        String preStr = null;
        for(String s : urls) {
            lastIndex = s.indexOf(view.getFolderName());
            preStr = s.substring(0,lastIndex-1);
            preindex =preStr.lastIndexOf("\\");
            folderOrFiles.add(s.substring(preindex+1,lastIndex-1));
        }

        for(String s : folderOrFiles) {
            if(!norepeatFoldorFile.contains(s)){
                norepeatFoldorFile.add(s);
            }
        }

        String str = null;
        if (norepeatFoldorFile != null) {
            ObjectMapper  x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
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
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 4:33
     * @param:
     * @return:
     * @describtion
     */

    @ResponseBody
    @RequestMapping(value="/findNextFoldersByQueryParam")
    public void findNextFoldersByQueryParam(@RequestBody(required = true) DownloadView view,HttpSession session,HttpServletResponse response) throws Exception{
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(),Integer.valueOf(view.getEngineer()),view.getOrderNo());
        List<String> norepeatFoldorFile = new ArrayList<String>();
        List<String> folderOrFiles = new ArrayList<String>();
        Integer index = null;
        Integer lastIndex = null;
        for(String s : urls) {
            index = s.indexOf(view.getFolderName());//字符串第一次出现的位置
            lastIndex = s.indexOf("\\",index+1+view.getFolderName().length());//取第一次出现的位置开始的第一个文件夹或文件位置
            if(lastIndex==-1){
                lastIndex = s.length();
            }
           folderOrFiles.add(s.substring(index+1+view.getFolderName().length(),lastIndex));
        }

        for(String s : folderOrFiles) {
            if(!norepeatFoldorFile.contains(s)){
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
     * @auther: homey Wong
     * @date: 2019/1/17 0017 下午 4:33
     * @param:
     * @return:
     * @describtion
     */
    @ResponseBody
    @RequestMapping(value="/findFoldersByQueryParam")
    public void showFolderByParamThree(@RequestBody(required = true) DownloadView view,HttpSession session,HttpServletResponse response) throws Exception{
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
        List<String> urls = fileUploadAndDownServ.findAllUrlByParamThree(view.getSalor(),Integer.valueOf(view.getEngineer()),view.getOrderNo());
        List<String> folderOrFiles = new ArrayList<String>();
        List<String[]> strarray = new ArrayList<String[]>();
        List<String> norepeatFoldorFile = new ArrayList<String>();
        for(String str : urls){
            strarray.add(str.replaceAll("\\\\","/").split("/"));
        }
        for(String[] stra : strarray) {
            folderOrFiles.add(stra[1]);
        }
        for(String s : folderOrFiles) {
            if(!norepeatFoldorFile.contains(s)){
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
    public void updateAndSavePrivilegeStatus(@RequestBody(required = true) AjaxBean bean, HttpServletResponse response,HttpSession session) throws Exception {
        UserInfo info = (UserInfo) session.getAttribute("account");
        DownloadView view = bean.getView();
        List<String> privilelist = bean.getPrivilegestrs();//权限
        List<String> privilegeusers =  bean.getPrivilegeusers();//用户
        String oprighter = view.getOprighter();//权限被修改者 空为所有人 否则只能单个人选
        //权限  1代表查看  2代表修改  3代表删除
        //转换成List类型
          String[] filesId  = null;
        for (int i = 0; i < privilelist.size(); i++) {
            filesId = privilelist.get(i).split(",");
                if (filesId.length > 1) {
                    String privileflag = StringUtil.afterString(privilelist.get(i), ",");
                    fileUploadAndDownServ.saveOrUpdateFilePrivilege(privilegeusers, Integer.parseInt(filesId[0]), privileflag, info,oprighter);
                } else {
                    fileUploadAndDownServ.saveOrUpdateFilePrivilege(privilegeusers, Integer.parseInt(filesId[0]), "", info,oprighter);
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
    public ModelAndView toPrivilManagePage(HttpSession session, int currentPage) {
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
    public void findFileUrlByConditionParam(@RequestBody DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
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
    public void showFileUrlDiv(@RequestBody DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
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
    public void downloadByQueryCondition(@RequestBody DownloadView view, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        view.setuId(userInfo.getuId());
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
        boolean isFileLarge = FileUtil.checkFileSize(fileArray, 20, "M");//判断文件是否超过限制大小
        if (isFileLarge) {//没超过
            view = fileUploadAndDownServ.findIsExistFiles(fileArray, view, userInfo);
            //  view = fileUploadAndDownServ.addFilesData(view, fileArray, userInfo);
        } else {
            view.setFlag("-2");//超过
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
    public ModelAndView toModifyPage(String userName, String password, int currentPage, HttpServletRequest request) {
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
        boolean isFileLarge = FileUtil.checkFileSize(fileArray, 20, "M");//判断文件是否超过限制大小
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
    //文件更新
    @ResponseBody
    @RequestMapping(value = "/modifypagefolder", method = RequestMethod.POST)
    public ModelAndView modifyPageFolder(HttpServletRequest request,@ModelAttribute(value = "view") DownloadView view,HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo)session.getAttribute("account");
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = params.getFiles("fileFolder");     //fileFolder为文件项的name值
        List<UserInfo> uis = fileUploadAndDownServ.findAllUser();//查找所有设计师
        List<Integer> enginers = new ArrayList<Integer>();
        for(UserInfo fo : uis) {
            enginers.add(fo.getuId());
        }
        List<String> salors = StringUtil.getAllSalors();//业务员全部名单
        boolean isFileLarge = FileUtil.checkFileSize(files,20,"M");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());

        String yearMonth = "^2019[0|1][0-9]$";//年月正则
        String orderNo = "^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$";//订单编号正则
        Pattern yearMonthpattern = Pattern.compile(yearMonth);
        Pattern orderNopattern = Pattern.compile(orderNo);
        boolean isRightUrl = true;//为true上传的文件夹符合四层并命名正确
        String[] urls = null;
        String orginurl = null;
        for(MultipartFile file : files) {
            orginurl = file.getOriginalFilename();
            urls = orginurl.split("/");
            System.out.println(urls.length);
            if(urls.length>=5){//表示是由标准的四层文件夹组成
                System.out.println(urls.length);
                if(!enginers.contains(Integer.valueOf(urls[0]))){
                    isRightUrl = false;
                    view.setFlag("-33");//该标识代表设计师上传的文件夹第一层不符合标准，
                    return new ModelAndView("uploadpage");
                }
                Matcher matcher = yearMonthpattern.matcher(urls[1]);
                boolean isRight = matcher.find();
                if(!isRight){
                    isRightUrl = false;
                    view.setFlag("-66");//该标识代表设计师上传的文件夹第二层不符合标准，
                    return new ModelAndView("uploadpage");
                }
                if(!salors.contains(urls[2])){
                    isRightUrl = false;
                    view.setFlag("-99");//该标识代表设计师上传的文件夹第三层不符合标准，
                    return new ModelAndView("uploadpage");
                }
                Matcher matcher1 = orderNopattern.matcher(urls[3]);
                boolean isRight1 = matcher1.find();
                if(!isRight1){
                    isRightUrl = false;
                    view.setFlag("-100");//该标识代表设计师上传的文件夹第四层不符合标准，
                    return new ModelAndView("uploadpage");
                }
                if(!urls[4].contains(".")){
                    isRightUrl = false;
                    view.setFlag("-101");//该标识代表设计师上传的文件夹第5层不是文件，
                    return new ModelAndView("uploadpage");
                }

            }else{
                isRightUrl = false;
                view.setFlag("-333");//该标识代表设计师上传的文件夹不符合标准，
                return new ModelAndView("uploadpage");
            }
        }
        if(isFileLarge && isRightUrl) {
            view = fileUploadAndDownServ.findIsExistFilesFolderforUpdate(files,view,userInfo,urls[0],urls[1],urls[2],urls[3]);
        }else{
            view.setFlag("-2");
            return new ModelAndView("modifypage");
        }


        return new ModelAndView("modifypage");


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
        List<UserInfo> uis = fileUploadAndDownServ.findAllUser();//查找所有设计师
        List<Integer> enginers = new ArrayList<Integer>();
        for(UserInfo fo : uis) {
            enginers.add(fo.getuId());
        }
        List<String> salors = StringUtil.getAllSalors();//业务员全部名单
        boolean isFileLarge = FileUtil.checkFileSize(files,20,"M");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        view.setuId(userInfo.getuId());

        String yearMonth = "^2019[0|1][0-9]$";//年月正则
        String orderNo = "^[A-Z]{5}[0-9]{8}[A-Z]{2}[0-9]{2}$";//订单编号正则
        Pattern yearMonthpattern = Pattern.compile(yearMonth);
        Pattern orderNopattern = Pattern.compile(orderNo);
        boolean isRightUrl = true;//为true上传的文件夹符合四层并命名正确
        String[] urls = null;
        String orginurl = null;
        for(MultipartFile file : files) {
        orginurl = file.getOriginalFilename();
        urls = orginurl.split("/");
            System.out.println(urls.length);
        if(urls.length>=5){//表示是由标准的四层文件夹组成
            System.out.println(urls.length);
            if(!enginers.contains(Integer.valueOf(urls[0]))){
                isRightUrl = false;
                view.setFlag("-33");//该标识代表设计师上传的文件夹第一层不符合标准，
                return new ModelAndView("uploadpage");
            }
            Matcher matcher = yearMonthpattern.matcher(urls[1]);
            boolean isRight = matcher.find();
            if(!isRight){
                isRightUrl = false;
                view.setFlag("-66");//该标识代表设计师上传的文件夹第二层不符合标准，
                return new ModelAndView("uploadpage");
            }
            if(!salors.contains(urls[2])){
                isRightUrl = false;
                view.setFlag("-99");//该标识代表设计师上传的文件夹第三层不符合标准，
                return new ModelAndView("uploadpage");
            }
            Matcher matcher1 = orderNopattern.matcher(urls[3]);
            boolean isRight1 = matcher1.find();
            if(!isRight1){
                isRightUrl = false;
                view.setFlag("-100");//该标识代表设计师上传的文件夹第四层不符合标准，
                return new ModelAndView("uploadpage");
            }
            if(!urls[4].contains(".")){
                isRightUrl = false;
                view.setFlag("-101");//该标识代表设计师上传的文件夹第5层不是文件，
                return new ModelAndView("uploadpage");
            }

        }else{
            isRightUrl = false;
            view.setFlag("-333");//该标识代表设计师上传的文件夹不符合标准，
            return new ModelAndView("uploadpage");
        }
        }
        if(isFileLarge && isRightUrl) {
            view = fileUploadAndDownServ.findIsExistFilesFolder(files,view,userInfo,urls[0],urls[1],urls[2],urls[3]);
        }else{
            view.setFlag("-2");
            return new ModelAndView("uploadpage");
        }


        return new ModelAndView("uploadpage");
    }
}


