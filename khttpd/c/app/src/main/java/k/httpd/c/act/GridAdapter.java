package k.httpd.c.act;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import k.core.util.kil.KImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.Config;
import k.httpd.c.cons.IActionSet;

/**
 *@desc
 *@ref:
 *@author : key.guan @ 2017/6/15 21:42
 */

public class GridAdapter extends BaseAdapter {
    public static final String TAG = "GridAdapter";


    Context mContext;

    String[] mQSList ={Config.SERVER_IP+ IActionSet.Download
            ,"https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E9%A3%8E%E6%99%AF&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=3510422096,2563322625&os=1569480607,960621875&simid=&pn=0&rn=1&di=1&ln=1995&fr=&fmq=1497534709185_R&fm=se0&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=&istype=2&ist=&jit=&bdtype=-1&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fimgstore.cdn.sogou.com%2Fapp%2Fa%2F100540002%2F481521.jpg&rpstart=0&rpnum=0&adpicid=0"
            ,"https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E9%A3%8E%E6%99%AF&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=1645510249,1183117000&os=312634974,223123913&simid=3496507195,318114603&pn=1&rn=1&di=28261883100&ln=1995&fr=&fmq=1497534709185_R&fm=se0&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fpic38.nipic.com%2F20140225%2F10361790_200038251116_2.gif&rpstart=0&rpnum=0&adpicid=0"
            ,"https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E9%A3%8E%E6%99%AF&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=2723611149,4287815617&os=3343051992,1865709408&simid=3212181161,3919544143&pn=5&rn=1&di=6206427371&ln=1995&fr=&fmq=1497534709185_R&fm=se0&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Fxll%2F012%2F02%2F2%2F3.jpg&rpstart=0&rpnum=0&adpicid=0"
            ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497544840854&di=e23a5ad861fd5de4247b3c27a0832350&imgtype=0&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Fxll%2F012%2F02%2F2%2F3.jpg"};

    private final WifiManager mWifiManager;

    public GridAdapter(Context context) {
        mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public int getCount() {
        return mQSList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        KImgLoader.getIns().setImageBitmap(mQSList[position], holder.mImageView);
        return convertView;
    }

    private static class ViewHolder {
        ImageView mImageView;
    }
}
