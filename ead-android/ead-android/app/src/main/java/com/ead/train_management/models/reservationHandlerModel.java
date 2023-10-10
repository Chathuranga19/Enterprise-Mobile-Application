package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


 // Reservation handler model

public class ReservationHandlerModel {

    // Use the '@SerializedName' annotation to specify the JSON key for the 'isCancelled' field.
    @SerializedName("isCancelled")
    @Expose
    private boolean acc;

    // Create a getter method for accessing the 'isCancelled' field.
    public boolean isAcc() {
        return acc;
    }

    // Create a setter method to update the 'isCancelled' field.
    public void setAcc(boolean acc) {

        this.acc = acc;
    }
}
