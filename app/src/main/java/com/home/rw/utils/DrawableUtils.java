package com.home.rw.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.home.rw.R;

import java.util.Random;

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

    public static int getRandomBackgroundResource(){

        int d = 0;
        int random = new Random().nextInt(5);
            switch (random){
                case 0:
                    d = R.drawable.shape_circle_blue;
                    break;
                case 1:
                    d = R.drawable.shape_circle_red;
                    break;
                case 2:
                    d = R.drawable.shape_circle_green;
                    break;
                case 3:
                    d = R.drawable.shape_circle_yellow;
                    break;
                case 4:
                    d = R.drawable.shape_circle_purple;
                    break;

            }

        return d;

    }
}
