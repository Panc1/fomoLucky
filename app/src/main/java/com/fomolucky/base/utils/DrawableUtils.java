package com.fomolucky.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.io.Serializable;
import java.util.ArrayList;


public class DrawableUtils {

    //shape
    public static Drawable getRadiusDrawable(Context mContext, int topLeft, int topRight, int bottomLeft, int bottomRight, String color) {
        int backgroundColor = Color.parseColor(color);
        int topLeftRadius = dp2px(mContext, topLeft);
        int topRightRadius = dp2px(mContext, topRight);
        int bottomLeftRadius = dp2px(mContext, bottomLeft);
        int bottomRightRadius = dp2px(mContext, bottomRight);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        drawable.setColor(backgroundColor);
        //参数 1 2代表左上角 参数 3 4代表右上角 参数 5 6代表左下角 参数 7 8代表右下角
        drawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomLeftRadius, bottomLeftRadius, bottomRightRadius, bottomRightRadius});
//        drawable.setStroke(2, Color.parseColor("#979797"));
        return drawable;
    }

    public static Drawable getGradientAndRadiusDrawable(Context mContext, int topLeft, int topRight, int bottomLeft, int bottomRight, String startColor, String endColor) {
        return getRectangleDrawable(mContext, null,
                true, topLeft, topRight, bottomLeft, bottomRight,
                true, "", startColor, endColor,
                false, "", 0
        );
    }

    public static Drawable getGradientAndRadiusDrawable(Context mContext, GradientDrawable.Orientation orientation, int topLeft, int topRight, int bottomLeft, int bottomRight, String startColor, String endColor) {
        return getRectangleDrawable(mContext, orientation,
                true, topLeft, topRight, bottomLeft, bottomRight,
                true, "", startColor, endColor,
                false, "", 0
        );
    }

    public static Drawable getRadiusAndStroke(Context mContext, int topLeft, int topRight, int bottomLeft, int bottomRight, String strokeColor, int strokeWidth) {
        return getRectangleDrawable(mContext, null,
                true, topLeft, topRight, bottomLeft, bottomRight,
                false, "#00ffffff", "", "",
                true, strokeColor, strokeWidth);
    }

    public static Drawable getRadiusAndStroke(Context mContext, int topLeft, int topRight, int bottomLeft, int bottomRight, String BGColor, String strokeColor, int strokeWidth) {
        return getRectangleDrawable(mContext, null,
                true, topLeft, topRight, bottomLeft, bottomRight,
                false, BGColor, "", "",
                true, strokeColor, strokeWidth);
    }

    private static Drawable getRectangleDrawable(Context mContext, GradientDrawable.Orientation orientation, boolean isRadius, int topLeft, int topRight, int bottomLeft, int bottomRight, boolean isGradient, String color, String startColor, String endColor, boolean isStroke, String strokeColor, int strokeWidth) {
        GradientDrawable drawable;
        if (orientation != null)
            drawable = new GradientDrawable(orientation, null);
        else drawable = new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        if (isRadius) {
            int topLeftRadius = dp2px(mContext, topLeft);
            int topRightRadius = dp2px(mContext, topRight);
            int bottomLeftRadius = dp2px(mContext, bottomLeft);
            int bottomRightRadius = dp2px(mContext, bottomRight);
            //参数 1 2代表左上角 参数 3 4代表右上角 参数 5 6代表左下角 参数 7 8代表右下角
            drawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomLeftRadius, bottomLeftRadius, bottomRightRadius, bottomRightRadius});
        }
        if (isGradient) {
            //GradientDrawable.LINEAR_GRADIENT(线性渐变)，GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
            drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            //增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
            drawable.setColors(new int[]{Color.parseColor(startColor), Color.parseColor(endColor)});
        } else {
            int backgroundColor = Color.parseColor(color);
            drawable.setColor(backgroundColor);
        }
        if (isStroke)
            drawable.setStroke(strokeWidth, Color.parseColor(strokeColor));
        return drawable;
    }

    public static Drawable getClickedDrawable(Drawable clicked, Drawable afterClicked) {
        ArrayList<SelectorModel> list = new ArrayList<>();
        list.add(new SelectorModel(new int[]{-android.R.attr.state_enabled}, clicked));
        list.add(new SelectorModel(new int[]{android.R.attr.state_pressed}, clicked));
        list.add(new SelectorModel(new int[]{-android.R.attr.state_pressed}, afterClicked));
        return selectorDrawable(list);
    }

    private static Drawable selectorDrawable(ArrayList<SelectorModel> list) {
        StateListDrawable drawable = new StateListDrawable();
        for (SelectorModel item : list)
            drawable.addState(item.getStateSet(), item.getDrawable());
        return drawable;
    }


    public static class SelectorModel implements Serializable {
        private int[] stateSet;
        private Drawable drawable;

        public SelectorModel(int[] stateSet, Drawable drawable) {
            this.stateSet = stateSet;
            this.drawable = drawable;
        }

        public int[] getStateSet() {
            return stateSet;
        }

        public Drawable getDrawable() {
            return drawable;
        }
    }

    private static int dp2px(Context var0, float var1) {
        var1 = (float) (1.125D * (double) var1);
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int) (var1 * var2 + 0.5F);
    }
}
