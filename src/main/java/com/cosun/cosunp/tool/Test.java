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
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/曾红红/21430637/COSUN20190613WW01/000001 (1).exe\":\"E:/myFile/111/000001 (1).exe\",\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/凡钟俊/03063247/COSUN20190108WW26/IntelliJ IDEA 2018.5.zip\":\"E:/myFile/111/IntelliJ IDEA 2018.5.zip\"\r\n}";

    public static void main(String[] arg) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> resMap = JSON.parseObject(json, map.getClass());
        int times = 1;
        for (int index = 0; index < times; index++) {
            DownLoadUtil.batch(resMap);
        }
    }
}
