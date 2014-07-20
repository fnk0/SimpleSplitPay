package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FriendsFragment;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FriendCard extends Card implements Card.OnCardClickListener {

    private String LOG_TAG = "FriendCard";
    private ImageView friendPicView;
    private TextView friendNameView;
    private String friendName;
    private String friendPicUrl;
    private int addToBill;
    private Context mContext;

    public FriendCard(Context context) {
        this(context,  R.layout.friend_list_item);
        this.mContext = context;
        this.setOnClickListener(this);

    }

    public FriendCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.setOnClickListener(this);
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPicUrl() {
        return friendPicUrl;
    }

    public void setFriendPicUrl(String friendPicUrl) {
        this.friendPicUrl = friendPicUrl;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        friendPicView = (ImageView) parent.findViewById(R.id.friendProfilePic);
        friendNameView = (TextView) parent.findViewById(R.id.friendName);

        if(friendNameView != null && friendPicView != null) {
            Picasso.with(mContext).load(friendPicUrl).into(friendPicView);
            friendNameView.setText(friendName);
        }
    }

    public int getAddToBill() {
        return addToBill;
    }

    public void setAddToBill(int addToBill) {
        this.addToBill = addToBill;
    }

    @Override
    public void onClick(Card card, View view) {
        Log.i(LOG_TAG, "Clicked on friend: " + friendName);

        Bundle mBundle = new Bundle();

        if(addToBill == FriendsFragment.ADD_FRIEND_OK) {
            ((MainActivity) mContext).displayView(MainActivity.FRAGMENT_ACTIVE_DETAILS, mBundle);
        }

    }
}
