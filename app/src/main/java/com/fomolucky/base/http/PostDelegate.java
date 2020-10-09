package com.fomolucky.base.http;

import android.content.Context;
import android.graphics.Bitmap;


import com.fomolucky.base.helper.CompressHelper;
import com.fomolucky.base.utils.FileSizeUtil;
import com.fomolucky.base.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import okhttp3.FormEncodingBuilder;
//import okhttp3.FormEncodingBuilder;

//====================PostDelegate=======================
public class PostDelegate extends BaseDelegate {
    public PostDelegate(OkHttpClient mOkHttpClient) {
        super(mOkHttpClient);
    }

    private Request buildPostFormRequest(Context context, String url, Map<String, String> params, boolean forceNetWork, Object tag) {

        if (params == null) {
            params = new HashMap<String, String>();
        }

        //demo
//		FormBody body = new FormBody.Builder()
//				.add("your_param_1", "your_value_1")
//				.add("your_param_2", "your_value_2")
//				.build();

        FormBody.Builder builder = null;

        if (params.size() > 0) {
            builder = new FormBody.Builder();
        }

        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
//				System.out.println("Key = " + entry.getKey() + ", Value = "+ entry.getValue());
            builder.add(entry.getKey(), entry.getValue());
        }

        //		Set<Map.Entry<String, String>> entries = params.entrySet();
//		for (Map.Entry<String, String> entry : entries) {
//		 new FormBody.Builder() = body.addFormDataPart(entry.getKey(), entry.getValue());
//		//	.build();
//		}

        RequestBody body = builder.build();

        //demo
//		Request request = new Request.Builder()
//				.url("http://jifen.xianhua.cn/testpost")
//				.post(body)
//				.build();
//
//		return request;

//		if (params == null) {
//			params = new HashMap<String, String>();
//		}
//		//old
////		FormEncodingBuilder builder = null;
////		if (params.size() > 0) {
////			builder = new FormEncodingBuilder();
////		}
////		Set<Map.Entry<String, String>> entries = params.entrySet();
////		for (Map.Entry<String, String> entry : entries) {
////			builder.add(entry.getKey(), entry.getValue());
////		}
//		//old
//		//new
//		FormBody builder = null;
//		Set<Map.Entry<String, String>> entries = params.entrySet();
//		for (Map.Entry<String, String> entry : entries) {
//			if (params.size() > 0) {
//				builder = new FormBody.Builder()
//						.add(entry.getKey(), entry.getValue())
//						.build();
//			}
//		}
//		//new

        Request.Builder reqBuilder = null;
        if (forceNetWork) {// 强制走网络 对于特殊请求
            reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).url(url);
        } else if (Utils.isNetworkAvailable(context) == 0) {// 没有网络则强制请求缓存中数据
            reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_CACHE).url(url);
        } else {// 否则走网络
            reqBuilder = new Request.Builder().url(url);
        }
        if (builder != null) {
//			RequestBody requestBody = builder.build();
//			RequestBody requestBody =  new FormBody.Builder().build();
            reqBuilder.post(body);
        }
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        return reqBuilder.build();

    }

    private Request upLoadFiles(Context context, String url, String params_file_name, Map<String, String> params, final List<String> pathList, boolean forceNetWork, Object tag) {

        if (params == null) {
            params = new HashMap<String, String>();
        }

        //demo
//		FormBody body = new FormBody.Builder()
//				.add("your_param_1", "your_value_1")
//				.add("your_param_2", "your_value_2")
//				.build();

        MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
//				System.out.println("Key = " + entry.getKey() + ", Value = "+ entry.getValue());
           builder.addFormDataPart(entry.getKey(), entry.getValue());
        }

//循环添加文件
        for (int i = 0; i < pathList.size(); i++) {
//                Log.i("imageName:", file.getName());//经过测试，此处的名称不能相同，如果相同，只能保存最后一个图片，不知道那些同名的大神是怎么成功保存图片的。
            if (FileSizeUtil.getFileOrFilesSize(pathList.get(i), 2) > 500){
                File file = new CompressHelper.Builder(context).setQuality(90).setCompressFormat(Bitmap.CompressFormat.JPEG).build().compressToFile(new File(pathList.get(i)));
                builder.addFormDataPart(params_file_name + i, file.getName(), RequestBody.create(MutilPart_Form_Data, file));
            }else {
                File file = new File(pathList.get(i));
                builder.addFormDataPart(params_file_name + i, file.getName(), RequestBody.create(MutilPart_Form_Data, file));
            }
        }
        RequestBody requestBody = builder.build();

        Request.Builder reqBuilder = null;
        if (forceNetWork) {// 强制走网络 对于特殊请求
            reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).url(url);
        } else if (Utils.isNetworkAvailable(context) == 0) {// 没有网络则强制请求缓存中数据
            reqBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_CACHE).url(url);
        } else {// 否则走网络
            reqBuilder = new Request.Builder().url(url);
        }
        if (builder != null) {
            reqBuilder.post(requestBody);
        }
        if (tag != null) {
            reqBuilder.tag(tag);
        }
        return reqBuilder.build();

    }

    /**
     * 同步的Post请求
     */
    public Response post(Context context, String url, Map<String, String> params) throws IOException {
        return post(context, url, params, false, null);
    }

    public Response post(Context context, String url, Map<String, String> params, boolean forceNetWork) throws IOException {
        return post(context, url, params, false, null);
    }

    public Response post(Context context, String url, Map<String, String> params, boolean forceNetWork, Object tag) throws IOException {
        Request request = buildPostFormRequest(context, url, params, forceNetWork, tag);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 异步的Post请求
     */
    public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback) {
        postAsyn(context, url, params, callback, false, null);
    }

    public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, boolean forceNetWork) {
        postAsyn(context, url, params, callback, forceNetWork, null);
    }

    public void postAsyn(Context context, String url, String params_file_name, Map<String, String> params, final List<String> pathList, final ResultCallback callback, boolean forceNetWork) {
        postAsyn(context, url,params_file_name, params, pathList, callback, forceNetWork, null);
    }

    public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, Object tag) {
        postAsyn(context, url, params, callback, false, null);
    }

    public void postAsyn(Context context, String url, Map<String, String> params, final ResultCallback callback, boolean forceNetWork, Object tag) {
        Request request = buildPostFormRequest(context, url, params, forceNetWork, tag);
        String log = "接口：("+ url + ")\n参数 ：" + params.toString();
        deliveryResult(context, callback, request, log);
    }

    public void postAsyn(Context context, String url, String params_file_name, Map<String, String> params, final List<String> pathList, final ResultCallback callback, boolean forceNetWork, Object tag) {
        Request request = upLoadFiles(context, url, params_file_name, params, pathList, forceNetWork, tag);
        String log = "接口：("+ url + ")\n参数 ：" + params.toString();
        deliveryResult(context, callback, request, log);
    }

}
