package c.evdoo.producthuntclient.models;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Thumbnail implements Serializable {

    @Json(name = "image_url")
    private String url;

    public Thumbnail(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}