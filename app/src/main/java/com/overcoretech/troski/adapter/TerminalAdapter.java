package com.overcoretech.troski.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.overcoretech.troski.R;
import com.overcoretech.troski.template.TerminalTemplate;

import java.util.List;

/**
 * Created by pakabah on 27/05/16.
 */
public class TerminalAdapter extends RecyclerView.Adapter<TerminalAdapter.ViewHolder>{

    List<TerminalTemplate> terminalTemplates;

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

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.terminalCard);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
