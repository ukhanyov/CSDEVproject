package com.example.admin_linux.csdevproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.databinding.ActivityMainBinding;
import com.example.admin_linux.csdevproject.fragments.ChatFragment;
import com.example.admin_linux.csdevproject.fragments.CropStreamFragment;
import com.example.admin_linux.csdevproject.fragments.FavoritesFragment;
import com.example.admin_linux.csdevproject.fragments.SearchFragment;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.model.FireBaseUserModel;
import com.example.admin_linux.csdevproject.network.pojo.register_device.RDResponse;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        ChatFragment.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener {


    // Fancy dataBinding
    ActivityMainBinding mBinding;

    private String mFirebaseToken;

    private Boolean isFabOpen = false;

    private Animation
            fab_title_open_1, fab_title_open_2, fab_title_open_3, fab_title_open_4,
            fab_open_1, fab_open_2, fab_open_3, fab_open_4,
            fab_title_close_1, fab_title_close_2, fab_title_close_3, fab_title_close_4,
            fab_close_1, fab_close_2, fab_close_3, fab_close_4,
            rotate_forward, rotate_backward;

    // Fragment stuff
    FragmentManager fragmentManager;

    BottomNavigationView bottomNavigationView;

    //private CropStreamMessageViewModel viewModel;
    private CropStreamFragment fragmentCropStreamTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupNotifications();

        Intent intent = getIntent();
        String mUserFirebaseId = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_ID);
        String mUserFirebasePhoneNumber = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER);
        mFirebaseToken = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_TOKEN);

        // Toolbar
        Toolbar mToolbar = mBinding.layoutToolbar.toolbar;
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Bottom navigation
        bottomNavigationView = mBinding.bottomNavigation;
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Animations init
        initAnimations();

        // Click listeners for FAB
        mBinding.fab.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentCropStreamTransaction = new CropStreamFragment();

        // Toolbar title
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));

        fetchUserData(mUserFirebaseId, mUserFirebasePhoneNumber);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_cropstream:
                starCropStreamFragment();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));
                makeDefaultToolbarVisible();
                break;

            case R.id.action_chat:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new ChatFragment())
                        .commit();

                makeChatToolbarVisible();
                break;

            case R.id.action_favorites:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new FavoritesFragment())
                        .commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_favorites));
                makeDefaultToolbarVisible();
                break;

            case R.id.action_search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new SearchFragment())
                        .commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_search));
                makeDefaultToolbarVisible();
                break;

        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, "CropStreamFragment", fragmentCropStreamTransaction);
    }

    private void initAnimations() {
        fab_title_open_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_title_open_1);
        fab_title_open_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_title_open_2);
        fab_title_open_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_title_open_3);
        fab_title_open_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_title_open_4);
        fab_open_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_1);
        fab_open_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_2);
        fab_open_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_3);
        fab_open_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_4);

        fab_title_close_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_title_1);
        fab_title_close_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_title_2);
        fab_title_close_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_title_3);
        fab_title_close_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_title_4);
        fab_close_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_1);
        fab_close_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_2);
        fab_close_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_3);
        fab_close_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_4);

        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
    }

    private void animateFAB() {

        if (isFabOpen) {

            mBinding.fab.startAnimation(rotate_backward);
            mBinding.fab1.startAnimation(fab_close_4);
            mBinding.fab2.startAnimation(fab_close_3);
            mBinding.fab3.startAnimation(fab_close_2);
            mBinding.fab4.startAnimation(fab_close_1);

            mBinding.fab1.setClickable(false);
            mBinding.fab2.setClickable(false);
            mBinding.fab3.setClickable(false);
            mBinding.fab4.setClickable(false);
            isFabOpen = false;

            mBinding.tvFavTitle1.startAnimation(fab_title_close_4);
            mBinding.tvFavTitle2.startAnimation(fab_title_close_3);
            mBinding.tvFavTitle3.startAnimation(fab_title_close_2);
            mBinding.tvFavTitle4.startAnimation(fab_title_close_1);

            mBinding.tvFavTitle1.setVisibility(View.GONE);
            mBinding.tvFavTitle2.setVisibility(View.GONE);
            mBinding.tvFavTitle3.setVisibility(View.GONE);
            mBinding.tvFavTitle4.setVisibility(View.GONE);
        } else {

            mBinding.fab.startAnimation(rotate_forward);
            mBinding.fab1.startAnimation(fab_open_1);
            mBinding.fab2.startAnimation(fab_open_2);
            mBinding.fab3.startAnimation(fab_open_3);
            mBinding.fab4.startAnimation(fab_open_4);

            mBinding.fab1.setClickable(true);
            mBinding.fab2.setClickable(true);
            mBinding.fab3.setClickable(true);
            mBinding.fab4.setClickable(true);
            isFabOpen = true;

            mBinding.tvFavTitle1.startAnimation(fab_title_open_1);
            mBinding.tvFavTitle2.startAnimation(fab_title_open_2);
            mBinding.tvFavTitle3.startAnimation(fab_title_open_3);
            mBinding.tvFavTitle4.startAnimation(fab_title_open_4);

            mBinding.tvFavTitle1.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle2.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle3.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle4.setVisibility(View.VISIBLE);
        }
    }

    private void fetchUserData(String userFirebaseId, String phoneNumber) {
        if (userFirebaseId != null && !userFirebaseId.equals("") &&
                phoneNumber != null && !phoneNumber.equals("")) {

            GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
            Call<FirebaseUserReturnValue> parsedJSON = service.getUserGetByFireBaseId(
                    userFirebaseId,
                    phoneNumber);

            parsedJSON.enqueue(new Callback<FirebaseUserReturnValue>() {
                @Override
                public void onResponse(@NonNull Call<FirebaseUserReturnValue> call, @NonNull Response<FirebaseUserReturnValue> response) {
                    FirebaseUserReturnValue returnValue = response.body();
                    FireBaseUserModel userModel = Objects.requireNonNull(returnValue).getFireBaseUserModel();

                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString(Constants.PREF_PROFILE_IMAGE_URL, userModel.getProfileImageUrl());
                    editor.putString(Constants.PREF_PROFILE_FULL_NAME, userModel.getFirstName() + " " + userModel.getLastName());
                    editor.putString(Constants.PREF_PROFILE_EMAIL, userModel.getEmailAddress());
                    editor.putString(Constants.PREF_PROFILE_PHONE_NUMBER, userModel.getMobilePhoneNumber());
                    editor.putString(Constants.PREF_PROFILE_BEARER, "Bearer " + userModel.getAuthorizeToken());
                    editor.putString(Constants.PREF_PROFILE_FIREBASE_ID, String.valueOf(userFirebaseId));
                    editor.putBoolean(Constants.PREF_PROFILE_DEFAULT, false);
                    editor.putInt(Constants.PREF_PROFILE_PERSON_ID, userModel.getPersonId());
                    editor.putString(Constants.PREF_PROFILE_DEVICE_TOKEN, mFirebaseToken);
                    editor.apply();

                    postRegisterDevice("Bearer " + userModel.getAuthorizeToken(), userModel.getPersonId(), mFirebaseToken);

                    starCropStreamFragment();
                }

                @Override
                public void onFailure(@NonNull Call<FirebaseUserReturnValue> call, @NonNull Throwable t) {
                    Log.d("Error: ", t.getMessage());
                    Toast.makeText(MainActivity.this, "Oh no... Error fetching user data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.channel_id);
            String channelName = getString(R.string.channel_name);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            String token;
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                if(key.equals("key_intent_firebase_token")){
                    if (value != null) {
                        token = value.toString();
                        SharedPreferences postPreferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
                        String bearer = postPreferences.getString(Constants.PREF_PROFILE_BEARER, null);
                        int personId = postPreferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0);
                        if(bearer != null && personId != 0) postRegisterDevice(bearer, personId, token);
                    }
                }
                Log.d("NS_test_MA", "Key: " + key + " Value: " + value);
            }

            SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);

            Log.d("NS_test_MA", "----------------------------");

            Log.d("NS_test_MA", "Pref_Firebase_id: " + preferences.getString(Constants.PREF_PROFILE_FIREBASE_ID, null));
            Log.d("NS_test_MA", "Pref_phone: " + preferences.getString(Constants.PREF_PROFILE_PHONE_NUMBER, null));
            Log.d("NS_test_MA", "Pref_firebase_token: " + preferences.getString(Constants.PREF_PROFILE_DEVICE_TOKEN, null));

            Log.d("NS_test_MA", "----------------------------");


        }
    }

    private void postRegisterDevice(String bearer, int personId, String deviceTokenId){
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RDResponse> responseCall = service.postRegisterDevice(bearer, personId, deviceTokenId, Constants.DEVICE_TYPE);
        responseCall.enqueue(new Callback<RDResponse>() {
            @Override
            public void onResponse(@NonNull Call<RDResponse> call, @NonNull Response<RDResponse> response) {
                if(response.body() != null)
                Log.d("RegisterDevice_ma", "onResponse: " + response.body().getResultCodeName());
            }

            @Override
            public void onFailure(@NonNull Call<RDResponse> call, @NonNull Throwable t) {
                Log.d("RegisterDevice", "error: " + t.getMessage());
            }
        });
    }

    private void starCropStreamFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
                .commit();
    }

    public void ivSettingsClicked(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void makeDefaultToolbarVisible() {
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);

        mBinding.layoutToolbar.contentCropStream.ivToolbarSearch.setVisibility(View.GONE);
        mBinding.layoutToolbar.contentCropStream.ivToolbarProfilePicture.setVisibility(View.GONE);
        mBinding.layoutToolbar.contentCropStream.tvToolbarProfileName.setVisibility(View.GONE);
        mBinding.layoutToolbar.contentCropStream.tvToolbarArrow.setVisibility(View.GONE);
        mBinding.layoutToolbar.contentCropStream.ivToolbarSettings.setVisibility(View.GONE);
    }

    private void makeChatToolbarVisible() {
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.GONE);

        mBinding.layoutToolbar.contentCropStream.ivToolbarSearch.setVisibility(View.VISIBLE);
        mBinding.layoutToolbar.contentCropStream.ivToolbarProfilePicture.setVisibility(View.VISIBLE);
        mBinding.layoutToolbar.contentCropStream.tvToolbarProfileName.setVisibility(View.VISIBLE);
        mBinding.layoutToolbar.contentCropStream.tvToolbarArrow.setVisibility(View.VISIBLE);
        mBinding.layoutToolbar.contentCropStream.ivToolbarSettings.setVisibility(View.VISIBLE);

        SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);

        mBinding.layoutToolbar.contentCropStream.ivToolbarProfilePicture.setBackground(null);
        Picasso.get().load(preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null)).fit().centerInside()
                .placeholder(Objects.requireNonNull(getDrawable(R.drawable.ic_profile_default)))
                .error(Objects.requireNonNull(getDrawable(R.drawable.ic_error_red)))
                .transform(new CircleTransform())
                .into(mBinding.layoutToolbar.contentCropStream.ivToolbarProfilePicture);
        mBinding.layoutToolbar.contentCropStream.tvToolbarProfileName.setText(preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null));
    }

}