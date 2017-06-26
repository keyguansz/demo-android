package k.httpd.c.act;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;

import k.core.util.KTextUtil;
import k.core.util.kil.KRawImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.Config;
import k.httpd.c.cons.ICsProtocolSet;
import k.httpd.c.model.FileInfoModels;

/**
 * @author :key.guan
 * @package :k.httpd.c.act
 * @date : 2017/6/25
 * Description: http://www.jianshu.com/p/fee7db238ff2
 * 滑动会不流畅有停顿
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public class KListAdapter extends BaseAdapter {
    private final ArrayList<FileInfoModels> mData = new ArrayList<>();
    private String mExt;
    private Gson _gson = new Gson();
    private Context mCtx;
    private LayoutInflater mInflater;

    public KListAdapter(Context context, String ext) {
        mExt = ext;
        mInflater = LayoutInflater.from(context);
        mCtx = context;
    }

    public void updateView() {
        //仅仅为空的时候才向服务器请求
        //  if (mData == null || mData.size() == 0){
        getFileList(mExt);
        //   }
    }

    Handler mHandler;

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public View getView(final int gId, View convertView, ViewGroup parent) {
        GViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GViewHolder();
            convertView = mInflater.inflate(R.layout.klv_item, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.title_tv);
            viewHolder.gv = (GridView) convertView.findViewById(R.id.images_gv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(KTextUtil.formatADate(mData.get(gId).mtime) + "." + mData.get(gId).datas.size());
        GridAdapter mImageAdapter = new GridAdapter(mCtx, mExt);
        // KWillDo: 2017/6/25 性能？
        mImageAdapter.updateList(mData.get(gId).datas);
        viewHolder.gv.setAdapter(mImageAdapter);
        viewHolder.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mData.get(gId).datas.get(position).state == ICsProtocolSet.StateType.init) {
                    mData.get(gId).datas.get(position).state = ICsProtocolSet.StateType.selected;
                    ((KCheckImageView) view).setChecked(true);
                    DJIDshareView.mSelected.add(mData.get(gId).datas.get(position).path);
                } else if (mData.get(gId).datas.get(position).state == ICsProtocolSet.StateType.selected) {
                    mData.get(gId).datas.get(position).state = ICsProtocolSet.StateType.init;
                    ((KCheckImageView) view).setChecked(false);
                    DJIDshareView.mSelected.remove(mData.get(gId).datas.get(position).path);
                }
                mHandler.obtainMessage(KRawImgLoader.MSG.MSG_select).sendToTarget();
            }
        });
        return convertView;
    }

    //获取文件列表
    // KWillDo: 2017/6/25 主线程卡死？
    private void getFileList(final String ext) {
        RequestParams params = new RequestParams(Config.SERVER_IP + ICsProtocolSet.getFileList.DO);
        params.addParameter(ICsProtocolSet.getFileList.pageId, 1);
        params.addParameter(ICsProtocolSet.getFileList.ext, ext);
        showLongToast("getFileList=" + params);
        org.xutils.x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String jsonString) {
                showLongToast("onSuccess,jsonString=" + jsonString);
                Type listType = new TypeToken<ArrayList<FileInfoModels>>() {
                }.getType();
                ArrayList<FileInfoModels> ls = _gson.fromJson(jsonString, listType);
                if (ls != null && ls.size() > 0)
                    //showLongToast(ls.get(0).toString());
                    updateData(ls);
            }

            @Override
            public void onCancelled(CancelledException arg0) {
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

    private void updateData(ArrayList<FileInfoModels> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
            super.notifyDataSetChanged();
        }
    }
   /* public static  boolean contain(String path){
        return mSelectedPath.contains(path);
    }*/

    private void showLongToast(String log) {
        Log.d("dshare", log);
    }

    static class GViewHolder {
        TextView tv;
        GridView gv;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int gId) {
        return null;
    }

    @Override
    public long getItemId(int gId) {
        return gId;
    }
}
