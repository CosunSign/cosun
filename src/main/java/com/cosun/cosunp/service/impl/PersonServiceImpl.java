package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.DateUtil;
import com.cosun.cosunp.tool.FileUtil;
import jxl.Cell;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/9 0009 下午 5:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements IPersonServ {

    private static Logger logger = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    PersonMapper personMapper;

    String nameTitle = "姓名";//姓名
    Integer nameTitleIndex;
    String sexTitle = "性别";//性别
    Integer sexTitleIndex;
    String deptIdTitle = "部门";//部门编号
    Integer deptIdTitleIndex;
    String empNoTitle = "编号";//工号
    Integer empNoTitleIndex;
    String positionIdTitle = "职务";//职位ID
    Integer positionIdTitleIndex;
    String positionAttrIdTitle = "职位";
    Integer positionAttrIdTitleIndex;
    String incompdateTitle = "到职日期";//入厂时间
    Integer incompdateTitleIndex;
    String conExpDateTitle = "合同到期时间";//合同到期时间
    Integer conExpDateTitleIndex;
    String birthDayTitle = "出生日期";//出生日期
    Integer birthDayTitleIndex;
    String ID_NOTitle = "身份证号码";//身份证号码
    Integer ID_NOTitleIndex;
    String nativePlaTitle = "籍贯";//籍guan
    Integer nativePlaTitleIndex;
    String homeAddrTtile = "家庭住址";//家庭住址
    Integer homeAddrTtileIndex;
    String valiPeriodOfIDTitle = "身份证到期时间";//身份证有效期
    Integer valiPeriodOfIDTitleIndex;
    String nationTitle = "民族";//民族
    Integer nationTitleIndex;
    String marriagedTitle = "婚否";//婚否
    Integer marriagedTitleIndex;
    String contactPhoneTitle = "联系电话";//联系电话
    Integer contactPhoneTitleIndex;
    String educationLeTitle = "学历";//学历
    Integer educationLeTitleIndex;
    String screAgreementTitle = "保密协议";//保密协议
    Integer screAgreementTitleIndex;
    String healthCertiTitle = "健康证";//健康证
    Integer healthCertiTitleIndex;
    String sateListAndLeaCertiTitle = "社保清单或离职证明";//社保清单或离职证明
    Integer sateListAndLeaCertiTitleIndex;
    String otherCertiTitle = "其他证件";//其它证件
    Integer otherCertiTitleIndex;
    String attributeTitle = "属性";
    Integer attributeTitleIndex;


    public void deletePositionByIdBatch(List<Integer> ids) throws Exception {
        personMapper.deletePositionByIdBatch(ids);
    }

    public void deleteDeptByBatch(List<Integer> ids) throws Exception {
        personMapper.deleteDeptByBatch(ids);
    }

    public void deleteEmpByBatch(List<Integer> ids) throws Exception {
        List<Employee> emlist = personMapper.getEmployeeByIds(ids);
        for (Employee ee : emlist) {
            String folderName = finalDirPath + "append/" + ee.getEmpNo() + "/";
            personMapper.deleteSalaryByEmpno(ee.getEmpNo());
            personMapper.deleteUserInfoByEmpNo(ee.getEmpNo());
            FileUtil.delFolderNew(folderName);
        }
        personMapper.deleteEmpByBatch(ids);

    }


    public void deleteLeaveByBatch(List<Integer> ids) throws Exception {
        personMapper.deleteLeaveByBatch(ids);
    }


    public int checkAndSavePosition(Position position) throws Exception {
        int isExist = personMapper.findSaveOrNot(position);
        if (isExist > 0) {//代表数据库存在职位，不允许重复增加 0
            return isExist; //0代表重复
        } else {
            personMapper.savePosition(position);
        }
        return isExist;
    }

    public int checkAndSaveDept(String deptName) throws Exception {
        int isExist = personMapper.findSaveOrNot2(deptName);
        if (isExist > 0) {//代表数据库存在职位，不允许重复增加 0
            return isExist; //0代表重复
        } else {
            personMapper.saveDept(deptName);
        }
        return isExist;
    }

    public List<Position> findAllPosition(Position position) throws Exception {
        return personMapper.findAllPosition(position);
    }

    public List<Dept> findAllDept(Dept dept) throws Exception {
        return personMapper.findAllDept(dept);
    }

    public int findAllDeptConditionCount(Dept dep) throws Exception {
        return personMapper.findAllDeptConditionCount();
    }

    public void deletePositionById(Integer id) throws Exception {
        personMapper.deletePositionById(id);
    }

    public void deleteDeptById(Integer id) throws Exception {
        personMapper.deleteDeptById(id);
    }

    public int queryDeptCountByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDeptConditionCount();
        } else {
            return personMapper.findAllDeptByConditionCount(dept);
        }
    }

    public List<Position> queryPositionByName(String positionName) throws Exception {
        return personMapper.queryPositionByName(positionName);
    }


    public void saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception {
        personMapper.saveUpdateData(id, positionName, positionLevel);
    }

    public void saveUpdateData2(Integer id, String deptName) throws Exception {
        personMapper.saveUpdateData2(id, deptName);
    }

    public int findAllPositionConditionCount() throws Exception {
        return personMapper.findAllPositionConditionCount();
    }

    public int checkIfExsit(String positionName) throws Exception {
        return personMapper.checkIfExsit(positionName);
    }

    public int checkIfExsit2(String deptName) throws Exception {
        return personMapper.checkIfExsit2(deptName);
    }

    public List<Position> queryPositionByNameA(Position position) throws Exception {
        return personMapper.queryPositionByNameA(position);
    }

    public List<Dept> queryDeptByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDept(dept);
        } else {
            return personMapper.queryDeptByNameA(dept);
        }
    }


    public int queryPositionCountByNameA(Position position) throws Exception {
        return personMapper.findAllPositionByConditionCount(position);
    }

    public List<Position> findAllPositionAll() throws Exception {
        return personMapper.findAllPositionAll();
    }

    public List<Dept> findAllDeptAll() throws Exception {
        return personMapper.findAllDeptAll();
    }

    public void addEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile, MultipartFile otherCertiFile, Employee employee) throws Exception {
        String fileName;
        String folderName = finalDirPath + "append/" + employee.getEmpNo() + "/";
        UserInfo userInfo;
        if (employee.getEducationLe() > 7) {
            FileUtil.uploadFileForEmployeeAppend(educationLeFile, folderName);
            fileName = educationLeFile.getOriginalFilename();
            employee.setEducationLeUrl(folderName + fileName);
        } else {
            employee.setEducationLeUrl("");
        }
        if (employee.getSateListAndLeaCerti() > 0) {
            FileUtil.uploadFileForEmployeeAppend(sateListAndLeaCertiFile, folderName);
            fileName = sateListAndLeaCertiFile.getOriginalFilename();
            employee.setSateListAndLeaCertiUrl(folderName + fileName);
        } else {
            employee.setSateListAndLeaCertiUrl("");
        }
        if (employee.getOtherCerti() > 0) {
            FileUtil.uploadFileForEmployeeAppend(otherCertiFile, folderName);
            fileName = otherCertiFile.getOriginalFilename();
            employee.setOtherCertiUrl(folderName + fileName);
        } else {
            employee.setOtherCertiUrl("");

        }

        if (employee.getUsername() != null && employee.getPassowrd() != null && employee.getUsername().trim().length() > 0 && employee.getPassowrd().trim().length() > 0) {
            userInfo = new UserInfo();
            userInfo.setEmpNo(employee.getEmpNo());
            userInfo.setFullName(employee.getName());
            userInfo.setUserName(employee.getUsername());
            userInfo.setUserPwd(employee.getPassowrd());
            userInfo.setState(0);// 0代表未审核
            userInfo.setUseruploadright(1);//默认有上传
            userInfo.setUserActor(employee.getPositionAttrId());//默认普通员工
            userInfoMapper.saveUserInfoByBean(userInfo);
        }
        employee.setIsQuit(0);
        personMapper.addEmployeeData(employee);
        personMapper.addSalaryData(employee.getEmpNo(), employee.getCompreSalary(), employee.getPosSalary(), employee.getJobSalary(), employee.getMeritSalary());
    }

    public List<Employee> findAllEmployee(Employee employee) throws Exception {
        return personMapper.findAllEmployee(employee);
    }

    public int findAllEmployeeCount() throws Exception {
        return personMapper.findAllEmployeeCount();
    }

    public int checkEmployIsExsit(String name) throws Exception {
        return personMapper.checkEmployIsExsit(name);
    }

    public int checkEmployNoIsExsit(String empoyeeNo) throws Exception {
        return personMapper.checkEmployNoIsExsit(empoyeeNo);
    }

    public List<Employee> queryEmployeeByCondition(Employee employee) throws Exception {
        return personMapper.queryEmployeeByCondition(employee);
    }

    public int queryEmployeeByConditionCount(Employee employee) throws Exception {
        return personMapper.queryEmployeeByConditionCount(employee);

    }

    public void deleteEmployeetById(Integer id) throws Exception {
        List<Employee> emlist = personMapper.getEmployeeById(id);
        String folderName = finalDirPath + "append/" + emlist.get(0).getEmpNo() + "/";
        personMapper.deleteEmployeetById(id);
        personMapper.deleteSalaryByEmpno(emlist.get(0).getEmpNo());
        personMapper.deleteUserInfoByEmpNo(emlist.get(0).getEmpNo());
        FileUtil.delFolderNew(folderName);
    }

    public List<Employee> getEmployeeById(Integer id) throws Exception {
        return personMapper.getEmployeeById(id);
    }

    public void addLeaveData(Leave leave) throws Exception {
        personMapper.addLeaveData(leave);
    }

    public void deleteLeaveById(Integer id) throws Exception {
        personMapper.deleteLeaveById(id);
    }

    public Leave getLeaveById(Integer id) throws Exception {
        return personMapper.getLeaveById(id);
    }

    public void addWorkSetData(WorkSet workSet) throws Exception {
        personMapper.addWorkSetData(workSet);
    }

    public WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate) throws Exception {
        return personMapper.getWorkSetByMonthAndPositionLevel(workDate);
    }

    public List<WorkSet> findAllWorkSet(WorkSet workSet) throws Exception {
        return personMapper.findAllWorkSet(workSet);
    }

    public int findAllWorkSetCount(WorkSet workSet) throws Exception {
        return personMapper.findAllWorkSetCount(workSet);
    }

    public void deleteWorkSetById(WorkSet workSet) throws Exception {
        personMapper.deleteWorkSetById(workSet);
    }


    public List<WorkSet> queryWorkSetByCondition(WorkSet workSet) throws Exception {
        return personMapper.queryWorkSetByCondition(workSet);
    }

    public int queryWorkSetByConditionCount(WorkSet workSet) throws Exception {
        return personMapper.queryWorkSetByConditionCount(workSet);

    }

    public void updateWorkSetDataById(WorkSet workSet) throws Exception {
        personMapper.updateWorkSetDataById(workSet);
    }


    public WorkSet getWorkSetById(Integer id) throws Exception {
        return personMapper.getWorkSetById(id);
    }


    public String getPositionNamesByPositionLevel(String positionLevel) throws Exception {
        return personMapper.getPositionNamesByPositionLevel(positionLevel);
    }


    public void saveOrUpdateWorkData(WorkDate workDate) throws Exception {
        WorkDate num = personMapper.getWorkDateByMonth(workDate);
        if (num == null) {//save
            if (workDate.getEmpNos() != null && workDate.getEmpNos().size() > 0)
                workDate.setEmpNostr(workDate.getEmpNos().toString());
            personMapper.saveWorkData(workDate);
        } else {//update
            if (workDate.getEmpNos() != null && workDate.getEmpNos().size() > 0)
                workDate.setEmpNostr(workDate.getEmpNos().toString());
            personMapper.updateWorkData(workDate);
        }

    }


    public void updateEmployeeData(MultipartFile educationLeFile, MultipartFile sateListAndLeaCertiFile,
                                   MultipartFile otherCertiFile, Employee employee) throws Exception {
        String fileName;
        String folderName = finalDirPath + "append/" + employee.getEmpNo() + "/";
        UserInfo userInfo;
        Employee eee = personMapper.getEmployeeByEmpNo(employee.getEmpNo());
        if (employee.getEducationLe() <= 7) {
            FileUtil.delFile(eee.getEducationLeUrl());
            employee.setEducationLeUrl("");
        } else {
            fileName = educationLeFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getEducationLeUrl());
                FileUtil.uploadFileForEmployeeAppend(educationLeFile, folderName);
                employee.setEducationLeUrl(folderName + fileName);
            } else {
                employee.setEducationLeUrl(eee.getEducationLeUrl());
            }

        }
        if (employee.getSateListAndLeaCerti() == 0) {
            FileUtil.delFile(eee.getSateListAndLeaCertiUrl());
            employee.setSateListAndLeaCertiUrl("");
        } else {
            fileName = sateListAndLeaCertiFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getSateListAndLeaCertiUrl());
                FileUtil.uploadFileForEmployeeAppend(sateListAndLeaCertiFile, folderName);
                employee.setSateListAndLeaCertiUrl(folderName + fileName);
            } else {
                employee.setSateListAndLeaCertiUrl(eee.getSateListAndLeaCertiUrl());
            }
        }
        if (employee.getOtherCerti() == 0) {
            FileUtil.delFile(eee.getOtherCertiUrl());
            employee.setOtherCertiUrl("");
        } else {
            fileName = otherCertiFile.getOriginalFilename();
            if (fileName.trim().length() > 0) {
                FileUtil.delFile(eee.getOtherCertiUrl());
                FileUtil.uploadFileForEmployeeAppend(otherCertiFile, folderName);
                employee.setOtherCertiUrl(folderName + fileName);
            } else {
                employee.setOtherCertiUrl(eee.getOtherCertiUrl());
            }
        }

        if (employee.getUsername() != null) {
            userInfo = new UserInfo();
            userInfo.setEmpNo(employee.getEmpNo());
            userInfo.setUserName(employee.getUsername());
            userInfo.setUserPwd(employee.getPassowrd());
            userInfoMapper.updateUserInfoByBean(userInfo);
        }
        personMapper.updateEmployeeData(employee);
        personMapper.updateSalaryData(employee.getEmpNo(), employee.getCompreSalary(), employee.getPosSalary(), employee.getJobSalary(), employee.getMeritSalary());
    }

    public void updateLeaveToMysql(Leave leave) throws Exception {
        personMapper.updateLeaveToMysql(leave);
    }

    public List<Leave> queryLeaveByCondition(Leave leave) throws Exception {
        return personMapper.queryLeaveByCondition(leave);
        //queryEmployeeByCondition()
    }

    public int checkBeginLeaveRight(Leave leave) throws Exception {
        return personMapper.checkBeginLeaveRight(leave);
    }


    public int queryLeaveByConditionCount(Leave leave) throws Exception {
        return personMapper.queryLeaveByConditionCount(leave);
    }

    public List<Leave> findAllLeave(Leave leave) throws Exception {
        return personMapper.findAllLeave(leave);
    }

    public int findAllLeaveCount() throws Exception {
        return personMapper.findAllLeaveCount();
    }

    public List<Employee> findAllEmployees() throws Exception {
        return personMapper.findAllEmployees();
    }


    public WorkDate getWorkDateByMonth(WorkDate workDate) throws Exception {
        return personMapper.getWorkDateByMonth(workDate);
    }

    public WorkDate getWorkDateByMonth2(WorkDate workDate) throws Exception {
        return personMapper.getWorkDateByMonth2(workDate);
    }


    public List<ClockInOrgin> translateTabletoBean(MultipartFile file) throws Exception {
        // File file = new File("C:\\Users\\Administrator\\Desktop\\4月车间考勤记录.xls");
        WorkbookSettings ws = new WorkbookSettings();
        String fileName = file.getOriginalFilename();
        ws.setCellValidationDisabled(true);
        //if (fileName.contains("-1.")) {
        jxl.Workbook Workbook = null;//.xlsx
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //根据后缀创建读取不同类型的excel
        if (fileType.equals("xls")) {
            Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);//它是专门读取.xlsx的
        } else {
            throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
        }
        jxl.Sheet xlsfSheet = null;
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
        }
        List<ClockInOrgin> clockInOrgins = new ArrayList<ClockInOrgin>();
        ClockInOrgin cio = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String timestr;
        for (int i = 1; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                cio = new ClockInOrgin();
                cio.setAttendMachineId(Integer.valueOf(cell[0].getContents().trim()));
                cio.setEmployeeName(cell[1].getContents().trim());
                cio.setDeptName(cell[2].getContents().trim());
                cio.setDateStr(cell[3].getContents().trim());
                timestr = cell[4].getContents().trim();
                cio.setTimeStr(timestr);
                if (timestr != null && timestr.length() > 0) {
                    cio.setTimes(timestr.split(" "));
                }
                clockInOrgins.add(cio);
            }
        }
        return clockInOrgins;
    }

    public List<Employee> translateTabletoEmployeeBean(MultipartFile file) throws Exception {
        WorkbookSettings ws = new WorkbookSettings();
        String fileName = file.getOriginalFilename();
        ws.setCellValidationDisabled(true);
        jxl.Workbook Workbook = null;//.xlsx
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //根据后缀创建读取不同类型的excel
        if (fileType.equals("xls")) {
            Workbook = jxl.Workbook.getWorkbook(file.getInputStream(), ws);//它是专门读取.xlsx的
        } else {
            throw new Exception("文档格式后缀不正确!!！只接受xls格式.");
        }
        jxl.Sheet xlsfSheet = null;
        jxl.Sheet xlsfSheet2 = null;
        if (Workbook != null) {
            jxl.Sheet[] sheets = Workbook.getSheets();
            xlsfSheet = sheets[0];
            xlsfSheet2 = sheets[1];
        }
        List<Employee> employeeList = new ArrayList<Employee>();
        Employee em = null;
        int rowNums = xlsfSheet.getRows();
        jxl.Cell[] cell = null;
        String name = null;
        Cell cella;
        if (rowNums > 0) {
            cell = xlsfSheet.getRow(1);
            int coloumNum = cell.length;
            for (int ab = 0; ab < coloumNum; ab++) {
                cella = cell[ab];// 获得第i行的第3个单元格
                if (nameTitle.equals(cella.getContents().trim())) {
                    nameTitleIndex = ab;
                }
                if (sexTitle.equals(cella.getContents().trim())) {
                    sexTitleIndex = ab;
                }
                if (deptIdTitle.equals(cella.getContents().trim())) {
                    deptIdTitleIndex = ab;
                }
                if (empNoTitle.equals(cella.getContents().trim())) {
                    empNoTitleIndex = ab;
                }

                if (positionIdTitle.equals(cella.getContents().trim())) {
                    positionIdTitleIndex = ab;
                }
                if (incompdateTitle.equals(cella.getContents().trim())) {
                    incompdateTitleIndex = ab;
                }

                if (conExpDateTitle.equals(cella.getContents().trim())) {
                    conExpDateTitleIndex = ab;
                }

                if (birthDayTitle.equals(cella.getContents().trim())) {
                    birthDayTitleIndex = ab;
                }

                if (ID_NOTitle.equals(cella.getContents().trim())) {
                    ID_NOTitleIndex = ab;
                }

                if (nativePlaTitle.equals(cella.getContents().trim())) {
                    nativePlaTitleIndex = ab;
                }

                if (homeAddrTtile.equals(cella.getContents().trim())) {
                    homeAddrTtileIndex = ab;
                }
                if (valiPeriodOfIDTitle.equals(cella.getContents().trim())) {
                    valiPeriodOfIDTitleIndex = ab;
                }
                if (nationTitle.equals(cella.getContents().trim())) {
                    nationTitleIndex = ab;
                }
                if (marriagedTitle.equals(cella.getContents().trim())) {
                    marriagedTitleIndex = ab;
                }

                if (contactPhoneTitle.equals(cella.getContents().trim())) {
                    contactPhoneTitleIndex = ab;
                }

                if (educationLeTitle.equals(cella.getContents().trim())) {
                    educationLeTitleIndex = ab;
                }
                if (screAgreementTitle.equals(cella.getContents().trim())) {
                    screAgreementTitleIndex = ab;
                }
                if (healthCertiTitle.equals(cella.getContents().trim())) {
                    healthCertiTitleIndex = ab;
                }

                if (sateListAndLeaCertiTitle.equals(cella.getContents().trim())) {
                    sateListAndLeaCertiTitleIndex = ab;
                }
                if (otherCertiTitle.equals(cella.getContents().trim())) {
                    otherCertiTitleIndex = ab;
                }
                if (positionAttrIdTitle.equals(cella.getContents().trim())) {
                    positionAttrIdTitleIndex = ab;
                }
            }
        }
        for (int i = 2; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                name = cell[nameTitleIndex].getContents().trim();
                if (name.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell[empNoTitleIndex].getContents().trim());
                    em.setName(cell[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell[homeAddrTtileIndex].getContents().trim());
                    System.out.println("=====================" + cell[valiPeriodOfIDTitleIndex].getContents().length());
                    if ((cell[valiPeriodOfIDTitleIndex] != null || cell[valiPeriodOfIDTitleIndex].getContents() != null) && cell[valiPeriodOfIDTitleIndex].getContents().length() > 1) {
                        em.setValiPeriodOfIDStr(cell[valiPeriodOfIDTitleIndex].getContents().trim());
                    } else {
                        em.setValiPeriodOfIDStr("9999-12-31");
                    }
                    em.setNationStr(cell[nationTitleIndex].getContents().trim());
                    em.setMarriagedStr(cell[marriagedTitleIndex].getContents().trim());
                    em.setPositionAttrIdStr(cell[positionAttrIdTitleIndex].getContents().trim());
                    em.setContactPhone(cell[contactPhoneTitleIndex].getContents().trim());
                    em.setEducationLeStr(cell[educationLeTitleIndex].getContents().trim());
                    em.setScreAgreementStr(cell[screAgreementTitleIndex].getContents().trim());
                    em.setHealthCertiStr(cell[healthCertiTitleIndex].getContents().trim());
                    if (cell[sateListAndLeaCertiTitleIndex] != null || cell[sateListAndLeaCertiTitleIndex].getContents() != null)
                        em.setSateListAndLeaCertiStr(cell[sateListAndLeaCertiTitleIndex].getContents().trim());
                    em.setOtherCertiStr(cell[otherCertiTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell[incompdateTitleIndex].getContents().trim());

                    if (!"长期".equals(cell[conExpDateTitleIndex].getContents().trim())) {
                        em.setConExpDateStr(cell[conExpDateTitleIndex].getContents().trim());
                    } else {
                        em.setConExpDateStr("9999-12-31");
                    }
                    if (attributeTitleIndex != null) {
                        em.setPositionLevel(cell[attributeTitleIndex].getContents().trim());
                    }
                    em.setWorkType(0);
                    em.setPositionLevel("B");
                    em.setIsQuit(0);
                    employeeList.add(em);
                }
            }
        }

        int rowNums2 = xlsfSheet2.getRows();
        jxl.Cell[] cell2 = null;
        String name2 = null;
        Cell cella2;
        if (rowNums2 > 0) {
            cell2 = xlsfSheet2.getRow(1);
            int coloumNum2 = cell2.length;
            for (int ab = 0; ab < coloumNum2; ab++) {
                cella2 = cell2[ab];// 获得第i行的第3个单元格
                if (nameTitle.equals(cella2.getContents().trim())) {
                    nameTitleIndex = ab;
                }
                if (sexTitle.equals(cella2.getContents().trim())) {
                    sexTitleIndex = ab;
                }
                if (deptIdTitle.equals(cella2.getContents().trim())) {
                    deptIdTitleIndex = ab;
                }
                if (empNoTitle.equals(cella2.getContents().trim())) {
                    empNoTitleIndex = ab;
                }

                if (positionIdTitle.equals(cella2.getContents().trim())) {
                    positionIdTitleIndex = ab;
                }
                if (incompdateTitle.equals(cella2.getContents().trim())) {
                    incompdateTitleIndex = ab;
                }

                if (conExpDateTitle.equals(cella2.getContents().trim())) {
                    conExpDateTitleIndex = ab;
                }

                if (birthDayTitle.equals(cella2.getContents().trim())) {
                    birthDayTitleIndex = ab;
                }

                if (ID_NOTitle.equals(cella2.getContents().trim())) {
                    ID_NOTitleIndex = ab;
                }

                if (nativePlaTitle.equals(cella2.getContents().trim())) {
                    nativePlaTitleIndex = ab;
                }

                if (homeAddrTtile.equals(cella2.getContents().trim())) {
                    homeAddrTtileIndex = ab;
                }
                if (valiPeriodOfIDTitle.equals(cella2.getContents().trim())) {
                    valiPeriodOfIDTitleIndex = ab;
                }
                if (nationTitle.equals(cella2.getContents().trim())) {
                    nationTitleIndex = ab;
                }
                if (marriagedTitle.equals(cella2.getContents().trim())) {
                    marriagedTitleIndex = ab;
                }

                if (contactPhoneTitle.equals(cella2.getContents().trim())) {
                    contactPhoneTitleIndex = ab;
                }

                if (educationLeTitle.equals(cella2.getContents().trim())) {
                    educationLeTitleIndex = ab;
                }
                if (screAgreementTitle.equals(cella2.getContents().trim())) {
                    screAgreementTitleIndex = ab;
                }
                if (healthCertiTitle.equals(cella2.getContents().trim())) {
                    healthCertiTitleIndex = ab;
                }

                if (sateListAndLeaCertiTitle.equals(cella2.getContents().trim())) {
                    sateListAndLeaCertiTitleIndex = ab;
                }
                if (otherCertiTitle.equals(cella2.getContents().trim())) {
                    otherCertiTitleIndex = ab;
                }
                if (positionAttrIdTitle.equals(cella2.getContents().trim())) {
                    positionAttrIdTitleIndex = ab;
                }
            }
        }
        for (int i = 2; i < rowNums2; i++) {
            cell2 = xlsfSheet2.getRow(i);
            if (cell2 != null && cell2.length > 0) {
                name2 = cell2[1].getContents().trim();
                if (name2.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell2[empNoTitleIndex].getContents().trim());
                    em.setName(cell2[nameTitleIndex].getContents().trim());
                    em.setDeptName(cell2[deptIdTitleIndex].getContents().trim());
                    em.setPositionName(cell2[positionIdTitleIndex].getContents().trim());
                    em.setSexStr(cell2[sexTitleIndex].getContents().trim());
                    em.setBirthDayStr(cell2[birthDayTitleIndex].getContents().trim());
                    em.setID_NO(cell2[ID_NOTitleIndex].getContents().trim());
                    em.setNativePlaStr(cell2[nativePlaTitleIndex].getContents().trim());
                    em.setHomeAddr(cell2[homeAddrTtileIndex].getContents().trim());
                    if ((cell2[valiPeriodOfIDTitleIndex] != null || cell2[valiPeriodOfIDTitleIndex].getContents() != null) && cell2[valiPeriodOfIDTitleIndex].getContents().length() > 1) {
                        em.setValiPeriodOfIDStr(cell2[valiPeriodOfIDTitleIndex].getContents().trim());
                    } else {
                        em.setValiPeriodOfIDStr("9999-12-31");
                    }
                    em.setNationStr(cell2[nationTitleIndex].getContents().trim());
                    em.setMarriagedStr(cell2[marriagedTitleIndex].getContents().trim());
                    em.setPositionAttrIdStr(cell2[positionAttrIdTitleIndex].getContents().trim());
                    em.setContactPhone(cell2[contactPhoneTitleIndex].getContents().trim());
                    em.setEducationLeStr(cell2[educationLeTitleIndex].getContents().trim());
                    em.setScreAgreementStr(cell2[screAgreementTitleIndex].getContents().trim());
                    em.setHealthCertiStr(cell2[healthCertiTitleIndex].getContents().trim());
                    if (cell2[sateListAndLeaCertiTitleIndex] != null || cell2[sateListAndLeaCertiTitleIndex].getContents() != null)
                        em.setSateListAndLeaCertiStr(cell2[sateListAndLeaCertiTitleIndex].getContents().trim());
                    em.setOtherCertiStr(cell2[otherCertiTitleIndex].getContents().trim());
                    em.setIncompdateStr(cell2[incompdateTitleIndex].getContents().trim());

                    if (!"长期".equals(cell2[conExpDateTitleIndex].getContents().trim())) {
                        em.setConExpDateStr(cell2[conExpDateTitleIndex].getContents().trim());
                    } else {
                        em.setConExpDateStr("9999-12-31");
                    }
                    if (attributeTitleIndex != null) {
                        em.setPositionLevel(cell2[attributeTitleIndex].getContents().trim());
                    }
                    em.setWorkType(1);
                    em.setPositionLevel("A");
                    em.setIsQuit(0);
                    employeeList.add(em);
                }
            }
        }
        return employeeList;
    }

    public String checkEmpNoOrEmpNameRepeat(List<Employee> employeeList) throws Exception {
        int size = employeeList.size();
        List<Employee> newEm = new ArrayList<Employee>();
        String repeatData = "";
        Employee e1 = null;
        Employee e2 = null;
        int repeatnum;
        int repeatnum2;
        for (int i = 0; i < size; i++) {
            repeatnum = 0;
            repeatnum2 = 0;
            e1 = employeeList.get(i);
            for (int j = 0; j < size; j++) {
                e2 = employeeList.get(j);
                if (e1.getName().equals(e2.getName())) {
                    repeatnum += 1;
                    if (repeatnum == 2) {
                        repeatData += "重复的姓名比如:" + e1.getName();
                        return repeatData;
                    }
                }
                if (e1.getEmpNo().equals(e2.getEmpNo())) {
                    repeatnum2 += 1;
                    if (repeatnum2 == 2) {
                        repeatData += "重复的员工编号比如:" + e1.getEmpNo();
                        return repeatData;
                    }
                }
            }
        }

        return repeatData;
    }


    public void saveDeptNameAndPositionNameAndEms(List<Employee> employeeList) throws Exception {
        personMapper.clearDeptData();
        personMapper.clearPositionData();
        personMapper.clearEmployeeData();
        Employee em = null;
        List<String> depts = new ArrayList<String>();
        List<String> positions = new ArrayList<String>();
        int size = employeeList.size();
        for (int i = 0; i < size; i++) {
            em = employeeList.get(i);
            if (!depts.contains(em.getDeptName())) {
                personMapper.addDeptByDeptName(em.getDeptName());
                depts.add(em.getDeptName());
            }
            if (!positions.contains(em.getPositionName())) {
                if (em.getPositionLevel() != null) {
                    personMapper.addPositionByNameandPositionLevel(em.getPositionName(), em.getPositionLevel());
                } else {
                    personMapper.addPositionByName(em.getPositionName());
                }
                positions.add(em.getPositionName());
            }
        }

        for (Employee emm : employeeList) {
            Dept dept = personMapper.getDeptByName(emm.getDeptName());
            Position position = personMapper.getPositionByName(emm.getPositionName());
            emm.setDeptId(dept.getId());
            emm.setPositionId(position.getId());
            emm.setSex("男".equals(emm.getSexStr()) ? 1 : 0);

            if ("已".equals(emm.getMarriagedStr())) {
                emm.setMarriaged(1);
            } else if ("未".equals(emm.getMarriagedStr()) || "否".equals(emm.getMarriagedStr())) {
                emm.setMarriaged(0);
            } else if ("离".equals(emm.getMarriagedStr())) {
                emm.setMarriaged(2);
            }

            if ("壮".equals(emm.getNationStr())) {
                emm.setNation(1);
            } else if ("藏".equals(emm.getNationStr())) {
                emm.setNation(2);
            } else if ("裕固".equals(emm.getNationStr())) {
                emm.setNation(3);
            } else if ("彝".equals(emm.getNationStr())) {
                emm.setNation(4);
            } else if ("瑶".equals(emm.getNationStr())) {
                emm.setNation(5);
            } else if ("锡伯".equals(emm.getNationStr())) {
                emm.setNation(6);
            } else if ("乌孜别克".equals(emm.getNationStr())) {
                emm.setNation(7);
            } else if ("维吾尔".equals(emm.getNationStr())) {
                emm.setNation(8);
            } else if ("佤".equals(emm.getNationStr())) {
                emm.setNation(9);
            } else if ("土家".equals(emm.getNationStr())) {
                emm.setNation(10);
            } else if ("土".equals(emm.getNationStr())) {
                emm.setNation(11);
            } else if ("塔塔尔".equals(emm.getNationStr())) {
                emm.setNation(12);
            } else if ("塔吉克".equals(emm.getNationStr())) {
                emm.setNation(13);
            } else if ("水".equals(emm.getNationStr())) {
                emm.setNation(14);
            } else if ("畲".equals(emm.getNationStr())) {
                emm.setNation(15);
            } else if ("撒拉".equals(emm.getNationStr())) {
                emm.setNation(16);
            } else if ("羌".equals(emm.getNationStr())) {
                emm.setNation(17);
            } else if ("普米".equals(emm.getNationStr())) {
                emm.setNation(18);
            } else if ("怒".equals(emm.getNationStr())) {
                emm.setNation(19);
            } else if ("纳西".equals(emm.getNationStr())) {
                emm.setNation(20);
            } else if ("仫佬".equals(emm.getNationStr())) {
                emm.setNation(21);
            } else if ("苗".equals(emm.getNationStr())) {
                emm.setNation(22);
            } else if ("蒙古".equals(emm.getNationStr())) {
                emm.setNation(23);
            } else if ("门巴".equals(emm.getNationStr())) {
                emm.setNation(24);
            } else if ("毛南".equals(emm.getNationStr())) {
                emm.setNation(25);
            } else if ("满".equals(emm.getNationStr())) {
                emm.setNation(26);
            } else if ("珞巴".equals(emm.getNationStr())) {
                emm.setNation(27);
            } else if ("僳僳".equals(emm.getNationStr())) {
                emm.setNation(28);
            } else if ("黎".equals(emm.getNationStr())) {
                emm.setNation(29);
            } else if ("拉祜".equals(emm.getNationStr())) {
                emm.setNation(30);
            } else if ("柯尔克孜".equals(emm.getNationStr())) {
                emm.setNation(31);
            } else if ("景颇".equals(emm.getNationStr())) {
                emm.setNation(32);
            } else if ("京".equals(emm.getNationStr())) {
                emm.setNation(33);
            } else if ("基诺".equals(emm.getNationStr())) {
                emm.setNation(34);
            } else if ("回".equals(emm.getNationStr())) {
                emm.setNation(35);
            } else if ("赫哲".equals(emm.getNationStr())) {
                emm.setNation(36);
            } else if ("哈萨克".equals(emm.getNationStr())) {
                emm.setNation(37);
            } else if ("哈尼".equals(emm.getNationStr())) {
                emm.setNation(38);
            } else if ("仡佬".equals(emm.getNationStr())) {
                emm.setNation(39);
            } else if ("高山".equals(emm.getNationStr())) {
                emm.setNation(40);
            } else if ("鄂温克".equals(emm.getNationStr())) {
                emm.setNation(41);
            } else if ("俄罗斯".equals(emm.getNationStr())) {
                emm.setNation(42);
            } else if ("鄂伦春".equals(emm.getNationStr())) {
                emm.setNation(43);
            } else if ("独龙".equals(emm.getNationStr())) {
                emm.setNation(44);
            } else if ("东乡".equals(emm.getNationStr())) {
                emm.setNation(45);
            } else if ("侗".equals(emm.getNationStr())) {
                emm.setNation(46);
            } else if ("德昂".equals(emm.getNationStr())) {
                emm.setNation(47);
            } else if ("傣".equals(emm.getNationStr())) {
                emm.setNation(48);
            } else if ("达斡尔".equals(emm.getNationStr())) {
                emm.setNation(49);
            } else if ("朝鲜".equals(emm.getNationStr())) {
                emm.setNation(50);
            } else if ("布依".equals(emm.getNationStr())) {
                emm.setNation(51);
            } else if ("布朗".equals(emm.getNationStr())) {
                emm.setNation(52);
            } else if ("保安".equals(emm.getNationStr())) {
                emm.setNation(53);
            } else if ("白".equals(emm.getNationStr())) {
                emm.setNation(54);
            } else if ("阿昌".equals(emm.getNationStr())) {
                emm.setNation(55);
            } else if ("汉".equals(emm.getNationStr())) {
                emm.setNation(56);
            }


            if ("北京".equals(emm.getNativePlaStr())) {
                emm.setNativePla(1);
            } else if ("上海".equals(emm.getNativePlaStr())) {
                emm.setNativePla(2);
            } else if ("广东".equals(emm.getNativePlaStr())) {
                emm.setNativePla(3);
            } else if ("河北".equals(emm.getNativePlaStr())) {
                emm.setNativePla(4);
            } else if ("山西".equals(emm.getNativePlaStr())) {
                emm.setNativePla(5);
            } else if ("辽宁".equals(emm.getNativePlaStr())) {
                emm.setNativePla(6);
            } else if ("吉林".equals(emm.getNativePlaStr())) {
                emm.setNativePla(7);
            } else if ("黑龙江".equals(emm.getNativePlaStr())) {
                emm.setNativePla(8);
            } else if ("江苏".equals(emm.getNativePlaStr())) {
                emm.setNativePla(9);
            } else if ("浙江".equals(emm.getNativePlaStr())) {
                emm.setNativePla(10);
            } else if ("安徽".equals(emm.getNativePlaStr())) {
                emm.setNativePla(11);
            } else if ("福建".equals(emm.getNativePlaStr())) {
                emm.setNativePla(12);
            } else if ("江西".equals(emm.getNativePlaStr())) {
                emm.setNativePla(13);
            } else if ("山东".equals(emm.getNativePlaStr())) {
                emm.setNativePla(14);
            } else if ("河南".equals(emm.getNativePlaStr())) {
                emm.setNativePla(15);
            } else if ("湖北".equals(emm.getNativePlaStr()) || "武汉".equals(emm.getNativePlaStr())) {
                emm.setNativePla(16);
            } else if ("湖南".equals(emm.getNativePlaStr())) {
                emm.setNativePla(17);
            } else if ("天津".equals(emm.getNativePlaStr())) {
                emm.setNativePla(18);
            } else if ("陕西".equals(emm.getNativePlaStr())) {
                emm.setNativePla(19);
            } else if ("四川".equals(emm.getNativePlaStr())) {
                emm.setNativePla(20);
            } else if ("台湾".equals(emm.getNativePlaStr())) {
                emm.setNativePla(21);
            } else if ("云南".equals(emm.getNativePlaStr())) {
                emm.setNativePla(22);
            } else if ("青海".equals(emm.getNativePlaStr())) {
                emm.setNativePla(23);
            } else if ("甘肃".equals(emm.getNativePlaStr())) {
                emm.setNativePla(24);
            } else if ("海南".equals(emm.getNativePlaStr())) {
                emm.setNativePla(25);
            } else if ("贵州".equals(emm.getNativePlaStr())) {
                emm.setNativePla(26);
            } else if ("重庆".equals(emm.getNativePlaStr())) {
                emm.setNativePla(27);
            } else if ("新疆".equals(emm.getNativePlaStr())) {
                emm.setNativePla(28);
            } else if ("广西".equals(emm.getNativePlaStr())) {
                emm.setNativePla(29);
            } else if ("宁夏".equals(emm.getNativePlaStr())) {
                emm.setNativePla(30);
            } else if ("内蒙古".equals(emm.getNativePlaStr())) {
                emm.setNativePla(31);
            } else if ("西藏".equals(emm.getNativePlaStr())) {
                emm.setNativePla(32);
            }

            if ("小学".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(1);
            } else if ("初中".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(2);
            } else if ("高中".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(3);
            } else if ("技校".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(4);
            } else if ("中技".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(5);
            } else if ("中专".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(6);
            } else if ("大专".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(7);
            } else if ("本科".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(8);
            } else if ("研究生".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(9);
            } else if ("硕士".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(10);
            } else if ("博士".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(11);
            } else if ("MBA".equals(emm.getEducationLeStr())) {
                emm.setEducationLe(12);
            }

            emm.setScreAgreement("有".equals(emm.getScreAgreementStr()) || "是".equals(emm.getScreAgreementStr()) ? 1 : 0);

            if ("离职和社保".equals(emm.getSateListAndLeaCertiStr()) || "是".equals(emm.getSateListAndLeaCertiStr())) {
                emm.setSateListAndLeaCerti(1);
            } else if ("无".equals(emm.getSateListAndLeaCertiStr())) {
                emm.setSateListAndLeaCerti(0);
            } else if ("社保".equals(emm.getSateListAndLeaCertiStr())) {
                emm.setSateListAndLeaCerti(2);
            } else if ("离职".equals(emm.getSateListAndLeaCertiStr())) {
                emm.setSateListAndLeaCerti(3);
            }

            if ("毕业证".equals(emm.getOtherCertiStr())) {
                emm.setOtherCerti(1);
            } else if ("电工证".equals(emm.getOtherCertiStr())) {
                emm.setOtherCerti(2);
            } else if ("焊工证".equals(emm.getOtherCertiStr())) {
                emm.setOtherCerti(3);
            } else if ("结婚证".equals(emm.getOtherCertiStr())) {
                emm.setOtherCerti(4);
            }


            if ("健康证".equals(emm.getHealthCertiStr()) || "有".equals(emm.getHealthCertiStr()) || "是".equals(emm.getHealthCertiStr())) {
                emm.setHealthCerti(1);
            } else if ("体检单".equals(emm.getHealthCertiStr())) {
                emm.setHealthCerti(2);
            } else if ("职业病体检".equals(emm.getHealthCertiStr())) {
                emm.setHealthCerti(3);
            } else if ("无".equals(emm.getHealthCertiStr())) {
                emm.setHealthCerti(0);
            } else if (emm.getSateListAndLeaCertiStr() == null) {
                emm.setHealthCerti(0);
            }

            if ("经理".equals(emm.getPositionAttrIdStr())) {
                emm.setPositionAttrId(1);
            } else if ("主管".equals(emm.getPositionAttrIdStr())) {
                emm.setPositionAttrId(2);
            } else if ("组长".equals(emm.getPositionAttrIdStr())) {
                emm.setPositionAttrId(3);
            } else if ("职员".equals(emm.getPositionAttrIdStr())) {
                emm.setPositionAttrId(4);
            }
            UserInfo userInfo = personMapper.getUserInfoByEmpno(emm.getEmpNo());
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setEmpNo(emm.getEmpNo());
                userInfo.setFullName(emm.getName());
                userInfo.setUserName(emm.getEmpNo());
                userInfo.setUserPwd("cosun888");
                userInfo.setState(0);// 0代表未审核
                userInfo.setUseruploadright(1);//默认有上传
                userInfo.setUserActor(emm.getPositionAttrId());//默认普通员工
                userInfoMapper.saveUserInfoByBean(userInfo);
            }

            personMapper.saveEmployeeByBean(emm);
        }
    }

    public List<Time> getTimeList(String[] times) {
        if (times != null) {
            List<Time> timeList = new ArrayList<Time>();
            for (int i = 0; i < times.length; i++) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date d = format.parse(times[i] + ":00");
                    timeList.add(new java.sql.Time(d.getTime()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return timeList;
        }
        return null;
    }


    public List<OutPutWorkData> computeTableListData(List<ClockInOrgin> clockInOrginList) throws Exception {
        List<OutPutWorkData> outPutWorkData = new ArrayList<OutPutWorkData>();
        OutPutWorkData aaa = new OutPutWorkData();
        String errorMessage = "";
        List<Employee> employeeList = personMapper.findAllEmployeeAll();
        String[] months;
        String monstr = "";
        String yearMonth = "";
        List<String> monthList = new ArrayList<String>();
        for (ClockInOrgin c : clockInOrginList) {
            months = c.getDateStr().split("/");
            if (Integer.valueOf(months[1]) < 10) {
                months[1] = 0 + months[1];
            }
            monstr = months[0] + "-" + months[1] + "-" + months[2];
            yearMonth = months[0] + "-" + months[1];

            if (!monthList.contains(yearMonth)) {
                monthList.add(yearMonth);
                if (monthList.size() >= 2) {
                    errorMessage = "单次计算考勤只能计算同一月份，不能出现两个或两个以后的月份。比如这次您有" + monthList.toString() + "这几个月份";
                    aaa.setErrorMessage(errorMessage);
                    outPutWorkData.add(aaa);
                    return outPutWorkData;
                }
            }
        }
        String month = monthList.get(0);
        Employee em = null;
        ClockInOrgin co = null;
        int emLen = employeeList.size();
        String date = null;
        int clLen = clockInOrginList.size();
        WorkSet workSet = null;
        List<WorkDate> workDateList = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        OutPutWorkData otw = null;
        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDateList = personMapper.getWorkDateByMonthAnPositionLevelList(yearMonth, em.getPositionLevel());
            for (WorkDate workDate : workDateList) {
                int type = workDate.getType();//0正常工时  1 周末加班  2 法定假日
                if (workDate != null) {
                    for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                        date = workDate.getWorkDatess()[j];
                        boolean isWeekEnd = DateUtil.isWeekend(yearMonth + "-" + date);
                        for (int k = 0; k < clLen; k++) {
                            aon = false;
                            aoff = false;
                            pon = false;
                            poff = false;
                            co = clockInOrginList.get(k);
                            String[] coDay = co.getDateStr().split("/");
                            if (em.getName().equals(co.getEmployeeName()) && date.equals(coDay[2])) {
                                otw = new OutPutWorkData();
                                otw.setWorkArea(em.getWorkType());
                                otw.setEmployeeName(em.getName());
                                otw.setYearMonthDay(yearMonth + "-" + date);
                                otw.setEmpNo(em.getEmpNo());
                                otw.setPositionName(em.getPositionName());
                                otw.setPositionLevel(em.getPositionLevel());
                                otw.setDeptName(em.getDeptName());
                                otw.setWorkDate(workDate.getWorkDate());
                                otw.setWorkInDate(date);
                                otw.setYearMonth(yearMonth);
                                workSet = personMapper.getWorkSetByMonthAndPositionLevel2(yearMonth, em.getPositionLevel());
                                Time time = null;
                                if (workSet != null) {
                                    if (clockInOrginList.get(k).getTimes() != null && clockInOrginList.get(k).getTimes().length > 0) {
                                        otw.setOrginClockInStr(clockInOrginList.get(k).getTimeStr());
                                        List<Time> timeList = this.getTimeList(clockInOrginList.get(k).getTimes());
                                        for (int a = 0; a < timeList.size(); a++) {
                                            time = timeList.get(a);
                                            //上午上班
                                            if (workSet.getMorningOn() != null) {
                                                if (workSet.getMorningOnFrom() != null && workSet.getMorningOnEnd() != null) {
                                                    if (time.after(workSet.getMorningOnFrom()) && time.before(workSet.getMorningOnEnd()) || (time.equals(workSet.getMorningOn()))) {
                                                        aon = true;
                                                        otw.setWorkSetAOn(workSet.getMorningOn().toString());
                                                        otw.setWorkSetAonFroml(workSet.getMorningOnFrom().toString());
                                                        otw.setWorkSetAOnEnd(workSet.getMorningOnEnd().toString());
                                                        otw.setClockAOn(time.toString());
                                                        if (type == 0) {
                                                            if (isWeekEnd && em.getWorkType() == 0) {
                                                                otw.setIsAonOk("周六加班");
                                                            } else {
                                                                otw.setIsAonOk("正常");
                                                            }
                                                        } else if (type == 1) {
                                                            if (workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                otw.setIsAonOk("周末加班");
                                                            } else {
                                                                otw.setIsAonOk("周末不上班");
                                                            }
                                                        } else if (type == 2) {
                                                            otw.setIsAonOk("法定假日加班");
                                                        }
                                                    }
                                                } else {
                                                    errorMessage = em.getName() + "没有设置早上上班打卡时间段";
                                                    aaa.setErrorMessage(errorMessage);
                                                    outPutWorkData.add(aaa);
                                                    return outPutWorkData;
                                                }
                                            } else {
                                                errorMessage = em.getName() + "没有设置早上上班时间";
                                                aaa.setErrorMessage(errorMessage);
                                                outPutWorkData.add(aaa);
                                                return outPutWorkData;
                                            }
                                            //上午下班
                                            if (workSet.getMorningOff() != null) {
                                                if (workSet.getMorningOffFrom() != null && workSet.getMorningOffEnd() != null) {
                                                    if (time.after(workSet.getMorningOffFrom()) && time.before(workSet.getMorningOffEnd()) || (time.equals(workSet.getMorningOff()))) {
                                                        aoff = true;
                                                        otw.setWorkSetAOff(workSet.getMorningOff().toString());
                                                        otw.setWorkSetAOffFrom(workSet.getMorningOffFrom().toString());
                                                        otw.setWorkSetAOffEnd(workSet.getMorningOffEnd().toString());
                                                        otw.setClockAOff(time.toString());
                                                        if (type == 0) {
                                                            if (isWeekEnd && em.getWorkType() == 0) {
                                                                otw.setIsAoffOk("周六加班");
                                                            } else {
                                                                otw.setIsAoffOk("正常");
                                                            }
                                                        } else if (type == 1) {
                                                            if (workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                otw.setIsAoffOk("周末加班");
                                                            } else {
                                                                otw.setIsAoffOk("周末不上班");
                                                            }
                                                        } else if (type == 2) {
                                                            otw.setIsAoffOk("法定假日加班");
                                                        }
                                                    }
                                                } else {
                                                    aoff = true;
//                                                errorMessage = em.getName() + "没有设置早上下班打卡时间段";
//                                                aaa.setErrorMessage(errorMessage);
//                                                outPutWorkData.add(aaa);
//                                                return outPutWorkData;
                                                }
                                            } else {
                                                aoff = true;
//                                            errorMessage = em.getName() + "没有设置早上下班时间";
//                                            aaa.setErrorMessage(errorMessage);
//                                            outPutWorkData.add(aaa);
//                                            return outPutWorkData;
                                            }
                                            //下午上班
                                            if (workSet.getNoonOn() != null) {
                                                if (workSet.getNoonOnFrom() != null && workSet.getNoonOnEnd() != null) {
                                                    if (time.after(workSet.getNoonOnFrom()) && time.before(workSet.getNoonOnEnd()) || (time.equals(workSet.getNoonOn()))) {
                                                        pon = true;
                                                        otw.setWorkSetPOn(workSet.getNoonOn().toString());
                                                        otw.setWorkSetPOnFrom(workSet.getNoonOnFrom().toString());
                                                        otw.setWorkSetPOnEnd(workSet.getNoonOnEnd().toString());
                                                        otw.setClockPOn(time.toString());
                                                        if (type == 0) {
                                                            if (isWeekEnd && em.getWorkType() == 0) {
                                                                otw.setIsPOnOk("周六加班");
                                                            } else {
                                                                otw.setIsPOnOk("正常");
                                                            }
                                                        } else if (type == 1) {
                                                            if (workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                otw.setIsPOnOk("周末加班");
                                                            } else {
                                                                otw.setIsPOnOk("周末不上班");
                                                            }
                                                        } else if (type == 2) {
                                                            otw.setIsPOnOk("法定假日加班");
                                                        }
                                                    }
                                                } else {
                                                    pon = true;
//                                                errorMessage = em.getName() + "没有设置上午上班打卡时间段";
//                                                aaa.setErrorMessage(errorMessage);
//                                                outPutWorkData.add(aaa);
//                                                return outPutWorkData;
                                                }
                                            } else {
                                                pon = true;
//                                            errorMessage = em.getName() + "没有设置上午上班打卡时间";
//                                            aaa.setErrorMessage(errorMessage);
//                                            outPutWorkData.add(aaa);
//                                            return outPutWorkData;
                                            }
                                            //下午下班
                                            if (workSet.getNoonOff() != null) {
                                                if (workSet.getNoonOffFrom() != null && workSet.getNoonOffEnd() != null) {
                                                    if (time.after(workSet.getNoonOffFrom()) && time.before(workSet.getNoonOffEnd()) || (time.equals(workSet.getNoonOff()))) {
                                                        poff = true;
                                                        otw.setWorkSetPOff(workSet.getNoonOff().toString());
                                                        otw.setWorkSetPOffForm(workSet.getNoonOffFrom().toString());
                                                        otw.setWorkSetPOffEnd(workSet.getNoonOffEnd().toString());
                                                        otw.setClockPOff(time.toString());
                                                        if (type == 0) {
                                                            if (isWeekEnd && em.getWorkType() == 0) {
                                                                otw.setIsPOffOk("周六加班");
                                                            } else {
                                                                otw.setIsPOffOk("正常");
                                                            }
                                                        } else if (type == 1) {
                                                            if (workDate.getEmpNostr().contains(em.getEmpNo())) {
                                                                otw.setIsPOffOk("周末加班");
                                                            } else {
                                                                otw.setIsPOffOk("周末不上班");
                                                            }
                                                        } else if (type == 2) {
                                                            otw.setIsPOffOk("法定假日加班");
                                                        }
                                                    }
                                                } else {
                                                    poff = true;
//                                                errorMessage = em.getName() + "没有设置下午下班打卡时间段";
//                                                aaa.setErrorMessage(errorMessage);
//                                                outPutWorkData.add(aaa);
//                                                return outPutWorkData;
                                                }
                                            } else {
                                                poff = true;
//                                            errorMessage = em.getName() + "没有设置下午下班打卡时间";
//                                            aaa.setErrorMessage(errorMessage);
//                                            outPutWorkData.add(aaa);
//                                            return outPutWorkData;
                                            }
                                            //晚上加班
                                            Double extHours = 0.0;
                                            String abcd = null;
                                            String[] allNum = null;
                                            String intNum = "";
                                            String deciNum = "";
                                            if (workSet.getExtworkon() != null) {
                                                if (time.after(workSet.getExtworkonFrom()) && time.before(workSet.getExtworkonEnd())) {
                                                    Time lastT = timeList.get(timeList.size() - 1);
                                                    Long abc = lastT.getTime() - workSet.getExtworkon().getTime();
                                                    extHours = ((abc.doubleValue()) / (1000 * 60 * 60));
                                                    abcd = String.format("%.1f", extHours);
                                                    if (abcd != null && !extHours.equals("0.0")) {
                                                        String ccc = String.valueOf(abcd);
                                                        allNum = String.valueOf(abcd).split("\\.");
                                                        intNum = allNum[0];
                                                        deciNum = allNum[1];
                                                        if (Integer.valueOf(deciNum) >= 0 && Integer.valueOf(deciNum) < 5) {
                                                            deciNum = "0";
                                                        } else if (Integer.valueOf(deciNum) <= 9 && Integer.valueOf(deciNum) >= 5) {
                                                            deciNum = "5";
                                                        }
                                                        extHours = Double.valueOf(intNum + "." + deciNum);
                                                    }
                                                    otw.setWorkSetExtOn(time.toString());
                                                    otw.setWorkSetExtOff(lastT.toString());
                                                    if (extHours >= 0) {
                                                        otw.setExtHours(extHours);
                                                    }
                                                }
                                            }
                                        }

                                        if (!aon) {
                                            Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAonOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                otw.setIsAonOk("打卡时间不在规定时间范围内");
                                                otw.setRemark("打卡时间不在规定时间范围内");
                                            }
                                        }
                                        if (!aoff) {
                                            Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAoffOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAoffOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAoffOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                otw.setIsAoffOk("打卡时间不在规定时间范围内");
                                                otw.setRemark("打卡时间不在规定时间范围内");
                                            }
                                        }

                                        if (!pon) {
                                            Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOnOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOnOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOnOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                otw.setIsPOnOk("打卡时间不在规定时间范围内");
                                                otw.setRemark("打卡时间不在规定时间范围内");
                                            }
                                        }

                                        if (!poff) {
                                            Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("正常请假");
                                                    otw.setRemark("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("因公外出");
                                                    otw.setRemark("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOffOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                otw.setIsPOffOk("打卡时间不在规定时间范围内");
                                                otw.setRemark("打卡时间不在规定时间范围内");
                                            }
                                        }

                                    } else {
                                        Leave leave = personMapper.getLeaveByEmIdAndMonth(em.getId(), yearMonth + "-" + date + " " + "08:00:00", "2019-" + monstr + "-" + date + " " + "17:30:00");
                                        if (leave != null) {
                                            if (leave.getType() == 0) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setRemark("正常请假");
                                            } else if (leave.getType() == 1) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setRemark("因公外出");
                                            } else if (leave.getType() == 2) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setRemark("带薪年假");
                                            }
                                        } else {
                                            if (type == 0) {
                                                otw.setRemark("旷工");
                                            } else if (type == 1) {
                                                otw.setRemark("周末不上班");
                                            } else if (type == 2) {
                                                otw.setRemark("法定带薪假日");
                                            }
                                        }
                                        leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOn().toString());
                                        if (leave != null) {
                                            if (leave.getType() == 0) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsAonOk("正常请假");
                                                otw.setRemark("正常请假");
                                            } else if (leave.getType() == 1) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsAonOk("因公外出");
                                                otw.setRemark("因公外出");
                                            } else if (leave.getType() == 2) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsAonOk("带薪年假");
                                                otw.setRemark("带薪年假");
                                            }
                                        } else {
                                            if (type == 0) {
                                                otw.setIsAonOk("旷工");
                                                otw.setRemark("旷工");
                                            } else if (type == 1) {
                                                otw.setIsAonOk("周末不上班");
                                                otw.setRemark("周末不上班");
                                            } else if (type == 2) {
                                                otw.setIsAonOk("法定带薪假");
                                                otw.setRemark("法定带薪假");
                                            }
                                        }
                                        if (workSet.getMorningOff() != null) {
                                            leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getMorningOff().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("正常请假");
                                                    otw.setIsAoffOk("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("因公外出");
                                                    otw.setIsAoffOk("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsAoffOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                if (type == 0) {
                                                    otw.setIsAoffOk("旷工");
                                                    otw.setRemark("旷工");
                                                } else if (type == 1) {
                                                    otw.setIsAoffOk("周末不上班");
                                                    otw.setRemark("周末不上班");
                                                } else if (type == 2) {
                                                    otw.setIsAoffOk("法定带薪假");
                                                    otw.setRemark("法定带薪假");
                                                }
                                            }
                                        }
                                        if (workSet.getNoonOn() != null) {
                                            leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOn().toString());
                                            if (leave != null) {
                                                if (leave.getType() == 0) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("正常请假");
                                                    otw.setIsPOnOk("正常请假");
                                                } else if (leave.getType() == 1) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setRemark("因公外出");
                                                    otw.setIsPOnOk("因公外出");
                                                } else if (leave.getType() == 2) {
                                                    otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                    otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                    otw.setIsPOnOk("带薪年假");
                                                    otw.setRemark("带薪年假");
                                                }
                                            } else {
                                                if (type == 0) {
                                                    otw.setIsPOnOk("旷工");
                                                    otw.setRemark("旷工");
                                                } else if (type == 1) {
                                                    otw.setIsPOnOk("周末不上班");
                                                    otw.setRemark("周末不上班");
                                                } else if (type == 2) {
                                                    otw.setIsPOnOk("法定带薪假");
                                                    otw.setRemark("法定带薪假");
                                                }
                                            }
                                        }
                                        leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), yearMonth + "-" + date + " " + workSet.getNoonOff().toString());
                                        if (leave != null) {
                                            if (leave.getType() == 0) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsPOffOk("正常请假");
                                                otw.setRemark("正常请假");
                                            } else if (leave.getType() == 1) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsPOffOk("因公外出");
                                                otw.setRemark("因公外出");
                                            } else if (leave.getType() == 2) {
                                                otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                                otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                                otw.setIsPOffOk("带薪年假");
                                                otw.setRemark("带薪年假");
                                            }
                                        } else {
                                            if (type == 0) {
                                                otw.setIsPOffOk("旷工");
                                                otw.setRemark("旷工");
                                            } else if (type == 1) {
                                                otw.setIsPOffOk("周末不上班");
                                                otw.setRemark("周末不上班");
                                            } else if (type == 2) {
                                                otw.setIsPOffOk("法定带薪假");
                                                otw.setRemark("法定带薪假");
                                            }
                                        }
                                    }
                                } else {
                                    otw.setRemark("没有此人打卡记录");
                                }
                                if (otw.getEmployeeName() != null && otw.getEmployeeName().trim().length() > 0) {
                                    otw.setWorkType(type);
                                    outPutWorkData.add(otw);
                                }
                            }

                        }
                    }


                } else {
                    errorMessage = "您没有" + yearMonth + "年月份" + em.getName() + "人" + em.getPositionLevel() + "属性的排班表，请帮排单设置中设置。";
                    aaa.setErrorMessage(errorMessage);
                    outPutWorkData.add(aaa);
                    return outPutWorkData;
                }
            }


        }
        return outPutWorkData;

    }

    public List<Employee> findAllEmployeeAll() throws Exception {
        return personMapper.findAllEmployeeAll();
    }

    public List<Employee> findAllEmployeeFinance(Employee employee) throws Exception {
        return personMapper.findAllEmployeeFinance(employee);
    }

    public List<Employee> queryEmployeeSalaryByCondition(Employee employee) throws Exception {
        return personMapper.queryEmployeeSalaryByCondition(employee);
    }

    public int queryEmployeeSalaryByConditionCount(Employee employee) throws Exception {
        return personMapper.queryEmployeeSalaryByConditionCount(employee);
    }

    public void deleteEmployeeSalaryByEmpno(String empNo) throws Exception {
        personMapper.deleteEmployeeSalaryByEmpno(empNo);
    }

    public Employee getEmployeeByEmpno(String empNo) throws Exception {
        return personMapper.getEmployeeByEmpno(empNo);
    }


}


