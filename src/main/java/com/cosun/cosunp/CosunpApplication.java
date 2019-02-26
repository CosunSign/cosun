package com.cosun.cosunp;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = "com.cosun.cosunp")
@MapperScan("com.cosun.cosunp.mapper")
@Configuration
public class CosunpApplication {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(CosunpApplication.class, args);

    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("3000MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("3000MB");
        return factory.createMultipartConfig();
    }




}

