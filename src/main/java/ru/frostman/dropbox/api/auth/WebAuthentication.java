package ru.frostman.dropbox.api.auth;

import ru.frostman.dropbox.api.DropboxClient;

/**
 * @author slukjanov aka Frostman
 */
public interface WebAuthentication {

    String getAuthorizationUrl();

    DropboxClient receiveAccessToken();
}
