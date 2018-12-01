package top.koguma.tuner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import top.koguma.tuner.R;
import top.koguma.tuner.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item_product, parent, false);
        ProductViewHolder holder = new ProductViewHolder(v);
        return holder;
    }

    @Override public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameText.setText(product.name);
    }

    @Override public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView productImage;
        TextView nameText;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.img_product);
            nameText = itemView.findViewById(R.id.txt_name);
        }
    }

}
