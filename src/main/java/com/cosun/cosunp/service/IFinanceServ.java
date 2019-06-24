package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Salary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFinanceServ {

    List<Salary> translateExcelToBean(MultipartFile file1) throws Exception;

    void saveAllSalaryData(List<Salary> salaryList) throws Exception;

    void addSalaryByBean(Employee employee) throws Exception;

    void deleteEmpSalaryByBatch(Employee employee) throws Exception;

}
