package c.evdoo.producthuntclient.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import c.evdoo.producthuntclient.App;
import c.evdoo.producthuntclient.R;
import c.evdoo.producthuntclient.models.Product;
import c.evdoo.producthuntclient.retrofit.ProductsResponse;
import c.evdoo.producthuntclient.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.products_swipe_refresh_layout)
    SwipeRefreshLayout productsSwipeRefreshLayout;

    @BindView(R.id.products_list_view)
    ListView productsListView;

    private String currentCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        App.getApi().getTechDayPosts(Constants.TOKEN).enqueue(new TechPostsCallback());

//        productsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
    }

    private class ProductsAdapter extends BaseAdapter {
        ArrayList<Product> products;
        LayoutInflater inflater;

        public ProductsAdapter(Context context, ArrayList<Product> products) {
            this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.products = products;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Product getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.item_product, parent, false);
            }

            ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail_image_view);
            TextView title = (TextView) view.findViewById(R.id.title_text_view);
            TextView description = (TextView) view.findViewById(R.id.description_text_view);
            TextView upvotes = (TextView) view.findViewById(R.id.upvotes_text_view);

            Product product = products.get(position);

            title.setText(product.getTitle());
            description.setText(product.getDescription());
            upvotes.setText(String.valueOf(product.getUpvotes()));

            return view;
        }
    }

    private class TechPostsCallback implements Callback<ProductsResponse> {

        @Override
        public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                if (response.code() == Constants.RESULT_CODE_OK) {
                    ArrayList<Product> products = new ArrayList<>(response.body().getProducts());
                    ProductsAdapter productsAdapter = new ProductsAdapter(MainActivity.this, products);
                    productsListView.setAdapter(productsAdapter);
                    productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product p = (Product) parent.getAdapter().getItem(position);
                            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                            intent.putExtra(Constants.PRODUCT_KEY, p);
                            startActivity(intent);
                        }
                    });
                }
            }
        }

        @Override
        public void onFailure(Call<ProductsResponse> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(MainActivity.this, getResources().getString(R.string.smth_wrong),
                    Toast.LENGTH_SHORT).show();
        }
    }
}