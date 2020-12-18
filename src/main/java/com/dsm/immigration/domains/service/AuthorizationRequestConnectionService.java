package com.dsm.immigration.domains.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface AuthorizationRequestConnectionService {
    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @GET("/user")
    Call<String> get(@Header("Authorization") String token);
}
