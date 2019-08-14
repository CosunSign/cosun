package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.DownloadView;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    private static Logger logger = LogManager.getLogger(OrderController.class);


    @Autowired
    IOrderServ orderServ;


    @ResponseBody
    @RequestMapping(value = "/createsinglegoods")
    public ModelAndView toCreateSingleGoodsPage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            DownloadView view = new DownloadView();
            ModelAndView mav = new ModelAndView("singleorder");
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sDateFormat.format(new Date());
            String orderNo = orderServ.findNewestOrderNoBySalor(userInfo.getEmpNo(),dateStr+" 00:00:00",dateStr+" 23:59:59");
            String newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo,userInfo.getShortOrderName());
           // List<Employee> salorList = orderServ.findAllSalor();

            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setFullName(userInfo.getFullName());
            OrderHead orderHead = new OrderHead();
            orderHead.setSalor(userInfo.getFullName());
            orderHead.setOrderNo(newestOrderNo);
            mav.addObject("view", view);
            //mav.addObject("salorList",salorList);
            mav.addObject("orderHead",orderHead);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addOrderToMysql")
    public ModelAndView addOrderToMysql(@RequestParam("file") MultipartFile[] file, HttpSession session, OrderHead orderHead) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("singleorder");
        orderHead.setSingleOrProject(0);//单品
        orderHead.setSalorNo(userInfo.getEmpNo());
        orderHead.setEngName(userInfo.getEngName());
        orderServ.addOrderHeadAndItemByBean(orderHead,file);
        DownloadView view = new DownloadView();
        mav.addObject("view", view);
        return mav;
    }

}
