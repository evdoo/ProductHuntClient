package c.evdoo.producthuntclient.interfaces;

import c.evdoo.producthuntclient.retrofit.CategoryResponse;
import c.evdoo.producthuntclient.retrofit.ProductsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductHuntApi {

    @GET("/v1/categories/{category}/posts")
    Call<ProductsResponse> getDayPostsByCategory(@Header("Authorization") String token, @Path("category") String category);

    @GET("/v1/topics?search[trending]=true&per_page=10")
    Call<CategoryResponse> getTrendingTopics(@Header("Authorization") String token);
}