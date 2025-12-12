package com.rudra.moneynest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
    }

    val userPin: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_PIN]
    }

    suspend fun savePin(pin: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_PIN] = pin
        }
    }
}
