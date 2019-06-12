package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private String sexStr;
    private Integer deptId;//部门编号
    private String empNo;//工号
    private Integer positionId;//职位ID
    private Date incompdate;//入厂时间
    private String incomdateStr;
    private String deptName;
    private String positionName;

    private String positionLevel;

    private String endLeaveStr;
    private String beginLeaveStr;

    private List<Integer> deptIds;

    private List<Integer> sexIds;

    private List<Integer> positionIds;

    private List<Integer> ids;



    private List<Integer> nameIds;

    private String startIncomDateStr;
    private String endIncomDateStr;
    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;

    public List<Integer> getSexIds() {
        return sexIds;
    }

    public void setSexIds(List<Integer> sexIds) {
        this.sexIds = sexIds;
    }

    private String deptIdsstr;
    private String positionIdsstr;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getEndLeaveStr() {
        return endLeaveStr;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
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

    public String getDeptIdsstr() {
        return deptIdsstr;
    }

    public void setDeptIdsstr(String deptIdsstr) {
        this.deptIdsstr = deptIdsstr;
    }

    public String getPositionIdsstr() {
        return positionIdsstr;
    }

    public void setPositionIdsstr(String positionIdsstr) {
        this.positionIdsstr = positionIdsstr;
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

    public String getStartIncomDateStr() {
        return startIncomDateStr;
    }

    public void setStartIncomDateStr(String startIncomDateStr) {
        this.startIncomDateStr = startIncomDateStr;
    }

    public String getEndIncomDateStr() {
        return endIncomDateStr;
    }

    public void setEndIncomDateStr(String endIncomDateStr) {
        this.endIncomDateStr = endIncomDateStr;
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

    public String getSexStr() {
        if (this.sex != null && this.sex == 1) {
            return "男";
        } else if (this.sex != null && this.sex == 0) {
            return "女";
        }
        return null;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getIncomdateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.incompdate != null)
            return formatter.format(this.incompdate);
        return this.incomdateStr;
    }

    public void setIncomdateStr(String incomdateStr) {
        this.incomdateStr = incomdateStr;
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
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
