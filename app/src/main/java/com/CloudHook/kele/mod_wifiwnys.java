package com.CloudHook.kele;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.CloudHook.kele.Tools.Hook_con;

public class mod_wifiwnys {

    public static void startHook(final XC_LoadPackage.LoadPackageParam lppararm) throws Throwable {

//wifi密码显密
        if (lppararm.packageName.equals("com.snda.wifilocating")) {

            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {

                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                    Class<?> hookclass = null;
                    Class<?> hookclass2 = null;
                    Class<?> hookclass3 = null;
                    Class<?> hookclass4 = null;
                    try {
                        hookclass = cl.loadClass("com.wifi.connect.model.AccessPoint");

                        hookclass2 =XposedHelpers.findClassIfExists("com.vip.common.b", cl);
                        hookclass3 =XposedHelpers.findClassIfExists("com.lantern.core.t", cl);
                        hookclass4 = cl.loadClass("d.z.a.e");


                    } catch (Exception e) {

                    }
                    HookStart.Hook_activity(cl,"com.lantern.launcher.ui.MainActivity","com.lantern.launcher.ui.MainActivityICS");
                    XposedHelpers.findAndHookConstructor(java.net.URL.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            String[] ads = {"feeds.sec", "wifi188.com"};
                            if (!param.args[0].toString().isEmpty()) {
                                String Curl = param.args[0].toString();
//
                                if (!Curl.contains("51y5.net")) {
                                    param.args[0] = "http://0.0.0.0/";
                                    return;
                                }
                                //XposedBridge.log("WIFI钥匙请求2:" + param.args[0]);
                            }
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);


                        }
                    });
                    XposedHelpers.findAndHookMethod(hookclass, "getPassword", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Context context = Hook_con();
                            if (!param.getResult().toString().isEmpty()) {
                                Tools.copyToClipboard(context, param.getResult().toString());
                                Toast.makeText(context, "天帝VIP提醒您:\n当前wifi密码:" + param.getResult() + "\n已复制到剪贴板", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "天帝VIP提醒您:\n当前WiFi已解锁过,无法获取蓝钥匙密码!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    if(hookclass2!=null){
                        XposedHelpers.findAndHookMethod(hookclass2, "d", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);

                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                param.setResult(2);

                            }
                        });
                    }
                    if(hookclass3!=null){
                        XposedHelpers.findAndHookMethod(hookclass3, "U", new XC_MethodHook() {
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
                    if(hookclass4!=null){
                        XposedHelpers.findAndHookMethod(hookclass4, "c", new XC_MethodHook() {
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
                }

            });






        }

    }
}