package com.cosun.cosunp.weixin;

import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 上午 9:03
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
@WebServlet(urlPatterns = "/weixin/hello")
public class WeiXinController extends HttpServlet {

    public static final String tooken = "homeyhomeyhomey";

    public WeiXinController() {
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
                outPW.write(echostr);
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
        PrintWriter out = response.getWriter();
        System.out.println("******a***");
        try {
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
                System.out.println(message);
            }
            out.print(message);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
