package com.example.monitor.ViewModals

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import com.example.monitor.models.AppUsage
import com.example.monitor.models.AppUsageRepository

class UsageStatsViewModel : ViewModel() {
    // List to hold the app usage data
    private val _usageStatsData = mutableListOf<AppUsage>()

    // Expose the list as an immutable property
    val usageStatsData: MutableList<AppUsage>
        get() = _usageStatsData

    fun fetchUsageStats(context: Context) {
        val packageManager = context.packageManager

        _usageStatsData.clear() // Clear previous data if any
        val usageStats = AppUsageRepository.getUsageStatsData(context)

        _usageStatsData.addAll(usageStats.map { usageStat ->
            val appName = getAppNameFromPackage(context, usageStat.packageName, packageManager)
            AppUsage(appName, timeFormatter(usageStat.totalTimeInForeground))
        })
    }

    private fun getAppNameFromPackage(context: Context, packageName: String, packageManager: PackageManager): String {
        val applicationInfo = try {
            packageManager.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        return applicationInfo?.let {
            packageManager.getApplicationLabel(applicationInfo).toString()
        } ?: packageName
    }

    private fun timeFormatter(usageTime: Long): String{
        val timeInMinutes: Long = usageTime / (1000*60);
        val timeInHours: Long = usageTime / (1000*60*60);

        val formattedMinutes: Long = timeInMinutes % 60;

        return if(timeInHours > 0) {
            "$timeInHours hrs $formattedMinutes minutes"
        } else if(formattedMinutes > 0){
            "$formattedMinutes minutes"
        } else {
            ""
        }
    }
}