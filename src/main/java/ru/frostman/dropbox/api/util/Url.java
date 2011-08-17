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

package ru.frostman.dropbox.api.util;

import ru.frostman.dropbox.api.thr.DropboxBaseException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Util to work with urls.
 *
 * @author slukjanov aka Frostman
 */
public class Url {

    public static String encode(String url ){
        try {
            return URLEncoder.encode(url, "UTF-8").replace("%2F", "/").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new DropboxBaseException("UTF-8 is unsupported", e);
        }
    }

}
