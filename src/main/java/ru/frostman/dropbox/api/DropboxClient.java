package ru.frostman.dropbox.api;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import ru.frostman.dropbox.api.model.AccountInfo;
import ru.frostman.dropbox.api.model.Entry;
import ru.frostman.dropbox.api.util.Json;

import javax.annotation.Nullable;

import static ru.frostman.dropbox.api.util.DropboxError.check;
import static ru.frostman.dropbox.api.util.DropboxError.checkMetadata;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxClient {
    private static final String INFO_URL = "https://api.dropbox.com/0/account/info";
    private static final String METADATA_URL = "https://api.dropbox.com/0/metadata/dropbox";

    private final OAuthService service;
    private final Token accessToken;

    DropboxClient(OAuthService service, Token accessToken) {
        this.service = service;
        this.accessToken = accessToken;
    }

    public Token getAccessToken() {
        return accessToken;
    }

    public AccountInfo getAccountInfo() {
        OAuthRequest request = new OAuthRequest(Verb.GET, INFO_URL);
        service.signRequest(accessToken, request);
        String content = check(request.send()).getBody();

        return Json.parse(content, AccountInfo.class);
    }

    public Entry getMetadata(String path) {
        return getMetadata(path, 10000, null, true);
    }

    public Entry getMetadata(String path, int fileLimit) {
        return getMetadata(path, fileLimit, null, true);
    }

    public Entry getMetadata(String path, String hash) {
        return getMetadata(path, 10000, hash, true);
    }

    public Entry getMetadata(String path, int fileLimit, @Nullable String hash) {
        return getMetadata(path, fileLimit, hash, true);
    }

    public Entry getMetadata(String path, int fileLimit, @Nullable String hash, boolean list) {
        OAuthRequest request = new OAuthRequest(Verb.GET, METADATA_URL + path);

        if (fileLimit != 10000)
            request.addQuerystringParameter("file_limit", Integer.toString(fileLimit));

        if (hash != null) {
            request.addQuerystringParameter("hash", hash);
        }

        if (!list) {
            request.addQuerystringParameter("list", "false");
        }

        service.signRequest(accessToken, request);
        String content = checkMetadata(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

}
