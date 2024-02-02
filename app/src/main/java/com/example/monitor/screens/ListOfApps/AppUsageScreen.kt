package com.example.monitor.screens.ListOfApps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.monitor.models.AppUsage

@Composable
fun AppUsageScreen(appItems: MutableList<AppUsage>){

    Column (modifier =  Modifier.padding(horizontal = 20.dp).background(color = Color.Yellow)){
        Text(text = "Apps In Usage:", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth());
        Spacer(modifier = Modifier.height(16.dp))
        AppUsageList(appUsageList = appItems);
    }
}

@Composable
fun AppUsageList(appUsageList: List<AppUsage>){
    LazyColumn{
        items(appUsageList){ app ->
            AppUsageItem(app)
        }
    }
}

@Composable
fun AppUsageItem(data: AppUsage){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = data.appName, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = data.usageTime, color = MaterialTheme.colorScheme.primary)
    }
}