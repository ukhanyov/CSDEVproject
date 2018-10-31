package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.data.CropStreamMessageViewModel;
import com.example.admin_linux.csdevproject.databinding.ActivityMainBinding;
import com.example.admin_linux.csdevproject.fragments.ChatFragment;
import com.example.admin_linux.csdevproject.fragments.CropStreamFragment;
import com.example.admin_linux.csdevproject.fragments.FavoritesFragment;
import com.example.admin_linux.csdevproject.fragments.SearchFragment;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventItemModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMInvolvedPerson;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;

import java.util.ArrayList;
import java.util.List;
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
    private Boolean isSearchETOpened = false;
    private Boolean isNotDefaultUser = false;

    private String mBearer, mUserFirebaseId;
    private int mUserId;

    private Animation
            fab_title_open_1, fab_title_open_2, fab_title_open_3, fab_title_open_4,
            fab_open_1, fab_open_2, fab_open_3, fab_open_4,
            fab_title_close_1, fab_title_close_2, fab_title_close_3, fab_title_close_4,
            fab_close_1, fab_close_2, fab_close_3, fab_close_4,
            rotate_forward, rotate_backward;

    //private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Fragment stuff
    FragmentManager fragmentManager;

    BottomNavigationView bottomNavigationView;

    private CropStreamMessageViewModel viewModel;
    private CropStreamFragment fragmentCropStreamTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Intent intent = getIntent();
        mBearer = intent.getStringExtra(Constants.KEY_INTENT_BEARER);
        mUserFirebaseId = intent.getStringExtra(Constants.KEY_INTENT_USER_FIREBASE_ID);
        mUserId = intent.getIntExtra(Constants.KEY_INTENT_USER_ID, 0);
        if(mBearer != null && !mBearer.equals("") && mUserId != 0){
            // old user root
            isNotDefaultUser = false;

        } else if(mUserFirebaseId != null && !mUserFirebaseId.equals("")){
            // new user root
            isNotDefaultUser = true;
            mBearer = null;
            mUserId = 0;
            fetchUserData(mUserFirebaseId);
        }

        // Toolbar
        Toolbar toolbar = mBinding.layoutToolbar.toolbar;
        setSupportActionBar(toolbar);
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
        viewModel = ViewModelProviders.of(this).get(CropStreamMessageViewModel.class);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragmentCropStreamTransaction = (CropStreamFragment) getSupportFragmentManager().getFragment(savedInstanceState, "CropStreamFragment");
            if (fragmentCropStreamTransaction != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
                        .commit();
            }
        } else {
            if (viewModel.getList().getValue() == null) {
                fetchData(mBearer, mUserId);
            }
            starCropStreamFragment();
        }

        // Toolbar title
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);

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
                if (viewModel.getList().getValue() == null) {
                    fetchData(mBearer, mUserId);
                }
                starCropStreamFragment();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);

                mBinding.layoutToolbar.contentChat.contentToolbarEtSearch.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbCancelSearch.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbProfilePicture.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbSearch.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbSettings.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarTvLabelArrow.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarTvProfileName.setVisibility(View.INVISIBLE);

                break;

            case R.id.action_chat:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new ChatFragment())
                        .commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_chat));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.INVISIBLE);

                mBinding.layoutToolbar.contentChat.contentToolbarEtSearch.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbCancelSearch.setVisibility(View.INVISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbProfilePicture.setVisibility(View.VISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbSearch.setVisibility(View.VISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarIbSettings.setVisibility(View.VISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarTvLabelArrow.setVisibility(View.VISIBLE);
                mBinding.layoutToolbar.contentChat.contentToolbarTvProfileName.setVisibility(View.VISIBLE);
                break;

            case R.id.action_favorites:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new FavoritesFragment())
                        .commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_favorites));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
                break;

            case R.id.action_search:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, new SearchFragment())
                        .commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_search));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
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

    private void initAnimations(){
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

    private void fetchData(String bearer, int personId) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ApiResultOfFeedEventsModel> parsedJSON = service.getActivityCardFeedEventsByPerson(
                bearer,
                personId,
                Constants.MAX_EVENT_COUNT);

        parsedJSON.enqueue(new Callback<ApiResultOfFeedEventsModel>() {
            @Override
            public void onResponse(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Response<ApiResultOfFeedEventsModel> response) {

                // TODO: look into person/user id shenanigans

                ApiResultOfFeedEventsModel pj = response.body();
                List<CropStreamMessage> tempArray = new ArrayList<>();
                List<FeedEventItemModel> list = Objects.requireNonNull(pj).getFeedEventsModel().getFeedEventItemModels();
                for (FeedEventItemModel item : list) {

                    List<FEIMInvolvedPerson> involvedPeople;
                    involvedPeople = item.getInvolvedPersons();
                    StringBuilder stringBuilder = new StringBuilder();
                    int iterator = 0;
                    boolean combineImage = false;
                    String imageFirst = null;
                    String imageSecond = null;

                    if (involvedPeople != null) {
                        for (FEIMInvolvedPerson person : involvedPeople) {
                            if (person.getPersonId() == Constants.PERSON_ID) {
                                stringBuilder.append("you");
                            } else {
                                stringBuilder.append(person.getPersonFullName());
                                combineImage = true;
                            }
                            iterator++;
                            if (iterator < involvedPeople.size() - 2) {
                                stringBuilder.append(", ");
                            } else {
                                break;
                            }
                        }

                        if (involvedPeople.size() > 1) {
                            for (FEIMInvolvedPerson person : involvedPeople) {
                                if (person.getPersonId() != Constants.PERSON_ID) {

                                    if (imageFirst != null) {
                                        imageSecond = person.getIconPath();
                                        break;
                                    } else {
                                        imageFirst = person.getIconPath();
                                    }
                                }
                            }
                        }


                    } else {
                        stringBuilder.append("you");
                    }

                    String corpName;
                    String pictureUrl;
                    if (item.getOrganization() != null) {
                        corpName = item.getOrganization().getOrganizationName();
                        pictureUrl = item.getOrganization().getImageUrl();
                    } else {
                        corpName = null;
                        pictureUrl = item.getPerson().getIconPath();
                    }

                    tempArray.add(new CropStreamMessage(
                            pictureUrl,
                            item.getPerson().getPersonFullName(),
                            corpName,
                            item.getComments(),
                            item.getOnDate(),
                            null,
                            item.isConversationFirstMessage(),
                            stringBuilder.toString(),
                            item.getPerson().getOrganizationName(),
                            combineImage,
                            imageFirst,
                            imageSecond,
                            item.getFeedType()
                    ));
                }

                viewModel.setList(tempArray);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(MainActivity.this, "Oh no... Error fetching data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserData(String mUserFirebaseId){
        if(mUserFirebaseId != null && !mUserFirebaseId.equals("")){

        }
    }

    private void starCropStreamFragment() {
        viewModel.getList().observe(this, listArray -> {
            if (listArray != null) {
                List<CropStreamMessage> transferList = new ArrayList<>(listArray);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("transferList", (ArrayList<? extends Parcelable>) transferList);

                fragmentCropStreamTransaction = new CropStreamFragment();
                fragmentCropStreamTransaction.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
                        .commit();
            }
        });
    }

    public void toggleSearchButtonChatToolbar(View view) {
        if(isSearchETOpened){

            mBinding.layoutToolbar.contentChat.contentToolbarEtSearch.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbCancelSearch.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbProfilePicture.setVisibility(View.INVISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbSearch.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbSettings.setVisibility(View.INVISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarTvLabelArrow.setVisibility(View.INVISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarTvProfileName.setVisibility(View.INVISIBLE);

            isSearchETOpened = true;
        } else {

            mBinding.layoutToolbar.contentChat.contentToolbarEtSearch.setVisibility(View.INVISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbCancelSearch.setVisibility(View.INVISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbProfilePicture.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbSearch.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarIbSettings.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarTvLabelArrow.setVisibility(View.VISIBLE);
            mBinding.layoutToolbar.contentChat.contentToolbarTvProfileName.setVisibility(View.VISIBLE);

            isSearchETOpened = false;
        }
    }
}
