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

import org.junit.Test;

import java.util.Date;

/**
 * @author slukjanov aka Frostman
 */
public class DatesTest {

    @Test
    public void simpleTest() {
        String sourceString = "Sat, 21 Aug 2010 22:31:20 +0000";
        Date date = Dates.parse(sourceString);
        System.out.println(date);

        Date t = new Date(110, 8, 21, 22, 31, 20);
        System.out.println(Dates.format(t));
    }

}
