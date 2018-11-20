package com.exception;

/**
 * the exception of threadPool
 * @author chenjun
 */
public class WorkThredPoolException extends Exception {

    public WorkThredPoolException(String message) {
        super(message);
    }

    public WorkThredPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkThredPoolException() {
        super();
    }

    public WorkThredPoolException(Throwable cause) {
        super(cause);
    }
}
