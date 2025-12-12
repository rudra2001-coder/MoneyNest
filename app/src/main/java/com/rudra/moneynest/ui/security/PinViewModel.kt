package com.rudra.moneynest.ui.security

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PinState {
    object Loading : PinState()
    object PinNotSet : PinState()
    object PinSet : PinState()
    object PinVerified : PinState()
    data class Error(val message: String) : PinState()
}

@HiltViewModel
class PinViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _pinState = MutableStateFlow<PinState>(PinState.Loading)
    val pinState: StateFlow<PinState> = _pinState.asStateFlow()

    init {
        checkPinStatus()
    }

    private fun checkPinStatus() {
        viewModelScope.launch {
            val pin = userPreferencesRepository.userPin.first()
            _pinState.value = if (pin.isNullOrEmpty()) {
                PinState.PinNotSet
            } else {
                PinState.PinSet
            }
        }
    }

    fun setPin(pin: String) {
        viewModelScope.launch {
            userPreferencesRepository.savePin(pin)
            _pinState.value = PinState.PinSet
        }
    }

    fun verifyPin(pin: String) {
        viewModelScope.launch {
            val savedPin = userPreferencesRepository.userPin.first()
            if (pin == savedPin) {
                _pinState.value = PinState.PinVerified
            } else {
                _pinState.value = PinState.Error("Incorrect PIN")
            }
        }
    }
}
