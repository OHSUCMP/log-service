package edu.ohsu.cmp.logservice.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final String BEARER_PREFIX = "Bearer ";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecurityConfig security;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAuthorized = false;

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization)) {
            if (authorization.startsWith(BEARER_PREFIX)) {
                String clientToken = authorization.substring(BEARER_PREFIX.length());
                isAuthorized = security.hasAuthorizedClient(clientToken);
            }
        }

        if (isAuthorized) {
            logger.info("OK : \"{}\"", authorization);
            return true;

        } else {
            logger.warn("Unauthorized : \"{}\" ", authorization);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
