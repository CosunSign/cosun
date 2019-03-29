package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/3/29 0029 下午 1:49
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ShowUpdateDownRecord implements Serializable {

    private static final long serialVersionUID = -7918546692539525609L;

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
    private Integer updateUid;//更新UID
    private String updateFullName;//更新全名
    private String updateReason;//更新理油
    private Date updateDate;//更新时间
    private String updateDateStr;//更新时间
    private Integer typeK;//0代表更新   1代表下载

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
        if(this.downDate!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(this.downDate);
        }
        return "";
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

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    public String getUpdateFullName() {
        return updateFullName;
    }

    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDateStr() {
        if(this.updateDate!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(this.updateDate);
        }
        return "";
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public Integer getTypeK() {
        return typeK;
    }

    public void setTypeK(Integer typeK) {
        this.typeK = typeK;
    }
}
