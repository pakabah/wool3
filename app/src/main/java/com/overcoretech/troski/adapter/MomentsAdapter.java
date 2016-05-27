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
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return momentTemplates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView like;
        ImageView share;

        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
