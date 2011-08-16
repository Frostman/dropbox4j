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
