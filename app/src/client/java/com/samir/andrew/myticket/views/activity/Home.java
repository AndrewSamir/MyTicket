package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterClientAllServices;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelServiceDetails;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.samir.andrew.myticket.utlities.HelpMe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InterfaceGetDataFromFirebase {

    @Bind(R.id.rvClientHomeAllServices)
    RecyclerView rvClientHomeAllServices;

    private AdapterClientAllServices adapterClientAllServices;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelServiceDetails> modelServiceDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modelServiceDetailsList = new ArrayList<>();
        ButterKnife.bind(this);
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetAllServices(DataEnum.callGetAllServices.name());

        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance().getUid());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_tickets) {
            startActivity(new Intent(this, MyTickets.class));
        } else if (id == R.id.nav_profile) {

            startActivity(new Intent(this, Profile.class));
        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public void onGetStageChairs(List<ModelChair> modelChairLists, String flag) {

    }

    @Override
    public void onChairChanged(ModelChair modelChair, String flag) {

    }

}
