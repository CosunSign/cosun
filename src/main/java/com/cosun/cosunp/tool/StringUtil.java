package com.cosun.cosunp.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/22 0022 上午 11:27
 * @Modified By:
 * @Modified-date:2018/12/22 0022 上午 11:27
 */
public class StringUtil {

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
        return str.substring(0, index + 1);

    }


}
