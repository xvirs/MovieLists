package com.proyect.movielists.data.interfaces

import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.Flow

interface SessionDataStore {
    val sessionIdFlow: Flow<StatusResult<String?>>
    suspend fun saveSessionId(sessionId: String): StatusResult<Unit>
    suspend fun clearSessionId(): StatusResult<Unit>

    val accountIdFlow: Flow<StatusResult<String?>>
    suspend fun saveAccountId(accountId: String): StatusResult<Unit>
    suspend fun clearAccountId(): StatusResult<Unit>
}