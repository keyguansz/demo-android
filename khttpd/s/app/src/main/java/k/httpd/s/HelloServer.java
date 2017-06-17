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

import android.os.SystemClock;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.util.ServerRunner;

import k.httpd.s.cons.Config;
import k.httpd.s.model.FileInfoModel;
import k.httpd.s.model.RetModel;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class HelloServer extends NanoHTTPD {
    protected Gson _gson =  new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
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
        String rt = getAction(uri, parms);
        return Response.newFixedLengthResponse(rt);
    }

    private String getAction(String uri, Map<String, String> param) {
        if ( TextUtils.isEmpty(uri) || uri.length() <= 1){
            return "";
        }
        String action = uri.substring(1);
        if ( action.equals(IActionSet.getFileList) ) {
            return handleGetFileList(param);
        }
        return "";
    }

    private String handleGetFileList(Map<String, String> param) {
        ArrayList<FileInfoModel> rtLs = new ArrayList<>(Config.pageSize);
        int pageId = 0;
     //   File f = new File(Config.FileDir);
      //  if (f.listFiles())
        for (int i = 0;i < Config.pageSize;i++){
            FileInfoModel model = new FileInfoModel();
            model.len = 100 + i * 500;
            model.path = "path"+i;
            model.mtime = SystemClock.currentThreadTimeMillis();
            rtLs.add(model);
        }
        rt.setMsg(_gson.toJson(rtLs));
        return _gson.toJson(rt);
    }
}
