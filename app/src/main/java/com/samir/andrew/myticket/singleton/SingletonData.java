package com.samir.andrew.myticket.singleton;

/**
 * Created by ksi on 03-Jul-17.
 */

public class SingletonData {

    private static SingletonData mInstance = null;

    private String serviceId, eventName, eventTime, serviceImage, eventImage, serviceName, serviceDescription,eventDescription;
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
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
