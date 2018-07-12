package com.gofar.component.basiclib.image;

import android.content.Context;
import android.widget.ImageView;

import com.gofar.component.basiclib.image.glide.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * @author lcf
 * @date 2018/7/12 15:10
 * @since 1.0
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(context).load(path).into(imageView);
    }
}
