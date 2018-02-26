package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.McLarenApplication;
import dagger.Component;
import dagger.android.AndroidInjector;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends AndroidInjector<McLarenApplication> {
    void inject(McLarenApplication application);

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<McLarenApplication> {
        public abstract AppComponent build();
    }
}
