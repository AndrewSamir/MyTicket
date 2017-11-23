package com.samir.andrew.myticket.views.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceAddDataToFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleAddDataToFirebase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import developer.mokadim.projectmate.SharedPrefUtil;


public class Reservation extends AppCompatActivity implements InterfaceAddDataToFirebase {

    String paymentMethod = "0";

    @Bind(R.id.tvReservationEventName)
    TextView tvReservationEventName;

    @Bind(R.id.tvREservationNumberOfTickets)
    TextView tvREservationNumberOfTickets;

    @Bind(R.id.tvREservationTicketPrice)
    TextView tvREservationTicketPrice;

    @Bind(R.id.tvReservationTotalPrice)
    TextView tvReservationTotalPrice;

    @Bind(R.id.imgReservationOrangeMoney)
    ImageView imgReservationOrangeMoney;

    @Bind(R.id.imgReservationCash)
    ImageView imgReservationCash;

    @OnClick(R.id.imgReservationOrangeMoney)
    public void onClickimgReservationOrangeMoney() {
        showCustomDialog("The ticket is reserved for you for 24 hours You can pay the fees of your ticket by orange money service by your number at the nearest orange store \n" +
                "The ticket automatically will be confirmed within 24 hours from the time of payment and it will canceled within 24 hours from the time of reservation\n" +
                "Note : The mobile number that you will pay with orange money must be the same mobile number in the ticket the number is 0122 9189151", "orangeMoney");
    }

    @OnClick(R.id.imgReservationCash)
    public void onClickimgReservationCash() {
        showCustomDialog("Your ticket is reserved for you for only 24 hours you can pay by cash at the church ( Saint Mark Church Shubra )\n" +
                " If you does not pay within 24 hours the booking will be canceled automatically", "cash");
    }

    @OnClick(R.id.tbnReservationCancel)
    public void onClicktbnReservationCancel() {
        finish();
    }

    @OnClick(R.id.tbnReservationConfirm)
    public void onClicktbnReservationConfirm() {
        if (paymentMethod.equals("0")) {
            TastyToast.makeText(this, "أختر طريقة الدفع اولا", TastyToast.LENGTH_SHORT, TastyToast.WARNING);

        } else {
            List<ModelChair> modelChairList = new ArrayList<>();
            List<String> keys = new ArrayList<>();

            for (ModelChair modelChair : Stage.modelChairsToReserve) {
                if (modelChair.getState() != 4) {
                    TastyToast.makeText(this, "يوجد كرسى غير متوفر الان .. أعد الاختيار مره أخرى", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    finish();
                }
                modelChair.setName(SharedPrefUtil.getInstance(Reservation.this).read("name", "no name"));
                modelChair.setMobile(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                modelChair.setReservedBy(FirebaseAuth.getInstance().getUid());
                modelChair.setPaymentMethod(paymentMethod);
                modelChair.setState(2);
                keys.add(modelChair.getChairKey());
                modelChairList.add(modelChair);

            }
            HandleAddDataToFirebase.getInstance(this).callReserveChairs(DataEnum.callReserveChairs.name(), modelChairList, keys);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        tvReservationEventName.setText(SingletonData.getInstance().getEventName());
        tvREservationTicketPrice.setText(SingletonData.getInstance().getPrice() + "");
        tvREservationNumberOfTickets.setText(Stage.modelChairsToReserve.size() + "");
        int totalPrice = SingletonData.getInstance().getPrice() * Stage.modelChairsToReserve.size();

        tvReservationTotalPrice.setText(totalPrice + "");

        HandleAddDataToFirebase.getInstance(this).setClickDialogListener(this);
    }


    private void showCustomDialog(String text, final String flag) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        TextView tvDialogAction = (TextView) dialog.findViewById(R.id.tvDialogAction);
        final TextView tvDialogBack = (TextView) dialog.findViewById(R.id.tvDialogBack);
        final TextView tvDialogCustom = (TextView) dialog.findViewById(R.id.tvDialogCustom);

        tvDialogCustom.setText(text);
        tvDialogAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag.equals("cash")) {
                    imgReservationCash.setAlpha(1f);
                    imgReservationOrangeMoney.setAlpha(0.5f);
                    paymentMethod = flag;
                } else if (flag.equals("orangeMoney")) {
                    imgReservationCash.setAlpha(0.5f);
                    imgReservationOrangeMoney.setAlpha(1f);
                    paymentMethod = flag;
                }

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

    @Override
    public void onDataAddedSuccess(String flag) {
        if (flag.equals(DataEnum.callReserveChairs.name())) {
            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onDataAddedFailed(String flag) {

    }

    @Override
    public void onDataAddedRepeated(String flag) {

    }
}
