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
        String boundary = generateBoundaryString(10);
        String contentType = "multipart/form-data; boundary=" + boundary;
        request.addHeader("Content-Type", contentType);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(("--" + boundary + "\r\n").getBytes(CHARSET_NAME));
        out.write(("Content-Disposition: form-data; name=\"file\"\r\n").getBytes(CHARSET_NAME));
//        out.write(("Content-Type: content/unknown\r\n\r\n").getBytes(CHARSET_NAME));
        out.write(Files.readFile(file));
        out.write(("\r\n--" + boundary + "\r\n").getBytes(CHARSET_NAME));

        request.addPayload(out.toByteArray());

        return request;
    }

    private static String generateBoundaryString(int length) {
        //todo impl
        return "Asrf456BGe4h";
    }

}
