package com.xzchang.food2fork.model;

import java.net.URL;

/**
 * Created by xiangzhc on 01/12/2016.
 */

public class Recipie {
    private String publisher;
    private double socialRank;
    private String title;
    private URL f2fUrl;
    private URL publisherUrl;
    private URL sourceUrl;

    public String getPublisher() {
        return publisher;
    }

    public double getSocialRank() {
        return socialRank;
    }

    public String getTitle() {
        return title;
    }

    public URL getF2fUrl() {
        return f2fUrl;
    }

    public URL getPublisherUrl() {
        return publisherUrl;
    }

    public URL getSourceUrl() {
        return sourceUrl;
    }
}
