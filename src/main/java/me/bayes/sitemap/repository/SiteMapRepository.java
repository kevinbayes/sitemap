package me.bayes.sitemap.repository;

import me.bayes.sitemap.model.SiteUrl;

import java.util.List;

/**
 * Created by kevinbayes on 2014/12/05.
 */
public interface SiteMapRepository {

    void update(List<SiteUrl> urls);

}
