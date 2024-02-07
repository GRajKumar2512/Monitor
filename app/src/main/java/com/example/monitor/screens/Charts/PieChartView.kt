
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monitor.ViewModals.ChartModel
import com.example.monitor.ui.theme.MonitorTheme

data class Slice(val value: Float, val startColor: Color, val endColor: Color, val legend: String, val label: String)

@Composable
fun ChartScreen(viewModel: ChartModel){
    val appUsageList = viewModel.chartList

    var sliceList: List<Slice> = listOf(
        Slice(appUsageList[0].second.toFloat(), Color(120, 181, 0), Color(149, 224, 0), legend = appUsageList[0].first, label = "Label A"),
        Slice(appUsageList[1].second.toFloat(), Color(204, 168, 0), Color(249, 228, 0), legend = appUsageList[1].first, label = "Label B"),
        Slice(appUsageList[2].second.toFloat(), Color(0, 162, 216), Color(31, 199, 255), legend = appUsageList[2].first, label = "Label C"),
        Slice(appUsageList[3].second.toFloat(), Color(255, 4, 4), Color(255, 72, 86), legend = appUsageList[3].first, label = "Label D"),
        Slice(appUsageList[4].second.toFloat(), Color(160, 165, 170), Color(175, 180, 185), legend = appUsageList[4].first, label = "Last Label")
    )

    // create slices like in the default preview
    DonutChart(slices = sliceList)
}

@Composable
fun DonutChart(slices: List<Slice>) {
    val totalValue = slices.sumByDouble { it.value.toDouble() }.toFloat()
    var startAngle = 0f

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
                .padding(16.dp)
        ) {
            slices.forEachIndexed { index, slice ->
                val sweepAngle = (360 * (slice.value / totalValue))
                drawArc(
                    color = slice.startColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 50f),
                    size = size,
                )
                startAngle += sweepAngle
            }

            val center = Offset(size.width / 2, size.height / 2)
        }

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 120.dp)
                    .background(Color.Transparent), // Ensure the background is transparent to see the donut chart
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                slices.forEach {
                    LegendItem(legend = it.legend, color = it.startColor)
                }
            }
    }
}

@Composable
fun LegendItem(legend: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(16.dp)
                .background(color = color, shape = MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = legend, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MonitorTheme {
        DonutChart(slices = listOf(
            Slice(0.3f, Color(120, 181, 0), Color(149, 224, 0), legend = "Legend A", label = "Label A"),
            Slice(0.2f, Color(204, 168, 0), Color(249, 228, 0), legend = "Legend B", label = "Label B"),
            Slice(0.2f, Color(0, 162, 216), Color(31, 199, 255), legend = "Legend C", label = "Label C"),
            Slice(0.17f, Color(255, 4, 4), Color(255, 72, 86), legend = "Legend D", label = "Label D"),
            Slice(0.13f, Color(160, 165, 170), Color(175, 180, 185), legend = "Legend E", label = "Last Label")
        ))
    }
}
