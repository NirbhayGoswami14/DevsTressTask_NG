package com.ncode.devstresstask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ncode.devstresstask.database.DataBaseHelper;
import com.ncode.devstresstask.databinding.ActivityMapBinding;
import com.ncode.devstresstask.directionhelper.PointParser;
import com.ncode.devstresstask.directionhelper.TaskLoadCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMapBinding binding;
    private GoogleMap map;
    private DataBaseHelper dataBaseHelper;
    private Context context=MapActivity.this;
    private List<LatLng> latLngList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseHelper=new DataBaseHelper(context);
        latLngList=new ArrayList<>();
        SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map=googleMap;

        for (int i=0;i<dataBaseHelper.getLocationList().size();i++)
        {
            /*String tempString="lat/lng: (19.0759837,72.8776559)";
           String[] latlng= dataBaseHelper.getLocationList().get(i).getLocLatLang().split("lat/lng: ");
           String[] latlng1=latlng[1].split(",");
            latlng1[0].replace("(","");
            latlng1[1].replace(")","");
            Log.d("lang", "onMapReady:"+latlng1[0]);*/
           double latitude = Double.parseDouble(dataBaseHelper.getLocationList().get(i).getLat());
            double longitude = Double.parseDouble(dataBaseHelper.getLocationList().get(i).getLng());

            LatLng latLng=new LatLng(latitude,longitude);
            latLngList.add(new LatLng(latitude,longitude));
            map.addMarker(new MarkerOptions().position(latLng).title(dataBaseHelper.getLocationList().get(i).getLocName()));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
                      /*  Polyline line = map.addPolyline(new PolylineOptions()
                    .add(new LatLng(22.3038945,70.80215989999999), new LatLng(19.0759837,72.8776559))
                    .width(5)
                    .color(Color.RED));
*/
        }

        PolylineOptions polylineOptions=new PolylineOptions().addAll(latLngList).color(Color.RED).width(5);
        map.addPolyline(polylineOptions);
        //new FetchURL(context).execute(getRequestUrl(latLngList.get(0),latLngList.get(1)),"driving");
        //getRequestUrl(latLngList.get(0),latLngList.get(1));
    }


    /*private String getRequestUrl(LatLng start,LatLng end)
    {
        String str_start="origin="+start.latitude+","+start.latitude;
        String str_end="destination="+end.latitude+","+end.latitude;
        String mode="mode=driving";
        String param=str_start+"&"+str_end+"&"+mode;
       String url="https://maps.googleapis.com/maps/api/directions/json?"+param+"&key=" +getString(R.string.google_map_id);;

        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        Polyline polyline=map.addPolyline((PolylineOptions) values[0]);
        polyline.setColor(Color.RED);
        polyline.setWidth(5);
    }


    public class FetchURL extends AsyncTask<String, Void, String> {
        Context mContext;
        String directionMode = "driving";

        public FetchURL(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            // For storing data from web service
            String data = "";
            directionMode = strings[1];
            try {
                // Fetching the data from web service
                data = downloadUrl(strings[0]);
                Log.d("mylog", "Background task data " + data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PointParser parserTask = new PointParser(mContext, directionMode);
            // Invokes the thread for parsing the JSON data
            parserTask.execute(s);
        }

        private String downloadUrl(String strUrl) throws IOException, IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);
                // Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();
                // Connecting to url
                urlConnection.connect();
                // Reading data from url
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                data = sb.toString();
                Log.d("mylog", "Downloaded URL: " + data.toString());
                br.close();
            } catch (Exception e) {
                Log.d("mylog", "Exception downloading URL: " + e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }*/

}

