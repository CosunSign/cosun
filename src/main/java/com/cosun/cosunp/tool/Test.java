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
            "	\"ftp://admin:FL33771@192.168.0.152/1/2018公司宿舍管理制度/2018公司宿舍管理制度.docx\":\"E:/myFile/111/123.docx\",\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/8/2019项目中心设计部员工手册（福田组）/2019项目中心设计部员工手册（福田组）.docx\":\"E:/myFile/111/124.docx\"\r\n}";

    public static void main(String[] arg) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> resMap = JSON.parseObject(json, map.getClass());
        int times = 1;
        for (int index = 0; index < times; index++) {
            DownLoadUtil.batch(resMap);
        }
    }
}
