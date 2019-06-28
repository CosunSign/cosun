package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.Employee;
import com.cosun.cosunp.entity.OutPutWorkData;
import com.cosun.cosunp.entity.SubEmphours;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author:homey Wong
 * @date:2019/1/8 0008 下午 7:27
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class MathUtil {

    /**
     * 获取随机的数值。
     *
     * @param length 长度
     * @return
     */
    public static String getRandom620(Integer length) {
        String result = "";
        Random rand = new Random();
        int n = 20;
        if (null != length && length > 0) {
            n = length;
        }
        boolean[] bool = new boolean[n];
        int randInt = 0;
        for (int i = 0; i < length; i++) {
            do {
                randInt = rand.nextInt(n);

            } while (bool[randInt]);

            bool[randInt] = true;
            result += randInt;
        }
        return result;
    }

    /**
     * MD5 加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getMD5(String str) throws Exception {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            //LoggerUtils.fmtError(MathUtil.class,e, "MD5转换异常！message：%s", e.getMessage());
            throw e;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    public static List<SubEmphours> computeSubEmpHours(List<OutPutWorkData> outPutWorkDataList, List<Employee> employeeList) {
        List<SubEmphours> subEmphoursList = new ArrayList<SubEmphours>();
        double zhengbanWorkHours;//正常出勤工时
        double usualExtWorkHoursl;//平时加班工时
        double weekendWorkHours;//周末加班工时
        double legalPaidLeaveHours;//法定有薪假
        double legalDayWorkHours;//法定节假日加班工时
        double otherpaidLeaveHours;//其它有薪假工时
        String empNo;
        for (Employee ee : employeeList) {
            zhengbanWorkHours = 0.0;
            usualExtWorkHoursl = 0.0;
            weekendWorkHours = 0.0;
            legalPaidLeaveHours = 0.0;
            legalDayWorkHours = 0.0;
            otherpaidLeaveHours = 0.0;
            empNo = ee.getEmpNo();
            for (OutPutWorkData opw : outPutWorkDataList) {
                if(empNo.equals(opw.getEmpNo())) {
                    if(opw.getWorkType()==0 && opw.getIsAonOk().equals("正常")&&opw.getIsAoffOk().equals("正常")) {
                        zhengbanWorkHours += 4;
                    }else if(opw.getWorkType()==0 && opw.getIsPOnOk().equals("正常")&&opw.getIsPOffOk().equals("正常")) {
                        zhengbanWorkHours += 4;
                    }
                }
            }
        }
        return subEmphoursList;
    }
}


