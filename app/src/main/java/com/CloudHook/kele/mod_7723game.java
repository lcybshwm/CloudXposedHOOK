package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_7723game {
  
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {


        if (lppararm.packageName.equals("com.upgadata.up7723")) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> mainActivity = null;

                    try {
                        mainActivity = cl.loadClass("com.upgadata.up7723.main.activity.SplashActivity");


                    } catch (Exception e) {


                    }
                    XposedHelpers.findAndHookMethod(mainActivity, "showAd",  new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                        XposedHelpers.callMethod(param.thisObject, "toMain");
                        }

                    });


                }


            });

        }

    }
}
