package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.Transaction;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class ActiveBills extends Fragment implements View.OnClickListener{

    private CardListView mListView;
    private ArrayList<Transaction> mTransactionsList;
    private ArrayList<Card> mCardsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active_bills, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
