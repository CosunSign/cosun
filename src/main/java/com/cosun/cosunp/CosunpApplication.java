package com.cosun.cosunp;

import com.cosun.cosunp.service.IFileUploadAndDownServ;
import com.cosun.cosunp.weixin.AccessTokenServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import javax.servlet.MultipartConfigElement;

@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.cosun.cosunp.mapper")
@EnableCaching
@ServletComponentScan
public class CosunpApplication extends SpringBootServletInitializer {


    public final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CosunpApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CosunpApplication.class, args);
    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //单个文件最大
//        factory.setMaxFileSize("3000MB");
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("3000MB");
//        return factory.createMultipartConfig();
//    }


    @Bean
    public CommandLineRunner init(final IFileUploadAndDownServ fileUploadAndDownServ) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                fileUploadAndDownServ.deleteAll();
                fileUploadAndDownServ.init();
                new AccessTokenServlet().init();
            }
        };
    }
}

