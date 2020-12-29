package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.AuthorizationRequestConnectionService;
import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.google.common.io.CharStreams;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import org.apache.catalina.connector.InputBuffer;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        String uri = (String) request.getAttribute("uri");

        BufferedReader reader = null;
        try {
            reader = request.getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        reader.lines()
                .forEach(sb::append);
        String body = sb.toString();
        if(body == null || body.equals(""))
            body = "{}";

        System.out.println("baseUrl: " + baseUrl);
        System.out.println("uri : " + uri);
        System.out.println("userId : " + userId);
        System.out.println("context.body : " + body);

        Response<String> response = null;
        
        if(uri.equals("/user") && method.equals("GET")) {
            AuthorizationRequestConnectionService service = retrofit.create(AuthorizationRequestConnectionService.class);
            response = service.get(request.getHeader("Authorization")).execute();
        } else {
            DiaryStoryRequestConnectionService service = retrofit.create(DiaryStoryRequestConnectionService.class);
            try {
                if(method.equals("GET")) {
                    System.out.println("들어가기 전");
                    response = service.get(uri, userId).execute();
                    System.out.println("들어간 후");
                } else if(method.equals("POST")) {
                    System.out.println("들어가기 전");
                    response = service.post(uri, userId, body).execute();
                    System.out.println("들어간 후");
                } else if(method.equals("PATCH")) {
                    response = service.patch(uri, userId, body).execute();
                } else if(method.equals("DELETE")) {
                    response = service.delete(uri, userId).execute();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        context.setResponseBody(response.body());
        context.setResponseStatusCode(response.code());
        System.out.println("body : " + response.body());

        return null;
    }
}
