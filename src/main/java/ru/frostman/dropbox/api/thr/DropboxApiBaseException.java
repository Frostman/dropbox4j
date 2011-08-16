package ru.frostman.dropbox.api.thr;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxApiBaseException extends RuntimeException {
    public DropboxApiBaseException() {
    }

    public DropboxApiBaseException(String message) {
        super(message);
    }

    public DropboxApiBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxApiBaseException(Throwable cause) {
        super(cause);
    }
}
