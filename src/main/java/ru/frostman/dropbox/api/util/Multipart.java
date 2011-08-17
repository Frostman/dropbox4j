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

import org.scribe.model.OAuthRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author slukjanov aka Frostman
 */
public class Multipart {

    private static final String CHARSET_NAME = "UTF-8";

    public static OAuthRequest attachFile(File file, OAuthRequest request) throws IOException {
        String boundary = generateBoundaryString();
        request.addHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
        request.addBodyParameter("file", file.getName());

        StringBuilder boundaryMessage = new StringBuilder("--").append(boundary)
                .append("\r\n")
                .append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getName())
                .append("\"\r\n")
                .append("Content-Type: ").append("application/x-unknown")
                .append("\r\n\r\n");

        String endBoundary = "\r\n--" + boundary + "--\r\n";

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(boundaryMessage.toString().getBytes(CHARSET_NAME));
        buffer.write(Files.readFile(file));
        buffer.write(endBoundary.getBytes(CHARSET_NAME));
        request.addPayload(buffer.toByteArray());
        buffer.close();

        return request;
    }

    private static String generateBoundaryString() {
        //todo impl
       return Long.toHexString(System.nanoTime());
    }

}
