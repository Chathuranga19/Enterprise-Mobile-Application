package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// User Authentication model
public class UserAuthModel {
    @SerializedName("Nic") // Nic field serialization
    @Expose
    private String UserNIC;

    @SerializedName("Password") // Password field serialization
    @Expose
    private String UserPassword;

    // Default constructor
    public UserAuthModel() {
    }

    // Constructor with parameters
    public UserAuthModel(String UserNIC, String UserPassword) {
        this.UserNIC = UserNIC;
        this.UserPassword = UserPassword;
    }

    // Getter for Nic
    public String getNic() {
        return UserNIC;
    }

    // Setter for Nic
    public void setNic(String UserNIC) {
        this.UserNIC = UserNIC;
    }

    // Getter for Password
    public String getPassword() {
        return UserPassword;
    }

    // Setter for Password
    public void setPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }
}
