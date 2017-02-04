package key.android.demo.databindingdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.adapter.RecyclerAdapter;
import key.android.demo.databindingdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mBinding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerAdapter mRecyclerAdapter = new RecyclerAdapter();
        mBinding.recyclerView.setAdapter(mRecyclerAdapter);
    }
}
