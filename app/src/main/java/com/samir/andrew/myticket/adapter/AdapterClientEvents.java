package com.samir.andrew.myticket.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.models.ModelEventDetails;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.views.activity.EventDetails;

import java.util.List;

/**
 * Created by lenovo on 5/3/2016.
 */
public class AdapterClientEvents extends RecyclerView.Adapter<AdapterClientEvents.ViewHolder> {


    public List<ModelEventDetails> data;
    private Activity mContext;


    public AdapterClientEvents(List<ModelEventDetails> data, Activity mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvRvItemEvent.setText(data.get(position).getEventName());
        holder.imgRvItemEvent.setImageBitmap(StringToBitMap(data.get(position).eventImage));
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

    public void addAll(List<ModelEventDetails> items) {
        int startIndex = data.size();
        data.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRvItemEvent;
        com.joooonho.SelectableRoundedImageView imgRvItemEvent;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgRvItemEvent = (com.joooonho.SelectableRoundedImageView) itemView.findViewById(R.id.imgRvItemEvent);
            tvRvItemEvent = (TextView) itemView.findViewById(R.id.tvRvItemEvent);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            SingletonData.getInstance().setEventName(data.get(getAdapterPosition()).getEventName());
            SingletonData.getInstance().setEventImage(data.get(getAdapterPosition()).getEventImage());
            SingletonData.getInstance().setEventDescription(data.get(getAdapterPosition()).getEventDesciption());
            SingletonData.getInstance().setChairsInRow(data.get(getAdapterPosition()).getChairsInRow());


            Intent intent = new Intent(mContext, EventDetails.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Pair<View, String> pair1 = Pair.create((View) imgRvItemEvent, mContext.getString(R.string.shared_event_details_image));
                Pair<View, String> pair2 = Pair.create((View) tvRvItemEvent, mContext.getString(R.string.shared_event_details_title));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(mContext, pair1, pair2);
                mContext.startActivity(intent, options.toBundle());
            } else {
                mContext.startActivity(intent);
            }

        }


    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
