package com.hongsec.frame.tab.view.indicator.slidebar;

import android.content.Context;
import android.view.View;

/**
 * underline 类型为 颜色块
 * Created by Hongsec on 2016-03-22.
 */
public class ColorBar implements ScrollBar {

    protected Gravity gravity;
    protected View view;
    protected int color;
    protected int height;
    protected int width;


    /**
     * 默认为加到底部
     *
     * @param context
     * @param color
     * @param height
     */
    public ColorBar(Context context, int color, int height) {
        this(context, color, height, Gravity.BOTTOM);
    }

    public ColorBar(Context context, int color, int height, Gravity gravity) {
        this.view = new View(context);
        this.gravity = gravity;
        this.color = color;
        view.setBackgroundColor(color);
        this.height = height;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        view.setBackgroundColor(color);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight(int tabHeight) {
        if (height == 0) {
            return tabHeight;
        }
        return height;
    }

    @Override
    public int getWidth(int tabWidth) {
        if (width == 0) {
            return tabWidth;
        }
        return width;
    }

    @Override
    public View getSlideView() {
        return view;
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
}
