package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityObserverBinding;
import key.android.demo.databindingdemo.model.ObservableContact;


public class ObserverActivity extends BaseActivity {

    private ActivityObserverBinding mBinding;
    private ObservableContact mObservableContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_observer);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer);

        mObservableContact = new ObservableContact("police","110");
        mBinding.setContact(mObservableContact);
    }

    public void onClick(View view) {
       // mObservableContact.setName("key new");
        mObservableContact.setPhone("12345678901");
    }
}
