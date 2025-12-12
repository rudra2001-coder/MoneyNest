package com.rudra.moneynest.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rudra.moneynest.ui.navigation.Screen

@Composable
fun QuickActionsRow(navController: NavController) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        QuickAction(icon = Icons.Default.Add, text = "Add Expense") { navController.navigate(Screen.AddTransaction.route) }
        QuickAction(icon = Icons.Default.Add, text = "Add Income") { navController.navigate(Screen.AddTransaction.route) }
        QuickAction(icon = Icons.Default.IosShare, text = "Export CSV") { navController.navigate(Screen.DataExport.route) }
        QuickAction(icon = Icons.Default.Category, text = "Categories") { navController.navigate(Screen.Categories.route) }
    }
}

@Composable
private fun QuickAction(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = text, tint = MaterialTheme.colorScheme.onSecondaryContainer)
        }
        Text(text = text, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
    }
}