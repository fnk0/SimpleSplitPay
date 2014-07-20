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
import simpleplay.midwesthack.com.simplesplitpay.cards.TransactionCard;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.Transaction;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class FragmentTransactions extends Fragment {

    private CardListView mListView;
    private ArrayList<Transaction> mTransactionsList;
    private ArrayList<Card> mCardsList;

    public FragmentTransactions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCardsList = new ArrayList<Card>();
        mTransactionsList = new ArrayList<Transaction>();
        mListView = (CardListView) view.findViewById(R.id.transactionsList);
        mTransactionsList.add(new Transaction("70.47", "Jul 18, 2014", "Fancy restaurant bill..."));
        mTransactionsList.add(new Transaction("1080.64", "Jul 07, 2014", "Party bus with the bros!"));
        mTransactionsList.add(new Transaction("479.35", "Jun 26, 2014", "Concert tickets for the Festival awesome"));

        for(Transaction tr : mTransactionsList) {
            TransactionCard mCard = new TransactionCard(getActivity());
            mCard.setValue(tr.getValue());
            mCard.setDate(tr.getDate());
            mCard.setDescription(tr.getDescription());
            mCardsList.add(mCard);
        }

        CardArrayAdapter mAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mListView.setAdapter(mAdapter);
    }
}
