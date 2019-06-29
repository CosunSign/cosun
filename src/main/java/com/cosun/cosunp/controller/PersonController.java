package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.ExcelUtil;
import com.cosun.cosunp.tool.MathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
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

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @ResponseBody
    @RequestMapping("/toworkdatepage")
    public ModelAndView toworkdatepage() throws Exception {
        ModelAndView view = new ModelAndView("workdatepage");
        List<Employee> empList = personServ.findAllEmployeeAll();
        view.addObject("empList",empList);
        return view;
    }

    @ResponseBody
    @RequestMapping("/tocomputeworkdate")
    public ModelAndView tocomputeworkdate() throws Exception {
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File com = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
            Compute compute = new Compute();
            compute.setFileLocation(com.getPath());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toworksetpage")
    public ModelAndView toworksetpage() throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/tomainpage")
    public ModelAndView toUploadPage(HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toleavepage")
    public ModelAndView toleavepage() throws Exception {
        try {
            ModelAndView view = new ModelAndView("leave");
            Leave leave = new Leave();
            List<Position> positionList = personServ.findAllPositionAll();
            List<Employee> empList = personServ.findAllEmployeeAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            List<Leave> leaveList = personServ.findAllLeave(leave);
            int recordCount = personServ.findAllLeaveCount();
            int maxPage = recordCount % leave.getPageSize() == 0 ? recordCount / leave.getPageSize() : recordCount / leave.getPageSize() + 1;
            leave.setMaxPage(maxPage);
            leave.setRecordCount(recordCount);
            view.addObject("leaveList", leaveList);
            view.addObject("empList",empList);
            view.addObject("leave", leave);
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toaddleavepage")
    public ModelAndView toaddleavepage() throws Exception {
        try {
            ModelAndView view = new ModelAndView("addleavepage");
            List<Employee> employeeList = personServ.findAllEmployees();
            view.addObject("leave", new Leave());
            view.addObject("employeeList", employeeList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
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
        try {
            ModelAndView view = new ModelAndView("updateleavepage");
            List<Employee> employeeList = personServ.findAllEmployees();
            Leave leave = personServ.getLeaveById(id);
            view.addObject("leave", leave);
            view.addObject("employeeList", employeeList);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/updateLeaveToMysql")
    public ModelAndView updateLeaveToMysql(Leave leavea) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteLeaveByBatch")
    public ModelAndView deleteLeaveByBatch(Leave leave) throws Exception {
        try {
            personServ.deleteLeaveByBatch(leave.getIds());
            ModelAndView view = new ModelAndView("leave");
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteLeaveById")
    public ModelAndView deleteLeaveById(Integer id) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/toaddpersonpage")
    public ModelAndView toaddpersonpage(HttpSession session) throws Exception {
        try {
            ModelAndView mav = new ModelAndView("register");
            List<Employee> employeeList = personServ.findAllEmployees();
            mav.addObject("employeeList", employeeList);
            UserInfo info = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("addpersonpage");
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("employee", new Employee());
            view.addObject("userInfo", info);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage")
    public ModelAndView topositanddeptpage(Position positiona, Integer flagb) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage2")
    public ModelAndView topositanddeptpage2(Dept dept) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/computeWorkEmpHours", method = RequestMethod.POST)
    public ModelAndView computeWorkEmpHours(@RequestParam("file") MultipartFile file, HttpServletResponse resp, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("downloadstatus")){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:"+cookie.getName());
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            Compute compute = new Compute();
            List<ClockInOrgin> clockInOrginList = personServ.translateTabletoBean(file);
            List<OutPutWorkData> outPutWorkDataList = personServ.computeTableListData(clockInOrginList);
            List<Employee> employeeList = personServ.findAllEmployeeAll();
            List<SubEmphours> subEmphoursList = MathUtil.computeSubEmpHours(outPutWorkDataList,employeeList);
            String outpathname = "计算完成，请下载查看!";
            if (outPutWorkDataList.get(0).getErrorMessage() == null || outPutWorkDataList.get(0).getErrorMessage().trim().length() <= 0) {
                resp.setHeader("content-type", "application/octet-stream");
                resp.setContentType("application/octet-stream");
                List<String> pathName = ExcelUtil.writeExcelSubWorkHours(subEmphoursList,outPutWorkDataList.get(0).getYearMonth(), finalDirPath);
                resp.setHeader("Content-Disposition", "attachment;filename=" + new String(pathName.get(0).getBytes(), "iso-8859-1"));
                byte[] buff = new byte[1024];
                BufferedInputStream bufferedInputStream = null;
                OutputStream outputStream = null;
                Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
                cookie.setMaxAge(5 * 60);// 设置为30min
                cookie.setPath("/");
                resp.addCookie(cookie);
                try {
                    outputStream = resp.getOutputStream();
                    File fi = new File(pathName.get(1));
                    FileInputStream fis = new FileInputStream(fi);
                    bufferedInputStream = new BufferedInputStream(fis);
                    int num = bufferedInputStream.read(buff);
                    while (num != -1) {
                        outputStream.write(buff, 0, num);
                        outputStream.flush();
                        num = bufferedInputStream.read(buff);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                        outputStream.close();
                    }
                }
            }
            view.addObject("flag2", outpathname);
            view.addObject("errorMessage", outPutWorkDataList.get(0).getErrorMessage());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/computeWorkTable", method = RequestMethod.POST)
    public ModelAndView computeWorkTable(@RequestParam("file") MultipartFile file, HttpServletResponse resp, HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookie==============");
        } else {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("downloadstatus")){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:"+cookie.getName());
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
        try {
            ModelAndView view = new ModelAndView("computeworkdate");
            Compute compute = new Compute();
            List<ClockInOrgin> clockInOrginList = personServ.translateTabletoBean(file);
            List<OutPutWorkData> outPutWorkDataList = personServ.computeTableListData(clockInOrginList);
            String outpathname = "计算完成，请下载查看!";
            if (outPutWorkDataList.get(0).getErrorMessage() == null || outPutWorkDataList.get(0).getErrorMessage().trim().length() <= 0) {
                resp.setHeader("content-type", "application/octet-stream");
                resp.setContentType("application/octet-stream");
                List<String> pathName = ExcelUtil.writeExcel(outPutWorkDataList, finalDirPath);
                resp.setHeader("Content-Disposition", "attachment;filename=" + new String(pathName.get(0).getBytes(), "iso-8859-1"));
                byte[] buff = new byte[1024];
                BufferedInputStream bufferedInputStream = null;
                OutputStream outputStream = null;
                Cookie cookie = new Cookie("downloadstatus", String.valueOf(new Date().getTime()));
                cookie.setMaxAge(5 * 60);// 设置为30min
                cookie.setPath("/");
                resp.addCookie(cookie);
                try {
                    outputStream = resp.getOutputStream();
                    File fi = new File(pathName.get(1));
                    FileInputStream fis = new FileInputStream(fi);
                    bufferedInputStream = new BufferedInputStream(fis);
                    int num = bufferedInputStream.read(buff);
                    while (num != -1) {
                        outputStream.write(buff, 0, num);
                        outputStream.flush();
                        num = bufferedInputStream.read(buff);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                        outputStream.close();
                    }
                }
            }
            view.addObject("flag2", outpathname);
            view.addObject("errorMessage", outPutWorkDataList.get(0).getErrorMessage());
            view.addObject("compute", compute);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/dataInMysql", method = RequestMethod.POST)
    public ModelAndView dataInMysql(@RequestParam("file1") MultipartFile file1, HttpServletResponse response) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave", method = RequestMethod.POST)
    public void checkAndSave(Position position, HttpSession session, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkAndSavePosition(position);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据

            str1 = x.writeValueAsString(isSave);
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
    @RequestMapping(value = "/getWorkDatesAndPositionsByData", method = RequestMethod.POST)
    public void getWorkDatesAndPositionsByData(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            WorkSet workSet = personServ.getWorkSetByMonthAndPositionLevel(workDate);
            String workDatesAndPositionNames;
            if (workSet == null) {
                WorkDate workDate1 = personServ.getWorkDateByMonth2(workDate);
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

            str1 = x.writeValueAsString(workDatesAndPositionNames);
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
    @RequestMapping(value = "/getWorkDatesAndPositionsByData2", method = RequestMethod.POST)
    public void getWorkDatesAndPositionsByData2(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
            WorkSet workSet = personServ.getWorkSetByMonthAndPositionLevel(workDate);
            String workDatesAndPositionNames = "";
            if (workSet != null) {
                WorkDate workDate1 = personServ.getWorkDateByMonth2(workDate);
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
            }
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据

            str1 = x.writeValueAsString(workDatesAndPositionNames);
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
    @RequestMapping(value = "/saveOrUpdateWorkDate", method = RequestMethod.POST)
    public void saveOrUpdateWorkDate(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
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

            str1 = x.writeValueAsString(workDateList);
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
    @RequestMapping(value = "/getWorkDateByMonth", method = RequestMethod.POST)
    public void getWorkDateByMonth(WorkDate workDate, HttpServletResponse response) throws Exception {
        try {
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

            str1 = x.writeValueAsString(workDateList);
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
    @RequestMapping(value = "/getemployeebyId", method = RequestMethod.POST)
    public void getemployeebyId(@RequestBody Integer employeeId, HttpServletResponse response) throws Exception {
        try {
            List<Employee> employees = personServ.getEmployeeById(employeeId);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(employees);
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
    @RequestMapping(value = "/addWorkSet")
    public ModelAndView addWorkSet(WorkSet workSet) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/updateWorkSet")
    public ModelAndView updateWorkSet(WorkSet workSet) throws Exception {
        ModelAndView view = new ModelAndView("worksetpage");
        try {
            workSet.setUpdateDate(new Date());
            personServ.updateWorkSetDataById(workSet);
            List<WorkSet> workSetList = personServ.findAllWorkSet(workSet);
            int recordCount = personServ.findAllWorkSetCount(workSet);
            int maxPage = recordCount % workSet.getPageSize() == 0 ? recordCount / workSet.getPageSize() : recordCount / workSet.getPageSize() + 1;
            workSet.setMaxPage(maxPage);
            workSet.setRecordCount(recordCount);
            view.addObject("workSetList", workSetList);
            view.addObject("flag", 3);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteWorkSetById", method = RequestMethod.GET)
    public ModelAndView deleteWorkSetById(WorkSet workSet) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/addLeaveToMysql", method = RequestMethod.GET)
    public ModelAndView addLeaveToMysql(Leave leavea) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/checkAndSave2", method = RequestMethod.POST)
    public void checkAndSave2(@RequestBody String deptname, HttpSession session, HttpServletResponse response) throws
            Exception {
        try {
            int isSave = personServ.checkAndSaveDept(deptname);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(isSave);
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
    @RequestMapping(value = "/checkEmployNoIsExsit", method = RequestMethod.POST)
    public void checkEmployNoIsExsit(@RequestBody String empoyeeNo, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkEmployNoIsExsit(empoyeeNo);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(isSave);
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
    @RequestMapping(value = "/checkEmployIsExsit", method = RequestMethod.POST)
    public void checkEmployIsExsit(@RequestBody String name, HttpServletResponse response) throws Exception {
        try {
            int isSave = personServ.checkEmployIsExsit(name);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(isSave);
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
    @RequestMapping(value = "/deletePositionByBatch", method = RequestMethod.GET)
    public ModelAndView deletePositionByBatch(Position position) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
            personServ.deletePositionByIdBatch(position.getIds());
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/deletePosition", method = RequestMethod.GET)
    public ModelAndView deletePosition(Integer id, Position position) throws Exception {
        try {
            ModelAndView view = new ModelAndView("positionpage");
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept", method = RequestMethod.GET)
    public ModelAndView deleteDept(Integer id, Dept dept) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDeptByBatch", method = RequestMethod.GET)
    public ModelAndView deleteDeptByBatch(Dept dept) throws Exception {
        try {
            ModelAndView view = new ModelAndView("deptpage");
            personServ.deleteDeptByBatch(dept.getIds());
            List<Dept> deptList = personServ.findAllDept(dept);
            int recordCount = personServ.queryDeptCountByNameA(dept);
            int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
            dept.setMaxPage(maxPage);
            dept.setRecordCount(recordCount);
            view.addObject("deptList", deptList);
            view.addObject("dept", dept);
            view.addObject("flag", 4);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryPositionByName", method = RequestMethod.GET)
    public ModelAndView queryPositionByName(String positionName, String positionLevel, Integer currentpage) throws
            Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/queryDeptByName", method = RequestMethod.GET)
    public ModelAndView queryDeptByName(String deptName, Integer currentpage, Integer showFlag) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void checkPositionName(Position position, HttpServletResponse response) throws Exception {
        try {
            int isExsit = personServ.checkIfExsit(position.getPositionName());
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
            str1 = x.writeValueAsString(isExsit);
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
    @RequestMapping(value = "/saveUpdateData", method = RequestMethod.GET)
    public ModelAndView saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkDept", method = RequestMethod.POST)
    public void checkDept(Dept dept, HttpServletResponse response) throws Exception {
        try {
            int isExsit = personServ.checkIfExsit2(dept.getDeptname());
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据

            str1 = x.writeValueAsString(isExsit);
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
    @RequestMapping(value = "/saveUpdateData2", method = RequestMethod.GET)
    public ModelAndView saveUpdateData2(Integer id, String deptname) throws Exception {
        try {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/showImage")
    public void showImage(Integer id, Integer type, HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        List<Employee> ee = personServ.getEmployeeById(id);
        String pathName = "";
        if (type == 1) {
            pathName = ee.get(0).getEducationLeUrl();
        }else if(type==2) {
            pathName = ee.get(0).getSateListAndLeaCertiUrl();
        }else if(type==3) {
            pathName = ee.get(0).getOtherCertiUrl();
        }
        FileInputStream fis = new FileInputStream(pathName);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public ModelAndView addEmployee(@RequestParam("educationLeFile") MultipartFile educationLeFile,
                                    @RequestParam("sateListAndLeaCertiFile") MultipartFile sateListAndLeaCertiFile,
                                    @RequestParam("otherCertiFile") MultipartFile otherCertiFile,
                                    Employee employee1, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.addEmployeeData(educationLeFile, sateListAndLeaCertiFile, otherCertiFile, employee1);
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/toupdateEmployeeById", method = RequestMethod.GET)
    public ModelAndView toupdateEmployeeById(Employee employee) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updatepersonpage");
            List<Employee> employees = personServ.getEmployeeById(employee.getId());
            List<Position> positionList = personServ.findAllPositionAll();
            List<Dept> deptList = personServ.findAllDeptAll();
            view.addObject("positionList", positionList);
            view.addObject("deptList", deptList);
            view.addObject("employee", employees.get(0));
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }


    @ResponseBody
    @RequestMapping(value = "/toupdateWorkSetById", method = RequestMethod.GET)
    public ModelAndView toupdateWorkSetById(WorkSet workSet) throws Exception {
        try {
            ModelAndView view = new ModelAndView("updateworksetpage");
            workSet = personServ.getWorkSetById(workSet.getId());
            WorkDate a = new WorkDate();
            a.setMonth(workSet.getMonth());
            a.setPositionLevel(workSet.getWorkLevel());
            a = personServ.getWorkDateByMonth2(a);
            workSet.setWorkDate(a.getWorkDate());
            workSet.setWorkLevelStr(personServ.getPositionNamesByPositionLevel(workSet.getWorkLevel()));
            view.addObject("workSet", workSet);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/updatePersonToMysql", method = RequestMethod.POST)
    public ModelAndView updatePersonToMysql(@RequestParam("educationLeFile") MultipartFile educationLeFile,
                                            @RequestParam("sateListAndLeaCertiFile") MultipartFile sateListAndLeaCertiFile,
                                            @RequestParam("otherCertiFile") MultipartFile otherCertiFile,
                                            Employee employee1, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.updateEmployeeData(educationLeFile, sateListAndLeaCertiFile, otherCertiFile, employee1);
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/queryEmployeeByCondition", method = RequestMethod.POST)
    public void queryEmployeeByCondition(Employee employee, HttpServletResponse response, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            List<Employee> employeeList = personServ.queryEmployeeByCondition(employee);
            int recordCount = personServ.queryEmployeeByConditionCount(employee);
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
    @RequestMapping(value = "/queryWorkSetByCondition", method = RequestMethod.POST)
    public void queryWorkSetByCondition(WorkSet workSet, HttpServletResponse response) throws Exception {
        try {
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

            str1 = x.writeValueAsString(workSetList);
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
    @RequestMapping(value = "/checkBeginLeaveRight", method = RequestMethod.POST)
    public void checkBeginLeaveRight(Leave leave, HttpServletResponse response) throws Exception {
        try {
            int isright = personServ.checkBeginLeaveRight(leave);
            String str1;
            ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据

            str1 = x.writeValueAsString(isright);
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
    @RequestMapping(value = "/queryLeaveByCondition", method = RequestMethod.POST)
    public void queryLeaveByCondition(Leave leave, HttpServletResponse response) throws Exception {
        try {
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

            str1 = x.writeValueAsString(leaveList);
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
    @RequestMapping(value = "/deleteEmpByBatch", method = RequestMethod.GET)
    public ModelAndView deleteEmpByBatch(Employee employee, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
            ModelAndView view = new ModelAndView("person");
            personServ.deleteEmpByBatch(employee.getIds());
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteEmployeeById", method = RequestMethod.GET)
    public ModelAndView deleteEmployeeById(Integer id, HttpSession session) throws Exception {
        try {
            UserInfo userInfo = (UserInfo) session.getAttribute("account");
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
            view.addObject("userInfo", userInfo);
            return view;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

//    正常出勤工时+法定有薪假时间+平时加班工时+其它有薪假时间+周末加班工时
}
