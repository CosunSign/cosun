package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/5/13 0013 下午 12:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Leave implements Serializable {

    private static final long serialVersionUID = -5472326875954062283L;
    private Integer id;
    private Integer employeeId;
    private Date beginLeave;
    private Date endLeave;
    private Double leaveLong;
    private String leaveDescrip;
    private String remark;


    //回显信息
    private String positionName;
    private String deptName;
    private String name;
    private String sex;
    private String empNo;
    private String incomdateStr;
    private String endLeaveStr;
    private String beginLeaveStr;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getIncomdateStr() {
        return incomdateStr;
    }

    public void setIncomdateStr(String incomdateStr) {
        this.incomdateStr = incomdateStr;
    }

    public String getEndLeaveStr() {
        return endLeaveStr;
    }

    public void setEndLeaveStr(String endLeaveStr) {
        this.endLeaveStr = endLeaveStr;
    }

    public String getBeginLeaveStr() {
        return beginLeaveStr;
    }

    public void setBeginLeaveStr(String beginLeaveStr) {
        this.beginLeaveStr = beginLeaveStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getBeginLeave() {
        return beginLeave;
    }

    public void setBeginLeave(Date beginLeave) {
        this.beginLeave = beginLeave;
    }

    public Date getEndLeave() {
        return endLeave;
    }

    public void setEndLeave(Date endLeave) {
        this.endLeave = endLeave;
    }

    public Double getLeaveLong() {
        return leaveLong;
    }

    public void setLeaveLong(Double leaveLong) {
        this.leaveLong = leaveLong;
    }

    public String getLeaveDescrip() {
        return leaveDescrip;
    }

    public void setLeaveDescrip(String leaveDescrip) {
        this.leaveDescrip = leaveDescrip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
