package com.loy.httprequestsloggerspringbootstarter.config;

import com.loy.httprequestsloggerspringbootstarter.exception.LogStartupException;
import com.loy.httprequestsloggerspringbootstarter.init.LogEnvironmentPostProcessor;
import com.loy.httprequestsloggerspringbootstarter.interceptor.HttpRequestLogInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class HttpRequestLoggerAutoConfigurationTest {

    private final ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(HttpRequestLoggerAutoConfiguration.class));

    @Test
    void httpRequestLogInterceptorIsNotRegisteredWhenEnabledIsFalse() {
        applicationContextRunner
                .withPropertyValues("http-request-log.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(HttpRequestLogInterceptor.class);
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }

    @Test
    void httpRequestLogInterceptorIsRegisteredWhenEnabledIsTrue() {
        applicationContextRunner
                .withPropertyValues("http-request-log.enabled=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(HttpRequestLogInterceptor.class);
                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);

                    WebMvcConfigurer configurer = context.getBean(WebMvcConfigurer.class);
                    InterceptorRegistry registry = mock(InterceptorRegistry.class);
                    configurer.addInterceptors(registry);
                    verify(registry).addInterceptor(any(HandlerInterceptor.class));
                });
    }

    @Test
    void whenInvalidPropertyEnabled_thenThrowLogStartupException() {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("test", Collections.singletonMap("http-request-log.enabled", "test")));

        LogEnvironmentPostProcessor processor = new LogEnvironmentPostProcessor();

        assertThatExceptionOfType(LogStartupException.class)
                .isThrownBy(() -> processor.postProcessEnvironment(environment, new SpringApplication()))
                .withMessageContaining("Ошибка при проверке свойства http-request-log.enabled (может содержать только: true или false)");
    }
}