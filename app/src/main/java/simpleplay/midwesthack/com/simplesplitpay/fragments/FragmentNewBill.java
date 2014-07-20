package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import simpleplay.midwesthack.com.simplesplitpay.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/20/14.
 */
public class FragmentNewBill extends Fragment implements View.OnClickListener {

    private EditText billAmmountView;
    private EditText token;
    private EditText descriptionView;
    private Button createBill;

    public FragmentNewBill() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newbill, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        billAmmountView = (EditText) view.findViewById(R.id.totalAmount);
        token = (EditText) view.findViewById(R.id.token);
        descriptionView = (EditText) view.findViewById(R.id.description);

        createBill = (Button) view.findViewById(R.id.createBill);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createBill:

                String billAmmount = billAmmountView.getText().toString();
                String tokenText = token.getText().toString();
                String description = descriptionView.getText().toString();
                // TODO ADD CALL TO BACKEND
                break;
        }
    }
}
