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

        if(uri.equals("/user/diary-books")) {
            request.setAttribute("baseUrl", "http://hwanghae");
            request.setAttribute("method", "GET");
        } else if(uri.equals("/diary-book/participaint")) {
            request.setAttribute("baseUrl", "http://pyeongan");
            request.setAttribute("method", "POST");
        }

        return null;
    }
}
