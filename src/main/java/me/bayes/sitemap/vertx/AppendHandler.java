package me.bayes.sitemap.vertx;

import me.bayes.sitemap.model.SiteUrl;
import me.bayes.sitemap.repository.RepositoryFactory;
import me.bayes.sitemap.repository.SiteMapRepository;
import me.bayes.sitemap.repository.Type;
import me.bayes.sitemap.util.UrlSetBuilder;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.json.impl.Json;

import static me.bayes.sitemap.vertx.SitemapMod.Context.log;

/**
 * Created by kevinbayes on 2014/12/02.
 */
public class AppendHandler extends AbstractHandler {

    public void handleInternal(Message<JsonObject> message) {

        JsonObject config = SitemapMod.Context.config();

        Type repositoryType = Type.valueOf(config.getString("storage_type", "FILE"));
        log().info("Using storage type of " + repositoryType + ".");

        JsonArray uris = message.body().getArray("uris", new JsonArray());

        SiteMapRepository repository = RepositoryFactory.instance(repositoryType);

        UrlSetBuilder builder = UrlSetBuilder.instance(repository);

        uris.forEach(object -> {
            SiteUrl entry = Json.decodeValue(object.toString(), SiteUrl.class);
            builder.with(entry);
        });

        builder.write();

        message.reply(new JsonObject());
    }

}
