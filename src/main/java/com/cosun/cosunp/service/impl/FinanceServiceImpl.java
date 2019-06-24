package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Salary;
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
                cell = xlsfSheet.getRow(1);
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
                }
            }

            for (int i = 2; i < rowNums; i++) {
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
                        salaryList.add(sa);
                    }
                }
            }
            return salaryList;
        }catch (Exception e) {
            throw new NumberFormatException(empNo);
        }
    }

    public void saveAllSalaryData(List<Salary> salaryList) throws Exception {
        financeMapper.deleteAllSalaryData();
        for(Salary sa : salaryList) {
            financeMapper.saveSalary(sa);
        }

    }

    public void addSalaryByBean(Employee employee) throws Exception {
        Salary sa = financeMapper.getSalaryByEmpno(employee.getEmpNo());
        if(sa!=null) {
            financeMapper.updateSalaryByBean(employee);
        }else{
            financeMapper.addSalaryByBean(employee);

        }
    }

    public void deleteEmpSalaryByBatch(Employee employee) throws Exception {
        financeMapper.deleteEmpSalaryByBatch(employee.getIds());
    }


}
