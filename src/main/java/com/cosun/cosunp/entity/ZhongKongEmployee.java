package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/10/8 0008 下午 2:14
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ZhongKongEmployee implements Serializable {

    private static final long serialVersionUID = -6416619295751473097L;

    private Integer id;
    private Integer EnrollNumber;
    private String empNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnrollNumber() {
        return EnrollNumber;
    }

    public void setEnrollNumber(Integer enrollNumber) {
        EnrollNumber = enrollNumber;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }
}
