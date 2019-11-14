package com.cosun.cosunp.weixin;

import com.alibaba.fastjson.JSON;
import com.aspose.cad.internal.bouncycastle.crypto.engines.AESEngine;
import com.cosun.cosunp.entity.QYweixinSend;
import com.cosun.cosunp.entity.WeiXinUsrId;
import com.cosun.cosunp.tool.Constants;
import com.cosun.cosunp.tool.JSONUtils;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:homey Wong
 * @Date: 2019/9/11 0011 下午 2:13
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WeiXinUtil {


    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException,
            DocumentException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        if (ins != null) {
            Document doc = reader.read(ins);

            //获取根节点
            Element root = doc.getRootElement();

            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            ins.close();

            return map;
        } else {
            return null;
        }
    }

    public static AccessToken getTheCode(String code, Jedis jedis) {
        Map<String, String> authInfo = new HashMap<>();
        AccessToken at = new AccessToken();
        String openId = "";
        if (code != null) {
            // 调用根据用户的code得到需要的授权信息
            authInfo = getAuthInfo(code, jedis);
            //获取到openId
            openId = authInfo.get("Openid");
        }
        // 获取基础刷新的接口访问凭证（目前还没明白为什么用authInfo.get("AccessToken");这里面的access_token就不行）
        String accessToken = jedis.get(Constants.accessToken);
        //获取到微信用户的信息
        at.setOpenId(openId);
        return at;
    }


    public static Map<String, String> oauth2GetOpenid(String code, Jedis jedis) {
        //自己的配置appid（公众号进行查阅）
        String appid = WeiXinConfig.appid;
        //自己的配置APPSECRET;（公众号进行查阅）
        String appsecret = WeiXinConfig.secret;
        //拼接用户授权接口信息
        String requestUrl = ProjectConst.GET_WEBAUTH_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        //存储获取到的授权字段信息
        Map<String, String> result = new HashMap<String, String>();
        try {
            URL urlGet = new URL(requestUrl);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject OpenidJSONO = JSONObject.fromObject(message);
            //OpenidJSONO可以得到的内容：access_token expires_in  refresh_token openid scope
            String Openid = String.valueOf(OpenidJSONO.get("openid"));
            String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
            //用户保存的作用域
            String Scope = String.valueOf(OpenidJSONO.get("scope"));
            String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));
            result.put("Openid", Openid);
            result.put("AccessToken", AccessToken);
            result.put("scope", Scope);
            result.put("refresh_token", refresh_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Map<String, String> getAuthInfo(String code, Jedis jedis) {
        //进行授权验证，获取到OpenID字段等信息
        Map<String, String> result = oauth2GetOpenid(code, jedis);
        // 从这里可以得到用户openid
        String openId = result.get("Openid");
        return result;
    }


    public static String textMessageToXml(InMsgEntity textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static String textMessageToXml2(QYweixinSend textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

//    public String CODE_TO_USERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID";
//
//
//    /**
//     * 根据code获取成员信息
//     *
//     * @param access_token 调用接口凭证
//     * @param code         通过员工授权获取到的code，每次员工授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
//     * @param agentid      跳转链接时所在的企业应用ID 管理员须拥有agent的使用权限；agentid必须和跳转链接时所在的企业应用ID相同
//     */
//    public String getUserID(String access_token, String code, String agentid) {
//        String UserId = "";
//        CODE_TO_USERINFO = CODE_TO_USERINFO.replace("ACCESS_TOKEN", access_token).replace("CODE", code).replace("AGENTID", agentid);
//        JSONObject jsonobject = httpRequest(CODE_TO_USERINFO, "GET", null);
//        if (null != jsonobject) {
//            UserId = jsonobject.getString("UserId");
//            if (!"".equals(UserId)) {
//                System.out.println("获取信息成功，o(∩_∩)o ————UserID:" + UserId);
//            } else {
//                int errorrcode = jsonobject.getInt("errcode");
//                String errmsg = jsonobject.getString("errmsg");
//                String error = "错误码：" + errorrcode + "————" + "错误信息：" + errmsg;
//                System.out.println(error);
//            }
//        } else {
//            System.out.println("获取授权失败了");
//        }
//        return UserId;
//    }

    public static boolean isChinese(String str) {
        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result = true;
            }
        }
        return result;
    }


    //获取ticket
    public static List<String> getAddressBook(String access_token) {
        String ticket = null;
        List<String> userList = new ArrayList<>();
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=" + access_token + "&department_id=1&fetch_child=1";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            String message = IOUtils.toString(is);
            JSONObject demoJson = JSONObject.fromObject(message);
            ticket = demoJson.getString("userlist");
            List<WeiXinUsrId> wechatUsers = JSONUtils.toList(ticket, WeiXinUsrId.class);
            for (WeiXinUsrId wx : wechatUsers) {
                userList.add(wx.getUserid());
            }
            //https://blog.csdn.net/m0_37907797/article/details/102618796
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
         }
        return userList;
    }

    //获取ticket
    public static String getTicket(String access_token) {
        String ticket = null;
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串：" + demoJson);
            ticket = demoJson.getString("ticket");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticket;
    }

    //获取access_token
    public static String getAccessToken(String appid, String secret) {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + secret;
        //这个url链接地址和参数皆不能变
        String requestUrl = "";
        String oppid = "";
        JSONObject oppidObj = null;
        String openid = "";
        String requestUrl2 = "";
        String userInfoStr = "";
        JSONObject wxUserInfo = null;
        try {
            //获取code后，请求以下链接获取access_token
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            System.out.println("JSON字符串：" + demoJson);
            access_token = demoJson.getString("access_token");
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }


}
