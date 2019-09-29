package com.cosun.cosunp.scheduled;

import com.cosun.cosunp.tool.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author:homey Wong
 * @date:2019/6/20 0020 下午 5:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    //3.添加定时任务

    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    @Scheduled(cron = "0 0 23 * * ?")
    private void configureTasks() {
        try {
            System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
            System.err.println(finalDirPath);
            FileUtil.delFolder(finalDirPath + "linshi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
