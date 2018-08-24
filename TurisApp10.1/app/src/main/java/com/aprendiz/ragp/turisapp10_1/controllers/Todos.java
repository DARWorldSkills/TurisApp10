package com.aprendiz.ragp.turisapp10_1.controllers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aprendiz.ragp.turisapp10_1.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Todos extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locationManager;
    Location location;

    Marker marker;

    double latutud = 0.0;
    double longitud = 0.0;

    final int MY_LOCATION = 0;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listpoint = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        miUbicacion();
        // Add a marker in Sydney and move the camera
       LatLng sydney = new LatLng(-34, 151);
       mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        String url = getRequestUr(new LatLng(location.getLatitude(), location.getLongitude()),sydney );
    }

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION);

        {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizarUbicacion(location);
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1500,0, locationListener);

        }


    }

    private void actualizarUbicacion(Location location) {

        if (location != null){

            latutud = location.getLatitude();
            longitud = location.getLongitude();
            agregarMarcador(latutud, longitud);


        }
    }

    private void agregarMarcador(double latutud, double longitud) {

        LatLng marca = new LatLng(latutud, longitud);
        CameraUpdate mi = CameraUpdateFactory.newLatLngZoom(marca, 14);
        if (marker != null)marker.remove();
        marker = mMap.addMarker(new MarkerOptions().position(marca).title("Estas Aquí"));
        mMap.animateCamera(mi);


    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private String getRequestUr(LatLng origin, LatLng destino){

        String str_org ="origin" + origin.latitude + "," + origin.longitude;
        String str_destino = "destination" + destino.latitude +","+destino.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String param = str_org +"&"+ str_destino + "&" + sensor +"&" +mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/direction" + output + "?" +param;

        return url;

    }


    private String requestDirection(String reqUrl) throws IOException {

        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection= null;

        try {
            URL url =new URL(reqUrl);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);

            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();

            }
            httpURLConnection.disconnect();
        }
        return responseString;

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mMap.setMyLocationEnabled(true);
                }

                break;
        }
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Parse json

            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {

            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routes;

        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list routes  and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {


                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));

                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.MAGENTA);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions != null) {

                mMap.addPolyline(polylineOptions);

            } else {
                Toast.makeText(getApplicationContext(), "Directions not found ", Toast.LENGTH_SHORT).show();

            }
        }
    }



}
