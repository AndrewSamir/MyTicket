package com.samir.andrew.myticket.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitHelper;

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
        AutofitHelper.create(holder.tvRvItemStageChairKey);
        /*
        states
        0 -> empty
        1 -> reserved
        2 -> not confirmed
        3 -> to reserve
         */
        if (data.get(position).getState() == 0) {
            holder.tvRvItemStageChairKey.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorEmpty));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
        } else if (data.get(position).getState() == 1) {
            holder.tvRvItemStageChairKey.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorReserved));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (data.get(position).getState() == 2) {
            holder.tvRvItemStageChairKey.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorNotConfirmed));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
        } else if (data.get(position).getState() == 4) {//should be equal auth.
            holder.tvRvItemStageChairKey.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorMyReservation));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
        } else if (data.get(position).getState() == 3) {
            holder.tvRvItemStageChairKey.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToReserve));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
        }
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

    public List<ModelChair> getReservedChairs() {

        List<ModelChair> toReserveChairs = new ArrayList<>();

        for (ModelChair chair : data) {

            if (chair.getState() == 4)
                toReserveChairs.add(chair);
        }

        return toReserveChairs;

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

            ModelChair chair = data.get(getAdapterPosition());
            if (chair.getState() == 0) {
                chair.setState(4);
                notifyItemChanged(getAdapterPosition());
            } else if (chair.getState() == 4) {
                chair.setState(0);
                notifyItemChanged(getAdapterPosition());
            }

        }


    }
}
