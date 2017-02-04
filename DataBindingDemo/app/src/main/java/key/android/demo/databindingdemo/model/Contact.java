package key.android.demo.databindingdemo.model;

/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 21:37
 */
public class Contact {

    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        mName = name;
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
