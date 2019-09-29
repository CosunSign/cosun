package com.cosun.cosunp.adapter;

import com.cosun.cosunp.interceptor.UserSecurityInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.Configuration;

/**
 * @author:homey Wong
 * @date:2019/1/2 0002 下午 3:22
 * @Description: 静态资 源处理器（static）
 * @Modified By:
 * @Modified-date:
 */
@Configuration
public class MyWebAppConfigurer extends  WebMvcConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(UserSecurityInterceptor.class);

    /**
     * 资源处理器
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { logger.info("addResourceHandlers");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
