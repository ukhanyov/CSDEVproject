package com.example.admin_linux.csdevproject;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.admin_linux.csdevproject.databinding.ActivityMainBinding;

import java.util.Objects;

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
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;
    private Animation fab_open_1, fab_open_2, fab_open_3, fab_open_4,
            fab_close_1,  fab_close_2,  fab_close_3,  fab_close_4,
            rotate_forward, rotate_backward;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Fragment stuff
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: Look into progressbar

        // TODO: Chat make toolbar search
        // TODO: Chat make toolbar search separate cancel button (when search is active)

        // TODO: Chat make settings cog
        // TODO: Chat add settings screen
        // TODO: Favorites make toolbar name
        // TODO: Favorites make tabs
        // TODO: Favorites mock mechanism to add tabs
        // TODO: Search make toolbar search field (with cancel button)
        // TODO: Search mock image spin in top part of the fragment
        // TODO: Search make recyclerView with nested recyclers
        // TODO: Search make nested recyclers to recycle grids


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = mBinding.layoutToolbar.toolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // Bottom navigation
        bottomNavigationView = mBinding.bottomNavigation;
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // FAB init
        fab = mBinding.fab;
        fab1 = mBinding.fab1;
        fab2 = mBinding.fab2;
        fab3 = mBinding.fab3;
        fab4 = mBinding.fab4;

        // Animations init
        fab_open_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_1);
        fab_open_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_2);
        fab_open_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_3);
        fab_open_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open_4);
        fab_close_1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_1);
        fab_close_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_2);
        fab_close_3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_3);
        fab_close_4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close_4);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        // Click listeners for FAB
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);

        // Start activity with CropStreamFragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        CropStreamFragment fragmentCropStream = new CropStreamFragment();
        fragmentTransaction.replace(R.id.frame_layout_content_main, fragmentCropStream);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
            case R.id.fab1:

                Log.d(LOG_TAG, "Fab 1");
                break;
            case R.id.fab2:

                Log.d(LOG_TAG, "Fab 2");
                break;
            case R.id.fab3:

                Log.d(LOG_TAG, "Fab 3");
                break;
            case R.id.fab4:

                Log.d(LOG_TAG, "Fab 4");
                break;
        }

    }


    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close_4);
            fab2.startAnimation(fab_close_3);
            fab3.startAnimation(fab_close_2);
            fab4.startAnimation(fab_close_1);

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;

            mBinding.tvFavTitle1.startAnimation(fab_close_4);
            mBinding.tvFavTitle2.startAnimation(fab_close_3);
            mBinding.tvFavTitle3.startAnimation(fab_close_2);
            mBinding.tvFavTitle4.startAnimation(fab_close_1);

            mBinding.tvFavTitle1.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle2.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle3.setVisibility(View.INVISIBLE);
            mBinding.tvFavTitle4.setVisibility(View.INVISIBLE);
            Log.d(LOG_TAG, "close");
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open_1);
            fab2.startAnimation(fab_open_2);
            fab3.startAnimation(fab_open_3);
            fab4.startAnimation(fab_open_4);

            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;

            mBinding.tvFavTitle1.startAnimation(fab_open_1);
            mBinding.tvFavTitle2.startAnimation(fab_open_2);
            mBinding.tvFavTitle3.startAnimation(fab_open_3);
            mBinding.tvFavTitle4.startAnimation(fab_open_4);

            mBinding.tvFavTitle1.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle2.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle3.setVisibility(View.VISIBLE);
            mBinding.tvFavTitle4.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_cropstream:
                fragmentTransaction = fragmentManager.beginTransaction();
                CropStreamFragment fragmentCropStream = new CropStreamFragment();
                fragmentTransaction.replace(R.id.frame_layout_content_main, fragmentCropStream);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_cropstream));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
                break;

            case R.id.action_chat:
                fragmentTransaction = fragmentManager.beginTransaction();
                ChatFragment fragmentChat = new ChatFragment();
                fragmentTransaction.replace(R.id.frame_layout_content_main, fragmentChat);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_chat));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
                break;

            case R.id.action_favorites:
                fragmentTransaction = fragmentManager.beginTransaction();
                FavoritesFragment fragmentFavorites = new FavoritesFragment();
                fragmentTransaction.replace(R.id.frame_layout_content_main, fragmentFavorites);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_favorites));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
                break;

            case R.id.action_search:
                fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragment fragmentSearch = new SearchFragment();
                fragmentTransaction.replace(R.id.frame_layout_content_main, fragmentSearch);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setText(getString(R.string.title_search));
                mBinding.layoutToolbar.contentCropStream.tvToolbarTitle.setVisibility(View.VISIBLE);
                break;

        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void clearViewsFromToolbar(){

    }
}
