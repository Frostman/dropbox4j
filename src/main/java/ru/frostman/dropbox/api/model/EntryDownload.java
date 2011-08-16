package ru.frostman.dropbox.api.model;

import org.scribe.model.Response;

/**
 * @author slukjanov aka Frostman
 */
public class EntryDownload  extends Download{

    public EntryDownload(Response response, String path) {
        super(response, path);
    }

}
