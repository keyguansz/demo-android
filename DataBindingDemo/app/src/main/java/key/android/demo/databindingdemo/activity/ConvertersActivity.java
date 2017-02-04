package key.android.demo.databindingdemo.activity;

import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityConvertersBinding;
import key.android.demo.databindingdemo.model.User;

public class ConvertersActivity extends BaseActivity {

    private ObservableBoolean mIsError = new ObservableBoolean();

    ActivityConvertersBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_converters);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_converters);

        List<User> usrList = new ArrayList<>();
        usrList.add(new User("key", "guan", 28));
        mBinding.setList(usrList);

        mIsError.set(true);
        mBinding.setIsError(mIsError);
    }

    public void onToggle(View view) {
        mIsError.set(!mIsError.get());
    }

    // 方法名可自定义，只需关心参数 User
    @BindingConversion
    public static String convertUserToCharSequence(User user) {
        return user.getLastName();
    }

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}
