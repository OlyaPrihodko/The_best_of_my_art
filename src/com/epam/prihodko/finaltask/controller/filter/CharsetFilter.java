package com.epam.prihodko.finaltask.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 *Choose utf-8 encoding
 */
public class CharsetFilter implements Filter {
    private String encoding;
    public void destroy(){
        encoding=null;
    }
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)throws IOException,ServletException{
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request,response);
    }
    public void init(FilterConfig filterConfig){
        encoding=filterConfig.getInitParameter("characterEncoding");
    }
}
