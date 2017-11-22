package com.samir.andrew.myticket.utlities;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samir.andrew.myticket.R;
import com.samir.andrew.myticket.interfaces.InterfaceDailogClicked;
import com.samir.andrew.myticket.interfaces.InterfaceGetDataFromFirebase;
import com.samir.andrew.myticket.models.ModelChair;
import com.samir.andrew.myticket.singleton.SingletonData;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import developer.mokadim.projectmate.dialog.IndicatorStyle;
import developer.mokadim.projectmate.dialog.ProgressDialog;


/**
 * Created by lenovo on 6/28/2017.
 */

public class HandleGetDataFromFirebase {
    private static Context context;
    private static HandleGetDataFromFirebase instance = null;
    private InterfaceGetDataFromFirebase clickListener;
    private InterfaceDailogClicked interfaceDailogClicked;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    public static HandleGetDataFromFirebase getInstance(Context context) {

        HandleGetDataFromFirebase.context = context;

        if (instance == null) {
            instance = new HandleGetDataFromFirebase();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
            myRef.keepSynced(true);

        }
        return instance;
    }

    public void setGetDataFromFirebaseInterface(InterfaceGetDataFromFirebase itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void setListnerToDialog(InterfaceDailogClicked interfaceDailogClicked) {
        this.interfaceDailogClicked = interfaceDailogClicked;
    }

    public void callGetAllServices(final String flag) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallZigZag).show();
        progressDialog.show();
        if (isOnline()) {
            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.firebase_services));
            myRefJobs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    clickListener.onGetDataFromFirebase(dataSnapshot, flag);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    public void callGetAllEvents(final String flag, final String serviceID) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        if (isOnline()) {
            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.firebase_services))
                    .child(serviceID)
                    .child(context.getString(R.string.firebase_events));
            myRefJobs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    clickListener.onGetDataFromFirebase(dataSnapshot, flag);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    public void callGetMyTickets(final String flag) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        if (isOnline()) {
            DatabaseReference myRefJobs = myRef.child("users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("tickets");
            myRefJobs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    clickListener.onGetDataFromFirebase(dataSnapshot, flag);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    public void callCheckProfileData(final String flag) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        if (isOnline()) {
            DatabaseReference myRefJobs = myRef.child("users")
                    .child(FirebaseAuth.getInstance().getUid());
            myRefJobs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    clickListener.onGetDataFromFirebase(dataSnapshot, flag);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    public void callGetEventTimes(final String flag, final String serviceId, final String eventName, final int chairsInRow) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        if (true) {
            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.firebase_services))
                    .child(serviceId)
                    .child(context.getString(R.string.firebase_events))
                    .child(eventName)
                    .child(context.getString(R.string.firebase_times));
            myRefJobs.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    ArrayList<String> stringArrayList = new ArrayList<String>();
                    Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();

                    while (myChildren.iterator().hasNext()) {
                        DataSnapshot myChild = myChildren.iterator().next();
                        stringArrayList.add(myChild.getKey());
                    }
                    populate(stringArrayList, flag, eventName, serviceId, chairsInRow);
                    progressDialog.dismiss();
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                    Log.d("valueError", error.toString());
                }
            });
        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    public void callGetStageChairs(final String flag, String serviceId, String eventName, String time) {
        final Dialog progressDialog = new ProgressDialog(context, IndicatorStyle.BallBeat).show();
        progressDialog.show();
        if (isOnline()) {
            DatabaseReference myRefJobs = myRef.child(context.getString(R.string.firebase_services))
                    .child(serviceId)
                    .child(context.getString(R.string.firebase_events))
                    .child(eventName)
                    .child(context.getString(R.string.firebase_times))
                    .child(time);
           /* myRefJobs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    clickListener.onGetDataFromFirebase(dataSnapshot, flag);
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    progressDialog.dismiss();
                }
            });*/

            final int numberOfChairs = SingletonData.getInstance().getChairsInRow() * SingletonData.getInstance().getRows();
            final List<ModelChair> modelChairList = new ArrayList<>();
            myRefJobs.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    ModelChair modelChair = dataSnapshot.getValue(ModelChair.class);
                    modelChairList.add(modelChair);
                    if (modelChairList.size() == numberOfChairs) {
                        clickListener.onGetStageChairs(modelChairList, flag);
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    ModelChair modelChair = dataSnapshot.getValue(ModelChair.class);
                    clickListener.onChairChanged(modelChair,s);

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {
            TastyToast.makeText(context, context.getString(R.string.connection_error), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            progressDialog.dismiss();
        }
    }

    private void populate(ArrayList<String> jobItems, String flag, String dialogTitle, String serviseId, int chairsInRow) {

        if (jobItems.size() > 0) {
            ShowDIalog(jobItems, flag, dialogTitle, serviseId, chairsInRow);
        }
    }

    private void ShowDIalog(ArrayList<String> arrName, final String flag, final String dialogTitle, final String serviseId, final int chairsInRow) {
        new MaterialDialog.Builder(context)
                .title(dialogTitle)
                .items(arrName)

                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (interfaceDailogClicked != null)
                            interfaceDailogClicked.onClickDialog(text + "", flag, dialogTitle, serviseId, chairsInRow);
                        //Log.e("loge", text + "" + which);
                        dialog.dismiss();
                        return true;
                    }
                })
                .negativeText(context.getString(R.string.cancel))
                .show();
    }


    private Boolean isOnline() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal == 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


}
