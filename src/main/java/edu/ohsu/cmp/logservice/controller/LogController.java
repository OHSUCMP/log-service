package edu.ohsu.cmp.logservice.controller;

import edu.ohsu.cmp.logservice.Constants;
import edu.ohsu.cmp.logservice.model.LogRequest;
import edu.ohsu.cmp.logservice.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;

    @PostMapping(value = "do-log", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> doLog(HttpServletRequest request,
                                        HttpSession session,
                                        @RequestBody LogRequest logRequest) {

        logger.info("executing log request for session " + session.getId());

        String clientAppName = String.valueOf(request.getAttribute(Constants.CLIENT_APP_NAME_ATTRIBUTE));

        try {
            logService.doLog(
                    clientAppName,
                    logRequest.getLogLevel(),
                    logRequest.getEvent(),
                    logRequest.getPage(),
                    logRequest.getMessage()
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
}
