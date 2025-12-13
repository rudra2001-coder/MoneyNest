package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import kotlin.math.min

data class SummaryCardData(
    val title: String,
    val amount: Float,
    val icon: ImageVector,
    val primaryColor: Color,
    val secondaryColor: Color,
    val progress: Float? = null,
    val showTrend: Boolean = false,
    val trendValue: Float = 0f
)

@Composable
fun MonthlySummaryCards(
    monthlyBudget: MonthlyBudget?,
    modifier: Modifier = Modifier
) {
    val totalBalance by animateFloatAsState(
        targetValue = (monthlyBudget?.plannedAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = "totalBalance"
    )

    val spending by animateFloatAsState(
        targetValue = (monthlyBudget?.spentAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = "spending"
    )

    val remaining by animateFloatAsState(
        targetValue = (monthlyBudget?.remainingAmount ?: 0.0).toFloat(),
        animationSpec = tween(1000),
        label = "remaining"
    )

    // Calculate spending percentage for progress
    val spendingProgress = if (totalBalance > 0) {
        min(spending / totalBalance, 1f)
    } else {
        0f
    }

    // Calculate remaining percentage
    val remainingProgress = if (totalBalance > 0) {
        min(remaining / totalBalance, 1f)
    } else {
        0f
    }

    val cardsData = listOf(
        SummaryCardData(
            title = "Total Balance",
            amount = totalBalance,
            icon = Icons.Outlined.AccountBalanceWallet,
            primaryColor = Color(0xFF6366F1),
            secondaryColor = Color(0xFF8B5CF6),
            showTrend = true,
            trendValue = 12.5f // Example trend
        ),
        SummaryCardData(
            title = "Monthly Spending",
            amount = spending,
            icon = Icons.Outlined.ShowChart,
            primaryColor = Color(0xFF10B981),
            secondaryColor = Color(0xFF34D399),
            progress = spendingProgress
        ),
        SummaryCardData(
            title = "Budget Remaining",
            amount = remaining,
            icon = Icons.Outlined.Savings,
            primaryColor = Color(0xFFF59E0B),
            secondaryColor = Color(0xFFFBBF24),
            progress = remainingProgress
        ),
        SummaryCardData(
            title = "Savings Rate",
            amount = calculateSavingsRate(totalBalance, spending),
            icon = Icons.Outlined.TrendingUp,
            primaryColor = Color(0xFFEF4444),
            secondaryColor = Color(0xFFF87171)
        )
    )

    // Responsive grid layout
    val columns = 2
    val rows = (cardsData.size + columns - 1) / columns

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (i in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in 0 until columns) {
                    val index = i * columns + j
                    if (index < cardsData.size) {
                        PremiumSummaryCard(
                            data = cardsData[index],
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        // Empty space for odd number of cards
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumSummaryCard(
    data: SummaryCardData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = data.primaryColor.copy(alpha = 0.3F),
                spotColor = data.primaryColor.copy(alpha = 0.3F)
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header row with icon and title
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = data.primaryColor.copy(alpha = 0.1F),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = data.title,
                                tint = data.primaryColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Trend indicator (if applicable)
                    if (data.showTrend) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (data.trendValue >= 0) {
                                Color(0xFF10B981).copy(alpha = 0.1F)
                            } else {
                                Color(0xFFEF4444).copy(alpha = 0.1F)
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = if (data.trendValue >= 0) "↑${data.trendValue}%" else "↓${-data.trendValue}%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (data.trendValue >= 0) Color(0xFF10B981) else Color(0xFFEF4444),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Amount with currency
                Column {
                    Text(
                        text = "৳${formatAmount(data.amount)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 22.sp
                    )

                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Progress indicator (if applicable)
                data.progress?.let { progress ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Progress",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = data.primaryColor
                            )
                        }

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = data.primaryColor,
                            trackColor = data.primaryColor.copy(alpha = 0.1F)
                        )
                    }
                }

                // For savings rate, show a circular indicator
                if (data.title == "Savings Rate") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            progress = data.amount / 100f,
                            primaryColor = data.primaryColor,
                            secondaryColor = data.secondaryColor,
                            strokeWidth = 6.dp
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${data.amount.toInt()}%",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = data.primaryColor
                            )
                            Text(
                                text = "of income",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CircularProgressIndicator(
    progress: Float,
    primaryColor: Color,
    secondaryColor: Color,
    strokeWidth: androidx.compose.ui.unit.Dp = 8.dp
) {
    Box(
        modifier = Modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = primaryColor.copy(alpha = 0.1F),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        // Progress arc
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}

fun formatAmount(amount: Float): String {
    return when {
        amount >= 1000000 -> String.format("%.1fM", amount / 1000000)
        amount >= 1000 -> String.format("%.1fK", amount / 1000)
        else -> String.format("%.0f", amount)
    }
}

fun calculateSavingsRate(totalBalance: Float, spending: Float): Float {
    if (totalBalance <= 0) return 0f
    val savings = totalBalance - spending
    return (savings / totalBalance * 100).coerceIn(0f, 100f)
}