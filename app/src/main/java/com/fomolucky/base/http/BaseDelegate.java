package com.fomolucky.base.http;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.fomolucky.base.utils.NoticeDialogUtils;
import com.fomolucky.base.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseDelegate {

    protected Handler mDelivery;
    private Gson mGson;
    protected OkHttpClient mOkHttpClient;

    public BaseDelegate(OkHttpClient mOkHttpClient) {
        mGson = new Gson();
        mDelivery = new Handler(Looper.getMainLooper());
        this.mOkHttpClient = mOkHttpClient;
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public void onBefore(Request request) {
        }

        public void onAfter() {
        }

        public void onTimeFailed(Request request, Object tag, Exception e) {
        }


        public abstract void onError(Request request, Object tag, Exception e);

        public abstract void onResponse(T response, Object tag);
    }

    private final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(Request request, Object tag, Exception e) {
        }

        @Override
        public void onResponse(String response, Object tag) {

        }
    };

    protected void deliveryResult(Context context, ResultCallback callback, Request request, String log) {
        if (callback == null)
            callback = DEFAULT_RESULT_CALLBACK;
        final ResultCallback resCallBack = callback;
        // UI thread
        callback.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//				sendFailedStringCallback(request, e, resCallBack);

                if (e instanceof SocketTimeoutException) {
                    //判断超时异常
                    Utils.LogI(log + "\n获得数据超时：" + e.toString());
                    sendTimeFailedCallback(request, e, resCallBack);
                    return;
                }
                if (e instanceof ConnectException) {
                    ////判断连接异常，
                    Utils.LogI(log + "\n连接异常：" + e.toString());
                    sendFailedStringCallback(request, e, resCallBack);
                    return;
                }

                if (e instanceof UnknownHostException) {
                    Utils.LogI(log + "\n找不到服务器地址：" + e.toString());
                    sendFailedStringCallback(request, e, resCallBack);
                    return;
                }
                Utils.LogI(log + "\n未知错误：" + e.toString());
                sendFailedStringCallback(request, e, resCallBack);

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.body() == null)
                        return;
                    final String string = response.body().string();
                    Utils.LogI(log + "\n成功获得数据：" + string);
                    if (string != null && !TextUtils.isEmpty(string)) {
                        if (resCallBack.mType != String.class) {
                            try {
                                JSONObject jsonObject = new JSONObject(string);
                                if (jsonObject.has("code")) {
                                    if (jsonObject.getInt("code") == -220) {
                                        String msg = jsonObject.getString("msg");

                                        mDelivery.postDelayed(() -> {
                                            if (context != null && !((Activity) context).isFinishing())
                                                NoticeDialogUtils.notice_one((Activity) context, false, "提示", msg, "了解", () -> {
                                                }, null);
                                        }, 1000);
                                        return;
                                    }
                                }
                            } catch (JSONException e) {
                                Utils.LogI("解析失败：" + e.toString());
                                sendFailedStringCallback(response.request(), e, resCallBack);
                                return;
                            }
                        }
                        if (resCallBack.mType == String.class) {
                            sendSuccessResultCallback(response.request(), string, resCallBack);
                        } else {
                            Object o = mGson.fromJson(string, resCallBack.mType);
                            sendSuccessResultCallback(response.request(), o, resCallBack);
                        }
                    }

                } catch (IOException e) {
                    Utils.LogI("解析失败：" + e.toString());
                    sendFailedStringCallback(response.request(), e, resCallBack);
                } catch (com.google.gson.JsonParseException e) {// Json解析的错误
                    Utils.LogI("解析失败：" + e.toString());
                    sendFailedStringCallback(response.request(), e, resCallBack);
                }
            }
        });
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(() -> {
            callback.onError(request, request.tag(), e);
            callback.onAfter();
        });
    }

    private void sendSuccessResultCallback(final Request request, final Object object, final ResultCallback callback) {
        mDelivery.post(() -> {

            callback.onResponse(object, request.tag());
            callback.onAfter();
        });
    }

    private void sendTimeFailedCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(() -> {
            callback.onTimeFailed(request, request.tag(), e);
            callback.onAfter();
        });
    }
}
