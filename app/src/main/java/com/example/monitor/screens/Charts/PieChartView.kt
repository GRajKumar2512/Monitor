import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.monitor.ui.theme.MonitorTheme

data class Slice(val value: Float, val startColor: Color, val endColor: Color, val legend: String, val label: String)

class SecondaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DonutChart(slices = listOf(
                        Slice(0.3f, Color(120, 181, 0), Color(149, 224, 0), legend = "Legend A", label = "Label A"),
                        Slice(0.2f, Color(204, 168, 0), Color(249, 228, 0), legend = "Legend B", label = "Label B"),
                        Slice(0.2f, Color(0, 162, 216), Color(31, 199, 255), legend = "Legend C", label = "Label C"),
                        Slice(0.17f, Color(255, 4, 4), Color(255, 72, 86), legend = "Legend D", label = "Label D"),
                        Slice(0.13f, Color(160, 165, 170), Color(175, 180, 185), legend = "Legend E", label = "Last Label")
                    ))
                }
            }
        }
    }
}

@Composable
fun DonutChart(slices: List<Slice>) {
    val totalValue = slices.sumByDouble { it.value.toDouble() }.toFloat()
    var startAngle = 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
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
                .fillMaxSize()
                .padding(16.dp)
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
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(16.dp)
                .background(color = color, shape = MaterialTheme.shapes.small)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicText(text = legend, style = LocalTextStyle.current)
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
