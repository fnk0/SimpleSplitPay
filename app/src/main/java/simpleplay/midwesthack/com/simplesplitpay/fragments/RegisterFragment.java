package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;

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
                mActivity.displayView(MainActivity.FRAGMENT_PROFILE, extras);
                break;
        }
    }
}
