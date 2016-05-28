package com.overcoretech.troski.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.overcoretech.troski.R;
import com.overcoretech.troski.template.MomentTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakabah on 27/05/16.
 */
public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.ViewHolder>{

    List<MomentTemplate> momentTemplates;

    public MomentsAdapter(List<MomentTemplate> momentTemplates)
    {
        this.momentTemplates = new ArrayList<>();
        this.momentTemplates.addAll(momentTemplates);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_moment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MomentTemplate momentTemplate = momentTemplates.get(position);
        holder.momentpic.setImageResource(momentTemplate.ImagePath);
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.like.getTag().toString()) == 1) {
                    holder.like.setImageResource(R.drawable.hearts_2);
                    holder.like.setTag(2);
                } else {
                    holder.like.setImageResource(R.drawable.hearts);
                    holder.like.setTag(1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return momentTemplates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView like;
        ImageView share;
        ImageView momentpic;

        public ViewHolder(View itemView) {
            super(itemView);
        momentpic = (ImageView) itemView.findViewById(R.id.momentpic);
            like = (ImageView) itemView.findViewById(R.id.like);

        }
    }
}
