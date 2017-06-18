package k.httpd.c.act;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import k.core.util.kil.KImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.Config;
import k.httpd.c.cons.IActionSet;
import k.httpd.c.model.FileInfoModel;

/**
 *@desc
 *@ref:
 *@author : key.guan @ 2017/6/15 21:42
 */

public class GridAdapter extends BaseAdapter {
    public static final String TAG = "GridAdapter";


    Context mContext;

    final ArrayList<FileInfoModel>  mQSList =new ArrayList<>();
    private final WifiManager mWifiManager;

    public GridAdapter(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public int getCount() {
        return mQSList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(ArrayList<FileInfoModel> list) {
        if ( list != null && list.size() > 0 ){
            mQSList.clear();
            mQSList.addAll(list);
            notifyDataSetChanged();
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.act_ch12_item, null);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag( holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KImgLoader.getIns().setImageBitmap(Config.SERVER_IP+mQSList.get(position).path, holder.mImageView);
        return convertView;
    }

    private static class ViewHolder {
        ImageView mImageView;
    }
}
