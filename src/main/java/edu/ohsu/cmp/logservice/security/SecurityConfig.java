package edu.ohsu.cmp.logservice.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {
    private Map<String, String> authorizedClients;

    public Map<String, String> getAuthorizedClients() {
        return authorizedClients;
    }

    public void setAuthorizedClients(Map<String, String> authorizedClients) {
        this.authorizedClients = authorizedClients;
    }

    public String getAuthorizedClientAppName(String clientToken) {
        return StringUtils.isNotBlank(clientToken) && authorizedClients != null ?
                authorizedClients.get(clientToken) :
                null;
    }
}
