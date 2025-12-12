package com.rudra.moneynest.ui.billtracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.local.db.entity.RecurringBill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillTrackerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _bills = MutableStateFlow<List<RecurringBill>>(emptyList())
    val bills: StateFlow<List<RecurringBill>> = _bills.asStateFlow()

    init {
        repository.getAllRecurringBills()
            .onEach { _bills.value = it }
            .launchIn(viewModelScope)
    }

    fun addBill(bill: RecurringBill) {
        viewModelScope.launch {
            repository.insertRecurringBill(bill)
        }
    }
}
