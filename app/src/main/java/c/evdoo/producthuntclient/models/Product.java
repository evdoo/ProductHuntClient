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

    @Json(name = "thumbnail")
    private Thumbnail tumbnail;

    @Json(name = "screenshot_url")
    private Screenshot screenshot;

    @Json(name = "redirect_url")
    private String url;

    public Product(String title, String description, int upvotes, Thumbnail tumbnail, Screenshot screenshot, String url) {
        this.title = title;
        this.description = description;
        this.upvotes = upvotes;
        this.tumbnail = tumbnail;
        this.screenshot = screenshot;
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

    public Thumbnail getTumbnail() {
        return this.tumbnail;
    }

    public Screenshot getScreenshot() {
        return screenshot;
    }

    public String getUrl() {
        return url;
    }
}