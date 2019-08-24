package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private IFileUploadAndDownServ fileUploadAndDownServ;


    @ResponseBody
    @RequestMapping(value = "/createsinglegoods")
    public ModelAndView toCreateSingleGoodsPage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            DownloadView view = new DownloadView();
            ModelAndView mav = new ModelAndView("singleorder");
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sDateFormat.format(new Date());
            String orderNo = orderServ.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            String newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
            String oldFinishProductNo = orderServ.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            String newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(oldFinishProductNo, userInfo.getShortOrderName());
            view.setUserName(userInfo.getUserName());
            view.setPassword(userInfo.getUserPwd());
            view.setFullName(userInfo.getFullName());
            OrderHead orderHead = new OrderHead();
            orderHead.setSalor(userInfo.getFullName());
            orderHead.setNewFinishProudNo(newestFinishProdNo);
            orderHead.setOrderNo(newestOrderNo);
            mav.addObject("view", view);
            //mav.addObject("salorList",salorList);
            mav.addObject("orderHead", orderHead);
            List<OrderItem> orderItemList = new ArrayList<OrderItem>();
            mav.addObject("orderItemList", orderItemList);
            return mav;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toOrderPage")
    public ModelAndView toOrderPage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("order");
        OrderHead orderHead = new OrderHead();
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/queryOrderHeadByCondition", method = RequestMethod.POST)
    public void queryOrderHeadByCondition(OrderHead orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<OrderHead> orderList = orderServ.queryOrderHeadByCondition(orderHead);
            int recordCount = orderServ.queryOrderHeadByConditionCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(orderHead.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(orderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showOrderItemByOrderHeadId")
    public void showOrderItemByOrderHeadId(@RequestBody(required = true) OrderHead orderHead, HttpSession session, HttpServletResponse response) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<OrderHead> orderItemList = orderServ.getOrderItemByOrderHeadId(orderHead.getId());
        String str = null;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str = x.writeValueAsString(orderItemList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str); //返回前端ajax
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toUpdateOrderHeadItemByheadId")
    public ModelAndView toUpdateOrderHeadItemByheadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
        ModelAndView mav = new ModelAndView("updateorder");
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/toDeleteOrderHeadItemByheadId")
    public ModelAndView toDeleteOrderHeadItemByheadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
        ModelAndView mav = new ModelAndView("deleteorder");
        List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
        mav.addObject("orderHeadList", orderHeadList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 0);
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/deleteOrderItemByItemId", method = RequestMethod.POST)
    public void deleteOrderItemByItemId(OrderHead orderHead, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            int isdeleteall = orderServ.deleteOrderItemByItemId(orderHead.getId());
            List<OrderHead> orderList = orderServ.queryOrderHeadByCondition(orderHead);
            int recordCount = orderServ.queryOrderHeadByConditionCount(orderHead);
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            if (orderList.size() > 0) {
                orderList.get(0).setMaxPage(maxPage);
                orderList.get(0).setRecordCount(recordCount);
                orderList.get(0).setCurrentPage(orderHead.getCurrentPage());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(orderList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toDeleteOrderHeadItemByItemid")
    public ModelAndView toDeleteOrderHeadItemByItemid(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        int isdeleteall = orderServ.deleteOrderItemByItemId(orderHead.getId());
        if (isdeleteall == 0) {
            orderHead = orderServ.getOrderHeadByHeadId(orderHead.getId());
            ModelAndView mav = new ModelAndView("deleteorder");
            List<OrderHead> orderHeadList = orderServ.getOrderItemByHeadId(id);
            mav.addObject("orderHeadList", orderHeadList);
            mav.addObject("orderHead", orderHead);
            mav.addObject("flag", 2);
            return mav;
        } else {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView mav = new ModelAndView("order");
            List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
            List<OrderHead> orderNoList = orderServ.findAllOrderNo();
            List<String> prodNameList = orderServ.findAllProdName();
            List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
            int recordCount = orderServ.findAllOrderHeadCount();
            int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
            orderHead.setMaxPage(maxPage);
            orderHead.setRecordCount(recordCount);
            mav.addObject("salorList", salorList);
            mav.addObject("orderNoList", orderNoList);
            mav.addObject("prodNameList", prodNameList);
            mav.addObject("orderList", orderList);
            mav.addObject("orderHead", orderHead);
            mav.addObject("flag", 2);
            return mav;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toDeleteAllOrderByHeadId")
    public ModelAndView toDeleteAllOrderByHeadId(HttpSession session, OrderHead orderHead) throws Exception {
        Integer id = orderHead.getId();
        orderServ.deleteAllOrderByHeadId(orderHead.getId());
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("order");
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 2);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/createprojectgoods")
    public ModelAndView createprojectgoods(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        OrderHead orderHead = new OrderHead();
        ModelAndView mav = new ModelAndView("projectorder");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sDateFormat.format(new Date());
        String oldFinishProductNo = orderServ.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
        String newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(oldFinishProductNo, userInfo.getShortOrderName());
        String orderNo = orderServ.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
        String newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
        orderHead.setOrderNo(newestOrderNo);
        orderHead.setShortEngName(userInfo.getShortOrderName());
        orderHead.setNewFinishProudNo(newestFinishProdNo);
        orderHead.setSalor(userInfo.getFullName());
        mav.addObject("orderHead", orderHead);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        mav.addObject("orderItemList", orderItemList);
        mav.addObject("flag", 0);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/addProjectOrderToMysql")
    public ModelAndView addProjectOrderToMysql(@RequestParam("file") MultipartFile[] file, OrderHead orderHead, HttpSession session, HttpServletRequest request) throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(orderHead.getOrderItemList());
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Object o;
        JSONObject jsonObject2;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        OrderItem oi = null;
        OrderHead oh = null;
        for (int a = 0; a < jsonArray.size(); a++) {
            oi = new OrderItem();
            o = jsonArray.get(a);
            jsonObject2 = JSONObject.fromObject(o);
            oh = (OrderHead) JSONObject.toBean(jsonObject2, OrderHead.class);
            if (oh != null) {
                oi.setProductBigType(oh.getProductBigType()); //产品大类
                oi.setProductMainShape(oh.getProductMainShape()); //产品主体
                oi.setNewFinishProudNo(oh.getNewFinishProudNo()); //新成品编号
                oi.setProductSize(oh.getProductSize()); //产品尺寸
                oi.setEdgeHightSize(oh.getEdgeHightSize()); //边高尺寸
                oi.setMainMateriAndArt(oh.getMainMateriAndArt());//主体材质需求及工艺
                oi.setBackInstallSelect(oh.getBackInstallSelect());//背部安装选项
                oi.setElectMateriNeeds(oh.getElectMateriNeeds());//电子类辅料需求
                oi.setInstallTransfBacking(oh.getInstallTransfBacking());//安装运输包装
                oi.setItemDeliverTimeStr(oh.getItemDeliverTimeStr());
                oi.setOtherRemark(oh.getOtherRemark());//其它说明
                oi.setNeedNum(oh.getNeedNum());//商品数量
                oi.setProductName(oh.getProductName());
                orderItemList.add(oi);
            }
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        oh.setOrderTimeStr(dateStr);
        oh.setState(0);
        String returnmessage = orderServ.saveProjectData(oh, orderItemList, userInfo, file);
        ModelAndView mav = new ModelAndView("order");
        List<Employee> salorList = fileUploadAndDownServ.findAllSalorByDeptName();
        List<OrderHead> orderNoList = orderServ.findAllOrderNo();
        List<String> prodNameList = orderServ.findAllProdName();
        List<OrderHead> orderList = orderServ.findAllOrderHead(orderHead);
        int recordCount = orderServ.findAllOrderHeadCount();
        int maxPage = recordCount % orderHead.getPageSize() == 0 ? recordCount / orderHead.getPageSize() : recordCount / orderHead.getPageSize() + 1;
        orderHead.setMaxPage(maxPage);
        orderHead.setRecordCount(recordCount);
        mav.addObject("salorList", salorList);
        mav.addObject("orderNoList", orderNoList);
        mav.addObject("prodNameList", prodNameList);
        mav.addObject("orderList", orderList);
        mav.addObject("orderHead", orderHead);
        mav.addObject("flag", 1);
        mav.addObject("returnmessage", returnmessage);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/addOrderToMysql")
    public ModelAndView addOrderToMysql(@RequestParam("file") MultipartFile[] file, HttpSession session, OrderHead orderHead) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView mav = new ModelAndView("order");
        orderHead.setSingleOrProject(0);//单品
        orderHead.setSalorNo(userInfo.getEmpNo());
        orderHead.setEngName(userInfo.getEngName());
        orderServ.addOrderHeadAndItemByBean(orderHead, file);
        DownloadView view = new DownloadView();
        mav.addObject("view", view);
        mav.addObject("flag", 1);
        return mav;
    }

}
