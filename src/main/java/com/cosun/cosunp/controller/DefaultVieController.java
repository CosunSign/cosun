package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author:homey Wong
 * @date:2018/12/28 0028 下午 4:12
 * @Description: 黑认路径为index.html
 * @Modified By:
 * @Modified-date:
 */
@Controller
public class DefaultVieController {

    @ResponseBody
    @RequestMapping("/")
    public ModelAndView toIndexPage() throws Exception {
        ModelAndView mav = new ModelAndView("index");
        DownloadView downloadView = new DownloadView();
        downloadView.setFlag("true");
        mav.addObject("view",downloadView);
        return mav;
    }



}
