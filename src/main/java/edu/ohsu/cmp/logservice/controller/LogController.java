package edu.ohsu.cmp.logservice.controller;

import edu.ohsu.cmp.logservice.Constants;
import edu.ohsu.cmp.logservice.model.LogRequest;
import edu.ohsu.cmp.logservice.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/log")
public class LogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;

    // useful: https://www.baeldung.com/spring-cors

    @CrossOrigin
    @PostMapping(value = "do-log", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> doLog(HttpServletRequest request,
                                        HttpSession session,
                                        @RequestBody LogRequest logRequest) {

        logger.info("executing log request for session " + session.getId());

        String clientAppName = String.valueOf(request.getAttribute(Constants.CLIENT_APP_NAME_ATTRIBUTE));

        try {
            logService.doLog(
                    clientAppName,
                    sanitize(logRequest.getSessionId(), 50),
                    logRequest.getLogLevel(),
                    sanitize(logRequest.getEvent(), 50),
                    sanitize(logRequest.getPage(), 50),
                    sanitize(logRequest.getMessage(), 1000)
            );
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            logger.error("caught " + e.getClass().getSimpleName() + " writing log - " + e.getMessage(), e);
            if (logger.isDebugEnabled()) {
                logger.debug("logRequest=" + logRequest);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final Pattern SANITIZE_PATTERN = Pattern.compile("[^a-zA-Z0-9 ():.-]");
    private String sanitize(String s, int maxLength) {
        if (StringUtils.isEmpty(s)) return s;

        String sanitized = SANITIZE_PATTERN.matcher(s).replaceAll("?");
        if (sanitized.length() > maxLength) {
            sanitized = sanitized.substring(0, maxLength - 1) + " (truncated)";
        }

        if ( ! sanitized.equals(s) ) {
            logger.debug("sanitized: \"" + sanitized + "\"");
        }

        return sanitized;
    }
}
