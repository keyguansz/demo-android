package key.android.demo.databindingdemo.handler;

import android.content.Context;
import android.content.Intent;

import key.android.demo.databindingdemo.model.RecyclerItem;


/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/3 13:18
 */
public class Presenter {

    public void onTypeClick(Context context, RecyclerItem recyclerItem) {
        Intent intent = new Intent(recyclerItem.getAction());
        context.startActivity(intent);
    }
}
