package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import key.android.demo.databindingdemo.DemoBinding;
import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.adapter.ListAdapter;
import key.android.demo.databindingdemo.handler.DemoHandler;
import key.android.demo.databindingdemo.model.ObservableContact;


public class DemoActivity extends BaseActivity {

    private DemoBinding mDemoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        ObservableContact contact = new ObservableContact("", "");
        mDemoBinding.setContact(contact);

        initViews();
    }

    private void initViews() {
        mDemoBinding.listView.setEmptyView(mDemoBinding.emptyView);

        ListAdapter listAdapter = new ListAdapter(this);
        mDemoBinding.listView.setAdapter(listAdapter);

        DemoHandler viewHandler = new DemoHandler(this, mDemoBinding, listAdapter);
        mDemoBinding.setHandler(viewHandler);
    }
}
