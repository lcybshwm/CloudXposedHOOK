package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_bingxiang {
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {
if(lppararm.packageName.equals("com.catchingnow.icebox")){

    XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            ClassLoader cl = ((Context) param.args[0]).getClassLoader();
            Class hookclass=XposedHelpers.findClassIfExists("com.catchingnow.icebox.provider.m",cl);
            if(hookclass==null){
                return;
            }

            XposedHelpers.findAndHookMethod(hookclass, "c", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(true);
XposedBridge.log("冰箱Hook成功");
                }
            });

        }


    });
}


    }
}
