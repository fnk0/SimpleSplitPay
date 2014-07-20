package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.json.JSONParser;

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

    private ProgressDialog pDialog;
    private String REQUEST_URL = "http://someurl:8000/simplepay/bill/make/";
    public JSONParser jsonParser = new JSONParser();

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

                String billAmount = billAmmountView.getText().toString();
                String tokenText = token.getText().toString();
                String description = descriptionView.getText().toString();
                // TODO ADD CALL TO BACKEND

                new DoNetWorkRequest().execute(billAmount, tokenText, description);
                break;
        }
    }

    /**
     * Request is done in a Async task otherwise it throws a Network on Main thread exception
     */
    private class DoNetWorkRequest extends AsyncTask<String, Void, Boolean> {

        /**
         * This sets a nice Dialog to the user telling the user that we are doing something
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setTitle("Creating new bill...");
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {

                // The parameters send to the server
                List<NameValuePair> mList = new ArrayList<NameValuePair>();
                mList.add(new BasicNameValuePair("bill_amount", params[0]));
                mList.add(new BasicNameValuePair("token_text", params[1]));
                mList.add(new BasicNameValuePair("description", params[2]));

                // The actual httpRequest to the server. Returns a JSON object with the data grom the server
                JSONObject json = jsonParser.makeHttpRequest(REQUEST_URL, "POST", mList);

                // Success code to know if we were successfull
                String success = json.getString("Success");

                if(success.equals("Success")) {
                    // Handle the data received.
                    // In this case a post request so we just return true to go direct to onPostExecute
                    return true;
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            return null;
        }

        /**
         * Handles any UI Updates and deals with the Data from the Request
         * @param aBoolean
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(!aBoolean) {
                ((MainActivity)getActivity()).displayView(MainActivity.FRAGMENT_PROFILE, null);
            } else {
                Toast.makeText(getActivity(), "Error creating new bill...", Toast.LENGTH_LONG).show();
            }
        }
    }
}
