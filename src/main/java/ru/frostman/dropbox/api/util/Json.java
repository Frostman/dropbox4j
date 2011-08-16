package ru.frostman.dropbox.api.util;

import org.codehaus.jackson.map.ObjectMapper;
import ru.frostman.dropbox.api.thr.DropboxJsonException;

import java.io.IOException;

/**
 * @author slukjanov aka Frostman
 */
public class Json {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String content, Class<T> type) {
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            throw new DropboxJsonException("Exception while parsing json: " + content, e);
        }
    }
}
