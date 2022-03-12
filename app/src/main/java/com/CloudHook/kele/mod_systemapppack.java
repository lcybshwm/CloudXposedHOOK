package com.CloudHook.kele;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_systemapppack {
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {
if(lppararm.packageName.equals("com.miui.packageinstaller")){
    XposedHelpers.findAndHookConstructor(java.net.URL.class, String.class, new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);

            if(param.args[0].toString().contains("game/interceptcheck")){
                param.args[0]="https://api.tiandivip.cc/hack/xposed/mi_install_tip.php";
            }
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);


        }
    });
}


    }
}
