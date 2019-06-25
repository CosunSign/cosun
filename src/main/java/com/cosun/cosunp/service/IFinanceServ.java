package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.EmpHours;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.FinanceSetUpData;
import com.cosun.cosunp.entity.Salary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFinanceServ {

    List<Salary> translateExcelToBean(MultipartFile file1) throws Exception;

    void saveAllSalaryData(List<Salary> salaryList) throws Exception;

    void addSalaryByBean(Employee employee) throws Exception;

    void deleteEmpSalaryByBatch(Employee employee) throws Exception;

    void deleteEmpHoursByBatch(Employee employee) throws Exception;

    List<EmpHours> translateExcelToBeanEmpHours(MultipartFile file1,String yearMonth) throws Exception;

    void saveAllEmpHours(List<EmpHours> empHoursList,String yearMonth) throws Exception;

    List<EmpHours> findAllEmpHours(Employee employee) throws Exception;

    void updateEmpHoursByBean(EmpHours empHours) throws Exception;

    int findAllEmpHoursHours() throws Exception;

    FinanceSetUpData findFinanceSetUpData() throws Exception;

    void saveFinanceSetUp(FinanceSetUpData financeSetUpData) throws Exception;

    void addEmpHoursByBean(EmpHours empHours) throws Exception;

    int checkEmpNoandYearMonthIsExsit(EmpHours empHours) throws Exception;

    List<EmpHours> queryEmployeeHoursByCondition(Employee employee) throws Exception;

    int queryEmployeeHoursByConditionCount(Employee employee) throws Exception;

    void deleteEmployeeHoursByEmpno(String empNo) throws Exception;

    EmpHours getEmpHoursByEmpNo(String empNo) throws Exception;

}
