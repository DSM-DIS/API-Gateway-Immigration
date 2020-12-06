package com.dsm.immigration.domains.service;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.Executor;

public interface DiaryStoryRequestConnectionService {
//    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @GET("/{uri}")
    Call<String> get(@Path("uri") String uri, @Header("userId") String userId);

//    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @POST("/{uri}")
    Call<String> post(@Path("uri") String uri, @Header("userId") String userId, @Body String body);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @PATCH("/{uri}")
    Call<String> patch(@Path("uri") String uri, @Header("userId") String userId, @Body String body);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @DELETE("/{uri}")
    Call<String> delete(@Path("uri") String uri, @Header("userId") String userId);
}
