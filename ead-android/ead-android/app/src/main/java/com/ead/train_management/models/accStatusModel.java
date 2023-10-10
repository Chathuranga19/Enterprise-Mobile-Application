package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// 'accStatusModel' to represent account status.
public class accStatusModel {
    // Use the '@SerializedName' annotation to specify the JSON key for this field.
    @SerializedName("AccStatus")
    @Expose
    private boolean acc;

    // Create a getter method for accessing the account status.
    public boolean isAcc() {
        return acc;
    }

    // Create a setter method to update the account status.
    public void setAcc(boolean acc) {
        this.acc = acc;
    }
}
