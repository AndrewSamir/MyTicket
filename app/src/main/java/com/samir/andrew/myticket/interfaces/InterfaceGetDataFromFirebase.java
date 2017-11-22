package com.samir.andrew.myticket.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.samir.andrew.myticket.models.ModelChair;

import java.util.List;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface InterfaceGetDataFromFirebase {
    // id is selected id from dialog
    // name is selected name
    // flag witch dialog clciked
    void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag);

    void onGetStageChairs(List<ModelChair> modelChairLists, String flag);

    void onChairChanged(ModelChair modelChair, String flag);
}
