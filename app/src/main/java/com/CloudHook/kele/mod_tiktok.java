package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_tiktok {
  
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {


        if (lppararm.packageName.equals("com.ss.android.ugc.trill")) {




                    XposedHelpers.findAndHookMethod(android.telephony.TelephonyManager.class, "getSimState",  new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                            param.setResult(0);
                        }

                    });







        }

    }
}
