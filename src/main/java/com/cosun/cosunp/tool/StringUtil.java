package com.cosun.cosunp.tool;

import com.cosun.cosunp.weixin.OutClockIn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/22 0022 上午 11:27
 * @Modified By:
 * @Modified-date:2018/12/22 0022 上午 11:27
 */
public class StringUtil {


    public static String increateFinishiNoByOrldFinishiNo(String oldnewestProdNo, String shortEngName) throws Exception {
        //C190815ZT01
        if (oldnewestProdNo != null) {
            String newestOrder;
            String beforStr = oldnewestProdNo.substring(0, 9);
            String afterNum = oldnewestProdNo.substring(9, oldnewestProdNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            //COSUN20190108WW01
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            dateStr = dateStr.substring(2, dateStr.length());
            String orderName = "C" + dateStr + shortEngName + "01";
            return orderName;
        }
    }


    public static String onlyTimeStr(OutClockIn outClockIn) throws Exception {
        StringBuilder sb = new StringBuilder();
        //2019-10-09 11:56:29
        if (outClockIn != null) {
            if (outClockIn.getClockInDateAMOnStr() != null) {
                sb.append(outClockIn.getClockInDateAMOnStr().split(" ")).append(" ");
            }
            if (outClockIn.getClockInDatePMOnStr() != null) {
                sb.append(outClockIn.getClockInDatePMOnStr().split(" ")).append(" ");
            }
            if (outClockIn.getClockInDateNMOnStr() != null) {
                sb.append(outClockIn.getClockInDatePMOnStr().split(" "));
            }
        }
        return "";
    }


    public static String increateOrderByOlderOrderNo(String oldNewestNo, String shortEngName) throws Exception {
        //COSUN2019XXXXZTXX
        if (oldNewestNo != null) {
            String newestOrder;
            String beforStr = oldNewestNo.substring(0, 15);
            String afterNum = oldNewestNo.substring(15, oldNewestNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            //COSUN20190108WW01
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            String orderName = "COSUN" + dateStr + shortEngName + "01";
            return orderName;
        }
    }

    public static String increateProdNoByOlderProductNo(String oldNewestNo, String shortEngName) throws Exception {
        //C190822HW01
        if (oldNewestNo != null) {
            String newestOrder;
            String beforStr = oldNewestNo.substring(0, 9);
            String afterNum = oldNewestNo.substring(9, oldNewestNo.length());
            Integer num = Integer.valueOf(afterNum);
            num++;
            String afterNewNum;
            if (num < 10) {
                afterNewNum = "0" + num;
            } else {
                afterNewNum = num.toString();
            }
            newestOrder = beforStr + afterNewNum;
            return newestOrder;
        } else {
            //C190822HW01
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
            String dateStr = sDateFormat.format(new Date());
            dateStr = dateStr.substring(2, dateStr.length());
            String orderName = "C" + dateStr + shortEngName + "01";
            return orderName;
        }
    }

    /**
     * 功能描述:业务员名单暂放JAVA里，日后再建员工数据库
     *
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
        if (index >= 1) {
            return str.substring(0, index + 1);
        }
        return null;
    }

    public static String subMyString1(String str, String a) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg
        int index = str.lastIndexOf(a);
        if (index >= 1) {
            return str.substring(0, index);
        }
        return null;
    }


    public static String subAfterString(String str, String a) {
        //F:\1000005\201901\zhongyuan\COSUN20190108WW03\52401367\小猫 - 副本.jpg 取小猫文件名
        if (str.contains(a)) {
            int index = str.lastIndexOf(a);
            if (index >= 1) {
                str = str.substring(index + 1, str.length());
                return str;
            }
        }
        return str;

    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    //查找字符串出现某字符的个数
    public static int searchStrNum(String str, String strRes) {
        int n = 0;//计数器
        int index = 0;//指定字符的长度
        index = str.indexOf(strRes);
        while (index != -1) {
            n++;
            index = str.indexOf(strRes, index + 1);
        }

        return n;
    }


}
