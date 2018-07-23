package c.evdoo.producthuntclient.models;

import com.squareup.moshi.Json;

public class Category {

    @Json(name = "name")
    private String title;

    private String slug;

    public Category(String title, String slug) {
        this.title = title;
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }
}