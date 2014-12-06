package me.bayes.sitemap.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevinbayes on 2014/12/01.
 */
public class DateUtil {

    public static String convert(Date date) {
        //2005-05-10T17:33:30+08:00
        //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        return dateFormat.format(date);
    }

}
