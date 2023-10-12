package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Travel model.

public class TravelModel {

    // Use the '@SerializedName' annotation to specify the JSON key for the 'id' field.
    @SerializedName("id")
    @Expose
    private String trainID;
    private String trainName;

    public String getTrainName() {
        return trainName;
    }

    // Getter method for accessing the 'id' field.
    public String trainIDFetcher() {
        return trainID;
    }

    // Setter method to update the 'id' field.
    public void TrainIDAllocator(String trainID) {
        this.trainID = trainID;
    }
}
