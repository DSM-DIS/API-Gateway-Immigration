//package com.dsm.immigration.utils.filters;
//
//import com.dsm.immigration.domains.service.URISlicer;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class SlicePreFilter extends ZuulFilter {
//
//    private RequestContext context = RequestContext.getCurrentContext();
//
//    @Override
//    public String filterType() {
//        return "pre";
//    }
//
//    @Override
//    public int filterOrder() {
//        return 2;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//
//        HttpServletRequest request = context.getRequest();
//        String uri = URISlicer.slice(request.getRequestURI());
//        String method = request.getMethod();
//
//        if(uri.equals("/user/diary-books")) {
//            request.setAttribute("baseUrl", "https://api.diarystory.site/");
//        } else if(uri.equals("/diary-book/participant")) {
//            request.setAttribute("baseUrl", "http://pyeongan:8892");
//        } else if(uri.equals("/user")) {
//            request.setAttribute("baseUrl", "http://");
//        }
//
//        request.setAttribute("method", method);
//
//        return null;
//    }
//}
