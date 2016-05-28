package com.overcoretech.troski.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.overcoretech.troski.db.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pakabah on 27/05/16.
 */
public class ApiCall {

    Context context;
    public static final String LOGIN = "android.intent.action.Login";
    public static final String ROUTE_FINISH = "android.intent.action.route";
    public static final String DEFAULT = "N/A";
    public static final String ROUTE_DETAILS = "android.intent.action.route_details";
    public static final String ROUTE_FIND = "android.intent.action.route_find";

    public ApiCall(Context context)
    {
        this.context = context;
    }

    public  ApiCall()
    {

    }

    public void getTerminal()
    {
        class altTerminals extends AsyncTask<String,Void,String>
        {

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL("http://162.243.96.232/trotro/process/process_request.php?getTerminal");
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!= null && !s.isEmpty())
                {
                    DBHelper dbHelper = new DBHelper(context);
                    try {
                        JSONArray jsonArray = new JSONArray(s);
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            dbHelper.insertAgency(jsonObject.getString("from_terminal"),jsonObject.getString("from_terminal"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        altTerminals altTerminals = new altTerminals();
        altTerminals.execute();
    }

   public void getRoutesForTerminal(String Terminal)
    {
        class altgetRoutes extends AsyncTask<String,Void,String>
        {
            String terminal = null;

            @Override
            protected String doInBackground(String... params) {
                 terminal = params[0];
                try {
                    URL url = new URL("http://162.243.96.232/trotro/process/process_request.php?selectedTerminal="+terminal);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s != null && !s.isEmpty())
                {
                    Log.e("Data",s);
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.deleteAllTerminalROutes();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(s);
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            dbHelper.insertTerminalROutes(jsonObject.getString("from_terminal"),jsonObject.getString("to_terminal"),terminal);
                            publishRouteFinish("DONE");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        altgetRoutes altgetRoutes = new altgetRoutes();
        altgetRoutes.execute(Terminal);
    }

    public void getRouteDetails(final String from, String to)
    {
        class altgetRouteDetails extends AsyncTask<String,Void,String>
        {

            @Override
            protected String doInBackground(String... params) {
                String Src = params[0];
                String Des = params[1];

                Log.e("Call Api", "In Api...");
                try {
                    URL url = new URL("http://162.243.96.232/trotro/process/process_request.php?routeSrc="+ Uri.encode(Src)+"&routeDes"+Uri.encode(Des));
                    Log.e("Call Api", "URL "+ url.toString());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        Log.e("Buffer", bufferedStrChunk);
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Outside s", s);
                if(s != null && !s.isEmpty())
                {
                    Log.e("Details Data",s);
                    Log.e("Call Api", "Done...");
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(s);
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                          String fare =  jsonObject.getString("fare");
                           String maxTime = jsonObject.getString("maxtime");
                         String mintime =    jsonObject.getString("mintime");
                         String src_lon =    jsonObject.getString("src_lat");
                          String src_lat =  jsonObject.getString("src_lon");
                           String des_lat =      jsonObject.getString("dest_lat");
                          String des_lon =    jsonObject.getString("dest_lon");

                            publishROuteDetails(src_lon,src_lat,mintime,maxTime,fare,des_lon,des_lat);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        altgetRouteDetails altgetRouteDetails = new altgetRouteDetails();
        altgetRouteDetails.execute(from,to);
    }


    public void getList(String QueryString)
    {
        class altSearch extends AsyncTask<String,Void,String>
        {
            String Query = null;
            @Override
            protected String doInBackground(String... params) {
                Query = params[0];
                try {
                    URL url = new URL("http://162.243.96.232/trotro/process/process_request.php?getStops="+Query);
                    Log.e("Call Api", "URL "+ url.toString());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null)
                {

                    JSONArray jsonArray = null;
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.deleteAllDestination();

                    try {
                        jsonArray = new JSONArray(s);
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(jsonObject.has("stop_lat_orig"))
                            {
                                Log.e("Result",jsonObject.getString("stop_name"));
                                dbHelper.insertDestination(jsonObject.getString("stop_name"),jsonObject.getString("stop_lat_orig"),jsonObject.getString("stop_lon_orig"));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        altSearch altSearch = new altSearch();
        altSearch.execute(QueryString);
    }

    public void getListFinal(String QueryString)
    {
        class altSearch extends AsyncTask<String,Void,String>
        {
            String Query = null;
            @Override
            protected String doInBackground(String... params) {
                Query = params[0];
                try {
                    URL url = new URL("http://162.243.96.232/trotro/process/process_request.php?getStops="+Query);
                    Log.e("Call Api", "URL "+ url.toString());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestProperty("User-Agent", "");
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }
                    return stringBuilder.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null)
                {

                    JSONArray jsonArray = null;
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.deleteAllDestination();

                    try {
                        jsonArray = new JSONArray(s);
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if(jsonObject.has("stop_lat_orig"))
                            {
                                Log.e("Result",jsonObject.getString("stop_name"));
                                dbHelper.insertDestination(jsonObject.getString("stop_name"),jsonObject.getString("stop_lat_orig"),jsonObject.getString("stop_lon_orig"));
                            }
                            publishRouteFind(Query);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        altSearch altSearch = new altSearch();
        altSearch.execute(QueryString);
    }

    private void publishLogin(String Result)
    {
        Intent intent = new Intent(LOGIN);
        intent.setAction(LOGIN);
        intent.putExtra("Status", Result);
        Log.e("Intent Filter", "Register Set");
        context.sendBroadcast(intent);
    }

    private void publishRouteFinish(String Result)
    {
        Intent intent = new Intent(ROUTE_FINISH);
        intent.setAction(ROUTE_FINISH);
        intent.putExtra("Status", Result);
        Log.e("Intent Filter", "Route Finish");
        context.sendBroadcast(intent);
    }

    private void publishRouteFind(String Result)
    {
        Intent intent = new Intent(ROUTE_FIND);
        intent.setAction(ROUTE_FIND);
        intent.putExtra("Status", Result);
        Log.e("Intent Filter", "Route Finish");
        context.sendBroadcast(intent);
    }

    private void publishROuteDetails(String src_longt,String src_lat, String mintime,String maxTime,String price,String dest_longt,String dest_lat)
    {
        Intent intent = new Intent(ROUTE_DETAILS);
        intent.setAction(ROUTE_DETAILS);
        intent.putExtra("src_lon", src_longt);
        intent.putExtra("src_lat", src_lat);
        intent.putExtra("dest_lat", dest_lat);
        intent.putExtra("dest_lon", dest_longt);
        intent.putExtra("maxtime", maxTime);
        intent.putExtra("mintime", mintime);
        intent.putExtra("price", price);

        Log.e("Intent Filter", "Register Set");
        context.sendBroadcast(intent);
    }

    private void saveUsername(String username)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void saveProfile(String profile)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("profile", profile);
        editor.apply();
    }

    private void saveName(String name)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public String getUsername()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", DEFAULT);
    }

    public String getProfile()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("profile", DEFAULT);
    }

    public String getName()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", DEFAULT);
    }
}
