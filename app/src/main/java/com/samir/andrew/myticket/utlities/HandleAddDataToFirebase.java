package com.samir.andrew.myticket.utlities;

import android.app.Dialog;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceAddDataToFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.models.ModelProfile;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import developer.mokadim.projectmate.dialog.IndicatorStyle;
import developer.mokadim.projectmate.dialog.ProgressDialog;


/**
 * Created by lenovo on 6/28/2017.
 */

public class HandleAddDataToFirebase {
    private static Context context;
    private static HandleAddDataToFirebase instance = null;
    private InterfaceAddDataToFirebase clickListener;
    private static DatabaseReference myRef;

    public static HandleAddDataToFirebase getInstance(Context context) {

        HandleAddDataToFirebase.context = context;

        if (instance == null) {
            instance = new HandleAddDataToFirebase();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
            myRef.keepSynced(true);

        }
        return instance;
    }

    public void setClickDialogListener(InterfaceAddDataToFirebase itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void callAddProfileData(final String flag, ModelProfile modelMember) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();

        if (HelpMe.getInstance(context).isOnline()) {

            DatabaseReference myRefJobs = myRef.child("users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("details");

            myRefJobs.setValue(modelMember, new DatabaseReference.CompletionListener() {
                public void onComplete(DatabaseError error, DatabaseReference ref) {

                    if (error == null) {
                        clickListener.onDataAddedSuccess(flag);
                    } else {
                        clickListener.onDataAddedFailed(flag);
                    }

                    progressDialog.dismiss();
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }

    }

    public void callReserveChairs(final String flag, List<ModelChair> modelChairs, List<String> chairs) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();

        if (HelpMe.getInstance(context).isOnline()) {

            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.firebase_services))
                    .child(SingletonData.getInstance().getServiceId())
                    .child(context.getString(R.string.firebase_events))
                    .child(SingletonData.getInstance().getEventName())
                    .child(context.getString(R.string.firebase_times))
                    .child(SingletonData.getInstance().getEventTime());
            for (int i = 0; i < chairs.size(); i++) {

                myRefJobs.child(chairs.get(i)).setValue(modelChairs.get(i), new DatabaseReference.CompletionListener() {
                    public void onComplete(DatabaseError error, DatabaseReference ref) {

                        if (error == null) {
                            clickListener.onDataAddedSuccess(flag);
                        } else {
                            clickListener.onDataAddedFailed(flag);
                        }

                        progressDialog.dismiss();
                    }
                });
            }

        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }

    }

}
