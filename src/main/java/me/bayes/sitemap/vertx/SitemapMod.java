package me.bayes.sitemap.vertx;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

/**
 * Created by kevinbayes on 2014/12/01.
 */
public class SitemapMod extends BusModBase {

    public void start() {
        super.start();

        Context.container = container;

        String prefix = config != null ? config.getString("prefix", "") : "";

        eb.registerHandler(prefix + "sitemap.append", new AppendHandler());

    }

    public static class Context {

        public static Container container;

        public static final JsonObject config() {
            return container.config();
        }

        public static final Logger log() {
            return container.logger();
        }

    }
}

