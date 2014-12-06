package me.bayes.sitemap.util;

import static me.bayes.sitemap.vertx.SitemapMod.Context.log;

import me.bayes.sitemap.exception.ExceptionCode;
import me.bayes.sitemap.exception.SystemException;
import me.bayes.sitemap.model.SiteUrl;
import me.bayes.sitemap.xsd.sitemap.ObjectFactory;
import me.bayes.sitemap.xsd.sitemap.TChangeFreq;
import me.bayes.sitemap.xsd.sitemap.TUrl;
import me.bayes.sitemap.xsd.sitemap.Urlset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 * Created by kevinbayes on 2014/12/01.
 */
public class JaxbUtil {

    public static class SiteMap {

        private static JAXBContext SITEMAP_CONTEXT;
        private static final ObjectFactory SITEMAP_FACTORY = new ObjectFactory();

        static {
            try {
                SITEMAP_CONTEXT = JAXBContext.newInstance("me.bayes.sitemap.xsd.sitemap");
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        public static ObjectFactory factory() {
            return SITEMAP_FACTORY;
        }

        public static void marshal(Urlset urlset, OutputStream outputStream) {
            try {
                SITEMAP_CONTEXT.createMarshaller().marshal(urlset, outputStream);
            } catch (JAXBException e) {
                log().error(e.getMessage(), e);
            }
        }

        public static Urlset unmarshal(File file) {

            try {
                return SITEMAP_CONTEXT.createUnmarshaller().unmarshal(
                    new StreamSource(new FileInputStream(file)),
                    Urlset.class
                ).getValue();
            } catch (JAXBException e) {
                e.printStackTrace();
                throw new SystemException(ExceptionCode.INVALID_FILE_CONTENT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new SystemException(ExceptionCode.INVALID_FILE_CONTENT);
            }


        }

        public static TUrl convert(SiteUrl siteUrl) {

            TUrl url = SITEMAP_FACTORY.createTUrl();

            url.setLoc(siteUrl.getUri());
            url.setPriority(siteUrl.getPriority());
            url.setLastmod(
                    DateUtil.convert(siteUrl.getLastModified()));
            url.setChangefreq(TChangeFreq.fromValue(siteUrl.getChangeFrequency()));

            return url;
        }

    }

    public static class SiteIndex {
        private static JAXBContext SITEINDEX_CONTEXT;
        private static final ObjectFactory SITEINDEX_FACTORY = new ObjectFactory();

        static {
            try {
                SITEINDEX_CONTEXT = JAXBContext.newInstance("me.bayes.sitemap.xsd.siteindex");
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        public static ObjectFactory factory() {
            return SITEINDEX_FACTORY;
        }
    }

}
