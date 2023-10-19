package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Model for viewing booking information.

public class ViewBookingModel {

    @SerializedName("id")
    @Expose
    private String id; // Unique identifier for the booking.

    @SerializedName("travallerName")
    @Expose
    private String name; // Name of the traveler.

    @SerializedName("noOfPassenger")
    @Expose
    private int num; // Number of passengers in the booking.

    @SerializedName("emailAddress")
    @Expose
    private String email; // Email address of the traveler.

    @SerializedName("reservationDate")
    @Expose
    private String date; // Date of the reservation.

    @SerializedName("isCancelled")
    @Expose
    private boolean isCancelled; // Indicates if the booking is cancelled or not.

    @SerializedName("isCc")
    @Expose
    private boolean cc;

    public boolean isCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    // Getter and setter methods for the class members.

    public String getId() {
        return id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

     public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
