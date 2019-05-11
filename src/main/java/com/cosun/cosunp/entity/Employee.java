package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/3/5 0005 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 3941220683407513983L;
    private Integer id;
    private String name;//姓名
    private Integer sex;//性别
    private Integer deptId;//部门编号
    private String empNo;//工号
    private String positionId;//职位ID
    private Date incompdate;//入厂时间

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Date getIncompdate() {
        return incompdate;
    }

    public void setIncompdate(Date incompdate) {
        this.incompdate = incompdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


}
