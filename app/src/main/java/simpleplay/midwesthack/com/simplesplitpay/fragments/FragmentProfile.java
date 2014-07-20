package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.facebook.Session;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import simpleplay.midwesthack.com.simplesplitpay.CreateCCActivity;
import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.cards.CardCreditCard;
import simpleplay.midwesthack.com.simplesplitpay.cards.ProfileCard;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.CreditCard;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = "Fragment Profile";
    private Button btnSignOut, addCC;
    private MainActivity mActivity;
    private LinearLayout cardsLayout;
    private ProfileCard mProfile;
    private CardListView creditCardListView;
    private ArrayList<CreditCard> mCreditCardList;

    public FragmentProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle extras = getArguments();
        mActivity = (MainActivity) getActivity();

        cardsLayout = (LinearLayout) rootView.findViewById(R.id.cardsProfile);
        btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);

        addCC = (Button) rootView.findViewById(R.id.addCreditCard);

        mProfile = new ProfileCard(getActivity());
        mProfile.setName(extras.getString(MainActivity.PERSON_NAME));
        mProfile.setPhotoUrl(extras.getString(MainActivity.PERSON_PHOTO_URL));
        mProfile.setUserBalance("267.35");
        //extras.getString(MainActivity.PERSON_EMAIL)

        CardView mCardView = new CardView(getActivity());
        mCardView.setCard(mProfile);

        cardsLayout.addView(mCardView);

        creditCardListView = (CardListView) rootView.findViewById(R.id.creditCardList);

        mCreditCardList = new ArrayList<CreditCard>();
        mCreditCardList.add(new CreditCard(CreditCard.VISA, "4879471200018945"));
        mCreditCardList.add(new CreditCard(CreditCard.MASTER, "5579471200014791"));

        List<Card> mCards = new ArrayList<Card>();

        for(CreditCard cc : mCreditCardList) {
            CardCreditCard mCard = new CardCreditCard(getActivity());
            String ccNum = cc.getCreditCardNumber();
            mCard.setCcType(cc.getCreditCardType()).setTextLast4(ccNum.substring(ccNum.length() - 4, ccNum.length()));
            mCards.add(mCard);
        }

        CardArrayAdapter mAdapter = new CardArrayAdapter(getActivity(), mCards);
        creditCardListView.setAdapter(mAdapter);

        btnSignOut.setOnClickListener(this);
        addCC.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_out:
                if(mActivity.getmGoogleServices().isConnected()) {
                    mActivity.signOutFromGplus();
                } else {
                    Session.getActiveSession().closeAndClearTokenInformation();
                }
                mActivity.displayView(MainActivity.FRAGMENT_LOGIN, null);
                break;
            case R.id.addCreditCard:
                startActivityForResult(new Intent(getActivity(), CreateCCActivity.class), CreditCard.CREATE_CREDIT_CARD_CODE);
                break;
        }
    }

}
