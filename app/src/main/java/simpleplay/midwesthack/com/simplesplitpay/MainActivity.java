package simpleplay.midwesthack.com.simplesplitpay;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.HashMap;


public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static final String LOG_TAG = "MAIN ACTIVITY";
    /**
     * Google Plus Sign-In Variables
     */
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleServices;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // Nav Drawer and app Title
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleServices = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        if(mGoogleServices.isConnected()) {
            Log.i(LOG_TAG, "User is connected");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleServices.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleServices.isConnected()) {
            mGoogleServices.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        // TODO retrieve user info and update UI

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleServices.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if(!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }

        if(!mIntentInProgress) {
            mConnectionResult = connectionResult;

            if(mSignInClicked) {

            }
        }
    }

    private void updateUI(boolean update) {

    }

    private void signInWithGlLus() {
        if(mGoogleServices.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    private void signOutFromGplus() {
        if(mGoogleServices.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleServices);
            mGoogleServices.disconnect();
            mGoogleServices.connect();
            updateUI(false);
        }
    }

    private void revokeGplusAccess() {
        if(mGoogleServices.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleServices);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleServices)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.i(LOG_TAG, "User access revoked");
                            mGoogleServices.connect();
                            updateUI(false);
                        }
                    });
        }
    }

    public void resolveSignInError() {
        if(mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException ex) {
                mIntentInProgress = false;
                mGoogleServices.connect();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_SIGN_IN) {
            if(resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if(!mGoogleServices.isConnecting()) {
                mGoogleServices.connect();
            }
        }
    }

    public HashMap<String, Object> getProfileInformation() {
        HashMap<String, Object> userProfile = new HashMap<String, Object>();

        try {
            if(Plus.PeopleApi.getCurrentPerson(mGoogleServices) != null) {
                Person mCurrentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleServices);
                String personName = mCurrentPerson.getDisplayName();
                String personPhotoUrl = mCurrentPerson.getImage().getUrl();
                String personGooglePlusProfile = mCurrentPerson.getUrl();
                final String personEmail = Plus.AccountApi.getAccountName(mGoogleServices);

                Log.i(LOG_TAG, "Person Name: " + personName);
                Log.i(LOG_TAG, "Person Photo URL: " + personPhotoUrl);
                Log.i(LOG_TAG, "Person G+ Profile: " + personGooglePlusProfile);
                Log.i(LOG_TAG, "Person E-Mail: " + personEmail);

                userProfile.put("personName", personName);
                userProfile.put("personPhotoUrl", personPhotoUrl);
                userProfile.put("personG+", personGooglePlusProfile);
                userProfile.put("personEmail", personEmail);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we 2want by
                // replacing sz=X
                //personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() -2) + PROFILE_PIC_SIZE;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return userProfile;
    }
}
