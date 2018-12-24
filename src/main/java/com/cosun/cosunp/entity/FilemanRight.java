package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

public class FilemanRight implements Serializable {


    private static final long serialVersionUID = 4824598905346901899L;
    private Integer id;
    private Integer fileInfoId;
    private Integer uId;
    private String userName;
    private String fileName;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;
    private Integer opRight;//op_right是对文件的操作权限的描述


    public Integer getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Integer fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOpRight() {
        return opRight;
    }

    public void setOpRight(Integer opRight) {
        this.opRight = opRight;
    }
}
