package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @date:2019/6/26 0026 上午 10:17
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class SalaryDataOutPut implements Serializable {
    private static final long serialVersionUID = -6881009009574748691L;
    private Integer id;//序号
    private String yearMonth;//年月
    private String bigDeptName;//大部门
    private String incomDate;//入厂日期
    private String deptName;//部门
    private String positionName;//职务
    private String positionAttrName;//职位
    private String empNo;//工号
    private String name;//姓名
    private String inCompDate;//入职时间
    private Double basickWorkHours;//基本工时
    private Double norAttenHours;//正常出勤工时
    private Double norAttendSalary;//正常出勤工资;
    private Double chinaPailLeavHours;//法定有薪假工时
    private Double chinaPaidLeavSalary;//法定有薪假工资
    private Double otherPaidLeavHours;//其它有薪假工时
    private Double otherPaidLeavSalary;//其它有薪假工资
    private Double basicSalarySubTotal;//基本工资小计
    private Double usualExtraHours;//平时加班工时
    private Double usralExtraSalary;//平时加班费;
    private Double weekendWorkHours;//周末加班工时
    private Double weekendWorkSalary;//周末加班费
    private Double chinaHoliWorkHours;//法定假日加班工时
    private Double chinaHoliWorkSalary;//法定假日加班费
    private Double compressSalary;//综合技能
    private Double jobSalary;//岗位工资
    private Double positionSalary;//职务工资
    private Double meritSalary;//绩效奖金
    private Double meritScore;//绩效分数
    private Double subbonusTotal;//奖金/技能小计
    private Double salorLevelSalary;//业务等级工资
    private Double salrActuGetSalary;//业务等级实得
    private Double houseOrTELSubsidy;//房补/话补
    private Double hotTempOrOtherAllow;//高温补贴及其它
    private Double fullWorkReword;//全勤奖 importdata
    private Double workYearsSalary;//工龄工资 importdata
    private Double sellCommi;//业务提成 importdata
    private Double compreSalary;    //综合工资
    private Double buckFoodCost;//扣代付餐费
    private Double buckWaterEleCost;//扣代付水电
    private Double buckOldAgeInsurCost;//扣代付养老险
    private Double buckMedicInsurCost;//扣代付医疗险
    private Double buckUnEmployCost; //扣代付失业险
    private Double buckAccumCost;//扣代付公积金
    private Double otherBuckCost;//其他扣款
    private Double sixDeducCost;//专项附加扣除
    private Double personIncomTaxCost;//个税
    private Double netPaySalary;//实发工资

    private String errorMessage;
    private Integer type;

    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;

    public String getBigDeptName() {
        return bigDeptName;
    }

    public void setBigDeptName(String bigDeptName) {
        this.bigDeptName = bigDeptName;
    }

    public String getIncomDate() {
        return incomDate;
    }

    public void setIncomDate(String incomDate) {
        this.incomDate = incomDate;
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPositionAttrName() {
        return positionAttrName;
    }

    public void setPositionAttrName(String positionAttrName) {
        this.positionAttrName = positionAttrName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInCompDate() {
        return inCompDate;
    }

    public void setInCompDate(String inCompDate) {
        this.inCompDate = inCompDate;
    }

    public Double getBasickWorkHours() {
        return basickWorkHours;
    }

    public void setBasickWorkHours(Double basickWorkHours) {
        this.basickWorkHours = basickWorkHours;
    }

    public Double getNorAttenHours() {
        return norAttenHours;
    }

    public void setNorAttenHours(Double norAttenHours) {
        this.norAttenHours = norAttenHours;
    }

    public Double getNorAttendSalary() {
        return norAttendSalary;
    }

    public void setNorAttendSalary(Double norAttendSalary) {
        this.norAttendSalary = norAttendSalary;
    }

    public Double getChinaPailLeavHours() {
        return chinaPailLeavHours;
    }

    public void setChinaPailLeavHours(Double chinaPailLeavHours) {
        this.chinaPailLeavHours = chinaPailLeavHours;
    }

    public Double getChinaPaidLeavSalary() {
        return chinaPaidLeavSalary;
    }

    public void setChinaPaidLeavSalary(Double chinaPaidLeavSalary) {
        this.chinaPaidLeavSalary = chinaPaidLeavSalary;
    }

    public Double getOtherPaidLeavHours() {
        return otherPaidLeavHours;
    }

    public void setOtherPaidLeavHours(Double otherPaidLeavHours) {
        this.otherPaidLeavHours = otherPaidLeavHours;
    }

    public Double getOtherPaidLeavSalary() {
        return otherPaidLeavSalary;
    }

    public void setOtherPaidLeavSalary(Double otherPaidLeavSalary) {
        this.otherPaidLeavSalary = otherPaidLeavSalary;
    }

    public Double getBasicSalarySubTotal() {
        return basicSalarySubTotal;
    }

    public void setBasicSalarySubTotal(Double basicSalarySubTotal) {
        this.basicSalarySubTotal = basicSalarySubTotal;
    }

    public Double getUsualExtraHours() {
        return usualExtraHours;
    }

    public void setUsualExtraHours(Double usualExtraHours) {
        this.usualExtraHours = usualExtraHours;
    }

    public Double getUsralExtraSalary() {
        return usralExtraSalary;
    }

    public void setUsralExtraSalary(Double usralExtraSalary) {
        this.usralExtraSalary = usralExtraSalary;
    }

    public Double getWeekendWorkHours() {
        return weekendWorkHours;
    }

    public void setWeekendWorkHours(Double weekendWorkHours) {
        this.weekendWorkHours = weekendWorkHours;
    }

    public Double getWeekendWorkSalary() {
        return weekendWorkSalary;
    }

    public void setWeekendWorkSalary(Double weekendWorkSalary) {
        this.weekendWorkSalary = weekendWorkSalary;
    }

    public Double getChinaHoliWorkHours() {
        return chinaHoliWorkHours;
    }

    public void setChinaHoliWorkHours(Double chinaHoliWorkHours) {
        this.chinaHoliWorkHours = chinaHoliWorkHours;
    }

    public Double getChinaHoliWorkSalary() {
        return chinaHoliWorkSalary;
    }

    public void setChinaHoliWorkSalary(Double chinaHoliWorkSalary) {
        this.chinaHoliWorkSalary = chinaHoliWorkSalary;
    }

    public Double getCompressSalary() {
        return compressSalary;
    }

    public void setCompressSalary(Double compressSalary) {
        this.compressSalary = compressSalary;
    }

    public Double getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Double getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(Double positionSalary) {
        this.positionSalary = positionSalary;
    }

    public Double getMeritSalary() {
        return meritSalary;
    }

    public void setMeritSalary(Double meritSalary) {
        this.meritSalary = meritSalary;
    }

    public Double getMeritScore() {
        return meritScore;
    }

    public void setMeritScore(Double meritScore) {
        this.meritScore = meritScore;
    }

    public Double getSubbonusTotal() {
        return subbonusTotal;
    }

    public void setSubbonusTotal(Double subbonusTotal) {
        this.subbonusTotal = subbonusTotal;
    }

    public Double getSalorLevelSalary() {
        return salorLevelSalary;
    }

    public void setSalorLevelSalary(Double salorLevelSalary) {
        this.salorLevelSalary = salorLevelSalary;
    }

    public Double getSalrActuGetSalary() {
        return salrActuGetSalary;
    }

    public void setSalrActuGetSalary(Double salrActuGetSalary) {
        this.salrActuGetSalary = salrActuGetSalary;
    }

    public Double getHouseOrTELSubsidy() {
        return houseOrTELSubsidy;
    }

    public void setHouseOrTELSubsidy(Double houseOrTELSubsidy) {
        this.houseOrTELSubsidy = houseOrTELSubsidy;
    }

    public Double getHotTempOrOtherAllow() {
        return hotTempOrOtherAllow;
    }

    public void setHotTempOrOtherAllow(Double hotTempOrOtherAllow) {
        this.hotTempOrOtherAllow = hotTempOrOtherAllow;
    }

    public Double getFullWorkReword() {
        return fullWorkReword;
    }

    public void setFullWorkReword(Double fullWorkReword) {
        this.fullWorkReword = fullWorkReword;
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

    public Double getCompreSalary() {
        return compreSalary;
    }

    public void setCompreSalary(Double compreSalary) {
        this.compreSalary = compreSalary;
    }

    public Double getBuckFoodCost() {
        return buckFoodCost;
    }

    public void setBuckFoodCost(Double buckFoodCost) {
        this.buckFoodCost = buckFoodCost;
    }

    public Double getBuckWaterEleCost() {
        return buckWaterEleCost;
    }

    public void setBuckWaterEleCost(Double buckWaterEleCost) {
        this.buckWaterEleCost = buckWaterEleCost;
    }

    public Double getBuckOldAgeInsurCost() {
        return buckOldAgeInsurCost;
    }

    public void setBuckOldAgeInsurCost(Double buckOldAgeInsurCost) {
        this.buckOldAgeInsurCost = buckOldAgeInsurCost;
    }

    public Double getBuckMedicInsurCost() {
        return buckMedicInsurCost;
    }

    public void setBuckMedicInsurCost(Double buckMedicInsurCost) {
        this.buckMedicInsurCost = buckMedicInsurCost;
    }

    public Double getBuckUnEmployCost() {
        return buckUnEmployCost;
    }

    public void setBuckUnEmployCost(Double buckUnEmployCost) {
        this.buckUnEmployCost = buckUnEmployCost;
    }

    public Double getBuckAccumCost() {
        return buckAccumCost;
    }

    public void setBuckAccumCost(Double buckAccumCost) {
        this.buckAccumCost = buckAccumCost;
    }

    public Double getOtherBuckCost() {
        return otherBuckCost;
    }

    public void setOtherBuckCost(Double otherBuckCost) {
        this.otherBuckCost = otherBuckCost;
    }

    public Double getSixDeducCost() {
        return sixDeducCost;
    }

    public void setSixDeducCost(Double sixDeducCost) {
        this.sixDeducCost = sixDeducCost;
    }

    public Double getPersonIncomTaxCost() {
        return personIncomTaxCost;
    }

    public void setPersonIncomTaxCost(Double personIncomTaxCost) {
        this.personIncomTaxCost = personIncomTaxCost;
    }

    public Double getNetPaySalary() {
        return netPaySalary;
    }

    public void setNetPaySalary(Double netPaySalary) {
        this.netPaySalary = netPaySalary;
    }


//    private Double norAttendSalarySample;//月正常工时工资标准 setup
////    private Double norAttendHoursSample;//月正常出勤工时标准  setup
////    private Double zhengbanHours;//正班出勤工时 emphours
////    private Double chinaPaidLeave;//国家有薪假  emphours
////    private Double otherPaidLeave;//其它有薪假 emphours
////    private Double norExtraMutiple;//月平常加班工时倍数 setup
////    private Double usualExtHours;//平时加班 emphours
////    private Double workendHours;//周末加班 emphours
////    private Double weekEndWorkMutiple;//周末加班工时倍数 setup
////    private Double legalWorkMutiple;//法定节假时加班工时部数 setup
////    private Double legalHolidWorkHours;//法定节假日加班工时 impurtdata
////    private Double compreSalary;//salary
////    private Double posSalary;//salary
////    private Double jobSalary;//salary
////    private Double meritSalary; //salary
////    private Double meritScore;//绩效分 emphours
////    private Double meritScoreSample;//绩效满分标准 setup
////    private Double sellActual;//业务实际 importdata
////    private Double sellThreshold;//业务阈值 importdata
////    private Double sellLevelSalary;//业务等级工资 importdata
////    private Double houseSubsidy;//房补 importdata
////    private Double hotTempOrOtherAllow;//高温等其它补贴 importdata
////    private Double fullWorkReword;//全勤奖 importdata
////    private Double workYearsSalary;//工龄工资 importdata
////    private Double sellCommi;//业务提成 importdata
////    private Double errorInWork;//工作失误 emphours



}
