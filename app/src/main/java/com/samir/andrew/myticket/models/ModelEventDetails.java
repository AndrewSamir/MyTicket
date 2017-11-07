package com.samir.andrew.myticket.models;

/**
 * Created by andre on 07-Nov-17.
 */

public class ModelEventDetails {

    public String eventDesciption, eventImage, eventName, serviceId;
    public int chairsInRow, numberOfRows;

    public ModelEventDetails() {
        serviceId = "e";
    }

    public String getEventDesciption() {
        return eventDesciption;
    }

    public void setEventDesciption(String eventDesciption) {
        this.eventDesciption = eventDesciption;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getChairsInRow() {
        return chairsInRow;
    }

    public void setChairsInRow(int chairsInRow) {
        this.chairsInRow = chairsInRow;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
