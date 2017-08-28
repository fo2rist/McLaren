package com.github.fo2rist.mclaren.dagger;

import com.github.fo2rist.mclaren.McLarenApplication;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(McLarenApplication app);
}
