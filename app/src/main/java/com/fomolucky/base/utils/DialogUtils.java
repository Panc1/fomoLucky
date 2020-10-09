package com.fomolucky.base.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;


import com.fomolucky.base.dialog.Dialog_Notice;


public class DialogUtils {

    //基础提示dialog
    public static void Dialog_Notice(Activity activity, boolean is_open, String title, String message, boolean isShowNo, String noStr, dialogNoListener noListener, Drawable noDrawable, String noTextColor, String yesStr, dialogYesListener yesListener, Drawable yesDrawable, String yesTextColor, LifecycleOwner owner) {

        Dialog_Notice dialog_notice;
        if (owner == null)
            dialog_notice = new Dialog_Notice(activity, is_open);
        else
            dialog_notice = new Dialog_Notice(activity, is_open, owner);
        if (title != null && !TextUtils.isEmpty(title))
            dialog_notice.setTitle(title);
        else
            dialog_notice.setTitle("提示");

        if (message != null)
            dialog_notice.setMessage(message);

        if (isShowNo) {
            String strNo = "取消";
            if (noStr != null && !TextUtils.isEmpty(noStr))
                strNo = noStr;
            dialog_notice.setNoOnclickListener(strNo, () -> {
                if (noListener != null)
                    noListener.onClicked();
                dialog_notice.dismiss();
            });

            Drawable drawable_no = DrawableUtils.getRadiusAndStroke(activity, 18, 18, 18, 18, "#480C4B", 1);
            if (noDrawable != null)
                drawable_no = noDrawable;
            String textColor_no = "#480C4B";
            if (noTextColor != null && !TextUtils.isEmpty(noTextColor))
                textColor_no = noTextColor;
            dialog_notice.setNoDrawable(drawable_no, textColor_no);
        }

        String strYes = "确定";
        if (yesStr != null && !TextUtils.isEmpty(yesStr))
            strYes = yesStr;
        dialog_notice.setYesOnclickListener(strYes, () -> {
            if (yesListener != null)
                yesListener.onClicked();
            dialog_notice.dismiss();
        });
        Drawable drawable_yes = DrawableUtils.getClickedDrawable(
                DrawableUtils.getRadiusDrawable(activity, 18, 18, 18, 18, "#D6D6D6"),
                DrawableUtils.getRadiusDrawable(activity, 18, 18, 18, 18, "#480C4B"));
        if (yesDrawable != null)
            drawable_yes = yesDrawable;
        String textColor_yes = "#ffffff";
        if (yesTextColor != null && !TextUtils.isEmpty(yesTextColor))
            textColor_yes = yesTextColor;
        dialog_notice.setYesDrawable(drawable_yes, textColor_yes);

        if (activity != null && !activity.isFinishing())
            dialog_notice.show();
    }

    public interface dialogYesListener {
        void onClicked();
    }

    public interface dialogNoListener {
        void onClicked();
    }
}
