package com.rudra.moneynest.ui.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material.icons.rounded.Work
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rudra.moneynest.data.local.db.entity.Category

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var selectedCategoryForEdit: Category? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetContent = {
            if (selectedCategoryForEdit != null) {
                EditCategoryBottomSheet(
                    category = selectedCategoryForEdit!!,
                    onUpdate = { updatedCategory ->
                        viewModel.updateCategory(updatedCategory)
                        scope.launch { sheetState.hide() }
                        selectedCategoryForEdit = null
                    },
                    onDelete = {
                        viewModel.deleteCategory(selectedCategoryForEdit!!)
                        scope.launch { sheetState.hide() }
                        selectedCategoryForEdit = null
                    },
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                        selectedCategoryForEdit = null
                    }
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                "Categories",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )
                            )
                            Text(
                                "Manage your spending categories",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    actions = {
                        IconButton(
                            onClick = { /* Settings */ }
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Category",
                        tint = Color.White
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
            ) {
                // Stats Card
                CategoryStatsCard(categories)

                Spacer(modifier = Modifier.height(20.dp))

                // Categories List
                if (categories.isEmpty()) {
                    EmptyCategoriesState(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    )
                } else {
                    PremiumCategoriesList(
                        categories = categories,
                        onCategoryClick = { category ->
                            selectedCategoryForEdit = category
                            scope.launch { sheetState.show() }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            }

            if (showDialog) {
                PremiumAddCategoryDialog(
                    onDismiss = { showDialog = false },
                    onAddCategory = {
                        viewModel.addCategory(it)
                        showDialog = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PremiumCategoriesList(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(categories, key = { _, category -> category.id ?: category.hashCode() }) { index, category ->
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(
                    animationSpec = tween(300, delayMillis = index * 100),
                    initialOffsetX = { it }
                ) + fadeIn(tween(300, delayMillis = index * 100))
            ) {
                PremiumCategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
}

@Composable
private fun PremiumCategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    val icon = getCategoryIcon(category.icon)
    val progress: Float = (category.envelopeBalance / category.budgetLimit).coerceIn(0.0, 1.0).toFloat()
    val isOverBudget = category.envelopeBalance > category.budgetLimit

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with gradient background
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                getCategoryColor(category.icon).copy(alpha = 0.9f),
                                getCategoryColor(category.icon).copy(alpha = 0.6f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = category.name,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Category details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "$${String.format("%.2f", category.envelopeBalance)} / $${String.format("%.2f", category.budgetLimit)}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isOverBudget) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar


                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}% used",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    if (isOverBudget) {
                        Text(
                            text = "Over budget!",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryStatsCard(categories: List<Category>) {
    val totalBudget = categories.sumOf { it.budgetLimit }
    val totalSpent = categories.sumOf { it.envelopeBalance }
    val remainingBudget = totalBudget - totalSpent
    val overallProgress = if (totalBudget > 0) (totalSpent / totalBudget).toFloat() else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF667eea),
                            Color(0xFF764ba2)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Budget Overview",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BudgetStatItem(
                        title = "Total Budget",
                        value = "$${String.format("%.2f", totalBudget)}",
                        color = Color.White
                    )

                    BudgetStatItem(
                        title = "Total Spent",
                        value = "$${String.format("%.2f", totalSpent)}",
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    BudgetStatItem(
                        title = "Remaining",
                        value = "$${String.format("%.2f", remainingBudget)}",
                        color = if (remainingBudget >= 0) Color(0xFF4cd964) else Color(0xFFff3b30)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Overall progress


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${(overallProgress * 100).toInt()}% of total budget used",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.9f)
                    )
                )
            }
        }
    }
}

@Composable
private fun BudgetStatItem(
    title: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color.White.copy(alpha = 0.8f)
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 18.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PremiumAddCategoryDialog(
    onDismiss: () -> Unit,
    onAddCategory: (Category) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableIntStateOf(0) }
    var budgetLimit by remember { mutableFloatStateOf(500f) }

    val iconOptions = listOf(
        Icons.Rounded.Fastfood,
        Icons.Rounded.ShoppingCart,
        Icons.Rounded.Home,
        Icons.Rounded.LocalGroceryStore,
        Icons.Rounded.TravelExplore,
        Icons.Rounded.Work,
        Icons.Rounded.AccountBalanceWallet
    )

    val iconNames = listOf("Food", "Shopping", "Home", "Groceries", "Travel", "Work", "Finance")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(28.dp)
                ),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Category,
                            contentDescription = "Add Category",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Create New Category",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        "Set up your spending category",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                // Category Name
                Column {
                    Text(
                        "Category Name",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("e.g., Dining Out, Groceries") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                // Icon Selection
                Column {
                    Text(
                        "Select Icon",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        iconOptions.forEachIndexed { index, icon ->
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selectedIcon == index) getCategoryColor(iconNames[index])
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .clickable { selectedIcon = index }
                                    .border(
                                        width = if (selectedIcon == index) 2.dp else 0.dp,
                                        color = if (selectedIcon == index) MaterialTheme.colorScheme.primary
                                        else Color.Transparent,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = iconNames[index],
                                    tint = if (selectedIcon == index) Color.White
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                // Budget Limit
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Monthly Budget",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            "$${String.format("%.0f", budgetLimit)}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Slider(
                        value = budgetLimit,
                        onValueChange = { budgetLimit = it },
                        valueRange = 0f..5000f,
                        steps = 49,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    androidx.compose.material3.Button(
                        onClick = {
                            val category = Category(
                                name = name,
                                icon = iconNames[selectedIcon],
                                budgetLimit = budgetLimit.toDouble(),
                                envelopeBalance = 0.0
                            )
                            onAddCategory(category)
                        },
                        modifier = Modifier.weight(1f),
                        enabled = name.isNotBlank()
                    ) {
                        Text("Create Category")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditCategoryBottomSheet(
    category: Category,
    onUpdate: (Category) -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(category.name) }
    var budgetLimit by remember { mutableFloatStateOf(category.budgetLimit.toFloat()) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Edit Category",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        category.icon,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Divider()

            // Category Name
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Budget Limit
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Monthly Budget",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        "$${String.format("%.0f", budgetLimit)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                Slider(
                    value = budgetLimit,
                    onValueChange = { budgetLimit = it },
                    valueRange = 0f..10000f,
                    steps = 99,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Current Stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatItem(
                        title = "Spent",
                        value = "$${String.format("%.2f", category.envelopeBalance)}"
                    )
                    StatItem(
                        title = "Budget",
                        value = "$${String.format("%.2f", category.budgetLimit)}"
                    )
                    StatItem(
                        title = "Remaining",
                        value = "$${String.format("%.2f", category.budgetLimit - category.envelopeBalance)}"
                    )
                }
            }

            // Update Button
            androidx.compose.material3.Button(
                onClick = {
                    val updatedCategory = category.copy(
                        name = name,
                        budgetLimit = budgetLimit.toDouble()
                    )
                    onUpdate(updatedCategory)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank()
            ) {
                Text("Update Category")
            }
        }
    }
}

@Composable
private fun StatItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun EmptyCategoriesState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Category,
                contentDescription = "No Categories",
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Categories Yet",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your first category to start tracking expenses",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

private fun getCategoryIcon(iconName: String): ImageVector {
    return when (iconName.lowercase()) {
        "food" -> Icons.Rounded.Fastfood
        "shopping" -> Icons.Rounded.ShoppingCart
        "home" -> Icons.Rounded.Home
        "groceries" -> Icons.Rounded.LocalGroceryStore
        "travel" -> Icons.Rounded.TravelExplore
        "work" -> Icons.Rounded.Work
        else -> Icons.Rounded.AccountBalanceWallet
    }
}

private fun getCategoryColor(iconName: String): Color {
    return when (iconName.lowercase()) {
        "food" -> Color(0xFFff9500)
        "shopping" -> Color(0xFF5856d6)
        "home" -> Color(0xFF34c759)
        "groceries" -> Color(0xFF5ac8fa)
        "travel" -> Color(0xFFaf52de)
        "work" -> Color(0xFF007aff)
        else -> Color(0xFFff3b30)
    }
}