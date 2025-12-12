package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rudra.moneynest.ui.dashboard.BudgetProgress

@Composable
fun BudgetProgressSection(budgetProgress: List<BudgetProgress>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Budget Progress", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                budgetProgress.forEach { progress ->
                    BudgetProgressBar(progress = progress)
                }
            }
        }
    }
}

@Composable
fun BudgetProgressBar(progress: BudgetProgress) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.percentage,
        animationSpec = tween(1000),
        label = ""
    )
    val progressColor = when {
        progress.percentage > 0.8f -> Color.Red
        progress.percentage > 0.6f -> Color(0xFFFFA000) // Amber
        else -> Color.Green
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = progress.categoryName, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "${(progress.percentage * 100).toInt()}% used",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxWidth(),
            color = progressColor
        )
    }
}
