package com.jaderbittencourt.sicredidigital;

import android.app.Application;

import com.jaderbittencourt.sicredidigital.di.AppComponent;
import com.jaderbittencourt.sicredidigital.di.AppModule;
import com.jaderbittencourt.sicredidigital.di.DaggerAppComponent;
import com.jaderbittencourt.sicredidigital.di.NetworkModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class App extends Application {

    private static AppComponent component;

    public void onCreate() {
        super.onCreate();
        initDagger();
        initPicasso();
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    private void initDagger() {
        if (component == null) {
            component =
                    DaggerAppComponent.builder()
                            .appModule(new AppModule(this))
                            .networkModule(new NetworkModule())
                            .build();
        }
    }

    public static AppComponent getAppComponent() {
        return component;
    }
}