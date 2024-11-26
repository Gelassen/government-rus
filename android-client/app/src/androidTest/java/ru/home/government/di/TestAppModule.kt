package ru.home.government.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [])
class TestAppModule(val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context {
        return context
    }
}