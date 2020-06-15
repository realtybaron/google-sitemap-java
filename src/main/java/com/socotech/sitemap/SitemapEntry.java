package com.socotech.sitemap;

import com.google.common.base.Preconditions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: marc Date: Jan 13, 2009 Time: 8:09:08 AM
 */
public class SitemapEntry {

    private Date lastModified;
    private String url;
    private float priority;
    private ChangeFrequency changeFrequency;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float f) {
        this.priority = f;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public ChangeFrequency getChangeFrequency() {
        return changeFrequency;
    }

    public void setChangeFrequency(ChangeFrequency frequency) {
        this.changeFrequency = frequency;
    }

    public String getLastModifiedAsString() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(lastModified);
    }

    static public class Builder {

        private SitemapEntry entry;

        public Builder() {
            this.entry = new SitemapEntry();
            this.entry.setPriority(0.5f);
        }

        public Builder url(String url) {
            Preconditions.checkNotNull(url, "URL is null");
            this.entry.setUrl(url);
            return this;
        }

        public Builder priority(float priority) {
            Preconditions.checkState(priority >= 0.0f && priority <= 1.0f);
            this.entry.setPriority(priority);
            return this;
        }

        public Builder lastModified(Date date) {
            this.entry.setLastModified(date);
            return this;
        }

        public Builder changeFrequency(ChangeFrequency frequency) {
            Preconditions.checkNotNull(frequency, "ChangeFrequency cannot be null");
            this.entry.setChangeFrequency(frequency);
            return this;
        }

        public SitemapEntry build() {
            return entry;
        }
    }

    public static SitemapEntry of(String url) {
        return of(url, 1.0f, ChangeFrequency.never);
    }

    public static SitemapEntry of(String url, ChangeFrequency frequency) {
        return of(url, 1.0f, frequency);
    }

    public static SitemapEntry of(String url, float priority, ChangeFrequency frequency) {
        Preconditions.checkNotNull(url, "URL is null");
        Preconditions.checkNotNull(frequency, "ChangeFrequency is null");
        Preconditions.checkState(priority >= 0.0f && priority <= 1.0f);
        SitemapEntry impl = new SitemapEntry();
        impl.setUrl(url);
        impl.setPriority(priority);
        impl.setChangeFrequency(frequency);
        return impl;
    }

}
