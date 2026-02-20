package com.meticha.permissions_compose

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import kotlin.collections.contains

internal fun checkPermissionAddedInManifest(
    permission: AppPermission,
    context: Context
): Boolean = try {
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong())
            )
        } else {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_PERMISSIONS
            )
        }
        val declaredPermissions = packageInfo.requestedPermissions ?: emptyArray()
        declaredPermissions.contains(permission.permission)
    } catch (e: Exception) {
        Log.e("PermissionCheck", "Error checking permission", e)
        false
    }

class PermissionNotAddedException(
    private val permission: String
) : Exception() {
    override val message: String
        get() = "You forgot to add $permission permission in the manifest"
}
