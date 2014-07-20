package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = "Fragment Profile";
    private HashMap<String, String> userInfo;
    private Button btnSignOut;
    private ImageView imageProfilePic;
    private TextView txtName, txtEmail;
    private MainActivity mActivity;

    public FragmentProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle extras = getArguments();
        mActivity = (MainActivity) getActivity();
        btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
        imageProfilePic = (ImageView) rootView.findViewById(R.id.profilePicture);
        txtName = (TextView) rootView.findViewById(R.id.userName);
        txtEmail = (TextView) rootView.findViewById(R.id.userEmail);

        if(extras != null) {
            Picasso.with(getActivity()).load(extras.getString(MainActivity.PERSON_PHOTO_URL)).into(imageProfilePic);
            txtName.setText(extras.getString(MainActivity.PERSON_NAME));
            txtEmail.setText(extras.getString(MainActivity.PERSON_EMAIL));
        }

        btnSignOut.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_out:
                if(mActivity.getmGoogleServices().isConnected()) {
                    mActivity.signOutFromGplus();
                } else {
                    Session.getActiveSession().closeAndClearTokenInformation();
                }
                mActivity.displayView(MainActivity.FRAGMENT_LOGIN, null);
                break;
        }
    }

}
