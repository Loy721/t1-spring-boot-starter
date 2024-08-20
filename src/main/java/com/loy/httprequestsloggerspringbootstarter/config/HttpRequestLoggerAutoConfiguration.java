package com.loy.httprequestsloggerspringbootstarter.config;

import com.loy.httprequestsloggerspringbootstarter.interceptor.LoggingInterceptor;
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
    public LoggingInterceptor loggingInterceptor(HttpRequestLogProperty httpRequestLogProperty) {
        return new LoggingInterceptor(httpRequestLogProperty);
    }

    @Bean
    @ConditionalOnBean(name = "loggingInterceptor")
    public WebMvcConfigurer webMvcConfigurer(LoggingInterceptor loggingInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loggingInterceptor);
            }
        };
    }
}
