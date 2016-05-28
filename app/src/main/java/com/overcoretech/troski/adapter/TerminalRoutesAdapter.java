package com.overcoretech.troski.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.overcoretech.troski.R;
import com.overcoretech.troski.template.TerminalRoutesTemplate;
import com.overcoretech.troski.views.TerminalRoutesDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakabah on 27/05/16.
 */
public class TerminalRoutesAdapter extends RecyclerView.Adapter<TerminalRoutesAdapter.ViewHolder>{

    List<TerminalRoutesTemplate> terminalRoutesTemplates;

    public TerminalRoutesAdapter(List<TerminalRoutesTemplate> terminalRoutesTemplates)
    {
        this.terminalRoutesTemplates = new ArrayList<>();
        this.terminalRoutesTemplates.addAll(terminalRoutesTemplates);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_terminal_routes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TerminalRoutesTemplate terminalRoutesTemplate = terminalRoutesTemplates.get(position);
        holder.name.setText(terminalRoutesTemplate.RouteName);
        holder.name.setTag(terminalRoutesTemplate.RouteId);
        holder.routeTo.setText(terminalRoutesTemplate.RouteTo);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), TerminalRoutesDetails.class);
                intent.putExtra("tag", holder.name.getTag().toString());
                intent.putExtra("nameF",holder.name.getText().toString() );
                intent.putExtra("nameS", holder.routeTo.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return terminalRoutesTemplates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {

        CardView cardView;
        TextView name;
        TextView routeTo;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.routeFrom);
            cardView = (CardView) itemView.findViewById(R.id.terminalCard);
            routeTo = (TextView) itemView.findViewById(R.id.routeTo);
        }
    }
}
