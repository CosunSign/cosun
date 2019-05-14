package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static Logger logger = LogManager.getLogger(FileUploadAndDownController.class);

    @Autowired
    IPersonServ personServ;
    private Integer flag;

    @ResponseBody
    @RequestMapping("/toworkdatepage")
    public ModelAndView toworkdatepage() throws Exception {
        ModelAndView view = new ModelAndView("workdatepage.html");
        return view;
    }

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView toUploadPage() throws Exception {
        ModelAndView view = new ModelAndView("person");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
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
    public ModelAndView topositanddeptpage(Position positiona, Dept depta, Integer flaga, Integer flagb) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        List<Position> positionList = personServ.findAllPosition(positiona);
        int recordCount = personServ.findAllPositionConditionCount();
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);
        view.addObject("position", position);
        view.addObject("positionList", positionList);
        if (flaga != null && flaga != 0) {
            view.addObject("flag", flaga);
        }
        //部门
        Dept dept = new Dept();
        List<Dept> deptList = personServ.findAllDept(depta);
        int recordCounta = personServ.findAllDeptConditionCount(depta);
        int maxPagea = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        dept.setMaxPage(maxPagea);
        dept.setRecordCount(recordCounta);
        view.addObject("dept", dept);
        view.addObject("deptList", deptList);

        if (flagb != null && flagb != 0) {
            view.addObject("flagb", flagb);
        }

        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave", method = RequestMethod.POST)
    public void checkAndSave(@RequestBody String positionname, HttpSession session, HttpServletResponse response) throws Exception {
        int isSave = personServ.checkAndSavePosition(positionname);
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
        }else{
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
        ModelAndView view = new ModelAndView("positanddeptpage");
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


        List<Dept> deptList = personServ.queryDeptByNameA(dept);
        int recordCount1 = personServ.queryDeptCountByNameA(dept);
        int maxPage1 = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage1);
        dept.setRecordCount(recordCount1);

        view.addObject("flag", 4);
        view.addObject("deptList", deptList);
        view.addObject("dept", dept);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept", method = RequestMethod.GET)
    public ModelAndView deleteDept(Integer id, Dept dept) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        personServ.deleteDeptById(id);
        List<Dept> deptList = personServ.findAllDept(dept);
        int recordCount = personServ.queryDeptCountByNameA(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("dept", dept);


        List<Position> positionList = new ArrayList<Position>();
        positionList = personServ.findAllPosition(position);
        int recordCount1 = personServ.queryPositionCountByNameA(position);
        int maxPage1 = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage1);
        position.setRecordCount(recordCount1);
        view.addObject("positionList", positionList);
        view.addObject("position", position);

        view.addObject("flag", 41);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/queryPositionByName", method = RequestMethod.GET)
    public ModelAndView queryPositionByName(String positionName, Integer currentpage) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        Dept dept = new Dept();
        if (currentpage == null)
            currentpage = 1;
        position.setCurrentPage(currentpage);
        position.setPositionName(positionName);
        List<Position> positionList = personServ.queryPositionByNameA(position);
        int recordCount = personServ.queryPositionCountByNameA(position);
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);

        List<Dept> deptList = personServ.queryDeptByNameA(dept);
        int recordCount1 = personServ.queryDeptCountByNameA(dept);
        int maxPage1 = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage1);
        dept.setRecordCount(recordCount1);

        view.addObject("positionName", positionName);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("position", position);
        view.addObject("flag", 3);
        view.addObject("dept", dept);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryDeptByName", method = RequestMethod.GET)
    public ModelAndView queryDeptByName(String deptName, Integer currentpage) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
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

        List<Position> positionList = personServ.queryPositionByNameA(position);
        int recordCount1 = personServ.queryPositionCountByNameA(position);
        int maxPage1 = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage1);
        position.setRecordCount(recordCount1);

        view.addObject("deptName", deptName);
        view.addObject("position", position);
        view.addObject("deptList", deptList);
        view.addObject("positionList", positionList);
        view.addObject("dept", dept);
        view.addObject("flag", 31);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/saveUpdateData", method = RequestMethod.GET)
    public ModelAndView saveUpdateData(Integer id, String positionName) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Dept dept = new Dept();
        Position position = new Position();
        int isExsit = personServ.checkIfExsit(positionName);
        if (isExsit == 0) {
            personServ.saveUpdateData(id, positionName);
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


        List<Dept> deptList = personServ.queryDeptByNameA(dept);
        int recordCount1 = personServ.queryDeptCountByNameA(dept);
        int maxPage1 = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage1);
        dept.setRecordCount(recordCount1);

        view.addObject("dept", dept);
        view.addObject("deptList", deptList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/saveUpdateData2", method = RequestMethod.GET)
    public ModelAndView saveUpdateData2(Integer id, String deptname) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        Dept dept = new Dept();
        int isExsit = personServ.checkIfExsit2(deptname);
        if (isExsit == 0) {
            personServ.saveUpdateData2(id, deptname);
            view.addObject("flag", 11);
        } else {
            view.addObject("flag", 51);//新名字已存在，保存失败
        }
        List<Dept> deptList = personServ.findAllDept(dept);
        int recordCount = personServ.findAllDeptConditionCount(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("dept", dept);
        view.addObject("deptList", deptList);


        List<Position> positionList = personServ.findAllPosition(position);
        int recordCount1 = personServ.findAllPositionConditionCount();
        int maxPage1 = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage1);
        position.setRecordCount(recordCount1);
        view.addObject("position", position);
        view.addObject("positionList", positionList);

        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ModelAndView addEmployee(Employee employee1) throws Exception {
        ModelAndView view = new ModelAndView("person");
        personServ.addEmployeeData(employee1);
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
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
    @RequestMapping(value = "/updatePersonToMysql", method = RequestMethod.GET)
    public ModelAndView updatePersonToMysql(Employee employee1) throws Exception {
        ModelAndView view = new ModelAndView("person");
        personServ.updateEmployeeData(employee1);

        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
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
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> employeeList = personServ.findAllEmployee(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("flag", 2);
        return view;
    }
}
