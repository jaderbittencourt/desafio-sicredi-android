package com.jaderbittencourt.sicredidigital.api;

import com.jaderbittencourt.sicredidigital.model.CheckInBody;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.model.SimpleResposne;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class EventsService implements EventsApi {

    private final EventsApi eventsApi;

    @Inject
    public EventsService(EventsApi eventsApi) {
        this.eventsApi = eventsApi;
    }

    @Override
    public Observable<List<Event>> getEvent() {
        return eventsApi.getEvent();
    }

    @Override
    public Observable<SimpleResposne> checkin(CheckInBody checkInBody) {
        return eventsApi.checkin(checkInBody);
    }
}
