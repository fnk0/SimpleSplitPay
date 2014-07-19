package simpleplay.midwesthack.com.simplesplitpay;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.TypedArray;
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
import java.util.HashMap;

import simpleplay.midwesthack.com.simplesplitpay.adapters.NavDrawerItem;
import simpleplay.midwesthack.com.simplesplitpay.adapters.NavDrawerListAdapter;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentLogin;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FragmentProfile;
import simpleplay.midwesthack.com.simplesplitpay.fragments.FriendsFragment;


public class MainActivity extends FragmentActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
            ResultCallback<People.LoadPeopleResult> {

    public static final String LOG_TAG = "MAIN ACTIVITY";

    public static final int FRAGMENT_LOGIN = 0;
    public static final int FRAGMENT_PROFILE = 1;
    public static final int FRIENDS_FRAGMENT = 2;

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
        setContentView(R.layout.activity_main);
        Thread.currentThread().setContextClassLoader(MainActivity.class.getClassLoader());

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
        getActionBar().setDisplayHomeAsUpEnabled(true);

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
                displayView(FRAGMENT_PROFILE, null);
            } else {
                displayView(FRAGMENT_LOGIN, null);
            }
        }
    }

    public void displayView(int position, Bundle fragBundle) {
        activeFragment = null;

        switch (position) {
            case FRAGMENT_LOGIN:
                activeFragment = new FragmentLogin();
                break;
            case FRAGMENT_PROFILE:
                activeFragment = new FragmentProfile();
                break;
            case FRIENDS_FRAGMENT:
                activeFragment = new FriendsFragment();
                break;
            default:
                break;
        }

        if(activeFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, activeFragment).commit();
            setTitle(navMenuTitles[position]);
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
        } else {
            Log.e(LOG_TAG, "Error creating fragment!");
        }

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
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
            displayView(FRAGMENT_PROFILE, null);
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


}
