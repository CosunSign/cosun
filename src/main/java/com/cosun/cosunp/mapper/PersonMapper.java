package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import javafx.geometry.Pos;
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
    int findSaveOrNot(Position position);

    @Select("select count(*) from dept where deptName like  CONCAT('%',#{deptName},'%') ")
    int findSaveOrNot2(String deptName);

    @Insert("insert into position (positionName,positionLevel) values (#{positionName},#{positionLevel})")
    void savePosition(Position position);

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

    @Select("select * from workset where id = #{id}")
    WorkSet getWorkSetById(Integer id);

    @Select("select count(*) from employee where name = #{name}")
    int checkEmployIsExsit(String name);

    @Select("select count(*) from employee where empno = #{empoyeeNo}")
    int checkEmployNoIsExsit(String empoyeeNo);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByCondition")
    List<Employee> queryEmployeeByCondition(Employee employee);


    @SelectProvider(type = PseronDaoProvider.class, method = "queryWorkSetByCondition")
    List<WorkSet> queryWorkSetByCondition(WorkSet workSet);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryWorkSetByConditionCount")
    int queryWorkSetByConditionCount(WorkSet workSet);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLeaveByCondition")
    List<Leave> queryLeaveByCondition(Leave leave);

    @SelectProvider(type = PseronDaoProvider.class, method = "checkBeginLeaveRight")
    int checkBeginLeaveRight(Leave leave);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryLeaveByConditionCount")
    int queryLeaveByConditionCount(Leave leave);

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

    @SelectProvider(type = PseronDaoProvider.class, method = "queryPositionByNameA")
    List<Position> queryPositionByNameA(Position position);

    @Select("select * from workset order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}  ")
    List<WorkSet> findAllWorkSet(WorkSet workSet);

    @Select("select count(*) from workset ")
    int findAllWorkSetCount(WorkSet workSet);

    @Select("select count(*) from employee ")
    int findAllEmployeeCount();


    @Select("select *,#{currentPage} as currentPage from dept where deptname like  CONCAT('%',#{deptname},'%') order by deptname desc limit #{currentPageTotalNum},#{pageSize}")
    List<Dept> queryDeptByNameA(Dept dept);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllPositionByConditionCount")
    int findAllPositionByConditionCount(Position position);

    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%') ")
    int findAllDeptByConditionCount(Dept dept);

    @Delete("delete from position where id = #{id}")
    void deletePositionById(Integer id);

    @Delete("delete from leavedata where id = #{id}")
    void deleteLeaveById(Integer id);

    @Delete("delete from workset where id = #{id}")
    void deleteWorkSetById(WorkSet workSet);

    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\te.`name` as name,\n" +
            "\te.empno as empNo,\n" +
            "\te.sex as sex,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d %h:%m:%s') as incomdateStr,\n" +
            "\tdate_format(a.beginleave, '%Y-%m-%d %h:%m:%s') as beginLeaveStr,\n" +
            "\tdate_format(a.endleave, '%Y-%m-%d %h:%m:%s') as endLeaveStr,\n" +
            "\ta.leavelong as leaveLong,\n" +
            "\ta.leaveDescrip as leaveDescrip,\n" +
            "\ta.remark as remark\n" +
            "FROM\n" +
            "\tleavedata a\n" +
            "LEFT JOIN employee e ON a.employeeid = e.id\n" +
            "LEFT JOIN position n ON n.id = e.positionId\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            " where a.id = #{id} " +
            "ORDER BY\n" +
            "\te.name DESC")
    Leave getLeaveById(Integer id);

    @Select("select  GROUP_CONCAT(positionName) from position where positionLevel = #{positionLevel} ")
    String getPositionNamesByPositionLevel(String positionLevel);

    @Delete("delete from dept where id = #{id}")
    void deleteDeptById(Integer id);


    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel}")
    WorkDate getWorkDateByMonth(WorkDate workDate);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate);

    @Insert("\n" +
            "INSERT into workdate (month,positionLevel,workDate,remark)\n" +
            " values(#{month},#{positionLevel},#{workDate},#{remark})\n")
    void saveWorkData(WorkDate workDate);

    @Insert("\n" +
            "INSERT into workset(workLevel,month,updatedate,morningon,morningonfrom,morningonend,morningoff," +
            "morningofffrom,morningoffend,noonon,noononfrom,noononend,noonoff,noonofffrom,noonoffend," +
            "extworkon,extworkoff,remark)\n" +
            " values(#{workLevel},#{month},#{updateDate},#{morningOnStr},#{morningOnFromStr},#{morningOnEndStr},#{morningOffStr}" +
            ",#{morningOffFromStr},#{morningOffEndStr},#{noonOnStr},#{noonOnFromStr},#{noonOnEndStr},#{noonOffStr},#{noonOffFromStr},#{noonOffEndStr}" +
            ",#{extworkonStr},#{extworkoffStr},#{remark})\n")
    void addWorkSetData(WorkSet workSet);


    @Update("update workdate set workDate =  #{workDate},remark = #{remark} where month = #{month} and positionLevel = #{positionLevel} ")
    void updateWorkData(WorkDate workDate);

    @Delete("delete from employee where id = #{id}")
    void deleteEmployeetById(Integer id);

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\te.name AS name,\n" +
            "\te.sex as sex,\n" +
            "  e.deptId as deptId,\n" +
            "  e.positionId as positionId,\n" +
            "  t.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  e.empno as empNo\n" +
            "\t\tFROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId" +
            " where e.id = #{id}")
    List<Employee> getEmployeeById(Integer id);

    @Select("select * from position where positionName like  CONCAT('%',#{positionName},'%')")
    List<Position> queryPositionByName(String positionName);

    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\te.`name` as name,\n" +
            "\te.empno as empNo,\n" +
            "\tt.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\ta.beginleave as beginLeaveStr,\n" +
            "\ta.endleave as endLeaveStr,\n" +
            "\ta.leavelong as leaveLong,\n" +
            "\ta.leaveDescrip as leaveDescrip,\n" +
            "\ta.remark as remark\n" +
            "FROM\n" +
            "\tleavedata a\n" +
            "LEFT JOIN employee e ON a.employeeid = e.id\n" +
            "LEFT JOIN position n ON n.id = e.positionId\n" +
            "LEFT JOIN dept t ON t.id = e.deptId\n" +
            "ORDER BY\n" +
            "\te.name DESC limit #{currentPageTotalNum},#{pageSize} ")
    List<Leave> findAllLeave(Leave leave);

    @Select("select count(id) from leavedata ")
    int findAllLeaveCount();

    @Select("select * from employee ")
    List<Employee> findAllEmployees();

    @Update("update position set positionName =  #{positionName},positionLevel=#{positionLevel} where id = #{id} ")
    void saveUpdateData(Integer id, String positionName, String positionLevel);

    @Update("update workset set updatedate = #{updateDate},morningon = #{morningOn} , " +
            " morningonfrom = #{morningOnFrom},morningonend = #{morningOnEnd},morningoff = #{morningOff}," +
            " morningofffrom = #{morningOffFrom},morningoffend = #{morningOffEnd},noonon = #{noonOn}," +
            " noononfrom= #{noonOnFrom},noononend = #{noonOnEnd},noonoff = #{noonOff} ,noonofffrom = #{noonOffFrom}," +
            " noonoffend = #{noonOffEnd},extworkon = #{extworkon},extworkoff = #{extworkoff},remark = #{remark}" +
            " where id = #{id} ")
    void updateWorkSetDataById(WorkSet workSet);

    @Update("update dept set deptname =  #{deptName} where id = #{id} ")
    void saveUpdateData2(Integer id, String deptName);

    @Update("update employee set deptId =  #{deptId},positionId = #{positionId} where id = #{id} ")
    void updateEmployeeData(Employee employee);

    @Update("update leavedata set beginleave = #{beginLeaveStr},endleave = #{endLeaveStr},leavelong =#{leaveLong}" +
            ",leaveDescrip = #{leaveDescrip},remark = #{remark}  where id = #{id} ")
    void updateLeaveToMysql(Leave leave);

    @Insert("insert into leavedata (employeeid,beginleave,endleave,leavelong,leaveDescrip,remark)\n" +
            "VALUES(#{employeeId},#{beginLeaveStr},#{endLeaveStr},#{leaveLong},#{leaveDescrip},#{remark})")
    void addLeaveData(Leave leave);

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

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate  >= #{startIncomDateStr} and incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and incompdate <= #{endIncomDateStr}");
            }
            return sb.toString();
        }

        public String queryWorkSetByCondition(WorkSet workSet) {
            StringBuilder sb = new StringBuilder("SELECT * from workset where 1=1 ");
            if (workSet.getMonths() != null && workSet.getMonths().size() > 0) {
                sb.append(" and month in (" + StringUtils.strip(workSet.getMonths().toString(), "[]") + ")");
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'"+workSet.getWorkLevels().get(i)+"'" + ",");
                    }
                    sb.append("'"+ workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
            }
            sb.append(" order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryWorkSetByConditionCount(WorkSet workSet) {
            StringBuilder sb = new StringBuilder("SELECT count(*) from workset where 1=1 ");
            if (workSet.getMonths() != null && workSet.getMonths().size() > 0) {
                sb.append(" and month in (" + StringUtils.strip(workSet.getMonths().toString(), "[]") + ")");
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'"+workSet.getWorkLevels().get(i) +"'"+ ",");
                    }
                    sb.append("'"+workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
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

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            sb.append(" order by e.name desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryLeaveByCondition(Leave leave) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\te.id AS id,\n" +
                    "\te.`name` AS NAME,\n" +
                    "\te.sex AS sex,\n" +
                    "\te.empno AS empNo,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\tt.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(a.beginleave, '%Y-%m-%d %h:%m:%s')  AS beginLeaveStr,\n" +
                    "\tdate_format(a.endleave, '%Y-%m-%d %h:%m:%s')   AS endleaveStr,\n" +
                    "\ta.remark AS remark,\n" +
                    "\ta.leaveDescrip AS leaveDescrip,\n" +
                    "\ta.leavelong AS leaveLong" +
                    "\n" +
                    "FROM\n" +
                    "\t leavedata a join  employee e on a.employeeid = e.id \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");
            if (leave.getName() != "" && leave.getName() != null && leave.getName().trim().length() > 0) {
                sb.append(" and e.name like  CONCAT('%',#{name},'%') ");
            }
            if (leave.getSex() != null) {
                sb.append(" and e.sex = #{sex} ");
            }
            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empno},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.beginleave  >= #{beginLeaveStr} and a.endleave  <= #{endLeaveStr}");
            } else if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0) {
                sb.append(" and a.beginleave >= #{beginLeaveStr}");
            } else if (leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.endleave <= #{endLeaveStr}");
            }
            sb.append(" order by e.name desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }


        public String queryLeaveByConditionCount(Leave leave) {
            StringBuilder sb = new StringBuilder("SELECT count(*) as a " +
                    " FROM\n" +
                    "\t leavedata a join  employee e on a.employeeid = e.id \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");
            if (leave.getName() != "" && leave.getName() != null && leave.getName().trim().length() > 0) {
                sb.append(" and e.name like  CONCAT('%',#{name},'%') ");
            }
            if (leave.getSex() != null) {
                sb.append(" and e.sex = #{sex} ");
            }
            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empno},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getPositionIds() != null && leave.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(leave.getPositionIds().toString(), "[]") + ") ");
            }

            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.beginleave  >= #{beginLeaveStr} and a.endleave  <= #{endLeaveStr}");
            } else if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0) {
                sb.append(" and a.beginleave >= #{beginLeaveStr}");
            } else if (leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and a.endleave <= #{endLeaveStr}");
            }
            return sb.toString();
        }

        public String checkBeginLeaveRight(Leave leave) {
            StringBuilder sb = new StringBuilder("select count(*) from leavedata a where a.employeeid = #{employeeId}");
            if (leave.getBeginLeaveStr() != null && leave.getBeginLeaveStr().length() > 0 && leave.getEndLeaveStr() != null && leave.getEndLeaveStr().length() > 0) {
                sb.append(" and (a.beginleave  <= #{beginLeaveStr} and a.endleave  >= #{beginLeaveStr}");
                sb.append(" or a.beginleave  <= #{endLeaveStr} and a.endleave  >= #{endLeaveStr})");
            }
            return sb.toString();
        }

        public String queryPositionByNameA(Position position) {
            StringBuilder sb = new StringBuilder("select *,#{currentPage} as currentPage from position where 1=1 ");
            if (position.getPositionName() != null && position.getPositionName().trim().length() > 0) {
                sb.append(" and positionName like  CONCAT('%',#{positionName},'%') ");
            }
            if (position.getPositionLevel() != null && position.getPositionLevel().trim().length() > 0) {
                sb.append(" and positionLevel = #{positionLevel}");
            }
            sb.append(" order by positionName desc limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String findAllPositionByConditionCount(Position position) {
            StringBuilder sb = new StringBuilder("select count(*) from position where 1=1 ");
            if (position.getPositionName() != null && position.getPositionName().trim().length() > 0) {
                sb.append(" and positionName like  CONCAT('%',#{positionName},'%') ");
            }
            if (position.getPositionLevel() != null && position.getPositionLevel().trim().length() > 0) {
                sb.append(" and positionLevel = #{positionLevel}");
            }
            return sb.toString();
        }
    }

}