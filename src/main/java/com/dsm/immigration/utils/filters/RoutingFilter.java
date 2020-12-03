package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.dsm.immigration.domains.service.URISlicer;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RoutingFilter extends ZuulFilter {

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
        System.out.println("Routing Filter");

        RequestContext context = RequestContext.getCurrentContext();
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

        System.out.println("uri : " + uri);
        System.out.println("userId : " + userId);
        System.out.println("body : " + body);

        DiaryStoryRequestConnectionService zuulTestRequestConnectionService = retrofit.create(DiaryStoryRequestConnectionService.class);
        try {
            if(method.equals("GET")) {
                System.out.println("들어가기 전");
                zuulTestRequestConnectionService.get(uri, userId).execute();
                System.out.println("들어간 후");
            } else if(method.equals("POST")) {
                zuulTestRequestConnectionService.post(uri, userId, body).execute();
            } else if(method.equals("PATCH")) {
                zuulTestRequestConnectionService.patch(uri, userId, body).execute();
            } else if(method.equals("DELETE")) {
                zuulTestRequestConnectionService.delete(uri, userId).execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("uri : " + uri);
        System.out.println("userId : " + userId);
        System.out.println("body : " + body);

        return null;
    }
}
