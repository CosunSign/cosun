package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/14 0014 上午 11:30
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WorkDate implements Serializable {

    private static final long serialVersionUID = 5202823010119902688L;

    private Integer id;
    private String month;
    private String workDate;
    private String remark;
    private String positionLevel;
    private String[] workDates;
    private String[] workDatess;
    private Integer type;//0代表正班  1代表周末加班  2代表法定带薪假
    private List<String> positionLevels;

    public List<String> getPositionLevels() {
        return positionLevels;
    }

    public void setPositionLevels(List<String> positionLevels) {
        this.positionLevels = positionLevels;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String[] getWorkDatess() {
        if (workDate != null) {
            return this.workDate.split(",");
        }
        return null;
    }

    public void setWorkDatess(String[] workDatess) {
        this.workDatess = workDatess;
    }

    public Integer getId() {
        return id;
    }

    public String[] getWorkDates() {
        return workDates;
    }

    public void setWorkDates(String[] workDates) {
        this.workDates = workDates;
    }

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
