package ru.frostman.dropbox.api.model;

import org.scribe.model.Response;
import ru.frostman.dropbox.api.util.Files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author slukjanov aka Frostman
 */
public class ThumbnailDownload {
    private final Response response;
    private final String path;
    private final ThumbnailSize size;
    private final ThumbnailFormat format;

    public ThumbnailDownload(Response response, String path, ThumbnailSize size, ThumbnailFormat format) {
        this.response = response;
        this.path = path;
        this.size = size;
        this.format = format;
    }

    public InputStream getAsStream() {
        return response.getStream();
    }

    public String getAsString() {
        return response.getBody();
    }

    public void getAsImage() {
        //todo impl
    }

    public void saveToFile(File file) throws IOException {
        Files.writeFile(file, getAsStream());
    }

    public String getPath() {
        return path;
    }

    public ThumbnailSize getSize() {
        return size;
    }

    public ThumbnailFormat getFormat() {
        return format;
    }
}
