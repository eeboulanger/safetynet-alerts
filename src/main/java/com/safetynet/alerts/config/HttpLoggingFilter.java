package com.safetynet.alerts.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        if (!request.getRequestURI().startsWith("/actuator")) {
            logRequest(request);
            filterChain.doFilter(request, responseWrapper);
            logResponse(responseWrapper);
        } else {
            filterChain.doFilter(request, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(HttpServletRequest request) {
        String fullUri = request.getRequestURI() + (request.getQueryString() != null ?
                "?" + request.getQueryString() : "");
        logger.info(request.getMethod() + " Request: {}", fullUri);
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {
        if (responseWrapper.getStatus() >= 400) {
            logger.error("Status: " + responseWrapper.getStatus() + " Response: {}", new String(responseWrapper.getContentAsByteArray()));
        } else {
            logger.info("Status: " + responseWrapper.getStatus() + " Response: {}", new String(responseWrapper.getContentAsByteArray()));
        }
        responseWrapper.copyBodyToResponse();
    }
}
