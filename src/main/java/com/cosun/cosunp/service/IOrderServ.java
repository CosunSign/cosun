package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IOrderServ {

    List<Employee> findAllSalor() throws Exception;

    void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception;

    String findNewestOrderNoBySalor(String empNo,String startTime,String endTime) throws Exception;
}
