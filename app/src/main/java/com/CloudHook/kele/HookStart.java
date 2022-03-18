package com.CloudHook.kele;

import static com.CloudHook.kele.HookKt.readyHook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.widget.Toast;

import com.CloudHook.myhook.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HookStart implements IXposedHookLoadPackage {


    public static String getnetinfo_url = "https://tiandi.lovetom.top/app/xposedinfo.php";
    public static String getnetinfo_url_text = "";
    public static String Versiontype = "正常";

    private XSharedPreferences sp;
    private XSharedPreferences.Editor editor;
    public XC_LoadPackage.LoadPackageParam hookstratr = null;
    public String cur_packageName = "com.CloudHook.myhook";
    public static String curl_bbh = "";
    public static String curl_info = "";
    public static String http_GET(String urls){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //get的方式提交就是url拼接的方式
        String path = "";
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            //获得结果码
            int responseCode = connection.getResponseCode();
            if(responseCode ==200){
                //请求成功 获得返回的流
                InputStream is = connection.getInputStream();
                StringBuilder sb = new StringBuilder();
                String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String str = sb.toString();
                return str;

            }else {
                //请求失败
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
public static void net_specialHook2(final XC_LoadPackage.LoadPackageParam lppararm){
    String xposed_info= http_GET(getnetinfo_url);
    XposedBridge.log("test :"+xposed_info);
   String config = new String(Base64.decode(xposed_info, Base64.DEFAULT));
//    XposedBridge.log("报告长官 " + loadPackageParam!!.packageName + " 配置开始运行Hook!")

    readyHook(config, lppararm.classLoader, lppararm.packageName);

}
    public void getnetinfo(final XC_LoadPackage.LoadPackageParam lppararm) {
        if (lppararm != null) {
            if(lppararm.packageName.contains("com.miui")){
                return;
            }
        }
        XSharedPreferences xsp = new XSharedPreferences(lppararm.packageName, BuildConfig.APPLICATION_ID + "_appinfo");
        xsp.makeWorldReadable();
        curl_info = xsp.getString("xposedinfo", "");
        XposedBridge.log("当前包名:" + lppararm.packageName + "获取到的配置信息:" + curl_info);
        if (curl_info.equals("")) {
            XposedBridge.log("当前流程: " + lppararm.packageName + " 本地配置为空,先联网请求...");


//            curl_info = xsp.getString("xposedinfo", xposed_info);
//            HookKt.specialHook(lppararm);
            net_specialHook2(lppararm);
        HookKt.net_specialHook(lppararm);
        } else {
            XposedBridge.log("当前流程: " + lppararm.packageName + " 本地配置存在,先本地缓存...");
            HookKt.specialHook(lppararm);
        }


    }


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {

        getnetinfo(lppararm);

        mod_tiktok.startHook(lppararm);
        mod_bingxiang.startHook(lppararm);
        mod_wifiwnys.startHook(lppararm);
        mod_huluxia.startHook(lppararm);
        mod_jzkj.startHook(lppararm);
        mod_vmospro.startHook(lppararm);
        mod_7723game.startHook(lppararm);
        mod_systemapppack.startHook(lppararm);


        if (lppararm.packageName.equals("com.miui.securitycenter")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    try {
                        hookclass = cl.loadClass("com.miui.permcenter.privacymanager.InterceptPermissionFragment");

//                        hookclass2 = cl.loadClass("com.miui.permcenter.privacymanager.j.d");
                        hookclass2 = XposedHelpers.findClassIfExists("com.miui.permcenter.privacymanager.j.d", cl);
                    } catch (Exception e) {


                    }
//
                    XposedHelpers.findAndHookMethod(hookclass, "g", int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            param.args[0] = 0;

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);


                        }
                    });
                    if (hookclass2 != null) {
                        XposedHelpers.findAndHookMethod(hookclass2, "b", int.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                param.args[0] = 1;
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);


                            }
                        });
                    }

//

                }


            });
        }

//        if (lppararm.packageName.equals("com.android.settings")) {
//
//            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
//
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
//                    Class<?> hookclass = null;
//                    Class<?> hookclass2 = null;
//                    try {
//                        hookclass = cl.loadClass("com.android.settings.MiuiMasterClearApplyActivity");
//                        //    hookclass2 = cl.loadClass("com.bat.base.utils.NotAllowedChecker");
//
//                    } catch (Exception e) {
//
//                    }
//

