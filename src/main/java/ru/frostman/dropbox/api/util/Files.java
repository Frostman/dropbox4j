package ru.frostman.dropbox.api.util;

import java.io.*;

/**
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

    public static byte[] readFile(File file) throws IOException {
        InputStream in = null;
        try {
            in = createInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

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

}
