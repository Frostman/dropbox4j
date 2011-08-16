package ru.frostman.dropbox.api.thr;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxBaseException extends RuntimeException {
    public DropboxBaseException() {
    }

    public DropboxBaseException(String message) {
        super(message);
    }

    public DropboxBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxBaseException(Throwable cause) {
        super(cause);
    }
}
