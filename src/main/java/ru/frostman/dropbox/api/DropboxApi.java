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

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxApi extends DefaultApi10a {

    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.dropbox.com/1/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return "https://www.dropbox.com/1/oauth/authorize?oauth_token=" + requestToken.getToken();
    }

    @Override
    public String getRequestTokenEndpoint() {
        return "https://api.dropbox.com/1/oauth/request_token";
    }

}
