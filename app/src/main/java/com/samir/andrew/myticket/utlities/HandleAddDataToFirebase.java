package com.samir.andrew.myticket.utlities;

import android.app.Dialog;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.myticket.interfaces.InterfaceAddDataToFirebase;
import com.sdsmdg.tastytoast.TastyToast;

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
/*
    public void callAddMember(final String flag, String church, String area, String streetName, ModelMember modelMember) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();

        if (HelpMe.getInstance(context).isOnline()) {

            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.fire_churchs))
                    .child(church)
                    .child(context.getString(R.string.fire_areas))
                    .child(area)
                    .child(streetName)
                    .child(context.getString(R.string.fire_members));

            myRefJobs.push().setValue(modelMember, new DatabaseReference.CompletionListener() {
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
*/
}
