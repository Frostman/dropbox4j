package ru.frostman.dropbox.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DropBoxApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.Preconditions;
import ru.frostman.dropbox.api.auth.DropboxAuthentication;
import ru.frostman.dropbox.api.auth.MobileAuthentication;
import ru.frostman.dropbox.api.auth.WebAuthentication;

import static org.scribe.utils.URLUtils.formURLEncode;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxClientBuilder implements DropboxAuthentication, WebAuthentication, MobileAuthentication {
    private static final Verifier EMPTY_VERIFIER = new Verifier("");
    private static final String MOBILE_AUTH_URL = "https://api.dropbox.com/0/token";

    private final String appKey;
    private final String appSecret;

    private String callback;

    private final OAuthService service;

    private Token requestToken = null;
    private Token accessToken = null;

    private DropboxClientBuilder(String appKey, String appSecret) {
        Preconditions.checkNotNull(appKey, "App key cannot be null");
        Preconditions.checkNotNull(appSecret, "App secret cannot be null");

        this.appKey = appKey;
        this.appSecret = appSecret;

        service = new ServiceBuilder()
                .provider(DropBoxApi.class)
                .apiKey(appKey)
                .apiSecret(appSecret)
                .build();

        requestToken = service.getRequestToken();
    }

    public static DropboxAuthentication build(String appKey, String appSecret) {
        return new DropboxClientBuilder(appKey, appSecret);
    }

    public static DropboxClient build(String appKey, String appSecret, Token accessToken) {
        Preconditions.checkNotNull(accessToken, "Access token cannot be null");

        DropboxClientBuilder builder = new DropboxClientBuilder(appKey, appSecret);
        builder.accessToken = accessToken;

        return builder.buildDropboxClient();
    }

    public static DropboxClient build(String appKey, String appSecret, String token, String secret) {
        Preconditions.checkNotNull(token, "Token cannot be null");
        Preconditions.checkNotNull(secret, "Secret cannot be null");

        DropboxClientBuilder builder = new DropboxClientBuilder(appKey, appSecret);
        builder.accessToken = new Token(token, secret);

        return builder.buildDropboxClient();
    }

    private DropboxClient buildDropboxClient() {
        return new DropboxClient(service, accessToken);
    }

    // --- DropboxAuthentication Impl ---

    public WebAuthentication web() {
        return this;
    }

    public WebAuthentication web(String callback) {
        Preconditions.checkValidOAuthCallback(callback, "Callback should be valid url or 'oob'");

        this.callback = callback;

        return this;
    }

    public MobileAuthentication mobile() {
        return this;
    }

    // --- WebAuthentication Impl ---

    public String getAuthorizationUrl() {
        return service.getAuthorizationUrl(requestToken)
                + "&" + OAuthConstants.CALLBACK + "=" + formURLEncode(callback);
    }

    public DropboxClient receiveAccessToken() {
        accessToken = service.getAccessToken(requestToken, EMPTY_VERIFIER);
        requestToken = null;

        return buildDropboxClient();
    }

    // --- MobileAuthentication Impl ---

    public DropboxClient authenticate(String email, String password) {
        OAuthRequest request = new OAuthRequest(Verb.GET, MOBILE_AUTH_URL);
        request.addQuerystringParameter("email", email);
        request.addQuerystringParameter("password", password);
        service.signRequest(requestToken, request);

        //todo impl
//        accessToken = EXTRACTOR.extract(request.send().getBody());
//        System.out.println(request.send().getBody());

        return buildDropboxClient();
    }
}
