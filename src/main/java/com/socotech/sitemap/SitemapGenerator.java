package com.socotech.sitemap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.List;

/**
 * User: marc Date: Mar 2, 2008 Time: 7:10:13 PM
 * <p/>
 * THIS SOFTWARE IS COPYRIGHTED.  THE SOFTWARE MAY NOT BE COPIED REPRODUCED, TRANSLATED, OR REDUCED TO ANY ELECTRONIC MEDIUM OR MACHINE READABLE FORM WITHOUT THE PRIOR WRITTEN CONSENT OF SOCO
 * TECHNOLOGIES.
 */
public abstract class SitemapGenerator {

    private VelocityEngine ve;

    protected SitemapGenerator() {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    /**
     * Run it!
     *
     * @throws java.io.IOException if file writer cannot be created
     */
    public void export(Writer writer) throws IOException {
        SitemapPager pager = new SitemapPager();
        do {
            int startIndex = pager.getStartIndex();
            int maxResults = pager.getMaxResults();
            int exported = this.export(writer, startIndex, maxResults);
            pager.fetchSize(exported).advance();
        } while (pager.hasMore());
    }

    /**
     * Run it!
     *
     * @throws java.io.IOException if file writer cannot be created
     */
    public int export(Writer writer, int startIndex, int maxResults) throws IOException {
        Template template = ve.getTemplate("google_sitemap_urls.vm");
        VelocityContext context = new VelocityContext();
        List<SitemapEntry> entries = this.getEntries(startIndex, maxResults);
        context.put("entries", entries);
        try {
            template.merge(context, writer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return entries.size();
    }

    protected abstract List<SitemapEntry> getEntries(int startIndex, int maxResults) throws MalformedURLException;

    /**
     * logger
     */
    private static final Logger log = LoggerFactory.getLogger(SitemapGenerator.class);
}
