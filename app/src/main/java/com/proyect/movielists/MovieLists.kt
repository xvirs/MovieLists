package com.proyect.movielists

import android.app.Application
import com.google.firebase.FirebaseApp
import com.proyect.movielists.di.useCaseModule
import com.proyect.movielists.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MovieLists : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidLogger()
            androidContext(this@MovieLists)
            modules(
                viewModelModule,
                useCaseModule
            )
        }
    }
}