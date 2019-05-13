package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Position;

import java.util.List;

public interface IPersonServ {

    int checkAndSavePosition(String positionName) throws Exception;

    int checkAndSaveDept(String deptName) throws Exception;

    int checkEmployIsExsit(String name) throws Exception;

    int checkEmployNoIsExsit(String empoyeeNo) throws Exception;

    List<Position> findAllPositionAll() throws Exception;

    List<Dept> findAllDeptAll() throws Exception;

    void updateEmployeeData(Employee employee) throws Exception;

    List<Position> findAllPosition(Position position) throws Exception;

    void addEmployeeData(Employee employee) throws Exception;

    List<Employee> findAllEmployee(Employee employee) throws Exception;

    Employee getEmployeeById(Integer id) throws Exception;

    List<Employee> queryEmployeeByCondition(Employee employee) throws Exception;

    int queryEmployeeByConditionCount(Employee employee) throws Exception;

    List<Dept> findAllDept(Dept dept) throws Exception;

    void deleteEmployeetById(Integer deleteEmployeetById) throws Exception;

    int findAllEmployeeCount() throws Exception;

    int findAllDeptConditionCount(Dept dep) throws Exception;

    void deletePositionById(Integer id) throws  Exception;

    void deleteDeptById(Integer id) throws Exception;

    List<Position> queryPositionByName(String positionName) throws Exception;

    List<Position> queryPositionByNameA(Position position) throws Exception;

    List<Dept> queryDeptByNameA(Dept dept) throws Exception;

    int queryPositionCountByNameA(Position position) throws Exception;

    int queryDeptCountByNameA(Dept dept) throws Exception;

    void saveUpdateData(Integer id,String positionName) throws Exception;

    void saveUpdateData2(Integer id,String deptname) throws Exception;

    int findAllPositionConditionCount() throws Exception;

    int checkIfExsit(String positionName) throws Exception;

    int checkIfExsit2(String deptName) throws Exception;
}
