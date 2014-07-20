package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.cards.ActiveBillCard;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.Bill;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class FragmentActiveBillsList extends Fragment implements View.OnClickListener{

    private CardListView mListView;
    private ArrayList<Bill> mBillsList;
    private ArrayList<Card> mCardsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active_bills, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardsList = new ArrayList<Card>();
        mBillsList = new ArrayList<Bill>();
        mListView = (CardListView) view.findViewById(R.id.activeBillsList);
        mBillsList.add(new Bill("Description lalalala", "Restaurant Bill - $180.17", Bill.BILL_OK));
        mBillsList.add(new Bill("Description lalalala", "Tickets Bill - $632.60", Bill.BILL_BAD));
        mBillsList.add(new Bill("Description lalalala", "Party Bus - $1020.38", Bill.BILL_OK));

        for(Bill bill : mBillsList) {
            ActiveBillCard mCard = new ActiveBillCard(getActivity());
            mCard.setBillDetails(bill.getBillDetails());
            mCard.setBillStatus(bill.getBillStatus());
            mCardsList.add(mCard);
        }

        CardArrayAdapter mAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
