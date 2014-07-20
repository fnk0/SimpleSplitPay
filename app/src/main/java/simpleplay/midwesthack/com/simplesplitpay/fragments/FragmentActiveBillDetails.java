package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import it.gmariotti.cardslib.library.view.CardView;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.cards.ActiveBillDetailsCard;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.Bill;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class FragmentActiveBillDetails extends Fragment {

    public FragmentActiveBillDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extras = getArguments();

        ActiveBillDetailsCard activeCard = new ActiveBillDetailsCard(getActivity());
        activeCard.setValue(extras.getString(Bill.BILL_VALUE));
        activeCard.setDescription(extras.getString(Bill.BILL_DESCRIPTION));

        CardView mCardView = new CardView(getActivity());
        mCardView.setCard(activeCard);

        LinearLayout mActiveBillLayout = (LinearLayout) view.findViewById(R.id.layoutCardDetails);
        mActiveBillLayout.addView(mCardView);


    }
}
