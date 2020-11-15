package com.dsm.immigration.utils.filters;

import com.dsm.immigration.domains.service.URISlicer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

public class SlicePreFilter extends ZuulFilter {

    private RequestContext context = RequestContext.getCurrentContext();

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
        }

        request.setAttribute("method", method);

        return null;
    }
}
