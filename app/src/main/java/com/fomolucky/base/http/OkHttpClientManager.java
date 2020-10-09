package com.fomolucky.base.http;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * @ClassName: OkHttpClientManager网络请求类 * @author yaodingding
 * @date 2015-9-22 下午1:56:46
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private PostDelegate mPostDelegate = null;
    private GetDelegate mGetDelegate = null;
    /**
     * 设置连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 60;
    /**
     * 设置读取超时时间
     */
    public final static int READ_TIMEOUT = 100;
    /**
     * 设置写的超时时间
     */
    public final static int WRITE_TIMEOUT = 60;

    private OkHttpClientManager(Context context) {
        mOkHttpClient = new OkHttpClient();
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
//        mOkHttpClient.setCache(cache);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        MediaType mediaType =  MediaType.parse("multipart/form-data; charset=utf-8");

        builder.cache(cache);
        mOkHttpClient = builder
                .connectTimeout(10000, TimeUnit.MILLISECONDS)//连接超时
                .readTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)//读取时间
                .writeTimeout(5 * 60 * 1000, TimeUnit.MILLISECONDS)//写入时间
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    //                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//
//                    }
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        mPostDelegate = new PostDelegate(mOkHttpClient);
        mGetDelegate = new GetDelegate(mOkHttpClient);

//        // cookie enabled
//        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
//        /* just for test !!! */
//        mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });

    }

    public static OkHttpClientManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager(context);
                }
            }
        }
        return mInstance;
    }

    public PostDelegate _getPostDelegate() {
        return mPostDelegate;
    }

    private GetDelegate _getGetDelegate() {
        return mGetDelegate;
    }

    public static GetDelegate getGetDelegate(Context context) {
        return getInstance(context)._getGetDelegate();
    }

    public static PostDelegate getPostDelegate(Context context) {
        return getInstance(context)._getPostDelegate();
    }

    /**
     * ============Get方便的访问方式============
     */
    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, false, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, null);
    }

    public static void getAsyn(Context context, String url, BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getGetDelegate(context).getAsyn(context, url, callback, forceNetWork, tag);
    }

    /**
     * ============POST方便的访问方式===============
     */
    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, null);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, tag);
    }

    public static void postAsyn(Context context, String url, Map<String, String> params, final BaseDelegate.ResultCallback callback, boolean forceNetWork, Object tag) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url, params, callback, forceNetWork, tag);
    }

    public static void postAsyn(Context context, String url, String params_file_name , Map<String, String> params, final List<String> pathList, final BaseDelegate.ResultCallback callback, boolean forceNetWork) {
        getInstance(context).getPostDelegate(context).postAsyn(context, url,params_file_name, params, pathList, callback, forceNetWork);
    }

    //    public static void cancelTag(Context context, Object tag) {
    public static void cancelTag(Context context, Object tag) {
        //   client = mOkHttpClient,
        if (context == null || tag == null) return;
        getInstance(context)._cancelTag(tag);
    }

    private void _cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        //     mOkHttpClient.cancel(tag);
//        mOkHttpClient.dispatcher().cancelAll();
    }

    public static OkHttpClient getClinet(Context context) {
        return getInstance(context).client();
    }

    private OkHttpClient client() {
        return mOkHttpClient;
    }

}
