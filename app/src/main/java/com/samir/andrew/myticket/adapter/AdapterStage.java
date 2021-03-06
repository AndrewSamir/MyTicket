package com.samir.andrew.myticket.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceAddDataToFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleAddDataToFirebase;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitHelper;

/**
 * Created by lenovo on 5/3/2016.
 */
public class AdapterStage extends RecyclerView.Adapter<AdapterStage.ViewHolder> implements InterfaceAddDataToFirebase {


    public List<ModelChair> data;
    private Activity mContext;

    int width;

    public AdapterStage(List<ModelChair> data, Activity mContext) {
        this.data = data;
        this.mContext = mContext;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        HandleAddDataToFirebase.getInstance(mContext).setClickDialogListener(this);
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
        4 -> to reserve
        5 -> not a chair
         */


        holder.cardRvItemStageChair.getLayoutParams().height = width / SingletonData.getInstance().getChairsInRow();


        if (data.get(position).getState() == 0) {
            holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorEmpty));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
        } else if (data.get(position).getState() == 1) {
            if (data.get(position).getReservedBy().equals(FirebaseAuth.getInstance().getUid())) {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorMyReservation));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorReserved));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (data.get(position).getState() == 2) {
            holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorNotConfirmed));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));

          /*  if (data.get(position).getPaymentMethod().equals("cash")) {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorNotConfirmed));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));

            }*/
        } else if (data.get(position).getState() == 4) {//should be equal auth.


            holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToReserve));
            holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));

        } else if (data.get(position).getState() == 3) {
            if (data.get(position).getReservedBy().equals(FirebaseAuth.getInstance().getUid())) {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorMyReservation));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                holder.cardRvItemStageChair.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorToReserve));
                holder.tvRvItemStageChairKey.setTextColor(Color.parseColor("#000000"));
            }


        } else if (data.get(position).getState() == 5) {
            holder.cardRvItemStageChair.setVisibility(View.INVISIBLE);
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

    public void updateNextKey(ModelChair modelChair, String presKey) {

        if (presKey == null) {
            data.set(0, modelChair);
            return;
        }
        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getChairKey().equals(presKey)) {
                data.set(i + 1, modelChair);
                notifyItemChanged(i + 1);
                return;
            }

        }
    }

    public List<ModelChair> getReservedChairs() {

        List<ModelChair> toReserveChairs = new ArrayList<>();

        for (ModelChair chair : data) {

            if (chair.getState() == 4)
                toReserveChairs.add(chair);
        }

        return toReserveChairs;

    }

    @Override
    public void onDataAddedSuccess(String flag) {

    }

    @Override
    public void onDataAddedFailed(String flag) {

    }

    @Override
    public void onDataAddedRepeated(String flag) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvRvItemStageChairKey;
        CardView cardRvItemStageChair;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);
            tvRvItemStageChairKey = (TextView) itemView.findViewById(R.id.tvRvItemStageChairKey);
            cardRvItemStageChair = (CardView) itemView.findViewById(R.id.cardRvItemStageChair);
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

           /* ModelChair chair = data.get(getAdapterPosition());
            showCustomDialog(chair, chair.getState() + "", getAdapterPosition());
*/

        }
    }

    private void showCustomDialog(final ModelChair modelChair, final String flag, final int pos) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_admin_reserve);

        final TextView tvRvItemMyTicketName = (TextView) dialog.findViewById(R.id.tvRvItemMyTicketName);
        TextView tvRvItemMyTicketMobile = (TextView) dialog.findViewById(R.id.tvRvItemMyTicketMobile);
        final TextView tvRvItemMyTicketMyChair = (TextView) dialog.findViewById(R.id.tvRvItemMyTicketMyChair);
        final TextView tvRvItemMyTicketPaymentMethod = (TextView) dialog.findViewById(R.id.tvRvItemMyTicketPaymentMethod);
        final TextView tvRvItemMyTicketState = (TextView) dialog.findViewById(R.id.tvRvItemMyTicketState);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        tvRvItemMyTicketName.setText(modelChair.getName());
        tvRvItemMyTicketMobile.setText(modelChair.getMobile());
        tvRvItemMyTicketMyChair.setText(modelChair.getChairKey());
        tvRvItemMyTicketPaymentMethod.setText(modelChair.getPaymentMethod());

        final List<ModelChair> modelChairs = new ArrayList<>();
        final List<String> chairs = new ArrayList<>();

        if (flag.equals("0")) {
            btnCancel.setVisibility(View.GONE);

        } else if (flag.equals("1")) {
            btnConfirm.setVisibility(View.GONE);
        }
        // tvRvItemMyTicketState.setText(modelChair);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("0")) {
                    modelChair.setPaymentMethod("cash");
                    modelChair.setReservedBy(FirebaseAuth.getInstance().getUid());
                    modelChair.setName("ADMIN");
                    modelChair.setMobile("00");
                    modelChair.setState(1);
                    modelChairs.add(modelChair);
                    chairs.add(modelChair.getChairKey());
                    HandleAddDataToFirebase.getInstance(mContext).callReserveChairs("flag", modelChairs, chairs);

                } else if (flag.equals("2")) {
                    modelChair.setState(1);
                    modelChairs.add(modelChair);
                    chairs.add(modelChair.getChairKey());
                    HandleAddDataToFirebase.getInstance(mContext).callReserveChairs("flag", modelChairs, chairs);

                }
                notifyItemChanged(pos);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelChair.setPaymentMethod("cash");
                modelChair.setReservedBy(FirebaseAuth.getInstance().getUid());
                modelChair.setName("ADMIN");
                modelChair.setMobile("00");
                modelChair.setState(0);
                modelChairs.add(modelChair);
                chairs.add(modelChair.getChairKey());
                HandleAddDataToFirebase.getInstance(mContext).callReserveChairs("flag", modelChairs, chairs);


                notifyItemChanged(pos);

                dialog.dismiss();
            }
        });

        dialog.show();


    }

}
