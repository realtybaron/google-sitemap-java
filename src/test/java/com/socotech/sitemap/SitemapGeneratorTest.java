package com.socotech.sitemap;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marc
 * Date: 3/12/16
 * Time: 12:02 PM
 */
public class SitemapGeneratorTest {
    @Test
    public void testExport() throws Exception {
        SitemapGenerator generator = new SitemapGenerator() {
            @Override
            protected List<SitemapEntry> getEntries(int startIndex, int maxResults) throws MalformedURLException {
                List<SitemapEntry> entries = Lists.newArrayList();
                for (int i = 0; i < 5; i++) {
                    entries.add(SitemapEntry.newSitemapEntry("http://www.foo.com/" + i));
                }
                return entries;
            }
        };
        StringWriter writer = new StringWriter();
        generator.export(writer);
        Assert.assertFalse("Export was empty", writer.toString().isEmpty());
    }
}
