package com.example.afinal.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.afinal.databinding.FragmentSecondBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class SecondFragment extends Fragment {
    NavController navController;
    private FragmentSecondBinding binding;
    private String title;
    private String name;
    private String country;
    private String date;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        title = getArguments().getString("title");
        binding.title.setText(title);
        String localname = getArguments().getString("localname");
        binding.localname.setText("Local name: " + localname);
        name = getArguments().getString("name");
        binding.name.setText("Name: " + name);
        date = getArguments().getString("date");
        binding.date.setText("Date: " + date);
        country = getArguments().getString("country");
        binding.country.setText("Country code: " + country);

        Button calendar = binding.calendarButton;
        calendar.setOnClickListener(v->addCalendarEvent(v));
        Button searchBtn = binding.searchButton;
        searchBtn.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,"What is "+ localname + "?");
            startActivity(intent);
        });
    }

    private void addCalendarEvent(View v) {
        Calendar beginTime = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7)) -1;
        int day = Integer.parseInt(date.substring(8));
        beginTime.set(year, month, day, 00, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day+1, 00, 00);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Events.TITLE, name)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, country)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
