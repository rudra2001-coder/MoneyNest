package com.rudra.moneynest.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rudra.moneynest.data.local.db.entity.Goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(
    viewModel: GoalViewModel = hiltViewModel()
) {
    val goals by viewModel.goals.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Goals") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Goal")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(goals) { goal ->
                GoalItem(goal = goal)
            }
        }

        if (showDialog) {
            AddGoalDialog(
                onDismiss = { showDialog = false },
                onAddGoal = {
                    viewModel.addGoal(it)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
private fun GoalItem(goal: Goal) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Goal: ${goal.goalAmount}")
            Text("Saved: ${goal.savedAmount}")
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = (goal.savedAmount / goal.goalAmount).toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AddGoalDialog(
    onDismiss: () -> Unit,
    onAddGoal: (Goal) -> Unit
) {
    var goalAmount by remember { mutableStateOf("") }
    var savedAmount by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Add Goal", modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = goalAmount,
                    onValueChange = { goalAmount = it },
                    label = { Text("Goal Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = savedAmount,
                    onValueChange = { savedAmount = it },
                    label = { Text("Saved Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = deadline,
                    onValueChange = { deadline = it },
                    label = { Text("Deadline (timestamp)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        val goal = Goal(
                            goalAmount = goalAmount.toDoubleOrNull() ?: 0.0,
                            savedAmount = savedAmount.toDoubleOrNull() ?: 0.0,
                            deadline = deadline.toLongOrNull() ?: 0L
                        )
                        onAddGoal(goal)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add")
                }
            }
        }
    }
}
