package k.core.util.kil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

import k.core.util.KLogUtil;

import static android.content.ContentValues.TAG;

/**
 * @author :key.guan
 * @package :k.core.util
 * @date : 2017/6/15
 * Description:
 * Copyright (k.httpd.c) 2017. DJI All Rights Reserved.
 */

public class KImgResizer {
    public static void main(){
        getSampleSize(1000,1000,50,50);
        getSampleSize(800,600,40,50);
    }
    public static Bitmap decode(Resources res, int resId, int reqW, int reqH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId ,options);
        options.inSampleSize = getSampleSize(options, reqW, reqH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId ,options);
    }
    public static Bitmap decode(FileDescriptor fd, int reqW, int reqH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = getSampleSize(options, reqW, reqH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }
    private static int getSampleSize(BitmapFactory.Options options,int reqW, int reqH){
        if (reqW == 0 || reqH == 0) {
            return 1;
        }
        final int h = options.outHeight;
        final int w = options.outWidth;
        LOG_D("origin, w= " + w + " h=" + h);
        int inSampleSize = 1;
        while ( h/inSampleSize >= reqH && w/inSampleSize >= reqW)
            inSampleSize = inSampleSize<< 1;
        return Math.max(1,inSampleSize/2);
    }
    private static int getSampleSize(int rawW, int rawH,int reqW, int reqH){
        if (reqW == 0 || reqH == 0) {
            return 1;
        }
        LOG_D("origin, w= " + rawW + " h=" + rawH);
        int inSampleSize = 1;
        while ( rawH/inSampleSize >= reqH && rawW/inSampleSize >= reqW)
            inSampleSize = inSampleSize<< 1;
        return Math.max(1,inSampleSize/2);
    }

    @Deprecated
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(TAG, "origin, w= " + width + " h=" + height);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "sampleSize:" + inSampleSize);

        return inSampleSize;
    }
    @Deprecated
    public static int calculateInSampleSize(int rawW, int rawH,
                                     int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }
        Log.d(TAG, "origin, w= " + rawW + " h=" + rawH);
        int inSampleSize = 1;

        if (rawH > reqHeight || rawW > reqWidth) {
            final int halfHeight = rawH / 2;
            final int halfWidth = rawW / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "sampleSize:" + inSampleSize);
        return inSampleSize;
    }
    private static void LOG_D(String log){
        KLogUtil.E("KImgResizer",log);
    }
}
