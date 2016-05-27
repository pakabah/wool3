package com.overcoretech.troski.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleTerminals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        TerminalAdapter terminalAdapter = new TerminalAdapter(initializeData());
        recyclerView.setAdapter(terminalAdapter);
    }


    public List<TerminalTemplate> initializeData()
    {
        List<TerminalTemplate> terminalTemplates = new ArrayList<>();
        terminalTemplates.add(new TerminalTemplate());
        terminalTemplates.add(new TerminalTemplate());

        return terminalTemplates;
    }
}
