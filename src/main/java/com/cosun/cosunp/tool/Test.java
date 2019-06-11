package com.cosun.cosunp.tool;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:homey Wong
 * @date:2019/6/11 0011 上午 9:57
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Test {


//   static  String  json = "{\r\n" +
//            "	\"E:/ftpserver/8/2018公司宿舍管理制度/2018公司宿舍管理制度.docx\":\"E:/ftpserver/8/2018公司宿舍管理制度/2018公司宿舍管理制度.docx\"}";

    static String json = "{\r\n" +
            "	\"https://code.jquery.com/jquery-3.4.1.js\":\"E:/myFile/111/123.js\",\r\n" +
            "	\"https://code.jquery.com/jquery-migrate-1.4.1.min.js\":\"E:/myFile/111/124.js\",\r\n" +
            "	\"https://code.jquery.com/jquery-migrate-3.0.1.js\":\"E:/myFile/111/125.js\"\r\n" + "}";

    public static void main(String[] arg) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> resMap = JSON.parseObject(json, map.getClass());
        int times = 1;
        for (int index = 0; index < times; index++) {
            DownLoadUtil.batch(resMap);
        }
    }
}
