package com.example.admin_linux.csdevproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritesTabs;

public class FavoritesFragmentPage extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NAME = "ARG_NAME";

    private FavoritesTabs mFavoritesTab;
    private String mName;

    public static FavoritesFragmentPage newInstance(String name, FavoritesTabs favoritesTab) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAGE, favoritesTab);
        args.putString(ARG_NAME, name);
        FavoritesFragmentPage fragment = new FavoritesFragmentPage();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavoritesTab = getArguments().getParcelable(ARG_PAGE);
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites_page, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.tv_fragment_favorites_page);
        textView.setText("Fragment #" + mName);
        return rootView;
    }
}
