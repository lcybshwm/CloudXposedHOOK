package com.CloudHook.kele

import android.app.Application
import android.content.Context
import android.util.Base64
import com.CloudHook.kele.HookStart.Hook_ActivityFIELD
import com.CloudHook.kele.HookStart.Hook_activity
import com.CloudHook.myhook.BuildConfig
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


var xposedinfo = "https://tiandi.lovetom.top/app/xposedinfo.php"


private var config = "错误"

var urlString = "";
fun specialHook(loadPackageParam: XC_LoadPackage.LoadPackageParam?) {
    XposedHelpers.findAndHookMethod(
        Application::class.java,
        "attach",
        Context::class.java,
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                val context = param!!.args[0] as Context

                val sxw = param.args[0] as Context
                if (loadPackageParam != null) {
                    if (Tools.isSystemApplication(sxw, loadPackageParam.packageName)) {
                        return;
                    }
                }
                val app2 = sxw.getSharedPreferences(BuildConfig.APPLICATION_ID + "_appinfo", 0)
                var loc_config = app2.getString("xposedinfo", "")
                if (!loc_config.isNullOrEmpty()) {
                    config = String(Base64.decode(loc_config, Base64.DEFAULT));
                    XposedBridge.log("报告长官 " + loadPackageParam!!.packageName + " 配置开始运行Hook!")

                    readyHook(config, context.classLoader, loadPackageParam!!.packageName)
                    HookStart.Update_local_configuration(context);
                } else {

                    XposedBridge.log("报告长官 " + loadPackageParam!!.packageName + " 配置为空!")

                }


            }

        }
    )
}

fun net_specialHook(loadPackageParam: XC_LoadPackage.LoadPackageParam?) {



    XposedHelpers.findAndHookMethod(
        Application::class.java,
        "attach",
        Context::class.java,
        object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                val context = param!!.args[0] as Context



                if (Tools.isSystemApplication(context, loadPackageParam!!.packageName)) {
                    XposedBridge.log("包名:" + loadPackageParam!!.packageName + "因为判断是系统应用,停止执行Hook流程")
                    return;
                }
//                    else if (!Tools.isOnForground(context)) {
//                        XposedBridge.log("包名:" + loadPackageParam.packageName + "因为判断应用未在前台,停止执行")
//                        return;
//                    }


                XposedBridge.log("hook已进入联网方法开头")

//                var config = app2.getString("xposedinfo", "")
//                if(!config.isNullOrEmpty()){
//                    config= Base64.decode(config,Base64.DEFAULT).toString()
//                    XposedBridge.log("报告长官,配置开始运行Hook!")
//                    readyHook(config, context.classLoader, loadPackageParam!!.packageName)
//                }else{
//
//
//                    XposedBridge.log("报告长官,配置为空!")
//                }

                urlString = xposedinfo;
                val okHttpClient = OkHttpClient()
                val request: Request = Request.Builder()
                    .url(urlString)
                    .build()
                val call = okHttpClient.newCall(request)
                Thread {
                    try {
                        val response = call.execute()
                        config = response.body!!.string()
                        val app2 = context.getSharedPreferences(BuildConfig.APPLICATION_ID + "_appinfo", 0)
                        val edit = app2.edit()
                        //根据要保存的数据的类型，调用对应的put方法,
                        //以键值对的形式添加新值。
                        //根据要保存的数据的类型，调用对应的put方法,
                        //以键值对的形式添加新值。
                        edit.putString("xposedinfo", config)
                        edit.commit()
                        config = String(Base64.decode(config, Base64.DEFAULT))
                        if (config.contains("[")) {
                            XposedBridge.log("联网获取配置成功,开始载入")
                            readyHook(config, context.classLoader, loadPackageParam!!.packageName)
                        } else {
                            XposedBridge.log("警告,联网获取配置失败,请检查!")
                        }
//                        config = response.body!!.string();

                        //获取Editor对象

                        //  Log.d(TAG, "run: " + response.body()!!.string())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (config == "错误") {
                        //  "稍后再试，网络错误或者其他错误".log()
                        XposedBridge.log("稍后再试，网络错误或者其他错误")
                    }
                }.start()


            }

        }
    )
}

fun readyHook(config: String, classLoader: ClassLoader, packageName: String) {


    //如果配置不包含就不转json
    if (!config.contains(packageName)) {
        return;
    }
    // 如果以‘[’开头说明是多个hook配置（粗略判断）
    if (config.startsWith("[")) {

        val jsonArray = JSONArray(config)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getString("packageName") == packageName) {

                startHook(jsonObject, classLoader)
            }
        }


    } else {
        val jsonObject = JSONObject(config)
        if (jsonObject.getString("packageName") == packageName) {
            startHook(jsonObject, classLoader)
        }
    }

}


