package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/fileupdown")
public class FileUploadAndDownController {

    @Autowired
    private IUserInfoServ userInfoServ;

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;


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
         * @author:homey Wong
         * @Date: 2018.12.21 18:18
         */
        @ResponseBody
        @RequestMapping("/toprivilegemanagepage")
        public ModelAndView goPrivilegeManagePage (HttpServletRequest request, HttpServletResponse response){
            ModelAndView modelAndView = new ModelAndView("filemanage/privilegemanage");
            String username = request.getParameter("username");//业务员
            String password = request.getParameter("password");//订单号
            UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(username, password);
            List<UserInfo> userInfos = fileUploadAndDownServ.findAllUser();
            List<DownloadView> downloadViews = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
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
        public void downloadFile (HttpServletResponse resp,String urlname, String filename)
            throws IOException {
            String filePath = urlname;
            resp.setHeader("content-type", "application/octet-stream");
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"iso-8859-1"));
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
         * @date:2018.12.21
         */
        //下载清单
        @ResponseBody
        @RequestMapping("/download")
        public ModelAndView toFileDownPage (HttpServletRequest request, HttpServletResponse response) throws Exception {
            String userName = request.getParameter("username");
            String userPwd = request.getParameter("password");
            ModelAndView mav = new ModelAndView("filemanage/filedownloadpage");
            UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, userPwd);
            List<DownloadView> downloadViewList = fileUploadAndDownServ.findAllUploadFileByUserId(userInfo.getuId());
            mav.addObject("downloadViewList", downloadViewList);
            return mav;
        }

        /**
         * @author:homey Wong
         * @date:2018.12.20
         */
        //上传
        @ResponseBody
        @RequestMapping("/upload")
        public ModelAndView toFileUpAndDownPage (HttpServletRequest request, HttpServletResponse response) throws
        Exception {
            ModelAndView mav = new ModelAndView("filemanage/filemanage");
            String username = request.getParameter("username");//业务员
            String password = request.getParameter("password");//订单号
            UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(username, password);
            String salorname = request.getParameter("salorname");//业务员
            String ordername = request.getParameter("ordername");//订单号
            String projectname = request.getParameter("projectname");//项目名
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            MultipartFile mulFile = fileMap.get("file");
            if (mulFile == null || mulFile.getSize() <= 0) { // -1
                return mav.addObject("flag", "0");
            }
            String orgFileName = mulFile.getOriginalFilename();
            long fileSize = mulFile.getSize();
            String name = mulFile.getName();//file input域的参数名  , file
            String suffix = orgFileName.substring(orgFileName.lastIndexOf("."));//文件后缀
            //文件新名字
            String newFileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + suffix;
            //文件最终路径
            String destPath = "f:/file/" + newFileName;
            FileManFileInfo fileManFileInfo;
            FilemanUrl filemanUrl;
            FilemanRight filemanRight;
            try {
                mulFile.transferTo(new File(destPath));
                //存文件的人与文件信息
                fileManFileInfo = new FileManFileInfo();
                fileManFileInfo.setCreateTime(new Date());
                fileManFileInfo.setCreateUser(userInfo.getUserName());
                fileManFileInfo.setUserName(userInfo.getUserName());
                fileManFileInfo.setuId(userInfo.getuId());
                fileManFileInfo.setFileName(orgFileName);
                fileManFileInfo.setExtInfo1(salorname);
                fileManFileInfo.setOrderNum(ordername);
                fileManFileInfo.setProjectName(projectname);

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
                mav.addObject("flag", "1");
                return mav;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mav.addObject("flag", "-1");

        }
    }
