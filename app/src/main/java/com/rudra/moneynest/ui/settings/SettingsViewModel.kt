package com.rudra.moneynest.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isBiometricLockEnabled: StateFlow<Boolean> = userPreferencesRepository.isBiometricLockEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )

    val currency: StateFlow<String> = userPreferencesRepository.currency
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "USD"
        )

    private val _isCurrencyDialogShown = MutableStateFlow(false)
    val isCurrencyDialogShown: StateFlow<Boolean> = _isCurrencyDialogShown.asStateFlow()

    fun setBiometricLockEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setBiometricLockEnabled(enabled)
        }
    }

    fun resetSecuritySettings() {
        viewModelScope.launch {
            userPreferencesRepository.clearSecuritySettings()
        }
    }

    fun onCurrencyChange(currency: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveCurrency(currency)
        }
    }

    fun showCurrencyDialog() {
        _isCurrencyDialogShown.value = true
    }

    fun dismissCurrencyDialog() {
        _isCurrencyDialogShown.value = false
    }
}
