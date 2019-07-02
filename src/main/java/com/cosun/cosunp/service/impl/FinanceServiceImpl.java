package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.FinanceMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IFinanceServ;
import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/21 0021 上午 9:52
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class FinanceServiceImpl implements IFinanceServ {

    @Autowired
    private FinanceMapper financeMapper;

    private static Logger logger = LogManager.getLogger(FinanceServiceImpl.class);

    String empNoTitle = "工号";//姓名
    Integer empNoTitleIndex;
    String compreSalaryTitle = "综合技能";
    Integer compreSalaryTitleIndex;
    String posSalaryTitle = "岗位工资";
    Integer posSalaryTitleIndex;
    String jobSalaryTitle = "职务工资";
    Integer jobSalaryTitleIndex;
    String meritSalaryTitle = "绩效工资";
    Integer meritSalaryTitleIndex;
    String salaryNameTitle = "姓名";
    Integer salaryNameTitleIndex;


    private String nameTitle = "姓名"; //
    Integer nameTitleIndex;
    private String empNoTitle2 = "工号"; //工号
    Integer empNoTitle2Index;
    private String deptNameTitle = "部门";//部门
    Integer deptNameTitleIndex;
    private String zhengbanHoursTitle = "正班出勤工时";//正班出勤工时
    Integer zhengbanHoursTitleIndex;
    private String usualExtHoursTitle = "平时加班";//平时加班
    Integer usualExtHoursTitleIndex;
    private String workendHoursTitle = "周末加班";//周末加班
    Integer workendHoursTitleIndex;
    private String chinaPaidLeaveTitle = "国家有薪假";//国家有薪假
    Integer chinaPaidLeaveTitleIndex;
    private String otherPaidLeaveTitle = "其它有薪假";//其它有薪假
    Integer otherPaidLeaveTitleIndex;
    private String leaveOfAbsenseTitle = "事假";//事假
    Integer leaveOfAbsenseTitleIndex;
    private String sickLeaveTitle = "病假";//病假
    Integer sickLeaveTitleIndex;
    private String otherAlloTitle = "其它补贴（出差/夜班）";//其它补贴
    Integer otherAlloTitleIndex;
    private String fullWorkRewordTitle = "全勤奖";//全勤奖
    Integer fullWorkRewordTitleIndex;
    private String foodExpenseTitle = "伙食费";//伙食费
    Integer foodExpenseTitleIndex;
    private String roomOrWaterEleExpenseTitle = "房租/水电费";//房租及水电费
    Integer roomOrWaterEleExpenseTitleIndex;
    private String oldAgeINsuranTitle = "扣代付养老险";//养老险
    Integer oldAgeINsuranTitleIndex;
    private String medicalInsuranTitle = "扣代付医疗险";//医疗险
    Integer medicalInsuranTitleIndex;
    private String unEmployeeInsurTitle = "扣代付失业险";//失业险
    Integer unEmployeeInsurTitleIndex;
    private String accumulaFundTitle = "扣代付公积金";//公积金
    Integer accumulaFundTitleIndex;
    private String errorInWorkTitle = "工作失误";//工作失误
    Integer errorInWorkTitleIndex;
    private String meritScoreTitle = "绩效分";//绩效分
    Integer meritScoreTitleIndex;


    private String empNoTitle3 = "工号";//工号
    Integer empNoTitle3Index;
    private String nameTitle3 = "姓名";//姓名
    Integer nameTitle3Index;
    private String deptNameTitle3 = "部门";//部门
    Integer deptNameTitle3Index;
    private String legalHolidWorkHoursTitle = "法定节假日加班工时";//法定节假日加班工时
    Integer legalHolidWorkHoursTitleIndex;
    private String sellActualTitle = "业务实际";//业务实际
    Integer sellActualTitleIndex;
    private String sellThresholdTitle = "业务阈值";//业务阈值
    Integer sellThresholdTitleIndex;
    private String sellLevelSalaryTitle = "业务等级工资";//业务等级工资
    Integer sellLevelSalaryTitleIndex;
    private String houseSubsidyTitle = "房补";//房补
    Integer houseSubsidyTitleIndex;
    private String hotTempOrOtherAllowTitle = "高温等其它补贴";//高温等其它补贴
    Integer hotTempOrOtherAllowTitleIndex;
    private String workYearsSalaryTitle = "工龄工资";//工龄工资
    Integer workYearsSalaryTitleIndex;
    private String sellCommiTitle = "业务提成";//业务提成
    Integer sellCommiTitleIndex;
    private String speciAddDeductCostTitle = "专项附加扣除";
    private Integer speciAddDeductCostTitleIndex;


    public List<Salary> translateExcelToBean(MultipartFile file) throws Exception {
        List<Salary> salaryList = new ArrayList<Salary>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);//它是专门读取.xls的
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            Salary sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (empNoTitle.equals(cella.getContents().trim())) {
                        empNoTitleIndex = ab;
                    }
                    if (compreSalaryTitle.equals(cella.getContents().trim())) {
                        compreSalaryTitleIndex = ab;
                    }
                    if (posSalaryTitle.equals(cella.getContents().trim())) {
                        posSalaryTitleIndex = ab;
                    }
                    if (jobSalaryTitle.equals(cella.getContents().trim())) {
                        jobSalaryTitleIndex = ab;
                    }
                    if (meritSalaryTitle.equals(cella.getContents().trim())) {
                        meritSalaryTitleIndex = ab;
                    }
                    if (salaryNameTitle.equals(cella.getContents().trim())) {
                        salaryNameTitleIndex = ab;
                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                if (cell != null && cell.length > 0) {
                    empNo = cell[empNoTitleIndex].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new Salary();
                        sa.setEmpNo(cell[empNoTitleIndex].getContents().trim());
                        sa.setCompreSalary(Double.valueOf(cell[compreSalaryTitleIndex].getContents().trim()));
                        sa.setPosSalary(Double.valueOf(cell[posSalaryTitleIndex].getContents().trim()));
                        sa.setJobSalary(Double.valueOf(cell[jobSalaryTitleIndex].getContents().trim()));
                        sa.setMeritSalary(Double.valueOf(cell[meritSalaryTitleIndex].getContents().trim()));
                        sa.setName(cell[salaryNameTitleIndex].getContents().trim());
                        salaryList.add(sa);
                    }
                }
            }
            return salaryList;
        } catch (Exception e) {
            throw new NumberFormatException(empNo);
        }
    }

    public void saveAllSalaryData(List<Salary> salaryList) throws Exception {
        financeMapper.deleteAllSalaryData();
        Employee employee;
        for (Salary sa : salaryList) {
            employee = financeMapper.getEmployeeByEmpNoAndName(sa.getEmpNo(), sa.getName());
            if (employee != null) {
                sa.setState(0);
                financeMapper.saveSalary(sa);
            }
        }

    }

    public List<FinanceImportData> queryFinanceImportDataByCondition(Employee employee) throws Exception {
        return financeMapper.queryFinanceImportDataByCondition(employee);
    }

    public int queryFinanceImportDataByConditionCount(Employee employee) throws Exception {
        return financeMapper.queryFinanceImportDataByConditionCount(employee);
    }

    public int findAllFinanceImportDataCount() throws Exception {
        return financeMapper.findAllFinanceImportDataCount();
    }

    public void addSalaryByBean(Employee employee) throws Exception {
        Salary sa = financeMapper.getSalaryByEmpno(employee.getEmpNo());
        if (sa != null) {
            financeMapper.updateSalaryByBean(employee);
        } else {
            financeMapper.addSalaryByBean(employee);

        }
    }

    public void deleteEmpSalaryByBatch(Employee employee) throws Exception {
        financeMapper.deleteEmpSalaryByBatch(employee.getIds());
    }


    public List<EmpHours> translateExcelToBeanEmpHours(MultipartFile file1, String yearMonth) throws Exception {
        List<EmpHours> salaryList = new ArrayList<EmpHours>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file1.getInputStream(), ws);//它是专门读取.xls的
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            EmpHours sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (nameTitle.equals(cella.getContents().trim())) {
                        nameTitleIndex = ab;
                    } else if (empNoTitle2.equals(cella.getContents().trim())) {
                        empNoTitle2Index = ab;
                    } else if (deptNameTitle.equals(cella.getContents().trim())) {
                        deptNameTitleIndex = ab;
                    } else if (zhengbanHoursTitle.equals(cella.getContents().trim())) {
                        zhengbanHoursTitleIndex = ab;
                    } else if (usualExtHoursTitle.equals(cella.getContents().trim())) {
                        usualExtHoursTitleIndex = ab;
                    } else if (workendHoursTitle.equals(cella.getContents().trim())) {
                        workendHoursTitleIndex = ab;
                    } else if (chinaPaidLeaveTitle.equals(cella.getContents().trim())) {
                        chinaPaidLeaveTitleIndex = ab;
                    } else if (otherPaidLeaveTitle.equals(cella.getContents().trim())) {
                        otherPaidLeaveTitleIndex = ab;
                    } else if (leaveOfAbsenseTitle.equals(cella.getContents().trim())) {
                        leaveOfAbsenseTitleIndex = ab;
                    } else if (sickLeaveTitle.equals(cella.getContents().trim())) {
                        sickLeaveTitleIndex = ab;
                    } else if (otherAlloTitle.equals(cella.getContents().trim())) {
                        otherAlloTitleIndex = ab;
                    } else if (fullWorkRewordTitle.equals(cella.getContents().trim())) {
                        fullWorkRewordTitleIndex = ab;
                    } else if (foodExpenseTitle.equals(cella.getContents().trim())) {
                        foodExpenseTitleIndex = ab;
                    } else if (roomOrWaterEleExpenseTitle.equals(cella.getContents().trim())) {
                        roomOrWaterEleExpenseTitleIndex = ab;
                    } else if (oldAgeINsuranTitle.equals(cella.getContents().trim())) {
                        oldAgeINsuranTitleIndex = ab;
                    } else if (medicalInsuranTitle.equals(cella.getContents().trim())) {
                        medicalInsuranTitleIndex = ab;
                    } else if (unEmployeeInsurTitle.equals(cella.getContents().trim())) {
                        unEmployeeInsurTitleIndex = ab;
                    } else if (accumulaFundTitle.equals(cella.getContents().trim())) {
                        accumulaFundTitleIndex = ab;
                    } else if (errorInWorkTitle.equals(cella.getContents().trim())) {
                        errorInWorkTitleIndex = ab;
                    } else if (meritScoreTitle.equals(cella.getContents().trim())) {
                        meritScoreTitleIndex = ab;
                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                if (cell != null && cell.length > 0) {
                    empNo = cell[empNoTitle2Index].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new EmpHours();
                        sa.setName(cell[nameTitleIndex].getContents().trim());
                        sa.setEmpNo(cell[empNoTitle2Index].getContents().trim());
                        sa.setDeptName(cell[deptNameTitleIndex].getContents().trim());
                        sa.setZhengbanHours(Double.valueOf(cell[zhengbanHoursTitleIndex].getContents().trim()));
                        sa.setUsualExtHours(Double.valueOf(cell[usualExtHoursTitleIndex].getContents().trim()));
                        sa.setWorkendHours(Double.valueOf(cell[workendHoursTitleIndex].getContents().trim()));
                        sa.setChinaPaidLeave(Double.valueOf(cell[chinaPaidLeaveTitleIndex].getContents().trim()));
                        sa.setOtherPaidLeave(Double.valueOf(cell[otherPaidLeaveTitleIndex].getContents().trim()));
                        sa.setLeaveOfAbsense(Double.valueOf(cell[leaveOfAbsenseTitleIndex].getContents().trim()));
                        sa.setSickLeave(Double.valueOf(cell[sickLeaveTitleIndex].getContents().trim()));
                        sa.setOtherAllo(Double.valueOf(cell[otherAlloTitleIndex].getContents().trim()));
                        sa.setFullWorkReword(Double.valueOf(cell[fullWorkRewordTitleIndex].getContents().trim()));
                        sa.setFoodExpense(Double.valueOf(cell[foodExpenseTitleIndex].getContents().trim()));
                        sa.setRoomOrWaterEleExpense(Double.valueOf(cell[roomOrWaterEleExpenseTitleIndex].getContents().trim()));
                        sa.setOldAgeINsuran(Double.valueOf(cell[oldAgeINsuranTitleIndex].getContents().trim()));
                        sa.setMedicalInsuran(Double.valueOf(cell[medicalInsuranTitleIndex].getContents().trim()));
                        sa.setUnEmployeeInsur(Double.valueOf(cell[unEmployeeInsurTitleIndex].getContents().trim()));
                        sa.setAccumulaFund(Double.valueOf(cell[accumulaFundTitleIndex].getContents().trim()));
                        sa.setErrorInWork(Double.valueOf(cell[errorInWorkTitleIndex].getContents().trim()));
                        sa.setMeritScore(Double.valueOf(cell[meritScoreTitleIndex].getContents().trim()));
                        sa.setYearMonthStr(yearMonth);
                        salaryList.add(sa);
                    }
                }
            }
            return salaryList;
        } catch (Exception e) {
            throw new NumberFormatException(empNo);
        }
    }

    public void saveAllEmpHours(List<EmpHours> empHoursList, String yearMonth) throws Exception {
        financeMapper.deleteAllEmpHoursByYearMonthData(yearMonth);
        Employee employee;
        for (EmpHours em : empHoursList) {
            employee = financeMapper.getEmployeeByEmpNoAndName(em.getEmpNo(), em.getName());
            if (employee != null) {
                financeMapper.saveEmpHours(em);
            }
        }
    }

    public List<EmpHours> findAllEmpHours(Employee employee) throws Exception {
        return financeMapper.findAllEmpHours(employee);
    }

    public int findAllEmpHoursHours() throws Exception {
        return financeMapper.findAllEmpHoursHours();
    }

    public List<EmpHours> queryEmployeeHoursByCondition(Employee employee) throws Exception {
        return financeMapper.queryEmployeeHoursByCondition(employee);
    }

    public void deleteFinanceImportDataByBatch(Employee employee) throws Exception {
        financeMapper.deleteFinanceImportDataByBatch(employee.getIds());
    }

    public void deleteFinanceImportDataById(Integer id) throws Exception {
        financeMapper.deleteFinanceImportDataById(id);
    }

    public int checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours) throws Exception {
        return financeMapper.checkFinanceImportNoandYearMonthIsExsit(empHours);
    }

    public void addFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception {
        financeMapper.saveFinanceImportData(financeImportData);
    }

    public void updateFinanceImportDataByBean(FinanceImportData financeImportData) throws Exception {
        financeMapper.updateFinanceImportDataByBean(financeImportData);
    }

    public FinanceImportData getFinanceImportDataById(Integer id) throws Exception {
        return financeMapper.getFinanceImportDataById(id);
    }


    public List<FinanceImportData> findAllFinanceImportData(Employee employee) throws Exception {
        return financeMapper.findAllFinanceImportData(employee);
    }


    public int queryEmployeeHoursByConditionCount(Employee employee) throws Exception {
        return financeMapper.queryEmployeeHoursByConditionCount(employee);
    }

    public void deleteEmployeeHoursByEmpno(Integer id) throws Exception {
        financeMapper.deleteEmployeeHoursByEmpno(id);
    }

    public void deleteEmpHoursByBatch(Employee employee) throws Exception {
        financeMapper.deleteEmpHoursByBatch(employee.getIds());
    }

    public EmpHours getEmpHoursByEmpNo(Integer id) throws Exception {
        return financeMapper.getEmpHoursByEmpNo(id);
    }

    public void updateEmpHoursByBean(EmpHours empHours) throws Exception {
        financeMapper.updateEmpHoursByBean(empHours);
    }

    public int checkEmpNoandYearMonthIsExsit(EmpHours empHours) throws Exception {
        return financeMapper.checkEmpNoandYearMonthIsExsit(empHours);
    }

    public void addEmpHoursByBean(EmpHours empHours) throws Exception {
        financeMapper.addEmpHoursByBean(empHours);
    }

    public FinanceSetUpData findFinanceSetUpData() throws Exception {
        return financeMapper.findFinanceSetUpData();
    }

    public List<FinanceImportData> translateExcelToBeanFinanceImportData(MultipartFile file1, String yearMonth) throws Exception {
        List<FinanceImportData> financeImportDataList = new ArrayList<FinanceImportData>();
        String empNo = null;
        try {
            WorkbookSettings ws = new WorkbookSettings();
            jxl.Sheet xlsfSheet = null;
            jxl.Workbook Workbook = jxl.Workbook.getWorkbook(file1.getInputStream(), ws);//它是专门读取.xls的
            if (Workbook != null) {
                jxl.Sheet[] sheets = Workbook.getSheets();
                xlsfSheet = sheets[0];
            }
            FinanceImportData sa = null;
            int rowNums = xlsfSheet.getRows();
            jxl.Cell[] cell = null;
            Cell cella;
            if (rowNums > 0) {
                cell = xlsfSheet.getRow(0);
                int coloumNum = cell.length;
                for (int ab = 0; ab < coloumNum; ab++) {
                    cella = cell[ab];
                    if (empNoTitle3.equals(cella.getContents().trim())) {
                        empNoTitle3Index = ab;
                    } else if (nameTitle3.equals(cella.getContents().trim())) {
                        nameTitle3Index = ab;
                    } else if (deptNameTitle3.equals(cella.getContents().trim())) {
                        deptNameTitle3Index = ab;
                    } else if (legalHolidWorkHoursTitle.equals(cella.getContents().trim())) {
                        legalHolidWorkHoursTitleIndex = ab;
                    } else if (sellActualTitle.equals(cella.getContents().trim())) {
                        sellActualTitleIndex = ab;
                    } else if (sellThresholdTitle.equals(cella.getContents().trim())) {
                        sellThresholdTitleIndex = ab;
                    } else if (sellLevelSalaryTitle.equals(cella.getContents().trim())) {
                        sellLevelSalaryTitleIndex = ab;
                    } else if (houseSubsidyTitle.equals(cella.getContents().trim())) {
                        houseSubsidyTitleIndex = ab;
                    } else if (hotTempOrOtherAllowTitle.equals(cella.getContents().trim())) {
                        hotTempOrOtherAllowTitleIndex = ab;
                    } else if (workYearsSalaryTitle.equals(cella.getContents().trim())) {
                        workYearsSalaryTitleIndex = ab;
                    } else if (sellCommiTitle.equals(cella.getContents().trim())) {
                        sellCommiTitleIndex = ab;
                    } else if (speciAddDeductCostTitle.equals(cella.getContents().trim())) {
                        speciAddDeductCostTitleIndex = ab;
                    }
                }
            }

            for (int i = 1; i < rowNums; i++) {
                cell = xlsfSheet.getRow(i);
                if (cell != null && cell.length > 0) {
                    empNo = cell[empNoTitle3Index].getContents().trim();
                    if (empNo.length() > 0) {
                        sa = new FinanceImportData();
                        sa.setName(cell[nameTitle3Index].getContents().trim());
                        sa.setEmpNo(cell[empNoTitle3Index].getContents().trim());
                        sa.setDeptName(cell[deptNameTitle3Index].getContents().trim());
                        sa.setLegalHolidWorkHours(Double.valueOf(cell[legalHolidWorkHoursTitleIndex].getContents().trim()));
                        sa.setSellActual(Double.valueOf(cell[sellActualTitleIndex].getContents().trim()));
                        sa.setSellThreshold(Double.valueOf(cell[sellThresholdTitleIndex].getContents().trim()));
                        sa.setSellLevelSalary(Double.valueOf(cell[sellLevelSalaryTitleIndex].getContents().trim()));
                        sa.setHouseSubsidy(Double.valueOf(cell[houseSubsidyTitleIndex].getContents().trim()));
                        sa.setHotTempOrOtherAllow(Double.valueOf(cell[hotTempOrOtherAllowTitleIndex].getContents().trim()));
                        sa.setWorkYearsSalary(Double.valueOf(cell[workYearsSalaryTitleIndex].getContents().trim()));
                        sa.setSellCommi(Double.valueOf(cell[sellCommiTitleIndex].getContents().trim()));
                        sa.setSpeciAddDeductCost(Double.valueOf(cell[speciAddDeductCostTitleIndex].getContents().trim()));
                        sa.setYearMonth(yearMonth);
                        financeImportDataList.add(sa);
                    }
                }
            }
            return financeImportDataList;
        } catch (Exception e) {
            throw new NumberFormatException(empNo);
        }
    }

    public void saveAllFinanceImportData(List<FinanceImportData> financeImportDataList, String yearMonth) throws Exception {
        financeMapper.deleteAllsaveAllFinanceImportDataByYearMonthData(yearMonth);
        Employee employee;
        for (FinanceImportData fid : financeImportDataList) {
            employee = financeMapper.getEmployeeByEmpNoAndName(fid.getEmpNo(), fid.getName());
            if (employee != null) {
                fid.setYearMonth(yearMonth);
                financeMapper.saveFinanceImportData(fid);
            }
        }
    }

    public List<SalaryDataOutPut> querySalaryDataOutPutByCondition(Employee employee) throws Exception {
        return financeMapper.querySalaryDataOutPutByCondition(employee);
    }

    public int querySalaryDataOutPutByConditionCount(Employee employee) throws Exception {
        return financeMapper.querySalaryDataOutPutByConditionCount(employee);
    }


    public void saveFinanceSetUp(FinanceSetUpData financeSetUpData) throws Exception {
        int count = financeMapper.findFinanceSetUpDataCount();
        if (count == 0) {
            financeMapper.saveFinanceSetUp(financeSetUpData);
        } else {
            financeMapper.updateFinanceSetUp(financeSetUpData);

        }
    }

    public List<SalaryDataOutPut> findAllSalaryDataOutPut(Employee employee) throws Exception {
        return financeMapper.findAllSalaryDataOutPut(employee);
    }

    public int findAllSalaryDataOutPutCount() throws Exception {
        return financeMapper.findAllSalaryDataOutPutCount();
    }

    public void saveSalaryDataOutPutsList(List<SalaryDataOutPut> salaryDataOutPutList, String yearMonth) throws Exception {
        financeMapper.deleteSalaryDataOutPutByYearMonth(yearMonth);
        for (SalaryDataOutPut sdo : salaryDataOutPutList) {
            financeMapper.saveSalaryDataOutPut(sdo);
        }
    }


    public List<SalaryDataOutPut> computeSalaryData(String yearMonth) throws Exception {
        List<SalaryDataOutPut> salaryDataOutPutList = new ArrayList<SalaryDataOutPut>();
        SalaryDataOutPut sdo;
        List<EmpHours> ehList = financeMapper.getAllEmpHoursByYearMonth(yearMonth);
        FinanceSetUpData fsu = financeMapper.findFinanceSetUpData();
        Salary salary;
        Employee ee;
        FinanceImportData fid;
        Double extWorkFee;
        Double after = 0.0;
        for (EmpHours eh : ehList) {
            salary = financeMapper.getSalaryByEmpno(eh.getEmpNo());
            ee = financeMapper.getEmployeeByEmpno(eh.getEmpNo());
            fid = financeMapper.getFinanceImportDataByEmpNoandYearMonth(eh.getEmpNo(), yearMonth);

            if (salary != null && fid != null) {
                sdo = new SalaryDataOutPut();
                sdo.setEmpNo(salary.getEmpNo());
                sdo.setYearMonth(yearMonth);
                sdo.setDeptName(eh.getDeptName());
                sdo.setPositionName(ee.getPositionName());
                sdo.setPositionAttrName(ee.getPositionAttrIdStr());
                sdo.setName(ee.getName());
                sdo.setInCompDate(ee.getIncompdateStr());
                sdo.setBasickWorkHours(fsu.getBasicWorkHours());
                sdo.setNorAttenHours(eh.getZhengbanHours());
                sdo.setNorAttendSalary(fsu.getNorAttendSalarySample() / fsu.getBasicWorkHours() * eh.getZhengbanHours());
                sdo.setChinaPailLeavHours(eh.getChinaPaidLeave());
                sdo.setChinaPaidLeavSalary(fsu.getNorAttendSalarySample() / fsu.getNorAttendHoursSample() * eh.getChinaPaidLeave());
                sdo.setOtherPaidLeavHours(eh.getOtherPaidLeave());
                sdo.setOtherPaidLeavSalary(fsu.getNorAttendSalarySample() / fsu.getNorAttendHoursSample() * eh.getOtherPaidLeave());
                sdo.setBasicSalarySubTotal(sdo.getChinaPaidLeavSalary() + sdo.getNorAttendSalary() + sdo.getOtherPaidLeavSalary());

                sdo.setUsualExtraHours(eh.getUsualExtHours());
                sdo.setUsralExtraSalary(fsu.getNorAttendSalarySample() / fsu.getNorAttendHoursSample() * sdo.getUsualExtraHours() * fsu.getNorExtraMutiple());
                sdo.setWeekendWorkHours(eh.getWorkendHours());
                sdo.setWeekendWorkSalary(fsu.getNorAttendSalarySample() / fsu.getNorAttendHoursSample() * sdo.getWeekendWorkHours() * fsu.getWeekEndWorkMutiple());
                sdo.setChinaHoliWorkHours(fid.getLegalHolidWorkHours());
                sdo.setChinaHoliWorkSalary(fsu.getNorAttendSalarySample() / fsu.getNorAttendHoursSample() * sdo.getChinaHoliWorkHours() * fsu.getLegalWorkMutiple());
                sdo.setCompressSalary(salary.getCompreSalary());
                sdo.setJobSalary(salary.getJobSalary());
                sdo.setPositionSalary(salary.getPosSalary());
                sdo.setMeritSalary(salary.getMeritSalary());
                sdo.setMeritScore(eh.getMeritScore());
                sdo.setSubbonusTotal(sdo.getCompressSalary() + sdo.getJobSalary() + sdo.getPositionSalary() + sdo.getMeritSalary() / fsu.getMeritScoreSample() * sdo.getMeritScore());
                extWorkFee = sdo.getUsralExtraSalary() + sdo.getWeekendWorkSalary() + sdo.getChinaHoliWorkSalary();
                sdo.setSalorLevelSalary(fid.getSellLevelSalary());
                after = fid.getSellActual() / fid.getSellThreshold();
                sdo.setSalrActuGetSalary((after < 1 ? after : 1) * sdo.getSalorLevelSalary());
                sdo.setHouseOrTELSubsidy(fid.getHouseSubsidy());
                sdo.setHotTempOrOtherAllow(fid.getHotTempOrOtherAllow());
                sdo.setFullWorkReword(eh.getFullWorkReword());
                sdo.setWorkYearsSalary(fid.getWorkYearsSalary());
                sdo.setSellCommi(fid.getSellCommi());
                sdo.setCompreSalary(sdo.getBasicSalarySubTotal() + sdo.getSubbonusTotal() + sdo.getSalrActuGetSalary() +
                        sdo.getHouseOrTELSubsidy() + sdo.getHotTempOrOtherAllow() + sdo.getFullWorkReword() + sdo.getWorkYearsSalary() + extWorkFee);
                sdo.setBuckFoodCost(eh.getFoodExpense());
                sdo.setBuckWaterEleCost(eh.getRoomOrWaterEleExpense());
                sdo.setBuckOldAgeInsurCost(eh.getOldAgeINsuran());
                sdo.setBuckMedicInsurCost(eh.getMedicalInsuran());
                sdo.setBuckUnEmployCost(eh.getUnEmployeeInsur());
                sdo.setBuckAccumCost(eh.getAccumulaFund());
                sdo.setOtherBuckCost(eh.getErrorInWork());
                sdo.setSixDeducCost(fid.getSpeciAddDeductCost());
                sdo.setPersonIncomTaxCost(0.0);
                sdo.setNetPaySalary(sdo.getCompreSalary() - sdo.getBuckFoodCost() - sdo.getBuckWaterEleCost()
                        - sdo.getBuckOldAgeInsurCost() - sdo.getBuckMedicInsurCost() - sdo.getBuckUnEmployCost()
                        - sdo.getBuckAccumCost() - sdo.getOtherBuckCost() - sdo.getPersonIncomTaxCost());

                salaryDataOutPutList.add(sdo);
            }
        }

        return salaryDataOutPutList;
    }


}
