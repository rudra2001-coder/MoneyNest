package com.rudra.moneynest.ui.networth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.local.db.entity.NetWorthItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetWorthViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _netWorthItems = MutableStateFlow<List<NetWorthItem>>(emptyList())
    val netWorthItems: StateFlow<List<NetWorthItem>> = _netWorthItems.asStateFlow()

    init {
        repository.getAllNetWorthItems()
            .onEach { _netWorthItems.value = it }
            .launchIn(viewModelScope)
    }

    fun addNetWorthItem(item: NetWorthItem) {
        viewModelScope.launch {
            repository.insertNetWorthItem(item)
        }
    }
}
