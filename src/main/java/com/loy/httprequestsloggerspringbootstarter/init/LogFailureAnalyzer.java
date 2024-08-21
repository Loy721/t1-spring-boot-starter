package com.loy.httprequestsloggerspringbootstarter.init;

import com.loy.httprequestsloggerspringbootstarter.exception.LogStartupException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class LogFailureAnalyzer extends AbstractFailureAnalyzer<LogStartupException> {
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, LogStartupException cause) {
        return new FailureAnalysis(cause.getMessage(),"Укажите валидные значения для свойств", cause);
    }
}
