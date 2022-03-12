package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_jzkj {
/*
支持芥子空间

原理拦截okhttp
*/

    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {


        if (lppararm.packageName.equals("com.aigz.cloudgame") || lppararm.packageName.equals("com.aigz.cloudgame.cat")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;


                    hookclass = XposedHelpers.findClassIfExists("okhttp3.Request$Builder", cl);

                    hookclass2 = XposedHelpers.findClassIfExists("com.aigz.version.VersionDialog", cl);

                    hookclass3 = XposedHelpers.findClassIfExists("androidx.fragment.app.FragmentManager", cl);


                    if (hookclass != null) {

                        XposedHelpers.findAndHookMethod(hookclass, "url", String.class, new XC_MethodHook() {
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

                    if (hookclass2 != null && hookclass3 != null) {
                        XposedHelpers.findAndHookMethod(hookclass2, "show", hookclass3, String.class,
                                new XC_MethodReplacement() {


                                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {

                                        return null;

                                    }
                                });
                    }


                }


            });
        }

    }
}