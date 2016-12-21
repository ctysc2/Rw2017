package com.home.rw.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import static android.R.attr.topLeftRadius;

/**
 * Created by cty on 2016/12/20.
 */

public class DrawableUtils {
    public static final int RADIUS_NONE = -1;
    public static final int RADIUS_LEFT = 0;
    public static final int RADIUS_RIGHT= 1;
    public static final int RADIUS_ALL = 2;
    public static Drawable getShapeDrawable(int bacgroundColor, int radiusType,int radiusVaule){
        float radius = radiusVaule;
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bacgroundColor);
        switch (radiusType){
            case RADIUS_NONE:
                gd.setCornerRadii(new float[] { 0, 0,
                                                0, 0,
                                                0, 0,
                                                0, 0 });
                break;
            case RADIUS_LEFT:
                gd.setCornerRadii(new float[] { radius, radius,
                                                0, 0,
                                                0, 0,
                                                radius, radius });
                break;
            case RADIUS_RIGHT:
                gd.setCornerRadii(new float[] { 0, 0,
                                                radius, radius,
                                                radius, radius,
                                                0, 0 });
                break;
            case RADIUS_ALL:
                gd.setCornerRadii(new float[] { radius, radius,
                                                radius, radius,
                                                radius, radius,
                                                radius, radius, });
                break;

        }
        return gd;

    }
}
