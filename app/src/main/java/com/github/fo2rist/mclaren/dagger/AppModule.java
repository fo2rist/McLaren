package com.github.fo2rist.mclaren.dagger;

import android.content.Context;

import com.github.fo2rist.mclaren.McLarenApplication;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepositoryImpl;
import com.github.fo2rist.mclaren.repository.RepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.StoryStreamRepository;
import com.github.fo2rist.mclaren.repository.StoryStreamRepositoryImpl;
import com.github.fo2rist.mclaren.repository.TransmissionRepository;
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryImpl;
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.ui.calendar.CalendarEventsLoader;
import com.github.fo2rist.mclaren.ui.calendar.CalendarEventsLoaderImpl;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionActivity;
import com.github.fo2rist.mclaren.web.DefaultOkHttpClientFactory;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.McLarenWebServiceImpl;
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
    abstract Context application(McLarenApplication application);

    @Provides
    @Singleton
    static FeedRepository provideMcLarenRepository(McLarenFeedRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    static McLarenFeedWebService provideMcLarenFeedWebservice(McLarenWebServiceImpl webService) {
        return webService;
    }


    @Provides
    @Singleton
    static StoryStreamRepository provideStoryStreamRepository(StoryStreamRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    static StoryStreamWebService provideStoryStreamWebservice(StoryStreamWebServiceImpl webService) {
        return webService;
    }

    @Provides
    @Singleton
    static FeedRepositoryPubSub provideFeedRepositoryPubSub(RepositoryPubSubImpl pubSub) {
        return pubSub;
    }

    @Provides
    @Singleton
    static FeedHistoryPredictor provideHistoryPredictor(McLarenFeedHistoryPredictor historyPredictor) {
        return historyPredictor;
    }

    @Provides
    @Singleton
    static TransmissionRepository provideTransmissionRepository(TransmissionRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    static TransmissionWebService provideTransmissionWebService(McLarenWebServiceImpl webService) {
        return webService;
    }

    @Provides
    @Singleton
    static TransmissionRepositoryPubSub provideTransmissionRepositoryPubSub(RepositoryPubSubImpl pubSub) {
        return pubSub;
    }

    @Provides
    @Singleton
    static EventsLogger provideEventsLogger(EventsLoggerImpl logger) {
        return logger;
    }

    @Provides
    @Singleton
    static CalendarEventsLoader provideCalendarEventsLoader(CalendarEventsLoaderImpl loader) {
        return loader;
    }

    @Provides
    @Named("web-okhttp")
    @Singleton
    static OkHttpClient provideWebOkHttpClient(Context context) {
        return DefaultOkHttpClientFactory.getInstance(context);
    }
    //endregion
}
