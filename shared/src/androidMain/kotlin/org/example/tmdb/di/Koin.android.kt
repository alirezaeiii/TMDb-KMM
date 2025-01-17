package org.example.tmdb.di

import org.example.tmdb.cache.AndroidDatabaseDriverFactory
import org.example.tmdb.cache.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun databaseModule() = module {
    single<Database> {
        Database(databaseDriverFactory = AndroidDatabaseDriverFactory(androidContext()))
    }
}