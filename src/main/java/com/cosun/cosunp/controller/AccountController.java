package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IUserInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ModelAndView toForgetPassword() throws Exception {
        ModelAndView mav = new ModelAndView("reset-password");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public ModelAndView login(@ModelAttribute(value="view") DownloadView view, HttpSession session) throws Exception{
        ModelAndView mav;
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(),view.getPassword());
        if (userInfo != null && userInfo.getUserName() != null) {
            session.setAttribute("account",userInfo);
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
    public ModelAndView logout(HttpSession session) {
        ModelAndView view = new ModelAndView(INDEX);
        // 移除session
        session.removeAttribute("account");
        DownloadView downloadView = new DownloadView();
        downloadView.setFlag("true");
        view.addObject("view",downloadView);
        return view;
    }

}
