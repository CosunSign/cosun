package com.cosun.cosunp.tool;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author:homey Wong
 * @date:2019/6/4 0004 上午 10:14
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WordToPDF {

    // 将word格式的文件转换为pdf格式
    public static void WordToPDF(MultipartFile mf, String pdfName, String descDir) throws IOException {
        // 源文件目录
        File inputFile = new File(descDir + "linshi/" + mf.getOriginalFilename());
//        inputFile.
//        if (!inputFile.exists()) {
//            System.out.println("源文件不存在！");
//            return;
//        }
        FileUtils.copyInputStreamToFile(mf.getInputStream(), inputFile);

        // 输出文件目录cc
        File outputFile = new File(pdfName);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }


        // 调用openoffice服务线程
        /** 我把openOffice下载到了 C:/Program Files (x86)/下  ,下面的写法自己修改编辑就可以**/
       // String command = "/opt/openoffice4/program/soffice --headless --accept=\"socket,host=0.0.0.0,port=8100;urp;\" --nofirststartwizard &";
       // String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
        //Process p = Runtime.getRuntime().exec(command);

        // 连接openoffi ce服务
         OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0", 8100);
        //OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
        connection.connect();

        // 转换
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile , outputFile);

        // 关闭连接
        connection.disconnect();

        // 关闭进程
        //p.destroy();
    }


//    public static void main(String[] args) {
//        String start = "C:\\Users\\Administrator\\Desktop\\employee.xls";
//        String over = "C:\\Users\\Administrator\\Desktop\\okok.pdf";
//        try {
//            //WordToPDF(null,null, null);
//            throw new ConnectException();
//        } catch (ConnectException e) {
//            System.out.println();
//        }
//    }


}
