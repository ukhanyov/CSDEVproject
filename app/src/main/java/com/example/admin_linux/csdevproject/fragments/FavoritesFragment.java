package com.example.admin_linux.csdevproject.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.adapters.FragmentFavoritesPagerAdapter;
import com.example.admin_linux.csdevproject.data.models.favorites.Favorites;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoriteFormTemplate;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoriteFormTemplateItem;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritePossibleItem;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritesTabs;
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.FavoriteEntriesReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.FavoriteEntryGroupModel;
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.template_model.model_base.FFTFormTemplateItemModelBase;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FavoritesFragment extends Fragment {


    private Favorites mFavorites;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);

        String bearer = preferences.getString(Constants.PREF_PROFILE_BEARER, null);
        int id = preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0);

        if(bearer != null && id != 0) fetchData(bearer, id, rootView);

        return rootView;
    }

    public void fetchData(String bearer, int yourPersonId, View rootView) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FavoriteEntriesReturnValue> parsedJSON = service.getFavoriteEntries(
                bearer,
                yourPersonId);

        parsedJSON.enqueue(new Callback<FavoriteEntriesReturnValue>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteEntriesReturnValue> call, @NonNull Response<FavoriteEntriesReturnValue> response) {

                FavoriteEntriesReturnValue body = response.body();
                if(body != null) {
                    if(body.getReturnValue().getFavoriteEntriesModelGroups() != null){

                        List<FavoritesTabs> favoritesTabsList = new ArrayList<>();

                        for (FavoriteEntryGroupModel favoriteEntryGroupModel : body.getReturnValue().getFavoriteEntriesModelGroups()){

                            List<FavoritePossibleItem> favoritePossibleItemList = new ArrayList<>();
                            List<FavoriteFormTemplate> favoriteFormTemplateList = new ArrayList<>();

                            if(favoriteEntryGroupModel.getPossibleItemsList() != null){
                                // Manage Possible items here
                                for (com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_possible_item.FavoritePossibleItem item : favoriteEntryGroupModel.getPossibleItemsList()){
                                    favoritePossibleItemList.add(new FavoritePossibleItem(
                                            item.getFavoritePossibleItemItemType(),
                                            item.getFavoritePossibleItemName(),
                                            item.getFavoritePossibleItemManufacturer()
                                    ));
                                }
                            }

                            if(favoriteEntryGroupModel.getFormTemplatesList() != null){
                                // Manage Form templates items here
                                for(com.example.admin_linux.csdevproject.network.pojo.favorite_entries.madel.group.favorite_form_template.FavoriteFormTemplate item : favoriteEntryGroupModel.getFormTemplatesList()){

                                    List<FavoriteFormTemplateItem> favoriteFormTemplateItemList = new ArrayList<>();

                                    if(item.getFormTemplateModel().getFormTemplateItems() != null){
                                        for(FFTFormTemplateItemModelBase itemModelBase : item.getFormTemplateModel().getFormTemplateItems()){
                                            favoriteFormTemplateItemList.add(new FavoriteFormTemplateItem(
                                                    itemModelBase.getTemplateItemModelBaseItemType(),
                                                    itemModelBase.getTemplateItemModelBaseLabel(),
                                                    itemModelBase.getTemplateItemModelBaseResourceUrl(),
                                                    itemModelBase.getTemplateItemModelBaseInnerHtml()
                                            ));
                                        }
                                    }

                                    favoriteFormTemplateList.add(new FavoriteFormTemplate(favoriteFormTemplateItemList));
                                }
                            }

                            favoritesTabsList.add(new FavoritesTabs(favoriteEntryGroupModel.getGroupName(), favoriteFormTemplateList, favoritePossibleItemList));

                        }

                        mFavorites = new Favorites(favoritesTabsList);
                    }
                }
                Log.d("favorites_fragment", "Success");
                initiateTabs(rootView);
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteEntriesReturnValue> call, @NonNull Throwable t) {
                Log.d("favorites_fragment: ", t.getMessage());
                Toast.makeText(getContext(), "Oh no... Error fetching favorites data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateTabs(View rootView) {
        try {
            // Get the ViewPager and set it's PagerAdapter so that it can display items
            ViewPager viewPager = rootView.findViewById(R.id.vp_fragment_favorites);
            viewPager.setAdapter(new FragmentFavoritesPagerAdapter(mFavorites, getChildFragmentManager(), getContext()));

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = rootView.findViewById(R.id.tl_fragment_favorites);
            tabLayout.setupWithViewPager(viewPager);
        }catch (IllegalStateException exception){
            Log.d("FavoritesFragment", exception.toString());
        }
    }

}
