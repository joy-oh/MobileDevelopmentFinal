package com.example.afinal.model;

import com.google.gson.annotations.SerializedName;

public class PublicHolidays {
    @SerializedName("date")
    private String date;
    @SerializedName("localName")
    private String localName;
    @SerializedName("name")
    private String name;

    public PublicHolidays(String date, String localName, String name){
        this.date = date;
        this.localName = localName;
        this.name = name;
    }
    public String getDate(){
        return date;
    }
    public String getLocalName(){
        return localName;
    }
    public String getName(){
        return name;
    }


}
