package com.cosun.cosunp.controller;

import com.cosun.cosunp.service.IrulesServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:homey Wong
 * @date:2019/5/20 0020 下午 1:39
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/rules")
public class RulesController {

    private static Logger logger = LogManager.getLogger(RulesController.class);

    @Autowired
    IrulesServ rulesServ;



}
