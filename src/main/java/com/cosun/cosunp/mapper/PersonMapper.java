package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.*;
import com.cosun.cosunp.weixin.OutClockIn;
import com.sun.org.glassfish.gmbal.IncludeSubclass;
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


    @Delete({
            "<script>",
            "delete",
            "from position",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deletePositionByIdBatch(@Param("ids") List<Integer> ids);

    @Select({
            "<script>",
            "select e.id AS id, e.empno as empNo ",
            "FROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId",
            " where e.id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Employee> getEmployeeByIds(@Param("ids") List<Integer> ids);

    @Delete({
            "<script>",
            "delete",
            "from dept",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteDeptByBatch(@Param("ids") List<Integer> ids);


    @Delete({
            "<script>",
            "delete",
            "from employee",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteEmpByBatch(@Param("ids") List<Integer> ids);


    @Delete({
            "<script>",
            "delete",
            "from leavedata",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteLeaveByBatch(@Param("ids") List<Integer> ids);

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

    @Select("select count(*) from clockinsetup where outDays = #{outDays}")
    int findIfExsit(ClockInSetUp clockInSetUp);

    @Insert("insert into clockinsetup (outDays,dayClockInTimes,remark) values (#{outDays},#{dayClockInTimes},#{remark})")
    void saveClockInSetUp(ClockInSetUp clockInSetUp);

    @Update("update clockinsetup set dayClockInTimes = #{dayClockInTimes},remark = #{remark} where outDays = #{outDays}")
    void updateClockInSetUp(ClockInSetUp clockInSetUp);

    @Select("select count(*) from position where positionName like  CONCAT('%',#{positionName},'%')")
    int checkIfExsit(String positionName);

    @Select("select count(*) from dept where deptname like  CONCAT('%',#{deptname},'%')")
    int checkIfExsit2(String deptname);


    @SelectProvider(type = PseronDaoProvider.class, method = "isClockInAlready")
    int isClockInAlready(String openId, String dateStr, String titleName);

    @Select("select " +
            "weixinNo,\n" +
            "\tifnull(date_format(clockInDateAMOn, '%h:%i:%s'),'')  as clockInDateAMOnStr,\n" +
            "\tifnull(clockInAddrAMOn,'') as clockInAddrAMOn,\n" +
            "\t \tCASE\n" +
            "WHEN amOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN amOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as amOnUrl ,\n" +
            "\tifnull(date_format(clockInDatePMOn, '%h:%i:%s'),'') as clockInDatePMOnStr,\n" +
            "\tifnull(clockInAddrPMOn,'') as clockInAddrPMOn,\n" +
            "\t\tCASE\n" +
            "WHEN pmOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN pmOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as pmOnUrl,\n" +
            "\tifnull(date_format(clockInDateNMOn, '%h:%i:%s'),'') as clockInDateNMOnStr,\n" +
            "\tifnull(clockInAddNMOn,'') as clockInAddNMOn,\n" +
            "\t\tCASE\n" +
            "WHEN nmOnUrl IS NULL THEN\n" +
            "\t'无'\n" +
            "WHEN nmOnUrl IS NOT NULL THEN\n" +
            "\t'有'\n" +
            "ELSE\n" +
            "\t'other' \n" +
            "END as nmOnUrl,\n" +
            "\tclockInDate as clockInDateStr" +
            " from outclockin where weixinNo = #{openId} order by clockInDate asc")
    List<OutClockIn> findAllOutClockInByOpenId(String openId);

    @Select("select * from outclockin where id = #{id}")
    OutClockIn getOutClockInById(Integer id);

    @Select("select gongzhonghaoId from gongzhonghao")
    List<String> findAllOpenId();

    @Select("select * from clockinsetup order by outDays desc")
    List<ClockInSetUp> findAllClockInSetUp();

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllLeaveA")
    List<Leave> findAllLeaveA(String monday, String tuesday, String wednesday, String thurday, String today);

    @Select("SELECT\n" +
            "\tee.`name`,\n" +
            "  ee.empno,\n" +
            "  ee.id,\n" +
            "\tgg.gongzhonghaoId,\n" +
            "\tld.beginleave,\n" +
            "\tld.endleave,\n" +
            "\toc.clockInDate,\n" +
            "\toc.clockInDateAMOn,\n" +
            "\toc.clockInAddrAMOn,\n" +
            "\toc.amOnUrl,\n" +
            "\toc.clockInDatePMOn,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl,\n" +
            "\toc.clockInDateNMOn,\n" +
            "\toc.clockInAddrPMOn,\n" +
            "\toc.pmOnUrl\n" +
            "FROM\n" +
            "\tleavedata ld\n" +
            "LEFT JOIN employee ee ON ld.employeeid = ee.id\n" +
            "LEFT JOIN gongzhonghao gg ON gg.empNo = ee.empno\n" +
            "LEFT JOIN outclockin oc ON oc.weixinNo = gg.gongzhonghaoId\n" +
            "AND oc.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
            "AND oc.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
            " where oc.clockInDate in(#{monday},#{tuesday},#{wednesday},#{thurday},#{today})" +
            "ORDER BY\n" +
            "\tee.empno  ASC")
    List<Employee> findLeaveDataUionOutClockData(String monday, String tuesday, String wednesday, String thurday, String today);

    @Select("select * from outclockin where weixinNo = #{weixinNo} and clockInDate = #{clockInDateStr} ")
    OutClockIn getOutClockInByDateAndWeiXinId(OutClockIn outClockIn);

    @Insert("INSERT into outclockin (weixinNo,\n" +
            "\tclockInDateAMOn,\n" +
            "\tclockInAddrAMOn,\n" +
            "\tclockInDatePMOn,\n" +
            "\tclockInAddrPMOn,\n" +
            "\tclockInDateNMOn,\n" +
            "\tclockInAddNMOn,\n" +
            "\tclockInDate)\n" +
            " values(" +
            "#{weixinNo}," +
            "#{clockInDateAMOnStr}," +
            "#{clockInAddrAMOn}," +
            "#{clockInDatePMOnStr}," +
            "#{clockInAddrPMOn}," +
            "#{clockInDateNMOnStr}," +
            "#{clockInAddNMOn}," +
            "#{clockInDateStr})\n")
    void addOutClockInDateByBean(OutClockIn outClockIn);

    @Insert("INSERT into outclockin (weixinNo,\n" +
            "\tclockInDateAMOn,\n" +
            "\tclockInAddrAMOn,\n" +
            "\tamOnUrl,\n" +
            "\tclockInDatePMOn,\n" +
            "\tclockInAddrPMOn,\n" +
            "\tpmOnUrl,\n" +
            "\tclockInDateNMOn,\n" +
            "\tclockInAddNMOn,\n" +
            "\tnmOnUrl,\n" +
            "\tclockInDate)\n" +
            " values(" +
            "#{weixinNo}," +
            "#{clockInDateAMOnStr}," +
            "#{clockInAddrAMOn}," +
            "#{amOnUrl}," +
            "#{clockInDatePMOnStr}," +
            "#{clockInAddrPMOn}," +
            "#{pmOnUrl}," +
            "#{clockInDateNMOnStr}," +
            "#{clockInAddNMOn}," +
            "#{nmOnUrl}," +
            "#{clockInDateStr})\n")
    void addPhotoOutClockInDateByBean(OutClockIn outClockIn);

    @Update("update outclockin set amOnUrl = #{amOnUrl} " +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByAM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDateAMOn = #{clockInDateAMOnStr},clockInAddrAMOn = #{clockInAddrAMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByAM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDatePMOn = #{clockInDatePMOnStr},clockInAddrPMOn = #{clockInAddrPMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByPM(OutClockIn outClockIn);

    @Update("update outclockin set pmOnUrl = #{pmOnUrl}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByPM(OutClockIn outClockIn);

    @Update("update outclockin set clockInDateNMOn = #{clockInDateNMOnStr},clockInAddNMOn = #{clockInAddNMOn}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updateClockInDateByNM(OutClockIn outClockIn);

    @Update("update outclockin set nmOnUrl = #{nmOnUrl}" +
            " where weixinNo = #{weixinNo} and  clockInDate = #{clockInDateStr} ")
    void updatePhotoClockInDateByNM(OutClockIn outClockIn);

    @Select("select * from clockinsetup order by outDays asc")
    List<ClockInSetUp> findAllCLockInSetUp();

    @Select("SELECT\n" +
            "\te.empno as empNo,\n" +
            "\te.`name` as name,\n" +
            "\tdate_format(ld.beginleave, '%Y-%m-%d %h:%i:%s')  as beginleaveStr,\n" +
            "\tdate_format(ld.endleave, '%Y-%m-%d %h:%i:%s') as endleaveStr,\n" +
            "\tld.leavelong,\n" +
            "\tld.leaveDescrip\n" +
            "FROM\n" +
            "\tleavedata ld\n" +
            "LEFT JOIN employee e ON ld.employeeid = e.id\n" +
            "LEFT JOIN gongzhonghao g ON g.empNo = e.empno" +
            " where g.gongzhonghaoId = #{openId} " +
            "order by ld.id desc")
    List<Leave> findAllLeaveByWeiXinId(String openId);

    @Select("select e.`name` from gongzhonghao g LEFT JOIN employee e on e.empno = g.empNo\n" +
            "where gongzhonghaoId = #{openId} limit 1 ")
    String getNameByWeiXinId(String openId);

    @Delete("delete from clockinsetup where outDays = #{outDays}")
    void deleteClockSetInByOutDays(Double outDays);

    @Select("SELECT\n" +
            "\tid,\n" +
            "\tworkLevel,\n" +
            "\t`month`,\n" +
            "\tupdatedate as updateDateStr,\n" +
            "\tmorningon as morningOnStr,\n" +
            "\tmorningonfrom as morningOnFromStr,\n" +
            "\tmorningonend as morningOnEndStr,\n" +
            "\tmorningoff as morningOffStr,\n" +
            "\tmorningofffrom as morningOffFromStr,\n" +
            "\tmorningoffend as morningOffEndStr,\n" +
            "\tnoonon as noonOnStr,\n" +
            "\tnoononfrom as noonOnFromStr,\n" +
            "\tnoononend as noonOnEndStr,\n" +
            "\tnoonoff as noonOffStr,\n" +
            "\tnoonofffrom as noonOffFromStr,\n" +
            "\tnoonoffend as noonOffEndStr,\n" +
            "\textworkon as extworkonStr,\n" +
            "\textworkonfrom as extworkonFromStr,\n" +
            "\textworkonend as extworkonEndStr,\n" +
            "\textworkoff as extworkoffStr,\n" +
            "\tremark as remark\n" +
            "FROM\n" +
            "\tworkset\n" +
            "WHERE\n" +
            "\tid = #{id}")
    WorkSet getWorkSetById(Integer id);

    @Select("select * from dept where deptname = #{name}")
    Dept getDeptByName(String name);

    @Select("select count(*) from employee where name = #{name}")
    int checkEmployIsExsit(String name);

    @Select("select count(*) from employee where empno = #{empoyeeNo}")
    int checkEmployNoIsExsit(String empoyeeNo);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeByCondition")
    List<Employee> queryEmployeeByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutClockInByCondition")
    List<Employee> queryOutClockInByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryOutClockInByConditionCount")
    int queryOutClockInByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryGongZhongHaoByCondition")
    List<Employee> queryGongZhongHaoByCondition(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryGongZhongHaoByConditionCount")
    int queryGongZhongHaoByConditionCount(Employee employee);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeSalaryByCondition")
    List<Employee> queryEmployeeSalaryByCondition(Employee employee);

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

    @SelectProvider(type = PseronDaoProvider.class, method = "queryEmployeeSalaryByConditionCount")
    int queryEmployeeSalaryByConditionCount(Employee employee);


    @Select("select * from position order by positionName desc limit #{currentPageTotalNum},#{pageSize}")
    List<Position> findAllPosition(Position position);

    @Select("select * from clockinsetup order by outDays asc ")
    List<ClockInSetUp> findAllOutClockInSetUp();

    @Select("select count(*) from gongzhonghao where empNo = #{empNo}")
    int getGongZhongHaoByEmpNo(String empNo);


    @Insert("INSERT into gongzhonghao (empNo,gongzhonghaoId)\n" +
            " values(#{empNo},#{gongzhonghaoId})\n")
    void saveGongZhongHaoByBean(GongZhongHao gongZhongHao);


    @Delete("delete from gongzhonghao where empNo = #{empNo} ")
    void deleteGongZhongHaoByEmpNo(String empNo);

    @Update(" update gongzhonghao set gongzhonghaoId = #{gongzhonghaoId} where empNo = #{empNo} ")
    void updateGongZhongHaoByEmpNo(GongZhongHao gongZhongHao);

    @Select("select * from dept order by deptname desc limit #{currentPageTotalNum},#{pageSize} ")
    List<Dept> findAllDept(Dept dept);

    @Select("select deptName from dept order by deptname desc ")
    List<String> findAllDeptA();

    @Select("select * from position order by positionName desc")
    List<Position> findAllPositionAll();

    @Select("select positionName from position order by positionName desc")
    List<String> findAllPositionAllA();

    @Select("select * from dept order by deptname desc")
    List<Dept> findAllDeptAll();

    @Insert("insert into employee (name,sex,deptId,empno,positionId,incompdate,conExpDate,birthDay,ID_NO,\n" +
            "nativePla,homeAddr,valiPeriodOfID,nation,marriaged,contactPhone,educationLe,educationLeUrl,\n" +
            "screAgreement,healthCerti,sateListAndLeaCerti,sateListAndLeaCertiUrl,otherCerti,otherCertiUrl,positionAttrId,isQuit)" +
            "values (#{name},#{sex},#{deptId},#{empNo},#{positionId},#{incomdateStr},#{conExpDateStr},#{birthDayStr},#{ID_NO}," +
            "#{nativePla},#{homeAddr},#{valiPeriodOfIDStr},#{nation},#{marriaged},#{contactPhone},#{educationLe},#{educationLeUrl}," +
            "#{screAgreement},#{healthCerti},#{sateListAndLeaCerti},#{sateListAndLeaCertiUrl},#{otherCerti},#{otherCertiUrl},#{positionAttrId},#{isQuit})")
    void addEmployeeData(Employee employee);


    @Insert("\n" +
            "INSERT into salary (empno,compresalary,possalary,jobsalary,meritsalary)\n" +
            " values(#{empNo},#{compSalary},#{posiSalary},#{jobSalary},#{meritSalary})\n")
    void addSalaryData(String empNo, Double compSalary, Double posiSalary, Double jobSalary, Double meritSalary);

    @Update(" update salary set compresalary = #{compSalary},possalary=#{posiSalary},jobsalary=#{jobSalary},meritsalary = #{meritSalary}\n" +
            " where empno = #{empNo}\n")
    void updateSalaryData(String empNo, Double compSalary, Double posiSalary, Double jobSalary, Double meritSalary);

    @Select("SELECT\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\te.otherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo," +
            "e.isQuit," +
            "h.gongzhonghaoId " +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id " +
            " left join gongzhonghao h on e.empno = h.empNo " +
            "\t\tORDER BY\n" +
            "\t\t\te.empno asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployee(Employee employee);


//    SELECT
//    e. NAME,
//    d.deptname AS deptName,
//    n.positionName AS positionName,
//    e.empno,
//
//
//    FROM
//    employee e
//    LEFT JOIN dept d ON e.deptId = d.id
//    LEFT JOIN position n ON e.positionId = n.id
//    LEFT JOIN gongzhonghao h ON e.empno = h.empNo
//    left join outclockin o on o.weixinNo = h.gongzhonghaoId
//    left join leavedata l on l.employeeid = e.id
//    where l.type = 1
//    ORDER BY
//    e.empno ASC
//    LIMIT 0,10

    @Select("SELECT\n" +
            "\to.id,\n" +
            "\te.NAME,\n" +
            "\td.deptname as deptName,\n" +
            "\tn.positionName as positionName,\n" +
            "\tifnull(ld.beginleave,'') as beginleaveStr,\n" +
            "\tifnull(ld.endleave,'') as endleaveStr,\n" +
            "\to.clockInDate AS clockInDateStr,\n" +
            "\to.clockInDateAMOn AS clockInDateAMOnStr,\n" +
            "\to.clockInAddrAMOn,\n" +
            "\tCASE\n" +
            "WHEN o.amOnUrl IS NULL THEN\n" +
            "\t 0\n" +
            "WHEN o.amOnUrl IS NOT NULL THEN\n" +
            "\t 1 \n" +
            "END AS amOnUrlInt,\n" +
            " o.clockInDatePMOn AS clockInDatePMOnStr,\n" +
            " o.clockInAddrPMOn,\n" +
            " CASE\n" +
            "WHEN o.pmOnUrl IS NULL THEN\n" +
            "\t0\n" +
            "WHEN o.pmOnUrl IS NOT NULL THEN\n" +
            "\t1\n" +
            "END AS pmOnUrlInt,\n" +
            " o.clockInDateNMOn AS clockInDateNMOnStr,\n" +
            " o.clockInAddNMOn,\n" +
            " CASE\n" +
            "WHEN o.nmOnUrl IS NULL THEN\n" +
            "\t0\n" +
            "WHEN o.nmOnUrl IS NOT NULL THEN\n" +
            "\t1\n" +
            "END AS nmOnUrlInt\n" +
            "FROM\n" +
            "\toutclockin o\n" +
            "LEFT JOIN gongzhonghao g ON g.gongzhonghaoId = o.weixinNo\n" +
            "LEFT JOIN employee e ON e.empno = g.empNo\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id\n" +
            "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
            "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
            "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
            "ORDER BY\n" +
            "\te.empno ASC\n" +
            " limit #{currentPageTotalNum},#{pageSize} ")
    List<Employee> findAllEmployeeOutClockIn(Employee employee);


    @Select("SELECT count(*)  " +
            "FROM\n" +
            "\toutclockin o\n" +
            "LEFT JOIN gongzhonghao g ON g.gongzhonghaoId = o.weixinNo\n" +
            "LEFT JOIN employee e ON e.empno = g.empNo\n" +
            "LEFT JOIN dept d ON e.deptId = d.id\n" +
            "LEFT JOIN position n ON e.positionId = n.id\n" +
            "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
            "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
            "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d') ")
    int findAllEmployeeOutClockInCount(Employee employee);

    @Select("SELECT\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\te.otherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "  e.id AS id,\n" +
            "\td.deptname AS deptName,\n" +
            "\tn.positionName AS positionName,\n" +
            "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "\te.empno AS empNo," +
            "\tIFNULL(s.compreSalary,0) AS compreSalary," +
            "\tIFNULL(s.posSalary,0) AS posSalary," +
            "\tIFNULL(s.jobSalary,0) AS jobSalary," +
            "\tIFNULL(s.meritSalary,0) AS meritSalary," +
            "\ts.id AS salaryId, " +
            "\ts.state AS state,e.isQuit " +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id  left join salary s on e.empno = s.empno  \n" +
            "\t\tORDER BY\n" +
            "\t\t\te.empno asc limit #{currentPageTotalNum},#{pageSize}")
    List<Employee> findAllEmployeeFinance(Employee employee);

    @Select("SELECT\n" +
            "\te.id AS id,\n" +
            "\tname AS name,\n" +
            "\tsex as sex,\n" +
            "  d.deptname as deptName,\n" +
            "  n.positionName as positionName,\n" +
            "  n.positionLevel as positionLevel,\n" +
            "  date_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
            "  e.empno as empNo,worktype as workType,e.isQuit \n" +
            "\t\tFROM\n" +
            "\t\t\temployee e LEFT JOIN dept d on e.deptId = d.id \n" +
            "LEFT JOIN position n on e.positionId = n.id\n" +
            "\t\tORDER BY\n" +
            "\t\t\tNAME ASC ")
    List<Employee> findAllEmployeeAll();

    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel}")
    WorkDate getWorkDateByMonthAnPositionLevel(String month, String positionLevel);

    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel} or (month = #{month} and type = 2)")
    List<WorkDate> getWorkDateByMonthAnPositionLevelList(String month, String positionLevel);


    @Select("SELECT\n" +
            "e.empNo,e.name \n" +
            "FROM\n" +
            "\temployee e\n" +
            "JOIN position n ON e.positionId = n.id\n" +
            "AND n.positionLevel = #{positionLevel} ")
    List<SmallEmployee> findAllEmployeeByPositionLevel(String positionLevel);


    @Select("select group_concat(workDate,',') as workDate from workdate where month = #{month} and positionLevel = #{positionLevel} and type in(0,1) ")
    WorkDate getWorkDateByMonthAnPositionLevelandType(String month, String positionLevel);

    @Select("select * from workdate where month = #{month}")
    List<WorkDate> findAllWorkDateListByMonth(String month);

    @SelectProvider(type = PseronDaoProvider.class, method = "queryPositionByNameA")
    List<Position> queryPositionByNameA(Position position);

    @Select("select * from workset order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}  ")
    List<WorkSet> findAllWorkSet(WorkSet workSet);

    @Select("select count(*) from workset ")
    int findAllWorkSetCount(WorkSet workSet);

    @Select("select count(*) from employee ")
    int findAllEmployeeCount();

    @Select("select * from userinfo  where empno = #{empNo} limit 1 ")
    UserInfo getUserInfoByEmpno(String empNo);


    @Select("select *,#{currentPage} as currentPage from dept where deptname like  CONCAT('%',#{deptname},'%') order by deptname desc limit #{currentPageTotalNum},#{pageSize}")
    List<Dept> queryDeptByNameA(Dept dept);

    @SelectProvider(type = PseronDaoProvider.class, method = "findAllPositionByConditionCount")
    int findAllPositionByConditionCount(Position position);

    @Insert("insert into zhongkongbean (EnrollNumber,Date,timeStr) values(#{EnrollNumber},#{dateStr},#{timeStr})")
    void saveBeforeDayZhongKongData(ZhongKongBean zkb);

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
            "\tdate_format(e.incompdate, '%Y-%m-%d') as incomdateStr,\n" +
            "\ta.beginleave as beginLeaveStr,\n" +
            "\ta.endleave as endLeaveStr,\n" +
            "\ta.leavelong as leaveLong,\n" +
            "\ta.leaveDescrip as leaveDescrip,\n" +
            "\ta.remark as remark,\n" +
            "\ta.type as type\n" +
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


    @Select("select * from workdate where month = #{month} and positionLevel = #{positionLevel} " +
            " and type = #{type} ")
    WorkDate getWorkDateByMonth(WorkDate workDate);


    @Select("select *,GROUP_CONCAT(workdate) from workdate where month = #{month} and positionLevel = #{positionLevel} " +
            " limit 1 ")
    WorkDate getWorkDateByMonth2(WorkDate workDate);

    @Insert("insert into dept (deptname) values(#{deptName})")
    void addDeptByDeptName(String deptName);

    @Insert("insert into position (positionName) values(#{positionName})")
    void addPositionByName(String positionName);

    @Update("update employee set isQuit = 1 ")
    void updateAllEmployeeNotExist();

    @Update("update employee set isQuit = 0 where empNo = #{empNo} ")
    void updateEmployeeIsQuitExsit(String empNo);

    @Insert("insert into position (positionName,positionLevel) values(#{positionName},#{positionLevel})")
    void addPositionByNameandPositionLevel(String positionName, String positionLevel);

    @Update("TRUNCATE table dept")
    void clearDeptData();

    @Update("TRUNCATE table position")
    void clearPositionData();

    @Update("TRUNCATE table employee")
    void clearEmployeeData();

    @Select("select * from position where positionName = #{name} ")
    Position getPositionByName(String name);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevel(WorkDate workDate);

    @Select("select * from workset where month = #{month} and workLevel = #{positionLevel}")
    WorkSet getWorkSetByMonthAndPositionLevel2(String month, String positionLevel);

    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s') as beginLeaveStr ,date_format(endleave, '%Y-%m-%d %h:%i:%s') as endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type  " +
            "from leavedata where employeeid = #{employeeId} and  beginleave<= #{dataStrStart} and endleave>= #{dataEnd} limit 1 ")
    Leave getLeaveByEmIdAndMonth(Integer employeeId, String dataStrStart, String dataEnd);

    @Select("select  employeeid as employeeId,date_format(beginleave, '%Y-%m-%d %h:%i:%s" +
            "') as beginLeaveStr,date_format(endleave, '%Y-%m-%d %h:%i:%s') endLeaveStr,leavelong as leaveLong,leaveDescrip,remark,type " +
            "  from leavedata where employeeid = #{employeeId} and beginleave <= #{dataStr} and endleave >= #{dataStr} limit 1 ")
    Leave getLeaveByEmIdAndMonthA(Integer employeeId, String dataStr);

    @Insert("insert into employee (name,sex,deptId,empno,positionId,incompdate,conExpDate,birthDay,ID_NO,\n" +
            "nativePla,homeAddr,valiPeriodOfID,nation,marriaged,contactPhone,educationLe,\n" +
            "screAgreement,healthCerti,sateListAndLeaCerti,otherCerti,positionAttrId,worktype,isQuit)" +
            "values (#{name},#{sex},#{deptId},#{empNo},#{positionId},#{incompdateStr},#{conExpDateStr},#{birthDayStr},#{ID_NO}," +
            "#{nativePla},#{homeAddr},#{valiPeriodOfIDStr},#{nation},#{marriaged},#{contactPhone},#{educationLe}," +
            "#{screAgreement},#{healthCerti},#{sateListAndLeaCerti},#{otherCerti},#{positionAttrId},#{workType},#{isQuit})")
    void saveEmployeeByBean(Employee employee);

    @Insert("\n" +
            "INSERT into workdate (month,positionLevel,workDate,remark,type,empNostr)\n" +
            " values(#{month},#{positionLevel},#{workDate},#{remark},#{type},#{empNostr})\n")
    void saveWorkData(WorkDate workDate);

    @Insert("\n" +
            "INSERT into workset(workLevel,month,updatedate,morningon,morningonfrom,morningonend,morningoff," +
            "morningofffrom,morningoffend,noonon,noononfrom,noononend,noonoff,noonofffrom,noonoffend," +
            "extworkon,extworkonfrom,extworkonend,extworkoff,remark)\n" +
            " values(#{workLevel},#{month},#{updateDate},#{morningOnStr},#{morningOnFromStr},#{morningOnEndStr},#{morningOffStr}" +
            ",#{morningOffFromStr},#{morningOffEndStr},#{noonOnStr},#{noonOnFromStr},#{noonOnEndStr},#{noonOffStr},#{noonOffFromStr},#{noonOffEndStr}" +
            ",#{extworkonStr},#{extworkonFromStr},#{extworkonEndStr},#{extworkoffStr},#{remark})\n")
    void addWorkSetData(WorkSet workSet);


    @Update("update workdate set workDate =  #{workDate},remark = #{remark} " +
            ",type=#{type},empNostr = #{empNostr} where month = #{month} and positionLevel = #{positionLevel} and" +
            " type = #{type} ")
    void updateWorkData(WorkDate workDate);

    @Delete("delete from employee where id = #{id}")
    void deleteEmployeetById(Integer id);

    @Delete("delete from salary where empno = #{empNo}")
    void deleteEmployeeSalaryByEmpno(String empNo);

    @Delete("delete from salary where empno = #{empno}")
    void deleteSalaryByEmpno(String empno);

    @Delete("delete from userinfo where empno = #{empno}")
    void deleteUserInfoByEmpNo(String empno);

    @Select("SELECT\n" +
            "\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\totherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "\to.username,\n" +
            "\to.userpwd AS passowrd,\n" +
            "\to.userpwd AS passowrd22,\n" +
            "\ts.compresalary,\n" +
            "\ts.possalary,\n" +
            "\ts.jobsalary,\n" +
            "\ts.meritsalary,\n" +
            "\tn.positionname,\n" +
            "\tt.deptname,\n" +
            "\ts.remark,\n" +
            "\ts.state,e.isQuit \n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN salary s ON e.empno = s.empno\n" +
            "LEFT JOIN userinfo o ON o.empno = e.empno " +
            "LEFT JOIN position n ON n.id = e.positionId " +
            "LEFT JOIN dept t ON t.id = e.deptId " +
            "where e.id = #{id}")
    List<Employee> getEmployeeById(Integer id);

    @Select("SELECT\n" +
            "\te. NAME,\n" +
            "\te. NAME AS namea,\n" +
            "\te.sex,\n" +
            "\te.deptId,\n" +
            "\te.empno,\n" +
            "\te.positionId,\n" +
            "\te.incompdate,\n" +
            "\te.conExpDate,\n" +
            "\te.birthDay,\n" +
            "\te.ID_NO,\n" +
            "\te.nativePla,\n" +
            "\te.homeAddr,\n" +
            "\te.valiPeriodOfID,\n" +
            "\te.nation,\n" +
            "\te.marriaged,\n" +
            "\te.contactPhone,\n" +
            "\te.educationLe,\n" +
            "\te.educationLeUrl,\n" +
            "\te.screAgreement,\n" +
            "\te.healthCerti,\n" +
            "\te.sateListAndLeaCerti,\n" +
            "\te.sateListAndLeaCertiUrl,\n" +
            "\te.otherCerti,\n" +
            "\totherCertiUrl,\n" +
            "\te.positionAttrId,\n" +
            "\to.username,\n" +
            "\to.userpwd AS passowrd,\n" +
            "\to.userpwd AS passowrd22,\n" +
            "\ts.compresalary,\n" +
            "\ts.possalary,\n" +
            "\ts.jobsalary,\n" +
            "\ts.meritsalary,\n" +
            "\tn.positionname,\n" +
            "\tt.deptname,\n" +
            "\ts.remark,\n" +
            "\ts.state,e.isQuit \n" +
            "FROM\n" +
            "\temployee e\n" +
            "LEFT JOIN salary s ON e.empno = s.empno\n" +
            "LEFT JOIN userinfo o ON o.empno = e.empno " +
            "LEFT JOIN position n ON n.id = e.positionId " +
            "LEFT JOIN dept t ON t.id = e.deptId " +
            "where e.empNo = #{empNo}")
    Employee getEmployeeByEmpno(String empNo);

    @Select("SELECT e.* " +
            "FROM employee e left join dept t on e.deptId = t.id left join position n on n.id = e.positionId" +
            " where e.empno = #{empNo }")
    Employee getEmployeeByEmpNo(String empNo);

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
            "\ta.remark as remark,\n" +
            "\ta.type as type,e.isQuit " +
            "\n" +
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

    @Update("update workset set updatedate = #{updateDate},morningon = #{morningOnStr} , " +
            " morningonfrom = #{morningOnFromStr},morningonend = #{morningOnEndStr},morningoff = #{morningOffStr}," +
            " morningofffrom = #{morningOffFromStr},morningoffend = #{morningOffEndStr},noonon = #{noonOnStr}," +
            " noononfrom= #{noonOnFromStr},noononend = #{noonOnEndStr},noonoff = #{noonOffStr} ,noonofffrom = #{noonOffFromStr}," +
            " noonoffend = #{noonOffEndStr},extworkon = #{extworkonStr},extworkonFrom = #{extworkonFromStr},extworkonEnd = #{extworkonEndStr},extworkoff = #{extworkoffStr},remark = #{remark}" +
            " where id = #{id} ")
    void updateWorkSetDataById(WorkSet workSet);

    @Update("update dept set deptname =  #{deptName} where id = #{id} ")
    void saveUpdateData2(Integer id, String deptName);

    @Update("update employee set deptId = #{deptId},positionId = #{positionId},incompdate=#{incomdateStr},conExpDate=#{conExpDateStr}," +
            "birthDay=#{birthDayStr},ID_NO = #{ID_NO},\n" +
            "nativePla = #{nativePla},homeAddr=#{homeAddr},valiPeriodOfID=#{valiPeriodOfIDStr},nation=#{nation}," +
            "marriaged=#{marriaged},contactPhone=#{contactPhone},educationLe=#{educationLe},educationLeUrl=#{educationLeUrl},\n" +
            "screAgreement=#{screAgreement},healthCerti=#{healthCerti},sateListAndLeaCerti=#{sateListAndLeaCerti}," +
            "sateListAndLeaCertiUrl=#{sateListAndLeaCertiUrl},otherCerti=#{otherCerti},otherCertiUrl=#{otherCertiUrl}," +
            "positionAttrId=#{positionAttrId},isQuit = #{isQuit} " +
            "where empno = #{empNo}")
    void updateEmployeeData(Employee employee);

    @Update("update leavedata set beginleave = #{beginLeaveStr},endleave = #{endLeaveStr},leavelong =#{leaveLong}" +
            ",leaveDescrip = #{leaveDescrip},remark = #{remark},type = #{type }  where id = #{id} ")
    void updateLeaveToMysql(Leave leave);

    @Insert("insert into leavedata (employeeid,beginleave,endleave,leavelong,leaveDescrip,remark,type)\n" +
            "VALUES(#{employeeId},#{beginLeaveStr},#{endLeaveStr},#{leaveLong},#{leaveDescrip},#{remark},#{type})")
    void addLeaveData(Leave leave);

    class PseronDaoProvider {

        public String queryEmployeeByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from employee where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }
            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
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

        public String queryEmployeeSalaryByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("select count(id) from employee where 1=1");
            if (employee.getName() != "" && employee.getName() != null && employee.getName().trim().length() > 0) {
                sb.append(" and name like  CONCAT('%',#{name},'%') ");
            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }
            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
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
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and month in ('" + workSet.getMonths().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and month in (");
                    for (int i = 0; i < workSet.getMonths().size() - 1; i++) {
                        sb.append("'" + workSet.getMonths().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getMonths().get(workSet.getMonths().size() - 1) + "')");
                }
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'" + workSet.getWorkLevels().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
            }
            sb.append(" order by workLevel asc,month asc  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryWorkSetByConditionCount(WorkSet workSet) {
            StringBuilder sb = new StringBuilder("SELECT count(*) from workset where 1=1 ");
            if (workSet.getMonths() != null && workSet.getMonths().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and month in ('" + workSet.getMonths().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and month in (");
                    for (int i = 0; i < workSet.getMonths().size() - 1; i++) {
                        sb.append("'" + workSet.getMonths().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getMonths().get(workSet.getMonths().size() - 1) + "')");
                }
            }
            if (workSet.getWorkLevels() != null && workSet.getWorkLevels().size() > 0) {
                if (workSet.getWorkLevels().size() == 1) {
                    sb.append(" and workLevel in ('" + workSet.getWorkLevels().get(0) + "')");
                } else if (workSet.getWorkLevels().size() >= 2) {
                    sb.append(" and workLevel in (");
                    for (int i = 0; i < workSet.getWorkLevels().size() - 1; i++) {
                        sb.append("'" + workSet.getWorkLevels().get(i) + "'" + ",");
                    }
                    sb.append("'" + workSet.getWorkLevels().get(workSet.getWorkLevels().size() - 1) + "')");
                }
            }
            return sb.toString();
        }


        public String queryEmployeeSalaryByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo," +
                    "\tIFNULL(s.compreSalary,0) AS compreSalary," +
                    "\tIFNULL(s.posSalary,0) AS posSalary," +
                    "\tIFNULL(s.jobSalary,0) AS jobSalary," +
                    "\tIFNULL(s.meritSalary,0) AS meritSalary," +
                    "\ts.id AS salaryId, " +
                    "\ts.state AS state,e.isQuit  " +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id " +
                    "LEFT JOIN salary s ON s.empno = e.empno " +
                    "where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getState() != null) {
                sb.append(" and s.state  = #{state} ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and n.id in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0 && employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate  >= #{startIncomDateStr} and e.incompdate  <= #{endIncomDateStr}");
            } else if (employee.getStartIncomDateStr() != null && employee.getStartIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate >= #{startIncomDateStr}");
            } else if (employee.getEndIncomDateStr() != null && employee.getEndIncomDateStr().length() > 0) {
                sb.append(" and e.incompdate <= #{endIncomDateStr}");
            }
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sexStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sex ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionAttrIdStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.positionAttrId ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("birthDayStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.birthDay ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("incomdateStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.incompdate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nativePlaStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.nativePla ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("contactPhone".equals(employee.getSortByName())) {
                    sb.append(" order by e.contactPhone ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("educationLeStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.educationLe ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sateListAndLeaCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sateListAndLeaCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("compreSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.compreSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("posSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.posSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("jobSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.jobSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("meritSalary".equals(employee.getSortByName())) {
                    sb.append(" order by s.meritSalary ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("allMoney".equals(employee.getSortByName())) {
                    sb.append(" order by (compresalary+possalary+jobsalary+meritsalary) ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("state".equals(employee.getSortByName())) {
                    sb.append(" order by state ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isQuit".equals(employee.getSortByName())) {
                    sb.append(" order by isQuit ");
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

        public String queryGongZhongHaoByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsgongzhonghaoBangDing() != null
                    && employee.getIsgongzhonghaoBangDing().size() > 0
                    && employee.getIsgongzhonghaoBangDing().get(0) == 1) {
                sb.append("  join gongzhonghao h on e.empno = h.empNo ");
            } else {
                sb.append(" left join gongzhonghao h on e.empno = h.empNo ");
            }

            sb.append(" where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }
            return sb.toString();
        }

        public String queryGongZhongHaoByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo," +
                    "e.isQuit," +
                    "h.gongzhonghaoId " +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id ");
            if (employee.getIsgongzhonghaoBangDing() != null
                    && employee.getIsgongzhonghaoBangDing().size() > 0
                    && employee.getIsgongzhonghaoBangDing().get(0) == 1
            ) {
                sb.append("  join gongzhonghao h on e.empno = h.empNo ");
            } else {
                sb.append(" left join gongzhonghao h on e.empno = h.empNo ");
            }

            sb.append(" where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }
            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }
            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("gongzhonghaoId".equals(employee.getSortByName())) {
                    sb.append(" order by h.gongzhonghaoId ");
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

        public String queryOutClockInByConditionCount(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT count(*) " +
                    "FROM\n" +
                    "\toutclockin o\n" +
                    "LEFT JOIN gongzhonghao g ON g.gongzhonghaoId = o.weixinNo\n" +
                    " JOIN employee e ON e.empno = g.empNo\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id\n" +
                    "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
                    "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
                    "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
                    " where 1=1 ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            return sb.toString();
        }

        public String queryOutClockInByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\te.NAME,\n" +
                    "\to.id,\n" +
                    "\td.deptname,\n" +
                    "\tn.positionName,\n" +
                    "\tifnull(ld.beginleave,'') as beginleaveStr,\n" +
                    "\tifnull(ld.endleave,'') as endleaveStr,\n" +
                    "\to.clockInDate AS clockInDateStr,\n" +
                    "\to.clockInDateAMOn AS clockInDateAMOnStr,\n" +
                    "\to.clockInAddrAMOn,\n" +
                    "\tCASE\n" +
                    "WHEN o.amOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.amOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS amOnUrlInt,\n" +
                    " o.clockInDatePMOn AS clockInDatePMOnStr,\n" +
                    " o.clockInAddrPMOn,\n" +
                    " CASE\n" +
                    "WHEN o.pmOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.pmOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS pmOnUrlInt,\n" +
                    " o.clockInDateNMOn AS clockInDateNMOnStr,\n" +
                    " o.clockInAddNMOn,\n" +
                    " CASE\n" +
                    "WHEN o.nmOnUrl IS NULL THEN\n" +
                    "\t0\n" +
                    "WHEN o.nmOnUrl IS NOT NULL THEN\n" +
                    "\t1\n" +
                    "END AS nmOnUrlInt\n" +
                    "FROM\n" +
                    "\toutclockin o\n" +
                    "LEFT JOIN gongzhonghao g ON g.gongzhonghaoId = o.weixinNo\n" +
                    " JOIN employee e ON e.empno = g.empNo\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id\n" +
                    "LEFT JOIN leavedata ld ON ld.employeeid = e.id\n" +
                    "AND o.clockInDate >= date_format(ld.beginleave, '%Y-%m-%d')\n" +
                    "AND o.clockInDate <= date_format(ld.endleave, '%Y-%m-%d')\n" +
                    " where 1=1 ");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
            }

            if (employee.getPositionIds() != null && employee.getPositionIds().size() > 0) {
                sb.append(" and e.positionId in (" + StringUtils.strip(employee.getPositionIds().toString(), "[]") + ") ");
            }

            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("outLeaveSheet".equals(employee.getSortByName())) {
                    sb.append(" order by ld.beginleave ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateAMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDateAMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddrAMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddrAMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("amOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.amOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDatePMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDatePMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddrPMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddrPMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("pmOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.pmOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInDateNMOnStr".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInDateNMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("clockInAddNMOn".equals(employee.getSortByName())) {
                    sb.append(" order by o.clockInAddNMOn ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nmOnUrl".equals(employee.getSortByName())) {
                    sb.append(" order by o.nmOnUrl ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                }

            } else {
                sb.append(" order by e.name asc ");
            }
            sb.append("  limit #{currentPageTotalNum},#{pageSize}");
            return sb.toString();
        }

        public String queryEmployeeByCondition(Employee employee) {
            StringBuilder sb = new StringBuilder("SELECT\te. NAME,\n" +
                    "\te. NAME AS namea,\n" +
                    "\te.sex,\n" +
                    "\te.deptId,\n" +
                    "\te.empno,\n" +
                    "\te.positionId,\n" +
                    "\te.incompdate,\n" +
                    "\te.conExpDate,\n" +
                    "\te.birthDay,\n" +
                    "\te.ID_NO,\n" +
                    "\te.nativePla,\n" +
                    "\te.homeAddr,\n" +
                    "\te.valiPeriodOfID,\n" +
                    "\te.nation,\n" +
                    "\te.marriaged,\n" +
                    "\te.contactPhone,\n" +
                    "\te.educationLe,\n" +
                    "\te.educationLeUrl,\n" +
                    "\te.screAgreement,\n" +
                    "\te.healthCerti,\n" +
                    "\te.sateListAndLeaCerti,\n" +
                    "\te.sateListAndLeaCertiUrl,\n" +
                    "\te.otherCerti,\n" +
                    "\te.otherCertiUrl,\n" +
                    "\te.positionAttrId,\n" +
                    "  e.id AS id,\n" +
                    "\td.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\te.empno AS empNo,e.isQuit" +
                    "\t\t FROM \n" +
                    "\temployee e\n" +
                    "LEFT JOIN dept d ON e.deptId = d.id\n" +
                    "LEFT JOIN position n ON e.positionId = n.id where 1=1");
            if (employee.getNameIds() != null && employee.getNameIds().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(employee.getNameIds().toString(), "[]") + ") ");

            }

            if (employee.getSexIds() != null && employee.getSexIds().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(employee.getSexIds().toString(), "[]") + ") ");
            }

            if (employee.getEmpNo() != null && employee.getEmpNo() != "" && employee.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (employee.getDeptIds() != null && employee.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(employee.getDeptIds().toString(), "[]") + ") ");
            }

            if (employee.getWorkTypes() != null && employee.getWorkTypes().size() > 0) {
                sb.append(" and e.worktype in (" + StringUtils.strip(employee.getWorkTypes().toString(), "[]") + ") ");
            }

            if (employee.getIsQuits() != null && employee.getIsQuits().size() > 0) {
                sb.append(" and e.isQuit in (" + StringUtils.strip(employee.getIsQuits().toString(), "[]") + ") ");
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
            if (employee.getSortMethod() != null && !"undefined".equals(employee.getSortMethod()) && !"undefined".equals(employee.getSortByName()) && employee.getSortByName() != null) {
                if ("name".equals(employee.getSortByName())) {
                    sb.append(" order by e.name ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sexStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sex ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("empNo".equals(employee.getSortByName())) {
                    sb.append(" order by e.empno ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("deptName".equals(employee.getSortByName())) {
                    sb.append(" order by d.deptname ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionName".equals(employee.getSortByName())) {
                    sb.append(" order by n.positionName ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("positionAttrIdStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.positionAttrId ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("birthDayStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.birthDay ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("incomdateStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.incompdate ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("nativePlaStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.nativePla ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("contactPhone".equals(employee.getSortByName())) {
                    sb.append(" order by e.contactPhone ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("educationLeStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.educationLe ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("sateListAndLeaCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.sateListAndLeaCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("otherCertiStr".equals(employee.getSortByName())) {
                    sb.append(" order by e.otherCerti ");
                    if ("asc".equals(employee.getSortMethod())) {
                        sb.append(" asc ");
                    } else if ("desc".equals(employee.getSortMethod())) {
                        sb.append(" desc ");
                    }
                } else if ("isQuit".equals(employee.getSortByName())) {
                    sb.append(" order by e.isQuit ");
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


        public String queryLeaveByCondition(Leave leave) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\ta.id AS id,\n" +
                    "\te.`name` AS NAME,\n" +
                    "\te.sex AS sex,\n" +
                    "\te.empno AS empNo,\n" +
                    "\tdate_format(e.incompdate, '%Y-%m-%d') AS incomdateStr,\n" +
                    "\tt.deptname AS deptName,\n" +
                    "\tn.positionName AS positionName,\n" +
                    "\tdate_format(a.beginleave, '%Y-%m-%d %h:%i:%s')  AS beginLeaveStr,\n" +
                    "\tdate_format(a.endleave, '%Y-%m-%d %h:%i:%s')   AS endleaveStr,\n" +
                    "\ta.remark AS remark,\n" +
                    "\ta.leaveDescrip AS leaveDescrip,\n" +
                    "\ta.leavelong AS leaveLong," +
                    "\ta.type AS type,e.isQuit" +
                    "\n" +
                    "FROM\n" +
                    "\t leavedata a join  employee e on a.employeeid = e.id \n" +
                    "JOIN dept t ON e.deptId = t.id\n" +
                    "JOIN position n ON e.positionId = n.id where 1=1");

            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }
            if (leave.getSexs() != null && leave.getSexs().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(leave.getSexs().toString(), "[]") + ") ");
            }

            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getTypes() != null && leave.getTypes().size() > 0) {
                sb.append(" and a.type in (" + StringUtils.strip(leave.getTypes().toString(), "[]") + ") ");
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
            if (leave.getNames() != null && leave.getNames().size() > 0) {
                sb.append(" and e.id in (" + StringUtils.strip(leave.getNames().toString(), "[]") + ") ");
            }
            if (leave.getSexs() != null && leave.getSexs().size() > 0) {
                sb.append(" and e.sex in (" + StringUtils.strip(leave.getSexs().toString(), "[]") + ") ");
            }
            if (leave.getEmpNo() != null && leave.getEmpNo() != "" && leave.getEmpNo().trim().length() > 0) {
                sb.append(" and e.empno  like  CONCAT('%',#{empNo},'%') ");
            }

            if (leave.getDeptIds() != null && leave.getDeptIds().size() > 0) {
                sb.append(" and e.deptId in (" + StringUtils.strip(leave.getDeptIds().toString(), "[]") + ") ");
            }

            if (leave.getTypes() != null && leave.getTypes().size() > 0) {
                sb.append(" and a.type in (" + StringUtils.strip(leave.getTypes().toString(), "[]") + ") ");
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

        public String isClockInAlready(String openId, String dateStr, String titleName) {
            StringBuilder sb = new StringBuilder();
            if ("clockInDateAMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and clockInDate = #{dateStr} and clockInDateAMOn <> ''");
            } else if ("clockInDatePMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and clockInDatePMOn <> ''");
            } else if ("clockInDateNMOn".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and clockInDateNMOn <> ''");
            } else if ("amOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and   clockInDate = #{dateStr} and amOnUrl <> ''");
            } else if ("pmOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and pmOnUrl <> ''");
            } else if ("nmOnUrl".equals(titleName)) {
                sb.append("select count(*) from outclockin where weixinNo = #{openId} and  clockInDate = #{dateStr} and nmOnUrl <> ''");
            }
            return sb.toString();
        }

        public String findAllLeaveA(String monday, String tuesday, String wednesday, String thurday, String today) {
            StringBuilder sb = new StringBuilder("SELECT\n" +
                    "\tld.beginleave AS beginleave,\n" +
                    "\tld.beginleave AS beginleaveStr,\n" +
                    "\tld.endleave AS endleave,\n" +
                    "\tld.endleave AS endleaveStr,\n" +
                    "\tld.leavelong AS leavelong,\n" +
                    "\tld.leaveDescrip AS leaveDescrip,\n" +
                    "\tee.empno,\n" +
                    "\tee.`name`\n" +
                    "FROM\n" +
                    "\tleavedata ld\n" +
                    "LEFT JOIN employee ee ON ld.employeeid = ee.id\n" +
                    "WHERE ld.type=2 " +
                    " (AND date_format(ld.beginleave, '%Y-%m-%d') <= #{monday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{monday} \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{tuesday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{tuesday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{wednesday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{wednesday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{thurday} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{thurday}) \n" +
                    "OR (date_format(ld.beginleave, '%Y-%m-%d') <= #{today} \n" +
                    "AND date_format(ld.endleave, '%Y-%m-%d') >= #{today})) ");
            return sb.toString();
        }
    }

}
