package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;
import com.example.admin_linux.csdevproject.data.CropStreamMessageViewModel;
import com.example.admin_linux.csdevproject.databinding.ActivityMainBinding;
import com.example.admin_linux.csdevproject.fragments.ChatFragment;
import com.example.admin_linux.csdevproject.fragments.CropStreamFragment;
import com.example.admin_linux.csdevproject.fragments.FavoritesFragment;
import com.example.admin_linux.csdevproject.fragments.SearchFragment;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventItemModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMInvolvedPerson;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMPerson;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.model.FireBaseUserModel;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.CircleTransform;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.squareup.picasso.Picasso;

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

    // TODO: pull to refresh
    // TODO: paging (after you scrolled 70-80 percent download more feed events)

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

    private CropStreamMessageViewModel viewModel;
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
                fetchUserData(mUserFirebaseId, mUserFirebasePhoneNumber);
            }
            starCropStreamFragment();
        }

        // Toolbar title
        mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));

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
                    fetchUserData(mUserFirebaseId, mUserFirebasePhoneNumber);

                }
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

    public void fetchData(String bearer, int yourPersonId) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ApiResultOfFeedEventsModel> parsedJSON = service.getActivityCardFeedEventsByPerson(
                bearer,
                yourPersonId,
                Constants.MAX_EVENT_COUNT);

        parsedJSON.enqueue(new Callback<ApiResultOfFeedEventsModel>() {
            @Override
            public void onResponse(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Response<ApiResultOfFeedEventsModel> response) {

                // Tips "Comments" -> text displayed on CropStreamMessage instance
                // Tips "FeedType": "ConversationMessage" -> bottom of CropStreamMessage is "View message"
                // Tips "FeedType": "CatalogEntryPosted" -> some image or whatever

                // Possible roots |1|: organization -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
                // Possible roots |2|: organization -> "ConversationId" -> "InvolvedPersons" -> list everyone but you
                // Possible roots |3|: organization(null) -> "Person" -> "ConversationId"(null) -> "InvolvedPersons"(null) -> you
                // Possible roots |4|: organization(null) -> "Person" -> "ConversationId" -> "InvolvedPersons" -> list everyone but you

                ApiResultOfFeedEventsModel feedEventsModel = response.body();
                List<FeedEventItemModel> listOfEvents = Objects.requireNonNull(feedEventsModel).getFeedEventsModel().getFeedEventItemModels();
                List<CropStreamMessage> listToFeedIntoViewModel = new ArrayList<>();

                for (FeedEventItemModel event : listOfEvents) {
                    FEIMPerson person = event.getPerson();
                    if (event.getOrganization() != null) {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |1|
                            listToFeedIntoViewModel.add(instantiateCropStreamMessage(
                                    event.getOrganization().getImageUrl(),
                                    event.getPerson().getPersonFullName(),
                                    event.getOrganization().getOrganizationName(),
                                    event.getComments(),
                                    event.getOnDate(),
                                    null,
                                    event.isConversationFirstMessage(),
                                    "you",
                                    event.getPerson().getOrganizationName(),
                                    false,
                                    null,
                                    null,
                                    event.getFeedType(),
                                    true,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath())
                            );
                        } else {
                            // Go root |2|
                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            listToFeedIntoViewModel.add(instantiateCropStreamMessage(
                                    event.getOrganization().getImageUrl(),
                                    event.getPerson().getPersonFullName(),
                                    event.getOrganization().getOrganizationName(),
                                    event.getComments(),
                                    event.getOnDate(),
                                    null,
                                    event.isConversationFirstMessage(),
                                    (involvedPeople.size() == 0) ? "you" : involvedPeople.toString().replace("[", "").replace("]", ""),
                                    event.getPerson().getOrganizationName(),
                                    true,
                                    (event.getInvolvedPersons().size() > 1 && involvedPeople.size() > 1) ? getFirstImageUrl(event.getInvolvedPersons(), involvedPeople.get(0)) : null,
                                    (event.getInvolvedPersons().size() > 2 && involvedPeople.size() > 2) ? getSecondImageUrl(event.getInvolvedPersons(), involvedPeople.get(1)) : null,
                                    event.getFeedType(),
                                    true,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath())
                            );
                        }
                    } else {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |3|
                            listToFeedIntoViewModel.add(instantiateCropStreamMessage(
                                    event.getPerson().getIconPath(),
                                    event.getPerson().getPersonFullName(),
                                    event.getPerson().getOrganizationName(),
                                    event.getComments(),
                                    event.getOnDate(),
                                    null,
                                    event.isConversationFirstMessage(),
                                    "you",
                                    event.getPerson().getOrganizationName(),
                                    false,
                                    null,
                                    null,
                                    event.getFeedType(),
                                    false,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath())
                            );
                        } else {
                            // Go root |4|
                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            listToFeedIntoViewModel.add(instantiateCropStreamMessage(
                                    event.getPerson().getIconPath(),
                                    event.getPerson().getPersonFullName(),
                                    event.getPerson().getOrganizationName(),
                                    event.getComments(),
                                    event.getOnDate(),
                                    null,
                                    event.isConversationFirstMessage(),
                                    (involvedPeople.size() == 0) ? "you" : involvedPeople.toString().replace("[", "").replace("]", ""),
                                    event.getPerson().getOrganizationName(),
                                    true,
                                    (event.getInvolvedPersons().size() > 1 && involvedPeople.size() > 1) ? getFirstImageUrl(event.getInvolvedPersons(), involvedPeople.get(0)) : null,
                                    (event.getInvolvedPersons().size() > 2 && involvedPeople.size() > 2) ? getSecondImageUrl(event.getInvolvedPersons(), involvedPeople.get(1)) : null,
                                    event.getFeedType(),
                                    false,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath())
                            );
                        }
                    }
                }

                viewModel.setList(listToFeedIntoViewModel);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(MainActivity.this, "Oh no... Error fetching data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> populateListOfInvolvedPeople(List<FEIMInvolvedPerson> involvedPeople, int personId, int yourId) {
        List<String> people = new ArrayList<>();
        for (FEIMInvolvedPerson person : involvedPeople) {
            if (person.getPersonId() != personId &&
                    person.getPersonId() != yourId) {
                people.add(person.getPersonFullName());
            }
            if (person.getPersonId() == yourId) {
                mFullName = person.getPersonFullName();
                mProfileUrl = person.getIconPath();
                            }
        }
        return people;
    }

    private String getFirstImageUrl(List<FEIMInvolvedPerson> involvedPeople, String person) {
        for (FEIMInvolvedPerson iterator : involvedPeople) {
            if (iterator.getPersonFullName().equals(person)) return iterator.getIconPath();
        }
        return null;
    }

    private String getSecondImageUrl(List<FEIMInvolvedPerson> involvedPeople, String person) {
        for (FEIMInvolvedPerson iterator : involvedPeople) {
            if (iterator.getPersonFullName().equals(person)) return iterator.getIconPath();
        }
        return null;
    }

    private CropStreamMessage instantiateCropStreamMessage(String pictureUrl, String fullName, String corpName, String textToDisplay, String onDate, String messagePicture, boolean isFirstMessage,
                                                           String involvedPersons, String organizationName, boolean isCombinedImage, String firstImageUrl, String secondImageUrl, String feedType,
                                                           boolean isFromOrganization, String conversationId, String personId, boolean isConversation, String personPictureUrl) {
        return new CropStreamMessage(
                pictureUrl,
                fullName,
                corpName,
                textToDisplay,
                onDate,
                messagePicture,
                isFirstMessage,
                involvedPersons,
                organizationName,
                isCombinedImage,
                firstImageUrl,
                secondImageUrl,
                feedType,
                isFromOrganization,
                conversationId,
                personId,
                isConversation,
                personPictureUrl

        );
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

                    fetchData(mBearer, mUserId);
                }

                @Override
                public void onFailure(@NonNull Call<FirebaseUserReturnValue> call, @NonNull Throwable t) {
                    Log.d("Error: ", t.getMessage());
                    Toast.makeText(MainActivity.this, "Oh no... Error fetching user data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void starCropStreamFragment() {
        viewModel.getList().observe(this, listArray -> {
            if (listArray != null) {
                List<CropStreamMessage> transferList = new ArrayList<>(listArray);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("transferList", (ArrayList<? extends Parcelable>) transferList);
                bundle.putString("transferBearerToFragment", mBearer);
                bundle.putString("transferProfileUrlToFragment", mProfileUrl);
                bundle.putString("transferFullNameToFragment", mFullName);


                fragmentCropStreamTransaction = new CropStreamFragment();
                fragmentCropStreamTransaction.setArguments(bundle);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_content_main, fragmentCropStreamTransaction)
                        .commit();
            }
        });
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
