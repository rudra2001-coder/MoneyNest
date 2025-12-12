package com.rudra.moneynest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val USER_PIN = stringPreferencesKey("user_pin")
        val BIOMETRIC_LOCK_ENABLED = booleanPreferencesKey("biometric_lock_enabled")
        val CURRENCY = stringPreferencesKey("currency")
    }

    val userPin: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_PIN]
    }

    val isBiometricLockEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BIOMETRIC_LOCK_ENABLED] ?: true
    }

    val currency: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.CURRENCY] ?: "USD"
    }

    suspend fun savePin(pin: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_PIN] = pin
        }
    }

    suspend fun setBiometricLockEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BIOMETRIC_LOCK_ENABLED] = enabled
        }
    }

    suspend fun saveCurrency(currency: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENCY] = currency
        }
    }

    suspend fun clearSecuritySettings() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_PIN)
            preferences.remove(PreferencesKeys.BIOMETRIC_LOCK_ENABLED)
        }
    }
}
