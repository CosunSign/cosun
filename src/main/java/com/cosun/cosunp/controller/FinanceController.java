package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IFinanceServ;
import com.cosun.cosunp.service.IPersonServ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/17 下午 4:28
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/finance")
public class FinanceController {

    private static Logger logger = LogManager.getLogger(FinanceController.class);

    @Autowired
    IFinanceServ financeServ;

    @Autowired
    IPersonServ personServ;

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView tomainpage(HttpSession session) throws Exception {
        try {
            ModelAndView view = new ModelAndView("salaryimport");
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/toaddpersonsalarypage", method = RequestMethod.GET)
    public ModelAndView toaddpersonsalarypage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("addfinance");
        Employee employee = new Employee();
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("employee", employee);
        view.addObject("empList", empList);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeSalaryByempNo", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeSalaryByempNo(String empNo,HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("updatefinance");
        Employee employee = personServ.getEmployeeByEmpno(empNo);
        view.addObject("employee", employee);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmpByBatch", method = RequestMethod.GET)
    public  ModelAndView deleteEmpByBatch(Employee employee,HttpSession session) throws Exception {
        financeServ.deleteEmpSalaryByBatch(employee);
        ModelAndView view = new ModelAndView("finance");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        view.addObject("flag", 2);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addsalary", method = RequestMethod.POST)
    public ModelAndView addsalary(Employee employee,HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("finance");
        financeServ.addSalaryByBean(employee);
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(new Employee());
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeSalaryByEmpNo", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeSalaryByEmpNo(String empNo, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("finance");
            personServ.deleteEmployeeSalaryByEmpno(empNo);
            Employee employee = new Employee();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Employee> employeeList = personServ.findAllEmployeeFinance(employee);
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeSalaryByCondition", method = RequestMethod.POST)
    public void queryEmployeeSalaryByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryEmployeeSalaryByCondition(employee);
            int recordCount = personServ.queryEmployeeSalaryByConditionCount(employee);
            int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
            if (employeeList.size() > 0) {
                employeeList.get(0).setMaxPage(maxPage);
                employeeList.get(0).setRecordCount(recordCount);
                employeeList.get(0).setCurrentPage(employee.getCurrentPage());
                employeeList.get(0).setType(userInfo.getType());
            }
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(employeeList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tofinance")
    public ModelAndView tofinance(HttpSession session) throws Exception {
        ModelAndView view = new ModelAndView("finance");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        Employee employee = new Employee();
        List<Position> positionList = personServ.findAllPositionAll();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Employee> empList = personServ.findAllEmployeeAll();
        List<Employee> employeeList = personServ.findAllEmployeeFinance(employee);
        int recordCount = personServ.findAllEmployeeCount();
        int maxPage = recordCount % employee.getPageSize() == 0 ? recordCount / employee.getPageSize() : recordCount / employee.getPageSize() + 1;
        employee.setMaxPage(maxPage);
        employee.setRecordCount(recordCount);
        view.addObject("employeeList", employeeList);
        view.addObject("empList", empList);
        view.addObject("employee", employee);
        view.addObject("positionList", positionList);
        view.addObject("deptList", deptList);
        view.addObject("userInfo", userInfo);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/dataInMysql", method = RequestMethod.POST)
    public ModelAndView dataInMysql(@RequestParam("file1") MultipartFile file1, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("salaryimport");
        String errorMessage;
        try {
            List<Salary> salaryList = financeServ.translateExcelToBean(file1);
            financeServ.saveAllSalaryData(salaryList);
            view.addObject("flag", 1);
            return view;
        } catch (NumberFormatException e) {
            view.addObject("flag", 2);
            view.addObject("empnoerror", e.getMessage());
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return view;
        }
    }
}
