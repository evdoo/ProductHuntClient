package c.evdoo.producthuntclient.retrofit;

import com.squareup.moshi.Json;

import java.util.List;

import c.evdoo.producthuntclient.models.Product;

public class ProductsResponse {

    @Json(name = "posts")
    List<Product> products;

    public List<Product> getProducts() {
        return this.products;
    }
}