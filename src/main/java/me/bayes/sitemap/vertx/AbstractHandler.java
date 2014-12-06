package me.bayes.sitemap.vertx;

import me.bayes.sitemap.exception.ExceptionCode;
import me.bayes.sitemap.exception.SystemException;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

import static me.bayes.sitemap.vertx.SitemapMod.Context.log;

/**
 * <p>
 * Simple abstract handler to catch all exceptions and return them as
 * error replies on the event bus.
 * </p>
 *
 * @author Kevin Bayes
 * @since 1.0.0
 */
public abstract class AbstractHandler implements Handler<Message<JsonObject>> {

    /**
     * <p>
     * Concrete implementation to handle failures.
     * </p>
     *
     * @param message - received on the event bus.
     */
    public void handle(Message<JsonObject> message) {
        try {
            handleInternal(message);
        } catch (SystemException ex) {
            log().error(ex.getMessage(), ex);
            message.fail(ex.code(), ex.getMessage());
        } catch (Exception ex) {
            log().error(ex.getMessage(), ex);
            message.fail(ExceptionCode.GENERAL.code(), ex.getMessage());
        }
    }

    /**
     * <p>
     * Do all the logic here and don't worry about exceptions.
     * </p>
     *
     * @param message - received on the event bus.
     */
    public abstract void handleInternal(Message<JsonObject> message);

}
