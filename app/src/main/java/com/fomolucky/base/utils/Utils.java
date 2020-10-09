package com.fomolucky.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fomolucky.base.config.ConfigValue;
import com.fomolucky.main.R;
import com.google.gson.Gson;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sks on 2015/9/16.
 */
public class Utils {


    public static final int NO_NETWORK_STATE = 0;     //无网络
    public static final int WIFI_STATE = 1;     //wifi


    /*
     * 取得应用的版本号,就是哪个版本,
     */
    public static String longVersionName(Context context) {
        String mVersion = null;
        if (mVersion == null) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                mVersion = pi.versionName;

            } catch (NameNotFoundException e) {
                mVersion = ""; // failed, ignored
            }
        }
        return mVersion;
    }

    /*
     * 取得应用的版本号,就是修改次.
     */
    public static int longVersionCode(Context context) {
        int mVersionCode = 0;
        if (mVersionCode == 0) {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
                mVersionCode = pi.versionCode;

            } catch (NameNotFoundException e) {
                mVersionCode = 0; // failed, ignored
            }
        }
        return mVersionCode;
    }


    //使用Toast
    public static void showToast(Context context, String message) {
        //自定义Toast控件
        if (context != null) {
            View toastView = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
            TextView textView = toastView.findViewById(R.id.txt);
            textView.setText(message);
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(toastView);
            toast.show();
        }
    }


    //客户端认证加密字符串
    public static String AppMD5String(Context context, String model, String action) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            date = df.parse(df.format(new Date()));
        } catch (ParseException e) {
        }
        ;
        long time = date.getTime();
//        long timecurrentTimeMillis = System.currentTimeMillis();
        return md5(model + action + time + "99-k");
    }

    //字符串md5加密
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale - 0.5f);
    }

    /**
     * dp to px
     *
     * @param dp dip
     * @return int
     */
    public static int dip2px1(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //时间戳转时间
    public static String time(Context context, long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(s * 1000));
        return date;
    }


    //时间戳转时间
    public static String time2(Context context, long s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(s * 1000));
        return date;
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        Calendar from = Calendar.getInstance();
        from.setTime(date1);
        Calendar to = Calendar.getInstance();
        to.setTime(date2);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        int day = toDay - fromDay;
        return year;
    }

    public static String differentDaysByMillisecond1(Date date1, Date date2) {
        Calendar from = Calendar.getInstance();
        from.setTime(date1);
        Calendar to = Calendar.getInstance();
        to.setTime(date2);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int fromHH = from.get(Calendar.HOUR);
        int frommm = from.get(Calendar.MINUTE);
        int fromss = from.get(Calendar.SECOND);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int toHH = from.get(Calendar.HOUR);
        int tomm = from.get(Calendar.MINUTE);
        int toss = from.get(Calendar.SECOND);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        int day = toDay - fromDay;
        int hh = toHH - fromHH;
        int mm = tomm - frommm;
        int ss = toss - fromss;
        return "(" + hh + ":" + mm + ":" + ss + " 后过期)";
    }

    public static String ConciseTime(String createdatetimeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(System.currentTimeMillis());
        String createdatetime = createdatetimeStr;
        final String nowdate = simpleDateFormat.format(date);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(createdatetime);
            date2 = simpleDateFormat.parse(nowdate);
            long diff = (date2.getTime() - date1.getTime()) / 1000;
            if (diff > 0 && diff < 60) {
                return diff + "秒前";
            } else if (diff > 60 && diff < 3600) {
                return diff / 60 + "分钟前";
            } else if (diff >= 3600 && diff < 3600 * 24) {
                return diff / 3600 + "小时前";
            } else if (diff >= 3600 * 24 && diff < 3600 * 48) {
                return "昨天";
            } else if (diff >= 3600 * 48 && diff < 3600 * 72) {

                return "前天";
            } else if (diff >= 3600 * 72) {
                return createdatetimeStr;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "0秒前";
    }


    public static String TimeZhuanhuan(String dateStr) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = simpleDateFormat1.parse(dateStr);
            return simpleDateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String TimeZhuanhuan1(String dateStr) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd", Locale.CHINA);
        Date date = null;
        try {
            date = simpleDateFormat1.parse(dateStr);
            return simpleDateFormat2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    //适配 刘海屏
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    //截图保存手机

    public static void Jietu(Context context, View view) {
        Bitmap bmp = createViewBitmap2(view);
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "wemallShare");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sign_" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        Utils.showToast(context, "已保存到系统相册");
    }

    /**
     * 截取view
     **/
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 截取view
     **/
    public static Bitmap createViewBitmap2(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }


    //将像素转换为px
    public static int dip2pxXiding(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //将px转换为dp
    public static int px2dpXiding(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static StringBuffer formatNum(int num, Boolean b) {
        StringBuffer sb = new StringBuffer();
        BigDecimal b0 = new BigDecimal("100");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String unit = "";

        // 以百为单位处理
        if (b) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return sb.append("99+");
            }
            return sb.append(num);
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            formatNumStr = b3.toString();
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            unit = "万";

            formatNumStr = b3.divide(b1).toString();
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            unit = "亿";
            formatNumStr = b3.divide(b2).toString();

        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(unit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(unit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(unit);
                }
            }
        }
        if (sb.length() == 0)
            return sb.append("0");
        return sb;
    }


    /**
     * 判断本地已经安装好了指定的应用程序包
     *
     * @param packageNameTarget ：App 包名
     * @return 已安装时返回 true,不存在时返回 false
     */
    public static boolean appIsExist(String packageNameTarget, Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packageInfoList) {
            String packageNameSource = packageInfo.packageName;
            if (packageNameSource.equals(packageNameTarget)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(View scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度


//        for (int i = 0; i < scrollView.getChildCount(); i++) {
//            if (scrollView.getChildAt(i).getHeight() == 1300){
//                h += scrollView.getChildAt(i).getHeight();
//                Log.e("滚动值2：" , h + "");
//                scrollView.getChildAt(i).setBackgroundColor(
//                        Color.parseColor("#ffffff"));
//                break;
//            }
//        }

        // 创建对应大小的bitmap
        if (scrollView.getWidth() > 0 && scrollView.getHeight() > 0) {
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), scrollView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
        }


        return bitmap;
    }

    /**
     * 截取scrollview的屏幕
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

//    /**
//     * 压缩图片
//     * @param image
//     * @return
//     */
//    public static Bitmap compressImage(Bitmap image) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        int options = 100;
//        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//        while (baos.toByteArray().length / 1024 > 100) {
//            // 重置baos
//            baos.reset();
//            // 这里压缩options%，把压缩后的数据存放到baos中
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            // 每次都减少10
//            options -= 10;
//        }
//        // 把压缩后的数据baos存放到ByteArrayInputStream中
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
//        // 把ByteArrayInputStream数据生成图片
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
//        return bitmap;
//    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static String savePic(Context context, Bitmap b) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "wemall");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sign_" + System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 其次把文件插入到系统图库
//        String path = file.getAbsolutePath();
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return "已保存系统相册";
    }

    /**
     * 保存到sdcard
     *
     * @param b
     * @return
     */
    public static File shareFlie(Context context, Bitmap b) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "wemall");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sign_" + System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        return file;
    }

