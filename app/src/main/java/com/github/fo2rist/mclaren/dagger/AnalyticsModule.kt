package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.analytics.EventsLogger
import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AnalyticsModule {
    @Binds
    @Scopes.PerActivity
    fun provideEventsLogger(logger: EventsLoggerImpl): EventsLogger
}
