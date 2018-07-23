package c.evdoo.producthuntclient;

import android.app.Application;
import android.util.Log;

import c.evdoo.producthuntclient.interfaces.ProductHuntApi;
import c.evdoo.producthuntclient.util.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static android.content.ContentValues.TAG;

public class App extends Application {

    private static ProductHuntApi productHuntApi;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        init(Constants.BASE_URL);
    }

    private static void init(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                Log.d(TAG, message);
                            }
                        }).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .build();
    }

    public static ProductHuntApi getApi() {
        productHuntApi = retrofit.create(ProductHuntApi.class);
        return productHuntApi;
    }
}