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

package ru.frostman.dropbox.api.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * This class provides read only access to information
 * about Dropbox entry (such as file or directory).
 *
 * @author slukjanov aka Frostman
 */
public class Entry {
    private String path;

    private String root;

    @JsonProperty("is_dir")
    private boolean isDir;

    private long bytes;

    private String hash;

    private long revision;

    private String modified;

    private String size;

    @JsonProperty("mime_type")
    private String mimeType;

    private String icon;

    @JsonProperty("thumb_exists")
    private boolean thumbExists;

    private List<Entry> contents;

    public Entry() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public long getRevision() {
        return revision;
    }

    public void setRevision(long revision) {
        this.revision = revision;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isThumbExists() {
        return thumbExists;
    }

    public void setThumbExists(boolean thumbExists) {
        this.thumbExists = thumbExists;
    }

    public List<Entry> getContents() {
        return contents;
    }

    public void setContents(List<Entry> contents) {
        this.contents = contents;
    }
}
