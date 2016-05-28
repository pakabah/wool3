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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.overcoretech.troski.R;
import com.overcoretech.troski.api.ApiCall;
import com.overcoretech.troski.util.HttpConnection;
import com.overcoretech.troski.util.PathJSONParser;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TerminalRoutesDetails extends AppCompatActivity implements OnMapReadyCallback {

    String Title = "Default";
    TextView distance;
    TextView price;
    TextView time;
    ProgressDialog progress;
    String  nameS;
    String nameF;

    GoogleMap googleMap;
    boolean mapReady = false;
    private static final String KEY = "AIzaSyBDNx0wxeX4uBmDbcdukg7jt6OFiyWGs1A";

    private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,
            -73.998585);
    private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
    private static final LatLng WALL_STREET = new LatLng(40.7064, -74.0094);
    private  LatLng Destination = null;
    private  LatLng Origin  = null;
    String maxTime,minTime,Price,dest_lat,dest_longt,src_longt,src_lat;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {
                progress.dismiss();
               Price = bundle.getString("price")+ "GHC";
                minTime = bundle.getString("mintime");
                maxTime = bundle.getString("maxtime");
                src_lat = bundle.getString("src_lat");
                src_longt = bundle.getString("src_lon");
                dest_lat = bundle.getString("dest_lat");
                dest_longt = bundle.getString("dest_lon");

                time.setText(getTime(minTime,maxTime));

                price.setText(Price);

                 double dest_lat_temp   = Double.parseDouble(dest_lat);
                double dest_lng_temp = Double.parseDouble(dest_longt);

                double src_lat_temp = Double.parseDouble(src_lat);
                double src_long_temp = Double.parseDouble(src_longt);

                Destination = new LatLng(dest_lat_temp,dest_lng_temp);
                Origin = new LatLng(src_lat_temp,src_long_temp);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

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
            }
        }
    };

    public String getTime(String max,String min)
    {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Date Date1 = null;
        Date Date2 = null;
        try {
            Date1 = format.parse(min);
            Date2 = format.parse(max);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert Date1 != null;
        assert Date2 != null;
        long mills = Date1.getTime() - Date2.getTime();
        Log.v("Data1", ""+Date1.getTime());
        Log.v("Data2", ""+Date2.getTime());
        int Hours = (int) (mills/(1000 * 60 * 60));
        int Mins = (int) (mills/(1000*60)) % 60;

        return Hours + "hrs" + Mins+"mins";
    }

//    public String getDistance(LatLng First, LatLng Second)
//    {
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_routes_details);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Title = bundle.getString("tag");
              nameS = bundle.getString("nameS");
             nameF = bundle.getString("nameF");


        }

        initToolbar(Title);
        distance = (TextView) findViewById(R.id.distance);
        price = (TextView) findViewById(R.id.price);
        time = (TextView) findViewById(R.id.time);

        Log.e("Call Api", "Calling...");
        ApiCall apiCall = new ApiCall(getApplicationContext());
        apiCall.getRouteDetails(nameF,nameS);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapDetails);
        mapFragment.getMapAsync(this);

        registerReceiver(broadcastReceiver, new IntentFilter(ApiCall.ROUTE_DETAILS));
        progress = new ProgressDialog(TerminalRoutesDetails.this);
        progress.setTitle("Load");
        progress.setMessage("Loading...");
        progress.show();


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

//            String url = getMapsApiDirectionsUrl(Origin,Destination);
            ReadTask downloadTask = new ReadTask();
//            downloadTask.execute(url);

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
