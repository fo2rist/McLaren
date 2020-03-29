package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.repository.RepositoryEventBusImpl
import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepository
import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepositoryEventBus
import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepositoryImpl
import com.github.fo2rist.mclaren.web.feed.McLarenWebServiceImpl
import com.github.fo2rist.mclaren.web.transmission.TransmissionWebService
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface TransmissionRepositoryModule {

    @Binds
    @Singleton
    fun provideTransmissionRepository(repository: TransmissionRepositoryImpl): TransmissionRepository

    @Binds
    @Singleton
    fun provideTransmissionWebService(webService: McLarenWebServiceImpl): TransmissionWebService

    @Binds
    @Singleton
    fun provideTransmissionRepositoryEventBus(eventBus: RepositoryEventBusImpl): TransmissionRepositoryEventBus
}
