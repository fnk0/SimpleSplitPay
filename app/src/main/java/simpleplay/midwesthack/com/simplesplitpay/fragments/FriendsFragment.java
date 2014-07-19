package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.cards.FriendCard;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FriendsFragment extends Fragment {

    private String LOG_TAG = "Friends Fragment";
    private List<Card> mCardList;
    private CardListView mFriendsList;
    private CardArrayAdapter mAdapter;
    private MainActivity mActivity;
    private HashMap<String, String> friendList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        mActivity = (MainActivity) getActivity();
        friendList = mActivity.getFriends();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFriendsList = (CardListView) view.findViewById(R.id.friendList);
        mCardList = new ArrayList<Card>();

        Iterator it = friendList.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            FriendCard mCard = new FriendCard(getActivity());
            mCard.setFriendName(entry.getKey().toString());
            if(entry.getValue() != null) {
                //Log.i(LOG_TAG, entry.getValue().toString());
                mCard.setFriendPicUrl(entry.getValue().toString());
            }
            it.remove();
            mCardList.add(mCard);
        }
        mAdapter = new CardArrayAdapter(getActivity(), mCardList);
        mFriendsList.setAdapter(mAdapter);
    }
}
