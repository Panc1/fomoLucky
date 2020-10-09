package com.fomolucky.base.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LifecycleOwner;


public class NoticeDialogUtils extends DialogUtils{

    /**
     * NoticeDialog
     */
    //一按钮
    public static void notice_one(Activity activity, boolean is_open, String title, String message, String yesStr, dialogYesListener yesListener, Drawable yesDrawable, String yesTextColor, LifecycleOwner owner) {
        Dialog_Notice(activity, is_open, title, message, false, null, null, null, null, yesStr, yesListener, yesDrawable, yesTextColor, owner);
    }

    //一按钮 自定义：标题、内容、确定按钮(文字|事件)、外部点击事件
    public static void notice_one(Activity activity, boolean is_open, String title, String message, String yesStr, dialogYesListener yesListener, LifecycleOwner owner) {
        notice_one(activity, is_open, title, message, yesStr, yesListener, null, null, owner);
    }

    //一按钮 自定义：内容
    public static void notice_one(Activity activity, String message, LifecycleOwner owner) {
        notice_one(activity, true, null, message, null, null, null, null, owner);
    }

    //两按钮
    public static void notice_two(Activity activity, boolean is_open, String title, String message,
                                  String noStr, dialogNoListener noListener, Drawable noDrawable, String noTextColor,
                                  String yesStr, dialogYesListener yesListener, Drawable yesDrawable, String yesTextColor, LifecycleOwner owner) {
        Dialog_Notice(activity, is_open, title, message, true, noStr, noListener, noDrawable, noTextColor, yesStr, yesListener, yesDrawable, yesTextColor, owner);
    }

    // 两按钮 自定义： 标题、内容、取消按钮(文字|事件)、确定按钮(文字|事件)、外部点击事件
    public static void notice_two(Activity activity, boolean is_open, String title, String message, String noStr, dialogNoListener noListener, String yesStr, dialogYesListener yesListener, LifecycleOwner owner) {
        notice_two(activity, is_open, title, message, noStr, noListener, null, null, yesStr, yesListener, null, null, owner);
    }

    // 两按钮 自定义： 标题、内容、取消按钮(事件)、确定按钮(事件)、外部点击事件
    public static void notice_two(Activity activity, boolean is_open, String title, String message, dialogNoListener noListener, dialogYesListener yesListener, LifecycleOwner owner) {
        notice_two(activity, is_open, title, message, null, noListener, null, null, null, yesListener, null, null, owner);
    }

    //两按钮 自定义： 内容、确定按钮(事件)、外部点击事件
    public static void notice_two(Activity activity, boolean is_open, String message, dialogYesListener yesListener, LifecycleOwner owner) {
        notice_two(activity, is_open, null, message, null, null, null, null, null, yesListener, null, null, owner);
    }




}
