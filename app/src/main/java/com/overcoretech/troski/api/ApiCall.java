package com.overcoretech.troski.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

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
