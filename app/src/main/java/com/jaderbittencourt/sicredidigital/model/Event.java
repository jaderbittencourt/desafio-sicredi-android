package com.jaderbittencourt.sicredidigital.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

    public static final String OBJECT = "eventObject";

    private String id;

    private String title;

    private Double price;

    private String latitude;

    private String longitude;

    private String image;

    private String description;

    private Long date;

    @SerializedName("people")
    private List<People> peopleList;

    @SerializedName("cupons")
    private List<Coupon> coupons;

}
