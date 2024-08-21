package com.loy.httprequestsloggerspringbootstarter.config;

import com.loy.httprequestsloggerspringbootstarter.interceptor.HttpRequestLogInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(HttpRequestLogProperty.class)
@ConditionalOnProperty(prefix = "http-request-log", value = "enabled", havingValue = "true")
public class HttpRequestLoggerAutoConfiguration {

    @Bean
    public HttpRequestLogInterceptor loggingInterceptor(HttpRequestLogProperty httpRequestLogProperty) {
        return new HttpRequestLogInterceptor(httpRequestLogProperty);
    }

    @Bean
    @ConditionalOnBean(name = "loggingInterceptor")
    public WebMvcConfigurer webMvcConfigurer(HttpRequestLogInterceptor httpRequestLogInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(httpRequestLogInterceptor);
            }
        };
    }
}
