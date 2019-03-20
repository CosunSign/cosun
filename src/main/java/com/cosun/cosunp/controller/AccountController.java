package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IUserInfoServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {
    /**
     * @author:homey Wong
     * @date:2018.12.19
     */


    @Autowired
    private IUserInfoServ userInfoServ;

    private static final String INDEX = "index";
    private static Logger logger = LogManager.getLogger(AccountController.class);
    @ResponseBody
    @RequestMapping(value = "/tologin")
    public ModelAndView toLoginPage() throws Exception {
        ModelAndView mav = new ModelAndView(INDEX);
        DownloadView downloadView = new DownloadView();
        downloadView.setFlag("true");
        mav.addObject("view",downloadView);
        return mav;
    }


    @ResponseBody
    @RequestMapping("/toforgetpasswordpage")
    public ModelAndView toForgetPassword(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("reset-password");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        mav.addObject("userInfo",userInfo);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute(value="view") DownloadView view, HttpSession session) throws Exception{
        ModelAndView mav;
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(),view.getPassword());
        if (userInfo != null && userInfo.getUserName() != null) {
            session.setAttribute("account",userInfo);
            view.setFullName(userInfo.getFullName());
            session.setAttribute("view",view);
            mav = new ModelAndView("mainindex");
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            mav.addObject("view",view);
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
        view.addObject("view",downloadView);
        return view;
    }

    @GetMapping("/toMainPage")
    public ModelAndView toMainPage(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("mainindex");
        DownloadView v = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        v.setFullName(userInfo.getFullName());
        if(userInfo==null) {
            view = new ModelAndView(INDEX);
        }
        view.addObject("view",v);
        return view;

    }

    @GetMapping("/saveNewPassword")
    public ModelAndView saveNewPassword(String newPassword,HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView(INDEX);
        DownloadView v = new DownloadView();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        session.removeAttribute("account");
        v.setFullName(userInfo.getFullName());
        userInfoServ.setNewPasswordByuId(userInfo.getuId(),newPassword);
        userInfo.setUserPwd(newPassword);
        session.setAttribute("account",userInfo);
        view.addObject("view",v);
        return view;


    }

}
