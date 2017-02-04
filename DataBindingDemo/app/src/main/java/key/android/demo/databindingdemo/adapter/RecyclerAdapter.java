package key.android.demo.databindingdemo.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.databinding.RecyclerItemBinding;
import key.android.demo.databindingdemo.handler.Presenter;
import key.android.demo.databindingdemo.model.RecyclerItem;

/**
 * ClassName: RecyclerAdapter
 * Description:
 * Author: connorlin
 * Date: Created on 2016-7-1.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BindingHolder> {

    private static final String ACTION_PRE = "key.databinding.action.";

    private String[] mType = new String[]{
            "Combine",
            "NormalObject",
            "Observer",
            "ObserverField",
            "ObserverCollection",
            "ViewStub",
            "Event",
            "AttributeSetters",
            "Converters",
            "Demo",
            "TwoWay"
    };

    private List<RecyclerItem> mRecyclerItemList = new ArrayList<>();
    private RecyclerItemBinding mbinding;

    public RecyclerAdapter() {
        mRecyclerItemList.clear();
        for(String str : mType) {
            RecyclerItem mRecyclerItem = new RecyclerItem(str, ACTION_PRE + str);
            mRecyclerItemList.add(mRecyclerItem);
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.recycler_item,parent,false);

        Presenter presenter2 = new Presenter();
        binding.setPresenter(presenter2);

        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);//onBindViewHolder中调用
        mbinding = binding;// 动态绑定变量方法2
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        // 动态绑定变量方法1
       // holder.getBinding().setVariable(BR.item3, mRecyclerItemList.get(position));
      //  holder.getBinding().executePendingBindings();

        // 动态绑定变量方法2
        mbinding.setItem3(mRecyclerItemList.get(position));
        mbinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRecyclerItemList.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private RecyclerItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
        }

        public RecyclerItemBinding getBinding() {
            return binding;
        }

        public void setBinding(RecyclerItemBinding binding) {
            this.binding = binding;
        }
    }
}
