package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.dsm.immigration.domains.service.URISlicer;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Response;
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
                .baseUrl("http://naver.com")
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        String uri = URISlicer.slice(request.getRequestURI());
//        String uri = request.getRequestURI();
        String body = context.getResponseBody();
        
        System.out.println("baseUrl: " + baseUrl);
        System.out.println("uri : " + uri);
        System.out.println("userId : " + userId);
        System.out.println("context.body : " + context.getResponseBody());

        DiaryStoryRequestConnectionService service = retrofit.create(DiaryStoryRequestConnectionService.class);
        Response<String> response = null;
        try {
            if(method.equals("GET")) {
                System.out.println("들어가기 전");
                response = service.get(uri, userId).execute();
                System.out.println("들어간 후");
            } else if(method.equals("POST")) {
                response = service.post(uri, userId, body).execute();
            } else if(method.equals("PATCH")) {
                response = service.patch(uri, userId, body).execute();
            } else if(method.equals("DELETE")) {
                response = service.delete(uri, userId).execute();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestContext afterContext = RequestContext.getCurrentContext();
        afterContext.setResponseBody(response.body());

        System.out.println("uri : " + uri);
        System.out.println("userId : " + userId);
        System.out.println("response.body : " + response.body());
        System.out.println("header to string : " + response.headers.toString());
        System.out.println("context.body : " + afterContext.getResponseBody());

        return null;
    }
}
