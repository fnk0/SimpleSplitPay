package simpleplay.midwesthack.com.simplesplitpay.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.CreditCard;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class CardCreditCard extends Card {

    private ImageView creditCardImage;
    private TextView ccLast4;
    private String ccType, textLast4;

    public CardCreditCard(Context context) {
        super(context, R.layout.card_cc);
    }

    public CardCreditCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public String getCcType() {
        return ccType;
    }

    public CardCreditCard setCcType(String ccType) {
        this.ccType = ccType;
        return this;
    }

    public String getTextLast4() {
        return textLast4;
    }

    public CardCreditCard setTextLast4(String textLast4) {
        this.textLast4 = textLast4;
        return this;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);
        
        ccLast4 = (TextView) parent.findViewById(R.id.ccLast4);
        creditCardImage = (ImageView) parent.findViewById(R.id.ccImage);

        if(ccType.equals(CreditCard.VISA)) {
            creditCardImage.setImageResource(R.drawable.ic_visa);
        } else if(ccType.equals(CreditCard.MASTER)) {
            creditCardImage.setImageResource(R.drawable.ic_mastercard);
        } else if(ccType.equals(CreditCard.AMEX)) {
            creditCardImage.setImageResource(R.drawable.ic_amex);
        } else if(ccType.equals(CreditCard.DISCOVER)) {
            creditCardImage.setImageResource(R.drawable.ic_discover);
        }
        ccLast4.setText(textLast4);
    }
}
