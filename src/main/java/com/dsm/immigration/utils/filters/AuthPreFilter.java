package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthPreFilter extends ZuulFilter {

    private final static Logger log = LoggerFactory.getLogger(AuthPreFilter.class);

    private final RequestContext context = RequestContext.getCurrentContext();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("Auth Pre Filter");
        System.out.println("context : " + context);
        HttpServletRequest request = context.getRequest();
        System.out.println("request : " + request);
        String httpMethodName = request.getMethod();
        System.out.println("httpMethodName : " + httpMethodName);
        String uri = request.getRequestURI();

        log.info(String.format("request %s", uri));

        if(httpMethodName.equals("POST") && uri.equals("/auth")) {
            return false;
        } else if(httpMethodName.equals("POST") && uri.equals("/user")) {
            System.out.println("/user POST");
            return false;
        }
        System.out.println("요청은 들어옴");
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gangwon:8080")
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        HttpServletRequest request = context.getRequest();
        String uri = "/user";
        String accessToken = request.getHeader("Authorization");

        DiaryStoryRequestConnectionService zuulTestRequestConnectionService = retrofit.create(DiaryStoryRequestConnectionService.class);
        try {
            Response<String> response = zuulTestRequestConnectionService.get(uri, accessToken,null).execute();
            request.setAttribute("userId", response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
