package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.repository.DriversRepository
import com.github.fo2rist.mclaren.repository.DriversRepositoryImpl
import com.github.fo2rist.mclaren.repository.RaceCalendarRepository
import com.github.fo2rist.mclaren.repository.RaceCalendarRepositoryImpl
import com.github.fo2rist.mclaren.web.FirebaseRemoteConfigService
import com.github.fo2rist.mclaren.web.RemoteConfigService
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RemoteConfigRepositoryModule {

    @Binds
    @Singleton
    fun provideCalendarRepository(calendarRepository: RaceCalendarRepositoryImpl): RaceCalendarRepository

    @Binds
    @Singleton
    fun provideDriversRepository(driversRepository: DriversRepositoryImpl): DriversRepository

    @Binds
    @Singleton
    fun provideRemoteConfigService(service: FirebaseRemoteConfigService): RemoteConfigService
}
