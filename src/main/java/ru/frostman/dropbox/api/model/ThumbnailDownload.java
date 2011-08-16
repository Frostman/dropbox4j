package ru.frostman.dropbox.api.model;

import org.scribe.model.Response;

/**
 * @author slukjanov aka Frostman
 */
public class ThumbnailDownload extends Download {
    private final ThumbnailSize size;
    private final ThumbnailFormat format;

    public ThumbnailDownload(Response response, String path, ThumbnailSize size, ThumbnailFormat format) {
        super(response, path);

        this.size = size;
        this.format = format;
    }

    public void getAsImage() {
        //todo impl
    }

    public ThumbnailSize getSize() {
        return size;
    }

    public ThumbnailFormat getFormat() {
        return format;
    }
}
