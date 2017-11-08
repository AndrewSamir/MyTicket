package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterClientEvents;
import com.samir.andrew.myticket.adapter.AdapterStage;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelEventDetails;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.samir.andrew.myticket.utlities.HelpMe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Stage extends AppCompatActivity implements InterfaceGetDataFromFirebase {

    String serviceId, eventName, time;
    int chairsInRow;

    @Bind(R.id.rvClientStage)
    RecyclerView rvClientStage;

    private AdapterStage adapterStage;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelChair> modelChairList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        serviceId = SingletonData.getInstance().getServiceId();
        eventName = SingletonData.getInstance().getEventName();
        time = SingletonData.getInstance().getEventTime();
        chairsInRow = SingletonData.getInstance().getChairsInRow();

        modelChairList = new ArrayList<>();
        ButterKnife.bind(this);
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetStageChairs(DataEnum.callGetStageChairs.name(),
                serviceId, eventName, time);


    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {

        modelChairList = new ArrayList<>();
        if (flag.equals(DataEnum.callGetStageChairs.name())) {

            for (DataSnapshot chair : dataSnapshot.getChildren()) {

                ModelChair modelChair = chair.getValue(ModelChair.class);
                modelChairList.add(modelChair);
            }

            mLayoutManager = new GridLayoutManager(this, chairsInRow);

            rvClientStage.setLayoutManager(mLayoutManager);

            adapterStage = new AdapterStage(modelChairList, this);
            rvClientStage.setAdapter(adapterStage);

        }


    }
}
