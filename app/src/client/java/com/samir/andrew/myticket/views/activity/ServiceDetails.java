package com.samir.andrew.myticket.views.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterClientEvents;
import com.samir.andrew.myticket.interfaces.InterfaceDailogClicked;
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


public class ServiceDetails extends AppCompatActivity implements InterfaceGetDataFromFirebase, InterfaceDailogClicked {

    @Bind(R.id.rvClientEvents)
    RecyclerView rvClientEvents;

    @Bind(R.id.imgServiceDetails)
    ImageView imgServiceDetails;

    @Bind(R.id.tvServiceDetailsTitle)
    TextView tvServiceDetailsTitle;

    @Bind(R.id.tvServiceDetailsDesciption)
    TextView tvServiceDetailsDesciption;

    private AdapterClientEvents adapterClientEvents;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelEventDetails> modelEventDetailsList;

    String serviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.e("Strgg","kk");
                onBackPressed();
            }
        });
        toolbar.setTitle(SingletonData.getInstance().getServiceName());


        serviceId = SingletonData.getInstance().getServiceId();
        modelEventDetailsList = new ArrayList<>();
        ButterKnife.bind(this);

        tvServiceDetailsTitle.setText(SingletonData.getInstance().getServiceName());
        tvServiceDetailsDesciption.setText(SingletonData.getInstance().getServiceDescription());
        imgServiceDetails.setImageBitmap(StringToBitMap(SingletonData.getInstance().getServiceImage()));

        rvClientEvents.setNestedScrollingEnabled(false);
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
                mLayoutManager = new GridLayoutManager(this, 2);
            } else {
                mLayoutManager = new GridLayoutManager(this, 1);

            }
            rvClientEvents.setLayoutManager(mLayoutManager);

            AnimationSet set = new AnimationSet(true);

            Animation animation = new TranslateAnimation(0, 0, 1400, 0);
            animation.setDuration(1000);
            set.addAnimation(animation);


         /*   Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(1700);
            set.addAnimation(animation);

            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
            );
            animation.setDuration(100);
            set.addAnimation(animation);
*/
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

            // adapter = new RecycleViewAdapter(poetNameSetGets, this);

            adapterClientEvents = new AdapterClientEvents(modelEventDetailsList, this);
            rvClientEvents.setAdapter(adapterClientEvents);

            rvClientEvents.setLayoutAnimation(controller);


        }


    }

    @Override
    public void onGetStageChairs(List<ModelChair> modelChairLists, String flag) {

    }

    @Override
    public void onChairChanged(ModelChair modelChair, String flag) {

    }

    @Override
    public void onClickDialog(String name, String flag, String eventName, String serviceId, int chairsInRow) {


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
