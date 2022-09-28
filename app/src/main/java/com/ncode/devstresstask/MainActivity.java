package com.ncode.devstresstask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ncode.devstresstask.adapter.LocationAdapter;
import com.ncode.devstresstask.database.DataBaseHelper;
import com.ncode.devstresstask.databinding.ActivityMainBinding;
import com.ncode.devstresstask.model.LocationModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context = MainActivity.this;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<LocationModel> locationList;
    private int sortTag = 0;
    public static int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseHelper = new DataBaseHelper(context);
        if (!dataBaseHelper.getLocationList().isEmpty()) {
            locationList = dataBaseHelper.getLocationList();
        }
        else
        {
            locationList=new ArrayList<>();
        }
        binding.btnAddlocation.setOnClickListener(view -> {
            startActivity(new Intent(context, AddLocationActivity.class));
        });


        setupRecyclerView();

        binding.btnShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sortTag == 0) {
                    Collections.sort(locationList, new Comparator<LocationModel>() {
                        @Override
                        public int compare(LocationModel locationModel, LocationModel t1) {
                            sortTag = 1;
                            return locationModel.getLocName().compareToIgnoreCase(t1.getLocName());
                        }
                    });
                    binding.btnShort.setText("Z-A");
                }
                else {
                    Collections.sort(locationList, new Comparator<LocationModel>() {
                        @Override
                        public int compare(LocationModel locationModel, LocationModel t1) {
                            sortTag = 0;
                            return t1.getLocName().compareToIgnoreCase(locationModel.getLocName());
                        }
                    });
                    binding.btnShort.setText("A-Z");
                }
                setupRecyclerView();
            }
        });

        binding.btnShowroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MapActivity.class));
            }
        });
    }

    private void setupRecyclerView() {

        if (!locationList.isEmpty()) {
            LocationAdapter locationAdapter = new LocationAdapter(context, locationList);
            locationAdapter.setOnDeleteLocation(new LocationAdapter.OnDeleteLocation() {
                @Override
                public void delete(LocationModel locationModel, int position) {
                    dataBaseHelper.deleteLocation(locationModel.getId());
                    locationAdapter.notifyItemRemoved(position);
                }
            });
            binding.rcLocationList.setLayoutManager(new LinearLayoutManager(context));
            binding.rcLocationList.setAdapter(locationAdapter);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        flag=0;
        if (!dataBaseHelper.getLocationList().isEmpty()) {
            locationList = dataBaseHelper.getLocationList();

            setupRecyclerView();
        }

    }
}