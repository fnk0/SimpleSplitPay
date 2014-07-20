package simpleplay.midwesthack.com.simplesplitpay;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import simpleplay.midwesthack.com.simplesplitpay.adapters.NavDrawerItem;
import simpleplay.midwesthack.com.simplesplitpay.adapters.NavDrawerListAdapter;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentActiveBillDetails;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentActiveBillsList;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentLogin;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentNewBill;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentProfile;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentTransactions;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FriendsFragment;
import simpleplay.midwesthack.com.simplesplitpay.fragments.RegisterFragment;


public class MainActivity extends FragmentActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
            ResultCallback<People.LoadPeopleResult> {

    public static final String LOG_TAG = "MAIN ACTIVITY";

    public static final int FRAGMENT_PROFILE = 0;
    public static final int FRIENDS_FRAGMENT = 1;
    public static final int NEWBILL_FRAGMENT = 2;
    public static final int ACTIVE_BILLS_FRAG = 3;
    public static final int TRASACTIONS_FRAGMENT = 4;
    public static final int ABOUT_FRAGMENT = 5;

    public static final int FRAGMENT_LOGIN = 9;
    public static final int FRAGMENT_REGISTER = 10;
    public static final int FRAGMENT_ACTIVE_DETAILS = 11;

    /**
     * Google Plus Sign-In Variables
     */
    private static final int RC_SIGN_IN = 0;
    private static final int PROFILE_PIC_SIZE = 300;
    public static final String PERSON_NAME = "personName";
    public static final String PERSON_PHOTO_URL = "personPhotoUrl";
    public static final String PERSON_GPLUS_PROFILE = "personG";
    public static final String PERSON_EMAIL = "personEmail";
    private GoogleApiClient mGoogleServices;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * Facebook Stuff
     */
    private UiLifecycleHelper mFacebookHelper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");


    // Nav Drawer and app Title
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    // Nav Drawer Elements
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> mNavDrawerItems;
    private NavDrawerListAdapter mNavAdapter;
    private Fragment activeFragment;
    private HashMap<String, String> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFacebookHelper = new UiLifecycleHelper(this, statusCallback);
        mFacebookHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // load nav drawer items
        navMenuTitles = getResources().getStringArray(R.array.drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.drawer_icons);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerItems = new ArrayList<NavDrawerItem>();

        for(int i = 0; i < navMenuTitles.length; i++) {
            mNavDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }

        navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new DrawerListener());

        mNavAdapter = new NavDrawerListAdapter(getApplicationContext(), mNavDrawerItems);
        mDrawerList.setAdapter(mNavAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mGoogleServices = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
        if(mGoogleServices.isConnected()) {
            Log.i(LOG_TAG, "User is connected");
        }

        if(savedInstanceState == null) {
            if(mGoogleServices.isConnected()) {
                updateUI(true);
            } else {
                displayView(FRAGMENT_LOGIN, null);
            }
        }
    }

    public void displayView(int position, Bundle fragBundle) {
        activeFragment = null;
        boolean displayToggle = true;

        switch (position) {
            case FRAGMENT_LOGIN:
                activeFragment = new FragmentLogin();
                displayToggle = false;
                break;
            case FRAGMENT_PROFILE:
                fragBundle = new Bundle();
                fragBundle.putString(MainActivity.PERSON_NAME, getProfileInformation().get(MainActivity.PERSON_NAME));
                fragBundle.putString(MainActivity.PERSON_EMAIL, getProfileInformation().get(MainActivity.PERSON_EMAIL));
                fragBundle.putString(MainActivity.PERSON_PHOTO_URL, getProfileInformation().get(MainActivity.PERSON_PHOTO_URL));
                activeFragment = new FragmentProfile();
                break;
            case FRIENDS_FRAGMENT:
                activeFragment = new FriendsFragment();
                break;
            case NEWBILL_FRAGMENT:
                activeFragment = new FragmentNewBill();
                break;
            case TRASACTIONS_FRAGMENT:
                activeFragment = new FragmentTransactions();
                break;
            case FRAGMENT_REGISTER:
                activeFragment = new RegisterFragment();
                displayToggle = false;
                break;
            case ACTIVE_BILLS_FRAG:
                activeFragment = new FragmentActiveBillsList();
                break;
            case FRAGMENT_ACTIVE_DETAILS:
                activeFragment = new FragmentActiveBillDetails();
                break;
            default:
                break;
        }

        if(displayToggle) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }

        if(activeFragment != null) {

            if(fragBundle != null) {
                activeFragment.setArguments(fragBundle);
            }

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, activeFragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);

            if(position == FRAGMENT_REGISTER) {
                setTitle("Register");
            } else if(position == FRAGMENT_LOGIN) {
                setTitle("Login");
            } else {
                setTitle(navMenuTitles[position]);
            }

        } else {
            Log.e(LOG_TAG, "Error creating fragment!");
        }

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFacebookHelper.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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

        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
    protected void onPause() {
        super.onPause();
        mFacebookHelper.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Plus.PeopleApi.loadVisible(mGoogleServices, null).setResultCallback(this);
        getProfileInformation();
        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleServices.connect();
        updateUI(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFacebookHelper.onDestroy();
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
                resolveSignInError();
            }
        }
    }

    public void updateUI(boolean update) {
        if(update) {
            Bundle extras = new Bundle();
            extras.putString(MainActivity.PERSON_NAME, getProfileInformation().get(MainActivity.PERSON_NAME));
            extras.putString(MainActivity.PERSON_EMAIL, getProfileInformation().get(MainActivity.PERSON_EMAIL));
            extras.putString(MainActivity.PERSON_PHOTO_URL, getProfileInformation().get(MainActivity.PERSON_PHOTO_URL));
            displayView(FRAGMENT_PROFILE, extras);
        }
    }

    public void signInWithGlLus() {
        Log.i(LOG_TAG, "Logging in...");
        if(!mGoogleServices.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

    }

    public void signOutFromGplus() {
        if(mGoogleServices.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleServices);
            mGoogleServices.disconnect();
            mGoogleServices.connect();
            updateUI(false);
        }
    }

    public void revokeGplusAccess() {
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
        Log.i(LOG_TAG, "Resolving sign in error...");
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFacebookHelper.onSaveInstanceState(outState);
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

        mFacebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    public GoogleApiClient getmGoogleServices() {
        return mGoogleServices;
    }

    public HashMap<String, String> getProfileInformation() {
        HashMap<String, String> userProfile = new HashMap<String, String>();

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

                userProfile.put(PERSON_NAME, personName);
                userProfile.put(PERSON_GPLUS_PROFILE + "+", personGooglePlusProfile);
                userProfile.put(PERSON_EMAIL, personEmail);
                personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() -2) + PROFILE_PIC_SIZE;
                Log.i(LOG_TAG, "Photo URL Length: " + personPhotoUrl.length());
                userProfile.put(PERSON_PHOTO_URL, personPhotoUrl);

                //new LoadProfileImage(imageProfilePic).execute(personPhotoUrl);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return userProfile;
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        friendsList = new HashMap<String, String>();
        if(loadPeopleResult.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

            try {
                int count = personBuffer.getCount();

                for (int i = 0; i < count; i++) {
                    friendsList.put(personBuffer.get(i).getDisplayName(), personBuffer.get(i).getImage().getUrl());
                    //Log.i(LOG_TAG, "Display name: " + personBuffer.get(i).getDisplayName() + " --- Picture: " + personBuffer.get(i).getImage().getUrl());
                    /*
                    if(personBuffer.get(i).getImage().getUrl() != null) {
                        Log.i(LOG_TAG, "Person URL Len: " + personBuffer.get(i).getImage().getUrl().length());
                    }
                    */

                }
            } finally {
                personBuffer.close();
            }
        } else {
            Log.e(LOG_TAG, "Error requesting data..." + loadPeopleResult.getStatus());
        }
    }

    public HashMap<String, String> getFriends() {
        return friendsList;
    }

    private class DrawerListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position, null);
        }
    }

    public Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState sessionState, Exception e) {
            if (sessionState.isOpened()) {
                Log.i(LOG_TAG, "Facebook Logged in!");
            } else {
                Log.i(LOG_TAG, "Facebook Logged Out!");
                displayView(FRAGMENT_LOGIN, null);
            }
         }
    };


}
