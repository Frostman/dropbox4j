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

import ru.frostman.dropbox.api.model.ThumbnailFormat;
import ru.frostman.dropbox.api.model.ThumbnailSize;

/**
 * Class with public constants of some Dropbox API defaults.
 *
 * @author slukjanov aka Frostman
 */
public class DropboxDefaults {

    /**
     * Default file limit (for metadata requests)
     */
    public static final int FILE_LIMIT = 10000;

    /**
     * Default downloading child entries list (for metadata request)
     */
    public static final boolean LIST = true;

    /**
     * Default thumbnail size
     */
    public static final ThumbnailSize THUMBNAIL_SIZE = ThumbnailSize.SMALL;

    /**
     * Default thumbnail format
     */
    public static final ThumbnailFormat THUMBNAIL_FORMAT = ThumbnailFormat.JPEG;
}
