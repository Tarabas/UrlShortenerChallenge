package net.rorarius.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "urlshortener")
public class AppConfig {

    private Long expireMinutes;

    public Long getExpireMinutes() {
        return expireMinutes;
    }

    public void setExpireMinutes(Long expireMinutes) {
        this.expireMinutes = expireMinutes;
    }
}
