package ru.frostman.dropbox.api.thr;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxDateFormatException extends DropboxBaseException {
    public DropboxDateFormatException() {
    }

    public DropboxDateFormatException(String message) {
        super(message);
    }

    public DropboxDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DropboxDateFormatException(Throwable cause) {
        super(cause);
    }
}
