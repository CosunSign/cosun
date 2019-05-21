package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.Rules;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IUserInfoServ;
import com.cosun.cosunp.service.IrulesServ;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * @author:homey Wong
     * @date:2018.12.19
     */


    @Autowired
    private IUserInfoServ userInfoServ;

    @Autowired
    private IrulesServ rulesServ;

    private static final String INDEX = "index";
    private static Logger logger = LogManager.getLogger(AccountController.class);

    @ResponseBody
    @RequestMapping(value = "/tologin")
    public ModelAndView toLoginPage(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        int interval = session.getMaxInactiveInterval();
        System.out.println("=============session time================"+interval);
        ModelAndView mav = new ModelAndView(INDEX);
        DownloadView downloadView = new DownloadView();
        downloadView.setFlag("true");
        mav.addObject("view", downloadView);
        return mav;
    }


    @ResponseBody
    @RequestMapping("/toResetPassword")
    public ModelAndView toResetPassword(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("reset-password");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        if(userInfo==null) {
            mav = new ModelAndView(INDEX);
            DownloadView downloadView = new DownloadView();
            downloadView.setFlag("true");
            mav.addObject("view", downloadView);
            return mav;
        }
        mav.addObject("userInfo", userInfo);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/toforgetpasswordpage")
    public ModelAndView toForgetPassword(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("forget-password");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        mav.addObject("userInfo", userInfo);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute(value = "view") DownloadView view, HttpSession session) throws Exception {
        ModelAndView mav;
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(), view.getPassword());
        if (userInfo != null && userInfo.getUserName() != null) {
            session.setAttribute("account", userInfo);
            view.setFullName(userInfo.getFullName());
            session.setAttribute("view", view);
            mav = new ModelAndView("mainindex");
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            mav.addObject("view", view);
            List<Rules> menuList = rulesServ.findAllRulesAll();
            mav.addObject("menuList",menuList);
            return mav;
        }
        mav = new ModelAndView(INDEX);
        view.setUserName(null);
        view.setPassword(null);
        view.setFlag("false");
        mav.addObject("view", view);
        return mav;

    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView(INDEX);
        // 移除session
        session.removeAttribute("account");
        DownloadView downloadView = new DownloadView();
        downloadView.setFlag("true");
        view.addObject("view", downloadView);
        return view;
    }

    @GetMapping("/toMainPage")
    public ModelAndView toMainPage(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("mainindex");
        List<Rules> menuList = rulesServ.findAllRulesAll();
        DownloadView v = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        v.setFullName(userInfo.getFullName());
        if (userInfo == null) {
            view = new ModelAndView(INDEX);
        }
        view.addObject("view", v);
        view.addObject("menuList",menuList);
        return view;

    }

    @GetMapping("/saveNewPassword")
    public ModelAndView saveNewPassword(String newPassword, HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView(INDEX);
        DownloadView v = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        if(userInfo!=null) {
            session.removeAttribute("account");
            v.setFullName(userInfo.getFullName());
            userInfoServ.setNewPasswordByuId(userInfo.getuId(), newPassword);
            userInfo.setUserPwd(newPassword);
            session.setAttribute("account", userInfo);
            view.addObject("view", v);
        }
        return view;
    }

    @RequestMapping(value = "/getMobileNumByUserName", method = RequestMethod.POST)
    @ResponseBody
    public void getMobileNumByUserName(String userName, HttpServletResponse response) throws Exception {
        String mobileNum = userInfoServ.getMobileNumByUserName(userName);
        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(mobileNum);

        } catch (JsonProcessingException e) {
            logger.debug(e.getMessage());
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

}
