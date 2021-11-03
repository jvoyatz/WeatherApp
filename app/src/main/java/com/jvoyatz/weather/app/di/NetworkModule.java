package com.jvoyatz.weather.app.di;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.jvoyatz.weather.app.api.WorldWeatherAPI;
import com.jvoyatz.weather.app.api.config.LiveDataCallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

/**
 * There are 3 ways to add dependencies to the generated graph using Dagger Hilt.
 * <p>
 * a)Use @Inject when you own the classes
 * b)Use @Provides for classes which aren't yours.
 * c)Use @Binds when your injections is about an interface
 * <p>
 * In this class, we show how to inject using the second option.
 * Because Retrofit instance is being generated using the Bulder pattern,
 * we are not able to use @Inject annotation. For this reason, we select @Provides annotation.
 * <p>
 * Using this annotation, we can make this dependency available and injectable to our app.
 * <p>
 * NOTE: So as to use @Binds annotation, you need to define the @Module class
 * as abstract as well as the method annotated with @Binds.
 * So no implementation provided at all in compare with the other way using @Provides
 */
@Module
@InstallIn({SingletonComponent.class})
public class NetworkModule {
    static String ENDPOINT = "https://api.worldweatheronline.com/premium/v1/";

    @Singleton
    @Provides
    public static OkHttpClient provideOkHttp(@NonNull @Named("logInterceptor") Interceptor loggingInterceptor,
                                             @NonNull @Named("authInterceptor") Interceptor authInterceptor
            /*@NonNull @Named("netInterceptor") Interceptor networkConnectionInterceptor*/) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Singleton
    @Provides
    public static Gson provideGson(){
        return new Gson();
    }

    @Singleton
    @Provides
    @NotNull
    public static Retrofit provideRetrofit(@NonNull OkHttpClient okHttpClient, @NonNull LiveDataCallAdapterFactory liveDataCallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(liveDataCallAdapterFactory)
                .build();
    }

    @Provides
    @NotNull
    @Named("logInterceptor")
    public Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    @NotNull
    public static WorldWeatherAPI provideWorldWeatherService(Retrofit retrofit){
        return retrofit.create(WorldWeatherAPI.class);
    }
}
