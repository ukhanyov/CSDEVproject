package com.example.admin_linux.csdevproject.network.retrofit;

import com.example.admin_linux.csdevproject.network.pojo.ApiResultOfFeedEventsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("api/v1/ActivityCard/FeedEvents?PersonId=" +
            "{PersonId}&LastServerTimeMinutes=" +
            "{LastServerTimeMinutes}&MaxEventCount=" +
            "{MaxEventCount}&StartDateUTC=" +
            "{StartDateUTC}&OrganizationId=" +
            "{OrganizationId}&UserId=" +
            "{UserId}")

    Call<ApiResultOfFeedEventsModel> getActivityCardFeedEvents(
            @Path("PersonId") int personId,
            @Path("LastServerTimeMinutes") int timeMinutes,
            @Path("MaxEventCount") int maxEventCount,
            @Path("StartDateUTC") String startDateUTC,
            @Path("OrganizationId") int organizationId,
            @Path("UserId") int userId
    );
}