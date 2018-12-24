package com.cosun.cosunp.service;

import com.cosun.cosunp.entity.UserInfo;

public interface IUserInfoServ {

    UserInfo findUserByUserNameandPassword(String userName, String userPwd);
}
