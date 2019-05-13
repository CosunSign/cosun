package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Position;
import com.cosun.cosunp.mapper.PersonMapper;
import com.cosun.cosunp.service.IPersonServ;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/9 0009 下午 5:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements IPersonServ {

    private static Logger logger = LogManager.getLogger(FileUploadAndDownServiceImpl.class);

    @Autowired
    PersonMapper personMapper;

    public int checkAndSavePosition(String positionName) throws Exception {
        int isExist = personMapper.findSaveOrNot(positionName);
        if (isExist > 0) {//代表数据库存在职位，不允许重复增加 0
            return isExist; //0代表重复
        } else {
            personMapper.savePosition(positionName);
        }
        return isExist;
    }

    public int checkAndSaveDept(String deptName) throws Exception {
        int isExist = personMapper.findSaveOrNot2(deptName);
        if (isExist > 0) {//代表数据库存在职位，不允许重复增加 0
            return isExist; //0代表重复
        } else {
            personMapper.saveDept(deptName);
        }
        return isExist;
    }

    public List<Position> findAllPosition(Position position) throws Exception {
        return personMapper.findAllPosition(position);
    }

    public List<Dept> findAllDept(Dept dept) throws Exception {
        return personMapper.findAllDept(dept);
    }

    public int findAllDeptConditionCount(Dept dep) throws Exception {
        return personMapper.findAllDeptConditionCount();
    }

    public void deletePositionById(Integer id) throws Exception {
        personMapper.deletePositionById(id);
    }

    public void deleteDeptById(Integer id) throws Exception {
        personMapper.deleteDeptById(id);
    }

    public int queryDeptCountByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDeptConditionCount();
        } else {
            return personMapper.findAllDeptByConditionCount(dept);
        }
    }

    public List<Position> queryPositionByName(String positionName) throws Exception {
        return personMapper.queryPositionByName(positionName);
    }


    public void saveUpdateData(Integer id, String positionName) throws Exception {
        personMapper.saveUpdateData(id, positionName);
    }

    public void saveUpdateData2(Integer id, String deptName) throws Exception {
        personMapper.saveUpdateData2(id, deptName);
    }

    public int findAllPositionConditionCount() throws Exception {
        return personMapper.findAllPositionConditionCount();
    }

    public int checkIfExsit(String positionName) throws Exception {
        return personMapper.checkIfExsit(positionName);
    }

    public int checkIfExsit2(String deptName) throws Exception {
        return personMapper.checkIfExsit2(deptName);
    }

    public List<Position> queryPositionByNameA(Position position) throws Exception {
        if (position.getPositionName() == null || position.getPositionName().trim().length() == 0) {
            return personMapper.findAllPosition(position);
        } else {
            return personMapper.queryPositionByNameA(position);
        }
    }

    public List<Dept> queryDeptByNameA(Dept dept) throws Exception {
        if (dept.getDeptname() == null || dept.getDeptname().trim().length() == 0) {
            return personMapper.findAllDept(dept);
        } else {
            return personMapper.queryDeptByNameA(dept);
        }
    }


    public int queryPositionCountByNameA(Position position) throws Exception {
        if (position.getPositionName() == null || position.getPositionName().trim().length() == 0) {
            return personMapper.findAllPositionConditionCount();
        } else {
            return personMapper.findAllPositionByConditionCount(position);
        }
    }

    public List<Position> findAllPositionAll() throws Exception {
        return personMapper.findAllPositionAll();
    }

    public List<Dept> findAllDeptAll() throws Exception {
        return personMapper.findAllDeptAll();
    }

    public void addEmployeeData(Employee employee) throws Exception {
        personMapper.addEmployeeData(employee);
    }

    public List<Employee> findAllEmployee(Employee employee) throws Exception {
        return personMapper.findAllEmployee(employee);
    }

    public int findAllEmployeeCount() throws Exception {
        return personMapper.findAllEmployeeCount();
    }

    public int checkEmployIsExsit(String name) throws Exception {
        return personMapper.checkEmployIsExsit(name);
    }

    public int checkEmployNoIsExsit(String empoyeeNo) throws Exception {
        return personMapper.checkEmployNoIsExsit(empoyeeNo);
    }

    public List<Employee> queryEmployeeByCondition(Employee employee) throws Exception {
        return personMapper.queryEmployeeByCondition(employee);
    }

    public int queryEmployeeByConditionCount(Employee employee) throws Exception {
        return personMapper.queryEmployeeByConditionCount(employee);

    }

    public void deleteEmployeetById(Integer id) throws Exception {
        personMapper.deleteEmployeetById(id);
    }

    public Employee getEmployeeById(Integer id) throws Exception {
       return personMapper.getEmployeeById(id);
    }

    public void updateEmployeeData(Employee employee) throws Exception {
        personMapper.updateEmployeeData(employee);
    }



}
