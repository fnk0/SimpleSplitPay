package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;
import simpleplay.midwesthack.com.simplesplitpay.async.LoadProfileImage;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FragmentProfile extends Fragment implements View.OnClickListener {

    private HashMap<String, String> userInfo;
    private Button btnSignOut, btnRevokeAccess;
    private ImageView imageProfilePic;
    private TextView txtName, txtEmail;
    private MainActivity mActivity;

    public FragmentProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mActivity = (MainActivity) getActivity();
        userInfo = mActivity.getProfileInformation();

        btnSignOut = (Button) rootView.findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) rootView.findViewById(R.id.btn_revoke_access);
        imageProfilePic = (ImageView) rootView.findViewById(R.id.profilePicture);
        txtName = (TextView) rootView.findViewById(R.id.userName);
        txtEmail = (TextView) rootView.findViewById(R.id.userEmail);

        LoadProfileImage loadProfileImage = new LoadProfileImage(imageProfilePic);
        loadProfileImage.execute(userInfo.get(MainActivity.PERSON_PHOTO_URL));

        txtName.setText(userInfo.get(MainActivity.PERSON_NAME));
        txtEmail.setText(userInfo.get(MainActivity.PERSON_EMAIL));

        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_out:
                mActivity.signOutFromGplus();
                mActivity.displayView(MainActivity.FRAGMENT_LOGIN, null);
                break;
            case R.id.btn_revoke_access:
                mActivity.revokeGplusAccess();
                mActivity.displayView(MainActivity.FRAGMENT_LOGIN, null);
                break;
        }
    }
}
