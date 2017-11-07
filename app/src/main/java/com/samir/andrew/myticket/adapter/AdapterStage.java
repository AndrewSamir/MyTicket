package com.samir.andrew.myticket.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;

import java.util.List;

/**
 * Created by lenovo on 5/3/2016.
 */
public class AdapterStage extends RecyclerView.Adapter<AdapterStage.ViewHolder> {


    public List<ModelChair> data;
    private Activity mContext;


    public AdapterStage(List<ModelChair> data, Activity mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_stage_chair, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvRvItemStageChairKey.setText(data.get(position).getChairKey());
        if (data.get(position).getState() == 1)
            holder.tvRvItemStageChairKey.setBackgroundColor(Color.parseColor("#000000"));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(String string) {
        insert(string, data.size());
    }

    public void insert(String string, int position) {
        //data.add(position, string);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<ModelChair> items) {
        int startIndex = data.size();
        data.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRvItemStageChairKey;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvRvItemStageChairKey = (TextView) itemView.findViewById(R.id.tvRvItemStageChairKey);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


        }


    }
}
