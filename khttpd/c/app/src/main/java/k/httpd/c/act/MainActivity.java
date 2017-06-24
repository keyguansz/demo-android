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

import k.core.util.kil.KImgLoader;
import k.core.util.kil.KRawImgLoader;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        KImgLoader.getIns().init(this);
        KRawImgLoader.getIns().init(this);
        //初始化xUtils工具 初始化后才能使用
        org.xutils.x.Ext.init(getApplication());
        initView();
    }

    private void initView() {
        GridView gv = (GridView) findViewById(R.id.gv_image);
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
        getFileList(ICsProtocolSet.ExtType.image);
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
                if (ext.equalsIgnoreCase(ICsProtocolSet.ExtType.video)){
                    mVideoAdapter.updateList(ls);
                }else {
                    mImageAdapter.updateList(ls);
                }
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
    //获取文件列表
    public void downlaod(){
        RequestParams params=new RequestParams(Config.SERVER_IP+ ICsProtocolSet.Download.DO);
        org.xutils.x.http().get(params, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File jsonString) {
                showLongToast("jsonString="+jsonString);

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
    private void showLongToast(String log){
        Log.d("dshare", log);
    }
}
