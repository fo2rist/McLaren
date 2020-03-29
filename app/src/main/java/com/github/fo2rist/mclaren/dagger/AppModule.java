package com.github.fo2rist.mclaren.dagger;

import android.content.Context;

import com.github.fo2rist.mclaren.McLarenApplication;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.analytics.EventsLoggerImpl;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionActivity;
import com.github.fo2rist.mclaren.web.OkHttpClientFactory;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module(includes = {
        AndroidSupportInjectionModule.class,
        FeedRepositoriesModule.class,
        TransmissionRepositoryModule.class,
        RemoteConfigRepositoryModule.class,
})
abstract class AppModule {
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

    @Scopes.PerActivity
    @ContributesAndroidInjector
    abstract CircuitDetailsActivity circuitDetailsActivityInjector();
    //endregion

    //region global level providers
    @Binds
    @Singleton
    abstract Context applicationContext(McLarenApplication application);

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
