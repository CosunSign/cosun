package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Salary;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.SessionScope;
import sun.awt.EmbeddedFrame;

import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/6/21 上午 9:52
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Repository
@Mapper
public interface FinanceMapper {

    @Delete("delete from salary ")
    void deleteAllSalaryData();

    @Insert("insert into salary (empno,compresalary,possalary,jobsalary,meritsalary) " +
            "values (#{empNo},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary})")
    void saveSalary(Salary salary);

    @Insert("insert into salary (empno,compresalary,possalary,jobsalary,meritsalary,remark) " +
            "values (#{empNo},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary},#{remark})")
    void addSalaryByBean(Employee employee);

    @Update("update salary set compreSalary = #{compreSalary},posSalary = #{posSalary} , " +
            " jobSalary = #{jobSalary},meritSalary = #{meritSalary},remark = #{remark}" +
            " where empno = #{empNo} ")
    void updateSalaryByBean(Employee employee);

    @Select("select * from salary where empno = #{empno}")
    Salary getSalaryByEmpno(String empno);

    @Delete({
            "<script>",
            "delete",
            "from salary",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void  deleteEmpSalaryByBatch(@Param("ids") List<Integer> ids);


}
