package ru.frostman.dropbox.api.model;

import org.scribe.model.Response;
import ru.frostman.dropbox.api.util.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author slukjanov aka Frostman
 */
public abstract class Download {
    protected final Response response;
    protected final String path;

    protected Download(Response response, String path) {
        this.response = response;
        this.path = path;
    }

    public InputStream getAsStream() {
        return response.getStream();
    }

    public String getAsString() {
        return response.getBody();
    }

    public void saveToFile(File file) throws IOException {
        Files.writeFile(file, getAsStream());
    }

    public String getPath() {
        return path;
    }
}
