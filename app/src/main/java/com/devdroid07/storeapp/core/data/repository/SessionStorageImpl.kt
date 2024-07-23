package com.devdroid07.storeapp.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devdroid07.storeapp.core.data.mappers.toUser
import com.devdroid07.storeapp.core.data.mappers.toUserSerializable
import com.devdroid07.storeapp.core.domain.SessionStorage
import com.devdroid07.storeapp.core.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionStorageImpl(
    private val userPreferences: DataStore<Preferences>
): SessionStorage {

    companion object{
        private val USER_DATA = stringPreferencesKey("user_session")
    }
    override suspend fun get(): User? {
        return withContext(Dispatchers.IO) {
            try {
                val preferences = userPreferences.data.firstOrNull() ?: return@withContext null
                val userDataJson = preferences[USER_DATA] ?: return@withContext null
                Json.decodeFromString<UserSerializable>(userDataJson).toUser()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun set(user: User?) {
        withContext(Dispatchers.IO) {
            try {
                userPreferences.edit { preferences ->
                    if (user == null) {
                        preferences[USER_DATA] = ""
                    } else {
                        val userDataJson = Json.encodeToString(user.toUserSerializable())
                        preferences[USER_DATA] = userDataJson
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}