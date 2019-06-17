package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/3/5 0005 上午 11:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 3941220683407513983L;

    private Integer id;
    private String name;//姓名
    private Integer sex;//性别
    private String sexStr;
    private Integer deptId;//部门编号
    private String empNo;//工号
    private Integer positionId;//职位ID
    private Date incompdate;//入厂时间
    private Date conExpDate;//合同到期时间
    private Date birthDay;//出生日期
    private String ID_NO;//身份证号码
    private Integer nativePla;//籍guan
    private String homeAddr;//家庭住址
    private Date valiPeriodOfID;//身份证有效期
    private Integer nation;//民族
    private Integer marriaged;//婚否
    private String contactPhone;//联系电话
    private Integer educationLe;//学历
    private Integer screAgreement;//保密协议
    private Integer healthCerti;//健康证
    private Integer sateListAndLeaCerti;//社保清单或离职证明
    private Integer otherCerti;//其它证件
    private Integer positionAttrId;//职位
    private Integer type;

    private String educationLeUrl;
    private String sateListAndLeaCertiUrl;
    private String otherCertiUrl;



    private String username;
    private String passowrd;
    private Double posSalary;
    private Double jobSalary;
    private Double meritSalary;
    private Double compreSalary;


    private String marriagedStr;
    private String educationLeStr;
    private String screAgreementStr;
    private String healthCertiStr;
    private String sateListAndLeaCertiStr;
    private String otherCertiStr;
    private String birthDayStr;
    private String conExpDateStr;
    private String incompdateStr;
    private String positionAttrIdStr;
    private String valiPeriodOfIDStr;


    private String incomdateStr;
    private String deptName;
    private String positionName;
    private String positionLevel;
    private String endLeaveStr;
    private String beginLeaveStr;
    private List<Integer> deptIds;
    private List<Integer> sexIds;
    private List<Integer> positionIds;
    private List<Integer> ids;
    private List<Integer> nameIds;
    private String startIncomDateStr;
    private String endIncomDateStr;
    private String deptIdsstr;
    private String positionIdsstr;
    private String nativePlaStr;
    private String nationStr;
    private Double allMoney;

    private String namea;
    private String passowrd22;
    // 分页属性
    private int currentPage = 1;// 用于接收页面传过来的当前页数
    private int maxPage;// 最大页数
    private int recordCount;// 总记录数
    private int pageSize = 10;
    private int currentPageTotalNum;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAllMoney() {
        Double all = 0.0;
        if (this.getCompreSalary() != null)
            all += this.getCompreSalary();
        if (this.getPosSalary() != null)
            all += this.getPosSalary();
        if (this.getJobSalary() != null)
            all += this.getJobSalary();
        if (this.getMeritSalary() != null)
            all += this.getMeritSalary();
        return all;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public String getNamea() {
        return namea;
    }

    public void setNamea(String namea) {
        this.namea = namea;
    }

    public String getPassowrd22() {
        return passowrd22;
    }

    public void setPassowrd22(String passowrd22) {
        this.passowrd22 = passowrd22;
    }

    public String getNativePlaStr() {
        if(nativePla!=null) {
            if (nativePla==1) {
                return "北京";
            } else if (nativePla==2) {
                return "上海";
            } else if (nativePla==3) {
                return "广东";
            } else if (nativePla==4) {
                return "河北";
            } else if (nativePla==5) {
                return "山西";
            } else if (nativePla==6) {
                return "辽宁";
            } else if (nativePla==7) {
                return "吉林";
            } else if (nativePla==8) {
                return "黑龙江";
            } else if (nativePla==9) {
                return "江苏";
            } else if (nativePla==10) {
                return "浙江";
            } else if (nativePla==11) {
                return "安徽";
            } else if (nativePla==12) {
                return "福建";
            } else if (nativePla==13) {
                return "江西";
            } else if (nativePla==14) {
                return "山东";
            } else if (nativePla==15) {
                return "河南";
            } else if (nativePla==16) {
                return "湖北";
            } else if (nativePla==17) {
                return "湖南";
            } else if (nativePla==18) {
                return "天津";
            } else if (nativePla==19) {
                return "陕西";
            } else if (nativePla==20) {
                return "四川";
            } else if (nativePla==21) {
                return "台湾";
            } else if (nativePla==22) {
                return "云南";
            } else if (nativePla==23) {
                return "青海";
            } else if (nativePla==24) {
                return "甘肃";
            } else if (nativePla==25) {
                return "海南";
            } else if (nativePla==26) {
                return "贵州";
            } else if (nativePla==27) {
                return "重庆";
            } else if (nativePla==28) {
                return "新疆";
            } else if (nativePla==29) {
                return "广西";
            } else if (nativePla==30) {
                return "宁夏";
            } else if (nativePla==31) {
                return "内蒙古";
            } else if (nativePla==32) {
                return "西藏";
            }
        }
        return nativePlaStr;
    }

    public void setNativePlaStr(String nativePlaStr) {
        this.nativePlaStr = nativePlaStr;
    }

    public void setValiPeriodOfID(Date valiPeriodOfID) {
        this.valiPeriodOfID = valiPeriodOfID;
    }

    public String getValiPeriodOfIDStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.valiPeriodOfID != null)
            return formatter.format(this.valiPeriodOfID);
        return valiPeriodOfIDStr;
    }

    public void setValiPeriodOfIDStr(String valiPeriodOfIDStr) {
        this.valiPeriodOfIDStr = valiPeriodOfIDStr;
    }

    public String getEducationLeUrl() {
        return educationLeUrl;
    }

    public void setEducationLeUrl(String educationLeUrl) {
        this.educationLeUrl = educationLeUrl;
    }

    public String getSateListAndLeaCertiUrl() {
        return sateListAndLeaCertiUrl;
    }

    public void setSateListAndLeaCertiUrl(String sateListAndLeaCertiUrl) {
        this.sateListAndLeaCertiUrl = sateListAndLeaCertiUrl;
    }

    public String getOtherCertiUrl() {
        return otherCertiUrl;
    }

    public void setOtherCertiUrl(String otherCertiUrl) {
        this.otherCertiUrl = otherCertiUrl;
    }

    public Double getPosSalary() {
        return posSalary;
    }

    public void setPosSalary(Double posSalary) {
        this.posSalary = posSalary;
    }

    public Double getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(Double jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Double getMeritSalary() {
        return meritSalary;
    }

    public void setMeritSalary(Double meritSalary) {
        this.meritSalary = meritSalary;
    }

    public Double getCompreSalary() {
        return compreSalary;
    }

    public void setCompreSalary(Double compreSalary) {
        this.compreSalary = compreSalary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public Integer getPositionAttrId() {
        return positionAttrId;
    }

    public void setPositionAttrId(Integer positionAttrId) {
        this.positionAttrId = positionAttrId;
    }

    public String getPositionAttrIdStr() {
        if(positionAttrId!=null) {
            if (positionAttrId==1) {
                return "经理";
            } else if (positionAttrId==2) {
                return "主管";
            } else if (positionAttrId==3) {
                return "组长";
            } else if (positionAttrId==4) {
                return "职员";
            }
        }
        return positionAttrIdStr;
    }

    public void setPositionAttrIdStr(String positionAttrIdStr) {
        this.positionAttrIdStr = positionAttrIdStr;
    }

    public String getMarriagedStr() {
        if (marriaged != null) {
            if (this.marriaged == 1) {
                return "已";
            } else if (this.marriaged == 0) {
                return "未";
            } else if (this.marriaged == 2) {
                return "离";
            }
        }
        return marriagedStr;
    }

    public void setMarriagedStr(String marriagedStr) {
        this.marriagedStr = marriagedStr;
    }

    public String getEducationLeStr() {
        if (educationLe != null) {
            if (this.getEducationLe() == 1) {
                return "小学";
            } else if (this.getEducationLe() == 2) {
                return "初中";
            } else if (this.getEducationLe() == 3) {
                return "高中";
            } else if (this.getEducationLe() == 4) {
                return "技校";
            } else if (this.getEducationLe() == 5) {
                return "中技";
            } else if (this.getEducationLe() == 6) {
                return "中专";
            } else if (this.getEducationLe() == 7) {
                return "大专";
            } else if (this.getEducationLe() == 8) {
                return "本科";
            } else if (this.getEducationLe() == 9) {
                return "研究生";
            } else if (this.getEducationLe() == 10) {
                return "硕士";
            } else if (this.getEducationLe() == 11) {
                return "博士";
            } else if (this.getEducationLe() == 12) {
                return "MBA";
            }
        }

        return educationLeStr;
    }

    public void setEducationLeStr(String educationLeStr) {
        this.educationLeStr = educationLeStr;
    }

    public String getScreAgreementStr() {
        if (screAgreement != null) {
            if (this.screAgreement == 1) {
                return "是";
            } else if (this.screAgreement == 0) {
                return "否";
            }
        }
        return screAgreementStr;
    }

    public void setScreAgreementStr(String screAgreementStr) {
        this.screAgreementStr = screAgreementStr;
    }

    public String getHealthCertiStr() {
        if (this.getHealthCerti() != null) {
            if (this.getHealthCerti() == 0) {
                return "无";
            } else if (this.getHealthCerti() == 1) {
                return "健康证";
            } else if (this.getHealthCerti() == 2) {
                return "体检单";
            } else if (this.getHealthCerti() == 3) {
                return "职业病体检";
            }
        }
        return healthCertiStr;
    }

    public void setHealthCertiStr(String healthCertiStr) {
        this.healthCertiStr = healthCertiStr;
    }

    public String getSateListAndLeaCertiStr() {
        if (sateListAndLeaCerti != null) {
            if (this.getSateListAndLeaCerti() == 1) {
                return "离职和社保";
            } else if (this.getSateListAndLeaCerti() == 0) {
                return "无";
            } else if (this.getSateListAndLeaCerti() == 2) {
                return "社保";
            } else if (this.getSateListAndLeaCerti() == 3) {
                return "离职";
            }
        }
        return sateListAndLeaCertiStr;
    }

    public void setSateListAndLeaCertiStr(String sateListAndLeaCertiStr) {
        this.sateListAndLeaCertiStr = sateListAndLeaCertiStr;
    }

    public String getOtherCertiStr() {
        if (otherCerti != null) {
            if (this.otherCerti == 1) {
                return "毕业证";
            } else if (this.otherCerti == 2) {
                return "电工证";
            } else if (this.otherCerti == 3) {
                return "焊工证";
            } else if (this.otherCerti == 4) {
                return "结婚证";
            }
        }
        return otherCertiStr;
    }

    public void setOtherCertiStr(String otherCertiStr) {
        this.otherCertiStr = otherCertiStr;
    }

    public String getBirthDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.birthDay != null)
            return formatter.format(this.birthDay);
        return this.birthDayStr;
    }

    public void setBirthDayStr(String birthDayStr) {
        this.birthDayStr = birthDayStr;
    }

    public String getConExpDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.conExpDate != null)
            return formatter.format(this.conExpDate);
        return this.conExpDateStr;
    }

    public void setConExpDateStr(String conExpDateStr) {
        this.conExpDateStr = conExpDateStr;
    }

    public String getIncompdateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.incompdate != null)
            return formatter.format(this.incompdate);
        return this.incompdateStr;
    }

    public void setIncompdateStr(String incompdateStr) {
        this.incompdateStr = incompdateStr;
    }

    public Date getConExpDate() {
        return conExpDate;
    }

    public void setConExpDate(Date conExpDate) {
        this.conExpDate = conExpDate;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public Integer getNativePla() {
        return nativePla;
    }

    public void setNativePla(Integer nativePla) {
        this.nativePla = nativePla;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public Date getValiPeriodOfID() {
        return valiPeriodOfID;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getNationStr() {
        return nationStr;
    }

    public void setNationStr(String nationStr) {
        this.nationStr = nationStr;
    }

    public Integer getMarriaged() {
        return marriaged;
    }

    public void setMarriaged(Integer marriaged) {
        this.marriaged = marriaged;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getEducationLe() {
        return educationLe;
    }

    public void setEducationLe(Integer educationLe) {
        this.educationLe = educationLe;
    }

    public Integer getScreAgreement() {
        return screAgreement;
    }

    public void setScreAgreement(Integer screAgreement) {
        this.screAgreement = screAgreement;
    }

    public Integer getHealthCerti() {
        return healthCerti;
    }

    public void setHealthCerti(Integer healthCerti) {
        this.healthCerti = healthCerti;
    }

    public Integer getSateListAndLeaCerti() {
        return sateListAndLeaCerti;
    }

    public void setSateListAndLeaCerti(Integer sateListAndLeaCerti) {
        this.sateListAndLeaCerti = sateListAndLeaCerti;
    }

    public Integer getOtherCerti() {
        return otherCerti;
    }

    public void setOtherCerti(Integer otherCerti) {
        this.otherCerti = otherCerti;
    }

    public List<Integer> getSexIds() {
        return sexIds;
    }

    public void setSexIds(List<Integer> sexIds) {
        this.sexIds = sexIds;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getEndLeaveStr() {
        return endLeaveStr;
    }

    public List<Integer> getNameIds() {
        return nameIds;
    }

    public void setNameIds(List<Integer> nameIds) {
        this.nameIds = nameIds;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public void setEndLeaveStr(String endLeaveStr) {
        this.endLeaveStr = endLeaveStr;
    }

    public String getBeginLeaveStr() {
        return beginLeaveStr;
    }

    public void setBeginLeaveStr(String beginLeaveStr) {
        this.beginLeaveStr = beginLeaveStr;
    }

    public String getDeptIdsstr() {
        return deptIdsstr;
    }

    public void setDeptIdsstr(String deptIdsstr) {
        this.deptIdsstr = deptIdsstr;
    }

    public String getPositionIdsstr() {
        return positionIdsstr;
    }

    public void setPositionIdsstr(String positionIdsstr) {
        this.positionIdsstr = positionIdsstr;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public String getStartIncomDateStr() {
        return startIncomDateStr;
    }

    public void setStartIncomDateStr(String startIncomDateStr) {
        this.startIncomDateStr = startIncomDateStr;
    }

    public String getEndIncomDateStr() {
        return endIncomDateStr;
    }

    public void setEndIncomDateStr(String endIncomDateStr) {
        this.endIncomDateStr = endIncomDateStr;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageTotalNum() {
        if (this.currentPage != 0)
            return (currentPage - 1) * pageSize;
        return 0;
    }

    public void setCurrentPageTotalNum(int currentPageTotalNum) {
        this.currentPageTotalNum = currentPageTotalNum;
    }

    public String getSexStr() {
        if (this.sex != null && this.sex == 1) {
            return "男";
        } else if (this.sex != null && this.sex == 0) {
            return "女";
        }
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public String getIncomdateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this.incompdate != null)
            return formatter.format(this.incompdate);
        return this.incomdateStr;
    }

    public void setIncomdateStr(String incomdateStr) {
        this.incomdateStr = incomdateStr;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Date getIncompdate() {
        return incompdate;
    }

    public void setIncompdate(Date incompdate) {
        this.incompdate = incompdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
