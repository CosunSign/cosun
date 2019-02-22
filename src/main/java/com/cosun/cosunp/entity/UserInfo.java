package com.cosun.cosunp.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {


    private static final long serialVersionUID = -2934846573147845117L;
    private Integer uId;
    private String userName;
    private String userPwd;
    private Integer userActor;//角色
    private Integer type; //type是不同的权限配置
    private Integer useruploadright;//上传权限  0代表没有  默认1代表有

    public Integer getUseruploadright() {
        return useruploadright;
    }

    public void setUseruploadright(Integer useruploadright) {
        this.useruploadright = useruploadright;
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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getUserActor() {
        return userActor;
    }

    public void setUserActor(Integer userActor) {
        this.userActor = userActor;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
