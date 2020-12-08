package com.dsm.immigration.domains.service;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.Executor;

public interface DiaryStoryRequestConnectionService {
    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @GET
    Call<String> get(@Url String uri, @Header("userId") String userId);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @POST
    Call<String> post(@Url String uri, @Header("userId") String userId, @Body String body);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @PATCH
    Call<String> patch(@Url String uri, @Header("userId") String userId, @Body String body);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @DELETE
    Call<String> delete(@Url String uri, @Header("userId") String userId);
}
