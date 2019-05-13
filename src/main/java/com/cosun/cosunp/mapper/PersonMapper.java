package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.Dept;
import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.Position;
import org.apache.commons.lang.StringUtils;
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

    @Select("select count(*) from employee where name = #{name}")
    int checkEmployIsExsit(String name);

    @Select("select count(*) from employee where empno = #{empoyeeNo}")
    int checkEmployNoIsExsit(String empoyeeNo);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByCondition")
    List<Employee> queryEmployeeByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByConditionCount")
    int queryEmployeeByConditionCount(Employee employee);

    @Select("select * from position order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> findAllPosition(Position position);

    @Select("select * from dept order by deptname desc limit #{currentPageTotalNum},#{pageSize} ")
    List<Dept> findAllDept(Dept dept);

    @Select("select * from position order by positionName desc")
    List<Position> findAllPositionAll();

    @Select("select * from dept order by deptname desc")
    List<Dept> findAllDeptAll();

    @Insert("\n" +
            "INSERT into employee (name,sex,deptId,empno,positionId,incompdate)\n" +
            " values(#{name},#{sex},#{deptId},#{empNo},#{positionId},#{incompdate})\n")
    void addEmployeeData(Employee employee);

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  e.empno as empNo\n" +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id\n" +
            "\t\tORDER BY\n" +
            "\t\t\tNAME ASC limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployee(Employee employee);

    @Select("select *,#{currentPage} as currentPage from position where positionName like  CONCAT('%',#{positionName},'%') order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> queryPositionByNameA(Position position);


    @Select("select count(*) from employee ")
    int findAllEmployeeCount();


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

    @Delete("delete from employee where id = #{id}")
    void deleteEmployeetById(Integer id);

    @Select("SELECT\n" +
            "\tid AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  deptId as deptId,\n" +
            "  positionId as positionId,\n" +
            "  date_format(incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  empno as empNo\n" +
            "\t\tFROM employee where id = #{id}")
    Employee getEmployeeById(Integer id);

    @Select("select * from position where positionName like  CONCAT('%',#{positionName},'%')")
    List<Position> queryPositionByName(String positionName);

    @Update("update position set positionName =  #{positionName} where id = #{id} ")
    void saveUpdateData(Integer id, String positionName);

    @Update("update dept set deptname =  #{deptName} where id = #{id} ")
    void saveUpdateData2(Integer id, String deptName);

    @Update("update employee set deptId =  #{deptId},positionId = #{positionId} where id = #{id} ")
    void updateEmployeeData(Employee employee);

    class PseronDaoProvider {

        public String queryEmployeeByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from employee where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getSex() != null) {
                sb.append(" and sex = #{sex} ");
            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds()!=null && employee.getDeptIds().size()>0) {
                sb.append(" and deptId in ("+ StringUtils.strip(employee.getDeptIds().toString(), "[]")+") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size()>0 ) {
                sb.append(" and positionId in ("+ StringUtils.strip(employee.getPositionIds().toString(), "[]")+") ");
            }

            if (employee.getStartIncomDateStr() != null&&employee.getStartIncomDateStr().length()>0 && employee.getEndIncomDateStr() != null&&employee.getEndIncomDateStr().length()>0) {
                sb.append(" and incompdate  >= #{startIncomDateStr} and incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length()>0) {
                sb.append(" and incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length()>0) {
                sb.append(" and incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

        public String queryEmployeeByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\te.id AS id,\n" +
                    "\te.`name` AS NAME,\n" +
                    "\te.sex AS sex,\n" +
                    "\te.empno AS empNo,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\tt.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName\n" +
                    "FROM\n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept t ON e.deptId = t.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and e.name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getSex() != null) {
                sb.append(" and e.sex = #{sex} ");
            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empno},'%') ");
            }

            if (employee.getDeptIds()!=null && employee.getDeptIds().size()>0) {
                sb.append(" and e.deptId in ("+ StringUtils.strip(employee.getDeptIds().toString(), "[]")+") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size()>0 ) {
                sb.append(" and e.positionId in ("+ StringUtils.strip(employee.getPositionIds().toString(), "[]")+") ");
            }

            if (employee.getStartIncomDateStr() != null&&employee.getStartIncomDateStr().length()>0 && employee.getEndIncomDateStr() != null&&employee.getEndIncomDateStr().length()>0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length()>0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length()>0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            sb.append(" order by e.name desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }
    }

}
