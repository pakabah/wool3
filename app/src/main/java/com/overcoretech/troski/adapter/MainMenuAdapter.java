package com.overcoretech.troski.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.overcoretech.troski.R;
import com.overcoretech.troski.template.MainMenu;
import com.overcoretech.troski.views.Crowd;
import com.overcoretech.troski.views.Moments;
import com.overcoretech.troski.views.Terminals;
import com.overcoretech.troski.views.There;
import com.overcoretech.troski.views.Trips;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakabah on 21/02/16.
 */
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {

List<MainMenu> mainMenus;

    public MainMenuAdapter(List<MainMenu> mainMenus)
    {
        this.mainMenus = new ArrayList<>();
        this.mainMenus.addAll(mainMenus);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MainMenu mainMenu = mainMenus.get(position);
        holder.name.setText(mainMenu.Name);
        holder.name.setTag(mainMenu.tag);
        holder.image.setImageResource(mainMenu.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (holder.name.getTag().toString())
                {
                    case "tm":
                    {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), Terminals.class);
                        intent.putExtra("tag","tm");
                        v.getContext().startActivity(intent);
                        break;
                    }
                    case "wm":
                    {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), Moments.class);
                        intent.putExtra("tag","wm");
                        v.getContext().startActivity(intent);
                        break;
                    }
                    case "tp":
                    {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), Trips.class);
                        intent.putExtra("tag","tp");
                        v.getContext().startActivity(intent);
                        break;
                    }
                    case "ar":
                    {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), There.class);
                        intent.putExtra("tag","ar");
                        v.getContext().startActivity(intent);
                        break;
                    }
                    case "cs":
                    {
                        Intent intent = new Intent(v.getContext().getApplicationContext(), Crowd.class);
                        intent.putExtra("tag","cs");
                        v.getContext().startActivity(intent);
                        break;
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mainMenus.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView image;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView2);
            cardView = (CardView) itemView.findViewById(R.id.menuCard);
        }
    }
}
