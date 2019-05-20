package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private Integer sex;
    private String empNo;
    private String incomdateStr;
    private String endLeaveStr;
    private String beginLeaveStr;
    private Integer positionId;
    private Integer deptId;
    private List<Integer> deptIds;

    private List<Integer> positionIds;

    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;


    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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
        if(this.currentPage!=0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
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
