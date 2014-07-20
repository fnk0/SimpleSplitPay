package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class ProfileCard extends Card {

    private String name, photoUrl, userBalance;
    private ImageView profilePicView;
    private TextView userNameView, balanceView;
    private Context mContext;

    public ProfileCard(Context context) {
        this(context, R.layout.card_profile);
        this.mContext = context;
    }

    public ProfileCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        profilePicView = (ImageView) parent.findViewById(R.id.profilePicture);
        userNameView = (TextView) parent.findViewById(R.id.userName);
        balanceView = (TextView) parent.findViewById(R.id.balanceView);

        if(profilePicView != null && photoUrl != null) {
            Picasso.with(mContext).load(photoUrl).into(profilePicView);
        }

        if(userNameView != null && name != null) {
            userNameView.setText(name);
        }

        if(balanceView != null && userBalance != null) {
            balanceView.setText(userBalance);
        }

    }

    public String getName() {
        return name;
    }

    public ProfileCard setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ProfileCard setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public ProfileCard setUserBalance(String userBalance) {
        this.userBalance = userBalance;
        return this;
    }
}
