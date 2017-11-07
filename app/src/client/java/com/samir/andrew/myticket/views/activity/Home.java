package com.samir.andrew.myticket.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterClientAllServices;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelServiceDetails;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.samir.andrew.myticket.utlities.HelpMe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity implements InterfaceGetDataFromFirebase {

    @Bind(R.id.rvClientHomeAllServices)
    RecyclerView rvClientHomeAllServices;

    private AdapterClientAllServices adapterClientAllServices;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelServiceDetails> modelServiceDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modelServiceDetailsList = new ArrayList<>();
        ButterKnife.bind(this);
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetAllServices(DataEnum.callGetAllServices.name());

    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {

        if (flag.equals(DataEnum.callGetAllServices.name())) {

            for (DataSnapshot service : dataSnapshot.getChildren()) {

                ModelServiceDetails modelServiceDetails = service.child(getString(R.string.firebase_serviceDetails)).getValue(ModelServiceDetails.class);
                modelServiceDetails.setKey(service.getKey());
                modelServiceDetailsList.add(modelServiceDetails);
            }
            if (HelpMe.getInstance(this).isTablet()) {
                mLayoutManager = new GridLayoutManager(this, 3);
            } else {
                mLayoutManager = new GridLayoutManager(this, 2);

            }
            rvClientHomeAllServices.setLayoutManager(mLayoutManager);

            adapterClientAllServices = new AdapterClientAllServices(modelServiceDetailsList, this);
            rvClientHomeAllServices.setAdapter(adapterClientAllServices);

        }

    }

}
