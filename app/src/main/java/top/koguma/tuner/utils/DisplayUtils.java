//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.koguma.tuner.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtils {
    public DisplayUtils() {
    }

    public static int getWindowHeight(Context context) {
        return getWindowPoint(context).y;
    }

    private static Point getWindowPoint(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService("window");
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService("window");
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
