package com.samir.andrew.myticket.singleton;

/**
 * Created by ksi on 03-Jul-17.
 */

public class SingletonData {

    private static SingletonData mInstance = null;

    private String serviceId, eventName, eventTime;
    private int chairsInRow;

    private SingletonData() {
    }

    public static SingletonData getInstance() {
        if (mInstance == null) {
            mInstance = new SingletonData();
        }
        return mInstance;
    }

    public static SingletonData getmInstance() {
        return mInstance;
    }

    public static void setmInstance(SingletonData mInstance) {
        SingletonData.mInstance = mInstance;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getChairsInRow() {
        return chairsInRow;
    }

    public void setChairsInRow(int chairsInRow) {
        this.chairsInRow = chairsInRow;
    }
}
