package com.cityxcape.cityxcape.utilities

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.cityxcape.cityxcape.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



object PreferencesManager {
    private val Context.datastore by preferencesDataStore(name = "user_prefs")

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val UID_KEY = stringPreferencesKey("uid")
    private val STREETCRED_KEY = intPreferencesKey("streetcred")
    private val IMAGE_KEY = stringPreferencesKey("imageUrl")
    private val LASTSPOT_KEY = stringPreferencesKey("lastSpotId")


    suspend fun saveUsername(context: Context, username: String) {
        context.datastore.edit { prefs ->
            prefs[USERNAME_KEY] = username
        }
    }

    fun getUsername(context: Context) : Flow<String> {
        return  context.datastore.data.map { prefs ->
            prefs[USERNAME_KEY] ?: ""
        }
    }

    suspend fun saveImageUrl(context: Context, imageUrl: String) {
        context.datastore.edit { prefs ->
            prefs[IMAGE_KEY] = imageUrl
        }
    }

    fun getImageUrl(context: Context) : Flow<String> {
       return context.datastore.data.map { prefs ->
            prefs[IMAGE_KEY] ?: ""
        }
    }

    suspend fun saveUserId(context: Context, uid: String) {
        context.datastore.edit { prefs ->
            prefs[UID_KEY] = uid
        }
    }

    fun getUserId(context: Context) : Flow<String> {
        return  context.datastore.data.map { prefs ->
            prefs[UID_KEY] ?: ""
        }
    }

    suspend fun saveStreetCred(context: Context, streetcred: Int) {
        context.datastore.edit { prefs ->
            prefs[STREETCRED_KEY] = streetcred
        }
    }

    fun getStreetCred(context: Context) : Flow<Int> {
        return context.datastore.data.map { prefs ->
            prefs[STREETCRED_KEY] ?: 0
        }
    }

    suspend fun saveLastSpotId(context: Context, spotId: String) {
        context.datastore.edit { prefs ->
            prefs[LASTSPOT_KEY] = spotId
        }
    }

    fun getLastSpotId(context: Context) : Flow<String> {
        return context.datastore.data.map { prefs ->
            prefs[LASTSPOT_KEY] ?: ""
        }
    }

    suspend fun clearSharedPreferences(context: Context) {
        context.datastore.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun setPreferencesFrom(user: User, context: Context) {
        saveUsername(context, user.username)
        saveUserId(context, user.id)
        saveStreetCred(context, user.streetCred)
        saveImageUrl(context, user.imageUrl)
    }

}