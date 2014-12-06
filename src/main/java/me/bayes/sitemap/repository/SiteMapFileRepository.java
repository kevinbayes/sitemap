package me.bayes.sitemap.repository;

import me.bayes.sitemap.exception.ExceptionCode;
import me.bayes.sitemap.exception.SystemException;
import me.bayes.sitemap.model.SiteUrl;
import me.bayes.sitemap.util.JaxbUtil;
import me.bayes.sitemap.xsd.sitemap.Urlset;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static me.bayes.sitemap.vertx.SitemapMod.Context.log;
import static me.bayes.sitemap.util.JaxbUtil.SiteMap.convert;

/**
 * Created by kevinbayes on 2014/12/05.
 */
public class SiteMapFileRepository implements SiteMapRepository {

    private Path file;

    public SiteMapFileRepository(Path file) {
        this.file = file;
        if(!file.toString().endsWith(".xml")) {
            throw new SystemException(ExceptionCode.UNKNOWN_FILE_EXTENSION);
        }
    }

    public void update(List<SiteUrl> urls) {

        Urlset urlset;

        if(!Files.exists(file)) {
            try {
                Files.createFile(file);
                urlset = JaxbUtil.SiteMap.factory().createUrlset();
            } catch (IOException e) {
                e.printStackTrace();
                throw new SystemException(ExceptionCode.UNABLE_TO_CREATE_SITEMAP, e);
            }
        } else {
            urlset = JaxbUtil.SiteMap.unmarshal(file.toFile());
        }

        urls.stream().forEach( siteUrl -> {
            urlset.getUrl().add(convert(siteUrl));
        });

        try(FileOutputStream output = new FileOutputStream(this.file.toFile())) {
            JaxbUtil.SiteMap.marshal(urlset, output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log().info(e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            log().info(e.getMessage(), e);
        }
    }
}
