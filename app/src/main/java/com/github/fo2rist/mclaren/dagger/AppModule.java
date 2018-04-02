package com.github.fo2rist.mclaren.dagger;

import android.content.Context;

import com.github.fo2rist.mclaren.McLarenApplication;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl;
import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepositoryImpl;
import com.github.fo2rist.mclaren.repository.StoryStreamRepository;
import com.github.fo2rist.mclaren.repository.StoryStreamRepositoryImpl;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.web.DefaultOkHttpClientFactory;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.McLarenFeedWebServiceImpl;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.StoryStreamWebServiceImpl;
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
    @Scopes.PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @Binds
    abstract Context application(McLarenApplication application);

    @Provides
    @Singleton
    static FeedRepository provideMcLarenRepository(McLarenFeedRepositoryImpl repository) {
        return repository;
    }

    @Provides
    @Singleton
    static McLarenFeedWebService provideMcLarenFeedWebservice(McLarenFeedWebServiceImpl webService) {
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
    static FeedRepositoryPubSub provideFeedRepositoryPubSub(FeedRepositoryPubSubImpl pubSub) {
        return pubSub;
    }

    @Provides
    @Singleton
    static FeedHistoryPredictor provideHistoryPredictor(McLarenFeedHistoryPredictor historyPredictor) {
        return historyPredictor;
    }

    @Provides
    @Singleton
    static EventsLogger provideEventsLogger(EventsLoggerImpl logger) {
        return logger;
    }

    @Provides
    @Named("web-okhttp")
    @Singleton
    static OkHttpClient provideWebOkHttpClient(Context context) {
        return DefaultOkHttpClientFactory.getInstance(context);
    }
}
