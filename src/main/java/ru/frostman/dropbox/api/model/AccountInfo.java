package ru.frostman.dropbox.api.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author slukjanov aka Frostman
 */
public class AccountInfo {

    private long uid;

    @JsonProperty("display_name")
    private String displayName;

    private String email;

    @JsonProperty("referral_link")
    private String referralLink;

    private String country;

    @JsonProperty("quota_info")
    private QuotaInfo quotaInfo;

    public AccountInfo() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public QuotaInfo getQuotaInfo() {
        return quotaInfo;
    }

    public void setQuotaInfo(QuotaInfo quotaInfo) {
        this.quotaInfo = quotaInfo;
    }

    @Override
    public String toString() {
        return new StringBuilder("AccountInfo")
                .append("{uid=").append(uid)
                .append(", displayName='").append(displayName).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", referralLink='").append(referralLink).append('\'')
                .append(", country='").append(country).append('\'')
                .append(", quotaInfo=").append(quotaInfo)
                .append('}')
                .toString();
    }
}
