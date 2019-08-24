package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.OrderItem;
import com.cosun.cosunp.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOrderServ {

    List<Employee> findAllSalor() throws Exception;

    void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception;

    String findNewestOrderNoBySalor(String empNo,String startTime,String endTime) throws Exception;

    String saveProjectData(OrderHead orderHead, List<OrderItem> ois, UserInfo userInfo,MultipartFile[] file) throws Exception;

    String findNewestFinishProdNoByOldFinishProdNo(String empNo,String startTime,String endTime) throws Exception;

    List<OrderHead> getOrderItemByHeadId(Integer id) throws Exception;

    OrderHead getOrderHeadByHeadId(Integer id) throws Exception;

    List<OrderHead> findAllOrderNo() throws Exception;

    List<String> findAllProdName() throws Exception;

    List<OrderHead> findAllOrderHead(OrderHead orderHead) throws Exception;

    void deleteAllOrderByHeadId(Integer headId) throws Exception;

    int findAllOrderHeadCount() throws Exception;

    List<OrderHead> queryOrderHeadByCondition(OrderHead orderHead) throws Exception;

    int queryOrderHeadByConditionCount(OrderHead orderHead) throws Exception;

    List<OrderHead> getOrderItemByOrderHeadId(Integer id) throws Exception;

    int deleteOrderItemByItemId(Integer id) throws Exception;


}
