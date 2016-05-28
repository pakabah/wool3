package com.overcoretech.troski.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.overcoretech.troski.R;
import com.overcoretech.troski.adapter.MomentsAdapter;
import com.overcoretech.troski.template.MomentTemplate;

import java.util.ArrayList;
import java.util.List;

public class Moments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        initToolbar("Wool3 Moments");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleMoments);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        MomentsAdapter momentsAdapter = new MomentsAdapter(initializeData());
        recyclerView.setAdapter(momentsAdapter);
    }


    public List<MomentTemplate> initializeData()
    {
        List<MomentTemplate> momentTemplates = new ArrayList<>();

        momentTemplates.add(new MomentTemplate(R.drawable.tro));
        momentTemplates.add(new MomentTemplate(R.drawable.tro1));
        momentTemplates.add(new MomentTemplate(R.drawable.tro2));
        momentTemplates.add(new MomentTemplate(R.drawable.tro3));

        return momentTemplates;
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
