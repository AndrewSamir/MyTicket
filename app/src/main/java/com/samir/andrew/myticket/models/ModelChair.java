package com.samir.andrew.myticket.models;

/**
 * Created by andre on 07-Nov-17.
 */

public class ModelChair {

    public String mobile, name, paymentMethod, chairKey, reservedBy;
    int state;

    public ModelChair() {

        reservedBy = "e";
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
    }

    public String getChairKey() {
        return chairKey;
    }

    public void setChairKey(String chairKey) {
        this.chairKey = chairKey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
