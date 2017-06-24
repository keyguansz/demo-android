package k.httpd.s;

/*
 * #%L
 * NanoHttpd-Samples
 * %%
 * Copyright (C) 2012 - 2015 nanohttpd
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the nanohttpd nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;
import org.nanohttpd.util.ServerRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import k.httpd.s.cons.Config;
import k.httpd.s.model.FileInfoModel;
import k.httpd.s.model.FileInfoModels;
import k.httpd.s.model.RetModel;

import static org.nanohttpd.protocols.http.response.Response.newChunkedResponse;
import static org.nanohttpd.protocols.http.response.Response.newFixedLengthResponse;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 * https://confluence.djicorp.com/pages/viewpage.action?pageId=10937987
 */
public class HelloServer extends NanoHTTPD {
    protected Gson _gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    private KFileScanner mKFileScanner = new KFileScanner();
    private RetModel rt = new RetModel();

    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(HelloServer.class.getName());

    public static void main(String[] args) {
        ServerRunner.run(HelloServer.class);
    }

    public HelloServer() {
        super(Config.port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Method method = session.getMethod();
        String uri = session.getUri();
        HelloServer.LOG.info(method + " '" + uri + "' ");//// TODO: 2017/6/17 为何请求的是两次？

        Map<String, String> parms = session.getParms();
        return handleAction(uri, parms);

    }

    private Response handleAction(String uri, Map<String, String> param) {
        if (TextUtils.isEmpty(uri) || uri.length() <= 1) {
            return null;
        }
        String action = uri.substring(1);
        if (action.equals(ICsProtocolSet.getFileList.DO)) {
            return handleGetFileList(param);
        } else if (action.equals(ICsProtocolSet.Download.DO)) {
            return handleDownload(param);
        }
        return null;
    }

    private Response handleDownload(Map<String, String> param) {
        InputStream inputStream = null;
        Response response = null;
        String path = param.get("path");
        String level = param.get("level");
        String extType = KFileScanner.findExtType(path);
        if (TextUtils.isEmpty(level)) {
            level = ICsProtocolSet.LevelType.raw;
        }

        File file = new File(path);
        long contentLen = file.length();
        if (ICsProtocolSet.LevelType.raw.equalsIgnoreCase(level)) {//原始图
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }//// FIXME: 2017/6/20 怎么传递文件长度呢？
            response = newChunkedResponse(Status.OK, "application/octet-stream", inputStream);
        } else if (ICsProtocolSet.LevelType.nail.equalsIgnoreCase(level)) {//缩略图
            Bitmap bitmap = null;
            if (extType.equalsIgnoreCase(ICsProtocolSet.ExtType.image)) {//图片缩略图
                bitmap = ThumbnailUtils.extractThumbnail(KImgResizer.decode(path), 120, 120);
            } else if (extType.equalsIgnoreCase(ICsProtocolSet.ExtType.video)) {//视频缩略图
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(path);
                bitmap = retriever.getFrameAtTime();
            }
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                inputStream = new ByteArrayInputStream(baos.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = newChunkedResponse(Status.OK, "application/octet-stream", inputStream);
        }
        //这代表任意的二进制数据传输。
       
        response.addHeader("Content-Disposition", "attachment; filename=" + "test.java");
        return response;
    }
 /*   private String bitmapChangeString(Bitmap bitmap){
        if(bitmap != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            String str = new String(Base64.encode(baos.toByteArray()));
            return str;
        }
        return null;
    }*/

    private Response handleGetFileList(Map<String, String> param) {
        String rtJs = "";
        String ext = param.get(ICsProtocolSet.getFileList.ext);
        String order = param.get(ICsProtocolSet.getFileList.order);
        if (ICsProtocolSet.OrderType.no.equalsIgnoreCase(order)){//不分组
            ArrayList<FileInfoModel>  rtLs = mKFileScanner.start(ext);
            rtJs =  _gson.toJson(rtLs);
        }else {
            ArrayList<FileInfoModels> rtLs = mKFileScanner.start(ext, order);
            rtJs =  _gson.toJson(rtLs);
        }
        Log.e("TEst", "rtJs" + ": " +rtJs);
        //// TODO: 2017/6/19
        ;//rt客户端解析失败？
        return  newFixedLengthResponse(Status.OK, "application/json",rtJs);
    }

}
