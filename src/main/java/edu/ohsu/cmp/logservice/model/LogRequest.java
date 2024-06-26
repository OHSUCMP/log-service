package edu.ohsu.cmp.logservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ohsu.cmp.logservice.exception.CaseNotHandledException;
import org.slf4j.event.Level;

public class LogRequest {
    public static final String LEVEL_ERROR = "error";
    public static final String LEVEL_WARN = "warn";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_TRACE = "trace";

    private String sessionId;   // session identifier
    private String level;       // ERROR, WARN, INFO, DEBUG
    private String event;       // the WHAT: login, logout, someButtonClick, somethingElse, etc.
    private String page;        // the WHERE: Home, Vitals, Questionnaire, some-html-page.html, etc.
    private String message;     // "user logged in", "something went wrong", etc.

    @Override
    public String toString() {
        return "LogRequest{" +
                "sessionId='" + sessionId + '\'' +
                ", level='" + level + '\'' +
                ", event='" + event + '\'' +
                ", page='" + page + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @JsonIgnore
    public Level getLogLevel() throws CaseNotHandledException {
        if      (LEVEL_ERROR.equalsIgnoreCase(level)) return Level.ERROR;
        else if (LEVEL_WARN.equalsIgnoreCase(level)) return Level.WARN;
        else if (LEVEL_INFO.equalsIgnoreCase(level)) return Level.INFO;
        else if (LEVEL_DEBUG.equalsIgnoreCase(level)) return Level.DEBUG;
        else if (LEVEL_TRACE.equalsIgnoreCase(level)) return Level.TRACE;
        else throw new CaseNotHandledException("invalid level: " + level);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
