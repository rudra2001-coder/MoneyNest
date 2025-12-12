package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rudra.moneynest.data.local.db.entity.Transaction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RecentTransactionsList(transactions: List<Transaction>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recent Transactions", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                transactions.forEach { transaction ->
                    TransactionRow(transaction = transaction)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(transaction: Transaction) {
    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = format.format(Date(transaction.date))

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (transaction.type == "Expense") Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
            contentDescription = transaction.type,
            tint = if (transaction.type == "Expense") Color.Red else Color.Green
        )
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(text = transaction.category, style = MaterialTheme.typography.bodyMedium)
            Text(text = date, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$${String.format("%.2f", transaction.amount)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
