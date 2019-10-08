package com.cosun.cosunp.tool;

import org.apache.el.parser.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author:homey Wong
 * @date:2019/6/29 0029 上午 11:27
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DateUtil {

    /**
     * 计算两个日期之间的间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long startToEnd(Date startDate, Date endDate) {
        String[] startStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate).split("-");
        String[] endStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate).split("-");
        Integer startYear = Integer.parseInt(startStr[0]);
        Integer startMonth = Integer.parseInt(startStr[1]);
        Integer startDay = Integer.parseInt(startStr[2]);
        Integer endYear = Integer.parseInt(endStr[0]);
        Integer endMonth = Integer.parseInt(endStr[1]);
        Integer endDay = Integer.parseInt(endStr[2]);
        LocalDate endLocalDate = LocalDate.of(endYear, endMonth, endDay);
        LocalDate startLocalDate = LocalDate.of(startYear, startMonth, startDay);
        return startLocalDate.until(endLocalDate, ChronoUnit.DAYS);
    }

    public static String getBeforeDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        return df.format(time);
    }

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

    public static List<String> toDatePriodTranstoDays(String fromDate, String endDate) {
        List<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(fromDate);
            Date end_date = sdf.parse(endDate);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start_date);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end_date);
            while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(result);
        for (int a = 0; a < result.size(); a++) {
            System.out.println(result.get(a) + "***");
        }
        return result;
    }

    public static void main(String[] arg) {
        // boolean isWeekEnd = isWeekend("2019-04-26");
        // System.out.println(isWeekEnd ? "周末" : "周日");
        toDatePriodTranstoDays("2019-08-20", "2019-09-08");

    }

}
