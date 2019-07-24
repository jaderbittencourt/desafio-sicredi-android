package com.jaderbittencourt.sicredidigital.ui.interaction;

import com.jaderbittencourt.sicredidigital.model.CheckInBody;

public interface DetailsInteraction {

    void openMaps(Double latitude, Double longitude);
    void openDialog(String eventID);
    void openPeopleDialog(String name, String image);
    void share(String shareText);
    void doCheckin(CheckInBody checkInBody);
}
