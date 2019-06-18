package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.Compute;
import com.cosun.cosunp.service.IPersonServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

/**
 * @author:homey Wong
 * @date:2019/6/17  下午 4:28
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    private static Logger logger = LogManager.getLogger(FinanceController.class);


    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView tocomputeworkdate() throws Exception {
        try {
            ModelAndView view = new ModelAndView("finance");
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }
}
