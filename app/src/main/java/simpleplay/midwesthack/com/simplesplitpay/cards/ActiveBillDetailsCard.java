package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FriendsFragment;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class ActiveBillDetailsCard extends Card implements View.OnClickListener {

    private String value, description;
    private Context mContext;

    public ActiveBillDetailsCard(Context context) {
        this(context, R.layout.card_active_bill_details);
        this.mContext = context;
    }

    public ActiveBillDetailsCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView valueView = (TextView) parent.findViewById(R.id.billValue);
        TextView descriptionView = (TextView) parent.findViewById(R.id.billDescription);

        valueView.setText(value);
        descriptionView.setText(description);
    }

    @Override
    public void onClick(View v) {
        Bundle extras = new Bundle();
        extras.putInt(FriendsFragment.ADD_FRIEND, FriendsFragment.ADD_FRIEND_OK);
        ((MainActivity) mContext).displayView(MainActivity.FRIENDS_FRAGMENT, extras);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
