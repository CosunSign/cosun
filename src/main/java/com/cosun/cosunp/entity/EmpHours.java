package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/6/24 0024 上午 11:24
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class EmpHours implements Serializable {

    private Integer id;
    private String name; //姓名
    private String empNo; //工号
    private String deptName;//部门
    private Double zhengbanHours;//正班出勤工时
    private Double usualExtHours;//平时加班
    private Double workendHours;//周末加班
    private Double chinaPaidLeave;//国家有薪假
    private Double otherPaidLeave;//其它有薪假
    private Double leaveOfAbsense;//事假
    private Double sickLeave;//病假
    private Double otherAllo;//其它补贴
    private Double fullWorkReword;//全勤奖
    private Double foodExpense;//伙食费
    private Double roomOrWaterEleExpense;//房租及水电费
    private Double oldAgeINsuran;//养老险
    private Double medicalInsuran;//医疗险
    private Double unEmployeeInsur;//失业险
    private Double accumulaFund;//公积金
    private Double errorInWork;//工作失误
    private Double sixDeductions;//6个代扣项
    private Double meritScore;//绩效分
    private String yearMonth;
    private String yearMonthStr;
    private String remark;

    private Integer type;

    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;

    public Double getSixDeductions() {
        return sixDeductions;
    }

    public void setSixDeductions(Double sixDeductions) {
        this.sixDeductions = sixDeductions;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageTotalNum() {
        if (this.currentPage != 0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getYearMonthStr() {
        return yearMonthStr;
    }

    public void setYearMonthStr(String yearMonthStr) {
        this.yearMonthStr = yearMonthStr;
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

    public Double getZhengbanHours() {
        return zhengbanHours;
    }

    public void setZhengbanHours(Double zhengbanHours) {
        this.zhengbanHours = zhengbanHours;
    }

    public Double getUsualExtHours() {
        return usualExtHours;
    }

    public void setUsualExtHours(Double usualExtHours) {
        this.usualExtHours = usualExtHours;
    }

    public Double getWorkendHours() {
        return workendHours;
    }

    public void setWorkendHours(Double workendHours) {
        this.workendHours = workendHours;
    }

    public Double getChinaPaidLeave() {
        return chinaPaidLeave;
    }

    public void setChinaPaidLeave(Double chinaPaidLeave) {
        this.chinaPaidLeave = chinaPaidLeave;
    }

    public Double getOtherPaidLeave() {
        return otherPaidLeave;
    }

    public void setOtherPaidLeave(Double otherPaidLeave) {
        this.otherPaidLeave = otherPaidLeave;
    }

    public Double getLeaveOfAbsense() {
        return leaveOfAbsense;
    }

    public void setLeaveOfAbsense(Double leaveOfAbsense) {
        this.leaveOfAbsense = leaveOfAbsense;
    }

    public Double getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Double sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Double getOtherAllo() {
        return otherAllo;
    }

    public void setOtherAllo(Double otherAllo) {
        this.otherAllo = otherAllo;
    }

    public Double getFullWorkReword() {
        return fullWorkReword;
    }

    public void setFullWorkReword(Double fullWorkReword) {
        this.fullWorkReword = fullWorkReword;
    }

    public Double getFoodExpense() {
        return foodExpense;
    }

    public void setFoodExpense(Double foodExpense) {
        this.foodExpense = foodExpense;
    }

    public Double getRoomOrWaterEleExpense() {
        return roomOrWaterEleExpense;
    }

    public void setRoomOrWaterEleExpense(Double roomOrWaterEleExpense) {
        this.roomOrWaterEleExpense = roomOrWaterEleExpense;
    }

    public Double getOldAgeINsuran() {
        return oldAgeINsuran;
    }

    public void setOldAgeINsuran(Double oldAgeINsuran) {
        this.oldAgeINsuran = oldAgeINsuran;
    }

    public Double getMedicalInsuran() {
        return medicalInsuran;
    }

    public void setMedicalInsuran(Double medicalInsuran) {
        this.medicalInsuran = medicalInsuran;
    }

    public Double getUnEmployeeInsur() {
        return unEmployeeInsur;
    }

    public void setUnEmployeeInsur(Double unEmployeeInsur) {
        this.unEmployeeInsur = unEmployeeInsur;
    }

    public Double getAccumulaFund() {
        return accumulaFund;
    }

    public void setAccumulaFund(Double accumulaFund) {
        this.accumulaFund = accumulaFund;
    }

    public Double getErrorInWork() {
        return errorInWork;
    }

    public void setErrorInWork(Double errorInWork) {
        this.errorInWork = errorInWork;
    }

    public Double getMeritScore() {
        return meritScore;
    }

    public void setMeritScore(Double meritScore) {
        this.meritScore = meritScore;
    }
}
