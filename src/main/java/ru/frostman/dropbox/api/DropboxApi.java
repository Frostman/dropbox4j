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
 * Dropbox API impl, that contains all REST URLs
 *
 * @author slukjanov aka Frostman
 */
public class DropboxApi extends DefaultApi10a {

    public static final String API_VERSION = "1";

    /**
     * URL for step 1 of authentication -  obtaining an OAuth request token
     * to be used for the rest of the authentication process
     */
    public static final String REQUEST_TOKEN_URL = "https://api.dropbox.com/" + API_VERSION
            + "/oauth/request_token";

    /**
     * URL for step 2 of authentication - Applications should direct the user
     * to /oauth/authorize. This isn't an API call per se, but rather
     * a web endpoint that lets the user log in to Dropbox and choose
     * whether to grant the application the ability to access files
     * on their behalf.
     */
    public static final String AUTHORIZE_URL = "https://www.dropbox.com/" + API_VERSION
            + "/oauth/authorize?oauth_token=";

    /**
     * URL for step 3 of authentication - acquiring an access token
     */
    public static final String ACCESS_TOKEN_URL = "https://api.dropbox.com/" + API_VERSION
            + "/oauth/access_token";

    /**
     * Url to get access token from user's credentials.
     * It is a deprecated way to auth, can be removed from API.
     */
    public static final String MOBILE_AUTH_URL = "https://api.dropbox.com/" + API_VERSION
            + "/token";

    /**
     * Url to get account info
     */
    public static final String INFO_URL = "https://api.dropbox.com/1/account/info";

    /**
     * Url to get metadata information
     */
    public static final String METADATA_URL = "https://api.dropbox.com/1/metadata/dropbox";

    /**
     * Url to download and upload files
     */
    public static final String FILES_URL = "https://api-content.dropbox.com/1/files/dropbox";

    /**
     * Url to download thumbnails
     */
    public static final String THUMBNAILS_URL = "https://api-content.dropbox.com/1/thumbnails/dropbox";

    /**
     * Url to copy files
     */
    public static final String FILE_OPS_COPY_URL = "https://api.dropbox.com/1/fileops/copy";

    /**
     * Url to move files
     */
    public static final String FILE_OPS_MOVE_URL = "https://api.dropbox.com/1/fileops/move";

    /**
     * Url to delete files
     */
    public static final String FILE_OPS_DELETE_URL = "https://api.dropbox.com/1/fileops/delete";

    /**
     * Url to create folders
     */
    public static final String FILE_OPS_CREATE_FOLDER_URL = "https://api.dropbox.com/1/fileops/create_folder";


    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_URL;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return AUTHORIZE_URL + requestToken.getToken();
    }

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_URL;
    }

}
