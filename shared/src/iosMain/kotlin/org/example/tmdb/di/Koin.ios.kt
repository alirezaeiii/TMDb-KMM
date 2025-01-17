package org.example.tmdb.di

import org.example.tmdb.cache.Database
import org.example.tmdb.cache.IOSDatabaseDriverFactory
import org.koin.dsl.module

actual fun databaseModule() = module {
    single<Database> {
        Database(databaseDriverFactory = IOSDatabaseDriverFactory())
    }
}