package com.CloudHook.kele;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.jetbrains.annotations.NotNull;

public class ComponentUtilKt {
    public static final boolean getEnable(@NotNull ComponentName componentName, @NotNull Context context) {
        return !context.getPackageManager().queryIntentActivities(new Intent().setComponent(componentName), 65536).isEmpty();
    }

    public static final void setEnable(@NotNull ComponentName componentName, @NotNull Context context, boolean z) {
        PackageManager packageManager = context.getPackageManager();
        if (getEnable(componentName, context) != z) {
            packageManager.setComponentEnabledSetting(componentName, z ? 1 : 2, 1);
        }
    }

}
