package com.proyect.movielists.domine.interfaces

import com.proyect.movielists.domine.models.UserProfile
import com.proyect.movielists.utils.StatusResult

interface ProfileRepository {
    suspend fun getProfile() : StatusResult<UserProfile>
}