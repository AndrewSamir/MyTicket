package com.samir.andrew.myticket.interfaces;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface InterfaceDailogClicked {
    // id is selected id from dialog
    // name is selected name
    // flag witch dialog clciked
    void onClickDialog(String name, String flag, String eventName, String serviceId,int chairsInRow);
}
