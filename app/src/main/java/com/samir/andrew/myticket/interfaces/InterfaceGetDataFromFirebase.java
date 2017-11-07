package com.samir.andrew.myticket.interfaces;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface InterfaceGetDataFromFirebase {
    // id is selected id from dialog
    // name is selected name
    // flag witch dialog clciked
    void onGetDataFromFirebase(DataSnapshot dataSnapshot, String flag);
}
