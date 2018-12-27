package com.cosun.cosunp.entity;

import java.io.Serializable;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/21 0021 上午 10:36
 * @Modified By:
 * @Modified-date:2018/12/21 0021 上午 10:36
 */
public class DownloadView implements Serializable {

    private static final long serialVersionUID = 8178603164200909341L;
    private Integer id;
    private Integer uId;
    private String userName;
    private String password;
    private String creator;
    private String lastUpdator;
    private String fileName;
    private String salor;//业务员
    private String orderNo;//订单编号
    private String projectName;//项目名称
    private String lastUpdateTime;
    private String createTime;
    private Integer totalUpdateNum;
    private String opRight; //操作权限
    private String urlAddr;
    private String seePrivilege; //查看权限  1
    private String updatePrivilege; //修改权限  2
    private String cancelPrivilege; //取消权限  3
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeePrivilege() {
        if(opRight!=null && opRight.trim().length()>0 && opRight.contains("1")) {
            return "1";
        }
        return seePrivilege;
    }

    public void setSeePrivilege(String seePrivilege) {
        this.seePrivilege = seePrivilege;
    }

    public String getUpdatePrivilege() {
        if (opRight != null && opRight.trim().length() > 0 && opRight.contains("2")) {
            return "2";
        }
        return updatePrivilege;

    }

    public void setUpdatePrivilege(String updatePrivilege) {
        this.updatePrivilege = updatePrivilege;
    }

    public String getCancelPrivilege() {
        if(opRight!=null && opRight.trim().length()>0 && opRight.contains("3")) {
            return "3";
        }
        return cancelPrivilege;
    }

    public void setCancelPrivilege(String cancelPrivilege) {
        this.cancelPrivilege = cancelPrivilege;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastUpdator() {
        return lastUpdator;
    }

    public void setLastUpdator(String lastUpdator) {
        this.lastUpdator = lastUpdator;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSalor() {
        return salor;
    }

    public void setSalor(String salor) {
        this.salor = salor;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getTotalUpdateNum() {
        return totalUpdateNum;
    }

    public void setTotalUpdateNum(Integer totalUpdateNum) {
        this.totalUpdateNum = totalUpdateNum;
    }

    public String getOpRight() {
        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void setUrlAddr(String urlAddr) {
        this.urlAddr = urlAddr;
    }
}
