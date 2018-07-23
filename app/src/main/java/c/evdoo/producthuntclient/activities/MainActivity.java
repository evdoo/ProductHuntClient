package c.evdoo.producthuntclient.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import c.evdoo.producthuntclient.App;
import c.evdoo.producthuntclient.R;
import c.evdoo.producthuntclient.models.Category;
import c.evdoo.producthuntclient.models.Product;
import c.evdoo.producthuntclient.retrofit.CategoryResponse;
import c.evdoo.producthuntclient.retrofit.ProductsResponse;
import c.evdoo.producthuntclient.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.categories_drawer_layout)
    DrawerLayout categoriesDrawerLayout;

    @Bind(R.id.categories_navigation_view)
    NavigationView categoriesNavigationView;

    @Bind(R.id.categories_list_view)
    ListView categoriesListView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.products_swipe_refresh_layout)
    SwipeRefreshLayout productsSwipeRefreshLayout;

    @Bind(R.id.products_list_view)
    ListView productsListView;

    private String currentCategoryId = "tech";
    private String currentCategoryTitle = "Tech";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle(currentCategoryTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriesDrawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoriesDrawerLayout.openDrawer(Gravity.START);
            }
        });

        App.getApi().getDayPostsByCategory(Constants.TOKEN, currentCategoryId).enqueue(new PostsCallback());
        App.getApi().getTrendingTopics(Constants.TOKEN).enqueue(new CategoriesCallback());

        productsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                App.getApi().getDayPostsByCategory(Constants.TOKEN, currentCategoryId).enqueue(new PostsCallback());
            }
        });
    }

    private class ProductsAdapter extends BaseAdapter {
        ArrayList<Product> products;
        LayoutInflater inflater;

        ProductsAdapter(Context context, ArrayList<Product> products) {
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
            Log.wtf("product pic", product.getTumbnail().getUrl());

            title.setText(product.getTitle());
            description.setText(product.getDescription());
            upvotes.setText(String.valueOf(product.getUpvotes()));

            Glide.with(MainActivity.this)
                    .load(product.getTumbnail().getUrl())
                    .fitCenter()
                    .into(thumbnail);

            return view;
        }
    }

    private class CategoriesAdapter extends BaseAdapter {
        private ArrayList<Category> categories;
        private LayoutInflater inflater;

        CategoriesAdapter(Context context, ArrayList<Category> categories) {
            this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.categories = categories;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Category getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.item_category, parent, false);
            }

            TextView categoryTitle = (TextView) view.findViewById(R.id.category_text_view);

            Category c = categories.get(position);

            categoryTitle.setText(c.getTitle());

            return view;
        }
    }

    private class PostsCallback implements Callback<ProductsResponse> {

        @Override
        public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                if (response.code() == Constants.RESULT_CODE_OK) {
                    ArrayList<Product> products = new ArrayList<>(response.body().getProducts());
                    Log.wtf("list", products.toString());
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
                    productsSwipeRefreshLayout.setRefreshing(false);
                    toolbar.setTitle(currentCategoryTitle);
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

    private class CategoriesCallback implements Callback<CategoryResponse> {
        @Override
        public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                if (response.code() == Constants.RESULT_CODE_OK) {
                    ArrayList<Category> categories = new ArrayList<>(response.body().getCategories());
                    CategoriesAdapter categoriesAdapter = new CategoriesAdapter(MainActivity.this, categories);
                    categoriesListView.setAdapter(categoriesAdapter);
                    categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Category category = (Category) parent.getAdapter().getItem(position);
                            currentCategoryId = category.getSlug();
                            currentCategoryTitle = category.getTitle();
                            toolbar.setTitle(currentCategoryTitle);
                            App.getApi().getDayPostsByCategory(Constants.TOKEN, currentCategoryId).enqueue(new PostsCallback());
                            categoriesDrawerLayout.closeDrawer(Gravity.START);
                        }
                    });
                }
            }
        }

        @Override
        public void onFailure(Call<CategoryResponse> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(MainActivity.this, getResources().getString(R.string.smth_wrong),
                    Toast.LENGTH_SHORT).show();
        }
    }
}