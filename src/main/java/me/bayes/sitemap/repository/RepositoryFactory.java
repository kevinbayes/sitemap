package me.bayes.sitemap.repository;

import me.bayes.sitemap.exception.ExceptionCode;
import me.bayes.sitemap.exception.SystemException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static me.bayes.sitemap.repository.Type.*;
import static me.bayes.sitemap.vertx.SitemapMod.Context.*;

/**
 * Created by kevinbayes on 2014/12/05.
 */
public class RepositoryFactory {

    public static final Map<Type, SiteMapRepository> instances = new HashMap<>(1);

    public static SiteMapRepository instance(Type type) {

        if(instances.containsKey(type)) {
            return instance(type);
        }

        switch (type) {
            case FILE:
                return fileInstance();
            default:
                throw new SystemException(ExceptionCode.GENERAL, "Unknown repository type");
        }
    }

    private static SiteMapRepository fileInstance() {
        String file = config().getString("file", "sitemap.xml");
        SiteMapRepository repository = new SiteMapFileRepository(Paths.get(file));
        instances.put(FILE, repository);
        return repository;
    }

}
