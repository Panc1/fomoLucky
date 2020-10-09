package com.fomolucky.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fomolucky.main.R;

public class GlideLoadUtils {

    private String TAG = "ImageLoader";

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    public GlideLoadUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url       加载图片的url地址  String
     * @param imageView 加载图片的ImageView 控件
     */
    public void glideLoad(Context context, String url, ImageView imageView) {
        glideLoad(context, url, imageView, -1, -1);
    }

    public void glideLoad(Context context, String url, ImageView imageView, int coverImage) {
        glideLoad(context, url, imageView, coverImage, -1);
    }

    public void glideLoad(Context context, String url, ImageView imageView, int coverImage, int isShow) {
        if (context != null) {
            if (coverImage == -1)
                coverImage = R.mipmap.image_empty;
            RequestOptions options = new RequestOptions()
                    .error(coverImage)
                    .placeholder(coverImage);
            switch (isShow) {
                case 0:
                    Glide.with(context)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(options)
                            .into(imageView);
                    break;
                case 1:
                    Glide.with(context)
                            .load(url)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .apply(options)
                            .into(imageView);
                    break;
                case 2:
                    Glide.with(context)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .apply(options)
                            .into(imageView);
                    break;
                case 3:
                    Glide.with(context)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .apply(options)
                            .into(imageView);
                    break;
                case 4:
                    Glide.with(context)
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .apply(options)
                            .into(imageView);
                    break;
                default:
                    Glide.with(context)
                            .load(url)
                            .apply(options)
                            .into(imageView);
                    break;
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadWithThumbnail(Context context, String url, ImageView imageView, int coverImage) {
        RequestOptions options = new RequestOptions()
                .error(coverImage);

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }


    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url       加载图片的url地址  String
     * @param imageView 加载图片的ImageView 控件
     */
    public void glideLoadGif(Context context, String url, ImageView imageView) {
        if (context != null) {
            Glide.with(context).asGif().load(url).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            RequestOptions options = new RequestOptions()
                    .error(R.mipmap.image_empty)
                    .placeholder(R.mipmap.image_empty);
            Glide.with(activity)
                    .load(url)
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad1(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoadMain(Activity activity, String url, ImageView imageView) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, RequestOptions options, ImageView imageView) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, RequestOptions options) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).apply(options)
                    .preload();
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    public void glideLoad(Fragment fragment, String url, ImageView imageView) {
        if (fragment != null && fragment.getActivity() != null) {
            RequestOptions options = new RequestOptions()
                    .error(R.mipmap.image_empty)
                    .placeholder(R.mipmap.image_empty);
            Glide.with(fragment).load(url).apply(options)
                    .into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    public void glideLoad(androidx.fragment.app.Fragment fragment, String url, ImageView imageView) {
        if (fragment != null && fragment.getActivity() != null) {
            RequestOptions options = new RequestOptions()
                    .error(R.mipmap.image_empty)
                    .placeholder(R.mipmap.image_empty);
            Glide.with(fragment).load(url).apply(options).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,android.app.Fragment is null");
        }
    }


}
