package com.cosun.cosunp.weixin;

import com.cosun.cosunp.tool.Constants;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 上午 9:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@WebServlet(urlPatterns = "/weixin/hello")
public class WeiXinServlet extends HttpServlet {


    public static final String tooken = "homeyhomeyhomey";
    private static JedisPool pool;
    private static Jedis jedis;

    public WeiXinServlet() {
        //空构造函数
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("success");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter outPW = null;
        try {
            outPW = response.getWriter();
            if (CheckUtil.checkSignature(signature, timestamp, nonce, tooken)) {
                System.out.println("签名成功!");
                outPW.write(echostr);
            } else {
                System.out.println("签名失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outPW.close();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        System.out.println(jedis.get(Constants.accessToken) + "*****");
        System.out.println(jedis.get(Constants.jsapi_ticket) + "*****");
        try {
            PrintWriter out = response.getWriter();
            Map<String, String> map = WeiXinUtil.xmlToMap(request);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String message = null;
            if ("text".equals(msgType)) {
                InMsgEntity text = new InMsgEntity();
                text.setFromUserName(toUserName); //原来的信息发送者，将变成信息接受者
                text.setToUserName(fromUserName); //原理的接受者，变成发送者
                text.setMsgType("text"); //表示消息的类型是text类型
                text.setCreateTime(new Date().getTime());
                text.setContent("您发送的信息是：" + content);
                message = WeiXinUtil.textMessageToXml(text); //装换成 xml 格式发送给微信解析
            }
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void setRedisValue(AccessToken accessToken) {
        // 初始化Redis连接池
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
        jedis.set(Constants.accessToken, accessToken.getAccessToken());
        jedis.set(Constants.expiresin, accessToken.getExpiresin() + "");
        jedis.set(Constants.jsapi_ticket, accessToken.getJsapi_ticket());
    }

}
