package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.TwoWayBinding;
import key.android.demo.databindingdemo.model.ObservableFieldContact;


public class TwoWayActivity extends BaseActivity {

    private TwoWayBinding mBinding;
    private ObservableFieldContact mFieldContact;
//    private TwoWayModel mTwoWayModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_two_way);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_two_way);

        mFieldContact = new ObservableFieldContact("key","guan");
        mBinding.setContact(mFieldContact);

//        mTwoWayModel = new TwoWayModel();
//        mBinding.setModel(mTwoWayModel);
    }

    public void onClick(View view) {
        // 改变 Model，将同时改变 UI
        mFieldContact.mName.set("two-way");
    }

//    public void updateModel(View view) {
//        mTwoWayModel.mColor.set(Color.RED);
//    }
//
//    public void updateView(View view) {
//        mBinding.twowayView.setColor(Color.GREEN);
//        mTwoWayModel.mColor.set(mBinding.twowayView.getColor());
//    }
}
