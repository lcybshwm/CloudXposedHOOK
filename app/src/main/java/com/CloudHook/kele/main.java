package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {


        if (lppararm.packageName.equals("com.hero.sm.android.hero")) {
            XposedBridge.log("start hook");
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    try {
                        hookclass = cl.loadClass("com.rF");
                        //    hookclass2 = cl.loadClass("com.bat.base.utils.NotAllowedChecker");

                    } catch (Exception e) {

                        return;
                    }
                    XposedHelpers.findAndHookMethod(hookclass, "b",
                            new XC_MethodReplacement() {


                                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                    XposedBridge.log("已重写");
                                    return null;

                                }
                            });
//                    XposedHelpers.findAndHookMethod(hookclass, "getDeviceId", new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod (MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//
//                        }
//
//                        @Override
//                        protected void afterHookedMethod (MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//
//                            param.setResult("20210525223943d060b95587fdda9c44c0b3d25ed65e940102ecc9ba83bf33");
//
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod(hookclass, "getNoVipBtn", new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod (MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//
//                        }
//
//                        @Override
//                        protected void afterHookedMethod (MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//
//                            param.setResult(0);
//
//                        }
//                    });
//                    XposedHelpers.findAndHookMethod(hookclass2, "isEmulatorExt",Context.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod (MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//
//                        }
//
//                        @Override
//                        protected void afterHookedMethod (MethodHookParam param) throws Throwable {
//                            super.afterHookedMethod(param);
//
//                            param.setResult(false);
//
//                        }
//                    });

                }


            });
        }


    }
}

