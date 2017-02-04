package key.android.demo.databindingdemo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import key.android.demo.databindingdemo.BR;

/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 19:31
 */
public class ObservableContact extends BaseObservable {
    private String mName;
    private String mPhone;

    public ObservableContact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        notifyPropertyChanged(BR.phone);
    }
}
