package com.example.afinal.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.afinal.R;
import com.example.afinal.databinding.FragmentSecondBinding;
import com.example.afinal.databinding.FragmentThirdBinding;

import org.jetbrains.annotations.NotNull;

public class ThirdFragment extends Fragment {

    NavController navController;
    private FragmentThirdBinding binding;

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
        String title = getArguments().getString("title");
        binding.title.setText(title);
        String days = getArguments().getString("days");
        binding.days.setText("Duration: " + days);
        String start = getArguments().getString("start");
        binding.start.setText("Start day: " + start);
        String end = getArguments().getString("end");
        binding.end.setText(end);
        String country = getArguments().getString("country");
        binding.country.setText("End day: " + country);
        Button back = binding.backButton;
        back.setOnClickListener(v->{
            navController.navigate(R.id.action_ThirdFragment_to_FirstFragment);
        });

        Button searchBtn = binding.searchButton;
        String search = "holiday in " + country + " beginning on " + start;
        searchBtn.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,"What is "+ search + "?");
            startActivity(intent);
        });
    }
}