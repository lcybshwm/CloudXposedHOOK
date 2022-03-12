package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_vmospro {
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {

//wifi密码显密
        if (lppararm.packageName.equals("com.vmos.pro")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    try {
                        hookclass = cl.loadClass("com.vmos.pro.bean.UserBean");
                       hookclass2 = cl.loadClass("com.vmos.pro.account.AccountHelper");

                    } catch (Exception e) {

                    }

                    XposedHelpers.findAndHookMethod(hookclass, "isMember",  new XC_MethodHook() {
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
                    XposedHelpers.findAndHookMethod(hookclass2, "isForeverVip",  new XC_MethodHook() {
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
                    XposedHelpers.findAndHookMethod(hookclass2, "notLogin",  new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            param.setResult(false);

                        }
                    });
                }


            });
        }

    }
}