//    private ClipboardManager mClipboardManager;
//
//    /**
//     * 实现文本复制功能
//     *
//     * @param content 复制的文本
//     */
//    public static String copy(Context context, String content) {
//        if (!TextUtils.isEmpty(content)) {
////            // 得到剪贴板管理器
////            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
////            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
////            ClipData clipData = ClipData.newPlainText("Label", content);
////            // 把数据集设置（复制）到剪贴板
////            cmb.setPrimaryClip(clipData);
//            ClipboardManager  mClipboardManager = (ClipboardManager)  getSystemService(CLIPBOARD_SERVICE);
//
//
//            return "复制成功";
//        }
//
//        return "";
//    }
//
//
//    /**
//     * 获取系统剪贴板内容
//     */
//    public static String getClipContent(Context context) {
//        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//        if (manager != null) {
//            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
//                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
//                String addedTextString = String.valueOf(addedText);
//                if (!TextUtils.isEmpty(addedTextString)) {
//                    return addedTextString;
//                }
//            }
//        }
//        return "";
//    }

//    /**
//     * 清空剪贴板内容
//     */
//    public static void clearClipboard(Context context) {
//        ClipboardManager manager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//        if (manager != null) {
//            try {
//                manager.setPrimaryClip(manager.getPrimaryClip());
//                manager.setText(null);
//            } catch (Exception e) {
//                Utils.showToast(context, e.getMessage());
//            }
//        }
//    }


    public static String TodyDate() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    //缓存文件
    public static String getCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                if (context.getExternalCacheDir().getPath() != null) {
                    cachePath = context.getExternalCacheDir().getPath();
                }
            }
        } else {
            if (context.getCacheDir() != null) {
                if (context.getCacheDir().getPath() != null) {
                    cachePath = context.getCacheDir().getPath();
                }
            }
        }
        return cachePath;
    }

    //获取软件code
    public static int getAppCode(Context context) {
        //20190421 try
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //获取设备信息
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 如果只是用于程序中的格式化数值然后输出，那么这个方法还是挺方便的。
     * 应该是这样使用：System.out.println(String.format("%.2f", d));
     *
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }

