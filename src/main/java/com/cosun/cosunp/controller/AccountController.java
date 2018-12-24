package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IUserInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping("/tologin")
    public ModelAndView toLoginPage() throws Exception {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{
        ModelAndView mav;
        String userName = request.getParameter("username");
        String userPwd = request.getParameter("password");
        UserInfo userInfo = userInfoServ.findUserByUserNameandPassword(userName, userPwd);
        if (userInfo != null && userInfo.getUserName() != null) {
            mav = new ModelAndView("filemanage/filemanage");
            session.setAttribute("username", userInfo.getUserName());
            session.setAttribute("password", userInfo.getUserPwd());
            mav.addObject("username", userName);
            mav.addObject("password", userPwd);
            return mav;
        }
        mav = new ModelAndView(INDEX);
        mav.addObject("flag", "false");
        return mav;

    }
}
