package com.cosun.cosunp.entity;

import java.io.Serializable;

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
    private Integer month;
    private String workDate;
    private String remark;
    private String[] workDates;

    public Integer getId() {
        return id;
    }

    public String[] getWorkDates() {
        return workDates;
    }

    public void setWorkDates(String[] workDates) {
        this.workDates = workDates;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
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
