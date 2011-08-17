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

import static ru.frostman.dropbox.api.util.DropboxError.*;
import static ru.frostman.dropbox.api.util.Url.encode;

/**
 * @author slukjanov aka Frostman
 */
public class DropboxClient {
    private static final String INFO_URL = "https://api.dropbox.com/0/account/info";
    private static final String METADATA_URL = "https://api.dropbox.com/0/metadata/dropbox";
    private static final String FILES_URL = "https://api-content.dropbox.com/0/files/dropbox";
    private static final String THUMBNAILS_URL = "https://api-content.dropbox.com/0/thumbnails/dropbox";
    private static final String FILE_OPS_COPY_URL = "https://api.dropbox.com/0/fileops/copy";
    private static final String FILE_OPS_MOVE_URL = "https://api.dropbox.com/0/fileops/move";
    private static final String FILE_OPS_DELETE_URL = "https://api.dropbox.com/0/fileops/delete";
    private static final String FILE_OPS_CREATE_FOLDER_URL = "https://api.dropbox.com/0/fileops/create_folder";

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
        String content = check(request.send()).getBody();

        return Json.parse(content, AccountInfo.class);
    }

    public Entry getMetadata(String path) {
        return getMetadata(path, 10000, null, true);
    }

    public Entry getMetadata(String path, int fileLimit) {
        return getMetadata(path, fileLimit, null, true);
    }

    public Entry getMetadata(String path, String hash) {
        return getMetadata(path, 10000, hash, true);
    }

    public Entry getMetadata(String path, int fileLimit, @Nullable String hash) {
        return getMetadata(path, fileLimit, hash, true);
    }

    public Entry getMetadata(String path, int fileLimit, @Nullable String hash, boolean list) {
        OAuthRequest request = new OAuthRequest(Verb.GET, METADATA_URL + encode(path));

        if (fileLimit != 10000)
            request.addQuerystringParameter("file_limit", Integer.toString(fileLimit));

        if (hash != null) {
            request.addQuerystringParameter("hash", hash);
        }

        if (!list) {
            request.addQuerystringParameter("list", "false");
        }

        service.signRequest(accessToken, request);
        String content = checkMetadata(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    public Entry copy(String from, String to) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_COPY_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("from_path", encode(from));
        request.addQuerystringParameter("to_path", encode(to));

        service.signRequest(accessToken, request);
        String content = checkCopy(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    public Entry move(String from, String to) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_MOVE_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("from_path", encode(from));
        request.addQuerystringParameter("to_path", encode(to));

        service.signRequest(accessToken, request);
        String content = checkMove(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    public void delete(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_DELETE_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("path", encode(path));

        service.signRequest(accessToken, request);
        checkDelete(request.send());
    }

    public Entry createFolder(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILE_OPS_CREATE_FOLDER_URL);
        request.addQuerystringParameter("root", "dropbox");
        request.addQuerystringParameter("path", encode(path));

        service.signRequest(accessToken, request);
        String content = checkCreateFolder(request.send()).getBody();

        return Json.parse(content, Entry.class);
    }

    public void putFile(File file, String path) throws IOException {
        OAuthRequest request = new OAuthRequest(Verb.POST, FILES_URL + encode(path));
        Multipart.attachFile(file, request);
        service.signRequest(accessToken, request);

        checkFiles(request.send());
    }

    public EntryDownload getFile(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, FILES_URL + encode(path));
        service.signRequest(accessToken, request);

        Response response = checkFiles(request.send());

        return new EntryDownload(response, path);
    }

    public ThumbnailDownload getThumbnail(String path) {
        return getThumbnail(path, ThumbnailSize.SMALL, ThumbnailFormat.JPEG);
    }

    public ThumbnailDownload getThumbnail(String path, ThumbnailSize size, ThumbnailFormat format) {
        OAuthRequest request = new OAuthRequest(Verb.GET, THUMBNAILS_URL + encode(path));

        if (size != ThumbnailSize.SMALL) {
            request.addQuerystringParameter("size", size.toString());
        }

        if (format != ThumbnailFormat.JPEG) {
            request.addQuerystringParameter("format", format.toString());
        }

        service.signRequest(accessToken, request);
        Response response = checkThumbnails(request.send());

        return new ThumbnailDownload(response, path, size, format);
    }
}
