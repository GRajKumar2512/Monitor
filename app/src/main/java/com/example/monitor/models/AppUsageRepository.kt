package com.example.monitor.models

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Calendar

data class AppUsage(val appName: String, val usageTime: String)

object AppUsageRepository {
    fun getUsageStatsData(context: Context): List<UsageStats> {

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endTime = System.currentTimeMillis()
        val startTime = calendar.timeInMillis

        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
        val usageStats = usageStatsManager?.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY, startTime, endTime
        )

        return usageStats?.filter { it.totalTimeInForeground > 60000 }?.sortedByDescending { it.totalTimeInForeground }?: emptyList()
    }
}
