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

import ru.frostman.dropbox.api.thr.DropboxDateFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author slukjanov aka Frostman
 */
public class Dates {
    // smth like Sat, 21 Aug 2010 22:31:20 +0000
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public synchronized static Date parse(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (Exception e) {
            throw new DropboxDateFormatException("Exception while parse date", e);
        }
    }

    public synchronized static String format(Date date) {
        try {
            return DATE_FORMAT.format(date);
        } catch (Exception e) {
            throw new DropboxDateFormatException("Exception while formatting date", e);
        }
    }
}
