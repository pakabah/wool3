package com.overcoretech.troski.views;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.overcoretech.troski.R;
import com.overcoretech.troski.api.ApiCall;
import com.overcoretech.troski.db.DBHelper;
import com.overcoretech.troski.template.Destination;
import com.overcoretech.troski.util.CustomAuto;
import com.overcoretech.troski.util.CustomAutoView;
import com.overcoretech.troski.util.HttpConnection;
import com.overcoretech.troski.util.PathJSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trips extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    boolean mapReady = false;
    private static final String KEY = "AIzaSyBDNx0wxeX4uBmDbcdukg7jt6OFiyWGs1A";


    private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,
            -73.998585);
    private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
    private static final LatLng WALL_STREET = new LatLng(40.7064, -74.0094);

    private  LatLng Destination = null;
    private  LatLng Origin  = null;
    String dest_lat,dest_longt,src_longt,src_lat;
    ProgressDialog progress;


    public ArrayAdapter<String> adapter;
    public ArrayAdapter<String> adapter1;
    public CustomAutoView customAutoView, editText2;
   public String[] item = new String[] {"Please search..."};
    DBHelper dbHelper;
    Button search;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
               Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {

               String search = bundle.getString("Status");
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                List<Destination> destinations = dbHelper.getDestinationDetails(search);

                int rowCount = destinations.size();

                String[] item = new String[rowCount];
                int x = 0;

                for (Destination records : destinations) {
                    Log.e("record",records.name);
                    Log.e("Lat",records.lat);
                    Log.e("Long",records.lon);
                    src_lat = records.lat;
                    src_longt =  records.lon;
                    x++;
                }

                Log.e("Src_lat is set at", src_lat);
                Log.e("Src_lon is set at", src_longt);
                double src_lat_temp = Double.parseDouble(src_lat);
                double src_long_temp = Double.parseDouble(src_longt);

                Origin = new LatLng(src_lat_temp,src_long_temp);
                progress.dismiss();
                doStuff();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        initToolbar("Trips & Prices");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (Button) findViewById(R.id.button);
        registerReceiver(broadcastReceiver, new IntentFilter(ApiCall.ROUTE_FIND));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_NETWORK_STATE
                }, 1217);
            }
            return;
        }
        else
        {

            doStuff();
        }

        customAutoView = (CustomAutoView) findViewById(R.id.editText);
        customAutoView.addTextChangedListener(new CustomAuto(this));
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,item);
        customAutoView.setAdapter(adapter);

        editText2 = (CustomAutoView) findViewById(R.id.editText2);
        editText2.addTextChangedListener(new CustomAuto(this));
        adapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,item);
        editText2.setAdapter(adapter1);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(customAutoView, InputMethodManager.SHOW_IMPLICIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(Trips.this);
                progress.setTitle("Load");
                progress.setMessage("Loading...");
                progress.show();

                String Start =  customAutoView.getText().toString();
                Log.e("Start String", Start);

                ApiCall apiCall = new ApiCall(getApplicationContext());
                apiCall.getListFinal(Start);

                String End = editText2.getText().toString();


                DBHelper dbHelper1 = new DBHelper(getApplicationContext());
                List<Destination> destinationsEnd = dbHelper1.getDestinationDetails(End);

                int rowCountEnd = destinationsEnd.size();

                String[] itemEnd = new String[rowCountEnd];
                int i = 0;

                for (Destination record : destinationsEnd) {
                    Log.e("record",record.name);
                    Log.e("Lat",record.lat);
                    Log.e("Long",record.lon);
                    dest_lat = record.lat;
                    dest_longt =  record.lon;
                    i++;
                }

                Log.e("Dest_lat is set at", dest_lat);
                Log.e("Dest_lon is set at", dest_longt);
                double dest_lat_temp   = Double.parseDouble(dest_lat);
                double dest_lng_temp = Double.parseDouble(dest_longt);



                Destination = new LatLng(dest_lat_temp,dest_lng_temp);
            }
        });
    }

    private void initToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }


    public void doStuff()
    {
        if(mapReady)
        {
            MarkerOptions options = new MarkerOptions();
            assert Origin != null;
            options.position(Origin);
            options.position(Destination);
            googleMap.addMarker(options);

            String url = getMapsApiDirectionsUrl(Origin,Destination);
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Origin,
                    13));
            addMarkers(Origin,Destination);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case 1217:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    doStuff();
                break;

        }
    }

    private String getMapsApiDirectionsUrl(LatLng origin, LatLng destination) {
//        String waypoints = "waypoints=optimize:true|"
//                + LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude
//                + "|" + "|" + BROOKLYN_BRIDGE.latitude + ","
//                + BROOKLYN_BRIDGE.longitude + "|" + WALL_STREET.latitude + ","
//                + WALL_STREET.longitude;

        String sensor = "sensor=false";
//        String params = waypoints + "&" + sensor;
        String params = "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin.latitude+","+origin.longitude+"&destination="+destination.latitude+","+destination.longitude+"&key="+KEY+"&"+ params;
        return url;
    }

    private void addMarkers(LatLng origin, LatLng destination) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(origin)
                    .title("Origin"));
            googleMap.addMarker(new MarkerOptions().position(destination)
                    .title("Destination"));
        }
    }

    private class ReadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            try {
                Log.e("URL", strings[0]);
                HttpConnection http = new HttpConnection();
                data = http.readUrl(strings[0]);

            } catch (Exception e) {

            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                Log.e("Res", jsonData[0]);
                jObject = new JSONObject(jsonData[0]);

                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {

            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(3);
                polyLineOptions.color(Color.BLUE);
            }

            googleMap.addPolyline(polyLineOptions);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        mapReady = true;
        googleMap = googleMaps;

//        doStuff();
    }
}
