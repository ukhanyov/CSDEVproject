package com.example.admin_linux.csdevproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.adapters.FavoritesTabPossibleItemAdapter;
import com.example.admin_linux.csdevproject.adapters.FavoritesTabTemplateItemAdapter;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritesTabs;

public class FavoritesFragmentPage extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NAME = "ARG_NAME";

    private FavoritesTabs mFavoritesTab;
    private String mName;

    FavoritesTabTemplateItemAdapter templateItemAdapter;
    FavoritesTabPossibleItemAdapter possibleItemAdapter;


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

        // Setup recyclerViews
        RecyclerView recyclerViewTemplateItems = rootView.findViewById(R.id.rv_fragment_favorites_page_template_items);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        templateItemAdapter = new FavoritesTabTemplateItemAdapter(mFavoritesTab.getFavoriteFormTemplateList(), rootView.getContext());
        templateItemAdapter.setHasStableIds(true);
        recyclerViewTemplateItems.setAdapter(templateItemAdapter);
        recyclerViewTemplateItems.setLayoutManager(gridLayoutManager);

        RecyclerView recyclerViewPossibleItem = rootView.findViewById(R.id.rv_fragment_favorites_page_possible_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        possibleItemAdapter = new FavoritesTabPossibleItemAdapter(mFavoritesTab.getFavoritePossibleItemList(), rootView.getContext());
        possibleItemAdapter.setHasStableIds(true);
        recyclerViewPossibleItem.setAdapter(possibleItemAdapter);
        recyclerViewPossibleItem.setLayoutManager(linearLayoutManager);

        TextView textView = (TextView) rootView.findViewById(R.id.tv_fragment_favorites_page);
        textView.setText("Fragment #" + mName);
        return rootView;
    }
}
