package com.overcoretech.troski;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.overcoretech.troski.adapter.MainMenuAdapter;
import com.overcoretech.troski.template.MainMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    public static final String DEFAULT = "N/A";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleMenu);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new MainMenuAdapter(initializeData()));
    }

    public ArrayList<MainMenu> initializeData()
    {
        ArrayList<MainMenu> MainMenu = new ArrayList<>();
        MainMenu.add(new MainMenu("Terminals", R.drawable.terminal,"tm"));
        MainMenu.add(new MainMenu("Wool3 Moments", R.drawable.moment,"wm"));
        MainMenu.add(new MainMenu("Trips & Pricing", R.drawable.pricing,"tp"));
        MainMenu.add(new MainMenu("Are We there yet?", R.drawable.there,"ar"));
        MainMenu.add(new MainMenu("Crowd Sourcing", R.drawable.cs,"cs"));
        return MainMenu;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
