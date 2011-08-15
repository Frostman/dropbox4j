package ru.frostman.dropbox.api.auth;

/**
 * @author slukjanov aka Frostman
 */
public interface DropboxAuthentication {

    WebAuthentication web();

    WebAuthentication web(String callback);

    MobileAuthentication mobile();

}
