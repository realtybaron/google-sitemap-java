package com.socotech.sitemap;

import com.google.common.io.Closeables;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public SitemapGenerator() {
        Velocity.init();
    }

    /**
     * Run it!
     *
     * @throws java.io.IOException if file writer cannot be created
     */
    public void export(Writer writer) throws Exception {
        try {
            SitemapPager pager = new SitemapPager();
            Template template = Velocity.getTemplate("google_sitemap_urls.vm");
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
            log.info("Sitemap generator completed successfully");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            writer.flush();
            Closeables.close(writer, true);
        }
    }

    protected abstract List<SitemapEntry> getEntries(SitemapPager pager) throws MalformedURLException;

    /**
     * logger
     */
    private static Logger log = LoggerFactory.getLogger(SitemapGenerator.class);
}
