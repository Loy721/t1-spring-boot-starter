package com.loy.httprequestsloggerspringbootstarter.interceptor;

import com.loy.httprequestsloggerspringbootstarter.config.HttpRequestLogProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Enumeration;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpRequestLogInterceptorTest {

    @Mock
    private HttpRequestLogProperty httpRequestLogProperty;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private HttpRequestLogInterceptor httpRequestLogInterceptor;

    @Test
    void preHandle() throws Exception {

        when(httpRequestLogProperty.isDurationLog()).thenReturn(true);

        httpRequestLogInterceptor.preHandle(request, mock(), mock());

        verify(request).setAttribute(anyString(), anyLong());
    }

    @Test
    void afterCompletion() throws Exception {
        Enumeration<String> enumeration = mock();
        when(request.getHeaderNames()).thenReturn(enumeration);
        when(request.getRequestURL()).thenReturn(mock());
        when(request.getMethod()).thenReturn("PUT");
        when(enumeration.hasMoreElements()).thenReturn(false);
        HttpServletResponse response = mock();
        when(response.getHeaderNames()).thenReturn(Collections.emptyList());
        when(response.getStatus()).thenReturn(200);

        httpRequestLogInterceptor.afterCompletion(request, response, mock(), mock());

        verify(request).getRequestURL();
        verify(request).getHeaderNames();
        verify(response).getHeaderNames();
        verify(response).getStatus();
        verify(request).getMethod();
    }
}