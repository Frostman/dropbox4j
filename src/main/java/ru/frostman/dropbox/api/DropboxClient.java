package ru.frostman.dropbox.api;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import ru.frostman.dropbox.api.model.AccountInfo;
import ru.frostman.dropbox.api.util.Json;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxClient {
    private static final String INFO_URL = "https://api.dropbox.com/0/account/info";

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
        String content = request.send().getBody();

        //todo think about errors handling
        return Json.parse(content, AccountInfo.class);
    }

}
