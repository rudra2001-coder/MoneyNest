package com.rudra.moneynest.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
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
    private val userPreferencesRepository: UserPreferencesRepository,
    private val repository: Repository
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

    private val _isResetAppDialogShown = MutableStateFlow(false)
    val isResetAppDialogShown: StateFlow<Boolean> = _isResetAppDialogShown.asStateFlow()

    private val _isBackupDialogShown = MutableStateFlow(false)
    val isBackupDialogShown: StateFlow<Boolean> = _isBackupDialogShown.asStateFlow()

    private val _isRestoreDialogShown = MutableStateFlow(false)
    val isRestoreDialogShown: StateFlow<Boolean> = _isRestoreDialogShown.asStateFlow()

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

    fun showResetAppDialog() {
        _isResetAppDialogShown.value = true
    }

    fun dismissResetAppDialog() {
        _isResetAppDialogShown.value = false
    }

    fun showBackupDialog() {
        _isBackupDialogShown.value = true
    }

    fun dismissBackupDialog() {
        _isBackupDialogShown.value = false
    }

    fun showRestoreDialog() {
        _isRestoreDialogShown.value = true
    }

    fun dismissRestoreDialog() {
        _isRestoreDialogShown.value = false
    }

    fun resetApp() {
        viewModelScope.launch {
            repository.clearAllTables()
        }
    }

    fun backupData() {
        viewModelScope.launch {
            // TODO: Implement backup logic
        }
    }

    fun restoreData() {
        viewModelScope.launch {
            // TODO: Implement restore logic
        }
    }
}
