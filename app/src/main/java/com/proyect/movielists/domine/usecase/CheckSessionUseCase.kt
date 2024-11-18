package com.proyect.movielists.domine.usecase

import com.proyect.movielists.domine.interfaces.AuthRepository

class CheckSessionUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}
