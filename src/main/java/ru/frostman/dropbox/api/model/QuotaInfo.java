package ru.frostman.dropbox.api.model;

/**
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
