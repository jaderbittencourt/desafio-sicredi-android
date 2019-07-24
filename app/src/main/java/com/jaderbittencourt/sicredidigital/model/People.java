package com.jaderbittencourt.sicredidigital.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class People {

    private String id;

    @SerializedName("eventId")
    private String eventId;

    private String name;

    private String picture;

}
