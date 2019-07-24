package com.jaderbittencourt.sicredidigital.di;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.jaderbittencourt.sicredidigital.api.EventsApi;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final int SECONDS = 60;

    public NetworkModule() {
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 100 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>)
                (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));

        return gsonBuilder.create();
    }

    @Provides
    OkHttpClient provideOkhttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(SECONDS, TimeUnit.SECONDS)
                .connectTimeout(SECONDS, TimeUnit.SECONDS)
                .writeTimeout(SECONDS, TimeUnit.SECONDS);
        return client.build();
    }

    @Provides
    @Singleton
    EventsApi provideEventsApi(Gson gson, OkHttpClient okHttpClient) {
        String BASE_URL = "http://5b840ba5db24a100142dcd8c.mockapi.io/api/";
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(EventsApi.class);

    }

}
