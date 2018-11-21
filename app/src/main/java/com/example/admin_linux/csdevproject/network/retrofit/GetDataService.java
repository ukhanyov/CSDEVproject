package com.example.admin_linux.csdevproject.network.retrofit;

import com.example.admin_linux.csdevproject.network.pojo.activity_card.ApiAddOrganizationToFavorites;
import com.example.admin_linux.csdevproject.network.pojo.activity_card.ApiAddUserToFavorites;
import com.example.admin_linux.csdevproject.network.pojo.activity_card.ApiResultOfFeedEventAuthorUserApiResult;
import com.example.admin_linux.csdevproject.network.pojo.conversation_details.ConversationDetailsReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.favorite_entries.FavoriteEntriesReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.register_device.RDResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface GetDataService {

    // "api/v1/ActivityCard/FeedEvents?PersonId={PersonId}"
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/v1/ActivityCard/FeedEvents")
    Call<ApiResultOfFeedEventsModel> getActivityCardFeedEventsByPerson(
            @Header("Authorization") String BEARER,
            @Query("PersonId") int personId,
            @Query("MaxEventCount") int maxEventCount

    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/v1/ActivityCard/FeedEvents")
    Call<ApiResultOfFeedEventsModel> getActivityCardFeedEventsByPersonAndTimeOfLastMessage(
            @Header("Authorization") String BEARER,
            @Query("PersonId") int personId,
            @Query("MaxEventCount") int maxEventCount,
            @Query("StartDateUTC") String date
    );

    // "api/v1/User/GetByFireBaseId?FireBaseId={FireBaseId}&MobilePhoneNumber={MobilePhoneNumber}"
    @GET("api/v1/User/GetByFireBaseId")
    Call<FirebaseUserReturnValue> getUserGetByFireBaseId(
            @Query("FireBaseId") String FireBaseId,
            @Query("MobilePhoneNumber") String MobilePhoneNumber
    );

    // "api/v1/Conversation/ConversationDetails?ConversationId={ConversationId}&PersonId={PersonId}"
    @GET("api/v1/Conversation/ConversationDetails")
    Call<ConversationDetailsReturnValue> getConversationDetail(
            @Header("Authorization") String BEARER,
            @Query("ConversationId") int conversationId,
            @Query("PersonId") int personId
    );

    @FormUrlEncoded
    @POST("api/v1/User/RegisterDevice")
    Call<RDResponse> postRegisterDevice(
            @Header("Authorization") String BEARER,
            @Field("PersonId") int PersonId,
            @Field("DeviceTokenId") String DeviceTokenId,
            @Field("DeviceType") int DeviceType
    );

    // "api/v1/Catalog/GetFavoriteEntries?PersonId={PersonId}"
    @GET("api/v1/Catalog/GetFavoriteEntries")
    Call<FavoriteEntriesReturnValue> getFavoriteEntries(
            @Header("Authorization") String BEARER,
            @Query("PersonId") int personId
    );

    @FormUrlEncoded
    @POST("api/v1/ActivityCard/AddOrganizationToFavorites")
    Call<ApiAddOrganizationToFavorites> postAddOrganizationToFavorites(
            @Header("Authorization") String BEARER,
            @Field("PersonId") int personId,
            @Field("FavoriteOrganizationId") int favoriteOrganizationId,
            @Field("FavoriteOrganizationIds") int[] favoriteOrganizationIds
    );

    @FormUrlEncoded
    @POST("api/v1/ActivityCard/AddUserToFavorites")
    Call<ApiAddUserToFavorites> postAddUserToFavorites(
            @Header("Authorization") String BEARER,
            @Field("PersonId") int personId,
            @Field("FavoriteUserId") int favoriteUserId
    );

    //
    @GET("api/v1/ActivityCard/GetFeedEventAuthorUser")
    Call<ApiResultOfFeedEventAuthorUserApiResult> getFeedEventAuthorUser(
            @Header("Authorization") String BEARER,
            @Query("FeedEventId") int feedEventId
    );
}