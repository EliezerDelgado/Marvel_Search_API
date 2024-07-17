package com.eliezer.marvel_characters.core.modules
import com.eliezer.marvel_characters.core.domain.DefaultDispatcher
import com.eliezer.marvel_characters.core.domain.IoDispatcher
import com.eliezer.marvel_characters.core.domain.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.*
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @DefaultDispatcher
    @Singleton
    @Provides
    fun providesDefaultScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @MainDispatcher
    @Singleton
    @Provides
    fun providesMainScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)

    @IoDispatcher
    @Singleton
    @Provides
    fun providesIoScope(
        @IoDispatcher ioDispatcher:CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
