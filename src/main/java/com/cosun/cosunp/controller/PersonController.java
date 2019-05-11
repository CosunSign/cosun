package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Position;
import com.cosun.cosunp.service.IPersonServ;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @RequestMapping("/tomainpage")
    public ModelAndView toUploadPage() throws Exception {
        ModelAndView view = new ModelAndView("person");
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddpersonpage")
    public ModelAndView toaddpersonpage() throws Exception {
        ModelAndView view = new ModelAndView("addpersonpage");
        return view;
    }

    @ResponseBody
    @RequestMapping("/topositanddeptpage")
    public ModelAndView topositanddeptpage(Position positiona,Dept depta, Integer flaga, Integer flagb) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        List<Position> positionList = personServ.findAllPosition(positiona);
        int recordCount = personServ.findAllPositionConditionCount();
        int maxPage = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage);
        position.setRecordCount(recordCount);
        view.addObject("position", position);
        view.addObject("positionList", positionList);
        if (flaga!=null && flaga != 0) {
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

        if (flagb!=null && flagb != 0) {
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
        view.addObject("position",position);


        List<Dept> deptList = personServ.queryDeptByNameA(dept);
        int recordCount1 = personServ.queryDeptCountByNameA(dept);
        int maxPage1 = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage1);
        dept.setRecordCount(recordCount1);

        view.addObject("flag", 4);
        view.addObject("deptList", deptList);
        view.addObject("dept",dept);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDept", method = RequestMethod.GET)
    public ModelAndView deleteDept(Integer id, Dept dept) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        personServ.deleteDeptById(id);
        List<Dept> deptList  = personServ.findAllDept(dept);
        int recordCount = personServ.queryDeptCountByNameA(dept);
        int maxPage = recordCount % dept.getPageSize() == 0 ? recordCount / dept.getPageSize() : recordCount / dept.getPageSize() + 1;
        dept.setMaxPage(maxPage);
        dept.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("dept",dept);


        List<Position> positionList = new ArrayList<Position>();
        positionList = personServ.findAllPosition(position);
        int recordCount1 = personServ.queryPositionCountByNameA(position);
        int maxPage1 = recordCount % position.getPageSize() == 0 ? recordCount / position.getPageSize() : recordCount / position.getPageSize() + 1;
        position.setMaxPage(maxPage1);
        position.setRecordCount(recordCount1);
        view.addObject("positionList", positionList);
        view.addObject("position",position);

        view.addObject("flag", 41);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/queryPositionByName", method = RequestMethod.GET)
    public ModelAndView queryPositionByName(String positionName,Integer currentpage) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Position position = new Position();
        Dept dept = new Dept();
        if(currentpage==null)
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
        view.addObject("dept",dept);
        return view;
    }



    @ResponseBody
    @RequestMapping(value = "/queryDeptByName", method = RequestMethod.GET)
    public ModelAndView queryDeptByName(String deptName,Integer currentpage) throws Exception {
        ModelAndView view = new ModelAndView("positanddeptpage");
        Dept dept = new Dept();
        Position position = new Position();
        if(currentpage==null)
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
        if(isExsit==0) {
            personServ.saveUpdateData(id, positionName);
            view.addObject("flag", 1);
        }else{
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

        view.addObject("dept",dept);
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
        if(isExsit==0) {
            personServ.saveUpdateData2(id, deptname);
            view.addObject("flag", 11);
        }else{
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

}
