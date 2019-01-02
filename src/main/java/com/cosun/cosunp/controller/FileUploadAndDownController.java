package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        List<DownloadView> dataList = fileUploadAndDownServ.findAllUploadFileByParaCondition(view);
        int recordCount = fileUploadAndDownServ.findAllUploadFileCountByParaCondition(view);
        int maxPage = recordCount % view.getPageSize() == 0 ? recordCount / view.getPageSize() : recordCount / view.getPageSize() + 1;
       if(dataList!=null && dataList.size()>0) {
           dataList.get(0).setMaxPage(maxPage);
           dataList.get(0).setRecordCount(recordCount);
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
    //上传
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView toFileUpAndDownPage(@ModelAttribute(value = "view") DownloadView view, @RequestParam("file") MultipartFile file, Model model) throws
            Exception {
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(), view.getPassword());
        if (file == null || file.getSize() <= 0) { // -1
            view.setFlag("0");
        }
        String orgFileName = file.getOriginalFilename();
        long fileSize = file.getSize();
        String name = file.getName();//file input域的参数名  , file
        String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));//文件后缀
        //文件新名字
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + suffix;
        //文件最终路径
        String destPath = "f:/file/" + newFileName;
        FileManFileInfo fileManFileInfo;
        FilemanUrl filemanUrl;
        FilemanRight filemanRight;
        try {
            file.transferTo(new File(destPath));
            //存文件的人与文件信息
            fileManFileInfo = new FileManFileInfo();
            fileManFileInfo.setCreateTime(new Date());
            fileManFileInfo.setCreateUser(userInfo.getUserName());
            fileManFileInfo.setUserName(userInfo.getUserName());
            fileManFileInfo.setuId(userInfo.getuId());
            fileManFileInfo.setFileName(orgFileName);
            fileManFileInfo.setExtInfo1(view.getSalor());
            fileManFileInfo.setOrderNum(view.getOrderNo());
            fileManFileInfo.setProjectName(view.getProjectName());

            //文件路径
            filemanUrl = new FilemanUrl();
            filemanUrl.setFileName(name);
            filemanUrl.setUserName(userInfo.getUserName());
            filemanUrl.setUpTime(new Date());
            filemanUrl.setLogur1(destPath);

            //文件权限
            filemanRight = new FilemanRight();
            filemanRight.setCreateTime(new Date());
            filemanRight.setCreateUser(userInfo.getUserName());
            filemanRight.setFileName(name);
            filemanRight.setuId(userInfo.getuId());
            filemanRight.setUserName(userInfo.getUserName());

            fileUploadAndDownServ.addFileDataByUpload(filemanRight, filemanUrl, fileManFileInfo);
            view.setFlag("1");
        } catch (Exception e) {
            view.setFlag("-1");
            e.printStackTrace();
        }
        return new ModelAndView("uploadpage");

    }
}
