package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin_linux.csdevproject.data.models.favorites.Favorites;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritesTabs;
import com.example.admin_linux.csdevproject.fragments.FavoritesFragmentPage;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavoritesPagerAdapter extends FragmentPagerAdapter {
    private Favorites favoritesData;
    //private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context mContext;

    public FragmentFavoritesPagerAdapter(Favorites favorites, FragmentManager fm, Context context) {
        super(fm);
        this.favoritesData = favorites;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return favoritesData.getFavoritesTabs().size();
    }

    @Override
    public Fragment getItem(int position) {
        return FavoritesFragmentPage.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        List<String> titles = new ArrayList<>();
        for(FavoritesTabs tabs : favoritesData.getFavoritesTabs()){
            titles.add(tabs.getTabName());
        }

        return titles.get(position);
    }
}
