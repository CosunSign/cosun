package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPersonServ {

    int checkAndSavePosition(Position position) throws Exception;

    List<ClockInOrgin> translateTabletoBean(MultipartFile file) throws Exception;

    List<OutPutWorkData> computeTableListData(List<ClockInOrgin> clockInOrginList) throws Exception;

    List<Employee> translateTabletoEmployeeBean(MultipartFile file) throws Exception;

    void saveDeptNameAndPositionNameAndEms(List<Employee> employeeList) throws Exception;

    String checkEmpNoOrEmpNameRepeat(List<Employee> employeeList) throws Exception;

    WorkDate getWorkDateByMonth(WorkDate workDate) throws Exception;

    WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate) throws Exception;

    String getPositionNamesByPositionLevel(String positionLevel) throws Exception;

    void saveOrUpdateWorkData(WorkDate workDate) throws Exception;

    int checkAndSaveDept(String deptName) throws Exception;

    int checkEmployIsExsit(String name) throws Exception;

    int checkEmployNoIsExsit(String empoyeeNo) throws Exception;

    List<Position> findAllPositionAll() throws Exception;

    void deleteLeaveByBatch(List<Integer> ids) throws Exception;

    void updateLeaveToMysql(Leave leave) throws Exception;

    List<Employee> findAllEmployees() throws Exception;

    Leave getLeaveById(Integer id) throws Exception;

    List<Dept> findAllDeptAll() throws Exception;


    void deleteLeaveById(Integer id) throws Exception;

    void updateEmployeeData(Employee employee) throws Exception;

    List<Position> findAllPosition(Position position) throws Exception;

    void addEmployeeData(Employee employee) throws Exception;

    List<Employee> findAllEmployeeAll() throws Exception;

    List<Employee> findAllEmployee(Employee employee) throws Exception;

    List<WorkSet> findAllWorkSet(WorkSet workSet) throws Exception;


    void updateWorkSetDataById(WorkSet workSet) throws Exception;

    void deleteWorkSetById(WorkSet workSet) throws Exception;

    int findAllWorkSetCount(WorkSet workSet) throws Exception;

    List<Employee> getEmployeeById(Integer id) throws Exception;

    WorkSet getWorkSetById(Integer id) throws Exception;

    void addWorkSetData(WorkSet workSet) throws Exception;

    List<Employee> queryEmployeeByCondition(Employee employee) throws Exception;

    List<WorkSet> queryWorkSetByCondition(WorkSet workSet) throws Exception;

    int queryWorkSetByConditionCount(WorkSet workSet) throws Exception;

    List<Leave> queryLeaveByCondition(Leave leave) throws Exception;

    void deleteEmpByBatch(List<Integer> ids) throws Exception;

    int queryLeaveByConditionCount(Leave leave) throws Exception;

    int queryEmployeeByConditionCount(Employee employee) throws Exception;

    List<Dept> findAllDept(Dept dept) throws Exception;

    void deleteDeptByBatch(List<Integer> ids) throws Exception;

    int checkBeginLeaveRight(Leave leave) throws Exception;

    void deleteEmployeetById(Integer deleteEmployeetById) throws Exception;

    int findAllEmployeeCount() throws Exception;

    List<Leave> findAllLeave(Leave leave) throws Exception;

    void addLeaveData(Leave leave) throws Exception;

    int findAllLeaveCount() throws Exception;

    int findAllDeptConditionCount(Dept dep) throws Exception;

    void deletePositionById(Integer id) throws Exception;

    void deletePositionByIdBatch(List<Integer> ids) throws Exception;

    void deleteDeptById(Integer id) throws Exception;

    List<Position> queryPositionByName(String positionName) throws Exception;

    List<Position> queryPositionByNameA(Position position) throws Exception;

    List<Dept> queryDeptByNameA(Dept dept) throws Exception;

    int queryPositionCountByNameA(Position position) throws Exception;

    int queryDeptCountByNameA(Dept dept) throws Exception;

    void saveUpdateData(Integer id, String positionName, String positionLevel) throws Exception;

    void saveUpdateData2(Integer id, String deptname) throws Exception;

    int findAllPositionConditionCount() throws Exception;

    int checkIfExsit(String positionName) throws Exception;

    int checkIfExsit2(String deptName) throws Exception;
}
