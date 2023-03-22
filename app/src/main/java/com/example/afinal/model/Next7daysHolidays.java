package com.example.afinal.model;
import com.google.gson.annotations.SerializedName;
public class Next7daysHolidays {
    @SerializedName("date")
    private String date;
    @SerializedName("name")
    private String name;
    @SerializedName("countryCode")
    private String countryCode;

    public Next7daysHolidays(String date, String name, String countryCode){
        this.date = date;
        this.name = name;
        this.countryCode = countryCode;
    }
    public String getDate(){
        return date;
    }
    public String getName(){
        return name;
    }
    public String getCountry(){
        return countryCode;
    }
}
