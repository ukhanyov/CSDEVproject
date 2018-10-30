package com.example.admin_linux.csdevproject;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.adapters.CropStreamAdapter;
import com.example.admin_linux.csdevproject.data.CropStreamMessage;
import com.example.admin_linux.csdevproject.data.CropStreamMessageViewModel;
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


public class CropStreamFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ProgressBar progressBar;

//    private CropStreamMessageViewModel viewModel;
//    private MutableLiveData<List<CropStreamMessage>> listArray;

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

        List<CropStreamMessage> transferList = getArguments().getParcelableArrayList("transferList");
        final CropStreamAdapter mAdapter = new CropStreamAdapter(rootView.getContext());
        mAdapter.setCorpStreamMessages(transferList);
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_corp_stream_fragment);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        listArray = new MutableLiveData<>();
//        fetchData();
        //final CropStreamAdapter mAdapter = new CropStreamAdapter(rootView.getContext());

//        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CropStreamMessageViewModel.class);
//        viewModel.getList().observe(getActivity(), listArray -> {
//            if(listArray != null){
//                mAdapter.setCorpStreamMessages(listArray);
//            }
//        });

//        RecyclerView recyclerView = rootView.findViewById(R.id.rv_corp_stream_fragment);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar.setVisibility(View.INVISIBLE);
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

//    private void fetchData() {
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<ApiResultOfFeedEventsModel> parsedJSON = service.getActivityCardFeedEventsByPerson(
//                Constants.BEARER,
//                Constants.PERSON_ID,
//                Constants.MAX_EVENT_COUNT);
//
//        parsedJSON.enqueue(new Callback<ApiResultOfFeedEventsModel>() {
//            @Override
//            public void onResponse(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Response<ApiResultOfFeedEventsModel> response) {
//                ApiResultOfFeedEventsModel pj = response.body();
//                List<CropStreamMessage> tempArray = new ArrayList<>();
//                List<FeedEventItemModel> list = Objects.requireNonNull(pj).getFeedEventsModel().getFeedEventItemModels();
//                for (FeedEventItemModel item : list) {
//
//                    List<FEIMInvolvedPerson> involvedPeople;
//                    involvedPeople = item.getInvolvedPersons();
//                    StringBuilder stringBuilder = new StringBuilder();
//                    int iterator = 0;
//                    boolean combineImage = false;
//                    String imageFirst = null;
//                    String imageSecond = null;
//
//                    if (involvedPeople != null) {
//                        for (FEIMInvolvedPerson person : involvedPeople) {
//                            if (person.getPersonId() == Constants.PERSON_ID) {
//                                stringBuilder.append("you");
//                            } else {
//                                stringBuilder.append(person.getPersonFullName());
//                                combineImage = true;
//                            }
//                            iterator++;
//                            if (iterator < involvedPeople.size() - 2) {
//                                stringBuilder.append(", ");
//                            } else {
//                                break;
//                            }
//                        }
//
//                        if (involvedPeople.size() > 1) {
//                            for (FEIMInvolvedPerson person : involvedPeople) {
//                                if (person.getPersonId() != Constants.PERSON_ID) {
//
//                                    if (imageFirst != null) {
//                                        imageSecond = person.getIconPath();
//                                        break;
//                                    } else {
//                                        imageFirst = person.getIconPath();
//                                    }
//                                }
//                            }
//                        }
//
//
//                    } else {
//                        stringBuilder.append("you");
//                    }
//
//                    String corpName;
//                    String pictureUrl;
//                    if (item.getOrganization() != null) {
//                        corpName = item.getOrganization().getOrganizationName();
//                        pictureUrl = item.getOrganization().getImageUrl();
//                    } else {
//                        corpName = null;
//                        pictureUrl = item.getPerson().getIconPath();
//                    }
//
//                    tempArray.add(new CropStreamMessage(
//                            pictureUrl,
//                            item.getPerson().getPersonFullName(),
//                            corpName,
//                            "",
//                            item.getOnDate(),
//                            "",
//                            item.isConversationFirstMessage(),
//                            stringBuilder.toString(),
//                            item.getPerson().getOrganizationName(),
//                            combineImage,
//                            imageFirst,
//                            imageSecond
//                    ));
//                }
//
//                listArray.setValue(tempArray);
//                viewModel.setList(listArray);
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Throwable t) {
//                Log.d("Error: ", t.getMessage());
//                Toast.makeText(getActivity(), "Oh no... Error fetching data!", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
