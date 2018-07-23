package c.evdoo.producthuntclient.models;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Screenshot implements Serializable {

    @Json(name = "300px")
    private String url;

    public Screenshot(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}