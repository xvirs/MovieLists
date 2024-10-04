package com.proyect.movielists.data.repository

import com.proyect.movielists.data.interfaces.ProfileDataSource
import com.proyect.movielists.data.interfaces.SessionDataStore
import com.proyect.movielists.data.models.mappers.toUserProfile
import com.proyect.movielists.domine.interfaces.ProfileRepository
import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.utils.StatusResult
import kotlinx.coroutines.flow.first

class ProfileRepositoryImpl( private val profileDataSource : ProfileDataSource, private val sessionDataStore : SessionDataStore) : ProfileRepository {

    override suspend fun getProfile(): StatusResult<UserProfile> {
        val requestToken = when (sessionDataStore.sessionIdFlow.first()){
            is StatusResult.Success -> (sessionDataStore.sessionIdFlow.first() as StatusResult.Success<String?>).value
            is StatusResult.Error -> null
        }
        return when(val result = profileDataSource.getProfile(requestToken.toString())) {
            is StatusResult.Success -> {
                sessionDataStore.saveAccountId(result.value.id.toString())
                StatusResult.Success(result.value.toUserProfile())
            }
            is StatusResult.Error -> StatusResult.Error(result.message)
        }
    }
}