package com.overcoretech.troski.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.overcoretech.troski.R;
import com.overcoretech.troski.adapter.TerminalAdapter;
import com.overcoretech.troski.template.TerminalTemplate;

import java.util.ArrayList;
import java.util.List;

public class Terminals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminals);

        initToolbar("Terminals");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleTerminals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        TerminalAdapter terminalAdapter = new TerminalAdapter(initializeData());
        recyclerView.setAdapter(terminalAdapter);
    }


    public List<TerminalTemplate> initializeData()
    {
        List<TerminalTemplate> terminalTemplates = new ArrayList<>();
        terminalTemplates.add(new TerminalTemplate("37 Station","37"));
        terminalTemplates.add(new TerminalTemplate("Kaneshie Station", "KS"));

        return terminalTemplates;
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
