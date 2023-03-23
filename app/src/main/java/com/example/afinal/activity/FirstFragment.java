package com.example.afinal.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.adapter.CustomAdapter;
import com.example.afinal.databinding.FragmentFirstBinding;
import com.example.afinal.model.LongWeekends;
import com.example.afinal.model.Next7daysHolidays;
import com.example.afinal.model.PublicHolidays;
import com.example.afinal.network.GetDataService;
import com.example.afinal.network.RetrofitClientInstance;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class FirstFragment extends Fragment implements RecyclerViewInterface
{
    NavController navController;
    private FragmentFirstBinding binding;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String queryString;
    GetDataService service;
    private List<LongWeekends> lngwkndData;
    private List<PublicHolidays> publicholidaysData;
    private List<Next7daysHolidays> nextholidaysData;
    int progress = 0;

    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        Button btn = binding.searchButton;

        binding.nextHolidays.setOnClickListener(this::onRadioButtonClicked);
        binding.holidayCountryYear.setOnClickListener(this::onRadioButtonClicked);
        binding.longWeekend.setOnClickListener(this::onRadioButtonClicked);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        btn.setOnClickListener(v-> {
            if(binding.error.getVisibility() == View.VISIBLE){
                binding.error.setVisibility(View.INVISIBLE);
            }
            progressBar = binding.progressBar;
            progressBar.setVisibility(View.VISIBLE);
            setProgressValue(progress);
            callEnqueue(v);}
        );
    }
    private void setProgressValue(int progress) {
        progressBar.setProgress(progress);
        Thread thread = new Thread(() -> {
            try{Thread.sleep(1000);}
            catch(InterruptedException e)
            {e.printStackTrace();}
            setProgressValue(progress+10);
        });
        thread.start();
    }

    public void callEnqueue(View v){
        switch (queryString) {
            case "nextHolidays": {
                Call<List<Next7daysHolidays>> call = service.getNextHolidays();
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                if (networkInfo != null && networkInfo.isConnected() && call != null) {
                    call.enqueue(new Callback<List<Next7daysHolidays>>() {
                        @Override
                        @EverythingIsNonNull
                        public void onResponse(Call<List<Next7daysHolidays>> call, Response<List<Next7daysHolidays>> response) {
                            nextholidaysData = response.body();
                            generateNext7daysList(nextholidaysData);
                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        @EverythingIsNonNull
                        public void onFailure(Call<List<Next7daysHolidays>> call, Throwable t) {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            Toast.makeText(getActivity(), "Try later!", Toast.LENGTH_SHORT);
                        }
                    });
                }
                break;
            }
            case "holidayCountryYear": {
                Call<List<PublicHolidays>> call = service.getPublicHolidays(binding.year.getText().toString(), binding.countryCode.getText().toString().toUpperCase());
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                if (networkInfo != null && networkInfo.isConnected() && call != null) {
                    call.enqueue(new Callback<List<PublicHolidays>>() {
                        @Override
                        public void onResponse(Call<List<PublicHolidays>> call, Response<List<PublicHolidays>> response) {
                            publicholidaysData = response.body();
                            if (publicholidaysData != null) {
                                generateHolidaysYearCountryList(publicholidaysData);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                if (recyclerView != null && recyclerView.getVisibility() == View.VISIBLE) {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                }
                                binding.error.setVisibility(View.VISIBLE);
                                binding.error.setText(R.string.error);
                            }
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<List<PublicHolidays>> call, Throwable t) {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            Toast.makeText(getActivity(), "Try later!", Toast.LENGTH_SHORT);
                        }
                    });
                }
                break;
            }
            case "longWeekend": {
                Call<List<LongWeekends>> call = service.getLongWeekends(binding.longWeekendYear.getText().toString(), binding.longWeekendCountryCode.getText().toString().toUpperCase());
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (connMgr != null) {
                    networkInfo = connMgr.getActiveNetworkInfo();
                }
                if (networkInfo != null && networkInfo.isConnected() && call != null) {
                    call.enqueue(new Callback<List<LongWeekends>>() {
                        @Override
                        public void onResponse(Call<List<LongWeekends>> call, Response<List<LongWeekends>> response) {
                            lngwkndData = response.body();
                            if (lngwkndData != null) {
                                generateLongWeekendYearCountryList(lngwkndData);
                                recyclerView.setVisibility(View.VISIBLE);
                            } else {
                                if (recyclerView != null && recyclerView.getVisibility() == View.VISIBLE) {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                }
                                binding.error.setVisibility(View.VISIBLE);
                                binding.error.setText(R.string.error);
                            }
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<List<LongWeekends>> call, Throwable t) {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                            Toast.makeText(getActivity(), "Try later!", Toast.LENGTH_SHORT);
                        }
                    });
                }

                break;
            }
        }
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.nextHolidays:
                if(checked){
                    queryString = "nextHolidays";
                    if(binding.error.getVisibility()==View.VISIBLE){
                        binding.error.setVisibility(View.INVISIBLE);
                    }
                    if(recyclerView!=null && recyclerView.getVisibility()==View.VISIBLE){
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.holidayCountryYear:
                if(checked){
                    queryString = "holidayCountryYear";
                    if(binding.error.getVisibility()==View.VISIBLE){
                        binding.error.setVisibility(View.INVISIBLE);
                    }
                    if(recyclerView!=null && recyclerView.getVisibility()==View.VISIBLE){
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.longWeekend:
                if(checked){
                    queryString = "longWeekend";
                    if(binding.error.getVisibility()==View.VISIBLE){
                        binding.error.setVisibility(View.INVISIBLE);
                    }
                    if(recyclerView!=null && recyclerView.getVisibility()==View.VISIBLE){
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }
    private static <T> List<Object> convert(List<T>list){
        List<Object> newList = new ArrayList<>();
        for(T ob : list){
            newList.add(ob);
        }
        return newList;
    }
    private void generateNext7daysList(List<Next7daysHolidays> next7daysList){
        recyclerView = binding.customRecyclerView;
        recyclerView.setNestedScrollingEnabled(true);
        CustomAdapter adapter;
        adapter = new CustomAdapter(getActivity(), convert(next7daysList), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void generateHolidaysYearCountryList(List<PublicHolidays> publicHolidaysList){
        recyclerView = binding.customRecyclerView;
        recyclerView.setNestedScrollingEnabled(true);
        CustomAdapter adapter;
        adapter = new CustomAdapter(getActivity(),convert(publicHolidaysList),this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void generateLongWeekendYearCountryList(List<LongWeekends> longWeekendsList){
        recyclerView = binding.customRecyclerView;
        recyclerView.setNestedScrollingEnabled(true);
        CustomAdapter adapter;
        adapter = new CustomAdapter(getActivity(),convert(longWeekendsList),this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        switch(queryString){
            case "nextHolidays":
                Next7daysHolidays data1 = nextholidaysData.get(position);
                bundle.putString("title", "Next Holiday");
                bundle.putString("date",data1.getDate());
                bundle.putString("name",data1.getName() );
                bundle.putString("localname", data1.getName());
                bundle.putString("country", data1.getCountry());
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                break;
            case "holidayCountryYear":
                PublicHolidays data2 = publicholidaysData.get(position);
                bundle.putString("title", "Public Holiday");
                bundle.putString("date", data2.getDate());
                bundle.putString("name", data2.getName());
                bundle.putString("localname", data2.getLocalName());
                bundle.putString("country", binding.countryCode.getText().toString().toUpperCase());
                navController.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                break;
            case "longWeekend":
                LongWeekends data3 = lngwkndData.get(position);
                bundle.putString("title", "Long Weekend");
                bundle.putString("days", data3.getDays());
                bundle.putString("start", data3.getStart());
                bundle.putString("end", data3.getEnd());
                bundle.putString("country", binding.longWeekendCountryCode.getText().toString().toUpperCase());
                navController.navigate(R.id.action_FirstFragment_to_ThirdFragment,bundle);

                break;
        }

    }
}
