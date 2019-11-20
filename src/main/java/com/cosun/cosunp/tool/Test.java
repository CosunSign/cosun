package com.cosun.cosunp.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cosun.cosunp.controller.PersonController;
import com.cosun.cosunp.entity.KQBean;
import com.cosun.cosunp.entity.OutPunch;
import com.cosun.cosunp.entity.QYweixinSend;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.weixin.AccessToken;
import com.cosun.cosunp.weixin.InMsgEntity;
import com.cosun.cosunp.weixin.NetWorkHelper;
import com.cosun.cosunp.weixin.WeiXinUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.awt.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @author:homey Wong
 * @date:2019/6/11 0011 上午 9:57
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class Test {

    static String json = "{\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/曾红红/21430637/COSUN20190613WW01/000001 (1).exe\":\"E:/myFile/111/000001 (1).exe\",\r\n" +
            "	\"ftp://admin:FL33771@192.168.0.152/admin/201906/凡钟俊/03063247/COSUN20190108WW26/IntelliJ IDEA 2018.5.zip\":\"E:/myFile/111/IntelliJ IDEA 2018.5.zip\"\r\n}";

    private static JedisPool pool;
    private static Jedis jedis;

    public static void main(String[] arg) {
        try {
            new PersonController().getKQBean();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            File file = new File("E:\\WAMP\\wamp\\www\\cosunp\\src\\main\\resources\\templates");
//            System.out.println(getProjectFileNumber(file,"html"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //ASCII、ISO-8859-1、GB2312、GBK、UTF-8、UTF-16
//        try {
//            pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
//            jedis = pool.getResource();
//            NetWorkHelper netHelper = new NetWorkHelper();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date start_date = sdf.parse("2019-11-16 00:00:00");
//            Date end_date = sdf.parse("2019-11-16 23:59:59");
//            QYweixinSend text = new QYweixinSend();
//            text.setOpencheckindatatype(2);
//            text.setStarttime(start_date.getTime() / 1000);
//            text.setEndtime(end_date.getTime() / 1000);
//            String Url = String.format("https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=%s", jedis.get(Constants.accessToken));
//            List<String> userList = WeiXinUtil.getAddressBook(jedis.get(Constants.accessToken));
//            userList = userList.subList(300, 334);
//            text.setUseridlist(userList);
//            System.out.println(jedis.get(Constants.accessToken));
//            String result = netHelper.getHttpsResponse2(Url, text, "POST");
//            JSONObject json = JSON.parseObject(result);
//            String checkindata = String.valueOf(json.get("checkindata"));
//            JSONArray checkindataStr = JSON.parseArray(checkindata);
//            List<OutPunch> outPunchList = JSONUtils.toList(checkindataStr, OutPunch.class);
//            for (OutPunch op : outPunchList) {
//                System.out.println(op.getLocation_detail());
//                System.out.println(op.getLocation_title());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    public static int getProjectFileNumber(File file, String endsWith) throws IOException {
        int number = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File subFile : file.listFiles()) {
                    number += getProjectFileNumber(subFile, endsWith);
                }
            } else if (file.isFile() && file.getName().endsWith(endsWith)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (br.readLine() != null) {
                    number += 1;
                }
            } else {
                System.out.println("===" + file.getAbsolutePath());
            }
        }
        return number;
    }

}
