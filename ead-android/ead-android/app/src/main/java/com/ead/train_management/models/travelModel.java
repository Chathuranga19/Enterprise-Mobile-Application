package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Travel model.

public class travelModel {

    // Use the '@SerializedName' annotation to specify the JSON key for the 'id' field.
    @SerializedName("id")
    @Expose
    private String tidc;

    // Getter method for accessing the 'id' field.
    public String getTidc() {
        return tidc;
    }

    // Setter method to update the 'id' field.
    public void setTidc(String tidc) {
        this.tidc = tidc;
    }
}
