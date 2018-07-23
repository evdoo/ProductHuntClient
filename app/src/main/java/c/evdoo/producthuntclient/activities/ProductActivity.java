package c.evdoo.producthuntclient.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import c.evdoo.producthuntclient.R;
import c.evdoo.producthuntclient.models.Product;
import c.evdoo.producthuntclient.util.Constants;

public class ProductActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.screenshot_image_view)
    ImageView screenshotImageView;

    @Bind(R.id.title_text_view)
    TextView titleTextView;

    @Bind(R.id.description_text_view)
    TextView descriptionTextView;

    @Bind(R.id.upvotes_text_view)
    TextView upvotesTextView;

    @Bind(R.id.get_it_button)
    Button getItButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final Product product = (Product) getIntent().getSerializableExtra(Constants.PRODUCT_KEY);

        Glide.with(this)
                .load(product.getScreenshot().getUrl())
                .fitCenter()
                .into(screenshotImageView);
        titleTextView.setText(product.getTitle());
        descriptionTextView.setText(product.getDescription());
        upvotesTextView.setText(String.valueOf(product.getUpvotes()));

        getItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(product.getUrl()));
                startActivity(intent);
            }
        });
    }
}