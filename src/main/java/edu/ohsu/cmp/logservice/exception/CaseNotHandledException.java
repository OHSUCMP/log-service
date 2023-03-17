package edu.ohsu.cmp.logservice.exception;

public class CaseNotHandledException extends Exception {
    public CaseNotHandledException() {
    }

    public CaseNotHandledException(String message) {
        super(message);
    }

    public CaseNotHandledException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaseNotHandledException(Throwable cause) {
        super(cause);
    }

    public CaseNotHandledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
