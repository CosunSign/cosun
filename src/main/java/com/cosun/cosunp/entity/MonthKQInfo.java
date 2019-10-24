package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Date: 2019/10/16 0016 下午 2:02
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MonthKQInfo implements Serializable {

    private static final long serialVersionUID = 7115049136692832116L;

    private Integer id;
    private String empNo;
    private String name;
    private String yearMonth;
    private String deptName;

    private Double zhengbanHours;//正班出勤工时
    private Double usualExtHours;//平时加班
    private Double workendHours;//周末加班
    private Double chinaPaidLeave;//国家有薪假
    private Double otherPaidLeave;//其它有薪假
    private Double leaveOfAbsense;//事假
    private Double sickLeave;//病假
    private Double otherAllo;//其它补贴
    private Double fullWorkReword;//全勤奖
    private Double highTempAllow;//高温补贴
    private String remark;

    private String day01;
    private String day01Remark;
    private Integer day01AM;
    private Integer day01PM;
    private Double day01ExHours;


    private String day02;
    private String day02Remark;
    private Integer day02AM;
    private Integer day02PM;
    private Double day02ExHours;

    private String day03;
    private String day03Remark;
    private Integer day03AM;
    private Integer day03PM;
    private Double day03ExHours;


    private String day04;
    private String day04Remark;
    private Integer day04AM;
    private Integer day04PM;
    private Double day04ExHours;


    private String day05;
    private String day05Remark;
    private Integer day05AM;
    private Integer day05PM;
    private Double day05ExHours;


    private String day06;
    private String day06Remark;
    private Integer day06AM;
    private Integer day06PM;
    private Double day06ExHours;


    private String day07;
    private String day07Remark;
    private Integer day07AM;
    private Integer day07PM;
    private Double day07ExHours;


    private String day08;
    private String day08Remark;
    private Integer day08AM;
    private Integer day08PM;
    private Double day08ExHours;


    private String day09;
    private String day09Remark;
    private Integer day09AM;
    private Integer day09PM;
    private Double day09ExHours;


    private String day10;
    private String day10Remark;
    private Integer day10AM;
    private Integer day10PM;
    private Double day10ExHours;


    private String day11;
    private String day11Remark;
    private Integer day11AM;
    private Integer day11PM;
    private Double day11ExHours;

    private String day12;
    private String day12Remark;
    private Integer day12AM;
    private Integer day12PM;
    private Double day12ExHours;


    private String day13;
    private String day13Remark;
    private Integer day13AM;
    private Integer day13PM;
    private Double day13ExHours;


    private String day14;
    private String day14Remark;
    private Integer day14AM;
    private Integer day14PM;
    private Double day14ExHours;


    private String day15;
    private String day15Remark;
    private Integer day15AM;
    private Integer day15PM;
    private Double day15ExHours;

    private String day16;
    private String day16Remark;
    private Integer day16AM;
    private Integer day16PM;
    private Double day16ExHours;

    private String day17;
    private String day17Remark;
    private Integer day17AM;
    private Integer day17PM;
    private Double day17ExHours;

    private String day18;
    private String day18Remark;
    private Integer day18AM;
    private Integer day18PM;
    private Double day18ExHours;

    private String day19;
    private String day19Remark;
    private Integer day19AM;
    private Integer day19PM;
    private Double day19ExHours;

    private String day20;
    private String day20Remark;
    private Integer day20AM;
    private Integer day20PM;
    private Double day20ExHours;

    private String day21;
    private String day21Remark;
    private Integer day21AM;
    private Integer day21PM;
    private Double day21ExHours;


    private String day22;
    private String day22Remark;
    private Integer day22AM;
    private Integer day22PM;
    private Double day22ExHours;

    private String day23;
    private String day23Remark;
    private Integer day23AM;
    private Integer day23PM;
    private Double day23ExHours;

    private String day24;
    private String day24Remark;
    private Integer day24AM;
    private Integer day24PM;
    private Double day24ExHours;

    private String day25;
    private String day25Remark;
    private Integer day25AM;
    private Integer day25PM;
    private Double day25ExHours;


    private String day26;
    private String day26Remark;
    private Integer day26AM;
    private Integer day26PM;
    private Double day26ExHours;

    private String day27;
    private String day27Remark;
    private Integer day27AM;
    private Integer day27PM;
    private Double day27ExHours;

    private String day28;
    private String day28Remark;
    private Integer day28AM;
    private Integer day28PM;
    private Double day28ExHours;

    private String day29;
    private String day29Remark;
    private Integer day29AM;
    private Integer day29PM;
    private Double day29ExHours;

    private String day30;
    private String day30Remark;
    private Integer day30AM;
    private Integer day30PM;
    private Double day30ExHours;

    private String day31;
    private String day31Remark;
    private Integer day31AM;
    private Integer day31PM;
    private Double day31ExHours;


    //回显字段
    private String nameReal;
    private String daytitleSql;

    private String dayNum;
    private String dayNumRemark;



    private Integer renShiChecked;

    public Integer getRenShiChecked() {
        return renShiChecked;
    }

    public void setRenShiChecked(Integer renShiChecked) {
        this.renShiChecked = renShiChecked;
    }

    public String getDaytitleSql() {
        return daytitleSql;
    }

    public void setDaytitleSql(String daytitleSql) {
        this.daytitleSql = daytitleSql;
    }

    public String getNameReal() {
        return nameReal;
    }

    public void setNameReal(String nameReal) {
        this.nameReal = nameReal;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getDayNumRemark() {
        return dayNumRemark;
    }

    public void setDayNumRemark(String dayNumRemark) {
        this.dayNumRemark = dayNumRemark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Double getZhengbanHours() {
        return zhengbanHours;
    }

    public void setZhengbanHours(Double zhengbanHours) {
        this.zhengbanHours = zhengbanHours;
    }

    public Double getUsualExtHours() {
        return usualExtHours;
    }

    public void setUsualExtHours(Double usualExtHours) {
        this.usualExtHours = usualExtHours;
    }

    public Double getWorkendHours() {
        return workendHours;
    }

    public void setWorkendHours(Double workendHours) {
        this.workendHours = workendHours;
    }

    public Double getChinaPaidLeave() {
        return chinaPaidLeave;
    }

    public void setChinaPaidLeave(Double chinaPaidLeave) {
        this.chinaPaidLeave = chinaPaidLeave;
    }

    public Double getOtherPaidLeave() {
        return otherPaidLeave;
    }

    public void setOtherPaidLeave(Double otherPaidLeave) {
        this.otherPaidLeave = otherPaidLeave;
    }

    public Double getLeaveOfAbsense() {
        return leaveOfAbsense;
    }

    public void setLeaveOfAbsense(Double leaveOfAbsense) {
        this.leaveOfAbsense = leaveOfAbsense;
    }

    public Double getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Double sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Double getOtherAllo() {
        return otherAllo;
    }

    public void setOtherAllo(Double otherAllo) {
        this.otherAllo = otherAllo;
    }

    public Double getFullWorkReword() {
        return fullWorkReword;
    }

    public void setFullWorkReword(Double fullWorkReword) {
        this.fullWorkReword = fullWorkReword;
    }

    public Double getHighTempAllow() {
        return highTempAllow;
    }

    public void setHighTempAllow(Double highTempAllow) {
        this.highTempAllow = highTempAllow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDay01() {
        return day01;
    }

    public void setDay01(String day01) {
        this.day01 = day01;
    }

    public String getDay01Remark() {
        return day01Remark;
    }

    public void setDay01Remark(String day01Remark) {
        this.day01Remark = day01Remark;
    }

    public String getDay02() {
        return day02;
    }

    public void setDay02(String day02) {
        this.day02 = day02;
    }

    public String getDay02Remark() {
        return day02Remark;
    }

    public void setDay02Remark(String day02Remark) {
        this.day02Remark = day02Remark;
    }

    public String getDay03() {
        return day03;
    }

    public void setDay03(String day03) {
        this.day03 = day03;
    }

    public String getDay03Remark() {
        return day03Remark;
    }

    public void setDay03Remark(String day03Remark) {
        this.day03Remark = day03Remark;
    }

    public String getDay04() {
        return day04;
    }

    public void setDay04(String day04) {
        this.day04 = day04;
    }

    public String getDay04Remark() {
        return day04Remark;
    }

    public void setDay04Remark(String day04Remark) {
        this.day04Remark = day04Remark;
    }

    public String getDay05() {
        return day05;
    }

    public void setDay05(String day05) {
        this.day05 = day05;
    }

    public String getDay05Remark() {
        return day05Remark;
    }

    public void setDay05Remark(String day05Remark) {
        this.day05Remark = day05Remark;
    }

    public String getDay06() {
        return day06;
    }

    public void setDay06(String day06) {
        this.day06 = day06;
    }

    public String getDay06Remark() {
        return day06Remark;
    }

    public void setDay06Remark(String day06Remark) {
        this.day06Remark = day06Remark;
    }

    public String getDay07() {
        return day07;
    }

    public void setDay07(String day07) {
        this.day07 = day07;
    }

    public String getDay07Remark() {
        return day07Remark;
    }

    public void setDay07Remark(String day07Remark) {
        this.day07Remark = day07Remark;
    }

    public String getDay08() {
        return day08;
    }

    public void setDay08(String day08) {
        this.day08 = day08;
    }

    public String getDay08Remark() {
        return day08Remark;
    }

    public void setDay08Remark(String day08Remark) {
        this.day08Remark = day08Remark;
    }

    public String getDay09() {
        return day09;
    }

    public void setDay09(String day09) {
        this.day09 = day09;
    }

    public String getDay09Remark() {
        return day09Remark;
    }

    public void setDay09Remark(String day09Remark) {
        this.day09Remark = day09Remark;
    }

    public String getDay10() {
        return day10;
    }

    public void setDay10(String day10) {
        this.day10 = day10;
    }

    public String getDay10Remark() {
        return day10Remark;
    }

    public void setDay10Remark(String day10Remark) {
        this.day10Remark = day10Remark;
    }

    public String getDay11() {
        return day11;
    }

    public void setDay11(String day11) {
        this.day11 = day11;
    }

    public String getDay11Remark() {
        return day11Remark;
    }

    public void setDay11Remark(String day11Remark) {
        this.day11Remark = day11Remark;
    }

    public String getDay12() {
        return day12;
    }

    public void setDay12(String day12) {
        this.day12 = day12;
    }

    public String getDay12Remark() {
        return day12Remark;
    }

    public void setDay12Remark(String day12Remark) {
        this.day12Remark = day12Remark;
    }

    public String getDay13() {
        return day13;
    }

    public void setDay13(String day13) {
        this.day13 = day13;
    }

    public String getDay13Remark() {
        return day13Remark;
    }

    public void setDay13Remark(String day13Remark) {
        this.day13Remark = day13Remark;
    }

    public String getDay14() {
        return day14;
    }

    public void setDay14(String day14) {
        this.day14 = day14;
    }

    public String getDay14Remark() {
        return day14Remark;
    }

    public void setDay14Remark(String day14Remark) {
        this.day14Remark = day14Remark;
    }

    public String getDay15() {
        return day15;
    }

    public void setDay15(String day15) {
        this.day15 = day15;
    }

    public String getDay15Remark() {
        return day15Remark;
    }

    public void setDay15Remark(String day15Remark) {
        this.day15Remark = day15Remark;
    }

    public String getDay16() {
        return day16;
    }

    public void setDay16(String day16) {
        this.day16 = day16;
    }

    public String getDay16Remark() {
        return day16Remark;
    }

    public void setDay16Remark(String day16Remark) {
        this.day16Remark = day16Remark;
    }

    public String getDay17() {
        return day17;
    }

    public void setDay17(String day17) {
        this.day17 = day17;
    }

    public String getDay17Remark() {
        return day17Remark;
    }

    public void setDay17Remark(String day17Remark) {
        this.day17Remark = day17Remark;
    }

    public String getDay18() {
        return day18;
    }

    public void setDay18(String day18) {
        this.day18 = day18;
    }

    public String getDay18Remark() {
        return day18Remark;
    }

    public void setDay18Remark(String day18Remark) {
        this.day18Remark = day18Remark;
    }

    public String getDay19() {
        return day19;
    }

    public void setDay19(String day19) {
        this.day19 = day19;
    }

    public String getDay19Remark() {
        return day19Remark;
    }

    public void setDay19Remark(String day19Remark) {
        this.day19Remark = day19Remark;
    }

    public String getDay20() {
        return day20;
    }

    public void setDay20(String day20) {
        this.day20 = day20;
    }

    public String getDay20Remark() {
        return day20Remark;
    }

    public void setDay20Remark(String day20Remark) {
        this.day20Remark = day20Remark;
    }

    public String getDay21() {
        return day21;
    }

    public void setDay21(String day21) {
        this.day21 = day21;
    }

    public String getDay21Remark() {
        return day21Remark;
    }

    public void setDay21Remark(String day21Remark) {
        this.day21Remark = day21Remark;
    }

    public String getDay22() {
        return day22;
    }

    public void setDay22(String day22) {
        this.day22 = day22;
    }

    public String getDay22Remark() {
        return day22Remark;
    }

    public void setDay22Remark(String day22Remark) {
        this.day22Remark = day22Remark;
    }

    public String getDay23() {
        return day23;
    }

    public void setDay23(String day23) {
        this.day23 = day23;
    }

    public String getDay23Remark() {
        return day23Remark;
    }

    public void setDay23Remark(String day23Remark) {
        this.day23Remark = day23Remark;
    }

    public String getDay24() {
        return day24;
    }

    public void setDay24(String day24) {
        this.day24 = day24;
    }

    public String getDay24Remark() {
        return day24Remark;
    }

    public void setDay24Remark(String day24Remark) {
        this.day24Remark = day24Remark;
    }

    public String getDay25() {
        return day25;
    }

    public void setDay25(String day25) {
        this.day25 = day25;
    }

    public String getDay25Remark() {
        return day25Remark;
    }

    public void setDay25Remark(String day25Remark) {
        this.day25Remark = day25Remark;
    }

    public String getDay26() {
        return day26;
    }

    public void setDay26(String day26) {
        this.day26 = day26;
    }

    public String getDay26Remark() {
        return day26Remark;
    }

    public void setDay26Remark(String day26Remark) {
        this.day26Remark = day26Remark;
    }

    public String getDay27() {
        return day27;
    }

    public void setDay27(String day27) {
        this.day27 = day27;
    }

    public String getDay27Remark() {
        return day27Remark;
    }

    public void setDay27Remark(String day27Remark) {
        this.day27Remark = day27Remark;
    }

    public String getDay28() {
        return day28;
    }

    public void setDay28(String day28) {
        this.day28 = day28;
    }

    public String getDay28Remark() {
        return day28Remark;
    }

    public void setDay28Remark(String day28Remark) {
        this.day28Remark = day28Remark;
    }

    public String getDay29() {
        return day29;
    }

    public void setDay29(String day29) {
        this.day29 = day29;
    }

    public String getDay29Remark() {
        return day29Remark;
    }

    public void setDay29Remark(String day29Remark) {
        this.day29Remark = day29Remark;
    }

    public String getDay30() {
        return day30;
    }

    public void setDay30(String day30) {
        this.day30 = day30;
    }

    public String getDay30Remark() {
        return day30Remark;
    }

    public void setDay30Remark(String day30Remark) {
        this.day30Remark = day30Remark;
    }

    public String getDay31() {
        return day31;
    }

    public void setDay31(String day31) {
        this.day31 = day31;
    }

    public String getDay31Remark() {
        return day31Remark;
    }

    public void setDay31Remark(String day31Remark) {
        this.day31Remark = day31Remark;
    }


    public Integer getDay01AM() {
        if (this.day01 != null && this.day01.trim().length() > 0 && this.day01.contains(",")) {
            return Integer.valueOf(this.day01.split(",")[0]);
        }
        return day01AM;
    }

    public void setDay01AM(Integer day01AM) {
        this.day01AM = day01AM;
    }

    public Integer getDay01PM() {
        if (this.day01 != null && this.day01.trim().length() > 0 && this.day01.contains(",")) {
            return Integer.valueOf(this.day01.split(",")[1]);
        }
        return day01PM;
    }

    public void setDay01PM(Integer day01PM) {
        this.day01PM = day01PM;
    }

    public Double getDay01ExHours() {
        if (this.day01 != null && this.day01.trim().length() > 0 && this.day01.contains(",")) {
            return Double.valueOf(this.day01.split(",")[2]);
        }
        return day01ExHours;
    }

    public void setDay01ExHours(Double day01ExHours) {
        this.day01ExHours = day01ExHours;
    }

    public Integer getDay02AM() {
        if (this.day02 != null && this.day02.trim().length() > 0 && this.day02.contains(",")) {
            return Integer.valueOf(this.day02.split(",")[0]);
        }
        return day02AM;
    }

    public void setDay02AM(Integer day02AM) {
        this.day02AM = day02AM;
    }

    public Integer getDay02PM() {
        if (this.day02 != null && this.day02.trim().length() > 0 && this.day02.contains(",")) {
            return Integer.valueOf(this.day02.split(",")[1]);
        }
        return day02PM;
    }

    public void setDay02PM(Integer day02PM) {
        this.day02PM = day02PM;
    }

    public Double getDay02ExHours() {
        if (this.day02 != null && this.day02.trim().length() > 0 && this.day02.contains(",")) {
            return Double.valueOf(this.day02.split(",")[2]);
        }
        return day02ExHours;
    }

    public void setDay02ExHours(Double day02ExHours) {
        this.day02ExHours = day02ExHours;
    }

    public Integer getDay03AM() {
        if (this.day03 != null && this.day03.trim().length() > 0 && this.day03.contains(",")) {
            return Integer.valueOf(this.day03.split(",")[0]);
        }
        return day03AM;
    }

    public void setDay03AM(Integer day03AM) {
        this.day03AM = day03AM;
    }

    public Integer getDay03PM() {
        if (this.day03 != null && this.day03.trim().length() > 0 && this.day03.contains(",")) {
            return Integer.valueOf(this.day03.split(",")[1]);
        }
        return day03PM;
    }

    public void setDay03PM(Integer day03PM) {
        this.day03PM = day03PM;
    }

    public Double getDay03ExHours() {
        if (this.day03 != null && this.day03.trim().length() > 0 && this.day03.contains(",")) {
            return Double.valueOf(this.day03.split(",")[2]);
        }
        return day03ExHours;
    }

    public void setDay03ExHours(Double day03ExHours) {
        this.day03ExHours = day03ExHours;
    }

    public Integer getDay04AM() {
        if (this.day04 != null && this.day04.trim().length() > 0 && this.day04.contains(",")) {
            return Integer.valueOf(this.day04.split(",")[0]);
        }
        return day04AM;
    }

    public void setDay04AM(Integer day04AM) {
        this.day04AM = day04AM;
    }

    public Integer getDay04PM() {
        if (this.day04 != null && this.day04.trim().length() > 0 && this.day04.contains(",")) {
            return Integer.valueOf(this.day04.split(",")[1]);
        }
        return day04PM;
    }

    public void setDay04PM(Integer day04PM) {
        this.day04PM = day04PM;
    }

    public Double getDay04ExHours() {
        if (this.day04 != null && this.day04.trim().length() > 0 && this.day04.contains(",")) {
            return Double.valueOf(this.day04.split(",")[2]);
        }
        return day04ExHours;
    }

    public void setDay04ExHours(Double day04ExHours) {
        this.day04ExHours = day04ExHours;
    }

    public Integer getDay05AM() {
        if (this.day05 != null && this.day05.trim().length() > 0 && this.day05.contains(",")) {
            return Integer.valueOf(this.day05.split(",")[0]);
        }
        return day05AM;
    }

    public void setDay05AM(Integer day05AM) {
        this.day05AM = day05AM;
    }

    public Integer getDay05PM() {
        if (this.day05 != null && this.day05.trim().length() > 0 && this.day05.contains(",")) {
            return Integer.valueOf(this.day05.split(",")[1]);
        }
        return day05PM;
    }

    public void setDay05PM(Integer day05PM) {
        this.day05PM = day05PM;
    }

    public Double getDay05ExHours() {
        if (this.day05 != null && this.day05.trim().length() > 0 && this.day05.contains(",")) {
            return Double.valueOf(this.day05.split(",")[2]);
        }
        return day05ExHours;
    }

    public void setDay05ExHours(Double day05ExHours) {
        this.day05ExHours = day05ExHours;
    }

    public Integer getDay06AM() {
        if (this.day06 != null && this.day06.trim().length() > 0 && this.day06.contains(",")) {
            return Integer.valueOf(this.day06.split(",")[0]);
        }
        return day06AM;
    }

    public void setDay06AM(Integer day06AM) {
        this.day06AM = day06AM;
    }

    public Integer getDay06PM() {
        if (this.day06 != null && this.day06.trim().length() > 0 && this.day06.contains(",")) {
            return Integer.valueOf(this.day06.split(",")[1]);
        }
        return day06PM;
    }

    public void setDay06PM(Integer day06PM) {
        this.day06PM = day06PM;
    }

    public Double getDay06ExHours() {
        if (this.day06 != null && this.day06.trim().length() > 0 && this.day06.contains(",")) {
            return Double.valueOf(this.day06.split(",")[2]);
        }
        return day06ExHours;
    }

    public void setDay06ExHours(Double day06ExHours) {
        this.day06ExHours = day06ExHours;
    }

    public Integer getDay07AM() {
        if (this.day07 != null && this.day07.trim().length() > 0 && this.day07.contains(",")) {
            return Integer.valueOf(this.day07.split(",")[0]);
        }
        return day07AM;
    }

    public void setDay07AM(Integer day07AM) {
        this.day07AM = day07AM;
    }

    public Integer getDay07PM() {
        if (this.day07 != null && this.day07.trim().length() > 0 && this.day07.contains(",")) {
            return Integer.valueOf(this.day07.split(",")[1]);
        }
        return day07PM;
    }

    public void setDay07PM(Integer day07PM) {
        this.day07PM = day07PM;
    }

    public Double getDay07ExHours() {
        if (this.day07 != null && this.day07.trim().length() > 0 && this.day07.contains(",")) {
            return Double.valueOf(this.day07.split(",")[2]);
        }
        return day07ExHours;
    }

    public void setDay07ExHours(Double day07ExHours) {
        this.day07ExHours = day07ExHours;
    }

    public Integer getDay08AM() {
        if (this.day08 != null && this.day08.trim().length() > 0 && this.day08.contains(",")) {
            return Integer.valueOf(this.day08.split(",")[0]);
        }
        return day08AM;
    }

    public void setDay08AM(Integer day08AM) {
        this.day08AM = day08AM;
    }

    public Integer getDay08PM() {
        if (this.day08 != null && this.day08.trim().length() > 0 && this.day08.contains(",")) {
            return Integer.valueOf(this.day08.split(",")[1]);
        }
        return day08PM;
    }

    public void setDay08PM(Integer day08PM) {
        this.day08PM = day08PM;
    }

    public Double getDay08ExHours() {
        if (this.day08 != null && this.day08.trim().length() > 0 && this.day08.contains(",")) {
            return Double.valueOf(this.day08.split(",")[2]);
        }
        return day08ExHours;
    }

    public void setDay08ExHours(Double day08ExHours) {
        this.day08ExHours = day08ExHours;
    }

    public Integer getDay09AM() {
        if (this.day09 != null && this.day09.trim().length() > 0 && this.day09.contains(",")) {
            return Integer.valueOf(this.day09.split(",")[0]);
        }
        return day09AM;
    }

    public void setDay09AM(Integer day09AM) {
        this.day09AM = day09AM;
    }

    public Integer getDay09PM() {
        if (this.day09 != null && this.day09.trim().length() > 0 && this.day09.contains(",")) {
            return Integer.valueOf(this.day09.split(",")[1]);
        }
        return day09PM;
    }

    public void setDay09PM(Integer day09PM) {
        this.day09PM = day09PM;
    }

    public Double getDay09ExHours() {
        if (this.day09 != null && this.day09.trim().length() > 0 && this.day09.contains(",")) {
            return Double.valueOf(this.day09.split(",")[2]);
        }
        return day09ExHours;
    }

    public void setDay09ExHours(Double day09ExHours) {
        this.day09ExHours = day09ExHours;
    }

    public Integer getDay10AM() {
        if (this.day10 != null && this.day10.trim().length() > 0 && this.day10.contains(",")) {
            return Integer.valueOf(this.day10.split(",")[0]);
        }
        return day10AM;
    }

    public void setDay10AM(Integer day10AM) {
        this.day10AM = day10AM;
    }

    public Integer getDay10PM() {
        if (this.day10 != null && this.day10.trim().length() > 0 && this.day10.contains(",")) {
            return Integer.valueOf(this.day10.split(",")[1]);
        }
        return day10PM;
    }

    public void setDay10PM(Integer day10PM) {
        this.day10PM = day10PM;
    }

    public Double getDay10ExHours() {
        if (this.day10 != null && this.day10.trim().length() > 0 && this.day10.contains(",")) {
            return Double.valueOf(this.day10.split(",")[2]);
        }
        return day10ExHours;
    }

    public void setDay10ExHours(Double day10ExHours) {
        this.day10ExHours = day10ExHours;
    }

    public Integer getDay11AM() {
        if (this.day11 != null && this.day11.trim().length() > 0 && this.day11.contains(",")) {
            return Integer.valueOf(this.day11.split(",")[0]);
        }
        return day11AM;
    }

    public void setDay11AM(Integer day11AM) {
        this.day11AM = day11AM;
    }

    public Integer getDay11PM() {
        if (this.day11 != null && this.day11.trim().length() > 0 && this.day11.contains(",")) {
            return Integer.valueOf(this.day11.split(",")[1]);
        }
        return day11PM;
    }

    public void setDay11PM(Integer day11PM) {
        this.day11PM = day11PM;
    }

    public Double getDay11ExHours() {
        if (this.day11 != null && this.day11.trim().length() > 0 && this.day11.contains(",")) {
            return Double.valueOf(this.day11.split(",")[2]);
        }
        return day11ExHours;
    }

    public void setDay11ExHours(Double day11ExHours) {
        this.day11ExHours = day11ExHours;
    }

    public Integer getDay12AM() {
        if (this.day12 != null && this.day12.trim().length() > 0 && this.day12.contains(",")) {
            return Integer.valueOf(this.day12.split(",")[0]);
        }
        return day12AM;
    }

    public void setDay12AM(Integer day12AM) {
        this.day12AM = day12AM;
    }

    public Integer getDay12PM() {
        if (this.day12 != null && this.day12.trim().length() > 0 && this.day12.contains(",")) {
            return Integer.valueOf(this.day12.split(",")[1]);
        }
        return day12PM;
    }

    public void setDay12PM(Integer day12PM) {
        this.day12PM = day12PM;
    }

    public Double getDay12ExHours() {
        if (this.day12 != null && this.day12.trim().length() > 0 && this.day12.contains(",")) {
            return Double.valueOf(this.day12.split(",")[2]);
        }
        return day12ExHours;
    }

    public void setDay12ExHours(Double day12ExHours) {
        this.day12ExHours = day12ExHours;
    }

    public Integer getDay13AM() {
        if (this.day13 != null && this.day13.trim().length() > 0 && this.day13.contains(",")) {
            return Integer.valueOf(this.day13.split(",")[0]);
        }
        return day13AM;
    }

    public void setDay13AM(Integer day13AM) {
        this.day13AM = day13AM;
    }

    public Integer getDay13PM() {
        if (this.day13 != null && this.day13.trim().length() > 0 && this.day13.contains(",")) {
            return Integer.valueOf(this.day13.split(",")[1]);
        }
        return day13PM;
    }

    public void setDay13PM(Integer day13PM) {
        this.day13PM = day13PM;
    }

    public Double getDay13ExHours() {
        if (this.day13 != null && this.day13.trim().length() > 0 && this.day13.contains(",")) {
            return Double.valueOf(this.day13.split(",")[2]);
        }
        return day13ExHours;
    }

    public void setDay13ExHours(Double day13ExHours) {
        this.day13ExHours = day13ExHours;
    }

    public Integer getDay14AM() {
        if (this.day14 != null && this.day14.trim().length() > 0 && this.day14.contains(",")) {
            return Integer.valueOf(this.day14.split(",")[0]);
        }
        return day14AM;
    }

    public void setDay14AM(Integer day14AM) {
        this.day14AM = day14AM;
    }

    public Integer getDay14PM() {
        if (this.day14 != null && this.day14.trim().length() > 0 && this.day14.contains(",")) {
            return Integer.valueOf(this.day14.split(",")[1]);
        }
        return day14PM;
    }

    public void setDay14PM(Integer day14PM) {
        this.day14PM = day14PM;
    }

    public Double getDay14ExHours() {
        if (this.day14 != null && this.day14.trim().length() > 0 && this.day14.contains(",")) {
            return Double.valueOf(this.day14.split(",")[2]);
        }
        return day14ExHours;
    }

    public void setDay14ExHours(Double day14ExHours) {
        this.day14ExHours = day14ExHours;
    }

    public Integer getDay15AM() {
        if (this.day15 != null && this.day15.trim().length() > 0 && this.day15.contains(",")) {
            return Integer.valueOf(this.day15.split(",")[0]);
        }
        return day15AM;
    }

    public void setDay15AM(Integer day15AM) {
        this.day15AM = day15AM;
    }

    public Integer getDay15PM() {
        if (this.day15 != null && this.day15.trim().length() > 0 && this.day15.contains(",")) {
            return Integer.valueOf(this.day15.split(",")[1]);
        }
        return day15PM;
    }

    public void setDay15PM(Integer day15PM) {
        this.day15PM = day15PM;
    }

    public Double getDay15ExHours() {
        if (this.day15 != null && this.day15.trim().length() > 0 && this.day15.contains(",")) {
            return Double.valueOf(this.day15.split(",")[2]);
        }
        return day15ExHours;
    }

    public void setDay15ExHours(Double day15ExHours) {
        this.day15ExHours = day15ExHours;
    }

    public Integer getDay16AM() {
        if (this.day16 != null && this.day16.trim().length() > 0 && this.day16.contains(",")) {
            return Integer.valueOf(this.day16.split(",")[0]);
        }
        return day16AM;
    }

    public void setDay16AM(Integer day16AM) {
        this.day16AM = day16AM;
    }

    public Integer getDay16PM() {
        if (this.day16 != null && this.day16.trim().length() > 0 && this.day16.contains(",")) {
            return Integer.valueOf(this.day16.split(",")[1]);
        }
        return day16PM;
    }

    public void setDay16PM(Integer day16PM) {
        this.day16PM = day16PM;
    }

    public Double getDay16ExHours() {
        if (this.day16 != null && this.day16.trim().length() > 0 && this.day16.contains(",")) {
            return Double.valueOf(this.day16.split(",")[2]);
        }
        return day16ExHours;
    }

    public void setDay16ExHours(Double day16ExHours) {
        this.day16ExHours = day16ExHours;
    }

    public Integer getDay17AM() {
        if (this.day17 != null && this.day17.trim().length() > 0 && this.day17.contains(",")) {
            return Integer.valueOf(this.day17.split(",")[0]);
        }
        return day17AM;
    }

    public void setDay17AM(Integer day17AM) {
        this.day17AM = day17AM;
    }

    public Integer getDay17PM() {
        if (this.day17 != null && this.day17.trim().length() > 0 && this.day17.contains(",")) {
            return Integer.valueOf(this.day17.split(",")[1]);
        }
        return day17PM;
    }

    public void setDay17PM(Integer day17PM) {
        this.day17PM = day17PM;
    }

    public Double getDay17ExHours() {
        if (this.day17 != null && this.day17.trim().length() > 0 && this.day17.contains(",")) {
            return Double.valueOf(this.day17.split(",")[2]);
        }
        return day17ExHours;
    }

    public void setDay17ExHours(Double day17ExHours) {
        this.day17ExHours = day17ExHours;
    }

    public Integer getDay18AM() {
        if (this.day18 != null && this.day18.trim().length() > 0 && this.day18.contains(",")) {
            return Integer.valueOf(this.day18.split(",")[0]);
        }
        return day18AM;
    }

    public void setDay18AM(Integer day18AM) {
        this.day18AM = day18AM;
    }

    public Integer getDay18PM() {
        if (this.day18 != null && this.day18.trim().length() > 0 && this.day18.contains(",")) {
            return Integer.valueOf(this.day18.split(",")[1]);
        }
        return day18PM;
    }

    public void setDay18PM(Integer day18PM) {
        this.day18PM = day18PM;
    }

    public Double getDay18ExHours() {
        if (this.day18 != null && this.day18.trim().length() > 0 && this.day18.contains(",")) {
            return Double.valueOf(this.day18.split(",")[2]);
        }
        return day18ExHours;
    }

    public void setDay18ExHours(Double day18ExHours) {
        this.day18ExHours = day18ExHours;
    }

    public Integer getDay19AM() {
        if (this.day19 != null && this.day19.trim().length() > 0 && this.day19.contains(",")) {
            return Integer.valueOf(this.day19.split(",")[0]);
        }
        return day19AM;
    }

    public void setDay19AM(Integer day19AM) {
        this.day19AM = day19AM;
    }

    public Integer getDay19PM() {
        if (this.day19 != null && this.day19.trim().length() > 0 && this.day19.contains(",")) {
            return Integer.valueOf(this.day19.split(",")[1]);
        }
        return day19PM;
    }

    public void setDay19PM(Integer day19PM) {
        this.day19PM = day19PM;
    }

    public Double getDay19ExHours() {
        if (this.day19 != null && this.day19.trim().length() > 0 && this.day19.contains(",")) {
            return Double.valueOf(this.day19.split(",")[2]);
        }
        return day19ExHours;
    }

    public void setDay19ExHours(Double day19ExHours) {
        this.day19ExHours = day19ExHours;
    }

    public Integer getDay20AM() {
        if (this.day20 != null && this.day20.trim().length() > 0 && this.day20.contains(",")) {
            return Integer.valueOf(this.day20.split(",")[0]);
        }
        return day20AM;
    }

    public void setDay20AM(Integer day20AM) {
        this.day20AM = day20AM;
    }

    public Integer getDay20PM() {
        if (this.day20 != null && this.day20.trim().length() > 0 && this.day20.contains(",")) {
            return Integer.valueOf(this.day20.split(",")[1]);
        }
        return day20PM;
    }

    public void setDay20PM(Integer day20PM) {
        this.day20PM = day20PM;
    }

    public Double getDay20ExHours() {
        if (this.day20 != null && this.day20.trim().length() > 0 && this.day20.contains(",")) {
            return Double.valueOf(this.day20.split(",")[2]);
        }
        return day20ExHours;
    }

    public void setDay20ExHours(Double day20ExHours) {
        this.day20ExHours = day20ExHours;
    }

    public Integer getDay21AM() {
        if (this.day21 != null && this.day21.trim().length() > 0 && this.day21.contains(",")) {
            return Integer.valueOf(this.day21.split(",")[0]);
        }
        return day21AM;
    }

    public void setDay21AM(Integer day21AM) {
        this.day21AM = day21AM;
    }

    public Integer getDay21PM() {
        if (this.day21 != null && this.day21.trim().length() > 0 && this.day21.contains(",")) {
            return Integer.valueOf(this.day21.split(",")[1]);
        }
        return day21PM;
    }

    public void setDay21PM(Integer day21PM) {
        this.day21PM = day21PM;
    }

    public Double getDay21ExHours() {
        if (this.day21 != null && this.day21.trim().length() > 0 && this.day21.contains(",")) {
            return Double.valueOf(this.day21.split(",")[2]);
        }
        return day21ExHours;
    }

    public void setDay21ExHours(Double day21ExHours) {
        this.day21ExHours = day21ExHours;
    }

    public Integer getDay22AM() {
        if (this.day22 != null && this.day22.trim().length() > 0 && this.day22.contains(",")) {
            return Integer.valueOf(this.day22.split(",")[0]);
        }
        return day22AM;
    }

    public void setDay22AM(Integer day22AM) {
        this.day22AM = day22AM;
    }

    public Integer getDay22PM() {
        if (this.day22 != null && this.day22.trim().length() > 0 && this.day22.contains(",")) {
            return Integer.valueOf(this.day22.split(",")[1]);
        }
        return day22PM;
    }

    public void setDay22PM(Integer day22PM) {
        this.day22PM = day22PM;
    }

    public Double getDay22ExHours() {
        if (this.day22 != null && this.day22.trim().length() > 0 && this.day22.contains(",")) {
            return Double.valueOf(this.day22.split(",")[2]);
        }
        return day22ExHours;
    }

    public void setDay22ExHours(Double day22ExHours) {
        this.day22ExHours = day22ExHours;
    }

    public Integer getDay23AM() {
        if (this.day23 != null && this.day23.trim().length() > 0 && this.day23.contains(",")) {
            return Integer.valueOf(this.day23.split(",")[0]);
        }
        return day23AM;
    }

    public void setDay23AM(Integer day23AM) {
        this.day23AM = day23AM;
    }

    public Integer getDay23PM() {
        if (this.day23 != null && this.day23.trim().length() > 0 && this.day23.contains(",")) {
            return Integer.valueOf(this.day23.split(",")[1]);
        }
        return day23PM;
    }

    public void setDay23PM(Integer day23PM) {
        this.day23PM = day23PM;
    }

    public Double getDay23ExHours() {
        if (this.day23 != null && this.day23.trim().length() > 0 && this.day23.contains(",")) {
            return Double.valueOf(this.day23.split(",")[2]);
        }
        return day23ExHours;
    }

    public void setDay23ExHours(Double day23ExHours) {
        this.day23ExHours = day23ExHours;
    }

    public Integer getDay24AM() {
        if (this.day24 != null && this.day24.trim().length() > 0 && this.day24.contains(",")) {
            return Integer.valueOf(this.day24.split(",")[0]);
        }
        return day24AM;
    }

    public void setDay24AM(Integer day24AM) {
        this.day24AM = day24AM;
    }

    public Integer getDay24PM() {
        if (this.day24 != null && this.day24.trim().length() > 0 && this.day24.contains(",")) {
            return Integer.valueOf(this.day24.split(",")[1]);
        }
        return day24PM;
    }

    public void setDay24PM(Integer day24PM) {
        this.day24PM = day24PM;
    }

    public Double getDay24ExHours() {
        if (this.day24 != null && this.day24.trim().length() > 0 && this.day24.contains(",")) {
            return Double.valueOf(this.day24.split(",")[2]);
        }
        return day24ExHours;
    }

    public void setDay24ExHours(Double day24ExHours) {
        this.day24ExHours = day24ExHours;
    }

    public Integer getDay25AM() {
        if (this.day25 != null && this.day25.trim().length() > 0 && this.day25.contains(",")) {
            return Integer.valueOf(this.day25.split(",")[0]);
        }
        return day25AM;
    }

    public void setDay25AM(Integer day25AM) {
        this.day25AM = day25AM;
    }

    public Integer getDay25PM() {
        if (this.day25 != null && this.day25.trim().length() > 0 && this.day25.contains(",")) {
            return Integer.valueOf(this.day25.split(",")[1]);
        }
        return day25PM;
    }

    public void setDay25PM(Integer day25PM) {
        this.day25PM = day25PM;
    }

    public Double getDay25ExHours() {
        if (this.day25 != null && this.day25.trim().length() > 0 && this.day25.contains(",")) {
            return Double.valueOf(this.day25.split(",")[2]);
        }
        return day25ExHours;
    }

    public void setDay25ExHours(Double day25ExHours) {
        this.day25ExHours = day25ExHours;
    }

    public Integer getDay26AM() {
        if (this.day26 != null && this.day26.trim().length() > 0 && this.day26.contains(",")) {
            return Integer.valueOf(this.day26.split(",")[0]);
        }
        return day26AM;
    }

    public void setDay26AM(Integer day26AM) {
        this.day26AM = day26AM;
    }

    public Integer getDay26PM() {
        if (this.day26 != null && this.day26.trim().length() > 0 && this.day26.contains(",")) {
            return Integer.valueOf(this.day26.split(",")[1]);
        }
        return day26PM;
    }

    public void setDay26PM(Integer day26PM) {
        this.day26PM = day26PM;
    }

    public Double getDay26ExHours() {
        if (this.day26 != null && this.day26.trim().length() > 0 && this.day26.contains(",")) {
            return Double.valueOf(this.day26.split(",")[2]);
        }
        return day26ExHours;
    }

    public void setDay26ExHours(Double day26ExHours) {
        this.day26ExHours = day26ExHours;
    }

    public Integer getDay27AM() {
        if (this.day27 != null && this.day27.trim().length() > 0 && this.day27.contains(",")) {
            return Integer.valueOf(this.day27.split(",")[0]);
        }
        return day27AM;
    }

    public void setDay27AM(Integer day27AM) {
        this.day27AM = day27AM;
    }

    public Integer getDay27PM() {
        if (this.day27 != null && this.day27.trim().length() > 0 && this.day27.contains(",")) {
            return Integer.valueOf(this.day27.split(",")[1]);
        }
        return day27PM;
    }

    public void setDay27PM(Integer day27PM) {
        this.day27PM = day27PM;
    }

    public Double getDay27ExHours() {
        if (this.day27 != null && this.day27.trim().length() > 0 && this.day27.contains(",")) {
            return Double.valueOf(this.day27.split(",")[2]);
        }
        return day27ExHours;
    }

    public void setDay27ExHours(Double day27ExHours) {
        this.day27ExHours = day27ExHours;
    }

    public Integer getDay28AM() {
        if (this.day28 != null && this.day28.trim().length() > 0 && this.day28.contains(",")) {
            return Integer.valueOf(this.day28.split(",")[0]);
        }
        return day28AM;
    }

    public void setDay28AM(Integer day28AM) {
        this.day28AM = day28AM;
    }

    public Integer getDay28PM() {
        if (this.day28 != null && this.day28.trim().length() > 0 && this.day28.contains(",")) {
            return Integer.valueOf(this.day28.split(",")[1]);
        }
        return day28PM;
    }

    public void setDay28PM(Integer day28PM) {
        this.day28PM = day28PM;
    }

    public Double getDay28ExHours() {
        if (this.day28 != null && this.day28.trim().length() > 0 && this.day28.contains(",")) {
            return Double.valueOf(this.day28.split(",")[2]);
        }
        return day28ExHours;
    }

    public void setDay28ExHours(Double day28ExHours) {
        this.day28ExHours = day28ExHours;
    }

    public Integer getDay29AM() {
        if (this.day29 != null && this.day29.trim().length() > 0 && this.day29.contains(",")) {
            return Integer.valueOf(this.day29.split(",")[0]);
        }
        return day29AM;
    }

    public void setDay29AM(Integer day29AM) {
        this.day29AM = day29AM;
    }

    public Integer getDay29PM() {
        if (this.day29 != null && this.day29.trim().length() > 0 && this.day29.contains(",")) {
            return Integer.valueOf(this.day29.split(",")[1]);
        }
        return day29PM;
    }

    public void setDay29PM(Integer day29PM) {
        this.day29PM = day29PM;
    }

    public Double getDay29ExHours() {
        if (this.day29 != null && this.day29.trim().length() > 0 && this.day29.contains(",")) {
            return Double.valueOf(this.day29.split(",")[2]);
        }
        return day29ExHours;
    }

    public void setDay29ExHours(Double day29ExHours) {
        this.day29ExHours = day29ExHours;
    }

    public Integer getDay30AM() {
        if (this.day30 != null && this.day30.trim().length() > 0 && this.day30.contains(",")) {
            return Integer.valueOf(this.day30.split(",")[0]);
        }
        return day30AM;
    }

    public void setDay30AM(Integer day30AM) {
        this.day30AM = day30AM;
    }

    public Integer getDay30PM() {
        if (this.day30 != null && this.day30.trim().length() > 0 && this.day30.contains(",")) {
            return Integer.valueOf(this.day30.split(",")[1]);
        }
        return day30PM;
    }

    public void setDay30PM(Integer day30PM) {
        this.day30PM = day30PM;
    }

    public Double getDay30ExHours() {
        if (this.day30 != null && this.day30.trim().length() > 0 && this.day30.contains(",")) {
            return Double.valueOf(this.day30.split(",")[2]);
        }
        return day30ExHours;
    }

    public void setDay30ExHours(Double day30ExHours) {
        this.day30ExHours = day30ExHours;
    }

    public Integer getDay31AM() {
        if (this.day31 != null && this.day31.trim().length() > 0 && this.day31.contains(",")) {
            return Integer.valueOf(this.day31.split(",")[0]);
        }
        return day31AM;
    }

    public void setDay31AM(Integer day31AM) {
        this.day31AM = day31AM;
    }

    public Integer getDay31PM() {
        if (this.day31 != null && this.day31.trim().length() > 0 && this.day31.contains(",")) {
            return Integer.valueOf(this.day31.split(",")[1]);
        }
        return day31PM;
    }

    public void setDay31PM(Integer day31PM) {
        this.day31PM = day31PM;
    }

    public Double getDay31ExHours() {
        if (this.day31 != null && this.day31.trim().length() > 0 && this.day31.contains(",")) {
            return Double.valueOf(this.day31.split(",")[2]);
        }
        return day31ExHours;
    }

    public void setDay31ExHours(Double day31ExHours) {
        this.day31ExHours = day31ExHours;
    }


}
