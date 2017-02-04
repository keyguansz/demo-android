package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityCombineBinding;
import key.android.demo.databindingdemo.handler.ContextHandler;
import key.android.demo.databindingdemo.model.User;

public class CombineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_combine);

        ActivityCombineBinding binding = DataBindingUtil.setContentView(this,  R.layout.activity_combine);

        User user = new User("key", null, 28);
        binding.setUser(user);

        binding.setTest("Java lang");
        binding.setNum(1);

        // 容器类
        List<User> mUserList = new ArrayList<>();
        mUserList.add(user);
        mUserList.add(user);
        mUserList.add(user);
        binding.setUserList(mUserList);

        Map<String, String> map = new HashMap<>();
        map.put("1", "map1");
        map.put("2", "map2");
        map.put("3", "map3");
        binding.setMap(map);

        // Context
        ContextHandler handler = new ContextHandler();
        binding.setHandler(handler);
    }
}
