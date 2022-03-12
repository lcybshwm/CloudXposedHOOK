package com.CloudHook.kele

import de.robv.android.xposed.XposedBridge


//xposed log
fun String.log() {
    XposedBridge.log("===${this}===")
}