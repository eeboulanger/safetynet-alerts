package com.safetynet.alerts.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        logRequest(request);
        filterChain.doFilter(request, responseWrapper);
        logResponse(responseWrapper);
    }

    private void logRequest(HttpServletRequest request) {
        String fullUri = request.getRequestURI() + (request.getQueryString() != null ?
                "?" + request.getQueryString() : "");
        logger.info("Request {}", fullUri);
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {
        logger.info("Response {}", new String(responseWrapper.getContentAsByteArray()));
        responseWrapper.copyBodyToResponse();
    }
}