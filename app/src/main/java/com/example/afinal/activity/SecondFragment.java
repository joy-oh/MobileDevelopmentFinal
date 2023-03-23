package com.example.afinal.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.afinal.R;
import com.example.afinal.databinding.FragmentFirstBinding;
import com.example.afinal.databinding.FragmentSecondBinding;

import org.jetbrains.annotations.NotNull;

public class SecondFragment extends Fragment {
    NavController navController;
    private FragmentSecondBinding binding;

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
        String title = getArguments().getString("title");
        binding.title.setText(title);
        String localname = getArguments().getString("localname");
        binding.localname.setText("Local name: " + localname);
        String name = getArguments().getString("name");
        binding.name.setText("Name: " + name);
        String date = getArguments().getString("date");
        binding.date.setText("Date: " + date);
        String country = getArguments().getString("country");
        binding.country.setText("Country code: " + country);

        Button back = binding.backButton;
        back.setOnClickListener(v->{
            navController.navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
        Button calendar = binding.calendarButton;
        calendar.setOnClickListener(v->{

        });
        Button searchBtn = binding.searchButton;
        searchBtn.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,"What is "+ localname + "?");
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
