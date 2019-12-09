package com.cosun.cosunp.Test;

import com.cosun.cosunp.entity.MonthKQInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {



        ZkemSDK sdk = new ZkemSDK();
        boolean connFlag = sdk.connect("192.168.1.201", 4370);
        if (connFlag) {

            /*
             * Map<String, Object> m = sdk.getUserInfoByNumber("12112");
             * System.out.println(m);
             *
             * sdk.setUserInfo("12112", "绠�娲�", "123123", 0, true);
             *
             * m = sdk.getUserInfoByNumber("12112"); System.out.println(m);
             */
            // 12112

            /*
             * List<Map<String, Object>> l = sdk.getUserInfo(); for(Map<String, Object> one
             * : l) { Set<Entry<String, Object>> s = one.entrySet(); Iterator<Entry<String,
             * Object>> it = s.iterator(); while(it.hasNext()) { Entry<String, Object> e =
             * it.next(); System.out.println(e.getKey() + " : " + e.getValue()); } }
             */

            System.out.println(sdk.readLastestLogData(new Date()));
            System.out.println(sdk.getGeneralLogData().size());
            List<Map<String, Object>> listMap = sdk.getUserInfo();
            for (int i = 0; i < listMap.size(); i++) {
                System.out.println(listMap.get(i));
            }
            System.out.println(sdk.getUserInfoByNumber("2"));
            sdk.disConnect();
        } else {
            System.out.println("杩炴帴澶辫触");
        }
        /*
         * ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
         *
         * boolean result = zkem.invoke("Connect_NET", "192.168.11.205",
         * 4370).getBoolean(); System.out.println(result);
         *
         * result = zkem.invoke("ReadGeneralLogData",1).getBoolean();
         *
         * System.out.println(result);
         *
         * Variant v0 = new Variant(1); Variant dwEnrollNumber = new Variant("",true);
         * Variant dwVerifyMode = new Variant(0,true); Variant dwInOutMode = new
         * Variant(0,true); Variant dwYear = new Variant(0,true); Variant dwMonth = new
         * Variant(0,true); Variant dwDay = new Variant(0,true); Variant dwHour = new
         * Variant(0,true); Variant dwMinute = new Variant(0,true); Variant dwSecond =
         * new Variant(0,true); Variant dwWorkCode = new Variant(0,true);
         *
         * boolean newresult = false; do{ Variant vResult = Dispatch.call(zkem,
         * "SSR_GetGeneralLogData",
         * v0,dwEnrollNumber,dwVerifyMode,dwInOutMode,dwYear,dwMonth,dwDay,dwHour,
         * dwMinute,dwSecond,dwWorkCode); newresult = vResult.getBoolean();
         * System.out.println(newresult);
         * System.out.println(dwEnrollNumber.getStringRef());
         * System.out.println(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" +
         * dwDay.getIntRef() + " " + dwHour.getIntRef() + ":" + dwMinute.getIntRef() +
         * ":" + dwSecond.getIntRef()); System.out.println(); }while(newresult == true);
         * zkem.invoke("Disconnect");
         */
    }

}
