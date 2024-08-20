package com.loy.httprequestsloggerspringbootstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "http-request-log")
public class HttpRequestLogProperty {
    private boolean enabled;
    private boolean durationLog;
}
