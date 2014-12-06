package me.bayes.sitemap.util;

import me.bayes.sitemap.exception.ExceptionCode;
import me.bayes.sitemap.exception.SystemException;
import me.bayes.sitemap.model.SiteUrl;
import me.bayes.sitemap.repository.SiteMapRepository;
import me.bayes.sitemap.xsd.sitemap.TChangeFreq;
import me.bayes.sitemap.xsd.sitemap.TUrl;
import me.bayes.sitemap.xsd.sitemap.Urlset;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static me.bayes.sitemap.util.JaxbUtil.SiteMap.factory;
import static me.bayes.sitemap.vertx.SitemapMod.Context.log;

/**
 * Created by kevinbayes on 2014/12/01.
 */
public class UrlSetBuilder {

    public static UrlSetBuilder instance(SiteMapRepository repository) {
        return new UrlSetBuilder(repository);
    }

    private List<SiteUrl> urls = new ArrayList<>();
    private SiteMapRepository repository;

    private UrlSetBuilder(SiteMapRepository repository) {
        this.repository = repository;
    }

    public UrlSetBuilder with(SiteUrl siteUrl) {
        urls.add(siteUrl);
        return this;
    }

    public void write() {
        repository.update(urls);
    }
}