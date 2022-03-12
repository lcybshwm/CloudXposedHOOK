package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class mod_cckjgl {
    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {

        if (lppararm.packageName.equals("moe.shizuku.redirectstorage")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;

                    hookclass = XposedHelpers.findClassIfExists("moe.shizuku.redirectstorage.e91", cl);
//                    hookclass2 = XposedHelpers.findClassIfExists("moe.shizuku.redirectstorage.bt1", cl);
                    if (hookclass == null ) {
                        return;
                    }
                    XposedHelpers.findAndHookMethod(Class.class,"getDeclaredMethod",String.class,Class[].class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            if(param.args[0].equals("只是路过的魔法少女")){
                                param.args[0]="操你妈,搞我心态";
                            }

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);


                        }

                    });
                    XposedHelpers.findAndHookMethod(hookclass, "只是路过的魔法少女", Boolean.class, new XC_MethodHook() {
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
                    XposedHelpers.findAndHookMethod(hookclass, "尾巴捏捏", Boolean.class, int.class, new XC_MethodHook() {
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
                    XposedHelpers.findAndHookMethod(hookclass, "没收尾巴球", new XC_MethodHook() {
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


            });


        }


    }


}
