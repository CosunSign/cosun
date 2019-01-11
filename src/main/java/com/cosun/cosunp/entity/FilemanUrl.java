package com.cosun.cosunp.entity;

import java.io.Serializable;
import java.util.Date;

public class FilemanUrl implements Serializable {

    private static final long serialVersionUID = -8416020544103530588L;
    private Integer id;
    private Integer fileInfoId; //关联 FileManFileInfo表
    private String userName;
    private String orginName;//单个文件原始文件名
    private Date upTime;
    private String opRight; //op_right是对文件的操作权限的描述
    private String logur1;//最后的保存地址
    private Integer singleFileUpdateNum;//单个文件更新次数
    private String modifyReason;//单次文件更新原因
    public Integer getSingleFileUpdateNum() {
        return singleFileUpdateNum;
    }

    public void setSingleFileUpdateNum(Integer singleFileUpdateNum) {
        this.singleFileUpdateNum = singleFileUpdateNum;
    }

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

    public String getOrginName() {

        return orginName;
    }

    public void setOrginName(String orginName) {
        this.orginName = orginName;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public String getOpRight() {

        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getLogur1() {
        return logur1;
    }

    public void setLogur1(String logur1) {
        this.logur1 = logur1;
    }
}
