package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import org.apache.commons.lang.StringUtils;
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

    @Delete("delete from emphours where yearMonth = #{yearMonth} ")
    void deleteAllEmpHoursByYearMonthData(String yearMonth);

    @Delete("delete from financeimportdata where id = #{id} ")
    void deleteFinanceImportDataById(Integer id);

    @Delete("delete from financeimportdata where yearMonth = #{yearMonth} ")
    void deleteAllsaveAllFinanceImportDataByYearMonthData(String yearMonth);

    @Delete("delete from emphours where empNo = #{empNo} ")
    void deleteEmployeeHoursByEmpno(String empNo);

    @Insert("insert into financeimportdata (empNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,remark) " +
            "values (" +
            "#{empNo},\n" +
            "\t#{name},\n" +
            "\t#{deptName},\n" +
            "\t#{legalHolidWorkHours},\n" +
            "\t#{sellActual},\n" +
            "\t#{sellThreshold},\n" +
            "\t#{sellLevelSalary},\n" +
            "\t#{houseSubsidy},\n" +
            "\t#{hotTempOrOtherAllow},\n" +
            "\t#{workYearsSalary},\n" +
            "\t#{sellCommi},\n" +
            "\t#{yearMonth},#{remark}" +
            ")")
    void saveFinanceImportData(FinanceImportData financeImportData);

    @Update("update financeimportdata set legalHolidWorkHours = #{legalHolidWorkHours},\n" +
            "\t sellActual = #{sellActual},\n" +
            "\t sellThreshold = #{sellThreshold},\n" +
            "\t sellLevelSalary = #{sellLevelSalary},\n" +
            "\t houseSubsidy = #{houseSubsidy},\n" +
            "\t hotTempOrOtherAllow = #{hotTempOrOtherAllow},\n" +
            "\t workYearsSalary = #{workYearsSalary},\n" +
            "\t sellCommi = #{sellCommi},remark = #{remark} \n" +
            "\t where id = #{id}")
    void updateFinanceImportDataByBean(FinanceImportData financeImportData);

    @Insert("insert into salary (empno,compresalary,possalary,jobsalary,meritsalary) " +
            "values (#{empNo},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary})")
    void saveSalary(Salary salary);


    @Insert("insert into emphours (\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,yearMonth) " +
            "values (#{name},#{empNo},#{deptName},#{zhengbanHours},#{usualExtHours}," +
            "#{workendHours},#{chinaPaidLeave},#{otherPaidLeave},#{leaveOfAbsense},#{sickLeave}," +
            "#{otherAllo},#{fullWorkReword},#{foodExpense},#{roomOrWaterEleExpense},#{oldAgeINsuran}," +
            "#{medicalInsuran},#{unEmployeeInsur},#{accumulaFund},#{errorInWork},#{meritScore},#{yearMonthStr})")
    void saveEmpHours(EmpHours eh);

    @Insert("insert into emphours (\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,yearMonth,remark) " +
            "values (#{name},#{empNo},#{deptName},#{zhengbanHours},#{usualExtHours}," +
            "#{workendHours},#{chinaPaidLeave},#{otherPaidLeave},#{leaveOfAbsense},#{sickLeave}," +
            "#{otherAllo},#{fullWorkReword},#{foodExpense},#{roomOrWaterEleExpense},#{oldAgeINsuran}," +
            "#{medicalInsuran},#{unEmployeeInsur},#{accumulaFund},#{errorInWork},#{meritScore},#{yearMonth},#{remark})")
    void addEmpHoursByBean(EmpHours empHours);


    @Update("update emphours set zhengbanHours = #{zhengbanHours}," +
            " usualExtHours = #{usualExtHours}, " +
            " workendHours = #{workendHours}, " +
            " chinaPaidLeave = #{chinaPaidLeave}, " +
            " otherPaidLeave = #{otherPaidLeave}, " +
            " leaveOfAbsense = #{leaveOfAbsense}, " +
            " sickLeave = #{sickLeave}, " +
            " otherAllo = #{otherAllo}, " +
            " fullWorkReword = #{fullWorkReword}, " +
            " foodExpense = #{foodExpense}, " +
            " roomOrWaterEleExpense = #{roomOrWaterEleExpense}, " +
            " oldAgeINsuran = #{oldAgeINsuran}, " +
            " medicalInsuran = #{medicalInsuran}, " +
            " unEmployeeInsur = #{unEmployeeInsur}, " +
            " accumulaFund = #{accumulaFund}, " +
            " errorInWork = #{errorInWork}, " +
            " meritScore = #{meritScore},remark = #{remark} " +
            " where empNo = #{empNo} and  yearMonth = #{yearMonth}")
    void updateEmpHoursByBean(EmpHours empHours);

    @Insert("insert into financesetupdata " +
            " (norAttendHoursSample,norAttendSalarySample,norExtraMutiple,weekEndWorkMutiple,legalWorkMutiple,meritScoreSample)" +
            " values (#{norAttendHoursSample}," +
            "#{norAttendSalarySample}," +
            "#{norExtraMutiple}," +
            " #{weekEndWorkMutiple}," +
            "#{legalWorkMutiple}," +
            " #{meritScoreSample})")
    void saveFinanceSetUp(FinanceSetUpData financeSetUpData);

    @Update("update  financesetupdata " +
            " set norAttendHoursSample = #{norAttendHoursSample}," +
            "norAttendSalarySample = #{norAttendSalarySample}," +
            "norExtraMutiple = #{norExtraMutiple}," +
            "weekEndWorkMutiple =  #{weekEndWorkMutiple}," +
            "legalWorkMutiple = #{legalWorkMutiple}," +
            " meritScoreSample = #{meritScoreSample}")
    void updateFinanceSetUp(FinanceSetUpData financeSetUpData);

    @Select("select count(*) from emphours where empNo = #{empNo} and yearMonth=#{yearMonth} ")
    int checkEmpNoandYearMonthIsExsit(EmpHours empHours);

    @Select("select count(*) from financeimportdata where empNo = #{empNo} and yearMonth=#{yearMonth} ")
    int checkFinanceImportNoandYearMonthIsExsit(EmpHours empHours);

    @Select("select count(*) from financesetupdata")
    int findFinanceSetUpDataCount();

    @Select("select * from financesetupdata")
    FinanceSetUpData findFinanceSetUpData();

    @Insert("insert into salary (empno,compresalary,possalary,jobsalary,meritsalary,remark,state) " +
            "values (#{empNo},#{compreSalary},#{posSalary},#{jobSalary},#{meritSalary},#{remark},#{state})")
    void addSalaryByBean(Employee employee);

    @Update("update salary set compreSalary = #{compreSalary},posSalary = #{posSalary} , " +
            " jobSalary = #{jobSalary},meritSalary = #{meritSalary},remark = #{remark},state = #{state} " +
            " where empno = #{empNo} ")
    void updateSalaryByBean(Employee employee);

    @Select("select * from salary where empno = #{empno}")
    Salary getSalaryByEmpno(String empno);


    @Select("SELECT count(*) " +
            "\t\tFROM\n" +
            "\t\t\tfinanceimportdata ")
    int findAllFinanceImportDataCount();

    @Select("SELECT id," +
            "\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,\n" +
            "\tyearMonth " +
            "\t\tFROM\n" +
            "\t\t\temphours " +
            "\t\tORDER BY\n" +
            "\t\t\t empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<EmpHours> findAllEmpHours(Employee employee);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\tfinanceimportdata " +
            "\tORDER BY\n" +
            "\t empNo asc limit #{currentPageTotalNum},#{pageSize}")
    List<FinanceImportData> findAllFinanceImportData(Employee employee);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tempNo,\n" +
            "\t`name`,\n" +
            "\tdeptName,\n" +
            "\tlegalHolidWorkHours,\n" +
            "\tsellActual,\n" +
            "\tsellThreshold,\n" +
            "\tsellLevelSalary,\n" +
            "\thouseSubsidy,\n" +
            "\thotTempOrOtherAllow,\n" +
            "\tworkYearsSalary,\n" +
            "\tsellCommi,\n" +
            "\tyearMonth,\n" +
            "\tremark\n" +
            "FROM\n" +
            "\tfinanceimportdata " +
            "\t where id = #{id}")
    FinanceImportData getFinanceImportDataById(Integer id);

    @Select("SELECT id," +
            "\tNAME,\n" +
            "\tempNo,\n" +
            "\tdeptName,\n" +
            "\tzhengbanHours,\n" +
            "\tusualExtHours,\n" +
            "\tworkendHours,\n" +
            "\tchinaPaidLeave,\n" +
            "\totherPaidLeave,\n" +
            "\tleaveOfAbsense,\n" +
            "\tsickLeave,\n" +
            "\totherAllo,\n" +
            "\tfullWorkReword,\n" +
            "\tfoodExpense,\n" +
            "\troomOrWaterEleExpense,\n" +
            "\toldAgeINsuran,\n" +
            "\tmedicalInsuran,\n" +
            "\tunEmployeeInsur,\n" +
            "\taccumulaFund,\n" +
            "\terrorInWork,\n" +
            "\tmeritScore,\n" +
            "\tyearMonth " +
            "\t\tFROM\n" +
            "\t\t\temphours where empNo = #{empNo} ")
    EmpHours getEmpHoursByEmpNo(String empNo);

    @Select("SELECT count(*) " +
            "\t\tFROM\n" +
            "\t\t\temphours ")
    int findAllEmpHoursHours();

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
    void deleteEmpSalaryByBatch(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from financeimportdata",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteFinanceImportDataByBatch(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from emphours",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteEmpHoursByBatch(@Param("ids") List<Integer> ids);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryFinanceImportDataByCondition")
    List<FinanceImportData> queryFinanceImportDataByCondition(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryFinanceImportDataByConditionCount")
    int queryFinanceImportDataByConditionCount(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryEmployeeHoursByCondition")
    List<EmpHours> queryEmployeeHoursByCondition(Employee employee);

    @SelectProvider(type = FinanceMapper.FinanceDaoProvider.class, method = "queryEmployeeHoursByConditionCount")
    int queryEmployeeHoursByConditionCount(Employee employee);

    class FinanceDaoProvider {

        public String queryEmployeeHoursByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM \n" +
                    "\temphours e\n" +
                    "LEFT JOIN employee ee ON ee.empno = e.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }

            return sb.toString();
        }

        public String queryFinanceImportDataByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tf.id,\n" +
                    "\tf.empNo,\n" +
                    "\tf.`name`,\n" +
                    "\tf.deptName,\n" +
                    "\tf.legalHolidWorkHours,\n" +
                    "\tf.sellActual,\n" +
                    "\tf.sellThreshold,\n" +
                    "\tf.sellLevelSalary,\n" +
                    "\tf.houseSubsidy,\n" +
                    "\tf.hotTempOrOtherAllow,\n" +
                    "\tf.workYearsSalary,\n" +
                    "\tf.sellCommi,\n" +
                    "\tf.yearMonth,\n" +
                    "\tf.remark\n" +
                    "FROM\n" +
                    "\tfinanceimportdata f " +
                    "LEFT JOIN employee ee ON ee.empno = f.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and f.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by f.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by f.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by f.empNo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by f.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("legalHolidWorkHours".equals(employee.getSortByName())) {
                    sb.append(" order by f.legalHolidWorkHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellActual".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellActual ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellThreshold".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellThreshold ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellLevelSalary".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellLevelSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("houseSubsidy".equals(employee.getSortByName())) {
                    sb.append(" order by f.houseSubsidy ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("hotTempOrOtherAllow".equals(employee.getSortByName())) {
                    sb.append(" order by f.hotTempOrOtherAllow ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("workYearsSalary".equals(employee.getSortByName())) {
                    sb.append(" order by f.workYearsSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sellCommi".equals(employee.getSortByName())) {
                    sb.append(" order by f.sellCommi ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by f.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(employee.getSortByName())) {
                    sb.append(" order by f.remark ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by f.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryFinanceImportDataByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) \n" +
                    "FROM\n" +
                    "\tfinanceimportdata f " +
                    "LEFT JOIN employee ee ON ee.empno = f.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and f.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }

            return sb.toString();
        }

        public String queryEmployeeHoursByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT e.id," +
                    "\te.NAME,\n" +
                    "\te.empNo,\n" +
                    "\te.deptName,\n" +
                    "\te.zhengbanHours,\n" +
                    "\te.usualExtHours,\n" +
                    "\te.workendHours,\n" +
                    "\te.chinaPaidLeave,\n" +
                    "\te.otherPaidLeave,\n" +
                    "\te.leaveOfAbsense,\n" +
                    "\te.sickLeave,\n" +
                    "\te.otherAllo,\n" +
                    "\te.fullWorkReword,\n" +
                    "\te.foodExpense,\n" +
                    "\te.roomOrWaterEleExpense,\n" +
                    "\te.oldAgeINsuran,\n" +
                    "\te.medicalInsuran,\n" +
                    "\te.unEmployeeInsur,\n" +
                    "\te.accumulaFund,\n" +
                    "\te.errorInWork,\n" +
                    "\te.meritScore,\n" +
                    "\te.yearMonth " +
                    "\t\t FROM \n" +
                    "\temphours e\n" +
                    "LEFT JOIN employee ee ON ee.empno = e.empno\n" +
                    "LEFT JOIN dept d ON ee.deptId = d.id\n" +
                    "LEFT JOIN position n ON ee.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and ee.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and ee.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and ee.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and ee.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and ee.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate  >= #{startIncomDateStr} and ee.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and ee.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("yearMonth".equals(employee.getSortByName())) {
                    sb.append(" order by e.yearMonth ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empNo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by e.deptName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("zhengbanHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.zhengbanHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("usualExtHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.usualExtHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("workendHours".equals(employee.getSortByName())) {
                    sb.append(" order by e.workendHours ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("chinaPaidLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.chinaPaidLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherPaidLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherPaidLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("leaveOfAbsense".equals(employee.getSortByName())) {
                    sb.append(" order by e.leaveOfAbsense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sickLeave".equals(employee.getSortByName())) {
                    sb.append(" order by e.sickLeave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherAllo".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherAllo ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("fullWorkReword".equals(employee.getSortByName())) {
                    sb.append(" order by e.fullWorkReword ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("foodExpense".equals(employee.getSortByName())) {
                    sb.append(" order by e.foodExpense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("roomOrWaterEleExpense".equals(employee.getSortByName())) {
                    sb.append(" order by e.roomOrWaterEleExpense ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("oldAgeINsuran".equals(employee.getSortByName())) {
                    sb.append(" order by e.oldAgeINsuran ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("medicalInsuran".equals(employee.getSortByName())) {
                    sb.append(" order by e.medicalInsuran ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("unEmployeeInsur".equals(employee.getSortByName())) {
                    sb.append(" order by e.unEmployeeInsur ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("accumulaFund".equals(employee.getSortByName())) {
                    sb.append(" order by e.accumulaFund ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("errorInWork".equals(employee.getSortByName())) {
                    sb.append(" order by e.errorInWork ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("meritScore".equals(employee.getSortByName())) {
                    sb.append(" order by e.meritScore ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("remark".equals(employee.getSortByName())) {
                    sb.append(" order by e.remark ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by e.empno asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }
    }


}
