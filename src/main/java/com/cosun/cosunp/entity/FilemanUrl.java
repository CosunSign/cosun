package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

public class FilemanUrl implements Serializable {

    private static final long serialVersionUID = -8416020544103530588L;
    private Integer id;
    private Integer fileInfoId; //关联 FileManFileInfo表
    private String userName;
    private String fileName;
    private Date upTime;
    private Integer opRight; //op_right是对文件的操作权限的描述
    private String logur1;//最后的保存地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Integer fileInfoId) {
        this.fileInfoId = fileInfoId;
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

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Integer getOpRight() {
        return opRight;
    }

    public void setOpRight(Integer opRight) {
        this.opRight = opRight;
    }

    public String getLogur1() {
        return logur1;
    }

    public void setLogur1(String logur1) {
        this.logur1 = logur1;
    }
}
