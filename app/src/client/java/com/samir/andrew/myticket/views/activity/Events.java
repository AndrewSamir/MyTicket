package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterClientAllServices;
import com.samir.andrew.myticket.adapter.AdapterClientEvents;
import com.samir.andrew.myticket.interfaces.InterfaceDailogClicked;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelEventDetails;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.samir.andrew.myticket.utlities.HelpMe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Events extends AppCompatActivity implements InterfaceGetDataFromFirebase, InterfaceDailogClicked {

    @Bind(R.id.rvClientEvents)
    RecyclerView rvClientEvents;

    private AdapterClientEvents adapterClientEvents;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelEventDetails> modelEventDetailsList;

    String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        serviceId = intent.getStringExtra(DataEnum.intentServiceId.name());
        modelEventDetailsList = new ArrayList<>();
        ButterKnife.bind(this);
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetAllEvents(DataEnum.callGetAllEvents.name(),
                serviceId);
        HandleGetDataFromFirebase.getInstance(this).setListnerToDialog(this);


    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {

        if (flag.equals(DataEnum.callGetAllEvents.name())) {

            for (DataSnapshot event : dataSnapshot.getChildren()) {

                ModelEventDetails modelEventDetails = event.child(getString(R.string.firebase_eventDetails)).getValue(ModelEventDetails.class);
                modelEventDetails.setServiceId(serviceId);
                modelEventDetailsList.add(modelEventDetails);
            }
            if (HelpMe.getInstance(this).isTablet()) {
                mLayoutManager = new GridLayoutManager(this, 3);
            } else {
                mLayoutManager = new GridLayoutManager(this, 2);

            }
            rvClientEvents.setLayoutManager(mLayoutManager);

            adapterClientEvents = new AdapterClientEvents(modelEventDetailsList, this);
            rvClientEvents.setAdapter(adapterClientEvents);

        }


    }


    @Override
    public void onClickDialog(String name, String flag, String eventName, String serviceId, int chairsInRow) {

        Intent intent = new Intent(Events.this, Stage.class);
        intent.putExtra(DataEnum.intentServiceId.name(), serviceId);
        intent.putExtra(DataEnum.intentEventName.name(), eventName);
        intent.putExtra(DataEnum.intentTime.name(), name);
        intent.putExtra(DataEnum.intentChairsInRow.name(), chairsInRow);
        startActivity(intent);
    }
}
