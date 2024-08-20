package com.loy.httprequestsloggerspringbootstarter.init;

import com.loy.httprequestsloggerspringbootstarter.exception.LoggingStartupException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class LoggingFailureAnalyzer extends AbstractFailureAnalyzer<LoggingStartupException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LoggingStartupException cause) {
        return new FailureAnalysis(cause.getMessage(),"Укажите валидные значения для свойств", cause);
    }
}
