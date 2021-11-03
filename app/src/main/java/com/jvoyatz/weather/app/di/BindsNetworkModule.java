package com.jvoyatz.weather.app.di;

import com.jvoyatz.weather.app.api.config.AuthInterceptor;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;

/**
 * There are 3 ways to add dependencies to the generated graph using Dagger Hilt.
 * <p>
 * a)Use @Inject when you own the classes
 * b)Use @Provides for classes which aren't yours.
 * c)Use @Binds when your injections is about an interface
 * <p>
 * In this class, we show how to inject using the third option.
 * Interfaces do not have constructors, so we cannot use the @Inject annotation.
 * However, if there is an one to one mapping between the interfaces and its implementation, @Binds annotation
 * is our way to make this interface injectable.
 * <p>
 * NOTE: So as to use @Binds annotation, you need to define the @Module class
 * as abstract as well as the method annotated with @Binds.
 * So no implementation provided at all in compare with the other way using @Provides
 */
@Module
@InstallIn({SingletonComponent.class})
public abstract class BindsNetworkModule {

    @Binds
    @Named("authInterceptor")
    public abstract Interceptor bindAuthInterceptor(AuthInterceptor authInterceptor);

}
