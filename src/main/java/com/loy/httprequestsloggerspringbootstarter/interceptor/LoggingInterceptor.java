package com.loy.httprequestsloggerspringbootstarter.interceptor;

import com.loy.httprequestsloggerspringbootstarter.model.HttpRequestLog;
import com.loy.httprequestsloggerspringbootstarter.config.HttpRequestLogProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final HttpRequestLogProperty httpRequestLogProperty;
    private final static String START_TIME_ATTRIBUTE = "start-time";

    private final static String TRACE_ID = "trace-id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (httpRequestLogProperty.isDurationLog()) {
            long startTime = System.currentTimeMillis();
            request.setAttribute(START_TIME_ATTRIBUTE, startTime);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long duration = 0;
        if (httpRequestLogProperty.isDurationLog()) {
            long startTime = (long) request.getAttribute(START_TIME_ATTRIBUTE);
            duration = System.currentTimeMillis() - startTime;
        }
        HttpRequestLog httpRequestLog = createHttpRequestLog(request, response, duration);
        log.info("Http info: {}", httpRequestLog);
    }

    private HttpRequestLog createHttpRequestLog(HttpServletRequest request, HttpServletResponse response, long duration) {
        HttpRequestLog httpRequestLog = new HttpRequestLog();
        httpRequestLog.setRequestHeaders(getHeaders(request));
        httpRequestLog.setResponseHeaders(getHeaders(response));
        httpRequestLog.setUrl(request.getRequestURL().toString());//TODO
        httpRequestLog.setDuration(duration);
        httpRequestLog.setHttpMethod(HttpMethod.valueOf(request.getMethod()));
        httpRequestLog.setStatusCode(HttpStatus.valueOf(response.getStatus()));
        return httpRequestLog;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }
        return headers;
    }
    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        for (String name : response.getHeaderNames()) {
            String value = response.getHeader(name);
            headers.put(name, value);
        }
        return headers;
    }

}
