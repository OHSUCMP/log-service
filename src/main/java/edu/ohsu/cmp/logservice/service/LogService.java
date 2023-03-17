package edu.ohsu.cmp.logservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;


@Service
public class LogService {
    private final Logger remoteLogger = LoggerFactory.getLogger("REMOTE");

    public void doLog(String clientAppName, Level level, String event, String page, String message) {
        remoteLogger.atLevel(level).log("clientApp={}, event={}, page={}, message={}",
                    clientAppName,
                    event,
                    page,
                    message);
    }
}
