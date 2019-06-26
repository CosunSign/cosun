package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/25 0025 下午 2:01
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FinanceImportData implements Serializable {


    private Integer id;
    private String empNo;//工号
    private String name;//姓名
    private String deptName;//部门
    private Double legalHolidWorkHours;//法定节假日加班工时
    private Double sellActual;//业务实际
    private Double sellThreshold;//业务阈值
    private Double sellLevelSalary;//业务等级工资
    private Double houseSubsidy;//房补
    private Double hotTempOrOtherAllow;//高温等其它补贴
    private Double workYearsSalary;//工龄工资
    private Double sellCommi;//业务提成
    private String remark;
    private String yearMonth;


    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;

    private List<Integer> ids;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
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

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Double getLegalHolidWorkHours() {
        return legalHolidWorkHours;
    }

    public void setLegalHolidWorkHours(Double legalHolidWorkHours) {
        this.legalHolidWorkHours = legalHolidWorkHours;
    }

    public Double getSellActual() {
        return sellActual;
    }

    public void setSellActual(Double sellActual) {
        this.sellActual = sellActual;
    }

    public Double getSellThreshold() {
        return sellThreshold;
    }

    public void setSellThreshold(Double sellThreshold) {
        this.sellThreshold = sellThreshold;
    }

    public Double getSellLevelSalary() {
        return sellLevelSalary;
    }

    public void setSellLevelSalary(Double sellLevelSalary) {
        this.sellLevelSalary = sellLevelSalary;
    }

    public Double getHouseSubsidy() {
        return houseSubsidy;
    }

    public void setHouseSubsidy(Double houseSubsidy) {
        this.houseSubsidy = houseSubsidy;
    }

    public Double getHotTempOrOtherAllow() {
        return hotTempOrOtherAllow;
    }

    public void setHotTempOrOtherAllow(Double hotTempOrOtherAllow) {
        this.hotTempOrOtherAllow = hotTempOrOtherAllow;
    }

    public Double getWorkYearsSalary() {
        return workYearsSalary;
    }

    public void setWorkYearsSalary(Double workYearsSalary) {
        this.workYearsSalary = workYearsSalary;
    }

    public Double getSellCommi() {
        return sellCommi;
    }

    public void setSellCommi(Double sellCommi) {
        this.sellCommi = sellCommi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
