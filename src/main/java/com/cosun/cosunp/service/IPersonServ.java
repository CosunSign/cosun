package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Position;

import java.util.List;

public interface IPersonServ {

    int checkAndSavePosition(String positionName) throws Exception;

    int checkAndSaveDept(String deptName) throws Exception;

    List<Position> findAllPosition(Position position) throws Exception;

    List<Dept> findAllDept(Dept dept) throws Exception;

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
