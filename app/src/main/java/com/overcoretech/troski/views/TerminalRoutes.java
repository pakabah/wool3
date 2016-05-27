package com.overcoretech.troski.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.overcoretech.troski.R;
import com.overcoretech.troski.adapter.TerminalRoutesAdapter;
import com.overcoretech.troski.template.TerminalRoutesTemplate;

import java.util.ArrayList;
import java.util.List;

public class TerminalRoutes extends AppCompatActivity {

String Title = "Default";

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleTerminalRoutes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        TerminalRoutesAdapter terminalRoutesAdapter = new TerminalRoutesAdapter(initializeData());
        recyclerView.setAdapter(terminalRoutesAdapter);
    }

    List<TerminalRoutesTemplate> initializeData()
    {
        List<TerminalRoutesTemplate> terminalRoutesTemplates = new ArrayList<>();
        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Osu","RA3","23km","2GHC"));
        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Madina","RA3","25km","2GHC"));
        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Adenta","RA3","26km","2GHC"));
        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Spintex","RA3","27km","2GHC"));
        terminalRoutesTemplates.add(new TerminalRoutesTemplate("37 to Achimota","RA3","28km","2GHC"));
        return terminalRoutesTemplates;
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
}
