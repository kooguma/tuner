package top.koguma.tuner.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import top.koguma.tuner.R;
import top.koguma.tuner.adapter.ProductAdapter;
import top.koguma.tuner.model.Product;

import static android.widget.LinearLayout.HORIZONTAL;

public class MainActivity extends TunerBaseActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private LinearLayoutManager layoutManager;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
    }

    private void initData() {
        products = new ArrayList<>();
        products.add(new Product("product1"));
        products.add(new Product("product2"));
        products.add(new Product("product3"));
        products.add(new Product("product4"));
        products.add(new Product("product5"));
        products.add(new Product("product6"));
        products.add(new Product("product7"));
        products.add(new Product("product8"));
        products.add(new Product("product9"));
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_32dp));
        recyclerView.addItemDecoration(itemDecoration);
        productAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(productAdapter);
    }
}
