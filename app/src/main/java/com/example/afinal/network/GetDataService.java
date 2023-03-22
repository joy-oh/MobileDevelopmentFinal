package com.example.afinal.network;

import com.example.afinal.model.LongWeekends;
import com.example.afinal.model.Next7daysHolidays;
import com.example.afinal.model.PublicHolidays;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("NextPublicHolidaysWorldwide")
    Call<List<Next7daysHolidays>> getNextHolidays();

    @GET("PublicHolidays/{year}/{countryCode}")
    Call<List<PublicHolidays>> getPublicHolidays(@Path("year") String year, @Path("countryCode") String countryCode);

    @GET("LongWeekend/{year}/{countryCode}")
    Call<List<LongWeekends>> getLongWeekends(@Path("year")String year, @Path("countryCode") String countryCode);
}
