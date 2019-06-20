package com.cosun.cosunp.tool;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.parallel.InputStreamSupplier;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author:homey Wong
 * @date:2019/6/13 上午 10:01
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HighEffiCompressZip {

    private String rootPaths;


    ParallelScatterZipCreator scatterZipCreator = new ParallelScatterZipCreator();
    // ParallelScatterZipCreator api says:
    // 注意这个类不保证写入到输出文件的顺序。需要保持特定顺序的（manifests，文件夹）必须使用这个类的客户类进行处理
    // 通常的做法是 在调用这个类的writeTo方法前把这些东西写入到ZipArchiveOutputStream
    ScatterZipOutputStream dirs = ScatterZipOutputStream
            .fileBased(File.createTempFile("whatever-preffix", ".whatever"));


    public HighEffiCompressZip(String rootPaths) throws IOException {
        this.rootPaths = rootPaths;
    }

    public HighEffiCompressZip() throws IOException {
    }


    public void addEntry(final ZipArchiveEntry zipArchiveEntry, final InputStreamSupplier streamSupplier)
            throws IOException {
        if (zipArchiveEntry.isDirectory() && !zipArchiveEntry.isUnixSymlink()) {
            dirs.addArchiveEntry(ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, streamSupplier));
        } else {
            scatterZipCreator.addArchiveEntry(zipArchiveEntry, streamSupplier);
        }
    }


    public void writeTo(final ZipArchiveOutputStream zipArchiveOutputStream)
            throws IOException, ExecutionException, InterruptedException {
        dirs.writeTo(zipArchiveOutputStream);
        dirs.close();
        scatterZipCreator.writeTo(zipArchiveOutputStream);
    }


    public String getRootPaths() {
        return rootPaths;
    }

    public void setRootPaths(String rootPath) {
        this.rootPaths = rootPaths;
    }


}
