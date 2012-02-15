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

package ru.frostman.dropbox.api.model;

import org.scribe.model.Token;
import ru.frostman.dropbox.api.thr.DropboxBaseException;

/**
 * This class needed to parse Dropbox API response for mobile
 * authentication.
 *
 * @author slukjanov aka Frostman
 */
public class DropboxAccessToken {
    private String token;
    private String secret;
    private String error;

    public DropboxAccessToken() {
    }

    public Token toOauthToken() {
        if (error == null || error.length() == 0) {
            return new Token(token, secret);
        }

        throw new DropboxBaseException("Error while taking access token: " + error);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
