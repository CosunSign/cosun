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
            "	\"http://www.xxx.com/111/123.mp4\":\"E:/myFile/111/123.mp4\",\r\n" +
            "	\"http://www.xxx.com/111/124.mp4\":\"E:/myFile/111/124.mp4\",\r\n" +
            "	\"http://www.xxx.com/111/125.mp4\":\"E:/myFile/111/125.mp4\"\r\n" + "}";

    public static void main(String[] arg) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> resMap = JSON.parseObject(json, map.getClass());
        int times = 1;
        for (int index = 0; index < times; index++) {
            DownLoadUtil.batch(resMap);
        }
    }
}
