package ru.frostman.dropbox.api.model;

/**
 * @author slukjanov aka Frostman
 */
public enum ThumbnailFormat {

    JPEG, PNG;

    @Override
    public String toString() {
        return name();
    }
}
