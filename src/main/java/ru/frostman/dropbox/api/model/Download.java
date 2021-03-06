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

import org.scribe.model.Response;
import ru.frostman.dropbox.api.util.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the base class for downloadable entries.
 *
 * @author slukjanov aka Frostman
 */
public abstract class Download {
    protected final Response response;
    protected final String path;

    protected Download(Response response, String path) {
        this.response = response;
        this.path = path;
    }

    /**
     * @return InputStream for the resource
     */
    public InputStream getAsStream() {
        return response.getStream();
    }

    /**
     * @return resource's content as string
     */
    public String getAsString() {
        return response.getBody();
    }

    /**
     * @param file to save in
     *
     * @throws IOException iff problems with accessing file
     */
    public void saveToFile(File file) throws IOException {
        Files.writeFile(file, getAsStream());
    }

    /**
     * @return path in Dropbox of the resource
     */
    public String getPath() {
        return path;
    }
}
