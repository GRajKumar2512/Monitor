package com.example.monitor.ViewModals

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.monitor.models.AppUsageRepository

class ChartModel : ViewModel() {
    fun calculateFractions(context: Context): List<Float> {
        val usageStats = AppUsageRepository.getUsageStatsData(context)
        val topFourUsage = usageStats.take(4)
        val restUsage = usageStats.drop(4)

        // Calculate the sum of the rest of the usage
        val restSum = restUsage.sumOf { it.totalTimeInForeground.toDouble() }.toFloat()

        // Calculate the sum of all usage
        val totalSum = usageStats.sumOf { it.totalTimeInForeground.toDouble() }.toFloat()

        // Append the restSum to the list
        val allUsage = topFourUsage.map { it.totalTimeInForeground.toFloat() } + restSum

        // Convert to fractions by dividing each value by the total sum
        return allUsage.map { it / totalSum }
    }
}
