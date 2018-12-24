package com.cosun.cosunp.tool;

/**
 * @author:homey Wong
 * @Description:
 * @date:2018/12/22 0022 上午 11:27
 * @Modified By:
 * @Modified-date:2018/12/22 0022 上午 11:27
 */
public class StringUtil {


    /**
      *@author:homey Wong
      *@Date:
      * 查看字符串某个东西出现的次数
      */
    public static int occurrencesNumber(String string,String str) {
        int i=0;
        while(string.indexOf(str)!=-1){
            int a=string.indexOf(str);
            string=string.substring(a+1);
            i++;
        }
        return i;
    }

    /**
      *@author:homey Wong
      *@Date:
      * 截取字符串以，号开始截取
      */
    public static String afterString(String string,String str) {
        int i=0;
        if(string.indexOf(str)!=-1){
            int beginstr =string.indexOf(str);
            int laststr = string.length();
            string=string.substring(beginstr+1,laststr);
        }
        return string;
    }
}
