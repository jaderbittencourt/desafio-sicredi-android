package com.jaderbittencourt.sicredidigital.di;

import com.jaderbittencourt.sicredidigital.ui.DetailsActivity;
import com.jaderbittencourt.sicredidigital.ui.MainActivity;
import com.jaderbittencourt.sicredidigital.ui.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(DetailsActivity detailsActivity);
}
