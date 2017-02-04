package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityEventBinding;
import key.android.demo.databindingdemo.handler.EventHandler;
import key.android.demo.databindingdemo.task.Task;


public class EventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_event);
        ActivityEventBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_event);

        binding.setHandler(new EventHandler(this));
        binding.setTask(new Task());
    }
}
