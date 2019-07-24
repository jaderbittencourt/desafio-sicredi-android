package com.jaderbittencourt.sicredidigital.ui.interaction;

import com.jaderbittencourt.sicredidigital.model.Event;

import java.util.List;

public interface MainInteraction {

    void loadAdapter(List<Event> events);
    void openDetails(Event event);


}
