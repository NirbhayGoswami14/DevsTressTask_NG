package com.ncode.devstresstask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ncode.devstresstask.database.DataBaseHelper;
import com.ncode.devstresstask.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMapBinding binding;
    private GoogleMap map;
    private DataBaseHelper dataBaseHelper;
    private Context context=MapActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseHelper=new DataBaseHelper(context);
        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;

        for (int i=0;i<dataBaseHelper.getLocationList().size();i++)
        {
            String tempString="lat/lng: (19.0759837,72.8776559)";
           String[] latlng= dataBaseHelper.getLocationList().get(i).getLocLatLang().split("lat/lng: ");
           String[] latlng1=latlng[1].split(",");
            latlng1[0].replace("(","");
            latlng1[1].replace(")","");
            Log.d("lang", "onMapReady:"+latlng1[0]);
          /* double latitude = Double.parseDouble(latlng1[0]);
            double longitude = Double.parseDouble(latlng1[1]);

            map.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(dataBaseHelper.getLocationList().get(i).getLocName()));*/
        }
    }
}