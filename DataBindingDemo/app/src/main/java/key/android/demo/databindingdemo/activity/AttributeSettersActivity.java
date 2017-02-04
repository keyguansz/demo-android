package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityAttributeSettersBinding;
import key.android.demo.databindingdemo.model.User;


public class AttributeSettersActivity extends BaseActivity {

    private ActivityAttributeSettersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attribute_setters);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_attribute_setters);

        User user = new User("key", "guan", 28);
        mBinding.setUser(user);

        mBinding.setUrl("http://connorlin.github.io/images/avatar.jpg");
    }
}
