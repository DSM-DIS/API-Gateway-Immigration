package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.dsm.immigration.domains.service.URISlicer;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RoutingFilter extends ZuulFilter {

    private RequestContext context = RequestContext.getCurrentContext();

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        HttpServletRequest request = context.getRequest();
        String userId = (String) request.getAttribute("userId");
        String baseUrl = (String) request.getAttribute("baseUrl");
        String method = (String) request.getAttribute("method");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        String uri = URISlicer.slice(request.getRequestURI());
        String body = context.getResponseBody();

        DiaryStoryRequestConnectionService zuulTestRequestConnectionService = retrofit.create(DiaryStoryRequestConnectionService.class);
        try {
            if(method.equals("GET")) {
                zuulTestRequestConnectionService.get(uri, userId, body).execute();
            } else if(method.equals("POST")) {
                zuulTestRequestConnectionService.post(uri, userId, body).execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
