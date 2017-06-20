package k.httpd.c.act;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import k.core.util.kil.KImgLoader;
import k.core.util.kil.KRawImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.model.FileInfoModel;

/**
 * @author : key.guan @ 2017/6/15 21:42
 * @desc
 * @ref:
 */

public class GridAdapter extends BaseAdapter {
    public static final String TAG = "GridAdapter";


    Context mContext;
    String mExt;

    final ArrayList<FileInfoModel> mQSList = new ArrayList<>();
    private final WifiManager mWifiManager;

    public GridAdapter(Context context, String ext) {
        mContext = context;
        mExt = ext;
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
        if (list != null && list.size() > 0) {
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
            holder.mDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.mDesc.setOnClickListener(new MyOnClickListener(position,mQSList.get(position).path));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        KImgLoader.getIns().setImageBitmap(mQSList.get(position).path, holder.mImageView);
      /*  if (MainActivity.ExtType.image.equalsIgnoreCase(mExt)) {

        } else {
            KImgLoader.getIns().setVideoBitmap(mQSList.get(position).path, holder.mImageView);
        }*/

        return convertView;
    }

    private static class ViewHolder {
        ImageView mImageView;
        TextView mDesc;
    }

    private class MyOnClickListener implements View.OnClickListener {
        final int mPos;
        final String mPath;

        public MyOnClickListener(int pos,String path) {
            mPos = pos;
            mPath = path;
        }

        @Override
        public void onClick(View v) {
            //mPath?
            Toast.makeText(v.getContext(), "down " + mQSList.get(mPos), Toast.LENGTH_LONG);
            Log.e("raw_down", "down " + mQSList.get(mPos));
            KRawImgLoader.getIns().setTextView( mQSList.get(mPos).path, (TextView)(v) );

        }
    }

    ;
}
