package c.evdoo.producthuntclient.models;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Product implements Serializable {

    @Json(name = "name")
    private String title;

    @Json(name = "tagline")
    private String description;

    @Json(name = "votes_count")
    private int upvotes;

    @Json(name = "image_url")
    private String tumbnailUrl;

    @Json(name = "300px")
    private String screenshotUrl;

    @Json(name = "redirect_url")
    private String url;

    public Product(String title, String description, int upvotes, String tumbnailUrl, String screenshotUrl, String url) {
        this.title = title;
        this.description = description;
        this.upvotes = upvotes;
        this.tumbnailUrl = tumbnailUrl;
        this.screenshotUrl = screenshotUrl;
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getUpvotes() {
        return this.upvotes;
    }

    public String getTumbnailUrl() {
        return this.tumbnailUrl;
    }

    public String getScreenshotUrl() {
        return screenshotUrl;
    }

    public String getUrl() {
        return url;
    }
}