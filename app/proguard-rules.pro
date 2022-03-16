# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep class com.example.kele.HookKt{*;}
#-keep class com.example.kele.HookStart{*;}

-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**

-keep class com.tencent.smtt.** {
    *;
}

-keep class com.tencent.tbs.** {
    *;
}
-keep class com.CloudHook.kele.HookStart {
     void handleLoadPackage*(...);
 }
 -keep class com.CloudHook.kele.X5Web {
      void onCreate*(...);
  }
  #去除log输出
#  -assumenosideeffects class de.robv.android.xposed.XposedBridge{
#      public static void log(...);
#
#  }
#  -assumenosideeffects class android.util.Log{
#      public static *** v(...);
#      public static *** i(...);
#      public static *** d(...);
#      public static *** w(...);
#      public static *** e(...);
#  }
