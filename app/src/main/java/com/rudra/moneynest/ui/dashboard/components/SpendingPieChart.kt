package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.rudra.moneynest.ui.components.PieChart

@Composable
fun SpendingPieChart(spendingByCategory: Map<String, Double>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Spending Breakdown", style = MaterialTheme.typography.titleMedium)
            AnimatedVisibility(
                visible = spendingByCategory.isNotEmpty(),
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                PieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    data = spendingByCategory
                )
            }
        }
    }
}
