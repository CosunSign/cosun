package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author:homey Wong
 * @date:2019/1/4 0004 上午 10:40
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/order")
public class OrderController {


    @ResponseBody
    @RequestMapping(value = "/createsinglegoods")
    public ModelAndView toCreateSingleGoodsPage(HttpSession session) throws Exception {
        UserInfo userInfo =(UserInfo) session.getAttribute("account");
        DownloadView view = new DownloadView();
        ModelAndView mav = new ModelAndView("singleorder");
        view.setUserName(userInfo.getUserName());
        view.setPassword(userInfo.getUserPwd());
        mav.addObject("view",view);
        return mav;
    }

}
