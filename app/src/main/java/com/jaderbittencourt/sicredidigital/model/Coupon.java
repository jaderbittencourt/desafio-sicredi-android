package com.jaderbittencourt.sicredidigital.model;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupon {

    private String id;

    @SerializedName("eventId")
    private String eventId;

    private Integer discount;

}
