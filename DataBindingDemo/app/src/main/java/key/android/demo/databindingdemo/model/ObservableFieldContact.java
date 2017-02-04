package key.android.demo.databindingdemo.model;

import android.databinding.ObservableField;

/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 21:54
 */
public class ObservableFieldContact {
    public ObservableField<String> mName = new ObservableField<>();
    public ObservableField<String> mPhone = new ObservableField<>();

    public ObservableFieldContact(String name, String phone) {
        mName.set(name);
        mPhone.set(phone);
    }
}
