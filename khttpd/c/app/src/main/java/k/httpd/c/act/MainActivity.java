package k.httpd.c.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import k.core.util.kil.NailFileLoader;
import k.core.util.kil.RawFileLoader;
import k.httpd.c.DApManager;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.Config;
import k.httpd.c.cons.ICsProtocolSet;
import k.httpd.c.model.FileInfoModel;
import k.httpd.c.model.FileInfoModels;


public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    protected Gson _gson =  new Gson();
    private GridAdapter mImageAdapter, mVideoAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化xUtils工具 初始化后才能使用,自定义类的时候，这个需要早点初始化！
        org.xutils.x.Ext.init(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        NailFileLoader.getIns().init(this);
        RawFileLoader.getIns().init(this);
        DApManager.getIns().init(this);
        initView();

    }

    private void initView() {
        findViewById(R.id.test_down_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
      /*  GridView gv = (GridView) findViewById(R.id.gv_image);
        mImageAdapter = new GridAdapter(this, ICsProtocolSet.ExtType.image);
        gv.setAdapter(mImageAdapter);
        mVideoAdapter = new GridAdapter(this, ICsProtocolSet.ExtType.video);
        ((GridView) findViewById(R.id.gv_video)).setAdapter(mVideoAdapter);
        findViewById(R.id.getFileList_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileList(ICsProtocolSet.ExtType.image);
            }
        });
        findViewById(R.id.getFileList_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileList(ICsProtocolSet.ExtType.video);
            }
        });
        getFileList(ICsProtocolSet.ExtType.image);*/
    }
    //获取文件列表
    public void getFileList(final String ext){
        RequestParams params=new RequestParams(Config.SERVER_IP + ICsProtocolSet.getFileList.DO);
        params.addParameter(ICsProtocolSet.getFileList.pageId, 1);
        params.addParameter(ICsProtocolSet.getFileList.ext, ext);
        showLongToast("getFileList="+params);
        org.xutils.x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String jsonString) {
                showLongToast("onSuccess,jsonString="+jsonString);
            /* //   RetModel  ret =_gson.fromJson(jsonString, RetModel.class); */
                Type listType = new TypeToken<ArrayList<FileInfoModels>>() {}.getType();
                ArrayList<FileInfoModels>  ls =_gson.fromJson(jsonString, new TypeToken<List<FileInfoModels>>(){}.getType());
                if (ls!=null && ls.size() > 0)showLongToast(ls.get(0).toString());
               /* if (ext.equalsIgnoreCase(ICsProtocolSet.ExtType.video)){
                    mVideoAdapter.updateList(ls);
                }else {
                    mImageAdapter.updateList(ls);
                }*/
            }
            @Override
            public void onCancelled(Callback.CancelledException arg0) {
                showLongToast("onCancelled");
            }
            @Override
            public void onError(Throwable t, boolean arg1) {
                showLongToast("onError");
            }
            @Override
            public void onFinished() {
                showLongToast("onFinished");
            }
        });
    }
    //获取文件列表,测试ok，试试afinal吧，
    public void download(){
        RequestParams params=new RequestParams(Config.SERVER_IP+ ICsProtocolSet.Download.DO);
        params.addQueryStringParameter(ICsProtocolSet.Download.path, "/mnt/internal_sd/Screenshots/Screenshot_2013-01-24-22-47-44.png");
        params.addQueryStringParameter(ICsProtocolSet.Download.level, "raw");
        params.setSaveFilePath("/sdcard/test.png");
        org.xutils.x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File file) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.e("x.http()", "current=" + current+",total="+total);

            }
        });
    }
    private void showLongToast(String log){
        Log.d("dshare", log);
    }
}
