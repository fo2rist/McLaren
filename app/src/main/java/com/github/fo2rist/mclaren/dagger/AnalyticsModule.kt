package com.github.fo2rist.mclaren.dagger

import com.github.fo2rist.mclaren.analytics.Analytics
import com.github.fo2rist.mclaren.analytics.AnalyticsImpl
import dagger.Binds
import dagger.Module

@Module
interface AnalyticsModule {
    @Binds
    @Scopes.PerActivity
    fun provideEventsLogger(analytics: AnalyticsImpl): Analytics
}
