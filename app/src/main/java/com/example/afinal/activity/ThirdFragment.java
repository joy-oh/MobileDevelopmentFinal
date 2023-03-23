package com.example.afinal.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.afinal.R;
import com.example.afinal.databinding.FragmentSecondBinding;
import com.example.afinal.databinding.FragmentThirdBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ThirdFragment extends Fragment {

    NavController navController;
    private FragmentThirdBinding binding;
    private String days;
    private String country;
    private String start;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            binding = FragmentThirdBinding.inflate(inflater, container, false);
            return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        title = getArguments().getString("title");
        binding.title.setText(title);
        days = getArguments().getString("days");
        binding.days.setText("Duration: " + days);
        start = getArguments().getString("start");
        binding.start.setText("Start day: " + start);
        String end = getArguments().getString("end");
        binding.end.setText("End day: " + end);
        country = getArguments().getString("country");
        binding.country.setText("Country Code: " + country);

        Button calendar = binding.calendarButton;
        calendar.setOnClickListener(v->addCalendarEvent(v));

        Button searchBtn = binding.searchButton;
        String search = "holiday in " + country + " beginning on " + start;
        searchBtn.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,"What is "+ search + "?");
            startActivity(intent);
        });
    }
    private void addCalendarEvent(View v) {
        String stringDur = days.substring(0,1);
        int numDays = Integer.parseInt(stringDur);
        Calendar beginTime = Calendar.getInstance();
        int year = Integer.parseInt(start.substring(0,4));
        int month = Integer.parseInt(start.substring(5,7)) -1;
        int day = Integer.parseInt(start.substring(8));
        beginTime.set(year, month, day, 00, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day+ numDays-1, 00, 00);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, country)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }
}