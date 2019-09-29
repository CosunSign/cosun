package com.cosun.cosunp.tool;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author:homey Wong
 * @date:2019/1/23 0023 下午 4:01
 * @Description:批量下载工具类
 * @Modified By:
 * @Modified-date:
 */
public class IOUtil {
    public static final int THREAD_COUNT = 5;
    private static int filesToBeCompressed = -1;
    private static Lock lock = new ReentrantLock();
    private final static Condition c = lock.newCondition();

    public static int getNumberOfFilesToBeCompressed() {
        return filesToBeCompressed;
    }
    /**
     * 将服务器文件存到压缩包中
     */
    public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                        FileInputStream inStream = new FileInputStream(inputFile);
                        BufferedInputStream bInStream = new BufferedInputStream(inStream);
                        ZipEntry entry = new ZipEntry(inputFile.getName());
                        outputstream.putNextEntry(entry);

                        final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
                        long streamTotal = 0; // 接受流的容量
                        int streamNum = 0; // 流需要分开的数量
                        int leaveByte = 0; // 文件剩下的字符数
                        byte[] inOutbyte; // byte数组接受文件的数据

                        streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
                        streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
                        leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

                        if (streamNum > 0) {
                            for (int j = 0; j < streamNum; ++j) {
                                inOutbyte = new byte[MAX_BYTE];
                                // 读入流,保存在byte数组
                                bInStream.read(inOutbyte, 0, MAX_BYTE);
                                outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
                            }
                        } // 写出剩下的流数据
                        inOutbyte = new byte[leaveByte];
                        bInStream.read(inOutbyte, 0, leaveByte);
                        outputstream.write(inOutbyte);
                        outputstream.closeEntry(); // Closes the current ZIP entry
                        // and positions the stream for
                        // writing the next entry
                        bInStream.close(); // 关闭
                        inStream.close();
                    }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static void handleFile(AtomicInteger totalFiles, Queue<File> pool,
                                   File f) {
        totalFiles.addAndGet(1);
        pool.add(f);
        lockedNotify();
    }

    private static void handleDir(AtomicInteger totalFiles, Queue<File> pool,
                                  File f) {
        File[] files = f.listFiles();
        for (int j = 0; j < files.length; j++) {
            if (!files[j].isDirectory()) {// 不递归处理目录
                handleFile(totalFiles, pool, f);
            }
        }
    }

    private static void lockedNotify() {
        try {
            lock.lock();
            c.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void downloadFile(File file, HttpServletResponse response, boolean isDelete) throws Exception{
        try { // 以流的形式下载文件。

//            //使用多线程分段下载文件 19-02-15  10:43
//            int threadSize = 4;
//            String serverPath = file.getPath();
//            String localPath = file.getName();
//            CountDownLatch latch = new CountDownLatch(threadSize);
//            MutiThreadDownLoadUtil m = new MutiThreadDownLoadUtil(threadSize, serverPath, localPath, latch);
//            long startTime = System.currentTimeMillis();
//            try {
//                m.executeDownLoad();
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            long endTime = System.currentTimeMillis();
//            System.out.println("全部下载结束,共耗时" + (endTime - startTime) / 1000 + "s");


            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            Cookie cookie = new Cookie("ccc", "111");
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24);
            response.addCookie(cookie);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete) {
                file.delete();        //是否将生成的服务器端文件删除
            }
       } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static void downloadFileByCut(File file, boolean isDelete) throws Exception{
        try {
            if (isDelete) {
                file.delete();        //是否将生成的服务器端文件删除
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
