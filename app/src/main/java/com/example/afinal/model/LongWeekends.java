package com.example.afinal.model;

import com.google.gson.annotations.SerializedName;

public class LongWeekends {
    @SerializedName("dayCount")
    private String days;
    @SerializedName("startDate")
    private String start;
    @SerializedName("endDate")
    private String end;
    public LongWeekends(String days, String start, String end){
        this.days = days;
        this.start = start;
        this.end = end;
    }
    public String getDays(){
        return days + " days";
    }
    public String getStart(){
        return start;
    }
    public String getEnd(){
        return end;
    }
}
