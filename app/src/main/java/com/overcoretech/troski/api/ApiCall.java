package com.overcoretech.troski.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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
    public static final String DEFAULT = "N/A";

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

                Log.e("Results", s);
            }
        }
        altTerminals altTerminals = new altTerminals();
        altTerminals.execute();
    }

    private void publishLogin(String Result)
    {
        Intent intent = new Intent(LOGIN);
        intent.setAction(LOGIN);
        intent.putExtra("Status", Result);
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
