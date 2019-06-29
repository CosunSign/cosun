package com.cosun.cosunp.tool;

import org.apache.el.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:homey Wong
 * @date:2019/6/29 0029 上午 11:27
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DateUtil {

    public static boolean isWeekend(String dateStr) {
        boolean isWeekend = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWeekend;
    }

    public static void main(String[] arg) {
        boolean isWeekEnd = isWeekend("2019-04-26");
        System.out.println(isWeekEnd?"周末":"周日");

    }

}
