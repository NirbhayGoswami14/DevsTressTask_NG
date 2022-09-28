package com.ncode.devstresstask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.ncode.devstresstask.database.DataBaseHelper;
import com.ncode.devstresstask.databinding.ActivityAddLocationBinding;

import java.util.Arrays;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity {
    private ActivityAddLocationBinding binding;
    private Context context = AddLocationActivity.this;
    private String TAG = "ADDLOCATION";
    private final int LOCATION_CODE = 101;
    private DataBaseHelper dataBaseHelper;
    private String loc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseHelper=new DataBaseHelper(context);

            loc_id=getIntent().getStringExtra("l_id");

        binding.fabSearchlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Places.isInitialized()) {
                    Places.initialize(context, getString(R.string.google_map_id));
                    Log.d(TAG, "onClick:==>");

                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(context);
                    startActivityForResult(intent, LOCATION_CODE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult:====>");
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                if(MainActivity.flag==0)
                {
                    dataBaseHelper.insertLocation(place.getName(),String.valueOf(place.getLatLng()),place.getAddress());
                }
                else
                {
                    dataBaseHelper.updateLocation(loc_id,place.getName(),String.valueOf(place.getLatLng()),place.getAddress());
                }
                Places.deinitialize();
                finish();
            }
            else if (resultCode==RESULT_CANCELED)
            {
                Log.d(TAG, "onActivityResult:==Fail");
            }
        }
    }
}