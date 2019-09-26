package com.cosun.cosunp.weixin;

import com.cosun.cosunp.entity.ClockInSetUp;
import com.cosun.cosunp.entity.Leave;
import com.cosun.cosunp.service.IPersonServ;
import com.cosun.cosunp.tool.Constants;
import net.sf.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
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

    private static Logger logger = LogManager.getLogger(WeiXinController.class);

    @Value("${spring.servlet.multipart.location}")
    private String finalDirPath;

    @Autowired
    IPersonServ personServ;

    private static JedisPool pool;
    private static Jedis jedis;

    @ResponseBody
    @RequestMapping(value = "/getMobileLocate")
    public ModelAndView getMobileLocate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("weixin");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/getCamera")
    public ModelAndView getCamera(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("camera");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/getMobileLocateReal")
    public void getMobileLocateReal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String wxMsgXml = IOUtils.toString(request.getInputStream(), "utf-8");
        String url = request.getParameter("wxurl");
        String openId = request.getParameter("bean");
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
            l_data.add(openId);
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

    //

    @ResponseBody
    @RequestMapping(value = "/saveOutPhoto")
    public ModelAndView saveOutPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        String openId = request.getParameter("openId");
        String serverId = request.getParameter("serverId");
        ModelAndView mav = new ModelAndView("success");
        Date date = new Date();
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat todaytime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1 = today.format(date);
        String todaytimeStr = todaytime.format(date);
        String hourStr = todaytimeStr.split(" ")[1];
        Integer hour = Integer.valueOf(hourStr.split(":")[0]);
        String folderName = "weixin/" + dateStr1 + "/";
        ImageUtils.saveImage(jedis.get(Constants.accessToken), finalDirPath + folderName, serverId, serverId + ".jpg");
        OutClockIn outClockIn = new OutClockIn();
        outClockIn.setClockInDateStr(dateStr1);
        outClockIn.setWeixinNo(openId);
        if (hour < 12 && hour >= 6) {//上午
            outClockIn.setAmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId,dateStr1, "amOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav.addObject("flag", "您上午已经摄过像,不能重复摄像!");
                return mav;
            }
        } else if (hour >= 12 && hour <= 18) {//下午
            outClockIn.setPmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId,dateStr1, "pmOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav.addObject("flag", "您下午已经摄过像,不能重复摄像!");
                return mav;
            }
        } else if (hour > 18 && hour <= 24) {//晚上
            outClockIn.setNmOnUrl(folderName + serverId + ".jpg");
            int isPhotoInAlready = personServ.isClockInAlready(openId,dateStr1, "nmOnUrl");
            if (isPhotoInAlready == 0) {
                personServ.saveOrUpdateOutClockInDataUrl(outClockIn);
            } else {
                mav.addObject("flag", "您晚上已经摄过像,不能重复摄像!");
                return mav;
            }
        }
        mav.addObject("flag", "已摄像!");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/punchClock")
    public ModelAndView punchClock(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //22.77200698852539
        //114.3155288696289
        Double latitude = Double.valueOf(request.getParameter("latitude"));
        Double longitude = Double.valueOf(request.getParameter("longitude"));
        String openId = request.getParameter("openId");
        ModelAndView mav = new ModelAndView("success");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        // Map<String, String> map = WeiXinUtil.xmlToMap(request);
        Map<String, String> address = MapUtil.getCityByLonLat(latitude, longitude);
        if (address != null) {
            OutClockIn outClockIn = new OutClockIn();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(date);
            String dateStr1 = format1.format(date);
            String hourStr = dateStr.split(" ")[1];
            String addr = address.get("province") + address.get("city") + address.get("district") +
                    address.get("street");
            Integer hour = Integer.valueOf(hourStr.split(":")[0]);
            outClockIn.setClockInDateStr(dateStr1);
            outClockIn.setWeixinNo(openId);
            if (hour < 12 && hour >= 6) {
                //视为上午打卡
                outClockIn.setClockInDateAMOnStr(dateStr);
                outClockIn.setClockInAddrAMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(openId,dateStr1, "clockInDateAMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    mav.addObject("flag", "您已经打过卡,不能重复打卡!");
                    return mav;
                }
            } else if (hour >= 12 && hour <= 18) {
                //视为下午打卡
                outClockIn.setClockInDatePMOnStr(dateStr);
                outClockIn.setClockInAddrPMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(openId,dateStr1, "clockInDatePMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    mav.addObject("flag", "您已经打过卡,不能重复打卡!");
                    return mav;
                }
            } else if (hour > 18 && hour <= 24) {
                //视为晚上打卡
                outClockIn.setClockInDateNMOnStr(dateStr);
                outClockIn.setClockInAddNMOn(addr);
                int isClockInAlready = personServ.isClockInAlready(openId,dateStr1, "clockInDateNMOn");
                if (isClockInAlready == 0) {
                    personServ.saveOrUpdateOutClockInData(outClockIn);
                } else {
                    mav.addObject("flag", "您已经打过卡,不能重复打卡!");
                    return mav;
                }
            } else {
                mav.addObject("flag", "您打卡的时间不在规定时间段内!");
                return mav;
            }

            mav.addObject("flag", "您打卡成功,打卡地址为:" + address.get("province") + address.get("city") + address.get("district") +
                    address.get("street"));


        } else {
            mav.addObject("flag", "打卡失败，请稍后重试!");
        }
        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/queryLeaveSheet")
    public ModelAndView queryLeaveSheet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("leavesheet");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        String name = personServ.getNameByWeiXinId(accessToken.getOpenId());
        List<Leave> leaveList = personServ.findAllLeaveByWeiXinId(accessToken.getOpenId());
        view.addObject("leaveList", leaveList);
        view.addObject("name", name);
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/outClockInNote")
    public ModelAndView outClockInNote(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("outclockinnote");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        view.addObject("bean", accessToken.getOpenId());
        List<ClockInSetUp> clockInSetUpList = personServ.findAllCLockInSetUp();
        view.addObject("clockInSetUpList", clockInSetUpList);
        return view;
    }


    @ResponseBody
    @RequestMapping(value = "/queryOutClockIn")
    public ModelAndView queryOutClockIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("queryOutClockIn");
        String code = request.getParameter("code");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        AccessToken accessToken = WeiXinUtil.getTheCode(code, jedis);
        List<OutClockIn> outClockInList = personServ.findAllOutClockInByOpenId(accessToken.getOpenId());
        String name = personServ.getNameByWeiXinId(accessToken.getOpenId());
        view.addObject("outClockInList", outClockInList);
        view.addObject("name", name);
        return view;
    }


}
