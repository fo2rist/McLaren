package com.github.fo2rist.mclaren.dagger;

import android.content.Context;

import com.github.fo2rist.mclaren.McLarenApplication;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepositoryImpl;
import com.github.fo2rist.mclaren.repository.RaceCalendarRepository;
import com.github.fo2rist.mclaren.repository.RaceCalendarRepositoryImpl;
import com.github.fo2rist.mclaren.repository.RepositoryEventBusImpl;
import com.github.fo2rist.mclaren.repository.StoryStreamRepositoryImpl;
import com.github.fo2rist.mclaren.repository.TransmissionRepository;
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus;
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryImpl;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionActivity;
import com.github.fo2rist.mclaren.web.FirebaseRemoteConfigService;
import com.github.fo2rist.mclaren.web.OkHttpClientFactory;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.McLarenWebServiceImpl;
import com.github.fo2rist.mclaren.web.RemoteConfigService;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.StoryStreamWebServiceImpl;
import com.github.fo2rist.mclaren.web.TransmissionWebService;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class AppModule {
    //region Activity injectors
    @Scopes.PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @Scopes.PerActivity
    @ContributesAndroidInjector(modules = PreviewActivityModule.class)
    abstract PreviewActivity previewActivityInjector();

    @Scopes.PerActivity
    @ContributesAndroidInjector(modules = TransmissionActivityModule.class)
    abstract TransmissionActivity transmissionActivityInjector();
    //endregion

    //region global level providers
    @Binds
    @Singleton
    abstract Context applicationContext(McLarenApplication application);

    @Binds
    @Named("mclaren-feed")
    @Singleton
    abstract FeedRepository provideMcLarenRepository(McLarenFeedRepositoryImpl repository);

    @Binds
    @Singleton
    abstract McLarenFeedWebService provideMcLarenFeedWebservice(McLarenWebServiceImpl webService);


    @Binds
    @Named("storystream-feed")
    @Singleton
    abstract FeedRepository provideStoryStreamRepository(StoryStreamRepositoryImpl repository);

    @Binds
    @Singleton
    abstract StoryStreamWebService provideStoryStreamWebservice(StoryStreamWebServiceImpl webService);

    @Binds
    @Singleton
    abstract FeedRepositoryEventBus provideFeedRepositoryEventBus(RepositoryEventBusImpl eventBus);

    @Binds
    @Singleton
    abstract FeedHistoryPredictor provideHistoryPredictor(McLarenFeedHistoryPredictor historyPredictor);

    @Binds
    @Singleton
    abstract TransmissionRepository provideTransmissionRepository(TransmissionRepositoryImpl repository);

    @Binds
    @Singleton
    abstract TransmissionWebService provideTransmissionWebService(McLarenWebServiceImpl webService);

    @Binds
    @Singleton
    abstract TransmissionRepositoryEventBus provideTransmissionRepositoryEventBus(RepositoryEventBusImpl eventBus);

    @Binds
    @Singleton
    abstract RaceCalendarRepository provideCalendarRepository(RaceCalendarRepositoryImpl calendarRepository);

    @Binds
    @Singleton
    abstract RemoteConfigService provideRemoteConfigService(FirebaseRemoteConfigService service);

    @Binds
    @Singleton
    abstract EventsLogger provideEventsLogger(EventsLoggerImpl logger);

    @Provides
    @Named("web-okhttp")
    @Singleton
    static OkHttpClient provideWebOkHttpClient(Context context) {
        return OkHttpClientFactory.createWebClient(context);
    }
    //endregion
}
