package com.cosun.cosunp.controller;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author:homey Wong
 * @date:2018/12/25 0025 下午 2:04
 * @Description:   用于静态文件获取驱动类
 * @Modified By:
 * @Modified-date:
 */
public class ServletInitializer extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootThymeleafApplication.class);
    }


}
