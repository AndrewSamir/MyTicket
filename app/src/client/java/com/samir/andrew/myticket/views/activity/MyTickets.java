package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterMyTickets;
import com.samir.andrew.myticket.adapter.AdapterStage;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelMyTicket;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyTickets extends AppCompatActivity implements InterfaceGetDataFromFirebase {

    @Bind(R.id.rvMyTickets)
    SuperRecyclerView rvMyTickets;

    List<ModelMyTicket> modelMyTickets;
    private AdapterMyTickets adapterMyTickets;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        modelMyTickets = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(this);

        rvMyTickets.setLayoutManager(mLayoutManager);

        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetMyTickets(DataEnum.callGetMyTickets.name());
    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {
        modelMyTickets = new ArrayList<>();

        for (DataSnapshot data : dataSnapshot.getChildren()) {

            ModelMyTicket modelMyTicket = data.getValue(ModelMyTicket.class);
            modelMyTickets.add(modelMyTicket);

            adapterMyTickets = new AdapterMyTickets(modelMyTickets, this);
            rvMyTickets.setAdapter(adapterMyTickets);

        }

    }

    @Override
    public void onGetStageChairs(List<ModelChair> modelChairLists, String flag) {

    }

    @Override
    public void onChairChanged(ModelChair modelChair, String flag) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
