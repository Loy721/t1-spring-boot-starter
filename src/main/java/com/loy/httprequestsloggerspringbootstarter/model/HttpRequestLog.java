package com.loy.httprequestsloggerspringbootstarter.model;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class HttpRequestLog {
    private String url;
    private HttpMethod httpMethod;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
    private HttpStatus statusCode;
    private long duration;


    @Override
    public String toString() {
        return "Метод запроса: " + httpMethod + "\n" +
                "URL: " + url + "\n" +
                "Статус: " + statusCode + "\n" +
                "Заголовки запроса: " + requestHeaders + "\n" +
                "Заголовки ответа: " + responseHeaders + "\n" +
                (duration == 0 ? "" : "Время выполнения запроса: " + duration + " мс" + "\n");
    }
}
