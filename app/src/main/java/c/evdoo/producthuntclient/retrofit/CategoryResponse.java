package c.evdoo.producthuntclient.retrofit;

import com.squareup.moshi.Json;

import java.util.List;

import c.evdoo.producthuntclient.models.Category;

public class CategoryResponse {

    @Json(name = "topics")
    List<Category> categories;

    public List<Category> getCategories() {
        return this.categories;
    }
}