package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// 'AccStatusModel' to represent account status.
public class AccStatusModel {
    // Use the '@SerializedName' annotation to specify the JSON key for this field.
    @SerializedName("AccStatus")
    @Expose
    private boolean status;

    // Create a setter method to update the account status.
    public void updateStatus(boolean status) {
        this.status = status;
    }

    // Create a getter method for accessing the account status.
    public boolean accountStatus() {
        return status;
    }
}