fun startHook(jsonObject: JSONObject, classLoader: ClassLoader) {
    XposedBridge.log(jsonObject.toString()+ "本地配置:"+"\n")
    val jsonArray = jsonObject.getJSONArray("config")
    val canuse = jsonObject.getBoolean("canUse")
    if (!canuse) {
        return;
    }
    for (i in 0 until jsonArray.length()) {
        val methodJsonObject = jsonArray.getJSONObject(i)
        val mode = methodJsonObject.getInt("mode")

        val className = methodJsonObject.getString("className")
        val values = methodJsonObject.getString("resultValues")

        if (mode == Constant.Hook_ACTIVITY) {
            XposedBridge.log("已获取到跳转函数,当前className:" + className + "当前resultValues:" + values)
            Hook_activity(classLoader, className, values);
        } else if (mode == Constant.HOOK_STATIC_FIELD) {
            val fieldName = methodJsonObject.getString("fieldName")
            val valueType = methodJsonObject.getString("fieldType")
            hookStaticField(className, classLoader, fieldName, values, valueType)
        } else if (mode == Constant.HOOK_ActivityFIELD) {
            XposedBridge.log("已获取到跳转函数,当前className:" + className + "当前resultValues:" + values)
            Hook_ActivityFIELD(classLoader, className, values);
        } else if (mode == Constant.HOOK_PARAMRET) {
            XposedBridge.log("已获取到跳转函数,当前className:" + className + "当前resultValues:" + values)
            val methodName = methodJsonObject.getString("methodName")
            val params = methodJsonObject.getString("params")
            val judge = methodJsonObject.getString("if")
            hook2(className, classLoader, methodName, values, params, mode, judge)
        }else if(mode == Constant.HTTP_Proxy){
            val ip=methodJsonObject.getString("ip")
            val port=methodJsonObject.getString("port")
            val user=methodJsonObject.getString("user")
            val pass=methodJsonObject.getString("pass")
            HookStart.HTTP_Proxy_Start(classLoader.loadClass(className),ip,port,user,pass);
//            if(user.isEmpty()){
//                Tools.Http_proxy(ip,port);
//                return;
//            }
//            Tools.Http_proxy_s(ip,port,user,pass);
        } else {
            val methodName = methodJsonObject.getString("methodName")
            val params = methodJsonObject.getString("params")
            hook(className, classLoader, methodName, values, params, mode)
        }
    }
}

fun hookStaticField(
    className: String,
    classLoader: ClassLoader,
    fieldName: String,
    values: String,
    valueType: String
) {
    val clazz: Class<*> = XposedHelpers.findClass(className, classLoader)
    when (valueType) {
        "byte" -> XposedHelpers.setStaticByteField(clazz, fieldName, values.toByte())
        "short" -> XposedHelpers.setStaticShortField(clazz, fieldName, values.toShort())
        "int" -> XposedHelpers.setStaticIntField(clazz, fieldName, values.toInt())
        "long" -> XposedHelpers.setStaticLongField(clazz, fieldName, values.toLong())
        "float" -> XposedHelpers.setStaticFloatField(clazz, fieldName, values.toFloat())
        "double" -> XposedHelpers.setStaticDoubleField(clazz, fieldName, values.toDouble())
        "boolean" -> XposedHelpers.setStaticBooleanField(clazz, fieldName, values.toBoolean())
        "null" -> XposedHelpers.setStaticObjectField(clazz, fieldName, null)
        "String" ->XposedHelpers.setStaticObjectField(clazz, fieldName, values.toString())
    }

}

fun hook2(
    className: String,
    classLoader: ClassLoader,
    methodName: String, values: String,
    params: String, mode: Int, pd: String
) {
    val methodParams = params.split(",")
    val realSize = if (params == "") 0 else methodParams.size
    val obj = arrayOfNulls<Any>(realSize + 1)
    for (i in methodParams.indices) {
        val classType = Type.getClassType(methodParams[i])
        if (classType == null) {
            obj[i] = methodParams[i]
        } else {
            obj[i] = classType
        }
    }
    when (mode) {
        Constant.HOOK_PARAMRET -> {
            obj[realSize] = object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (param.args[0].toString().equals(pd)) {
                        param.result = Type.getDataTypeValue(values)

                    }

                }
            }
        }

    }
    if (XposedHelpers.findClassIfExists(className, classLoader) == null) {
        //类名+方法名不存在,不进行hook
        return;
    }
    XposedHelpers.findAndHookMethod(className, classLoader, methodName, *obj)
}

fun hook(
    className: String,
    classLoader: ClassLoader,
    methodName: String, values: String,
    params: String, mode: Int
) {
    val methodParams = params.split(",")
    val realSize = if (params == "") 0 else methodParams.size
    val obj = arrayOfNulls<Any>(realSize + 1)
    for (i in methodParams.indices) {
        val classType = Type.getClassType(methodParams[i])
        if (classType == null) {
            obj[i] = methodParams[i]
        } else {
            obj[i] = classType
        }
    }
    when (mode) {
        Constant.HOOK_RETURN -> {
            obj[realSize] = object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = Type.getDataTypeValue(values)
                }
            }
        }

        Constant.HOOK_BREAK -> {
            obj[realSize] = object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result = null
                }
            }
        }
        Constant.HOOK_PARAM -> {
            obj[realSize] = object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    for (i in methodParams.indices) {
                        if (methodParams[i] == "") continue
                        var value = Type.getDataTypeValue(values.split(",")[i])
                        if (value == "original") {
                            param.args[i] = param.args[i]
                        } else {
                            param.args[i] = value
                        }

                    }
                }
            }
        }
    }
    if (XposedHelpers.findClassIfExists(className, classLoader) == null) {
        //类名+方法名不存在,不进行hook
        return;
    }
    XposedHelpers.findAndHookMethod(className, classLoader, methodName, *obj)
}