package com.cosun.cosunp.weixin;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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
        Document doc = reader.read(ins);

        //获取根节点
        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();

        return map;
    }

    public static String textMessageToXml(InMsgEntity textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }


}
