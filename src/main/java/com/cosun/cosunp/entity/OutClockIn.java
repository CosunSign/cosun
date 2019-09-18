package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:homey Wong
 * @Date: 2019/9/18 0018 下午 2:31
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class OutClockIn implements Serializable {

    private static final long serialVersionUID = -5440710339580920007L;

    private Integer id;
    private String weixinNo;
    private Date clockInDateAMOn;
    private String clockInAddrAMOn;
    private Date clockInDateAMOff;
    private String clockInAddrAMOff;
    private Date clockInDatePMOn;
    private String clockInAddrPMOn;
    private Date clockInDatePMOff;
    private String clockInAddrPMOff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeixinNo() {
        return weixinNo;
    }

    public void setWeixinNo(String weixinNo) {
        this.weixinNo = weixinNo;
    }

    public Date getClockInDateAMOn() {
        return clockInDateAMOn;
    }

    public void setClockInDateAMOn(Date clockInDateAMOn) {
        this.clockInDateAMOn = clockInDateAMOn;
    }

    public String getClockInAddrAMOn() {
        return clockInAddrAMOn;
    }

    public void setClockInAddrAMOn(String clockInAddrAMOn) {
        this.clockInAddrAMOn = clockInAddrAMOn;
    }

    public Date getClockInDateAMOff() {
        return clockInDateAMOff;
    }

    public void setClockInDateAMOff(Date clockInDateAMOff) {
        this.clockInDateAMOff = clockInDateAMOff;
    }

    public String getClockInAddrAMOff() {
        return clockInAddrAMOff;
    }

    public void setClockInAddrAMOff(String clockInAddrAMOff) {
        this.clockInAddrAMOff = clockInAddrAMOff;
    }

    public Date getClockInDatePMOn() {
        return clockInDatePMOn;
    }

    public void setClockInDatePMOn(Date clockInDatePMOn) {
        this.clockInDatePMOn = clockInDatePMOn;
    }

    public String getClockInAddrPMOn() {
        return clockInAddrPMOn;
    }

    public void setClockInAddrPMOn(String clockInAddrPMOn) {
        this.clockInAddrPMOn = clockInAddrPMOn;
    }

    public Date getClockInDatePMOff() {
        return clockInDatePMOff;
    }

    public void setClockInDatePMOff(Date clockInDatePMOff) {
        this.clockInDatePMOff = clockInDatePMOff;
    }

    public String getClockInAddrPMOff() {
        return clockInAddrPMOff;
    }

    public void setClockInAddrPMOff(String clockInAddrPMOff) {
        this.clockInAddrPMOff = clockInAddrPMOff;
    }
}
