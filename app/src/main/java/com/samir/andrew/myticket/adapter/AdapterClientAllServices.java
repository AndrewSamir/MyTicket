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
import com.samir.andrew.myticket.models.ModelServiceDetails;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.views.activity.ServiceDetails;

import java.util.List;

/**
 * Created by lenovo on 5/3/2016.
 */
public class AdapterClientAllServices extends RecyclerView.Adapter<AdapterClientAllServices.ViewHolder> {


    public List<ModelServiceDetails> data;
    private Activity mContext;


    public AdapterClientAllServices(List<ModelServiceDetails> data, Activity mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_services, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvRvItemServicesTitle.setText(data.get(position).getServiceName());
        holder.imgRvItemServices.setImageBitmap(StringToBitMap(data.get(position).getServiceImage()));
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

    public void addAll(List<ModelServiceDetails> items) {
        int startIndex = data.size();
        data.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRvItemServicesTitle;
        ImageView imgRvItemServices;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgRvItemServices = (ImageView) itemView.findViewById(R.id.imgRvItemServices);
            tvRvItemServicesTitle = (TextView) itemView.findViewById(R.id.tvRvItemServicesTitle);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            SingletonData.getInstance().setServiceId(data.get(getAdapterPosition()).getKey());
            SingletonData.getInstance().setServiceImage(data.get(getAdapterPosition()).getServiceImage());
            SingletonData.getInstance().setServiceName(data.get(getAdapterPosition()).getServiceName());
            SingletonData.getInstance().setServiceDescription(data.get(getAdapterPosition()).getServiceDescription());

            Intent intent = new Intent(mContext, ServiceDetails.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Pair<View, String> pair1 = Pair.create((View) imgRvItemServices, "profile");
                Pair<View, String> pair2 = Pair.create((View) tvRvItemServicesTitle, "text");
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
