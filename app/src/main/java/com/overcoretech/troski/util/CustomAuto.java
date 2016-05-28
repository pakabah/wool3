package com.overcoretech.troski.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.overcoretech.troski.api.ApiCall;
import com.overcoretech.troski.db.DBHelper;
import com.overcoretech.troski.template.Destination;
import com.overcoretech.troski.views.Trips;

import java.util.List;



/**
 * Created by akabah on 10/25/15.
 */
public class CustomAuto implements TextWatcher {

    Context context;

    Trips trips;

    public CustomAuto(Context context)
    {
        this.context = context;
//        context.getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter(ApiCall.ROUTE_FIND));
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {
//                progress.dismiss();
                String status = bundle.getString("Status");
                Log.e("receiver DevName", bundle.getString("Status"));
                assert status != null;
                switch (status) {
                    case "500":

                        break;
                    case "502":

                        break;

                    case "1":

                        break;
                    case "310":
//                        Toast.makeText(getApplicationContext(), "Username or Password Incorrect", Toast.LENGTH_LONG).show();
                        break;
                    case "200":
//                        Toast.makeText(getApplicationContext(),"Something Terrible happened", Toast.LENGTH_LONG).show();
                        break;
                    case "300":
//                        Toast.makeText(getApplicationContext(),"Invalid Request", Toast.LENGTH_LONG).show();
                        break;
                    default:
//                        Toast.makeText(getApplicationContext(),"Something horrible just happened", Toast.LENGTH_LONG).show();
                        break;
                }
            }
            else {

            }
        }
    };

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        DrugList drugList = (DrugList) context;
        trips  = (Trips) context;
        trips.item = getItemsFromDb(s.toString(),context);

        trips.adapter.notifyDataSetChanged();
        trips.adapter1.notifyDataSetChanged();

        trips.adapter = new ArrayAdapter<String>(trips, android.R.layout.simple_dropdown_item_1line, trips.item);
        trips.adapter1 = new ArrayAdapter<String>(trips, android.R.layout.simple_dropdown_item_1line, trips.item);

        trips.customAutoView.setAdapter(trips.adapter);
        trips.editText2.setAdapter(trips.adapter1);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public String[] getItemsFromDb(String searchTerm,Context context){
        if(searchTerm.length() > 4)
        {
            ApiCall apiCall = new ApiCall(context);
            apiCall.getList(searchTerm);
        }

        // add items on the array dynamically
        DBHelper dbHelper = new DBHelper(context);
        Log.e("results",dbHelper.getDestination(searchTerm).toString());

        List<Destination> destinations = dbHelper.getDestination(searchTerm);

        int rowCount = destinations.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (Destination record : destinations) {
                Log.e("record",record.name);
            item[x] = record.name;
            x++;
        }

        return item;
    }

    public void unregisterReceiver(Context context) {
        context.getApplicationContext().unregisterReceiver(broadcastReceiver);
    }


}
