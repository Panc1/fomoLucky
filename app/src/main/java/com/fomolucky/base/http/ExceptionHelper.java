package com.fomolucky.base.http;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.fomolucky.base.utils.Utils;
import com.google.gson.JsonParseException;

import java.net.UnknownHostException;

public class ExceptionHelper {
    public static String getMessage(Exception exception, Context context) {
        if (isNetworkProblem(context, exception)) {
            return "网络连接失败！请检查是否联网(4)";
        } else if (isServerProblem(exception)) {
            return "网络连接失败！请检查是否联网(3)";
        } else if (isJsonParseException(exception)) {
            return "网络连接失败！请检查是否联网";
        }
        return "网络连接失败！请检查是否联网(2)";
    }

    private static boolean isNetworkProblem(Context context, Exception exception) {
        if (exception instanceof NetworkErrorException) {
            return true;
        }
        if (exception instanceof UnknownHostException) {
            return Utils.isNetworkAvailable(context) == 0;
        }
        return false;
    }

    private static boolean isServerProblem(Exception exception) {
        return (exception instanceof UnknownHostException);
    }

    private static boolean isJsonParseException(Exception exception) {
        return (exception instanceof JsonParseException);
    }

}
