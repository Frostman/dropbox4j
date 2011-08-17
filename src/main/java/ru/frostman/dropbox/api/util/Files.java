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

import java.io.*;

/**
 * Helper class to work with files.
 *
 * @author slukjanov aka Frostman
 */
public class Files {
    private static final int BUFFER_SIZE = 4 * 1024;

    public static FileInputStream createInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' exists but cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }

        return new FileInputStream(file);
    }

    public static FileOutputStream createOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' exists but cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }

        return new FileOutputStream(file);
    }

    public static byte[] readFile(File file) throws IOException {
        InputStream in = null;
        try {
            in = createInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            copy(in, out);

            return out.toByteArray();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // no operations
                }
            }
        }
    }

    public static void writeFile(File file, InputStream in) throws IOException {
        OutputStream out = null;
        try {
            out = createOutputStream(file);

            copy(in, out);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // no operations
                }
            }
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
