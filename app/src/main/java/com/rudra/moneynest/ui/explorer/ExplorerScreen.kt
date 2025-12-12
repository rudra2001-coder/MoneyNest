package com.rudra.moneynest.ui.explorer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rudra.moneynest.R
import com.rudra.moneynest.ui.navigation.Screen
import com.rudra.moneynest.ui.theme.GradientBrush

data class ExplorerItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color,
    val route: String
)

@Composable
fun ExplorerScreen(navController: NavController) {
    val explorerItems = listOf(
        ExplorerItem(
            id = "add_transaction",
            title = "Add Transaction",
            description = "Record income and expenses",
            icon = Icons.Outlined.AddCard,
            iconColor = Color.White,
            backgroundColor = MaterialTheme.colorScheme.primary,
            route = Screen.AddTransaction.route
        ),
        ExplorerItem(
            id = "categories",
            title = "Categories",
            description = "Manage spending categories",
            icon = Icons.Outlined.Category,
            iconColor = Color.White,
            backgroundColor = Color(0xFF4CAF50),
            route = Screen.Categories.route
        ),
        ExplorerItem(
            id = "monthly_budget",
            title = "Monthly Budget",
            description = "Plan and track your budget",
            icon = Icons.Outlined.Paid,
            iconColor = Color.White,
            backgroundColor = Color(0xFF2196F3),
            route = Screen.MonthlyBudget.route
        ),
        ExplorerItem(
            id = "goals",
            title = "Financial Goals",
            description = "Set and achieve your targets",
            icon = Icons.Outlined.TrackChanges,
            iconColor = Color.White,
            backgroundColor = Color(0xFF9C27B0),
            route = Screen.Goals.route
        ),
        ExplorerItem(
            id = "bill_tracker",
            title = "Bill Tracker",
            description = "Never miss a payment",
            icon = Icons.Outlined.Wallet,
            iconColor = Color.White,
            backgroundColor = Color(0xFFFF9800),
            route = Screen.BillTracker.route
        ),
        ExplorerItem(
            id = "net_worth",
            title = "Net Worth",
            description = "Track your total wealth",
            icon = Icons.Outlined.ShowChart,
            iconColor = Color.White,
            backgroundColor = Color(0xFF3F51B5),
            route = Screen.NetWorth.route
        ),
        ExplorerItem(
            id = "reports",
            title = "Reports & Analytics",
            description = "Detailed financial insights",
            icon = Icons.Outlined.Analytics,
            iconColor = Color.White,
            backgroundColor = Color(0xFF009688),
            route = Screen.Report.route
        ),
        ExplorerItem(
            id = "data_export",
            title = "Data Export",
            description = "Backup and export data",
            icon = Icons.Outlined.FileDownload,
            iconColor = Color.White,
            backgroundColor = Color(0xFF795548),
            route = Screen.DataExport.route
        ),
        ExplorerItem(
            id = "settings",
            title = "Settings",
            description = "App preferences and more",
            icon = Icons.Outlined.Settings,
            iconColor = Color.White,
            backgroundColor = Color(0xFF607D8B),
            route = Screen.Settings.route
        )
    )

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                color = Color.Transparent
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Premium gradient background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = GradientBrush.primaryGradient(),
                                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        // Premium badge
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            color = Color.Transparent
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.WorkspacePremium,
                                    contentDescription = "Premium",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "MoneyNest Premium",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // App logo/avatar
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2F)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Replace with your app logo
                            Text(
                                text = "💰",
                                fontSize = 40.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Financial Explorer",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Manage your finances with style",
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.8F),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(explorerItems) { item ->
                ExplorerCard(
                    item = item,
                    onClick = { navController.navigate(item.route) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))

                // Optional: Add a footer message
                Text(
                    text = "All your financial tools in one place",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6F),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ExplorerCard(
    item: ExplorerItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container with colored background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(item.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = item.iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6F),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Arrow indicator
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Add this to your theme package or create a separate file
object GradientBrush {
    @Composable
    fun primaryGradient() = androidx.compose.ui.graphics.Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.8F),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.9F)
        ),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite
    )
}