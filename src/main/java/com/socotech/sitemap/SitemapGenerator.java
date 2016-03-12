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

    public SitemapGenerator() {
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
        Template template = ve.getTemplate("google_sitemap_urls.vm");
        SitemapPager pager = new SitemapPager();
        VelocityContext context = new VelocityContext();
        do {
            List<SitemapEntry> entries = this.getEntries(pager);
            pager.fetchSize(entries).advance();
            context.put("entries", entries);
            try {
                template.merge(context, writer);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (pager.hasMore());
    }

    protected abstract List<SitemapEntry> getEntries(SitemapPager pager) throws MalformedURLException;

    /**
     * logger
     */
    private static Logger log = LoggerFactory.getLogger(SitemapGenerator.class);
}
