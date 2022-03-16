package com.CloudHook.kele

object Constant {
    // app 整体hook模式
    const val HOOK_ORIGIN = 0
    const val HOOK_JIA_GU = 1

    const val HOOK_RETURN = 0  //Hook返回值
    const val HOOK_PARAM = 1   //Hook方法名参数
    const val HOOK_BREAK = 2   //中断方法名执行
    const val HOOK_STATIC_FIELD = 3  //Hook静态变量
    const val Hook_ACTIVITY=4   //Activity跳转
    const val HOOK_FIELD = 5    //hook类变量
    const val HOOK_ActivityFIELD =6 // 退出Activity界面
    const val HOOK_PARAMRET=7 //Hook判断方法名参数并返回结果

    const val HTTP_Proxy=20 //APP添加HTTP代理
}