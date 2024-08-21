package com.loy.httprequestsloggerspringbootstarter.init;

import com.loy.httprequestsloggerspringbootstarter.exception.LogStartupException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;


@Slf4j
public class LogEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Вызов LogEnvironmentPostProcessor");
        String enablePropertyValue = environment.getProperty("http-request-log.enabled");
        String durationLogPropertyValue = environment.getProperty("http-request-log.duration-log");

        validateBooleanString(enablePropertyValue, "http-request-log.enabled");
        validateBooleanString(durationLogPropertyValue, "http-request-log.duration-log");
    }

    private void validateBooleanString(String property, String nameProperty) {
        if (property != null
                && !property.equalsIgnoreCase("false")
                && !property.equalsIgnoreCase("true")) {
            throw new LogStartupException("Ошибка при проверке свойства " + nameProperty + " (может содержать только: true или false)");
        }
    }
}
