package key.android.demo.databindingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import key.android.demo.databindingdemo.R;
import key.android.demo.databindingdemo.model.User;


/**
 *@desc
 *@ref:
 *@author : key.guan @ 2017/2/4 21:56
 */
public class Card extends LinearLayout {

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mAge;

    public Card(Context context) {
        this(context, null);
    }

    public Card(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.card, this);
        setOrientation(VERTICAL);

        mFirstName = (TextView) findViewById(R.id.firstname);
        mLastName = (TextView) findViewById(R.id.lastname);
        mAge = (TextView) findViewById(R.id.age);
    }

    // 自动 Setter
    public void setObject(User user) {
        mFirstName.setText(user.getFirstName());
        mLastName.setText(user.getLastName());
        mAge.setText(String.valueOf(user.getAge()));
    }
}
