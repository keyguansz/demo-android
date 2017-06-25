package k.httpd.c.act;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class KExListAdapter extends BaseExpandableListAdapter {
    final ArrayList<FileInfoModels> mData = new ArrayList<>();
    private String mExt;
    private Gson _gson = new Gson();

    public KExListAdapter(Context context, String ext) {
        mExt = ext;
        mInflater = LayoutInflater.from(context);
    }
    public void updateView() {
        //仅仅为空的时候才向服务器请求
        if (mData == null || mData.size() == 0){
            getFileList(mExt);
        }
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GViewHolder();
            convertView = mInflater.inflate(R.layout.elv_group, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.g_title_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(getGroup(groupPosition).toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup
            parent) {
        CViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CViewHolder();
            convertView = mInflater.inflate(R.layout.elv_child, parent, false);
            viewHolder.gv = (GridView) convertView.findViewById(R.id.c_image_gv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CViewHolder) convertView.getTag();
        }
        GridAdapter mImageAdapter = new GridAdapter(parent.getContext(), mExt);
        viewHolder.gv.setAdapter(mImageAdapter);
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
                    showLongToast(ls.get(0).toString());
                updateData(ls);
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
    private void updateData(ArrayList<FileInfoModels> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
            super.notifyDataSetChanged();
        }
    }

    private void showLongToast(String log) {
        Log.d("dshare", log);
    }

    static class CViewHolder {
        GridView gv;
    }

    static class GViewHolder {
        TextView tv;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int gId) {
        return mData.get(gId).datas.size();
    }

    @Override
    public Object getGroup(int gId) {
        return mData.get(gId);
    }

    @Override
    public Object getChild(int gId, int cId) {
        return mData.get(gId).datas.get(cId);
    }

    // KWillDo: 2017/6/25 很多函数是不是不用写啊
    //取得指定分组的ID.该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //取得给定分组中给定子视图的ID. 该组ID必须在组中是唯一的.必须不同于其他所有ID（分组及子项目的ID）
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    //分组视图（group）的标题
    private String[] group = {"group1", "group2"};
    //子选项视图(child) 的标题
    private String[][] gridViewChild = {{"child11", "child12"}, {"child21", "child22"}};
    //子选项视图(child) 的图标
    private int[][] gridImgChild = new int[][]{
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher},
            {R.mipmap.ic_launcher, R.mipmap.ic_launcher}
    };
    private String[][] child = {{""}, {""}};
    private LayoutInflater mInflater;
    private Context context;


   /* //设置GridView点击事件监听
    private void setGridViewListener(final GridView gridView, final int groupPosition) {
        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RelativeLayout ff = (RelativeLayout) view;
                TextView gg = (TextView) ff
                        .findViewById(R.id.child_gridview_item);
                Toast.makeText(context, "你点击了" + gg.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    //设置GridView数据
    private ArrayList<HashMap<String, Object>> setGridViewData(String[] data, int[] img) {
        ArrayList<HashMap<String, Object>> gridItem = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("child_gridview_item", data[i]);
            hashMap.put("child_gridview_img1", img[i]);
            gridItem.add(hashMap);
        }
        return gridItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
