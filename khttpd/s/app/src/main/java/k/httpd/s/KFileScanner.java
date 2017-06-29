package k.httpd.s;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import k.httpd.s.cons.Config;
import k.httpd.s.model.FileInfoModel;
import k.httpd.s.model.FileInfoModels;

/**
 * @author :key.guan
 * @package :k.httpd.s
 * @date : 2017/6/18
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public final class KFileScanner {
    private static final String TAG = "KFileScanner";
    public static String DATA_ROOT = "/mList/media/0/";
    public static String FLASH_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// 
    // Memory的挂载目录， "/sdcard/"，mnt/internal_sd
    public static String SDCARD_ROOT = "/mnt/external_sd/";// main
    public static String SDCARD_ROOT1 = "/mnt/external_sd1/";// 第二张sd卡
    public static String USB_ROOT = "/mnt/usb_storage/USB_DISK2/udisk0/";
    public static String USB_ROOT1 = "/mnt/usb_storage/USB_DISK2/udisk0(1)/";
    public static boolean mIsSupportRecursion = true;//是否支持递归扫描

    private static final String[] DIRS = {DATA_ROOT, FLASH_ROOT, SDCARD_ROOT, SDCARD_ROOT1, USB_ROOT, USB_ROOT1};
    private static final String[] IMAGE_EXT = {".png", ".jpg",".bmp"};//不区分大小写，其他媒体格式？比较的时候，更改
    private static final String[] VIDEO_EXT = {".mov",".mp4"};
    private static final String[] BOTH_EXT = {".png", ".jpg", ".mov", ".mp4"};
    public static String findExtType(final String path){
        String ext = path.substring(path.lastIndexOf('.'),path.length());
        for (String str : IMAGE_EXT){
            if (str.equalsIgnoreCase(ext)){
                return ICsProtocolSet.ExtType.image;
            }
        }
        for (String str : VIDEO_EXT){
            if (str.equalsIgnoreCase(ext)){
                return ICsProtocolSet.ExtType.video;
            }
        }
        return ICsProtocolSet.ExtType.all;
    }

    private static final FileInfoModel.FileComparator mFileComparator = new FileInfoModel.FileComparator();
    private ArrayList<File> mFileList;

    private void reset() {
        if (mFileList != null) {
            mFileList.clear();
        }
        mFileList = new ArrayList<>();
    }

    public static void test() {
        KFileScanner kFileScanner = new KFileScanner();
        long time = System.currentTimeMillis();
        ArrayList<FileInfoModel> rt = kFileScanner.start(null);//211ms,hide后90ms
        long last = (System.currentTimeMillis() - time);
        LOG_I("last time=" + last + ",sec = " + last / 1000+",getSize = "+rt.size());
        int i = 0;
        for (FileInfoModel model : rt){
            LOG_I("model#" +(i++)+ ": " + model);
        }
    }

    public ArrayList<FileInfoModel> start(String ext) {
        ArrayList<FileInfoModel> rt = startScanImages(DIRS, ext,mIsSupportRecursion).getLs();
        return rt;
    }
    public ArrayList<FileInfoModels> start(String ext, String order) {
        ArrayList<FileInfoModel> lst = startScanImages(DIRS, ext,mIsSupportRecursion).getLs();
        ArrayList<FileInfoModels> rt = null;
        if (lst.size() >= 1 ){
            rt = new ArrayList<>();
            long titleMtime = -1;
            long itMtime;
            FileInfoModels fileInfoModels = null;
            for (FileInfoModel it : lst){
                itMtime = it.mtime / Config.Day;
                if ( titleMtime != itMtime){
                    titleMtime = itMtime;
                    fileInfoModels = new FileInfoModels();
                    fileInfoModels.mtime = it.mtime;
                    fileInfoModels.datas = new ArrayList<>();
                    fileInfoModels.mtimeStr = it.mtimeStr;
                    rt.add(fileInfoModels);
                }
                fileInfoModels.datas.add(it);
            }
        }
        return rt;
    }

    public KFileScanner startScanImages(String scanPaths[], String ext, boolean isSupportRecursion) {
        reset();
        LOG_I("startScanImages");
        for (String scanPath : scanPaths) {
            File scanRoot = new File(scanPath);
            if (!scanRoot.exists()) {
                LOG_I("scanPath=" + scanPath + ":is not exists or have no files!");
                continue;
            }
            LOG_I("scanPath=" + scanPath + " is a valid path, startViaDownload accept");
            startScanImages(scanRoot.getAbsolutePath(), ext, isSupportRecursion);
        }
        return this;
    }

    public ArrayList<FileInfoModel> getLs() {
        //按照时间排序？
        Collections.sort(mFileList, mFileComparator);
        ArrayList<FileInfoModel> rtLs = new ArrayList<>(Config.pageSize);
        int pageId = 0;
        //   File f = new File(Config.FileDir);
        //  if (f.listFiles())
        int len = Math.min(Config.pageSize, mFileList.size());
        for (int i = 0; i < len; i++) {
            File f = mFileList.get(i);
            FileInfoModel model = new FileInfoModel();
            model.len = f.length();
            model.path = f.getPath();
            model.mtime = f.lastModified();
            Date date = new Date(f.lastModified());

            model.mtimeStr= date.toString();

          //  model.ctime = f.
            rtLs.add(model);
        }
        return rtLs;
    }

    private void startScanImages(String scanPath, String ext, boolean isSupportRecursion) {
        LOG_I("startScanImages :scanPath=" + scanPath + ",isSupportRecursion=" + isSupportRecursion);
        File scanRoot = new File(scanPath);
        if (!scanRoot.exists()) {
            LOG_I("scanPath=" + scanPath + ":is not exists or have no files!");
            return;
        }
        if (scanRoot.isHidden()){
            LOG_I("scanPath=" + scanPath + ":isHidden,ignore!");
            return;
        }
        if (scanRoot.isFile()) {
            if (acceptFile(scanRoot,ext))
                mFileList.add(scanRoot);
            return;
        }
        if (isSupportRecursion && scanRoot.isDirectory()) {
            LOG_I("scanPath=" + scanPath + " is a valid path, startViaDownload accept");
            File[] fs = scanRoot.listFiles();
            if (fs != null){
                for (File itFile : fs) {
                    startScanImages(itFile.getAbsolutePath(), ext, isSupportRecursion);
                }
            }
        }
    }

    private boolean acceptFile(File file, String ext) {
        LOG_I("acceptFile: handlering the file = " + file.getAbsolutePath());
        if (file.isFile()) {
            if (TextUtils.isEmpty(ext) || ext.equalsIgnoreCase(ICsProtocolSet.ExtType.all)){
                return true;
            }
            String name = file.getName();
            LOG_I("acceptFile: accept prefix！");
            String[] suffs = BOTH_EXT;
            if (ext.equalsIgnoreCase(ICsProtocolSet.ExtType.image)){
                suffs = IMAGE_EXT;
            }else if (ext.equalsIgnoreCase(ICsProtocolSet.ExtType.video)){
                suffs = VIDEO_EXT;
            }
            for (String itStr :suffs ) {//后缀检查
                if (name.endsWith(itStr)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void LOG_I(String log) {
        Log.i(TAG, log);
    }

    private void LOG_E(String log) {
        Log.e(TAG, log);
    }
}
