package com.proyect.movielists.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.proyect.movielists.data.interfaces.SessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.catch
import com.proyect.movielists.utils.StatusResult

class SessionDataStoreImpl(private val dataStore: DataStore<Preferences>) : SessionDataStore {

    companion object {
        val SESSION_ID = stringPreferencesKey("session_id")
        val ACCOUNT_ID = stringPreferencesKey("account_id")
    }

    // Session ID
    override val sessionIdFlow: Flow<StatusResult<String?>> = dataStore.data
        .map { preferences ->
            StatusResult.Success(preferences[SESSION_ID])
        }
        .catch { exception ->
            StatusResult.Error(
                exception.message ?: "Error al retornar el Token de sesión",
                StatusResult.ErrorType.UNKNOWN
            )
        }

    override suspend fun saveSessionId(sessionId: String): StatusResult<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[SESSION_ID] = sessionId
            }
            StatusResult.Success(Unit)
        } catch (e: Exception) {
            StatusResult.Error(
                e.message ?: "Error al guardar el Token de sesión",
                StatusResult.ErrorType.UNKNOWN
            )
        }
    }

    override suspend fun clearSessionId(): StatusResult<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(SESSION_ID)
            }
            StatusResult.Success(Unit)
        } catch (e: Exception) {
            StatusResult.Error(
                e.message ?: "Error al limpiar el Token de sesión",
                StatusResult.ErrorType.UNKNOWN
            )
        }
    }

    // Account ID
    override suspend fun saveAccountId(accountId: String): StatusResult<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[ACCOUNT_ID] = accountId
            }
            StatusResult.Success(Unit)
        } catch (e: Exception) {
            StatusResult.Error(
                e.message ?: "Error al guardar el Account ID",
                StatusResult.ErrorType.UNKNOWN
            )
        }
    }

    override val accountIdFlow: Flow<StatusResult<String?>> =  dataStore.data
    .map { preferences ->
        StatusResult.Success(preferences[ACCOUNT_ID])
    }
    .catch { exception ->
        StatusResult.Error(
            exception.message ?: "Error al retornar el Account ID",
            StatusResult.ErrorType.UNKNOWN
        )
    }

    override suspend fun clearAccountId(): StatusResult<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(ACCOUNT_ID)
            }
            StatusResult.Success(Unit)
        } catch (e: Exception) {
            StatusResult.Error(
                e.message ?: "Error al limpiar el Account ID",
                StatusResult.ErrorType.UNKNOWN
            )
        }
    }
}