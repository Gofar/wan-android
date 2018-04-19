package com.gofar.component.basiclib.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * @author lcf
 * @date 2018/4/19 11:04
 * @since 1.0
 */
public abstract class ImageLoader {
    public abstract void load(Context context, String url, ImageView imageView);

    public abstract void load(Context context, String url, ImageView imageView,int placeHolder);
}
