package com.xzchang.food2fork.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by xiangzhc on 01/12/2016.
 */

public class Recipie {
    @SerializedName("recipe_id")
    private String recipieId;
    private String publisher;
    private double socialRank;
    private String title;
    private URL imageUrl;
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

    public String getRecipieId() {
        return recipieId;
    }

    public URL getImageUrl() {
        return imageUrl;
    }
}
