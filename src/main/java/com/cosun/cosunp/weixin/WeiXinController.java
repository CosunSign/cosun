package com.cosun.cosunp.weixin;

import com.cosun.cosunp.tool.Constants;
import fr.opensagres.xdocreport.document.json.JSONObject;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2019/9/12  下午 5:10
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {

    private static JedisPool pool;
    private static Jedis jedis;

    @ResponseBody
    @RequestMapping(value = "/getMobileLocate")
    public ModelAndView getMobileLocate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("weixin");
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/getMobileLocateReal")
    public void getMobileLocateReal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = request.getParameter("wxurl");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        try {
            String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
            System.out.println("accessToken:" + jedis.get(Constants.accessToken) + "\njsapi_ticket:" + jedis.get(Constants.jsapi_ticket) + "\n时间戳：" + timestamp + "\n随机字符串：" + noncestr);
            String str = "jsapi_ticket=" + jedis.get(Constants.jsapi_ticket) + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
            //6、将字符串进行sha1加密
            String signature = CheckUtil.getSha1(str);
            System.out.println("参数：" + str + "\n签名：" + signature);
            List l_data = new ArrayList();
            l_data.add(timestamp);
            l_data.add(noncestr);
            l_data.add(signature);
            l_data.add(url);
            l_data.add(WeiXinConfig.appid);
            JSONArray l_jsonarrary = JSONArray.fromObject(l_data);
            //json转的字符串值
            String l_jsonstring = l_jsonarrary.toString();
            response.getWriter().print(l_jsonstring);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/punchClock")
    public ModelAndView punchClock(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //22.77200698852539
        //114.3155288696289
        Double latitude = Double.valueOf(request.getParameter("latitude"));
        Double longitude = Double.valueOf(request.getParameter("longitude"));
        ModelAndView mav = new ModelAndView("success");
        Map<String, String> address = MapUtil.getCityByLonLat(latitude, longitude);
        if (address != null) {
            mav.addObject("flag", "您打卡成功,打卡地址为:" + address.get("province") + address.get("city") + address.get("district") +
                    address.get("street"));
        } else {
            mav.addObject("flag", "打卡失败，请稍后重试!");
        }
        return mav;
    }


}
