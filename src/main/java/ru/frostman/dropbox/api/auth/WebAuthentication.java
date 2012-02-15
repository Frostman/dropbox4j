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

package ru.frostman.dropbox.api.auth;

import ru.frostman.dropbox.api.DropboxClient;

/**
 * This class provides authentication by authorization url.
 *
 * @author slukjanov aka Frostman
 */
public interface WebAuthentication {

    /**
     * @return authorization url that user should open and accept app access
     */
    String getAuthorizationUrl();

    /**
     * Receive access token and prepare DropboxClient.
     *
     * @return ready to use DropboxClient
     *
     * @see DropboxClient
     */
    DropboxClient receiveAccessToken();
}
