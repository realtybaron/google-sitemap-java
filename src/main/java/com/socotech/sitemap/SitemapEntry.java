package com.socotech.sitemap;

import com.google.common.base.Preconditions;

/**
 * User: marc Date: Jan 13, 2009 Time: 8:09:08 AM
 */
public class SitemapEntry {
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

    public ChangeFrequency getChangeFrequency() {
        return changeFrequency;
    }

    public void setChangeFrequency(ChangeFrequency frequency) {
        this.changeFrequency = frequency;
    }

    public static SitemapEntry newSitemapEntry(String url) {
        return newSitemapEntry(url, 1.0f, ChangeFrequency.never);
    }

    public static SitemapEntry newSitemapEntry(String url, ChangeFrequency frequency) {
        return newSitemapEntry(url, 1.0f, frequency);
    }

    public static SitemapEntry newSitemapEntry(String url, float priority, ChangeFrequency frequency) {
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