//
//
//                }
//
//
//            });
//        }


    }
public static  void  HTTP_Proxy_Start(Class cl,final String ip, final String port, final String user, final String pass){

    XposedHelpers.findAndHookMethod(cl, "onCreate", Bundle.class, new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
//            Tools.Http_proxy_s(ip,port,user,pass);
            Tools.Http_proxy(ip,port);
            XposedBridge.log("IP="+ip+"端口="+port);
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);


        }
    });
}
    public static void Hook_activity(ClassLoader classLoader, final String curl_activity, final String value) {

        XposedBridge.log("跳转已开始执行...");
        Class<?> hookclass = XposedHelpers.findClassIfExists(curl_activity, classLoader);
        if (hookclass == null) {
            XposedBridge.log("无法跳转Activity");
            return;
        } else {
            XposedBridge.log("跳转继续执行...");
        }

        Class<?> hookclass1 = null;
        try {
            hookclass1 = classLoader.loadClass(curl_activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        XposedBridge.log("当前获取的:" + hookclass.toString());
        //    hookclass2 = cl.loadClass("com.bat.base.utils.NotAllowedChecker");


        XposedHelpers.findAndHookMethod(hookclass1, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("onCreate运行前...");
//                Context context= (Context) param.thisObject;
//                String filepath=context.getPackageManager().getApplicationInfo(context.getApplicationInfo().packageName,0).nativeLibraryDir;
//               String jiagu360=filepath+"libjiagu.so";
//             XposedBridge.log("So文件路径"+filepath);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Activity activity = (Activity) param.thisObject;
                XposedBridge.log("onCreate运行后...");
                if (activity != null) {

                    Intent intent = new Intent();
                    intent.setClassName(activity, value);
                    activity.startActivity(intent);
                    XposedBridge.log("跳转执行完成!");
                    activity.finish();

                } else {
                    XposedBridge.log("activity为空!");
                }


            }
        });


    }
    public static  void Hook_ActivityFIELD(ClassLoader classLoader, final String curl_activity, final String value) {

        XposedBridge.log("跳转已开始执行...");
        Class<?> hookclass = XposedHelpers.findClassIfExists(curl_activity, classLoader);
        if (hookclass == null) {
            XposedBridge.log("class为空");
            return;
        } else {
            XposedBridge.log("继续执行...");
        }

        Class<?> hookclass1 = null;
        try {
            hookclass1 = classLoader.loadClass(curl_activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        XposedBridge.log("当前获取的:" + hookclass.toString());
        //    hookclass2 = cl.loadClass("com.bat.base.utils.NotAllowedChecker");


        XposedHelpers.findAndHookMethod(hookclass1, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                XposedBridge.log("onCreate运行前...");

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Activity activity = (Activity) param.thisObject;
                XposedBridge.log("onCreate运行后...");

//                Toast.makeText((Application) param.thisObject, "此界面已禁止进入", Toast.LENGTH_SHORT).show();

                activity.finish();




            }
        });


    }

    public static void Update_local_configuration(final Context context) {
        XposedBridge.log("开始更新联网配置 Context:" + context);
        String url = getnetinfo_url;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();

                    String result = response.body().string();
                    SharedPreferences app2 = context.getSharedPreferences(BuildConfig.APPLICATION_ID + "_appinfo", 0);
                    SharedPreferences.Editor edit = app2.edit();
                    //根据要保存的数据的类型，调用对应的put方法,
                    //以键值对的形式添加新值。
                    //根据要保存的数据的类型，调用对应的put方法,
                    //以键值对的形式添加新值。
                    edit.putString("xposedinfo", result);
                    edit.commit();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
public static void Activate_hook(final XC_LoadPackage.LoadPackageParam lppararm){
        if(!lppararm.packageName.equals(BuildConfig.APPLICATION_ID)){
            return;
        }
        Class hookclass=XposedHelpers.findClassIfExists("com.example.kele.Main2Activity", lppararm.classLoader);
        if(hookclass==null){
            return;
        }
    XposedHelpers.findAndHookMethod(hookclass, "is_activate",  new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);

        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            param.setResult(true);

        }
    });
}
}





