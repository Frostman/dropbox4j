/*
 * Dropbox4j - Dropbox API Java implementation.
 *
 * Copyright (c) 2012 - Sergey "Frosman" Lukjanov, me@frostman.ru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.frostman.dropbox.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.Preconditions;
import ru.frostman.dropbox.api.auth.DropboxAuthentication;
import ru.frostman.dropbox.api.auth.MobileAuthentication;
import ru.frostman.dropbox.api.auth.WebAuthentication;
import ru.frostman.dropbox.api.model.DropboxAccessToken;
import ru.frostman.dropbox.api.util.Json;

import static org.scribe.utils.OAuthEncoder.encode;

/**
 * This class provides methods to create ready to use DropboxClient
 * instance by different ways.
 *
 * @author slukjanov aka Frostman
 * @see DropboxClient
 * @see DropboxApi
 */
public class DropboxClientBuilder implements DropboxAuthentication, WebAuthentication, MobileAuthentication {
    /**
     * Empty verifier for Scribe framework because of Dropbox isn't using OAuth verification
     */
    private static final Verifier EMPTY_VERIFIER = new Verifier("");

    /**
     * Callback url for web authentication
     */
    private String callback;

    /**
     * Current OAuth client
     */
    private final OAuthService service;

    /**
     * Current request token
     */
    private Token requestToken = null;

    /**
     * Current access token
     */
    private Token accessToken = null;

    /**
     * Creates instance of DropboxClientBuilder ready to authenticate user
     *
     * @param appKey    application key
     * @param appSecret application secret
     */
    private DropboxClientBuilder(String appKey, String appSecret) {
        Preconditions.checkNotNull(appKey, "App key cannot be null");
        Preconditions.checkNotNull(appSecret, "App secret cannot be null");

        service = new ServiceBuilder()
                .provider(new DropboxApi())
                .apiKey(appKey)
                .apiSecret(appSecret)
                        //todo remove debug in release
                .debug()
                .build();
    }

    /**
     * Build authenticator to application with specified appKey and appSecret.
     *
     * @param appKey    application key
     * @param appSecret application secret
     *
     * @return authenticator
     *
     * @see DropboxAuthentication
     */
    public static DropboxAuthentication build(String appKey, String appSecret) {
        DropboxClientBuilder builder = new DropboxClientBuilder(appKey, appSecret);
        builder.requestToken = builder.service.getRequestToken();

        return builder;
    }

    /**
     * Build ready to use DropboxClient with specified appKey and appSecret
     * with specified access token (without authenticator).
     *
     * @param appKey      application key
     * @param appSecret   application secret
     * @param accessToken access token
     *
     * @return ready to use dropbox client
     *
     * @see DropboxClient
     */
    public static DropboxClient build(String appKey, String appSecret, Token accessToken) {
        Preconditions.checkNotNull(accessToken, "Access token cannot be null");

        DropboxClientBuilder builder = new DropboxClientBuilder(appKey, appSecret);
        builder.accessToken = accessToken;

        return builder.buildDropboxClient();
    }

    /**
     * Build ready to use DropboxClient with specified appKey and appSecret
     * with specified access token (without authenticator).
     *
     * @param appKey    application key
     * @param appSecret application secret
     * @param token     part of access token
     * @param secret    part of access token
     *
     * @return ready to use dropbox client
     *
     * @see DropboxClient
     */
    public static DropboxClient build(String appKey, String appSecret, String token, String secret) {
        Preconditions.checkNotNull(token, "Token cannot be null");
        Preconditions.checkNotNull(secret, "Secret cannot be null");

        DropboxClientBuilder builder = new DropboxClientBuilder(appKey, appSecret);
        builder.accessToken = new Token(token, secret);

        return builder.buildDropboxClient();
    }

    /**
     * @return new instance of DropboxClient with current OAuth client and access token
     */
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
                + "&" + OAuthConstants.CALLBACK + "=" + encode(callback);
    }

    public DropboxClient receiveAccessToken() {
        accessToken = service.getAccessToken(requestToken, EMPTY_VERIFIER);
        requestToken = null;

        return buildDropboxClient();
    }

    // --- MobileAuthentication Impl ---

    public DropboxClient authenticate(String email, String password) {
        OAuthRequest request = new OAuthRequest(Verb.GET, DropboxApi.MOBILE_AUTH_URL);
        request.addQuerystringParameter("email", email);
        request.addQuerystringParameter("password", password);
        service.signRequest(requestToken, request);

        accessToken = Json.parse(request.send().getBody(), DropboxAccessToken.class).toOauthToken();

        return buildDropboxClient();
    }
}
