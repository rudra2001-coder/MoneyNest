package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget

@Composable
fun MonthlySummaryCards(
    monthlyBudget: MonthlyBudget?
) {
    val totalBalance by animateFloatAsState(
        targetValue = (monthlyBudget?.plannedAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = ""
    )
    val spending by animateFloatAsState(
        targetValue = (monthlyBudget?.spentAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = ""
    )
    val remaining by animateFloatAsState(
        targetValue = (monthlyBudget?.remainingAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = ""
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SummaryCard(
                title = "Total Balance",
                amount = totalBalance,
                modifier = Modifier.weight(1f),
                brush = Brush.horizontalGradient(listOf(Color(0xFF64B5F6), Color(0xFF1976D2)))
            )
            SummaryCard(
                title = "This Month's Spending",
                amount = spending,
                modifier = Modifier.weight(1f),
                brush = Brush.horizontalGradient(listOf(Color(0xFF81C784), Color(0xFF388E3C)))
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SummaryCard(
                title = "Remaining Budget",
                amount = remaining,
                modifier = Modifier.weight(1f),
                brush = Brush.horizontalGradient(listOf(Color(0xFFE57373), Color(0xFFD32F2F)))
            )
            SummaryCard(
                title = "Savings Growth",
                amount = 0f, // Placeholder
                modifier = Modifier.weight(1f),
                brush = Brush.horizontalGradient(listOf(Color(0xFFFFD54F), Color(0xFFFBC02D)))
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: Float, modifier: Modifier = Modifier, brush: Brush) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .background(brush)
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = Color.White)
            Text(
                text = "$${String.format("%.2f", amount)}",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
        }
    }
}
