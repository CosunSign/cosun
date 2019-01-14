package com.cosun.cosunp;
import com.cosun.cosunp.datasource.DruidConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

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
        factory.setMaxFileSize("500MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("500MB");
        return factory.createMultipartConfig();
    }




}

