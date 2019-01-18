package com.cosun.cosunp.service.impl;

import com.cosun.cosunp.entity.UserInfo;
import com.cosun.cosunp.mapper.UserInfoMapper;
import com.cosun.cosunp.service.IUserInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl implements IUserInfoServ {

    @Autowired
    private UserInfoMapper userInfoMapper;

    //@Transactional //添加事物管理
    public UserInfo findUserByUserNameandPassword(String userName, String userPwd) throws Exception {
        return userInfoMapper.findUserByUserNameandPassword(userName,userPwd);
    }
}
