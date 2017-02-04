package key.android.demo.databindingdemo.model;

/**
 *@desc   
 *@ref:
 *@author : key.guan @ 2017/2/4 13:24
 */
public class User {
    private final String mFirstName;
    private final String mLastName;
    private int mAge;

    public User(String firstName, String lastName, int age) {
        this(firstName, lastName);
        mAge = age;
    }

    public User(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public int getAge() {
        return mAge;
    }

    public boolean isAdult(int d) {
        return mAge >= 18;
    }
}
