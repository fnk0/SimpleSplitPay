package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.Bill;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class ActiveBillCard extends Card implements View.OnClickListener {

    private String billDetails;
    private int billStatus;


    public ActiveBillCard(Context context) {
        super(context, R.layout.card_active_bill);
    }

    public ActiveBillCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        ImageView statusView = (ImageView) parent.findViewById(R.id.activeStatus);
        TextView detailsView = (TextView) parent.findViewById(R.id.transactionDetails);

        if(billStatus == Bill.BILL_OK) {
            statusView.setImageResource(R.drawable.ic_smiley);
        } else {
            statusView.setImageResource(R.drawable.ic_sad);
        }

        detailsView.setText(billDetails);

    }

    public String getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(String billDetails) {
        this.billDetails = billDetails;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    @Override
    public void onClick(View v) {

    }
}
