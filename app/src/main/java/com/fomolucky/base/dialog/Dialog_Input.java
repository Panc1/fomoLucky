package com.fomolucky.base.dialog;

import android.accessibilityservice.AccessibilityService;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.lifecycle.LifecycleOwner;

import com.fomolucky.base.lifecycle.FullLifecycleObeserver;
import com.fomolucky.main.R;

public class Dialog_Input extends Dialog implements FullLifecycleObeserver {

    private Context mContext;


    public Dialog_Input(Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }

    public Dialog_Input(Context context, int themeResId) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        //设置dialog大小与位置
        Window dialogWindow = Dialog_Input.this.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = mContext.getResources().getDisplayMetrics().heightPixels;//高度
        lp.alpha = 0.8f; // 透明度
        dialogWindow.setAttributes(lp);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(true);
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

    }

    public static class Builder {

        private Dialog_Input dialog;

        public Builder(Context context) {
            this(new Dialog_Input(context));
        }

        public Builder(Dialog_Input dialog) {
            this.dialog = dialog;
        }

        public Builder setIsOpen(boolean isOpen) {
            dialog.setCanceledOnTouchOutside(isOpen);
            return this;
        }

        public Dialog_Input build() {
            return dialog;
        }

    }

}
