package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class TransactionCard extends Card{

    private String value, date, description;

    public TransactionCard(Context context) {
        super(context, R.layout.card_transactions);
    }

    public TransactionCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        TextView valueView = (TextView) parent.findViewById(R.id.transactionValue);
        TextView dateView = (TextView) parent.findViewById(R.id.transactionDate);
        TextView descriptionView = (TextView) parent.findViewById(R.id.transactionDescription);

        valueView.setText(value);
        dateView.setText(date);
        descriptionView.setText(description);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
