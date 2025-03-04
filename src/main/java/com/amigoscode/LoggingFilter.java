package com.amigoscode;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("LoggingFilter");
        request.getHeaderNames().
                asIterator().
                forEachRemaining(
                        n -> System.out.println(n + " : " + request.getHeader(n))
                );

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
