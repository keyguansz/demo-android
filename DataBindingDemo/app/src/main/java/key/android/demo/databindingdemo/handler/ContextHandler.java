package key.android.demo.databindingdemo.handler;

import android.content.Context;

import key.android.demo.databindingdemo.R;


/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 21:32
 */
public class ContextHandler {

    public String loadString(Context context) {
        return context.getResources().getString(R.string.string_from_context);
    }
}
