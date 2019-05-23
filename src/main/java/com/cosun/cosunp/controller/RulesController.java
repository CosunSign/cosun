package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.service.IrulesServ;
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
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/20 0020 下午 1:39
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Controller
@RequestMapping("/rules")
public class RulesController {

    private static Logger logger = LogManager.getLogger(RulesController.class);

    @Autowired
    IrulesServ rulesServ;

    @Autowired
    IPersonServ personServ;

    @ResponseBody
    @RequestMapping("/torulespage")
    public ModelAndView torulespage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("rules");
        Rules rules = new Rules();
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Rules> rulesList = rulesServ.findAllRules(rules);
        int recordCount = rulesServ.findAllRulesCount();
        int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                recordCount / rules.getPageSize() + 1;
        rules.setMaxPage(maxPage);
        rules.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("rules", rules);
        view.addObject("rulesList", rulesList);
        view.addObject("userInfo",userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toaddrulespage")
    public ModelAndView toaddrulespage(HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        ModelAndView view = new ModelAndView("addrules");
        Rules rules = new Rules();
        rules.setLoginName(userInfo.getFullName());
        List<Dept> deptList = personServ.findAllDeptAll();
        view.addObject("deptList", deptList);
        view.addObject("rules", rules);
        return view;
    }

    @ResponseBody
    @RequestMapping("/toupdateRulesById")
    public ModelAndView toupdateRulesById(Integer id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("updaterules");
        List<Dept> deptList = personServ.findAllDeptAll();
        Rules rules = rulesServ.getRulesById(id);
        modelAndView.addObject("rules", rules);
        modelAndView.addObject("deptList", deptList);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/downLoadByRulesId")
    public void downLoadByRulesId(HttpServletResponse resp, Integer id)
            throws Exception {
        Rules rule = rulesServ.getRulesById(id);
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Disposition", "attachment;filename=" + new String(rule.getFileName().getBytes(), "iso-8859-1"));
        byte[] buff = new byte[1024];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();

            File file = new File(rule.getFileDir());
            FileInputStream fis = new FileInputStream(file);
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
            }
        }
    }


    @ResponseBody
    @RequestMapping("/deleteRulesById")
    public ModelAndView deleteRulesById(Integer id,HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        rulesServ.deleteRulesById(id);
        Rules rules = new Rules();
        ModelAndView view = new ModelAndView("rules");
        List<Dept> deptList = personServ.findAllDeptAll();
        List<Rules> rulesList = rulesServ.findAllRules(rules);
        int recordCount = rulesServ.findAllRulesCount();
        int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                recordCount / rules.getPageSize() + 1;
        rules.setMaxPage(maxPage);
        rules.setRecordCount(recordCount);
        view.addObject("deptList", deptList);
        view.addObject("rules", rules);
        view.addObject("rulesList", rulesList);
        view.addObject("userInfo",userInfo);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/showRulesById", method = RequestMethod.POST)
    public void showRulesById(Integer id,HttpServletResponse response) throws Exception {
        Rules rules = rulesServ.getRulesById(id);
        int index = rules.getFileDir().lastIndexOf(".");
        String htmlName = rules.getFileDir().substring(0,index)+".html";
//        String str1;
//        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
//            str1 = x.writeValueAsString(new FileOutputStream(htmlName));
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().print(str1); //返回前端ajax
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(new FileInputStream(htmlName), "UTF-8"));
            String line;
            String htmlContent = "";
            while ((line = br.readLine()) != null) {
                htmlContent += line +"\n";
            }
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(htmlContent);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkRulesIsExsit", method = RequestMethod.POST)
    public void checkRulesIsExsit(@RequestBody Integer deptId, HttpServletResponse response) throws Exception {
        int isExist = rulesServ.getRulesByDeptId(deptId);
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(isExist);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryRulesByCondition", method = RequestMethod.POST)
    public void queryRulesByCondition(Rules rules, HttpServletResponse response,HttpSession session) throws Exception {
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        List<Rules> rulesList = rulesServ.queryRulesByCondition(rules);
        int recordCount = rulesServ.queryRulesByConditionCount(rules);
        int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() : recordCount / rules.getPageSize() + 1;
        if (rulesList.size() > 0) {
            rulesList.get(0).setMaxPage(maxPage);
            rulesList.get(0).setRecordCount(recordCount);
            rulesList.get(0).setCurrentPage(rules.getCurrentPage());
            rulesList.get(0).setUserActor(userInfo.getUserActor());
        }
        String str1;
        ObjectMapper x = new ObjectMapper();//ObjectMapper类提供方法将list数据转为json数据
        try {
            str1 = x.writeValueAsString(rulesList);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str1); //返回前端ajax
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/updateRules", method = RequestMethod.POST)
    public ModelAndView updateRules(@RequestParam("file") MultipartFile file, HttpSession session, Rules rules) throws Exception {
        ModelAndView view = new ModelAndView("rules");
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        rules.setUploaderId(userInfo.getuId());
        rules.setUploadDate(new Date());
        boolean isUploadFileRight = rulesServ.updateRulesById(file, rules);
        List<Rules> rulesList = rulesServ.findAllRules(new Rules());
        int recordCount = rulesServ.findAllRulesCount();
        int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                recordCount / rules.getPageSize() + 1;
        rules.setMaxPage(maxPage);
        rules.setRecordCount(recordCount);
        view.addObject("rulesList", rulesList);
        view.addObject("rules", rules);
        view.addObject("userInfo",userInfo);
        if(isUploadFileRight) {
            view.addObject("flag", 3);
        }else{
            view.addObject("flag", 5);
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/addRules", method = RequestMethod.POST)
    public ModelAndView addRules(@RequestParam("file") MultipartFile file, HttpSession session, Rules rules, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("rules");
        List<Dept> deptList = personServ.findAllDeptAll();
        UserInfo userInfo = (UserInfo) session.getAttribute("account");
        rules.setLoginName(userInfo.getFullName());
        rules.setUploadDate(new Date());
        rules.setUploaderId(userInfo.getuId());
        boolean isDocorDocx = rulesServ.saveRuleByRuleBean(file, rules);
        List<Rules> rulesList = rulesServ.findAllRules(new Rules());
        int recordCount = rulesServ.findAllRulesCount();
        int maxPage = recordCount % rules.getPageSize() == 0 ? recordCount / rules.getPageSize() :
                recordCount / rules.getPageSize() + 1;
        rules.setMaxPage(maxPage);
        rules.setRecordCount(recordCount);
        view.addObject("rulesList", rulesList);
        view.addObject("rules", rules);
        view.addObject("deptList", deptList);
        view.addObject("userInfo",userInfo);
        if (isDocorDocx) {
            view.addObject("flag", 1);
        } else {
            view.addObject("flag", 5);
        }
        return view;
    }

}
