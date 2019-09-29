package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/6/28 0028 上午 11:46
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class SubEmphours implements Serializable {

    private static final long serialVersionUID = 6097579885742254768L;
    private Integer id; //序号
    private String month; //月份
    private String name; //姓名
    private String empNo; //工号
    private String deptName; //部门
    private String positionName;//职位
    private double zhengbanWorkHours;//正常出勤工时
    private double usualExtWorkHoursl;//平时加班工时
    private double weekendWorkHours;//周末加班工时
    private double legalPaidLeaveHours;//法定有薪假
    private double legalDayWorkHours;//法定节假日加班工时
    private double otherpaidLeaveHours;//其它有薪假工时

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getZhengbanWorkHours() {
        return zhengbanWorkHours;
    }

    public void setZhengbanWorkHours(double zhengbanWorkHours) {
        this.zhengbanWorkHours = zhengbanWorkHours;
    }

    public double getUsualExtWorkHoursl() {
        return usualExtWorkHoursl;
    }

    public void setUsualExtWorkHoursl(double usualExtWorkHoursl) {
        this.usualExtWorkHoursl = usualExtWorkHoursl;
    }

    public double getWeekendWorkHours() {
        return weekendWorkHours;
    }

    public void setWeekendWorkHours(double weekendWorkHours) {
        this.weekendWorkHours = weekendWorkHours;
    }

    public double getLegalPaidLeaveHours() {
        return legalPaidLeaveHours;
    }

    public void setLegalPaidLeaveHours(double legalPaidLeaveHours) {
        this.legalPaidLeaveHours = legalPaidLeaveHours;
    }

    public double getLegalDayWorkHours() {
        return legalDayWorkHours;
    }

    public void setLegalDayWorkHours(double legalDayWorkHours) {
        this.legalDayWorkHours = legalDayWorkHours;
    }

    public double getOtherpaidLeaveHours() {
        return otherpaidLeaveHours;
    }

    public void setOtherpaidLeaveHours(double otherpaidLeaveHours) {
        this.otherpaidLeaveHours = otherpaidLeaveHours;
    }
}
