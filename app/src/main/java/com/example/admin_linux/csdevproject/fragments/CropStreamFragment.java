package com.example.admin_linux.csdevproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.ConversationDetailsActivity;
import com.example.admin_linux.csdevproject.MainActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.StartChatActivity;
import com.example.admin_linux.csdevproject.adapters.CropStreamAdapter;
import com.example.admin_linux.csdevproject.adapters.CropStreamClickListener;
import com.example.admin_linux.csdevproject.data.models.ConversationPerson;
import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class CropStreamFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<CropStreamMessage> transferList;

    ProgressBar progressBar;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    public CropStreamFragment() {
        // Required empty public constructor
    }

    public static CropStreamFragment newInstance(String param1, String param2) {
        CropStreamFragment fragment = new CropStreamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            transferList = savedInstanceState.getParcelableArrayList("saved_instance_transferList");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crop_stream, container, false);
        progressBar = rootView.findViewById(R.id.pb_loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        // List item click stuff
        CropStreamClickListener listener = (view, cropStreamMessage, key) -> {

            String bearer = Objects.requireNonNull(getArguments()).getString("transferBearerToFragment");
            String mProfileFullName = Objects.requireNonNull(getArguments()).getString("transferFullNameToFragment");
            String mProfileUrl = Objects.requireNonNull(getArguments()).getString("transferProfileUrlToFragment");

            if (key.equals(Constants.CLICK_KEY_CONVERSATION_DETAILS)) {
                Intent intent = new Intent(getActivity(), ConversationDetailsActivity.class);
                intent.putExtra("transfer_profile_url", mProfileUrl);
                intent.putExtra("transfer_full_name", mProfileFullName);
                intent.putExtra("transfer_bearer", bearer);
                intent.putExtra("transfer_message", cropStreamMessage);
                startActivity(intent);
            }

            if (key.equals(Constants.CLICK_KEY_START_CHAT)) {
                ConversationPerson person = new ConversationPerson(
                        Integer.valueOf(cropStreamMessage.getPersonId()),
                        null,
                        null,
                        cropStreamMessage.getProfileName(),
                        false,
                        cropStreamMessage.getPersonsCorp(),
                        cropStreamMessage.getPersonPictureUrl(),
                        cropStreamMessage.getMessageText()
                );
                Intent intent = new Intent(getActivity(), StartChatActivity.class);
                intent.putExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT, person);
                startActivity(intent);
            }
        };

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_corp_stream_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        final CropStreamAdapter mAdapter = new CropStreamAdapter(rootView.getContext(), listener);
        Log.d("adapter_version", mAdapter.toString());

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            // Maybe add button "go to the top"
//            @Override
//            public void onScrolled(@NonNull RecyclerView view, int dx, int dy) {
//                super.onScrolled(view, dx, dy);
//
//                if (linearLayoutManager.getItemCount() > 20) {
//                    //Show FAB
//                    Toast.makeText(rootView.getContext(), "We need to go back", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    //Hide FAB
//                }
//            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(linearLayoutManager.findFirstVisibleItemPosition());
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        if (transferList != null) {
            mAdapter.setCorpStreamMessages(transferList);
        } else {
            transferList = Objects.requireNonNull(getArguments()).getParcelableArrayList("transferList");
            mAdapter.setCorpStreamMessages(transferList);
        }

        // SwipeRefreshLayout
        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_crop_stream_fragment);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
            ((MainActivity) Objects.requireNonNull(getActivity())).fetchData(
                    preferences.getString(Constants.PREF_PROFILE_BEARER, null),
                    preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0));
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        progressBar.setVisibility(View.INVISIBLE);

        SharedPreferences preferencesAdapter = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_ADAPTER_SETTINGS, MODE_PRIVATE);
        if(preferencesAdapter.getBoolean(Constants.PREF_ADAPTER_LOADED_MORE, false)){
            recyclerView.scrollToPosition(preferencesAdapter.getInt(Constants.PREF_ADAPTER_POSITION, 5));
            SharedPreferences.Editor editor = preferencesAdapter.edit();
            editor.clear();
            editor.apply();
        }

        return rootView;
    }

    private void loadNextDataFromApi(int position){
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        ((MainActivity) Objects.requireNonNull(getActivity())).fetchMoreData(
                preferences.getString(Constants.PREF_PROFILE_BEARER, null),
                preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0),
                transferList.get(transferList.size() - 2).getMessageTime(),
                position);

        Log.d("fetchMoreData", "called");
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("saved_instance_transferList", (ArrayList<? extends Parcelable>) transferList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}