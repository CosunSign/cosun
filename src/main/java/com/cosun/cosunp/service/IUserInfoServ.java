package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.UserInfo;

public interface IUserInfoServ {

    UserInfo findUserByUserNameandPassword(String userName, String userPwd) throws Exception;

    void setNewPasswordByuId(Integer uId,String newPassword) throws Exception;

    String getMobileNumByUserName(String userName) throws Exception;
}


