package com.overcoretech.troski.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.overcoretech.troski.R;
import com.overcoretech.troski.template.TerminalTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pakabah on 27/05/16.
 */
public class TerminalAdapter extends RecyclerView.Adapter<TerminalAdapter.ViewHolder>{

    List<TerminalTemplate> terminalTemplates;

    public TerminalAdapter(List<TerminalTemplate> terminalTemplates)
    {
        this.terminalTemplates = new ArrayList<>();
        this.terminalTemplates.addAll(terminalTemplates);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_terminal,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TerminalTemplate terminalTemplate = terminalTemplates.get(position);
        holder.textView.setText(terminalTemplate.TerminalName);
        holder.textView.setTag(terminalTemplate.TerminalId);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.relativeLayout.setBackgroundColor(color);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return terminalTemplates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView textView;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.terminalCard);
            textView = (TextView) itemView.findViewById(R.id.title);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.headerCol);
        }
    }
}
