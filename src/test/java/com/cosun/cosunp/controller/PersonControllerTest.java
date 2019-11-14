package com.cosun.cosunp.controller;

import com.cosun.cosunp.entity.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        UserInfo user = new UserInfo();
        user.setUserName("root");
        user.setUserPwd("root");
        session.setAttribute("userInfo", user); //拦截器那边会判断用户是否登录，所以这里注入一个用户
    }

    @Test
    public void deleteLearn() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/person/deleteLianBanDateToMysql")
                .param("id", "18"));
    }

}