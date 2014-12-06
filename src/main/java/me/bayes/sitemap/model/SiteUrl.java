package me.bayes.sitemap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.bayes.sitemap.xsd.sitemap.TChangeFreq;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by kevinbayes on 2014/12/02.
 */
public class SiteUrl {

    private String uri;

    @JsonProperty("last_modified")
    private Date lastModified;

    @JsonProperty("priority")
    private BigDecimal priority;

    @JsonProperty("change_frequency")
    private String changeFrequency;

    public SiteUrl() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public BigDecimal getPriority() {
        if(priority == null) priority = new BigDecimal(0.5);
        return priority.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }

    public String getChangeFrequency() {
        if(changeFrequency == null) return TChangeFreq.NEVER.value();
        return changeFrequency;
    }

    public void setChangeFrequency(String changeFrequency) {
        this.changeFrequency = changeFrequency;
    }

}
