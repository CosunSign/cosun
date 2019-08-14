package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OrderHead;
import com.cosun.cosunp.entity.OrderItemAppend;
import com.cosun.cosunp.mapper.OrderMapper;
import com.cosun.cosunp.service.IOrderServ;
import com.cosun.cosunp.tool.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public void addOrderHeadAndItemByBean(OrderHead orderHead, MultipartFile[] files) throws Exception {
        orderMapper.addOrderHeadByBean(orderHead);
        orderHead.setOrderHeadId(orderHead.getId());
        orderMapper.addOrderItemByBean(orderHead);

        String urlName;
        String fileName;
        //存储订单所带的附件
        if (files.length > 0) {
            OrderItemAppend oia;
            for (MultipartFile file : files) {
                FileUtil.uploadOrderAppend(finalDirPath, orderHead.getEngName(), orderHead.getOrderNo(), file);
                fileName = file.getOriginalFilename();
                urlName = "order" + "\\" + orderHead.getEngName() + "\\" + orderHead.getOrderNo() + "\\"+fileName;
                if (urlName != null) {
                    oia = new OrderItemAppend();
                    oia.setFileName(fileName);
                    oia.setOrderNo(orderHead.getOrderNo());
                    oia.setUrlName(urlName);
                    oia.setItemId(orderHead.getItemId());
                    orderMapper.saveOrderItemAppend(oia);
                }
            }
        }


    }

   public String findNewestOrderNoBySalor(String empNo,String startTime,String endTime) throws Exception {
        return orderMapper.findNewestOrderNoBySalor(empNo,startTime,endTime);
   }



}
