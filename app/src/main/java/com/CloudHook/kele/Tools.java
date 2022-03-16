package com.CloudHook.kele;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import de.robv.android.xposed.XposedBridge;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.ACTIVITY_SERVICE;

public class Tools {
    /**
     * Activity是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isOnForground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        if (appProcessInfoList == null) {
            return false;
        }

        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo processInfo : appProcessInfoList) {
            if (processInfo.processName.equals(packageName) && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
public static void Http_proxy_s(final String ip,final String po,final String username,final String userpass){
    Authenticator.setDefault(new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            if (getRequestorType() == RequestorType.PROXY) {
                String prot = getRequestingProtocol().toLowerCase();
                String host = System.setProperty("http.proxyHost", ip);
                String port =   System.setProperty("http.proxyPort", po);
                System.setProperty("proxySet", "true");
                String user = System.getProperty(prot + ".proxyUser", username);
                String password = System.getProperty(prot + ".proxyPassword", userpass);
                if (getRequestingHost().equalsIgnoreCase(host)) {
                    if (Integer.parseInt(port) == getRequestingPort()) {
                        // Seems to be OK.
                        return new PasswordAuthentication(user, password.toCharArray());
                    }
                }
            }
            return null;
        }
    });
}
public static void Http_proxy(final String ip,final String po){

    System.setProperty("http.proxyHost", ip);
    System.setProperty("http.proxyPort", po);
    System.setProperty("https.proxyHost", ip);
    System.setProperty("https.proxyPort", po);
    System.setProperty("proxySet", "true");
}
    public static boolean isSystemApplication(Context context, String packageName) {
        PackageManager mPackageManager = context.getPackageManager();
        try {
            final PackageInfo packageInfo = mPackageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    //反射拿上下文
    public static Application Hook_con() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            return (Application) cls.getMethod("currentApplication", new Class[0]).invoke(cls, new Object[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        return null;
    }

    //复制文本到剪贴板
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text", text));
    }

    static String okHttpGet_result = "";

    public static String okHttpGet(String get) {
        String url = get;

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                XposedBridge.log("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //mTextView.setText(response.body().string());
                XposedBridge.log("请求成功");
                okHttpGet_result = response.body().string();
            }
        });
        return okHttpGet_result;
    }

    //Url替换get参数
    public static String urlreplaces(String url, String key, String value) {

        url = url.replaceAll("(" + key + "=[^&]*)", key + "=" + value);

        return url;
    }


}
