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

/**
 * This class provides read only information about quota.
 *
 * @author slukjanov aka Frostman
 */
public class QuotaInfo {

    private long shared;

    private long quota;

    private long normal;

    public QuotaInfo() {
    }

    public long getShared() {
        return shared;
    }

    public void setShared(long shared) {
        this.shared = shared;
    }

    public long getQuota() {
        return quota;
    }

    public void setQuota(long quota) {
        this.quota = quota;
    }

    public long getNormal() {
        return normal;
    }

    public void setNormal(long normal) {
        this.normal = normal;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("QuotaInfo")
                .append("{shared=").append(shared)
                .append(", quota=").append(quota)
                .append(", normal=").append(normal)
                .append('}')
                .toString();
    }
}
