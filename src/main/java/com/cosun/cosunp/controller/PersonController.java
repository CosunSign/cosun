package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/8 0008 下午 2:55
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    private static Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    IPersonServ personServ;
    private Integer flag;

    @ResponseBody
    @RequestMapping("/toworkdatepage")
    public ModelAndView toworkdatepage() throws Exception {
        ModelAndView view = new ModelAndView("workdatepage");
        return view;
    }

    @ResponseBody
    @RequestMapping("/tocomputeworkdate")
    public ModelAndView tocomputeworkdate() throws Exception {
        ModelAndView view = new ModelAndView("computeworkdate");
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        Compute compute = new Compute();
        compute.setFileLocation(com.getPath());
        view.addObject("compute", compute);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toworksetpage")
    public ModelAndView toworksetpage() throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        WorkSet workSet = new WorkSet();
        List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
        int recordCount = personServ.findAllWorkSetCount(workSet);
        int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
        workSet.setMaxPage(maxPage);
        workSet.setRecordCount(recordCount);
        view.addObject("workSet", workSet);
        view.addObject("workSetList", workSetList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView toUploadPage() throws Exception {
        ModelAndView view = new ModelAndView("person");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);

        return view;
    }

    @ResponseBody
    @RequestMapping("/toleavepage")
    public ModelAndView toleavepage() throws Exception {
        ModelAndView view = new ModelAndView("leave");
        Leave leave = new Leave();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Leave> leaveList = personServ.findAllLeave(leave);
        int recordCount = personServ.findAllLeaveCount();
        int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
        leave.setMaxPage(maxPage);
        leave.setRecordCount(recordCount);
        view.addObject("leaveList", leaveList);
        view.addObject("leave", leave);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddleavepage")
    public ModelAndView toaddleavepage() throws Exception {
        ModelAndView view = new ModelAndView("addleavepage");
        List<Employee> employeeList = personServ.findAllEmployees();
        view.addObject("leave", new Leave());
        view.addObject("employeeList", employeeList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddworksetpage")
    public ModelAndView toaddworksetpage() throws Exception {
        ModelAndView view = new ModelAndView("addworksetpage");
        view.addObject("workSet", new WorkSet());
        return view;
    }

    @ResponseBody
    @RequestMapping("/toUpdateLeaveById")
    public ModelAndView toUpdateLeaveById(Integer id) throws Exception {
        ModelAndView view = new ModelAndView("updateleavepage");
        List<Employee> employeeList = personServ.findAllEmployees();
        Leave leave = personServ.getLeaveById(id);
        view.addObject("leave", leave);
        view.addObject("employeeList", employeeList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/updateLeaveToMysql")
    public ModelAndView updateLeaveToMysql(Leave leavea) throws Exception {
        personServ.updateLeaveToMysql(leavea);
        ModelAndView view = new ModelAndView("leave");
        Leave leave = new Leave();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Leave> leaveList = personServ.findAllLeave(leave);
        int recordCount = personServ.findAllLeaveCount();
        int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
        leave.setMaxPage(maxPage);
        leave.setRecordCount(recordCount);
        view.addObject("leaveList", leaveList);
        view.addObject("leave", leave);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/deleteLeaveById")
    public ModelAndView deleteLeaveById(Integer id) throws Exception {
        personServ.deleteLeaveById(id);
        ModelAndView view = new ModelAndView("leave");
        Leave leave = new Leave();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Leave> leaveList = personServ.findAllLeave(leave);
        int recordCount = personServ.findAllLeaveCount();
        int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
        leave.setMaxPage(maxPage);
        leave.setRecordCount(recordCount);
        view.addObject("leaveList", leaveList);
        view.addObject("leave", leave);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddpersonpage")
    public ModelAndView toaddpersonpage() throws Exception {
        ModelAndView view = new ModelAndView("addpersonpage");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("employee", new Employee());
        return view;
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage")
    public ModelAndView topositanddeptpage(Position positiona, Integer flagb) throws Exception {
        ModelAndView view = new ModelAndView("positionpage");
        Position position = new Position();
        List<Position> positionList = personServ.findAllPosition(positiona);
        int recordCount = personServ.findAllPositionConditionCount();
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);
        view.addObject("position", position);
        view.addObject("positionList", positionList);
        view.addObject("flag", 0);
        return view;
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage2")
    public ModelAndView topositanddeptpage2(Dept dept) throws Exception {
        ModelAndView view = new ModelAndView("deptpage");
        List<Dept> deptList = personServ.findAllDept(dept);
        int recordCount = personServ.queryDeptCountByNameA(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("dept", dept);
        view.addObject("flag", 0);


        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/computeWorkTable", method = RequestMethod.POST)
    public ModelAndView computeWorkTable(@RequestParam("file") MultipartFile file, @RequestParam("fileLocation") String fileLocation, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("computeworkdate");
        Compute compute = new Compute();
        compute.setFileLocation(fileLocation);
        compute.setFileLocation(fileLocation);
        List<ClockInOrgin> clockInOrginList = personServ.translateTabletoBean(compute, file);
        List<OutPutWorkData> outPutWorkDataList = personServ.computeTableListData(clockInOrginList);
        String outpathname = "";
        if (outPutWorkDataList.get(0).getErrorMessage() == null || outPutWorkDataList.get(0).getErrorMessage().trim().length() <= 0) {
            outpathname = ExcelUtil.writeExcel(outPutWorkDataList, fileLocation);
        }
        view.addObject("flag2", outpathname);
        view.addObject("errorMessage", outPutWorkDataList.get(0).getErrorMessage());
        view.addObject("compute", compute);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/dataInMysql", method = RequestMethod.POST)
    public ModelAndView dataInMysql(@RequestParam("file1") MultipartFile file1, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("computeworkdate");
        List<Employee> employeeList = personServ.translateTabletoEmployeeBean(file1);
        String isRepeatData = personServ.checkEmpNoOrEmpNameRepeat(employeeList);//true 代表重复
        if (isRepeatData.trim().length() > 0) {
            view.addObject("flag1", isRepeatData);//代表有重复
            return view;
        } else {
            personServ.saveDeptNameAndPositionNameAndEms(employeeList);
        }

        view.addObject("compute", new Compute());
        view.addObject("flag1", 10);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave", method = RequestMethod.POST)
    public void checkAndSave(Position position, HttpSession session, HttpServletResponse response) throws Exception {
        int isSave = personServ.checkAndSavePosition(position);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkDatesAndPositionsByData", method = RequestMethod.POST)
    public void getWorkDatesAndPositionsByData(WorkDate workDate, HttpServletResponse response) throws Exception {
        WorkSet workSet = personServ.getWorkSetByMonthAndPositionLevel(workDate);
        String workDatesAndPositionNames;
        if (workSet == null) {
            WorkDate workDate1 = personServ.getWorkDateByMonth(workDate);
            String positionStr = personServ.getPositionNamesByPositionLevel(workDate.getPositionLevel());
            if (workDate1 != null && positionStr != null) {
                workDatesAndPositionNames = workDate1.getWorkDate() + "$" + positionStr;
            } else {
                if (workDate1 != null) {
                    workDatesAndPositionNames = "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";
                } else if (positionStr != null) {
                    workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                } else {
                    workDatesAndPositionNames = "您的排单表中没有" + workDate.getMonth() + "月份和" + workDate.getPositionLevel() + "职位类别的数据，请前往排单设置新增;";
                    workDatesAndPositionNames += "您所有的职位信息没有" + workDate.getPositionLevel() + "职位类别的数据，请前往职位模块设置;";

                }
            }
        } else {
            workDatesAndPositionNames = "系统中已存在" + workDate.getMonth() + "月份和" + workDate.getPositionLevel() + "职位类别的数据，不可重复增加;";

        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(workDatesAndPositionNames);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdateWorkDate", method = RequestMethod.POST)
    public void saveOrUpdateWorkDate(WorkDate workDate, HttpServletResponse response) throws Exception {
        String str = "";
        for (int i = 0; i < workDate.getWorkDates().length - 1; i++) {
            str += workDate.getWorkDates()[i].trim() + ",";
        }
        str += workDate.getWorkDates()[workDate.getWorkDates().length - 1].trim();
        workDate.setWorkDate(str);
        personServ.saveOrUpdateWorkData(workDate);
        workDate = personServ.getWorkDateByMonth(workDate);
        List<WorkDate> workDateList = new ArrayList<WorkDate>();
        workDateList.add(workDate);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(workDateList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getWorkDateByMonth", method = RequestMethod.POST)
    public void getWorkDateByMonth(WorkDate workDate, HttpServletResponse response) throws Exception {
        List<WorkDate> workDateList = new ArrayList<WorkDate>();
        workDate = personServ.getWorkDateByMonth(workDate);
        if (workDate != null) {
            workDateList.add(workDate);
        } else {
            WorkDate w = new WorkDate();
            w.setWorkDate("");
            workDateList.add(w);
        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(workDateList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getemployeebyId", method = RequestMethod.POST)
    public void getemployeebyId(@RequestBody Integer employeeId, HttpServletResponse response) throws Exception {
        List<Employee> employees = personServ.getEmployeeById(employeeId);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(employees);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addWorkSet")
    public ModelAndView addWorkSet(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        workSet.setUpdateDate(new Date());
        personServ.addWorkSetData(workSet);
        List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
        int recordCount = personServ.findAllWorkSetCount(workSet);
        int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
        workSet.setMaxPage(maxPage);
        workSet.setRecordCount(recordCount);
        view.addObject("workSetList", workSetList);
        view.addObject("flag", 1);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/updateWorkSet")
    public ModelAndView updateWorkSet(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        workSet.setUpdateDate(new Date());
        personServ.updateWorkSetDataById(workSet);
        List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
        int recordCount = personServ.findAllWorkSetCount(workSet);
        int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
        workSet.setMaxPage(maxPage);
        workSet.setRecordCount(recordCount);
        view.addObject("workSetList", workSetList);
        view.addObject("flag", 3);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteWorkSetById", method = RequestMethod.GET)
    public ModelAndView deleteWorkSetById(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        personServ.deleteWorkSetById(workSet);
        List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
        int recordCount = personServ.findAllWorkSetCount(workSet);
        int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
        workSet.setMaxPage(maxPage);
        workSet.setRecordCount(recordCount);
        view.addObject("workSetList", workSetList);
        view.addObject("flag", 2);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/addLeaveToMysql", method = RequestMethod.GET)
    public ModelAndView addLeaveToMysql(Leave leavea) throws Exception {
        ModelAndView view = new ModelAndView("leave");
        personServ.addLeaveData(leavea);
        Leave leave = new Leave();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Leave> leaveList = personServ.findAllLeave(leave);
        int recordCount = personServ.findAllLeaveCount();
        int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
        leave.setMaxPage(maxPage);
        leave.setRecordCount(recordCount);
        view.addObject("leaveList", leaveList);
        view.addObject("leave", leave);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        return view;

    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave2", method = RequestMethod.POST)
    public void checkAndSave2(@RequestBody String deptname, HttpSession session, HttpServletResponse response) throws Exception {
        int isSave = personServ.checkAndSaveDept(deptname);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmployNoIsExsit", method = RequestMethod.POST)
    public void checkEmployNoIsExsit(@RequestBody String empoyeeNo, HttpServletResponse response) throws Exception {
        int isSave = personServ.checkEmployNoIsExsit(empoyeeNo);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkEmployIsExsit", method = RequestMethod.POST)
    public void checkEmployIsExsit(@RequestBody String name, HttpServletResponse response) throws Exception {
        int isSave = personServ.checkEmployIsExsit(name);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isSave);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deletePosition", method = RequestMethod.GET)
    public ModelAndView deletePosition(Integer id, Position position) throws Exception {
        ModelAndView view = new ModelAndView("positionpage");
        Dept dept = new Dept();
        personServ.deletePositionById(id);

        List<Position> positionList = new ArrayList<Position>();
        positionList = personServ.findAllPosition(position);
        int recordCount = personServ.queryPositionCountByNameA(position);
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);
        view.addObject("positionList", positionList);
        view.addObject("position", position);
        view.addObject("flag", 4);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept", method = RequestMethod.GET)
    public ModelAndView deleteDept(Integer id, Dept dept) throws Exception {
        ModelAndView view = new ModelAndView("deptpage");
        personServ.deleteDeptById(id);
        List<Dept> deptList = personServ.findAllDept(dept);
        int recordCount = personServ.queryDeptCountByNameA(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("dept", dept);
        view.addObject("flag", 4);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/queryPositionByName", method = RequestMethod.GET)
    public ModelAndView queryPositionByName(String positionName, String positionLevel, Integer currentpage) throws Exception {
        ModelAndView view = new ModelAndView("positionpage");
        Position position = new Position();
        if (currentpage == null)
            currentpage = 1;
        position.setCurrentPage(currentpage);
        position.setPositionName(positionName);
        if (!"0".equals(positionLevel)) {
            position.setPositionLevel(positionLevel);
        }
        List<Position> positionList = personServ.queryPositionByNameA(position);
        int recordCount = personServ.queryPositionCountByNameA(position);
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);

        view.addObject("positionName", positionName);
        view.addObject("positionList", positionList);
        view.addObject("position", position);
        view.addObject("flag", 3);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryDeptByName", method = RequestMethod.GET)
    public ModelAndView queryDeptByName(String deptName, Integer currentpage, Integer showFlag) throws Exception {
        ModelAndView view = new ModelAndView("deptpage");
        Dept dept = new Dept();
        Position position = new Position();
        if (currentpage == null)
            currentpage = 1;
        dept.setCurrentPage(currentpage);
        dept.setDeptname(deptName);
        List<Dept> deptList = personServ.queryDeptByNameA(dept);
        int recordCount = personServ.queryDeptCountByNameA(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);


        view.addObject("deptName", deptName);
        view.addObject("deptList", deptList);
        view.addObject("dept", dept);
        view.addObject("flag", 3);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/saveUpdateData", method = RequestMethod.GET)
    public ModelAndView saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception {
        ModelAndView view = new ModelAndView("positionpage");
        Dept dept = new Dept();
        Position position = new Position();
        int isExsit = personServ.checkIfExsit(positionName);
        if (isExsit == 0) {
            personServ.saveUpdateData(id, positionName, positionLevel);
            view.addObject("flag", 1);
        } else {
            view.addObject("flag", 5);//新名字已存在，保存失败
        }
        List<Position> positionList = personServ.findAllPosition(position);
        int recordCount = personServ.findAllPositionConditionCount();
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);
        view.addObject("position", position);
        view.addObject("positionList", positionList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/saveUpdateData2", method = RequestMethod.GET)
    public ModelAndView saveUpdateData2(Integer id, String deptname) throws Exception {
        ModelAndView view = new ModelAndView("deptpage");
        Position position = new Position();
        Dept dept = new Dept();
        int isExsit = personServ.checkIfExsit2(deptname);
        if (isExsit == 0) {
            personServ.saveUpdateData2(id, deptname);
            view.addObject("flag", 1);
        } else {
            view.addObject("flag", 5);//新名字已存在，保存失败
        }
        List<Dept> deptList = personServ.findAllDept(dept);
        int recordCount = personServ.findAllDeptConditionCount(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("dept", dept);
        view.addObject("deptList", deptList);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ModelAndView addEmployee(Employee employee1) throws Exception {
        ModelAndView view = new ModelAndView("person");
        personServ.addEmployeeData(employee1);
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("flag", 1);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeById", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeById(Employee employee) throws Exception {
        ModelAndView view = new ModelAndView("updatepersonpage");
        List<Employee> employees = personServ.getEmployeeById(employee.getId());
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("employee", employees.get(0));
        return view;

    }


    @ResponseBody
    @RequestMapping(value = "/toupdateWorkSetById", method = RequestMethod.GET)
    public ModelAndView toupdateWorkSetById(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("updateworksetpage");
        workSet = personServ.getWorkSetById(workSet.getId());
        WorkDate a = new WorkDate();
        a.setMonth(workSet.getMonth());
        a.setPositionLevel(workSet.getWorkLevel());
        a = personServ.getWorkDateByMonth(a);
        workSet.setWorkDate(a.getWorkDate());
        workSet.setWorkLevel(personServ.getPositionNamesByPositionLevel(workSet.getWorkLevel()));
        view.addObject("workSet", workSet);
        return view;

    }

    @ResponseBody
    @RequestMapping(value = "/updatePersonToMysql", method = RequestMethod.GET)
    public ModelAndView updatePersonToMysql(Employee employee1) throws Exception {
        ModelAndView view = new ModelAndView("person");
        personServ.updateEmployeeData(employee1);
        List<Employee> empList = personServ.findAllEmployees();
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("flag", 3);
        return view;

    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeByCondition", method = RequestMethod.POST)
    public void queryEmployeeByCondition(Employee employee, HttpServletResponse response) throws Exception {
        List<Employee> employeeList = personServ.queryEmployeeByCondition(employee);
        int recordCount = personServ.queryEmployeeByConditionCount(employee);
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        if (employeeList.size() > 0) {
            employeeList.get(0).setMaxPage(maxPage);
            employeeList.get(0).setRecordCount(recordCount);
            employeeList.get(0).setCurrentPage(employee.getCurrentPage());
        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/queryWorkSetByCondition", method = RequestMethod.POST)
    public void queryWorkSetByCondition(WorkSet workSet, HttpServletResponse response) throws Exception {
        List<WorkSet> workSetList = personServ.queryWorkSetByCondition(workSet);
        int recordCount = personServ.queryWorkSetByConditionCount(workSet);
        int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
        if (workSetList.size() > 0) {
            workSetList.get(0).setMaxPage(maxPage);
            workSetList.get(0).setRecordCount(recordCount);
            workSetList.get(0).setCurrentPage(workSet.getCurrentPage());
        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(workSetList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkBeginLeaveRight", method = RequestMethod.POST)
    public void checkBeginLeaveRight(Leave leave, HttpServletResponse response) throws Exception {
        int isright = personServ.checkBeginLeaveRight(leave);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isright);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryLeaveByCondition", method = RequestMethod.POST)
    public void queryLeaveByCondition(Leave leave, HttpServletResponse response) throws Exception {
        List<Leave> leaveList = personServ.queryLeaveByCondition(leave);
        int recordCount = personServ.queryLeaveByConditionCount(leave);
        int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
        if (leaveList.size() > 0) {
            leaveList.get(0).setMaxPage(maxPage);
            leaveList.get(0).setRecordCount(recordCount);
            leaveList.get(0).setCurrentPage(leave.getCurrentPage());
        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(leaveList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeById", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeById(Integer id) throws Exception {
        ModelAndView view = new ModelAndView("person");
        personServ.deleteEmployeetById(id);
        Employee employee = new Employee();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("flag", 2);
        return view;
    }
}
