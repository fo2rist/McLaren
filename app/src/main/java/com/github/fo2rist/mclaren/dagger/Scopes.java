package com.github.fo2rist.mclaren.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

public interface Scopes {
    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface PerActivity {
    }

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface PerFragment {
    }

    @Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface PerChildFragment {
    }
}
