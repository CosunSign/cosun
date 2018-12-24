package com.cosun.cosunp;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cosun.cosunp")
@MapperScan("com.cosun.cosunp.mapper")
public class CosunpApplication {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(CosunpApplication.class, args);

    }

}

