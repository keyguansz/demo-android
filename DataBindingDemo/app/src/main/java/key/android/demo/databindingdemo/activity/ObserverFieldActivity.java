package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.ActivityObserverFieldBinding;
import key.android.demo.databindingdemo.model.ObservableFieldContact;


public class ObserverFieldActivity extends AppCompatActivity {

    ActivityObserverFieldBinding mBinding;
    private ObservableFieldContact mObservableFieldContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_observer_field);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_observer_field);

        mObservableFieldContact = new ObservableFieldContact("police","110");
        mBinding.setContact(mObservableFieldContact);
    }

    public void onClick(View view) {
        mObservableFieldContact.mName.set("key ObserverFieldA");
        mObservableFieldContact.mPhone.set("12345678901");
    }
}
