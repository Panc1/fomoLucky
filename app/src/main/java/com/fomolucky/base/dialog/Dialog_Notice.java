package com.fomolucky.base.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.fomolucky.base.lifecycle.FullLifecycleObeserver;
import com.fomolucky.base.lifecycle.FullLifecycleObserverAdapter;
import com.fomolucky.main.R;


/**
 * Created by 杨 on 2018/4/6.
 */

public class Dialog_Notice extends Dialog implements FullLifecycleObeserver {
    private TextView yes;//确定按钮
    private TextView no;//取消按钮
    private LinearLayout item_no, item_yes;
    //    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private TextView title;//

    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
    private boolean is_open;
    private int mLayout = -1;
    private LifecycleOwner owner;
    private FullLifecycleObserverAdapter observerAdapter;

    private Dialog_Notice.onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private Dialog_Notice.onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, Dialog_Notice.onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * //     * 设置确定按钮的显示内容和监听
     * //     *
     * //     * @param str
     * //     * @param onYesOnclickListener
     * //
     */
    public void setYesOnclickListener(String str, Dialog_Notice.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public Dialog_Notice(Activity activity, boolean is_open) {
        super(activity, R.style.MyDialog);
        this.is_open = is_open;
    }

    public Dialog_Notice(Activity activity, boolean is_open, LifecycleOwner owner) {
        super(activity, R.style.MyDialog);
        this.owner = owner;
        this.observerAdapter = new FullLifecycleObserverAdapter(this, owner);
        this.is_open = is_open;
    }

    public Dialog_Notice(Activity activity, boolean is_open, int mLayout) {
        super(activity, R.style.MyDialog);
        this.is_open = is_open;
        this.mLayout = mLayout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLayout != -1)
            setContentView(mLayout);
        else setContentView(R.layout.dislog_notice);
        //按空白处不能取消动画
        if (is_open) {
            setCanceledOnTouchOutside(true);
        } else {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }


        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(v -> {
            if (yesOnclickListener != null) {
                yesOnclickListener.onYesClick();
            } else dismiss();
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(v -> {
            if (noOnclickListener != null) {
                noOnclickListener.onNoClick();
            } else dismiss();
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
//        //如果用户自定了title和message
        if (title != null) {
            if (titleStr != null) {
                title.setVisibility(View.VISIBLE);
                title.setText(titleStr);
            } else
                title.setVisibility(View.GONE);
        }

        if (messageTv != null) {
            if (messageStr != null) {
                messageTv.setVisibility(View.VISIBLE);
                messageTv.setText(messageStr);
            } else messageTv.setVisibility(View.GONE);
        }

//        //如果设置按钮的文字
        if (yes != null) {
//            设置按钮的文字
            if (yesStr != null) {
                item_yes.setVisibility(View.VISIBLE);
                yes.setText(yesStr);
            } else item_yes.setVisibility(View.GONE);
//            设置按钮背景
            if (isSetYesBackgroup) {
                if (yesDrawable != null)
                    yes.setBackground(yesDrawable);
                if (yesTextColor != null && !TextUtils.isEmpty(yesTextColor))
                    yes.setTextColor(Color.parseColor(yesTextColor));
            }
        }

        if (no != null) {
            //            设置按钮的文字
            if (noStr != null) {
                item_no.setVisibility(View.VISIBLE);
                no.setText(noStr);
            } else item_no.setVisibility(View.GONE);
            //            设置按钮背景
            if (isSetNoBackgroup) {
                if (noDrawable != null)
                    no.setBackground(noDrawable);
                if (noTextColor != null && !TextUtils.isEmpty(noTextColor))
                    no.setTextColor(Color.parseColor(noTextColor));
            }
        }


    }


    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = findViewById(R.id.act_dislog1_yes);
        item_yes = findViewById(R.id.item_yes);
        no = findViewById(R.id.act_dislog1_no);
        item_no = findViewById(R.id.item_no);
        title = findViewById(R.id.title);
        messageTv = findViewById(R.id.act_dislog1_message);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    private boolean isSetYesBackgroup = false;
    private Drawable yesDrawable;
    private String yesTextColor = "#333333";

    public void setYesDrawable(Drawable drawable, String TextColor) {
        isSetYesBackgroup = true;
        yesDrawable = drawable;
        yesTextColor = TextColor;
        if (yes != null) {
            yes.setBackground(yesDrawable);
            yes.setTextColor(Color.parseColor(yesTextColor));
        }
    }

    private boolean isSetNoBackgroup = false;
    private Drawable noDrawable;
    private String noTextColor = "#333333";

    public void setNoDrawable(Drawable drawable, String TextColor) {
        isSetNoBackgroup = true;
        noDrawable = drawable;
        noTextColor = TextColor;
        if (no != null) {
            no.setBackground(noDrawable);
            no.setTextColor(Color.parseColor(noTextColor));
        }


    }

    @Override
    public void show() {
        super.show();
        if (owner != null && observerAdapter != null)
            owner.getLifecycle().addObserver(observerAdapter);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (owner != null && observerAdapter != null)
            owner.getLifecycle().removeObserver(observerAdapter);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
    }

    @Override
    public void onStart(LifecycleOwner owner) {
    }

    @Override
    public void onResume(LifecycleOwner owner) {
    }

    @Override
    public void onPause(LifecycleOwner owner) {
    }

    @Override
    public void onStop(LifecycleOwner owner) {
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        dismiss();
    }


    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }
}
