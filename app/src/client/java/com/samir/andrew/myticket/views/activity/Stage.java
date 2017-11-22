package com.samir.andrew.myticket.views.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.adapter.AdapterStage;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelProfile;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.samir.andrew.myticket.utlities.DataEnum;
import com.samir.andrew.myticket.utlities.HandleGetDataFromFirebase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import developer.mokadim.projectmate.SharedPrefUtil;


public class Stage extends AppCompatActivity implements InterfaceGetDataFromFirebase {

    String serviceId, eventName, time;
    int chairsInRow, Rows;
    public static List<ModelChair> modelChairsToReserve;

    private AdapterStage adapterStage;
    private RecyclerView.LayoutManager mLayoutManager;

    List<ModelChair> modelChairList;

    @Bind(R.id.rvClientStage)
    RecyclerView rvClientStage;

    @Bind(R.id.tvStage)
    View tvStage;

    @OnClick(R.id.btnReserveNow)
    public void onClickbtnReserveNow() {
        // TODO submit data to server...

        HandleGetDataFromFirebase.getInstance(this).callCheckProfileData(DataEnum.callCheckProfile.name());
    }

    @Bind(R.id.btnReserveNow)
    View btnReserveNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_stage);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        serviceId = SingletonData.getInstance().getServiceId();
        eventName = SingletonData.getInstance().getEventName();
        time = SingletonData.getInstance().getEventTime();
        chairsInRow = SingletonData.getInstance().getChairsInRow();
        Rows = SingletonData.getInstance().getRows();

        modelChairList = new ArrayList<>();
        modelChairsToReserve = new ArrayList<>();
        ButterKnife.bind(this);
        //   rvClientStage.setNestedScrollingEnabled(false);

        startAnimation(0, 0, -400, 0, tvStage, 3000);

        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);
        HandleGetDataFromFirebase.getInstance(this).callGetStageChairs(DataEnum.callGetStageChairs.name(),
                serviceId, eventName, time);
    }


    @Override
    protected void onResume() {
        super.onResume();
        HandleGetDataFromFirebase.getInstance(this).setGetDataFromFirebaseInterface(this);

    }

    @Override
    public void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag) {

       /* if (flag.equals(DataEnum.callGetStageChairs.name())) {
            modelChairList = new ArrayList<>();

            for (DataSnapshot chair : dataSnapshot.getChildren()) {

                ModelChair modelChair = chair.getValue(ModelChair.class);
                modelChairList.add(modelChair);
            }

            mLayoutManager = new GridLayoutManager(this, chairsInRow);

            rvClientStage.setLayoutManager(mLayoutManager);
            rvClientStage.setHasFixedSize(true);

            AnimationSet set = new AnimationSet(true);

            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(50);
            set.addAnimation(animation);

            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
            );
            animation.setDuration(30);
            set.addAnimation(animation);

            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

            // adapter = new RecycleViewAdapter(poetNameSetGets, this);

            adapterStage = new AdapterStage(modelChairList, this);
            rvClientStage.setAdapter(adapterStage);

            rvClientStage.setLayoutAnimation(controller);
            startAnimation(0, 0, 400, 0, btnReserveNow, 3000);

        } else*/
        if (flag.equals(DataEnum.callCheckProfile.name())) {

            if (dataSnapshot.exists()) {
                ModelProfile modelProfile = dataSnapshot.getValue(ModelProfile.class);
                SharedPrefUtil.getInstance(Stage.this).write("name", modelProfile.getName());
                modelChairsToReserve = adapterStage.getReservedChairs();
                if (modelChairsToReserve.size() > 0)
                    startActivity(new Intent(Stage.this, Reservation.class));
                else
                    TastyToast.makeText(this, "أختر كرسى واحد على الاقل", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);


            } else
                startActivity(new Intent(Stage.this, Profile.class));
        }
    }

    @Override
    public void onGetStageChairs(List<ModelChair> modelChairLists, String flag) {

        if (flag.equals(DataEnum.callGetStageChairs.name())) {
            modelChairList = new ArrayList<>();

            modelChairList = modelChairLists;

            mLayoutManager = new GridLayoutManager(this, chairsInRow);

            rvClientStage.setLayoutManager(mLayoutManager);
            rvClientStage.setHasFixedSize(true);

            AnimationSet set = new AnimationSet(true);

            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(50);
            set.addAnimation(animation);

            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
            );
            animation.setDuration(30);
            set.addAnimation(animation);

            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

            // adapter = new RecycleViewAdapter(poetNameSetGets, this);

            adapterStage = new AdapterStage(modelChairList, this);
            rvClientStage.setAdapter(adapterStage);

            rvClientStage.setLayoutAnimation(controller);
            startAnimation(0, 0, 400, 0, btnReserveNow, 3000);

        }
    }

    @Override
    public void onChairChanged(ModelChair modelChair, String flag) {

        adapterStage.updateNextKey(modelChair, flag);
    }

    private void startAnimation(int fromXDelta, int toXDelta, int fromYDelta, int toYDelta, View view, int duration) {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(duration);
        set.addAnimation(animation);
        view.setAnimation(animation);

    }


}
