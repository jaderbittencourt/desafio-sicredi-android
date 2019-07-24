package com.jaderbittencourt.sicredidigital.viewmodel;

import com.jaderbittencourt.sicredidigital.api.EventsService;
import com.jaderbittencourt.sicredidigital.ui.interaction.MainInteraction;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class EventsViewModel {

    private final EventsService service;
    private com.jaderbittencourt.sicredidigital.common.interaction.LoaderInteraction interaction;
    private MainInteraction mainInteraction;
    private Disposable subscribe;

    @Inject
    public EventsViewModel(EventsService service) {
        this.service = service;
    }

    public void setInteraction(com.jaderbittencourt.sicredidigital.common.interaction.LoaderInteraction interaction) {
        this.interaction = interaction;
    }

    public void setMainInteraction(MainInteraction mainInteraction) {
        this.mainInteraction = mainInteraction;
    }

    public void getEvents() {
        interaction.showLoader();
        subscribe = service.getEvent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    interaction.hideLoader();
                    mainInteraction.loadAdapter(response);
                }, throwable -> {
                    interaction.hideLoader();
                    interaction.showError(throwable);
                });
    }

    public void dispose() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
        }
    }
}
