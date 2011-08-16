package ru.frostman.dropbox.api.util;

import org.scribe.model.Response;

import java.util.Map;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxError {
    //  Standard Dropbox errors
    private static final Map<Integer, String> DROPBOX_ERRORS = Maps.createHashMap();

    static {
        DROPBOX_ERRORS.put(503, "Your app is making too many requests and is being rate limited. Our limits are fairly generous, but you may trigger 503s both on a per-app and per-user basis.");
        DROPBOX_ERRORS.put(507, "User over quota.");
    }


    //  Standard OAuth layer errors
    private static final Map<Integer, String> OAUTH_ERRORS = Maps.createHashMap();

    static {
        OAUTH_ERRORS.put(401, "Bad or expired token. This can happen if the user or Dropbox revoked or expired an access token. To fix, simply re-authenticate the user.");
        OAUTH_ERRORS.put(403, "Bad OAuth request (wrong consumer token, bad nonce, expired timestamp, ...). Unfortunately, re-authenticating the user won't help here.");
    }

    //  Standard API layer errors
    private static final Map<Integer, String> API_ERRORS = Maps.createHashMap();

    static {
        API_ERRORS.put(400, "Bad input parameter. Error message should indicate which one and why. (editor's note: in the future these will probably all be dictionaries of error field name to error reason)");
        API_ERRORS.put(405, "Request method not expected (generally should be GET or POST).");
    }

    //  Metadata request errors
    private static final Map<Integer, String> METADATA_ERRORS = Maps.createHashMap();

    static {
        METADATA_ERRORS.put(304, "Directory contents have not changed (relies on 'hash' parameter).");
        METADATA_ERRORS.put(400, "Operation attempted not allowed by token type. Root parameter is not full access or Sandbox.");
        METADATA_ERRORS.put(404, "File path not found.");
        METADATA_ERRORS.put(406, "Too many file entries to return.");
    }

    //  Thumbnails request errors
    private static final Map<Integer, String> THUMBNAILS_ERRORS = Maps.createHashMap();

    static {
        THUMBNAILS_ERRORS.put(404, "File extension doesn't allow thumbnailing.");
        THUMBNAILS_ERRORS.put(415, "Image is invalid and cannot be thumbnailed.");
        THUMBNAILS_ERRORS.put(500, "No thumbnail available due to system error.");
    }

    //  Copy requests errors
    private static final Map<Integer, String> COPY_ERRORS = Maps.createHashMap();

    static {
        COPY_ERRORS.put(304, "Directory contents have not changed (relies on 'hash' parameter).");
        COPY_ERRORS.put(400, "Operation attempted not allowed by token type. Root parameter is not full access or Sandbox.");
        COPY_ERRORS.put(404, "Specified file path not found.");
        COPY_ERRORS.put(507, "User over quota.");
    }

    //  Create folder requests errors
    private static final Map<Integer, String> CREATE_FOLDER_ERRORS = Maps.createHashMap();

    static {
        CREATE_FOLDER_ERRORS.put(400, "Root parameter not Dropbox or sandbox. Operation attempted not allowed by token type. No path specified.");
        CREATE_FOLDER_ERRORS.put(403, "A file or folder already exists at the location specified by the path.");
        CREATE_FOLDER_ERRORS.put(404, "Specified file path not found.");
        CREATE_FOLDER_ERRORS.put(507, "User over quota.");
    }

    // Delete requests errors
    private static final Map<Integer, String> DELETE_ERRORS = Maps.createHashMap();

    static {
        DELETE_ERRORS.put(400, "Root parameter not Dropbox or sandbox. Operation attempted not allowed by token type. No path specified.");
        DELETE_ERRORS.put(404, "Specified file path not found.");
    }

    // Move requests errors
    private static final Map<Integer, String> MOVE_ERRORS = Maps.createHashMap();

    static {
        MOVE_ERRORS.put(400, "Operation attempted not allowed by token type. Root parameter is not Dropbox or Sandbox.");
        MOVE_ERRORS.put(403, "File operation forbidden.");
        MOVE_ERRORS.put(404, "Specified file path not found.");
        MOVE_ERRORS.put(507, "User over quota.");
    }

    // Files requests errors
    private static final Map<Integer, String> FILES_ERRORS = Maps.createHashMap();

    static {
        FILES_ERRORS.put(304, "Directory contents have not changed (relies on 'hash' parameter).");
        FILES_ERRORS.put(400, "Operation attempted not allowed by token type. Root parameter is not full access or Sandbox.");
        FILES_ERRORS.put(404, "File path not found.");
        FILES_ERRORS.put(406, "Too many file entries to return.");
        FILES_ERRORS.put(411, "Length required. This typically returns when you attempt to upload over HTTP using chunked encoding. The Dropbox API currently does not support chunked transfer encoding.");
    }

    private static void throwError(Map<Integer, String> errors, int code) {
        String msg = errors.get(code);

        if (msg != null) {
            //todo impl
            throw new RuntimeException("Dropbox error: " + msg);
        }
    }

    public static Response check(Response response) {
        int code = response.getCode();

        throwError(DROPBOX_ERRORS, code);
        throwError(OAUTH_ERRORS, code);
        throwError(API_ERRORS, code);

        if (code != 200) {
            //todo impl
            throw new RuntimeException("Unknown error with code: " + code);
        }

        return response;
    }

    public static Response checkMetadata(Response response) {
        int code = response.getCode();

        throwError(METADATA_ERRORS, code);

        return check(response);
    }

    public static Response checkThumbnails(Response response) {
        int code = response.getCode();

        throwError(THUMBNAILS_ERRORS, code);

        return check(response);
    }

    public static Response checkCopy(Response response) {
        int code = response.getCode();

        throwError(COPY_ERRORS, code);

        return check(response);
    }

    public static Response checkMove(Response response) {
        int code = response.getCode();

        throwError(MOVE_ERRORS, code);

        return check(response);
    }

    public static Response checkDelete(Response response) {
        int code = response.getCode();

        throwError(DELETE_ERRORS, code);

        return check(response);
    }

    public static Response checkCreateFolder(Response response) {
        int code = response.getCode();

        throwError(CREATE_FOLDER_ERRORS, code);

        return check(response);
    }

    public static Response checkFiles(Response response) {
        int code = response.getCode();

        throwError(FILES_ERRORS, code);

        return check(response);
    }
}
