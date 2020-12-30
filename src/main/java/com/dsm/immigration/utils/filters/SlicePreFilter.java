package com.dsm.immigration.utils.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SlicePreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String uri = (String) request.getAttribute("uri");
        String method = request.getMethod();

        if(uri.equals("/user") || uri.equals("/user/username")) {
            request.setAttribute("baseUrl", "http://gangwon:8080");
        } else if(uri.equals("/auth")) {
            request.setAttribute("baseUrl", "http://gangwon:8080");
        } else if (uri.equals("/diary-book")) {
            request.setAttribute("baseUrl", "http://hamgyeong:8080");
        } else if(uri.equals("/diary-book/participant")) {
            request.setAttribute("baseUrl", "http://pyeongan:8892");
        } else if (uri.startsWith("/diary-book")) {
            if (method.equals("GET")) {
                request.setAttribute("baseUrl", "http://jeju:4008");
            } else if (method.equals("POST")) {
                request.setAttribute("baseUrl", "http://jeju:4008");
            }
        } else if(uri.equals("/user/diary-books")) {
            request.setAttribute("baseUrl", "http://hwanghae:8891");
        } else if(uri.equals("/testtesttest")) {
            request.setAttribute("baseUrl", "http://hwanghae:8891");
        } else if (uri.equals("/user/test")) {
            request.setAttribute("baseUrl", "http://gangwon:8080");
        } else {
            request.setAttribute("baseUrl", "localhost:8891");
        }

        request.setAttribute("method", method);

        return null;
    }
}
