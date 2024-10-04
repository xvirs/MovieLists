package com.proyect.movielists.data.interfaces

import com.proyect.movielists.data.models.UserProfileDto
import com.proyect.movielists.utils.StatusResult

interface ProfileDataSource {
    suspend fun getProfile( sessionId : String ) : StatusResult<UserProfileDto>
}