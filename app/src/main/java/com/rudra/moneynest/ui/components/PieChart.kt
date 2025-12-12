package com.rudra.moneynest.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    data: Map<String, Double>
) {
    AndroidView(modifier = modifier, factory = { context ->
        PieChart(context).apply {
            description.isEnabled = false
            isDrawHoleEnabled = true
            legend.isEnabled = false
        }
    }, update = {
        val entries = data.map { (label, value) -> PieEntry(value.toFloat(), label) }
        val dataSet = PieDataSet(entries, "").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = android.graphics.Color.BLACK
            valueTextSize = 12f
        }
        it.data = PieData(dataSet)
        it.invalidate()
    })
}
