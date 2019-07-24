package com.jaderbittencourt.sicredidigital.api;

import com.jaderbittencourt.sicredidigital.model.CheckInBody;
import com.jaderbittencourt.sicredidigital.model.Event;
import com.jaderbittencourt.sicredidigital.model.SimpleResposne;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EventsApi {

    @GET("events")
    Observable<List<Event>> getEvent();

    @POST("checkin")
    Observable<SimpleResposne> checkin(@Body CheckInBody checkInBody);
}
