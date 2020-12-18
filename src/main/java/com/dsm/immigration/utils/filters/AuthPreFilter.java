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

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String httpMethodName = request.getMethod();
        String uri = request.getRequestURI();
        request.setAttribute("uri", uri);

        log.info(String.format("request %s [%s]", uri, httpMethodName));

        if(httpMethodName.equals("POST") && uri.equals("/auth")) {           // 로그인
            System.out.println("로그인::로그인::로그인::로그인::로그인");
            return false;
        } else if(httpMethodName.equals("POST") && uri.equals("/user")) {    // 회원가입
            return false;
        } else if(uri.equals("/testtesttest")) {                             // 테스트
            return false;
        } else if(uri.equals("/user/test")) {                                // 테스트
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("유저 아이디를 얻기 위한 과정");
        RequestContext context = RequestContext.getCurrentContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gangwon:8080")
                .client(new OkHttpClient().newBuilder().build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        HttpServletRequest request = context.getRequest();
        String uri = "/user";
        String accessToken = request.getHeader("Authorization");
        log.info(String.format("request %s [%s]", uri, context.getRequest().getMethod()));

        DiaryStoryRequestConnectionService service = retrofit.create(DiaryStoryRequestConnectionService.class);
        try {
            System.out.println("강원 들어가기 전");
            Response<String> response = service.get(uri, accessToken).execute();
            System.out.println("강원 들어간 후");
            System.out.println("가져온 userId :: " + response.body());
            request.setAttribute("userId", response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
