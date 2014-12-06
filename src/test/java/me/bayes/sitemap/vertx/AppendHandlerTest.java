package me.bayes.sitemap.vertx;

import me.bayes.sitemap.model.SiteUrl;
import me.bayes.sitemap.util.JaxbUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.json.impl.Json;
import org.vertx.testtools.TestVerticle;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.*;
import static org.vertx.testtools.VertxAssert.*;

public class AppendHandlerTest extends TestVerticle {

    @Test
    public void testAppendToFile() {
        container.logger().info("Testing Append File");

        SiteUrl url = new SiteUrl();
        url.setChangeFrequency("never");
        url.setLastModified(new Date());
        url.setPriority(new BigDecimal(0.3));
        url.setUri("http://www.google.co.za");

        vertx.eventBus().sendWithTimeout("sitemap.append",
                new JsonObject().putArray("uris", new JsonArray().add(new JsonObject(Json.encode(url)))),
                10000L,
                (AsyncResult<Message<JsonObject>> reply) -> {
                    container.logger().info("Failed = " + reply.failed());
                    testComplete();
                });
    }

    //@AfterClass
    public static void cleanup() {
        try {
            Files.delete(Paths.get("target/testsite.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        // Make sure we call initialize() - this sets up the assert stuff so assert functionality works correctly
        initialize();
        // Deploy the module - the System property `vertx.modulename` will contain the name of the module so you
        // don't have to hardecode it in your tests
        System.out.println(SitemapMod.class.getCanonicalName());
        container.deployVerticle(SitemapMod.class.getCanonicalName(), new JsonObject().putString("file", "target/testsite.xml"), (AsyncResult<String> result) -> {
                    Assert.assertTrue(result.succeeded());
                    Assert.assertNotNull("deploymentID should not be null", result.result());
                    // If deployed correctly then start the tests!
                    startTests();
                }
        );
    }
}