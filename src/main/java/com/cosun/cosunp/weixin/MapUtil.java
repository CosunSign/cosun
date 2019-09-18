package com.cosun.cosunp.weixin;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author:homey Wong
 * @Date: 2019/9/17 0017 下午 6:49
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MapUtil {
    /**
     * 百度地图请求秘钥
     */
    private static final String KEY = "MiMff2zfRb5PMTSdB5GFeHX4GLGwisda";
    /**
     * 返回值类型
     */
    private static final String OUTPUT = "json";
    /**
     * 根据地名获取经纬度
     */
    private static final String GET_LNG_LAT_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";
    /**
     * 根据经纬度获取地名
     */
    //http://api.map.baidu.com/reverse_geocoding/v3/?ak=ZzGk5eR49FPNwxhXzbVWXjDsGBBXTA6V&output=json&coordtype=wgs84ll&location=22.77200698852539,114.3155288696289
    private static final String GET_ADDRESS_URL = "http://api.map.baidu.com/reverse_geocoding/v3/";

    private static final String coordtype = "wgs84ll";


    /**
     * 根据经纬度获得省市区信息
     *
     * @param lat 经度
     * @return
     */
    public static Map<String, String> getCityByLonLat(double lng, double lat) {
        String location = lng + "," + lat;
        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        params.put("coordtype", coordtype);
        try {
            //拼装url
            String url = joinUrl(params, OUTPUT, KEY, GET_ADDRESS_URL);
            JSONObject result = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(HttpClientUtils.doGet(url)).
                    getString("result")).getString("addressComponent"));
            Map<String, String> area = new HashMap<>();
            area.put("province", result.getString("province"));
            area.put("city", result.getString("city"));
            area.put("district", result.getString("district"));
            area.put("street", result.getString("street"));
            area.put("street_number", result.getString("street_number"));
            return area;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接url字符串
     *
     * @param params
     * @param output
     * @param key
     * @param url
     * @return
     * @throws IOException
     */
    private static String joinUrl(Map<String, String> params, String output, String key, String url) throws IOException {
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(url);

        int index = 0;
        Set<Map.Entry<String, String>> entrys = params.entrySet();
        for (Map.Entry<String, String> param : entrys) {
            // 判断是否是第一个参数
            if (index == 0) {
                baseUrl.append("?");
            } else {
                baseUrl.append("&");
            }
            baseUrl.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf-8"));
            index++;
        }
        baseUrl.append("&output=").append(output).append("&ak=").append(key);

        return baseUrl.toString();
    }

}