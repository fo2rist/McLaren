package com.github.fo2rist.mclaren.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

public class Scopes {
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PerActivity {
    }

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PerFragment {
    }

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PerChildFragment {
    }
}
