package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceDailogClicked;
import com.samir.andrew.myticket.models.ModelEventDetails;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetails extends AppCompatActivity implements InterfaceDailogClicked {

    @OnClick(R.id.btnEventDetailsReserveYourTicketNow)
    public void onClickbtnEventDetailsReserveYourTicketNow() {

        HandleGetDataFromFirebase.getInstance(this).callGetEventTimes(DataEnum.callGetEventTimes.name(),
                SingletonData.getInstance().getServiceId(),
                SingletonData.getInstance().getEventName()
                , SingletonData.getInstance().getChairsInRow());
    }

    @Bind(R.id.imgEventDetails)
    ImageView imgEventDetails;

    @Bind(R.id.tvEventDetailsTitle)
    TextView tvEventDetailsTitle;

    @Bind(R.id.tvEventDetailsDescription)
    TextView tvEventDetailsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(SingletonData.getInstance().getEventName());
        ButterKnife.bind(this);

        HandleGetDataFromFirebase.getInstance(this).setListnerToDialog(this);

        imgEventDetails.setImageBitmap(StringToBitMap(SingletonData.getInstance().getEventImage()));
        tvEventDetailsTitle.setText(SingletonData.getInstance().getEventName());
        tvEventDetailsDescription.setText(SingletonData.getInstance().getEventDescription());

    }

    @Override
    public void onClickDialog(String name, String flag, String eventName, String serviceId, int chairsInRow) {

        SingletonData.getInstance().setEventName(eventName);
        SingletonData.getInstance().setEventTime(name);
        SingletonData.getInstance().setChairsInRow(chairsInRow);

        Intent intent = new Intent(EventDetails.this, Stage.class);
        startActivity(intent);
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
