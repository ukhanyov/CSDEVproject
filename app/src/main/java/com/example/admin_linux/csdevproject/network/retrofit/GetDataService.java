package com.example.admin_linux.csdevproject.network.retrofit;

import com.example.admin_linux.csdevproject.network.pojo.conversation_details.ConversationDetailsReturnValue;
import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;
import com.example.admin_linux.csdevproject.network.pojo.firebase_user.FirebaseUserReturnValue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    // "api/v1/User/GetByFireBaseId?FireBaseId={FireBaseId}&MobilePhoneNumber={MobilePhoneNumber}"
    @GET("api/v1/User/GetByFireBaseId")
    Call<FirebaseUserReturnValue> getUserGetByFireBaseId(
            @Query("FireBaseId") String FireBaseId,
            @Query("MobilePhoneNumber") String MobilePhoneNumber
    );

    // "api/v1/Conversation/ConversationDetails?ConversationId={ConversationId}&PersonId={PersonId}"
    @GET("api/v1/Conversation/ConversationDetails")
    Call<ConversationDetailsReturnValue> geConversationDetail(
            @Header("Authorization") String BEARER,
            @Query("ConversationId") int conversationId,
            @Query("PersonId") int personId
    );
}