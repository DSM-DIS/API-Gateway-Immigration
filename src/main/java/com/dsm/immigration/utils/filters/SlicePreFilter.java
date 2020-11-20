package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.URISlicer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SlicePreFilter extends ZuulFilter {

    private RequestContext context = RequestContext.getCurrentContext();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("slice");
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Slice Pre Filter");

        HttpServletRequest request = context.getRequest();
        String uri = URISlicer.slice(request.getRequestURI());
        String method = request.getMethod();

        if(uri.equals("/user")) {
//            if(method.equals("POST")) {
//                request.setAttribute("baseUrl", "https://gangwon:8080");
//            } else if(method.equals("GET")) {
//                request.setAttribute("baseUrl", "https://gangwon:8080");
//            } else if(method.equals("DELETE")) {
//                request.setAttribute("baseUrl", "https://gangwon:8080");
//            }
            request.setAttribute("baseUrl", "https://gangwon:8080");
        } else if(uri.equals("/auth")) {
            request.setAttribute("baseUrl", "https://gangwon:8080");
        } else if (uri.equals("/diary-book")) {
            request.setAttribute("baseUrl", "https://hamgyeong:8080");
        } else if (uri.startsWith("/diary-book")) {
            if (method.equals("GET")) {
                request.setAttribute("baseUrl", "https://jeju:4008");
            } else if (method.equals("POST")) {
                request.setAttribute("baseUrl", "https://jeju:4008");
            }
        } else if(uri.equals("/user/diary-books")) {
            request.setAttribute("baseUrl", "https://hwanghae:8891");
        } else if(uri.equals("/diary-book/participant")) {
            request.setAttribute("baseUrl", "https://pyeongan:8892");
        } else {
            request.setAttribute("baseUrl", "localhost:8891");
        }

        request.setAttribute("method", method);

        return null;
    }
}
