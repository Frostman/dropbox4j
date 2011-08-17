/*
 * Dropbox API Java implementation.
 *
 * Copyright (c) 2011 - Sergey "Frosman" Lukjanov, me@frostman.ru
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

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import ru.frostman.dropbox.api.model.*;
import ru.frostman.dropbox.api.util.Json;
import ru.frostman.dropbox.api.util.Multipart;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

import static ru.frostman.dropbox.api.DropboxDefaults.FILE_LIMIT;
import static ru.frostman.dropbox.api.DropboxDefaults.THUMBNAIL_FORMAT;
import static ru.frostman.dropbox.api.DropboxDefaults.THUMBNAIL_SIZE;
import static ru.frostman.dropbox.api.util.DropboxError.*;
import static ru.frostman.dropbox.api.util.Url.encode;

/**
 * This class provides methods to access Dropbox files, thumbnails and others.
 * It returns instances of POJOs, so changes in them will not be accepted.
 *
 * To get an instance of DropboxClient you should use DropboxClientBuilder.
 *
 * @author slukjanov aka Frostman
 * @see DropboxClientBuilder
 */
public class DropboxClient {
    /**
     * Url to get account info
     */
    private static final String INFO_URL = "https://api.dropbox.com/0/account/info";

    /**
     * Url to get metadata information
     */
    private static final String METADATA_URL = "https://api.dropbox.com/0/metadata/dropbox";

    /**
     * Url to download and upload files
     */
    private static final String FILES_URL = "https://api-content.dropbox.com/0/files/dropbox";

    /**
     * Url to download thumbnails
     */
    private static final String THUMBNAILS_URL = "https://api-content.dropbox.com/0/thumbnails/dropbox";

    /**
     * Url to copy files
     */
    private static final String FILE_OPS_COPY_URL = "https://api.dropbox.com/0/fileops/copy";

    /**
     * Url to move files
     */
    private static final String FILE_OPS_MOVE_URL = "https://api.dropbox.com/0/fileops/move";

    /**
     * Url to delete files
     */
    private static final String FILE_OPS_DELETE_URL = "https://api.dropbox.com/0/fileops/delete";

    /**
     * Url to create folders
     */
    private static final String FILE_OPS_CREATE_FOLDER_URL = "https://api.dropbox.com/0/fileops/create_folder";

    /**
     * Current OAuth client instance
     */
    private final OAuthService service;

    /**
     * Current access token
     */
    private final Token accessToken;

    /**
     * Creates instance of ready to use DropboxClient.
     *
     * @param service     prepared OAuth client
     * @param accessToken of user
     */
    DropboxClient(OAuthService service, Token accessToken) {
        this.service = service;
        this.accessToken = accessToken;
    }

    /**
     * @return current access token
     */
    public Token getAccessToken() {
        return accessToken;
    }

    /**
     * Returns information about current user's account.
     *
     * @return info about user's account
     *
     * @see AccountInfo
     */
    public AccountInfo getAccountInfo() {
        OAuthRequest request = new OAuthRequest(Verb.GET, INFO_URL);
        service.signRequest(accessToken, request);
        String content = check(request.send()).getBody();

        return Json.parse(content, AccountInfo.class);
    }

    /**
     * Returns metadata information about specified resource.
     *
     * @param path to file or directory
     *
     * @return metadata of specified resource
     *
     * @see Entry
     */
    public Entry getMetadata(String path) {
        return getMetadata(path, FILE_LIMIT, null, true);
    }

    /**
     * Returns metadata information about specified resource with specified
     * maximum child entries count.
     *
     * @param path      to file or directory
     * @param fileLimit max child elements count
     *
     * @return metadata of specified resource
     *
     * @see Entry
     */
    public Entry getMetadata(String path, int fileLimit) {
        return getMetadata(path, fileLimit, null, true);
    }

    /**
     * Returns metadata information about specified resource with
     * checking for its hash. If nothing changes (hash isn't changed)
     * then 304 will returns.
     *
     * @param path to file or directory
     * @param hash to check smth changed
     *
     * @return metadata of specified resource
     *
     * @see Entry
     */
    public Entry getMetadata(String path, String hash) {
        return getMetadata(path, FILE_LIMIT, hash, true);
    }

