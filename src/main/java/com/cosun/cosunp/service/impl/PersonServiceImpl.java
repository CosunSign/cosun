package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.service.IPersonServ;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    PersonMapper personMapper;

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

    public void addEmployeeData(Employee employee) throws Exception {
        personMapper.addEmployeeData(employee);
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
        personMapper.deleteEmployeetById(id);
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
            personMapper.saveWorkData(workDate);
        } else {//update
            personMapper.updateWorkData(workDate);
        }

    }


    public void updateEmployeeData(Employee employee) throws Exception {
        personMapper.updateEmployeeData(employee);
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


    public List<ClockInOrgin> translateTabletoBean(Compute compute, MultipartFile file) throws Exception {
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
        for (int i = 2; i < rowNums; i++) {
            cell = xlsfSheet.getRow(i);
            if (cell != null && cell.length > 0) {
                name = cell[1].getContents().trim();
                if (name.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell[1].getContents().trim());
                    em.setName(cell[2].getContents().trim());
                    em.setDeptName(cell[3].getContents().trim());
                    em.setPositionName(cell[4].getContents().trim());
                    employeeList.add(em);
                }
            }
        }

        int rowNums2 = xlsfSheet2.getRows();
        jxl.Cell[] cell2 = null;
        String name2 = null;
        for (int i = 2; i < rowNums2; i++) {
            cell2 = xlsfSheet2.getRow(i);
            if (cell2 != null && cell2.length > 0) {
                name2 = cell2[1].getContents().trim();
                if (name2.length() > 0) {
                    em = new Employee();
                    em.setEmpNo(cell2[1].getContents().trim());
                    em.setName(cell2[2].getContents().trim());
                    em.setDeptName(cell2[3].getContents().trim());
                    em.setPositionName(cell2[4].getContents().trim());
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
                personMapper.addPositionByName(em.getPositionName());
                positions.add(em.getPositionName());
            }
        }

        for (Employee emm : employeeList) {
            Dept dept = personMapper.getDeptByName(emm.getDeptName());
            Position position = personMapper.getPositionByName(emm.getPositionName());
            emm.setDeptId(dept.getId());
            emm.setPositionId(position.getId());
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
        List<String> monthList = new ArrayList<String>();
        for (ClockInOrgin c : clockInOrginList) {
            months = c.getDateStr().split("/");
            if (!monthList.contains(months[1])) {
                monthList.add(months[1]);
                if (monthList.size() >= 2) {
                    errorMessage = "单次计算考勤只能计算同一月份，不能出现两个或两个以后的月份。比如这次您有" + monthList.toString() + "这几个月份";
                    aaa.setErrorMessage(errorMessage);
                    outPutWorkData.add(aaa);
                    return outPutWorkData;
                }
            }
        }
        String month = monthList.get(0);
        WorkDate workDate = null;
        Employee em = null;
        ClockInOrgin co = null;
        int emLen = employeeList.size();
        String date = null;
        int clLen = clockInOrginList.size();
        WorkSet workSet = null;
        boolean aon = false;
        boolean aoff = false;
        boolean pon = false;
        boolean poff = false;
        OutPutWorkData otw = null;
        for (int i = 0; i < emLen; i++) {
            em = employeeList.get(i);
            workDate = personMapper.getWorkDateByMonthAnPositionLevel(Integer.valueOf(month), em.getPositionLevel());
            if (workDate != null) {
                for (int j = 0; j < workDate.getWorkDatess().length; j++) {
                    date = workDate.getWorkDatess()[j];
                    for (int k = 0; k < clLen; k++) {
                        aon = false;
                        aoff = false;
                        pon = false;
                        poff = false;
                        co = clockInOrginList.get(k);
                        String[] coDay = co.getDateStr().split("/");
                        if (em.getName().equals(co.getEmployeeName()) && date.equals(coDay[2])) {
                            otw = new OutPutWorkData();
                            otw.setEmployeeName(em.getName());
                            otw.setEmpNo(em.getEmpNo());
                            otw.setPositionName(em.getPositionName());
                            otw.setPositionLevel(em.getPositionLevel());
                            otw.setDeptName(em.getDeptName());
                            otw.setWorkDate(workDate.getWorkDate());
                            otw.setWorkInDate(date);
                            workSet = personMapper.getWorkSetByMonthAndPositionLevel2(Integer.valueOf(month), em.getPositionLevel());
                            Time time = null;
                            if (workSet != null) {
                                String beforeM = "";
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
                                                    otw.setIsAonOk("正常");
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
                                                    otw.setIsAoffOk("正常");
                                                }
                                            } else {
                                                errorMessage = em.getName() + "没有设置早上下班打卡时间段";
                                                aaa.setErrorMessage(errorMessage);
                                                outPutWorkData.add(aaa);
                                                return outPutWorkData;
                                            }
                                        } else {
                                            errorMessage = em.getName() + "没有设置早上下班时间";
                                            aaa.setErrorMessage(errorMessage);
                                            outPutWorkData.add(aaa);
                                            return outPutWorkData;
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
                                                    otw.setIsPOnOk("正常");
                                                }
                                            } else {
                                                errorMessage = em.getName() + "没有设置上午上班打卡时间段";
                                                aaa.setErrorMessage(errorMessage);
                                                outPutWorkData.add(aaa);
                                                return outPutWorkData;
                                            }
                                        } else {
                                            errorMessage = em.getName() + "没有设置上午上班打卡时间";
                                            aaa.setErrorMessage(errorMessage);
                                            outPutWorkData.add(aaa);
                                            return outPutWorkData;
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
                                                    otw.setIsPOffOk("正常");
                                                }
                                            } else {
                                                errorMessage = em.getName() + "没有设置下午下班打卡时间段";
                                                aaa.setErrorMessage(errorMessage);
                                                outPutWorkData.add(aaa);
                                                return outPutWorkData;
                                            }
                                        } else {
                                            errorMessage = em.getName() + "没有设置下午下班打卡时间";
                                            aaa.setErrorMessage(errorMessage);
                                            outPutWorkData.add(aaa);
                                            return outPutWorkData;
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
                                                otw.setExtHours(extHours);
                                            }
                                        }
                                    }
                                    if (Integer.valueOf(month) < 10) {
                                        beforeM = "0";
                                    }
                                    if (!aon) {
                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getMorningOn().toString());
                                        if (leave != null) {
                                            otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                            otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                            otw.setIsAonOk("请假");
                                        } else {
                                            otw.setIsAonOk("打卡时间不在规定时间范围内");
                                        }
                                    }
                                    if (!aoff) {
                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getMorningOff().toString());
                                        if (leave != null) {
                                            otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                            otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                            otw.setIsAoffOk("请假");
                                        } else {
                                            otw.setIsAoffOk("打卡时间不在规定时间范围内");
                                        }
                                    }

                                    if (!pon) {
                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getNoonOn().toString());
                                        if (leave != null) {
                                            otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                            otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                            otw.setIsPOnOk("请假");
                                        } else {
                                            otw.setIsPOnOk("打卡时间不在规定时间范围内");
                                        }
                                    }

                                    if (!poff) {
                                        Leave leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getNoonOff().toString());
                                        if (leave != null) {
                                            otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                            otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                            otw.setIsPOffOk("请假");
                                        } else {
                                            otw.setIsPOffOk("打卡时间不在规定时间范围内");
                                        }
                                    }

                                } else {
                                    Leave leave = personMapper.getLeaveByEmIdAndMonth(em.getId(), "2019-" + month + "-" + date + " " + "08:00:00", "2019-" + month + "-" + date + " " + "17:30:00");
                                    if (leave != null) {
                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                        otw.setRemark("请假");
                                    } else {
                                        otw.setRemark("旷工");
                                    }
                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getMorningOn().toString());
                                    if (leave != null) {
                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                        otw.setIsAonOk("请假");
                                    } else {
                                        otw.setIsAonOk("旷工");
                                    }
                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getMorningOff().toString());
                                    if (leave != null) {
                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                        otw.setIsAoffOk("请假");
                                    } else {
                                        otw.setIsAoffOk("旷工");
                                    }
                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getNoonOn().toString());
                                    if (leave != null) {
                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                        otw.setIsPOnOk("请假");
                                    } else {
                                        otw.setIsPOnOk("旷工");
                                    }
                                    leave = personMapper.getLeaveByEmIdAndMonthA(em.getId(), "2019-" + beforeM + month + "-" + date + " " + workSet.getNoonOff().toString());
                                    if (leave != null) {
                                        otw.setLeaveDateStart(leave.getBeginLeaveStr());
                                        otw.setLeaveDateEnd(leave.getEndLeaveStr());
                                        otw.setIsPOffOk("请假");
                                    } else {
                                        otw.setIsPOffOk("旷工");
                                    }
                                }
                            } else {
                                otw.setRemark("没有此人打卡记录");
                            }
                            if (otw.getEmployeeName() != null && otw.getEmployeeName().trim().length() > 0) {
                                outPutWorkData.add(otw);
                            }
                        }

                    }
                }


            } else {
                errorMessage = "您没有" + month + "月份" + em.getName() + "人" + em.getPositionLevel() + "属性的排班表，请帮排单设置中设置。";
                aaa.setErrorMessage(errorMessage);
                outPutWorkData.add(aaa);
                return outPutWorkData;
            }
        }

        return outPutWorkData;

    }

    public List<Employee> findAllEmployeeAll() throws Exception {
        return personMapper.findAllEmployeeAll();
    }

}


