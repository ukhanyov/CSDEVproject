package com.example.admin_linux.csdevproject.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin_linux.csdevproject.ConversationDetailsActivity;
import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.StartChatActivity;
import com.example.admin_linux.csdevproject.adapters.CropStreamAdapter;
import com.example.admin_linux.csdevproject.adapters.CropStreamClickListener;
import com.example.admin_linux.csdevproject.data.models.ConversationPerson;
import com.example.admin_linux.csdevproject.data.models.CropStreamMessage;
import com.example.admin_linux.csdevproject.data.models.TemplateItemModelBase;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventCardRenderItems;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventCatalogEntries;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.FeedEventItemModel;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model.CEMFormTemplate;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.catalog_entry_model.item_model.CEMFormTemplateItemModelBase;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMInvolvedPerson;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.model.event_item.event_item_sub_models.FEIMPerson;
import com.example.admin_linux.csdevproject.network.retrofit.GetDataService;
import com.example.admin_linux.csdevproject.network.retrofit.RetrofitActivityFeedInstance;
import com.example.admin_linux.csdevproject.utils.Constants;
import com.example.admin_linux.csdevproject.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class CropStreamFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<CropStreamMessage> cropStreamMessages;

    CropStreamAdapter mAdapter;

    public CropStreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            cropStreamMessages = savedInstanceState.getParcelableArrayList("saved_instance_transferList");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crop_stream, container, false);

        // List item click stuff
        // -------------------------------------------------------------------------------------------
        CropStreamClickListener listener = (view, conversationId, personId, profileName, personsCorp, personsPictureUrl, messageText, key) -> {

            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);

            String bearer = preferences.getString(Constants.PREF_PROFILE_BEARER, null);
            String mProfileFullName = preferences.getString(Constants.PREF_PROFILE_FULL_NAME, null);
            String mProfileUrl = preferences.getString(Constants.PREF_PROFILE_IMAGE_URL, null);

            if (key.equals(Constants.CLICK_KEY_CONVERSATION_DETAILS)) {
                if (bearer != null && mProfileFullName != null && mProfileUrl != null) {
                    Intent intent = new Intent(getActivity(), ConversationDetailsActivity.class);
                    intent.putExtra("transfer_profile_url", mProfileUrl);
                    intent.putExtra("transfer_full_name", mProfileFullName);
                    intent.putExtra("transfer_bearer", bearer);
                    intent.putExtra("transfer_conversation_id", conversationId);
                    intent.putExtra("transfer_person_id", personId);
                    startActivity(intent);
                } else {
                    Log.d("CropStreamFragment", "Error launching ConversationDetailsActivity");
                }

            }

            if (key.equals(Constants.CLICK_KEY_START_CHAT)) {
                ConversationPerson person = new ConversationPerson(
                        personId,
                        null,
                        null,
                        profileName,
                        false,
                        personsCorp,
                        personsPictureUrl,
                        messageText
                );
                Intent intent = new Intent(getActivity(), StartChatActivity.class);
                intent.putExtra(Constants.INTENT_KEY_PERSON_TO_START_CHAT, person);
                startActivity(intent);
            }
        };
        // -------------------------------------------------------------------------------------------

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_corp_stream_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new CropStreamAdapter(cropStreamMessages, listener, rootView.getContext());
        mAdapter.setHasStableIds(true);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Store a member variable for the listener
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

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
                loadNextDataFromApi();
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        if (cropStreamMessages == null) {
            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
            fetchData(preferences.getString(Constants.PREF_PROFILE_BEARER, null),
                    preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0));
        }

        // SwipeRefreshLayout
        mSwipeRefreshLayout = rootView.findViewById(R.id.srl_crop_stream_fragment);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(true);

            mAdapter.removeAllItems();
            mAdapter.notifyItemRangeRemoved(0, cropStreamMessages.size());
            cropStreamMessages = null;

            SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
            fetchData(preferences.getString(Constants.PREF_PROFILE_BEARER, null),
                    preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0));
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        return rootView;
    }

    private void loadNextDataFromApi() {
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.PREF_PROFILE_SETTINGS, MODE_PRIVATE);
        fetchMoreData(preferences.getString(Constants.PREF_PROFILE_BEARER, null),
                preferences.getInt(Constants.PREF_PROFILE_PERSON_ID, 0),
                cropStreamMessages.get(cropStreamMessages.size() - 1).getMessageTime());

        Log.d("fetchMoreData", "called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("saved_instance_transferList", (ArrayList<? extends Parcelable>) cropStreamMessages);
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

                // Sub root (for all main roots) |5|: FeedEvents -> FeedEventItemModel-> CardRenderDataId(not null) ->
                // -> FeedEventItemModel -> CardRenderDataModel -> CardRenderDataId -> CardRenderTypes = CardMessage -> CardMessage

                // Sub root (for all main roots) |6|: FeedEvents -> CatalogEntryId(not null) -> CatalogEntries -> CatalogEntryInDetailsModel ->
                // FormTemplateModel(not null) -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)

                // Sub root (for all main roots) |7|: FeedEvents -> CatalogEntryId(null) -> CardRenderDataModel -> CatalogEntries -> CatalogEntryInDetailsModel ->
                // FormTemplateModel(not null) -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)

                ApiResultOfFeedEventsModel feedEventsModel = response.body();
                List<FeedEventItemModel> listOfEvents = Objects.requireNonNull(feedEventsModel).getFeedEventsModel().getFeedEventItemModels();
                cropStreamMessages = new ArrayList<>();

                for (FeedEventItemModel event : listOfEvents) {
                    FEIMPerson person = event.getPerson();
                    if (event.getOrganization() != null) {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |1|
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        } else {
                            // Go root |2|
                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            List<String> picturesOfPeople = populateListOfImages(event.getInvolvedPersons(), yourPersonId);
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    (picturesOfPeople.size() > 1) ? picturesOfPeople.get(0) : null,
                                    (picturesOfPeople.size() > 2) ? picturesOfPeople.get(1) : null,
                                    event.getFeedType(),
                                    true,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        }
                    } else {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |3|
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        } else {
                            // Go root |4|

                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            List<String> picturesOfPeople = populateListOfImages(event.getInvolvedPersons(), yourPersonId);
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    (picturesOfPeople.size() > 1) ? picturesOfPeople.get(0) : null,
                                    (picturesOfPeople.size() > 2) ? picturesOfPeople.get(1) : null,
                                    event.getFeedType(),
                                    false,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        }
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Error: ", t.getMessage());
                Toast.makeText(getContext(), "Oh no... Error fetching data!", Toast.LENGTH_SHORT).show();
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
        }
        return people;
    }

    private List<String> populateListOfImages(List<FEIMInvolvedPerson> involvedPeople, int yourId) {
        List<String> people = new ArrayList<>();
        for (FEIMInvolvedPerson person : involvedPeople) {
            if (person.getPersonId() != yourId) {
                people.add(person.getIconPath());
            }
        }
        return people;
    }

    private String getMessageHttp(List<FeedEventCardRenderItems> list, int id) {
        for (FeedEventCardRenderItems item : list) {
            if (item.getCardRenderDataId() == id) {
                if (item.getCardMessage() != null)
                    return item.getCardMessage().getMessage();
            }
        }
        return null;
    }

    private double getMessageAspectRatio(List<FeedEventCardRenderItems> list, double id) {
        for (FeedEventCardRenderItems item : list) {
            if (item.getCardRenderDataId() == id) {
                if (item.getCardMessage() != null)
                    return item.getCardMessage().getAspectRatio();
            }
        }
        return 0;
    }

    private String getMessageType(List<FeedEventCardRenderItems> list, double id) {
        for (FeedEventCardRenderItems item : list) {
            if (item.getCardRenderDataId() == id) {
                if (item.getCardMessage() != null)
                    return item.getCardMessage().getMessageType();
            }
        }
        return null;
    }

    private List<TemplateItemModelBase> getListOfTemplateItemModelBaseItems(int id, List<FeedEventCatalogEntries> catalogEntries, List<FeedEventCardRenderItems> feedEventCardRenderItems) {
        if (id != 0) {
            if (catalogEntries != null) {
                for (FeedEventCatalogEntries catalogEntry : catalogEntries) {
                    if (id == catalogEntry.getCatalogEntryId()) {
                        List<TemplateItemModelBase> returnList = new ArrayList<>();
                        List<CEMFormTemplateItemModelBase> list = catalogEntry.getFormTemplateModel().getFormTemplateItems();
                        for (CEMFormTemplateItemModelBase item : list) {
                            returnList.add(new TemplateItemModelBase(item.getItemType(), item.getLabel(), item.getResourceUrl(), item.getInnerHtml()));
                        }
                        return returnList;
                    }
                }
            } else if (feedEventCardRenderItems != null) {
                for (FeedEventCardRenderItems cardRenderItem : feedEventCardRenderItems) {
                    if (id == cardRenderItem.getCatalogEntry().getCatalogEntryId()) {
                        List<TemplateItemModelBase> returnList = new ArrayList<>();
                        List<CEMFormTemplateItemModelBase> list = cardRenderItem.getCatalogEntry().getFormTemplateModel().getFormTemplateItems();
                        for (CEMFormTemplateItemModelBase item : list) {
                            returnList.add(new TemplateItemModelBase(item.getItemType(), item.getLabel(), item.getResourceUrl(), item.getInnerHtml()));
                        }
                        return returnList;
                    }
                }
            }
        }
        return null;
    }

    private CEMFormTemplate getListOfTemplateItemModel(int id, List<FeedEventCatalogEntries> catalogEntries, List<FeedEventCardRenderItems> feedEventCardRenderItems) {
        if (id != 0) {
            if (catalogEntries != null) {
                for (FeedEventCatalogEntries catalogEntry : catalogEntries) {
                    if (id == catalogEntry.getCatalogEntryId()) {
                        return catalogEntry.getFormTemplateModel();
                    }
                }
            } else if (feedEventCardRenderItems != null) {
                for (FeedEventCardRenderItems cardRenderItem : feedEventCardRenderItems) {
                    if (id == cardRenderItem.getCatalogEntry().getCatalogEntryId()) {
                        return cardRenderItem.getCatalogEntry().getFormTemplateModel();
                    }
                }
            }
        }
        return null;
    }

    private CropStreamMessage instantiateCropStreamMessage(String pictureUrl,
                                                           String fullName,
                                                           String corpName,
                                                           String textToDisplay,
                                                           String onDate,
                                                           String messagePicture,
                                                           boolean isFirstMessage,
                                                           String involvedPersons,
                                                           String organizationName,
                                                           boolean isCombinedImage,
                                                           String firstImageUrl,
                                                           String secondImageUrl,
                                                           String feedType,
                                                           boolean isFromOrganization,
                                                           String conversationId,
                                                           String personId,
                                                           boolean isConversation,
                                                           String personPictureUrl,
                                                           int cardRenderDataId,
                                                           String messageHttp,
                                                           double aspectRatio,
                                                           String messageType,
                                                           int catalogEntryId,
                                                           List<TemplateItemModelBase> list,
                                                           String templateName,
                                                           String templateDescription) {
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
                personPictureUrl,
                cardRenderDataId,
                messageHttp,
                aspectRatio,
                messageType,
                catalogEntryId,
                list,
                templateName,
                templateDescription
        );
    }


    public void fetchMoreData(String bearer, int yourPersonId, String date) {
        GetDataService service = RetrofitActivityFeedInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ApiResultOfFeedEventsModel> parsedJSON = service.getActivityCardFeedEventsByPersonAndTimeOfLastMessage(
                bearer,
                yourPersonId,
                Constants.MAX_EVENT_COUNT,
                date);

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

                // Sub root (for all main roots) |5|: FeedEvents -> FeedEventItemModel-> CardRenderDataId(not null) ->
                // -> FeedEventItemModel -> CardRenderDataModel -> CardRenderDataId -> CardRenderTypes = CardMessage -> CardMessage

                // Sub root (for all main roots) |6|: FeedEvents -> CatalogEntryId(not null) -> CatalogEntries -> CatalogEntryInDetailsModel ->
                // FormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)

                // Sub root (for all main roots) |7|: FeedEvents -> CatalogEntryId(null) -> CardRenderDataModel -> CatalogEntries -> CatalogEntryInDetailsModel ->
                // FormTemplateModel -> FormTemplateItemModelBase(all items) -> ItemType(add views according to this type)


                ApiResultOfFeedEventsModel feedEventsModel = response.body();
                List<FeedEventItemModel> listOfEvents = Objects.requireNonNull(feedEventsModel).getFeedEventsModel().getFeedEventItemModels();
                for (FeedEventItemModel event : listOfEvents) {
                    FEIMPerson person = event.getPerson();
                    if (event.getOrganization() != null) {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |1|
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        } else {
                            // Go root |2|
                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            List<String> picturesOfPeople = populateListOfImages(event.getInvolvedPersons(), yourPersonId);
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    (picturesOfPeople.size() > 1) ? picturesOfPeople.get(0) : null,
                                    (picturesOfPeople.size() > 2) ? picturesOfPeople.get(1) : null,
                                    event.getFeedType(),
                                    true,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        }
                    } else {
                        if (event.getInvolvedPersons() == null) {
                            // Go root |3|
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        } else {
                            // Go root |4|
                            List<String> involvedPeople = populateListOfInvolvedPeople(event.getInvolvedPersons(), person.getPersonIs(), yourPersonId);
                            List<String> picturesOfPeople = populateListOfImages(event.getInvolvedPersons(), yourPersonId);
                            CEMFormTemplate template = getListOfTemplateItemModel(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems());

                            CropStreamMessage message = instantiateCropStreamMessage(
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
                                    (picturesOfPeople.size() > 1) ? picturesOfPeople.get(0) : null,
                                    (picturesOfPeople.size() > 2) ? picturesOfPeople.get(1) : null,
                                    event.getFeedType(),
                                    false,
                                    String.valueOf(event.getConversationId()),
                                    String.valueOf(yourPersonId),
                                    event.getConversationId() != 0,
                                    event.getPerson().getIconPath(),
                                    event.getCardRenderDataId(),
                                    getMessageHttp(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageAspectRatio(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    getMessageType(feedEventsModel.getFeedEventsModel().getCardRenderItems(), event.getCardRenderDataId()),
                                    event.getCatalogEntryId(),
                                    getListOfTemplateItemModelBaseItems(event.getCatalogEntryId(), feedEventsModel.getFeedEventsModel().getCatalogEntries(), feedEventsModel.getFeedEventsModel().getCardRenderItems()),
                                    (template != null) ? template.getName() : null,
                                    (template != null) ? template.getDescription() : null);
                            cropStreamMessages.add(message);
                            mAdapter.addItem(message);
                            mAdapter.notifyItemInserted(cropStreamMessages.size() - 1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResultOfFeedEventsModel> call, @NonNull Throwable t) {
                Log.d("Error: ", t.getMessage());
                Toast.makeText(getContext(), "Oh no... Error fetching more data!", Toast.LENGTH_SHORT).show();
            }


        });
    }

}