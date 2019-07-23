package com.cosun.cosunp.tool;

import com.cosun.cosunp.entity.Extension;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/4/25 0025 下午 3:59
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ReadTextUtil {


    /**
     * 读取TXT文本内容
     *
     * @param
     */
    public List<Extension> readTxtUtil(String configpath) throws Exception {
        List<Extension> list = new ArrayList<Extension>();
        InputStream is = null;
        try {
            try {
                //charset = detector.detectCodepage(file.toURI().toURL());

                //查找指定资源的URL，其中res.txt仍然开始的bin目录下
                File file = new File(configpath);
                is = new FileInputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String lineTxt = null;
            String args = null;
            Extension sargs = null;
            while ((lineTxt = br.readLine()) != null) {
                sargs = new Extension();
                if (lineTxt.trim().length() > 0) {
                    args = deleteString(lineTxt.trim(),'.');
                    sargs.setExtension(args);
                    list.add(sargs);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("文件读取错误!");
            throw e;
        }
        return list;
    }

    public static String deleteString(String str, char delChar) {
        String delStr = "";
        char[] Bytes = str.toCharArray();
        int iSize = Bytes.length;
        for (int i = Bytes.length - 1; i >= 0; i--) {
            if (Bytes[i] == delChar) {
                for (int j = i; j < iSize - 1; j++) {
                    Bytes[j] = Bytes[j + 1];
                }
                iSize--;
            }
        }
        for (int i = 0; i < iSize; i++) {
            delStr += Bytes[i];
        }
        return delStr;
    }


}
