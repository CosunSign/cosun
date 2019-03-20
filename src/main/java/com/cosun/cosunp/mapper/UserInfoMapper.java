package com.cosun.cosunp.mapper;

import com.cosun.cosunp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoMapper {


        @Select("SELECT * FROM userinfo WHERE username = #{userName} and userpwd=#{userPwd}")
        UserInfo findUserByUserNameandPassword(@Param("userName") String userName,@Param("userPwd") String userPwd);

        @Select("SELECT mobilenum FROM userinfo WHERE username = #{userName}")
        String getMobileNumByUserName(String userName);

        @Select("SELECT * FROM userinfo ")
        List<UserInfo> findAllUser();

        @Update("update userinfo set userpwd = #{newPassword} where uid = #{uId} ")
        void setNewPasswordByuId(Integer uId,String newPassword);
}
