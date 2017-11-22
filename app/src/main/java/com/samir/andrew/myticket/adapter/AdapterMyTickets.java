package com.samir.andrew.myticket.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.models.ModelMyTicket;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.views.activity.EventDetails;

import java.util.List;

/**
 * Created by lenovo on 5/3/2016.
 */
public class AdapterMyTickets extends RecyclerView.Adapter<AdapterMyTickets.ViewHolder> {


    public List<ModelMyTicket> data;
    private Activity mContext;


    public AdapterMyTickets(List<ModelMyTicket> data, Activity mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_my_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvRvItemMyTicketEventName.setText(data.get(position).getEventName());
        holder.tvRvItemMyTicketMyChair.setText(data.get(position).getReservedChair());
        holder.tvRvItemMyTicketEventTime.setText(data.get(position).getEventTime());
        holder.tvRvItemMyTicketPaymentMethod.setText(data.get(position).getPaymentMethod());
        holder.tvRvItemMyTicketName.setText(data.get(position).getReserveName());
        holder.tvRvItemMyTicketMobile.setText(data.get(position).getMobile());

        switch (data.get(position).getState()) {
            case "0":
                holder.tvRvItemMyTicketState.setText(R.string.reservation_canceled);
                holder.cardRvItemMyTicket.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorReserved));
                break;

            case "2":
                holder.tvRvItemMyTicketState.setText(R.string.reservstion_not_confirmed_yet);
                holder.cardRvItemMyTicket.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorNotConfirmed));
                break;

            case "1":
                holder.tvRvItemMyTicketState.setText(R.string.reservation_confirmed);
                holder.cardRvItemMyTicket.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorMyReservation));
                break;
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

    public void addAll(List<ModelMyTicket> items) {
        int startIndex = data.size();
        data.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRvItemMyTicketEventName, tvRvItemMyTicketMyChair, tvRvItemMyTicketEventTime,
                tvRvItemMyTicketPaymentMethod, tvRvItemMyTicketState, tvRvItemMyTicketName, tvRvItemMyTicketMobile;

        CardView cardRvItemMyTicket;

        ImageView imgRvItemMyTicket;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvRvItemMyTicketEventName = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketEventName);
            tvRvItemMyTicketMyChair = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketMyChair);
            tvRvItemMyTicketEventTime = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketEventTime);
            tvRvItemMyTicketPaymentMethod = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketPaymentMethod);
            tvRvItemMyTicketState = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketState);
            tvRvItemMyTicketName = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketName);
            tvRvItemMyTicketMobile = (TextView) itemView.findViewById(R.id.tvRvItemMyTicketMobile);

            cardRvItemMyTicket = (CardView) itemView.findViewById(R.id.cardRvItemMyTicket);

            imgRvItemMyTicket = (ImageView) itemView.findViewById(R.id.imgRvItemMyTicket);
            imgRvItemMyTicket.setOnClickListener(this);
            //  itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (data.get(getAdapterPosition()).getPaymentMethod().equals("cash")) {

                showCustomDialog("Your ticket is reserved for you for only 24 hours you can pay by cash at the church ( Saint Mark Church Shubra )\n" +
                        " If you does not pay within 24 hours the booking will be canceled automatically", "cash");

            } else if (data.get(getAdapterPosition()).getPaymentMethod().equals("orangeMoney")) {
                showCustomDialog("The ticket is reserved for you for 24 hours You can pay the fees of your ticket by orange money service by your number at the nearest orange store \n" +
                        "The ticket automatically will be confirmed within 24 hours from the time of payment and it will canceled within 24 hours from the time of reservation\n" +
                        "Note : The mobile number that you will pay with orange money must be the same mobile number in the ticket the number is 0122 9184900", "orangeMoney");
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

    private void showCustomDialog(String text, final String flag) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        TextView tvDialogAction = (TextView) dialog.findViewById(R.id.tvDialogAction);
        final TextView tvDialogBack = (TextView) dialog.findViewById(R.id.tvDialogBack);
        final TextView tvDialogCustom = (TextView) dialog.findViewById(R.id.tvDialogCustom);
        tvDialogBack.setVisibility(View.GONE);
        tvDialogCustom.setText(text);
        tvDialogAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });

        tvDialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

}
