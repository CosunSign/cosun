package com.cosun.cosunp.tool;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.io.input.NullInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * @author:homey Wong
 * @date:2019/6/13 0013 上午 10:02
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HighEffiCompressZipTest {

    public static void main(String[] arg) throws Exception {
        long begin = System.currentTimeMillis();
        final File result = new File("E:/myFile/test.zip");
        List<String> urls = new ArrayList<String>();
        urls.add("C:\\Users\\Administrator\\Desktop\\test\\000001 (1).exe");
        urls.add("C:\\Users\\Administrator\\Desktop\\test\\IntelliJ IDEA 2018.5.zip");
         new HighEffiCompressZipTest().createZipFile(urls, result);
        long end = System.currentTimeMillis();
        System.out.println("用时：" + (end - begin) + " ms");
    }


    class CustomInputStreamSupplier implements InputStreamSupplier {
        private File currentFile;


        public CustomInputStreamSupplier(File currentFile) {
            this.currentFile = currentFile;
        }


        @Override
        public InputStream get() {
            try {
                // InputStreamSupplier api says:
                // 返回值：输入流。永远不能为Null,但可以是一个空的流
                return currentFile.isDirectory() ? new NullInputStream(0) : new FileInputStream(currentFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void addEntry(String entryName, File currentFile, HighEffiCompressZip highEffiCompressZip) throws IOException {
        ZipArchiveEntry archiveEntry = new ZipArchiveEntry(entryName);
        archiveEntry.setMethod(ZipEntry.DEFLATED);
        final InputStreamSupplier supp = new CustomInputStreamSupplier(currentFile);
        highEffiCompressZip.addEntry(archiveEntry, supp);
    }


    private void compressCurrentDirectory(File dir, HighEffiCompressZip highEffiCompressZip) throws IOException {
        if (dir == null) {
            throw new IOException("源路径不能为空！");
        }
        String relativePath = "";
        if (dir.isFile()) {
            relativePath = dir.getName();
            addEntry(relativePath, dir, highEffiCompressZip);
            return;
        }


        // 空文件夹
        if (dir.listFiles().length == 0) {
            relativePath = dir.getAbsolutePath().replace(highEffiCompressZip.getRootPath(), "");
            addEntry(relativePath + File.separator, dir, highEffiCompressZip);
            return;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                compressCurrentDirectory(f, highEffiCompressZip);
            } else {
                relativePath = f.getParent().replace(highEffiCompressZip.getRootPath(), "");
                addEntry(relativePath + File.separator + f.getName(), f, highEffiCompressZip);
            }
        }
    }


    private void createZipFile(final List<String> rootPath, final File result) throws Exception {
        File dstFolder = new File(result.getParent());
        if (!dstFolder.isDirectory()) {
            dstFolder.mkdirs();
        }
        for(String url : rootPath) {
            File rootDir = new File(url);
            final HighEffiCompressZip scatterSample = new HighEffiCompressZip(rootDir.getAbsolutePath());
            compressCurrentDirectory(rootDir, scatterSample);
            final ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(result);
            scatterSample.writeTo(zipArchiveOutputStream);
            zipArchiveOutputStream.close();
        }
    }


}
