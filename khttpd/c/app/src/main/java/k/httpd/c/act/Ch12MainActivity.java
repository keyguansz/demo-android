package k.httpd.c.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import k.core.util.kil.KImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.Config;
import k.httpd.c.cons.IActionSet;
import k.httpd.c.model.FileInfoModel;
import k.httpd.c.model.RetModel;

import static k.httpd.c.cons.IActionSet.getFileList;


public class Ch12MainActivity extends Activity {
    private final String TAG = "Ch12MainActivity";
    protected Gson _gson =  new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ch12main);
        KImgLoader.getIns().init(this);
        //初始化xUtils工具 初始化后才能使用
        org.xutils.x.Ext.init(getApplication());
        initView();
    }

    private void initView() {
        GridView gv = (GridView) findViewById(R.id.gv);
        gridAdapter = new GridAdapter(this);
        gv.setAdapter(gridAdapter);
        findViewById(R.id.getFileList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFileList();
              //  KImgLoader.getIns().setImageBitmap(Config.SERVER_IP+ getFileList,(ImageView) findViewById(R.id.testFileList));
             /*   new Thread(new Runnable() {
                    @Override
                    public void run() {
                        KImgLoader.getIns().downToStr(Config.SERVER_IP+ getFileList);
                    }
                }).start();*/

            }
        });
        getFileList();
    }
    //获取文件列表
    public void getFileList(){
        RequestParams params=new RequestParams(Config.SERVER_IP+ getFileList);
        params.addParameter("pageId", 1);
        showLongToast("getFileList="+params);
        org.xutils.x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String jsonString) {
                RetModel  ret =_gson.fromJson(jsonString, RetModel.class);
            /*    ArrayList<FileInfoModel>  ls =_gson.fromJson(ret.getMsg(), new TypeToken<List<FileInfoModel>>(){}.getType());

                if (ls!=null && ls.size() > 0)showLongToast(ls.get(0).toString());
                gridAdapter.updateList(ls);
*/
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
        RequestParams params=new RequestParams(Config.SERVER_IP+ IActionSet.Download);
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
