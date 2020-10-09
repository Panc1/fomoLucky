package com.fomolucky.base.lifecycle;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by 杨 on 2018/3/4.
 */

public class ActExit {

    private ActExit() {
    }

    private static ActExit sManager;
    private static Stack<WeakReference<Activity>> mActivityStack;

    public static ActExit getManager() {
        if (sManager == null) {
            synchronized (ActExit.class) {
                if (sManager == null) {
                    sManager = new ActExit();
                }
            }
        }
        return sManager;
    }

    /**
     *  
     * 添加Activity到栈 
     *
     * @param activity 
     */

    public static void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     *  
     * 检查弱引用是否释放,若释放,则从栈中清理掉该元素 
     */

    public static void checkWeakReference() {
        if (mActivityStack != null) {
// 使用迭代器进行安全删除 
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                }
            }
        }
    }

    /**
     *  
     * 获取当前Activity(栈中最后一个压入的) 
     *
     * @return 
     */

    public static Activity currentActivity() {
        checkWeakReference();
        if (mActivityStack != null &&
                !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     *  
     * 关闭当前Activity(栈中最后一个压入的) 
     */

    public static void finishActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishActivity(activity);
        }
    }

    /**
     *  
     * 关闭指定的Activity 
     *
     * @param activity 
     */

    public static void finishActivity(Activity activity) {
        if (activity != null &&
                mActivityStack != null) {
// 使用迭代器进行安全删除 
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
// 清理掉已经释放的activity 
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
            activity.finish();
        }
    }

    /**
     *  
     * 关闭指定类名的所有Activity 
     *
     * @param cls 
     */


    public static void finishActivity(Class<?> cls) {
        if (mActivityStack != null) {
// 使用迭代器进行安全删除 
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
// 清理掉已经释放的activity 
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }
    /**
     *  
     * 结束所有Activity 
     */
    public static void finishAllActivity() {
        if (mActivityStack != null) {
            for (WeakReference<Activity> activityReference : mActivityStack) {
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            mActivityStack.clear();
        }
    }
}

