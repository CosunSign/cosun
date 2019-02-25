package com.cosun.cosunp.tool;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * @author:homey Wong
 * @date:2019/2/15 0015 上午 10:46
 * @Description:文件下载多线程分段  提升下载速度
 * @Modified By:
 * @Modified-date:
 */
public class MutiThreadDownLoadUtil {
        /**
         * 同时下载的线程数
         */
        private int threadCount;
        /**
         * 服务器请求路径
         */
        private String serverPath;
        /**
         * 本地路径
         */
        private String localPath;
        /**
         * 线程计数同步辅助
         */
        private CountDownLatch latch;

        public MutiThreadDownLoadUtil(int threadCount, String serverPath, String localPath, CountDownLatch latch) {
            this.threadCount = threadCount;
            this.serverPath = "file:///".concat(serverPath);
            this.localPath = localPath;
            this.latch = latch;
        }

        public void executeDownLoad() {

            try {
                URL url = new URL(serverPath);
               // URL url = new URL(null, serverPath,new sun.net.www.protocol.https.Handler());;
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                if (code == 200) {
                    //服务器返回的数据的长度，实际上就是文件的长度,单位是字节
                    int length = conn.getContentLength();
                    System.out.println("文件总长度:" + length + "字节(B)");
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    //指定创建的文件的长度
                    raf.setLength(length);
                    raf.close();
                    //分割文件
                    int blockSize = length / threadCount;
                    for (int threadId = 1; threadId <= threadCount; threadId++) {
                        //第一个线程下载的开始位置
                        int startIndex = (threadId - 1) * blockSize;
                        int endIndex = startIndex + blockSize - 1;
                        if (threadId == threadCount) {
                            //最后一个线程下载的长度稍微长一点
                            endIndex = length;
                        }
                        System.out.println("线程" + threadId + "下载:" + startIndex + "字节~" + endIndex + "字节");
                        new DownLoadThread(threadId, startIndex, endIndex).start();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        /**
         * 内部类用于实现下载
         */
        public class DownLoadThread extends Thread {
            /**
             * 线程ID
             */
            private int threadId;
            /**
             * 下载起始位置
             */
            private int startIndex;
            /**
             * 下载结束位置
             */
            private int endIndex;

            public DownLoadThread(int threadId, int startIndex, int endIndex) {
                this.threadId = threadId;
                this.startIndex = startIndex;
                this.endIndex = endIndex;
            }


            @Override
            public void run() {

                try {
                    System.out.println("线程" + threadId + "正在下载...");
                    URL url = new URL(serverPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    //请求服务器下载部分的文件的指定位置
                    conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();

                    System.out.println("线程" + threadId + "请求返回code=" + code);

                    InputStream is = conn.getInputStream();//返回资源
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    //随机写文件的时候从哪个位置开始写
                    raf.seek(startIndex);//定位文件

                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                    }
                    is.close();
                    raf.close();
                    System.out.println("线程" + threadId + "下载完毕");
                    //计数值减一
                    latch.countDown();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
