package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IUserInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @ResponseBody
    @RequestMapping(value = "/tologin")
    public ModelAndView toLoginPage() throws Exception {
        ModelAndView mav = new ModelAndView("sign-in");
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
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute(value="view") DownloadView view, HttpSession session) throws Exception{
        ModelAndView mav;
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(view.getUserName(),view.getPassword());
        if (userInfo != null && userInfo.getUserName() != null) {
            mav = new ModelAndView(INDEX);
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            mav.addObject("view",view);
            return mav;
        }
        mav = new ModelAndView("sign-in");
        view.setUserName(null);
        view.setPassword(null);
        view.setFlag("false");
        mav.addObject("view", view);
        return mav;

    }
}
