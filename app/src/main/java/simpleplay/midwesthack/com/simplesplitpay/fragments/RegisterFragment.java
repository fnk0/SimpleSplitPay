package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * @since 7/19/14.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    public static final int GOOGLE_PLUS_SIGNED_IN = 0;
    public static final int FACEBOOK_SIGNED_IN = 1;
    private MainActivity mActivity;

    private EditText nameView, emailView, passwordView, confirmPasswordView;
    private Bundle extras;

    private ProgressDialog pDialog;
    private String REQUEST_URL = "http://someurl:8000/simplepay/user/make/";
    public JSONParser jsonParser = new JSONParser();

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        mActivity = (MainActivity) getActivity();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameView = (EditText) view.findViewById(R.id.userName);
        emailView = (EditText) view.findViewById(R.id.emailAddress);
        passwordView = (EditText) view.findViewById(R.id.password);
        confirmPasswordView = (EditText) view.findViewById(R.id.confirmPassword);

        extras = getArguments();

        if(extras != null) {
            nameView.setText(extras.getString(MainActivity.PERSON_NAME));
            emailView.setText(extras.getString(MainActivity.PERSON_EMAIL));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                // TODO REGISTER THE USER WITH OUR SERVER!
                if(extras == null) {
                    extras.putString(MainActivity.PERSON_NAME, nameView.getText().toString());
                    extras.putString(MainActivity.PERSON_EMAIL, emailView.getText().toString());
                    extras.putString(MainActivity.PERSON_PHOTO_URL, null);
                }

                new DoNetWorkRequest().execute(extras.getString(MainActivity.PERSON_NAME), extras.getString(MainActivity.PERSON_EMAIL), extras.getString(MainActivity.PERSON_PHOTO_URL));


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
            pDialog.setTitle("Creating new user...");
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {

                // The parameters send to the server
                List<NameValuePair> mList = new ArrayList<NameValuePair>();
                mList.add(new BasicNameValuePair("name", params[0]));
                mList.add(new BasicNameValuePair("email", params[1]));
                mList.add(new BasicNameValuePair("photo_url", params[2]));

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
                mActivity.displayView(MainActivity.FRAGMENT_PROFILE, extras);
            } else {
                Toast.makeText(getActivity(), "Error creating new user...", Toast.LENGTH_LONG).show();
            }
        }
    }
}
