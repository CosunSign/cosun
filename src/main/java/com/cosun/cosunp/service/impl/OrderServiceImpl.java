package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.OrderMapper;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.FileUtil;
import com.cosun.cosunp.tool.StringUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Date: 2019/8/13 0013 上午 9:31
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements IOrderServ {

    private static Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    private OrderMapper orderMapper;

    public List<Employee> findAllSalor() throws Exception {
        return orderMapper.findAllSalor();
    }

    public List<OrderHead> getOrderItemByHeadId(Integer id) throws Exception {
        return orderMapper.getOrderItemByHeadId(id);
    }

    public OrderHead getOrderHeadByHeadId(Integer id) throws Exception {
        return orderMapper.getOrderHeadByHeadId(id);
    }

    public void deleteAllOrderByHeadId(Integer headId) throws Exception {
        orderMapper.deleAllOrderItemByHeadId(headId);
        orderMapper.deleteAllOrderHeadById(headId);
    }

    public OrderItem getOrderItemById(Integer itemId) throws Exception {
        return orderMapper.getOrderItemById(itemId);
    }

    public int deleteOrderItemByItemId(Integer id) throws Exception {
        List<OrderItem> ois = orderMapper.getAllOrderItemBy(id);
        if (ois.size() > 1) {
            orderMapper.updateOrderHeadTotalNum(ois.get(0).getOrderHeadId(), ois.size());
            orderMapper.deleteOrderItemByItemId(id);
            return 0;
        } else {
            orderMapper.deleteOrderItemByItemId(id);
            orderMapper.deleteAllOrderHeadAndItemByItemId(id);
            return 1;
        }
    }

    @Transactional
    public void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        orderHead.setState(0);
        Integer num = 1;
        orderHead.setOrderSetNum(num);
        orderMapper.addOrderHeadByBean(orderHead);
        orderHead.setOrderHeadId(orderHead.getId());
        orderHead.setProductName(orderHead.getProductTotalName());
        orderHead.setItemCreateTimeStr(dateStr);
        orderMapper.addOrderItemByBean(orderHead);

        String urlName;
        String fileName;
        //存储订单所带的附件
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
    }

    public void updateProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] files) throws Exception {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(new Date());
        orderHead.setOrderSetNum(ois.size());
        OrderHead oldHead = orderMapper.getOldHeadByOrderNo(orderHead.getOrderNo());
        if (!oldHead.getProductTotalName().equals(orderHead.getProductTotalName()) || oldHead.getOrderSetNum() != orderHead.getOrderSetNum()) {
            orderHead.setHeadUpdateTimeStr(dateStr);
            orderHead.setUpdateHeadTimes((oldHead.getUpdateHeadTimes() == null ? 0 : oldHead.getUpdateHeadTimes()) + 1);
            orderMapper.updateOrderHeadByOrderNo(orderHead);
        }

        OrderItem oldItem;
        OrderItem newItem;
        for (int a = 0; a < ois.size(); a++) {
            newItem = ois.get(a);
            ois.get(a).setOrderHeadId(oldHead.getId());
            oldItem = orderMapper.getOldItemByOrderIdandNewestFinishNo(ois.get(a));
            if (!oldItem.getProductName().equals(newItem.getProductName()) || !oldItem.getNewFinishProudNo().equals(newItem.getNewFinishProudNo()) ||
                    !oldItem.getProductBigType().equals(newItem.getProductBigType()) || !oldItem.getProductMainShape().equals(newItem.getProductMainShape()) ||
                    oldItem.getNeedNum() != newItem.getNeedNum() || !oldItem.getProductSize().equals(newItem.getProductSize()) ||
                    !oldItem.getEdgeHightSize().equals(newItem.getEdgeHightSize()) || !oldItem.getItemDeliverTimeStr().equals(newItem.getItemDeliverTimeStr()) ||
                    !oldItem.getBackInstallSelect().equals(newItem.getBackInstallSelect()) || !oldItem.getMainMateriAndArt().equals(newItem.getMainMateriAndArt()) ||
                    !oldItem.getElectMateriNeeds().equals(newItem.getElectMateriNeeds()) || !oldItem.getInstallTransfBacking().equals(newItem.getInstallTransfBacking()) ||
                    !oldItem.getOtherRemark().equals(newItem.getOtherRemark())) {
                ois.get(a).setItemUpdateTimeStr(dateStr);
                ois.get(a).setUpdateItemTimes((oldItem.getUpdateItemTimes() == null ? 0 : oldItem.getUpdateItemTimes()) + 1);
                ois.get(a).setId(oldItem.getId());
                orderMapper.updateOrderItemById(ois.get(a));
            }
        }


        String urlName;
        String fileName;
        //存储订单所带的附件
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
    }

    public List<String> findAllUrlByOrderNo(String orderNo) throws Exception {
        return orderMapper.findAllUrlByOrderNo(orderNo);
    }

    public void updateStateByOrderNo(OrderHead orderHead) throws Exception {
        orderMapper.updateStateByOrderNo(orderHead);
    }

    public String findNewestOrderNoBySalor(String empNo, String startTime, String endTime) throws Exception {
        return orderMapper.findNewestOrderNoBySalor(empNo, startTime, endTime);
    }

    public String findNewestFinishProdNoByOldFinishProdNo(String empNo, String startTime, String endTime) throws Exception {
        return orderMapper.findNewestFinishProdNoByOldFinishProdNo(empNo, startTime, endTime);
    }

    public List<OrderHead> getOrderItemByOrderHeadId(Integer id) throws Exception {
        return orderMapper.getOrderItemByOrderHeadId(id);
    }

    public String saveProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo, MultipartFile[] files) throws Exception {
        orderHead.setSingleOrProject(1);//项目类
        orderHead.setSalorNo(userInfo.getEmpNo());
        orderHead.setEngName(userInfo.getEngName());
        String orderNo;
        String newestOrderNo;
        SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1 = sDateFormat1.format(new Date());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sDateFormat.format(new Date());
        OrderHead isExsitOrderNo = orderMapper.getOrderNoBYOrderNo(orderHead.getOrderNo());
        String newestProductNo;
        OrderItemAppend oia;
        OrderItem isExsitProNo;
        String newestFinishProdNo;
        String returnmessage = "";
        String returnmessage2 = "";
        if (isExsitOrderNo == null) {
            orderHead.setOrderSetNum(ois.size());
            orderMapper.addOrderHeadByBean(orderHead);
        } else {
            returnmessage += "订单单号已存在，新订单单号为:" + orderHead.getOrderNo() + ".";
            orderNo = orderMapper.findNewestOrderNoBySalor(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
            newestOrderNo = StringUtil.increateOrderByOlderOrderNo(orderNo, userInfo.getShortOrderName());
        }
        for (int a = 0; a < ois.size(); a++) {
            ois.get(a).setOrderHeadId(orderHead.getId());
            ois.get(a).setItemCreateTimeStr(dateStr1);
            isExsitProNo = orderMapper.getProductNoByProductNoBeforSave(ois.get(a).getNewFinishProudNo());
            if (isExsitProNo == null) {
                orderMapper.addOrderItemByItemBean(ois.get(a));
            } else {
                returnmessage2 += ois.get(a).getNewFinishProudNo() + ",";
                newestProductNo = orderMapper.findNewestFinishProdNoByOldFinishProdNo(userInfo.getEmpNo(), dateStr + " 00:00:00", dateStr + " 23:59:59");
                newestFinishProdNo = StringUtil.increateFinishiNoByOrldFinishiNo(newestProductNo, userInfo.getShortOrderName());
                ois.get(a).setNewFinishProudNo(newestFinishProdNo);
                orderMapper.addOrderItemByItemBean(ois.get(a));
            }
        }

        String fileName;
        String urlName;
        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (file.getOriginalFilename().trim().length() > 0) {
                    FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                    fileName = file.getOriginalFilename();
                    urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\" + fileName;
                    if (urlName != null) {
                        oia = new OrderItemAppend();
                        oia.setFileName(fileName);
                        oia.setOrderNo(orderHead.getOrderNo());
                        oia.setUrlName(urlName);
                        oia.setHeadId(orderHead.getId());
                        orderMapper.saveOrderItemAppend(oia);
                    }
                }
            }
        }
        if (returnmessage2.trim().length() > 0) {
            returnmessage2 = "新成品编号已存在，新的为:" + returnmessage2;
        }
        return returnmessage + returnmessage2;
    }

    public List<OrderHead> findAllOrderNo() throws Exception {
        return orderMapper.findAllOrderNo();
    }

    public List<String> findAllProdName() throws Exception {
        return orderMapper.findAllProdName();
    }


    public List<OrderHead> findAllOrderHead(OrderHead orderHead) throws Exception {
        return orderMapper.findAllOrderHead(orderHead);
    }

    public int findAllOrderHeadCount() throws Exception {
        return orderMapper.findAllOrderHeadCount();
    }

    public List<OrderHead> queryOrderHeadByCondition(OrderHead orderHead) throws Exception {
        return orderMapper.queryOrderHeadByCondition(orderHead);
    }

    public int queryOrderHeadByConditionCount(OrderHead orderHead) throws Exception {
        return orderMapper.queryOrderHeadByConditionCount(orderHead);
    }

}
