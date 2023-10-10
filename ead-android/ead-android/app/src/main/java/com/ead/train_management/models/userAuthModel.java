package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// User Authentication model
public class UserAuthModel {
    @SerializedName("Nic") // Nic field serialization
    @Expose
    private String nic;

    @SerializedName("Password") // Password field serialization
    @Expose
    private String password;

    // Default constructor
    public UserAuthModel() {
    }

    // Constructor with parameters
    public UserAuthModel(String nic, String password) {
        this.nic = nic;
        this.password = password;
    }

    // Getter for Nic
    public String getNic() {
        return nic;
    }

    // Setter for Nic
    public void setNic(String nic) {
        this.nic = nic;
    }

    // Getter for Password
    public String getPassword() {
        return password;
    }

    // Setter for Password
    public void setPassword(String password) {
        this.password = password;
    }
}
