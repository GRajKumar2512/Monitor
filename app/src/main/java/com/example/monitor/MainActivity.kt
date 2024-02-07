package com.example.monitor

import ChartScreen
import android.app.AppOpsManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.monitor.ViewModals.ChartModel
import com.example.monitor.ViewModals.UsageStatsViewModel
import com.example.monitor.models.AppUsage
import com.example.monitor.screens.ListOfApps.AppUsageScreen
import com.example.monitor.ui.theme.MonitorTheme

class MainActivity : ComponentActivity() {

    private val  appViewModel: UsageStatsViewModel by viewModels()
    private val chartViewModel: ChartModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(hasUsageStatsPermission()){
            appViewModel.fetchUsageStats(this)
            chartViewModel.calculateFractions(this)
            setContent {
                MonitorTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
                            rememberScrollState(0))){
                            ChartScreen(chartViewModel)
                            AppUsageScreen(appViewModel)
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Usage stats permission not granted", Toast.LENGTH_SHORT).show();
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(hasUsageStatsPermission()){
            appViewModel.fetchUsageStats(this)
            chartViewModel.calculateFractions(this)
            setContent {
                MonitorTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column (horizontalAlignment = Alignment.CenterHorizontally){
                            ChartScreen(chartViewModel)
                            AppUsageScreen(appViewModel)
                        }
                    }
                }
            }
        } else {
            requestUsageStatsPermission();
        }
    }

    private fun hasUsageStatsPermission(): Boolean{
        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission(){
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        requestPermissionLauncher.launch(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }
}
