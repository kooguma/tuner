package top.koguma.tuner;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import top.koguma.tuner.activity.HelpActivity;
import top.koguma.tuner.activity.MainActivity;
import top.koguma.tuner.activity.ProductDetailActivity;
import top.koguma.tuner.activity.SettingActivity;
import top.koguma.tuner.activity.ToningActivity;
import top.koguma.tuner.model.Product;

public final class Navigator {

    public static final String EXTRA_PRODUCT = "extra_product";

    private Navigator() {

    }

    private static void startActivity(Context context, Class clz) {
        Intent intent = new Intent(context, clz);
        context.startActivity(intent);
    }

    public static void startProductDetailActivity(Context context, Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(EXTRA_PRODUCT, product);
        context.startActivity(intent);
    }

    public static void startSettingActivity(Context context) {
        startActivity(context, SettingActivity.class);
    }

    public static void startHelpActivity(Context context) {
        startActivity(context, HelpActivity.class);
    }


    public static void startToningActivity(Context context) {
        startActivity(context, ToningActivity.class);
    }
}
