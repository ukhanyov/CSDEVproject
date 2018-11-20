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
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.FavoriteEntriesReturnValue;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FavoritesFragment extends Fragment {




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

        if(bearer != null && id != 0) fetchData(bearer, id);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = rootView.findViewById(R.id.vp_fragment_favorites);
        viewPager.setAdapter(new FragmentFavoritesPagerAdapter(getActivity().getSupportFragmentManager(), getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = rootView.findViewById(R.id.tl_fragment_favorites);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    public void fetchData(String bearer, int yourPersonId) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FavoriteEntriesReturnValue> parsedJSON = service.getFavoriteEntries(
                bearer,
                yourPersonId);

        parsedJSON.enqueue(new Callback<FavoriteEntriesReturnValue>() {
            @Override
            public void onResponse(@NonNull Call<FavoriteEntriesReturnValue> call, @NonNull Response<FavoriteEntriesReturnValue> response) {
                FavoriteEntriesReturnValue body = response.body();
                Log.d("favorites_fragment", "Success");
            }

            @Override
            public void onFailure(@NonNull Call<FavoriteEntriesReturnValue> call, @NonNull Throwable t) {
                Log.d("favorites_fragment: ", t.getMessage());
                Toast.makeText(getContext(), "Oh no... Error fetching favorites data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
