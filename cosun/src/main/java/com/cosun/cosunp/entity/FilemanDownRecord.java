package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/3/29 0029 上午 11:53
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FilemanDownRecord implements Serializable {

    private static final long serialVersionUID = 8947970944236184109L;

    private Integer id;
    private String fileName;//文件名
    private String ipAddr;//IP地址
    private String ipName;//IP名
    private Date downDate;//下载日期
    private String downDateStr;//下载日期
    private Integer downUid;//下载登录者
    private String downFullName;//下载登录全名
    private Integer fileurlid;//关联URL ID
    private String orderNum;//订单编号
    private String projectName;//项目编码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public Date getDownDate() {
        return downDate;
    }

    public void setDownDate(Date downDate) {
        this.downDate = downDate;
    }

    public String getDownDateStr() {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.downDate);
    }

    public void setDownDateStr(String downDateStr) {
        this.downDateStr = downDateStr;
    }

    public Integer getDownUid() {
        return downUid;
    }

    public void setDownUid(Integer downUid) {
        this.downUid = downUid;
    }

    public String getDownFullName() {
        return downFullName;
    }

    public void setDownFullName(String downFullName) {
        this.downFullName = downFullName;
    }

    public Integer getFileurlid() {
        return fileurlid;
    }

    public void setFileurlid(Integer fileurlid) {
        this.fileurlid = fileurlid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
