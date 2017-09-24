package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.repository.FeedRepository;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSubImpl;
import com.github.fo2rist.mclaren.repository.McLarenFeedRepository;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.McLarenFeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedWebservice;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class AppModule {
    @Scopes.PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    @Provides
    @Singleton
    static FeedRepository provideRepository(McLarenFeedRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    static FeedWebsevice provideFeedWebservice(McLarenFeedWebservice webservice) {
        return webservice;
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
}
