package key.android.demo.databindingdemo.task;

import android.util.Log;

/**
 *@desc
 *@ref:
 *@author : key.guan @ 2017/2/3 20:33
 */
public class Task implements Runnable {

    @Override
    public void run() {
        Log.i("key-test", "Task run");
    }
}
