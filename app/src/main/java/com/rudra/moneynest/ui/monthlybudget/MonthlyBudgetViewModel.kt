package com.rudra.moneynest.ui.monthlybudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MonthlyBudgetViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _monthlyBudget = MutableStateFlow<MonthlyBudget?>(null)
    val monthlyBudget: StateFlow<MonthlyBudget?> = _monthlyBudget.asStateFlow()

    private val currentMonth: String
        get() = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    init {
        viewModelScope.launch {
            val budget = repository.getMonthlyBudget(currentMonth).first()
            if (budget == null) {
                val newBudget = MonthlyBudget(currentMonth, 0.0, 0.0, 0.0)
                repository.insertMonthlyBudget(newBudget)
                _monthlyBudget.value = newBudget
            } else {
                _monthlyBudget.value = budget
            }
        }
    }

    fun updatePlannedAmount(plannedAmount: Double) {
        viewModelScope.launch {
            _monthlyBudget.value?.let {
                val updatedBudget = it.copy(plannedAmount = plannedAmount)
                repository.updateMonthlyBudget(updatedBudget)
                _monthlyBudget.value = updatedBudget
            }
        }
    }
}
