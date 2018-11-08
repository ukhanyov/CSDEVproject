package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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

import com.example.admin_linux.csdevproject.data.CropStreamMessageViewModel;
import com.example.admin_linux.csdevproject.databinding.ActivityMainBinding;
import com.example.admin_linux.csdevproject.fragments.ChatFragment;
import com.example.admin_linux.csdevproject.fragments.CropStreamFragment;
import com.example.admin_linux.csdevproject.fragments.FavoritesFragment;
import com.example.admin_linux.csdevproject.fragments.SearchFragment;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.model.FireBaseUserModel;
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
        CropStreamFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        FavoritesFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener {

    // Fancy dataBinding
    ActivityMainBinding mBinding;

    private Boolean isFabOpen = false;

    private String mBearer;
    private String mUserFirebaseId;
    private String mUserFirebasePhoneNumber;
    private String mProfileUrl;
    private String mFullName;

    private int mUserId;

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

        Intent intent = getIntent();
        mUserFirebaseId = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_ID);
        mUserFirebasePhoneNumber = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_PHONE_NUMBER);
        mBearer = null;
        mUserId = 0;
        fetchUserData(mUserFirebaseId, mUserFirebasePhoneNumber);

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
        //viewModel = ViewModelProviders.of(this).get(CropStreamMessageViewModel.class);

//        if (savedInstanceState != null) {
//            //Restore the fragment's instance
//            fragmentCropStreamTransaction = (CropStreamFragment) getSupportFragmentManager().getFragment(savedInstanceState, "CropStreamFragment");
//            if (fragmentCropStreamTransaction != null) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
//                        .commit();
//            }
//        } else {
//            if (viewModel.getList().getValue() == null) {
//                fetchUserData(mUserFirebaseId, mUserFirebasePhoneNumber);
//            }
//            starCropStreamFragment();
//        }

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

            mBinding.tvFavTitle1.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle2.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle3.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle4.setVisibility(View.INVISIBLE);
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
                    mUserId = userModel.getPersonId();
                    mBearer = "Bearer " + userModel.getAuthorizeToken();
                    mProfileUrl = userModel.getProfileImageUrl();
                    mFullName = userModel.getFirstName() + " " + userModel.getLastName();

                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.putString(Constants.PREF_PROFILE_IMAGE_URL, userModel.getProfileImageUrl());
                    editor.putString(Constants.PREF_PROFILE_FULL_NAME, userModel.getFirstName() + " " + userModel.getLastName());
                    editor.putString(Constants.PREF_PROFILE_EMAIL, userModel.getEmailAddress());
                    editor.putString(Constants.PREF_PROFILE_PHONE_NUMBER, userModel.getMobilePhoneNumber());
                    editor.putString(Constants.PREF_PROFILE_BEARER, mBearer);
                    editor.putString(Constants.PREF_PROFILE_FIREBASE_ID, String.valueOf(userFirebaseId));
                    editor.putBoolean(Constants.PREF_PROFILE_DEFAULT, false);
                    editor.putInt(Constants.PREF_PROFILE_PERSON_ID, userModel.getPersonId());
                    editor.apply();

                    //fetchData(mBearer, mUserId);
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

    private void starCropStreamFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
                .commit();
//        viewModel.getList().observe(this, listArray -> {
//            if (listArray != null) {
//
//                List<CropStreamMessage> transferList = new ArrayList<>(listArray);
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("transferList", (ArrayList<? extends Parcelable>) transferList);
//                bundle.putString("transferBearerToFragment", mBearer);
//                bundle.putString("transferProfileUrlToFragment", mProfileUrl);
//                bundle.putString("transferFullNameToFragment", mFullName);
//
//
//                fragmentCropStreamTransaction = new CropStreamFragment();
//                fragmentCropStreamTransaction.setArguments(bundle);
//
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
//                        .commit();
//            }
//        });
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
        Picasso.with(this).load(preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null)).fit().centerInside()
                .placeholder(getDrawable(R.drawable.ic_profile_default))
                .error(Objects.requireNonNull(getDrawable(R.drawable.ic_error_red)))
                .transform(new CircleTransform())
                .into(mBinding.layoutToolbar.contentCropStream.ivToolbarProfilePicture);
        mBinding.layoutToolbar.contentCropStream.tvToolbarProfileName.setText(preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null));
    }
}