package com.example.monitor.ViewModals

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.monitor.models.AppUsage
import com.example.monitor.models.AppUsageRepository

class ChartModel : ViewModel() {
    lateinit var chartList: List<Pair<String, Double>>

    fun calculateFractions(context: Context) {
        val usageStats = AppUsageRepository.getUsageStatsData(context)

        val topFourUsage = usageStats.take(4)
        val restUsage = usageStats.drop(4)

        // Calculate the sum of the rest of the usage
        val restSum = restUsage.sumOf { it.totalTimeInForeground.toDouble() }

        // Convert top four usage to pairs of package name and usage time
        val topFourPairs = topFourUsage.map { it.packageName to it.totalTimeInForeground.toDouble() }

        // Append "Others" entry with the sum of the rest of the usage
        val allUsagePairs = topFourPairs + ("Others" to restSum)

        // Calculate the sum of all usage including "Others"
        val totalSum = allUsagePairs.sumOf { it.second }

        // Convert to fractions by dividing each value by the total sum
        chartList =  allUsagePairs.map { it.first to (it.second / totalSum) }
    }
}

