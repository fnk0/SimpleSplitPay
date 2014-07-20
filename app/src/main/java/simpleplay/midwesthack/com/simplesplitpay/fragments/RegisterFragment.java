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
public class RegisterFragment extends Fragment {

    public static final int GOOGLE_PLUS_SIGNED_IN = 0;
    public static final int FACEBOOK_SIGNED_IN = 1;

    private EditText nameView, emailView, passwordView, confirmPasswordView;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameView = (EditText) view.findViewById(R.id.userName);
        emailView = (EditText) view.findViewById(R.id.emailAddress);
        passwordView = (EditText) view.findViewById(R.id.password);
        confirmPasswordView = (EditText) view.findViewById(R.id.confirmPassword);

        Bundle extras = getArguments();

        if(extras != null) {
            nameView.setText(extras.getString(MainActivity.PERSON_NAME));
            emailView.setText(extras.getString(MainActivity.PERSON_EMAIL));
        }
    }
}