//    /** 判断程序是否在后台运行 */
//    public static boolean isRunBackground(Context context) {
//        ActivityManager activityManager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
//                .getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.processName.equals(context.getPackageName())) {
//                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
//                    // 表明程序在后台运行
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return false;
//    }
//    /** 判断程序是否在前台运行（当前运行的程序） */
//    public boolean isRunForeground() {
//        ActivityManager activityManager = (ActivityManager) getApplicationContext()
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        String packageName = getApplicationContext().getPackageName();
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
//                .getRunningAppProcesses();
//        if (appProcesses == null)
//            return false;
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.processName.equals(packageName)
//                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                return true;// 程序运行在前台
//            }
//        }
//        return false;
//    }

    /**
     * 剪裁照片，并将剪裁之后的照片存在imageUri中，照片的名称问newhead.jpg,若提交更改，则将newhead.jpg重命名为head.jpg
     *
     * @param uri
     */
    public static void startImageAction(Uri uri, Activity activity) {


        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//
//        }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        intent.putExtra("return-data", false);
//        **
//         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
//         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
//         */
        //intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, 3);
    }

    public static Bitmap decodeUriBitmap(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 获取时间
     *
     * @return 格式化后的时间
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return format.format(new Date());
    }

    /**
     * 获取时间
     *
     * @return 格式化后的时间
     */
    public static String getTimestamp() {
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis() / 1000;
        return String.valueOf(timeStamp);
    }

    /**
     * 计算时间查
     *
     * @return 格式化后的时间
     */
    public static long getTimeDifference(String t1, String t2) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        long diff = 0;
        long days = 0;
        long hours = 0;
        long minutes = 0;
        try {
            Date d1 = df.parse(t1);
            Date d2 = df.parse(t2);
            diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
//            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
        } catch (Exception e) {

        }
        return minutes;
    }

    public static Point getScreenSize(@NonNull Context context) {
        Point screenSize = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            screenSize.set(context.getResources().getDisplayMetrics().widthPixels,
                    context.getResources().getDisplayMetrics().heightPixels);
            return screenSize;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(screenSize);
        } else {
            wm.getDefaultDisplay().getSize(screenSize);
        }
        return screenSize;
    }

    public static int dp2px(Context context, float dpVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    /**
     * 将播放进度的毫米数转换成时间格式 如 3000 --> 00:03
     *
     * @param progress
     * @return
     */
    public static String formatTimeFromProgress(int progress) {
        // 总的秒数
        int msecTotal = progress / 1000;
        int min = msecTotal / 60;
        int msec = msecTotal % 60;
        String minStr = min < 10 ? "0" + min : "" + min;
        String msecStr = msec < 10 ? "0" + msec : "" + msec;
        return minStr + ":" + msecStr;
    }

    /**
     * @param image      需要模糊的图片
     * @param blurRadius 模糊的半径（1-25之间）
     * @return 模糊处理后的Bitmap
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius, int outWidth, int outHeight) {
        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, outWidth, outHeight, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        // 设置渲染的模糊程度, 25f是最大模糊度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurScript.setRadius(blurRadius);
        }
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    //CycleTimes动画重复的次数
    public static Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public static int getScreenWidth(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 改变弹窗背景颜色
     */
    public static void darkenBackground(Activity activity, Float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 禁止Edittext弹出软件盘，光标依然正常显示。
     */
    public static void disableShowSoftInput(EditText editText) {
        Class<EditText> cls = EditText.class;
        Method method;
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            method.setAccessible(true);
            method.invoke(editText, false);
        } catch (Exception e) {
            // TODO: 2018/8/27 处理错误
        }
    }

    /**
     * 转换.00
     */
    public static String DecimalFormatStr(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.parseDouble(str));
    }

    //显示键盘
    public static void showInputMethod(Context context) {
        //自动弹出键盘
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //强制隐藏Android输入法窗口
        // inputManager.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }

    public static final boolean isTest = true;
    public static final String TAG = "20200622";

    public static void LogI(String msg) {
        if (isTest)
            Log.i(TAG, msg);
    }

    public static void LogD(String msg) {
        if (isTest)
            Log.d(TAG, msg);
    }

    public static void LogE(String msg) {
        if (isTest)
            Log.e(TAG, msg);
    }

    public static boolean isOnline(Context context) {
        return Utils.NO_NETWORK_STATE != Utils.isNetworkAvailable(context);
    }


    private static long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;

    public static boolean checkClickTime() {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            // do something
            mLastClickTime = nowTime;
            return true;
        } else return false;
    }

    /**
     * 判断我们拉起（跳转）的第三方APP是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    public static boolean checkPackInfo(Context context, String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static int checkKeyStatus() {
        if (ConfigValue.DATA_KEY == null)
            return 0;
        if (TextUtils.isEmpty(ConfigValue.DATA_KEY))
            return 2;
        return 1;

    }


    /**
     * 判断是否是浮点型或者Double型
     */
    public static boolean isDoubleOrFloat(String str) {
        if (str == null || TextUtils.isEmpty(str))
            return false;
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否是数字
     */
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        if (str.equals(""))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean CompareToModel(Object objOne, Object objTwo) {
        if (objOne == null || objTwo == null)
            return false;
        try {
            Gson gson = new Gson();
            String strOne = gson.toJson(objOne);
            String strTwo = gson.toJson(objTwo);
            return strOne.compareTo(strTwo) == 0;
        } catch (Exception e) {
            return false;
        }
    }


    public static String copy(String txtStr, Activity activity) {
        if (txtStr == null)
            return "复制失败";

        ClipboardManager mClipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        String copyStr = getClipData(mClipboardManager);
        if (!TextUtils.isEmpty(copyStr)) {
            if (copyStr.compareTo(txtStr) == 0)
                return "您已复制过了";
        }

        ClipData clipData = ClipData.newPlainText("copy from demo", txtStr);
        mClipboardManager.setPrimaryClip(clipData);
        return "复制成功";
    }

    private static String getClipData(ClipboardManager mClipboardManager) {
        if (mClipboardManager.hasPrimaryClip() && mClipboardManager.getPrimaryClipDescription() != null) {
            if (mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) && mClipboardManager.getPrimaryClip() != null) {
                ClipData clipData = mClipboardManager.getPrimaryClip();
                if (clipData == null)
                    return "";
                ClipData.Item item = clipData.getItemAt(0);
                CharSequence text = item.getText();
                if (text != null)
                    return text.toString().trim();
            }
        }
        return "";
    }

    //文字颜色
    public static SpannableString getColorString(String textAll, String textKey, String color) {
        SpannableString spannableString = new SpannableString(textAll);
        Pattern pattern = Pattern.compile(Pattern.quote(textKey));
        Matcher matcher = pattern.matcher(textAll);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    //文字大小
    public static SpannableString changTvSize(String value, int position, float size) {
        SpannableString spannableString = new SpannableString(value);
        spannableString.setSpan(new RelativeSizeSpan(size), position, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    /**
     * 并用分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public static String[] timeStamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] list = times.split("[年月日时分秒]");
        return list;
    }

    //时间戳的差转成天
    public static int TimeStampToDay(int timeStamp, int current) {
        return (timeStamp - current) / (60 * 60 * 24);
    }

    //时间戳的差转成时
    public static int TimeStampToHour(int timeStamp, int current) {
        return ((timeStamp - current) % (60 * 60 * 24)) / (60 * 60);
    }

    //时间戳的差转成分
    public static int TimeStampToMinute(int timeStamp, int current) {
        return ((timeStamp - current) % (60 * 60 * 24)) / 60;
    }

    //时间戳的差转成秒
    public static int TimeStampToSecond(int timeStamp, int current) {
        return (timeStamp - current) % (60 * 60 * 24);
    }

    //秒转时分秒
    public static String SecondToDate(Integer date) {
        int h = date / 3600;
        int m = (date % 3600) / 60;
        int s = (date % 3600) % 60;
        return h + "小时" + m + "分" + s + "秒";
    }


    public static String BigDecimalDouble(Double value, int scale, int mode) {
        BigDecimal bigDecimal = new BigDecimal(value);
        BigDecimal afterDecimal = bigDecimal.setScale(scale, mode);
        double after = afterDecimal.doubleValue();
        String type = "0.";
        while (scale > 0) {
            type = type + "0";
            scale--;
        }
        return new DecimalFormat(type).format(after);
    }

}
