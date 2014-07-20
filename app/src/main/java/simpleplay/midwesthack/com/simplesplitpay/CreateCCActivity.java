package simpleplay.midwesthack.com.simplesplitpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import simpleplay.midwesthack.com.simplesplitpay.dataModel.Address;
import simpleplay.midwesthack.com.simplesplitpay.dataModel.CreditCard;

public class CreateCCActivity extends Activity {

    public static final String UNDEFINED = "undefined";
    private EditText ccNumber, ccCSV, ccMon, ccYear, billingLine1, billingLine2, billingZipCode, billingCity, billingState;
    private String creditCardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cc);

        ccNumber = (EditText) findViewById(R.id.cc_num);
        ccCSV = (EditText) findViewById(R.id.cc_csv);
        ccMon = (EditText) findViewById(R.id.cc_mon);
        ccYear = (EditText) findViewById(R.id.cc_year);
        billingLine1 = (EditText) findViewById(R.id.addressLine1);
        billingLine2 = (EditText) findViewById(R.id.addressLine2);
        billingZipCode = (EditText) findViewById(R.id.zipCode);
        billingCity = (EditText) findViewById(R.id.city);
        billingState = (EditText) findViewById(R.id.state);

        ccNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if(text.startsWith("4")) {
                    ccNumber.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_visa), null, null, null);
                    creditCardType = CreditCard.VISA;
                } else if(text.isEmpty()) {
                    ccNumber.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_cc), null, null, null);
                    creditCardType = UNDEFINED;
                } else if(text.startsWith("6")) {
                    ccNumber.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_discover), null, null, null);
                    creditCardType = CreditCard.DISCOVER;
                } else if(text.startsWith("5")) {
                    ccNumber.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_mastercard), null, null, null);
                    creditCardType = CreditCard.MASTER;
                } else if(text.length() > 1) {
                    if(text.substring(0, 2).equals("34") || text.substring(0, 2).equals("37")) {
                        ccNumber.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_amex), null, null, null);
                        creditCardType = CreditCard.AMEX;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_cc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCreditCard(View view) {
        CreditCard mCard = new CreditCard();
        mCard.setCreditCardType(creditCardType);
        mCard.setCreditCardNumber(ccNumber.getText().toString());
        mCard.setCreditCardCSV(ccCSV.getText().toString());
        mCard.setCreditCardExpMonth(ccMon.getText().toString());
        mCard.setCreditCardExpYear(ccYear.getText().toString());

        Address mAddress = new Address();
        mAddress.setAddressLine1(billingLine1.getText().toString());
        mAddress.setAddressLine2(billingLine2.getText().toString());
        mAddress.setCity(billingCity.getText().toString());
        mAddress.setState(billingState.getText().toString());
        mAddress.setZipCode(billingZipCode.getText().toString());

        mCard.setBillingAddress(mAddress);

        boolean addCC = mCard.addCreditCard();

        if(addCC) {
            Intent returnIntent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "There was an error adding your Credit Card", Toast.LENGTH_LONG).show();
        }
    }
}
