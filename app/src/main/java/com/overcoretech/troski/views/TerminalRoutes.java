package com.overcoretech.troski.views;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.overcoretech.troski.R;
import com.overcoretech.troski.adapter.TerminalRoutesAdapter;
import com.overcoretech.troski.api.ApiCall;
import com.overcoretech.troski.db.DBHelper;
import com.overcoretech.troski.template.TerminalRoutesTemplate;

import java.util.List;

public class TerminalRoutes extends AppCompatActivity {

String Title = "Default";
    ProgressDialog progress;
    RecyclerView recyclerView;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {
                progress.dismiss();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                TerminalRoutesAdapter terminalRoutesAdapter = new TerminalRoutesAdapter(initializeData());
                recyclerView.setAdapter(terminalRoutesAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_routes);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            Title = bundle.getString("tag");
        }
        initToolbar(Title);

        progress = new ProgressDialog(TerminalRoutes.this);
        progress.setTitle("Search");
        progress.setMessage("Searching...");
        progress.show();
        registerReceiver(broadcastReceiver, new IntentFilter(ApiCall.ROUTE_FINISH));

         recyclerView = (RecyclerView) findViewById(R.id.recycleTerminalRoutes);

        ApiCall apiCall = new ApiCall(getApplicationContext());
        apiCall.getRoutesForTerminal(Title);
    }

    List<TerminalRoutesTemplate> initializeData()
    {
//        List<TerminalRoutesTemplate> terminalRoutesTemplates = new ArrayList<>();
//        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Osu","RA3","23km","2GHC"));
//        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Madina","RA3","25km","2GHC"));
//        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Adenta","RA3","26km","2GHC"));
//        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Spintex","RA3","27km","2GHC"));
//        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Achimota","RA3","28km","2GHC"));
        DBHelper dbHelper = new DBHelper(getApplicationContext());

//        return terminalRoutesTemplates;
        return dbHelper.getAllTerminalRoutes(Title);
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
}
