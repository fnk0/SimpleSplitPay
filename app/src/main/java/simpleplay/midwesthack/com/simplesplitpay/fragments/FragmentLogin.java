package simpleplay.midwesthack.com.simplesplitpay.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

import simpleplay.midwesthack.com.simplesplitpay.MainActivity;
import simpleplay.midwesthack.com.simplesplitpay.R;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 7/19/14.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener {

    private String LOG_TAG = "Fragment Login";
    private LoginButton facebookLogin;

    public FragmentLogin() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        rootView.findViewById(R.id.sign_in_button).setOnClickListener(this);
        ArrayList<String> permissions = new ArrayList<String>();

        permissions.add("email");
        permissions.add("user_friends");

        facebookLogin = (LoginButton) rootView.findViewById(R.id.facebook_login);
        facebookLogin.setReadPermissions(permissions);
        facebookLogin.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser graphUser) {
                if(graphUser != null) {
                    Bundle extras = new Bundle();
                    Log.i(LOG_TAG, "https://graph.facebook.com/" + graphUser.getId() + "/picture?type=large");
                    String profilePicture = "https://graph.facebook.com/" + graphUser.getId() + "/picture?type=large";
                    extras.putString(MainActivity.PERSON_NAME, (graphUser.getFirstName() + graphUser.getLastName()));
                    Log.i(LOG_TAG, "Email: " + graphUser.asMap().get("email").toString());
                    extras.putString(MainActivity.PERSON_EMAIL, graphUser.asMap().get("email").toString());
                    extras.putString(MainActivity.PERSON_PHOTO_URL, profilePicture);
                    Log.i(LOG_TAG, "Photo Url Size: " + profilePicture.length());
                    getFacebookFriends();
                    ((MainActivity) getActivity()).displayView(MainActivity.FRAGMENT_PROFILE, extras);
                } else {
                    Log.i(LOG_TAG, "Facebook User Failed!");
                }
            }
        });

        return rootView;
    }

    private void getFacebookFriends() {
        Session activeSession = Session.getActiveSession();
        if(activeSession.getState().isOpened()) {
            Request mRequest = Request.newMyFriendsRequest(activeSession, new Request.GraphUserListCallback() {
                @Override
                public void onCompleted(List<GraphUser> users, Response response) {
                    if (response.getError() == null) {
                        Log.i(LOG_TAG, "Inside Friend Request...");
                        Log.i(LOG_TAG, "Users size..." + users.size());
                        for (int i = 0; i < users.size(); i++) {
                            Log.i("users", "users " + users.get(i).getName());
                        }
                    } else {
                        Toast.makeText(getActivity(), response.getError().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mRequest.executeAsync();
        } else {
            Log.i(LOG_TAG, "No Session is Active...");
        }
    }

    @Override
    public void onClick(View v) {
        Log.i(LOG_TAG, "Logging in...");

        switch (v.getId()) {
            case R.id.sign_in_button:
                ((MainActivity) getActivity()).signInWithGlLus();
                break;
            case R.id.register:

                break;
        }

    }
}
