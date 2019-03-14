package com.cosun.cosunp.servlet;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.listener.MyProgressListener;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/3/4 0004 下午 2:11
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@WebServlet(urlPatterns = "/fileupdown/modifypagefolder")
public class ModifyPageFolderServlet extends HttpServlet {

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;

    private static Logger logger = LogManager.getLogger(ModifyPageFolderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //取出监听器MyProgress在session中保存的进度信息
        String progress = (String) req.getSession().getAttribute("progress");
        //响应
        resp.getWriter().print(progress);
        //清除session中保存的数据
//        req.getSession().removeAttribute("progress");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");// 设置获取字体
        resp.setCharacterEncoding("UTF-8"); // 设置返回字体
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setProgressListener(new MyProgressListener(req));
        HttpSession session = req.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        DownloadView view = (DownloadView) session.getAttribute("view");
        view.setuId(userInfo.getuId());
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        List<MultipartFile> fileArray = null;
        try {
            List<FileItem> list = upload.parseRequest(req);
            if (userInfo.getUseruploadright() == 1) {
                fileArray = new ArrayList<MultipartFile>();
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {//普通表单
                        String name = fileItem.getFieldName();
                        String value = fileItem.getString("UTF-8");
                        if (name.equals("salor")) {
                            if (value.trim().length() > 0) {
                                view.setSalor(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("saveFolderName")) {
                            if (value.trim().length() > 0) {
                                view.setSaveFolderName(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("orderNo")) {
                            if (value.trim().length() > 0) {
                                view.setOrderNo(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("projectName")) {
                            if (value.trim().length() > 0) {
                                view.setProjectName(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("createTime")) {
                            if (value.trim().length() > 0) {
                                view.setCreateTime(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("filedescribtion")) {
                            if (value.trim().length() > 0) {
                                view.setFiledescribtion(value);
                                session.setAttribute("view", view);
                            }
                        }
                        if (name.equals("remark")) {
                            if (value.trim().length() > 0) {
                                view.setRemark(value);
                                session.setAttribute("view", view);
                            }
                        }

                    } else {//上传文件
                        String fileName = fileItem.getName();
                        if (fileName != null && fileName != "") {
                            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "", fileItem.getInputStream());
                            fileArray.add(multipartFile);
                        }
                    }
                }

                if (fileArray.size() > 0) {
                    view = fileUploadAndDownServ.findIsExistFilesFolderforUpdate(fileArray, view, userInfo);
                }
            }
            resp.sendRedirect("tomodifypage?currentPage=1&flag=" + view.getFlag());
        } catch (Exception e) {
            System.out.println("文件上传发生错误！");
            e.printStackTrace();
        }


    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

}
