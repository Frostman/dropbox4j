package ru.frostman.dropbox.api.thr;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxHttpCodeException extends DropboxApiBaseException {
    public DropboxHttpCodeException() {
    }

    public DropboxHttpCodeException(String message) {
        super(message);
    }

    public DropboxHttpCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxHttpCodeException(Throwable cause) {
        super(cause);
    }
}
