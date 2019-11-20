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
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                if (code == 200) {
                    int length = conn.getContentLength();
                    System.out.println("文件总长度:" + length + "字节(B)");
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    raf.setLength(length);
                    raf.close();
                    int blockSize = length / threadCount;
                    for (int threadId = 1; threadId <= threadCount; threadId++) {
                        int startIndex = (threadId - 1) * blockSize;
                        int endIndex = startIndex + blockSize - 1;
                        if (threadId == threadCount) {
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
                    conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();

                    System.out.println("线程" + threadId + "请求返回code=" + code);

                    InputStream is = conn.getInputStream();
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    raf.seek(startIndex);

                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                    }
                    is.close();
                    raf.close();
                    System.out.println("线程" + threadId + "下载完毕");
                    latch.countDown();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

