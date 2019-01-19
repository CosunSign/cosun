package com.cosun.cosunp.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/22 0022 上午 11:27
 * @Modified By:
 * @Modified-date:2018/12/22 0022 上午 11:27
 */
public class StringUtil {

    /**
     * 功能描述:业务员名单暂放JAVA里，日后再建员工数据库
     * @auther: homey Wong
     * @date: 2019/1/16 0016 下午 2:09
     * @param:
     * @return:
     * @describtion
     */
    public static List<String> getAllSalors() {
        List<String> salors = new ArrayList<String>();
        salors.add("邹时雨");
        salors.add("胡锋");
        salors.add("王世珺");
        salors.add("赵彩霞");
        salors.add("亚晓艾");
        salors.add("钟源");
        salors.add("陈小迷");
        salors.add("熊新宇");
        salors.add("潘莉");
        salors.add("连鸿业");
        salors.add("刘彩云");
        salors.add("明松");
        salors.add("周涛");
        salors.add("梁浩");
        salors.add("肖昌升");
        salors.add("高玉昕");
        salors.add("吴亚莹");
        salors.add("何嘉琪");
        salors.add("李彩苹");
        salors.add("龚菁");
        salors.add("李晨");
        salors.add("梁国程");
        salors.add("陈双全");
        salors.add("彭亚明");
        salors.add("陈军");
        return salors;
    }

    public static String formateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(date);
    }


    /**
     * @author:homey Wong
     * @Date: 查看字符串某个东西出现的次数
     */
    public static int occurrencesNumber(String string, String str) {
        int i = 0;
        while (string.indexOf(str) != -1) {
            int a = string.indexOf(str);
            string = string.substring(a + 1);
            i++;
        }
        return i;
    }

    /**
     * @author:homey Wong
     * @Date: 截取字符串以，号开始截取
     */
    public static String afterString(String string, String str) {
        int i = 0;
        if (string.indexOf(str) != -1) {
            int beginstr = string.indexOf(str);
            int laststr = string.length();
            string = string.substring(beginstr + 1, laststr);
        }
        return string;
    }

    /**
     * 功能描述:获取字符串，以某府号开始切割
     *
     * @auther: homey Wong
     * @date: 2019/1/10 0010 下午 2:38
     * @param:
     * @return:
     * @describtion
     */

    public static String subMyString(String str, String a) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        int index = str.lastIndexOf(a);
        if(index>=1) {
            return str.substring(0, index + 1);
        }
        return null;
    }


    public static String subAfterString(String str, String a) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg 取小猫文件名
        if(str.contains("a")) {
            int index = str.lastIndexOf(a);
            if(index>=1) {
                return str.substring(index + 1, str.length());
            }
        }
        return str;

    }


}
