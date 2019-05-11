package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Position;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/9 0009 下午 5:04
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Repository
@Mapper
public interface PersonMapper {

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%') ")
    int findSaveOrNot(String positionName);

    @Select("select count(*) from dept where deptName like  CONCAT('%',#{deptName},'%') ")
    int findSaveOrNot2(String deptName);

    @Insert("insert into position (positionName) values (#{positionName})")
    void savePosition(String positionName);

    @Insert("insert into dept (deptName) values (#{deptName})")
    void saveDept(String deptName);

    @Select("select count(*) from position")
    int findAllPositionConditionCount();

    @Select("select count(*) from dept")
    int findAllDeptConditionCount();

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%')")
    int checkIfExsit(String positionName);

    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%')")
    int checkIfExsit2(String deptname);

    @Select("select * from position order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> findAllPosition(Position position);

    @Select("select * from dept order by deptname desc limit #{currentPageTotalNum},#{pageSize} ")
    List<Dept> findAllDept(Dept dept);


    @Select("select *,#{currentPage} as currentPage from position where positionName like  CONCAT('%',#{positionName},'%') order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> queryPositionByNameA(Position position);

    @Select("select *,#{currentPage} as currentPage from dept where deptname like  CONCAT('%',#{deptname},'%') order by deptname desc limit #{currentPageTotalNum},#{pageSize}")
    List<Dept> queryDeptByNameA(Dept dept);

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%') ")
    int findAllPositionByConditionCount(Position position);

    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%') ")
    int findAllDeptByConditionCount(Dept dept);

    @Delete("delete from position where id = #{id}")
    void deletePositionById(Integer id);

    @Delete("delete from dept where id = #{id}")
    void deleteDeptById(Integer id);

    @Select("select * from position where positionName like  CONCAT('%',#{positionName},'%')")
    List<Position> queryPositionByName(String positionName);

    @Update("update position set positionName =  #{positionName} where id = #{id} ")
    void saveUpdateData(Integer id,String positionName);

    @Update("update dept set deptname =  #{deptName} where id = #{id} ")
    void saveUpdateData2(Integer id,String deptName);

}
