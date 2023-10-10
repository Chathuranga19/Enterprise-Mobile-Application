package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//Authentication response model
public class authResponseDataModel {

    // Use the '@SerializedName' annotation to specify the JSON key for the 'id' field.
    @SerializedName("id")
    @Expose
    private String id;

    // Use the '@SerializedName' annotation to specify the JSON key for the 'nic' field.
    @SerializedName("nic")
    @Expose
    private String nic;

    // Use the '@SerializedName' annotation to specify the JSON key for the 'password' field.
    @SerializedName("password")
    @Expose
    private String password;

    // Use the '@SerializedName' annotation to specify the JSON key for the 'role' field.
    @SerializedName("role")
    @Expose
    private String role;

    // Create a getter method for accessing the 'id' field.
    public String getId() {
        return id;
    }

    // Create a setter method to update the 'id' field.
    public void setId(String id) {
        this.id = id;
    }

    // Create a getter method for accessing the 'nic' field.
    public String getNic() {
        return nic;
    }

    // Create a setter method to update the 'nic' field.
    public void setNic(String nic) {
        this.nic = nic;
    }

    // Create a getter method for accessing the 'password' field.
    public String getPassword() {
        return password;
    }

    // Create a setter method to update the 'password' field.
    public void setPassword(String password) {
        this.password = password;
    }

    // Create a getter method for accessing the 'role' field.
    public String getRole() {
        return role;
    }

    // Create a setter method to update the 'role' field.
    public void setRole(String role) {
        this.role = role;
    }
}