    /**
     * Returns metadata information about specified resource with
     * checking for its hash with specified max child entries count.
     * If nothing changes (hash isn't changed) then 304 will returns.
     *
     * @param path      to file or directory
     * @param fileLimit max child entries count
     * @param hash      to check smth changed
     *
     * @return metadata of specified resource
     *
     * @see Entry
     */
    public Entry getMetadata(String path, int fileLimit, @Nullable String hash) {
        return getMetadata(path, fileLimit, hash, true);
    }

    /**
     * Returns metadata information about specified resource with
     * checking for its hash with specified max child entries count.
     * If nothing changes (hash isn't changed) then 304 will returns.
     *
     * @param path      to file or directory
     * @param fileLimit max child entries count
     * @param hash      to check smth changed
     * @param list      true to include child entries and false to not
     *
     * @return metadata of specified resource
     *
     * @see Entry
     * @see DropboxDefaults
     */
    public Entry getMetadata(String path, int fileLimit, @Nullable String hash, boolean list) {
        OAuthRequest request = new OAuthRequest(Verb.GET, METADATA_URL + encode(path));

        if (fileLimit != FILE_LIMIT)
            request.addQuerystringParameter("file_limit", Integer.toString(fileLimit));

        if (hash != null) {
            request.addQuerystringParameter("hash", hash);
        }

        if (list != DropboxDefaults.LIST) {
            request.addQuerystringParameter("list", Boolean.toString(list));
        }

        service.signRequest(accessToken, request);
        String content = checkMetadata(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    /**
     * Copy file from specified source to specified target.
     *
     * @param from source
     * @param to   target
     *
     * @return metadata of target file
     *
     * @see Entry
     */
    public Entry copy(String from, String to) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_COPY_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("from_path", encode(from));
        request.addQuerystringParameter("to_path", encode(to));

        service.signRequest(accessToken, request);
        String content = checkCopy(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }


    /**
     * Move file from specified source to specified target.
     *
     * @param from source
     * @param to   target
     *
     * @return metadata of target file
     *
     * @see Entry
     */
    public Entry move(String from, String to) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_MOVE_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("from_path", encode(from));
        request.addQuerystringParameter("to_path", encode(to));

        service.signRequest(accessToken, request);
        String content = checkMove(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    /**
     * Delete the specified file.
     *
     * @param path to delete
     */
    public void delete(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_DELETE_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("path", encode(path));

        service.signRequest(accessToken, request);
        checkDelete(request.send());
    }

    /**
     * Create folder with specified path.
     *
     * @param path to create
     *
     * @return metadata of created folder
     *
     * @see Entry
     */
    public Entry createFolder(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_CREATE_FOLDER_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("path", encode(path));

        service.signRequest(accessToken, request);
        String content = checkCreateFolder(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    /**
     * Upload specified file to specified folder.
     *
     * @param file to upload
     * @param path of target folder
     *
     * @throws IOException iff exception while accessing file
     */
    public void putFile(File file, String path) throws IOException {
        OAuthRequest request = new OAuthRequest(Verb.POST, FILES_URL + encode(path));
        Multipart.attachFile(file, request);
        service.signRequest(accessToken, request);

        checkFiles(request.send());
    }

    /**
     * Download file with specified path.
     *
     * @param path to download
     *
     * @return special objects that provides different ways to access file
     */
    public EntryDownload getFile(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILES_URL + encode(path));
        service.signRequest(accessToken, request);

        Response response = checkFiles(request.send());

        return new EntryDownload(response, path);
    }

    /**
     * Download thumbnail of the file with specified path (if exists).
     *
     * @param path to file
     *
     * @return special objects that provides different ways to access thumbnail
     */
    public ThumbnailDownload getThumbnail(String path) {
        return getThumbnail(path, THUMBNAIL_SIZE, THUMBNAIL_FORMAT);
    }

    /**
     * Download thumbnail of the file with specified path (if exists).
     *
     * @param path   to file
     * @param size   of thumbnail
     * @param format of thumbnail
     *
     * @return special objects that provides different ways to access thumbnail
     */
    public ThumbnailDownload getThumbnail(String path, ThumbnailSize size, ThumbnailFormat format) {
        OAuthRequest request = new OAuthRequest(Verb.GET, THUMBNAILS_URL + encode(path));

        if (size != THUMBNAIL_SIZE) {
            request.addQuerystringParameter("size", size.toString());
        }

        if (format != THUMBNAIL_FORMAT) {
            request.addQuerystringParameter("format", format.toString());
        }

        service.signRequest(accessToken, request);
        Response response = checkThumbnails(request.send());

        return new ThumbnailDownload(response, path, size, format);
    }
}
