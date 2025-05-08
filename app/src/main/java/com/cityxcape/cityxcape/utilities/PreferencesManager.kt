package com.cityxcape.cityxcape.utilities

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.datastore by preferencesDataStore(name = "user_prefs")

object PreferencesManager {

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val UID_KEY = stringPreferencesKey("uid")
    private val STREETCRED_KEY = stringPreferencesKey("streetcred")
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

    }


}