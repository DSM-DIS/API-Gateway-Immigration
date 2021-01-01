package com.dsm.immigration.domains.service;

import org.springframework.web.bind.annotation.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthorizationRequestConnectionService {
    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @GET
    Call<String> get(@Url String uri, @Header("Authorization") String token);

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @POST
    Call<String> post(@Url String uri, @Header("Authorization") String token, @RequestBody String body);
}
