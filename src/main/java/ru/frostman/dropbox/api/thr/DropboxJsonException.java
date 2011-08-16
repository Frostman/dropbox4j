package ru.frostman.dropbox.api.thr;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxJsonException extends DropboxBaseException {
    public DropboxJsonException() {
    }

    public DropboxJsonException(String message) {
        super(message);
    }

    public DropboxJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxJsonException(Throwable cause) {
        super(cause);
    }
}
