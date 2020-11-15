package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.DiaryStoryRequestConnectionService;
import com.dsm.immigration.domains.service.URISlicer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthPreFilter extends ZuulFilter {

    private final static Logger log = LoggerFactory.getLogger(AuthPreFilter.class);

    private RequestContext context = RequestContext.getCurrentContext();

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
        HttpServletRequest request = context.getRequest();
        String httpMethodName = request.getMethod();
        String uri = request.getRequestURI();

        log.info(String.format("request %s", uri));

        if(httpMethodName.equals("POST") && uri.equals("/auth")) {
            System.out.println("/auth POST 요청");
            return false;
        } else if(httpMethodName.equals("POST") && uri.equals("/user")) {
            System.out.println("/user POST 요청");
            return false;
        }
        System.out.println("요청은 들어옴");
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.diarystory.site")
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        HttpServletRequest request = context.getRequest();
        String uri = URISlicer.slice(request.getRequestURI());
        String accessToken = request.getHeader("Authorization");

        DiaryStoryRequestConnectionService zuulTestRequestConnectionService = retrofit.create(DiaryStoryRequestConnectionService.class);
        try {
            Response<String> response = zuulTestRequestConnectionService.get(uri, accessToken,null).execute();
            HttpServletRequest httpServletRequest = context.getRequest();
            httpServletRequest.setAttribute("userId", response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
