package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_huluxia {
/*
支持葫芦侠/葫芦侠三楼

原理拦截okhttp
*/


    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {

        if (lppararm.packageName.equals("com.huati")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    try {
                        hookclass = XposedHelpers.findClassIfExists("okhttp3.aa$a", cl);

                        hookclass2 = XposedHelpers.findClassIfExists("com.huluxia.data.e", cl);
                    } catch (Exception e) {

                    }
                    if (hookclass != null) {
                        XposedHelpers.findAndHookMethod(hookclass, "ri", String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);

                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                if (param.args[0] != null) {
                                    String url = (String) param.args[0];
                                    if (url.contains("/user/signin/ANDROID") & url.contains("cat_id")) {
                                        for (int i = 1; i < 200; i++) {
                                            Tools.okHttpGet(Tools.urlreplaces(url, "cat_id", String.valueOf(i)));

                                        }
                                    }

                                }

                            }
                        });
                    }

                    if (hookclass2 != null) {
                        XposedHelpers.findAndHookMethod(hookclass2, "getVersionCode", new XC_MethodHook() {
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


            });
        }
        if (lppararm.packageName.equals("com.huluxia.gametools")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    try {

                        hookclass = XposedHelpers.findClassIfExists("okhttp3.aa$a", cl);

                        hookclass2 = XposedHelpers.findClassIfExists("com.huluxia.data.e", cl);
                    } catch (Exception e) {

                    }
                    if (hookclass != null) {
                        XposedHelpers.findAndHookMethod(hookclass, "rM", String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);

                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                if (param.args[0] != null) {
                                    String url = (String) param.args[0];
                                    if (url.contains("/user/signin/ANDROID") & url.contains("cat_id")) {
                                        for (int i = 1; i < 200; i++) {
                                            Tools.okHttpGet(Tools.urlreplaces(url, "cat_id", String.valueOf(i)));

                                        }
                                    }

                                }

                            }
                        });
                    }
                    if (hookclass2 != null) {
                        XposedHelpers.findAndHookMethod(hookclass2, "getVersionCode", new XC_MethodHook() {
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


            });
        }
    }
}