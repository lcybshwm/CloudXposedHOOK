package com.CloudHook.kele;

import android.os.Bundle;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_testip {
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {
if(lppararm.packageName.equals("com.example.emos")){

//            Tools.Http_proxy_s(ip,port,user,pass);

    final Class main=lppararm.classLoader.loadClass("com.example.emos2.MainActivity");
    XposedHelpers.findAndHookConstructor(System.class,   new XC_MethodHook() {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            String ip="165.225.204.31";
            String port="10089";
            XposedHelpers.callMethod(param.thisObject,"setProperty","http.proxyHost",ip);
            XposedHelpers.callMethod(param.thisObject,"setProperty","http.proxyPort",port);
            XposedHelpers.callMethod(param.thisObject,"setProperty","https.proxyHost",ip);
            XposedHelpers.callMethod(param.thisObject,"setProperty","https.proxyPort",port);
            XposedHelpers.callMethod(param.thisObject,"setProperty","proxySet", "true");
         Object jg=   XposedHelpers.callMethod(param.thisObject,"getProperty","http.proxyHost");
            Object jg2=    XposedHelpers.callMethod(param.thisObject,"getProperty","http.proxyPort");
            Object jg3=    XposedHelpers.callMethod(param.thisObject,"getProperty","https.proxyHost");
            Object jg4=     XposedHelpers.callMethod(param.thisObject,"getProperty","https.proxyPort");
            XposedBridge.log(jg+"\n"+jg2+"\n"+jg3+"\n"+jg4);
        }
    });
    XposedHelpers.findAndHookMethod(main, "onCreate", Bundle.class, new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);

            String ip="165.225.204.31";
            String port="10089";
            System.setProperty("http.proxyHost", ip);
            System.setProperty("http.proxyPort", port);
            System.setProperty("https.proxyHost", ip);
            System.setProperty("https.proxyPort", port);
            System.setProperty("proxySet", "true");

        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            String ip="165.225.204.31";
            String port="10089";


//            Tools.Http_proxy(ip,port);
            Object jg= XposedHelpers.callStaticMethod(main,"http_GET","https://api.ipify.org/?format=jsonp&callback=?");
            XposedBridge.log("IP="+ip+"端口="+port+jg);

        }
    });

}


    }
}
