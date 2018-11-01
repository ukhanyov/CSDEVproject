package com.example.admin_linux.csdevproject.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.ConversationDetailsActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.adapters.CropStreamAdapter;
import com.example.admin_linux.csdevproject.adapters.CropStreamClickListener;
import com.example.admin_linux.csdevproject.data.CropStreamMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CropStreamFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<CropStreamMessage> transferList;

    ProgressBar progressBar;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crop_stream, container, false);
        progressBar = rootView.findViewById(R.id.pb_loading_indicator);
        progressBar.setVisibility(View.VISIBLE);

        // List item click stuff
        CropStreamClickListener listener = (view, cropStreamMessage) -> {

            String bearer = Objects.requireNonNull(getArguments()).getString("transferBearerToFragment");

            Intent intent = new Intent(getActivity(), ConversationDetailsActivity.class);
            intent.putExtra("transfer_bearer", bearer);
            intent.putExtra("transfer_message", cropStreamMessage);
            startActivity(intent);

        };

        final CropStreamAdapter mAdapter = new CropStreamAdapter(rootView.getContext(), listener);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_corp_stream_fragment);

        if(transferList != null){

            mAdapter.setCorpStreamMessages(transferList);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else {
            transferList = Objects.requireNonNull(getArguments()).getParcelableArrayList("transferList");
            mAdapter.setCorpStreamMessages(transferList);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }




        progressBar.setVisibility(View.INVISIBLE);
        return rootView;
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
