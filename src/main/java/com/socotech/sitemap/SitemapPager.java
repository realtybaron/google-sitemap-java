package com.socotech.sitemap;

import com.google.common.base.MoreObjects;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: marc
 * Date: 4/3/12
 * Time: 7:01 AM
 */
public class SitemapPager {
    private int startIndex, maxResults, fetchSize, pageNumber;

    /**
     * Default constructor
     */
    public SitemapPager() {
        this(PAGE_SIZE_DEFAULT);
    }

    /**
     * Alternative constructor
     *
     * @param pageSize page size
     */
    public SitemapPager(int pageSize) {
        this.maxResults = pageSize;
        this.pageNumber = 1;
    }

    public void reset() {
        this.fetchSize = 0;
        this.pageNumber = 1;
        this.startIndex = 0;
    }

    public SitemapPager advance() {
        this.pageNumber++;
        this.startIndex += this.maxResults;
        return this;
    }

    public SitemapPager fetchSize(int size) {
        this.fetchSize = size;
        return this;
    }

    public SitemapPager fetchSize(Collection collection) {
        this.fetchSize(collection.size());
        return this;
    }

    public boolean hasMore() {
        return this.fetchSize == this.maxResults;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public SitemapPager startIndex(int i) {
        this.startIndex = i;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("firstResults", startIndex).add("maxResults", maxResults).add("fetchSize", fetchSize).add("pageNumber", pageNumber).toString();
    }

    /**
     * Default page size
     */
    public static final int PAGE_SIZE_DEFAULT = 1000;
}
