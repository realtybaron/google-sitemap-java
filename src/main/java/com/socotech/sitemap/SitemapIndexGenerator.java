package com.socotech.sitemap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: marc Date: Mar 2, 2008 Time: 7:10:13 PM
 * <p/>
 * THIS SOFTWARE IS COPYRIGHTED.  THE SOFTWARE MAY NOT BE COPIED REPRODUCED, TRANSLATED, OR REDUCED TO ANY ELECTRONIC MEDIUM OR MACHINE READABLE FORM WITHOUT THE PRIOR WRITTEN CONSENT OF SOCO
 * TECHNOLOGIES.
 */
public class SitemapIndexGenerator {

    private final List<String> urls;
    private final VelocityEngine velocityEngine;

    public SitemapIndexGenerator() {
        this.urls = new ArrayList<>();
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        this.velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        this.velocityEngine.init();
    }

    public void addUrl(String url) {
        this.urls.add(url);
    }

    public void addUrls(Collection<String> urls) {
        this.urls.addAll(urls);
    }

    /**
     * Run it!
     *
     * @throws IOException if file writer cannot be created
     */
    public int export(Writer writer) throws IOException {
        Template template = velocityEngine.getTemplate("google_sitemap_index.vm");
        VelocityContext context = new VelocityContext();
        context.put("entries", this.urls);
        try {
            template.merge(context, writer);
        } finally {
            writer.flush();
        }
        return this.urls.size();
    }

}
