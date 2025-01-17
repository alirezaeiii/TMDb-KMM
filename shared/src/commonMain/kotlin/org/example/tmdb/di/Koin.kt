package org.example.tmdb.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.example.tmdb.data.network.jsonModule
import org.example.tmdb.data.network.ktorModule
import org.example.tmdb.data.repository.MovieFeedRepository
import org.example.tmdb.domain.repository.BaseFeedRepository
import org.example.tmdb.viewmodel.FeedViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            jsonModule,
            ktorModule,
            dispatcherModule,
            repositoryModule,
            viewModelModule,
            databaseModule()
        )
    }
}

val repositoryModule = module {
    single<BaseFeedRepository> { MovieFeedRepository(get(), get(), get(named("io"))) }
}

val viewModelModule = module {
    viewModelOf(::FeedViewModel)
}

val dispatcherModule = module {
    single(named("io")) { Dispatchers.IO }
}

expect fun databaseModule(): Module

fun getFeedViewModel(): FeedViewModel = KoinPlatform.getKoin().get()