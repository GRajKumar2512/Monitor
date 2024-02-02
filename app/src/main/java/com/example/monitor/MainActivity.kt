package com.example.monitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.monitor.models.AppUsage
import com.example.monitor.screens.ListOfApps.AppUsageScreen
import com.example.monitor.ui.theme.MonitorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appItems: MutableList<AppUsage> = mutableListOf()

        appItems.add(AppUsage(appName = "youtube", usageTime = "2 hrs"))
        appItems.add(AppUsage(appName = "brave", usageTime = "2 hrs"))
        appItems.add(AppUsage(appName = "pubg", usageTime = "2 hrs"))
        appItems.add(AppUsage(appName = "instagram", usageTime = "2 hrs"))
        appItems.add(AppUsage(appName = "facebook", usageTime = "2 hrs"))
        appItems.add(AppUsage(appName = "twitter", usageTime = "2 hrs"))
        
        setContent {
            MonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   AppUsageScreen(appItems = appItems)
                }
            }
        }
    }
}
