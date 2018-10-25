package com.example.admin_linux.csdevproject.network.retrofit;

import com.example.admin_linux.csdevproject.network.pojo.feed_events.ApiResultOfFeedEventsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface GetDataService {
//    @GET("api/v1/ActivityCard/FeedEvents?PersonId=" +
//            "{PersonId}&LastServerTimeMinutes=" +
//            "{LastServerTimeMinutes}&MaxEventCount=" +
//            "{MaxEventCount}&StartDateUTC=" +
//            "{StartDateUTC}&OrganizationId=" +
//            "{OrganizationId}&UserId=" +
//            "{UserId}")
//    Call<ApiResultOfFeedEventsModel> getActivityCardFeedEvents(
//            @Header("Authorization") String BEARER,
//            @Path("PersonId") int personId,
//            @Path("LastServerTimeMinutes") int timeMinutes,
//            @Path("MaxEventCount") int maxEventCount,
//            @Path("StartDateUTC") String startDateUTC,
//            @Path("OrganizationId") int organizationId,
//            @Path("UserId") int userId
//    );

    // "api/v1/ActivityCard/FeedEvents?PersonId={PersonId}"
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/v1/ActivityCard/FeedEvents")
    Call<ApiResultOfFeedEventsModel> getActivityCardFeedEventsByPerson(
            @Header("Authorization") String BEARER,
            @Query("PersonId") int personId,
            @Query("MaxEventCount") int maxEventCount

    );
}