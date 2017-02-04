package key.android.demo.databindingdemo.handler;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import key.android.demo.databindingdemo.task.Task;


/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 22:31
 */
public class EventHandler {

    private Context mContext;

    public EventHandler(Context context) {
        mContext = context;
    }

    public void onClickFriend(View view) {
        Toast.makeText(mContext, "onClickFriend", Toast.LENGTH_LONG).show();
    }

    public void onTaskClick(Task task) {
        task.run();
    }

    public void onTaskClickWithParams(View view, Task task) {
        task.run();
    }

    public void onCompletedChanged(Task task, boolean completed) {
        if(completed) {
            task.run();
        }
    }
}
