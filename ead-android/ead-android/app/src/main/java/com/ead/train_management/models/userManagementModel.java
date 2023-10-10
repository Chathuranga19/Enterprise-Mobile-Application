package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// User management model

public class userManagementModel {

    @SerializedName("Nic") // Nic field serialization
    @Expose
    private String nic;

    @SerializedName("FirstName") // FirstName field serialization
    @Expose
    private String fname;

    @SerializedName("LastName") // LastName field serialization
    @Expose
    private String lname;

    @SerializedName("PhoneNumber") // PhoneNumber field serialization
    @Expose
    private String phone;

    @SerializedName("AccStatus") // Account status field serialization
    @Expose
    private boolean acc;

    @SerializedName("UserInfo") // UserInfo field serialization
    public UserInfo data = null;

    // Inner class to represent user information
    public class UserInfo {
        @SerializedName("Password") // Password field serialization
        @Expose
        private String password;

        @SerializedName("Role") // Role field serialization
        @Expose
        private String role;

        // Getter for Password
        public String getPassword() {
            return password;
        }

        // Setter for Password
        public void setPassword(String password) {
            this.password = password;
        }

        // Getter for Role
        public String getRole() {
            return role;
        }

        // Setter for Role
        public void setRole(String role) {
            this.role = role;
        }
    }

    // Getter for Nic
    public String getNic() {
        return nic;
    }

    // Setter for Nic
    public void setNic(String nic) {
        this.nic = nic;
    }

    // Getter for FirstName
    public String getFname() {
        return fname;
    }

    // Setter for FirstName
    public void setFname(String fname) {
        this.fname = fname;
    }

    // Getter for LastName
    public String getLname() {
        return lname;
    }

    // Setter for LastName
    public void setLname(String lname) {
        this.lname = lname;
    }

    // Getter for PhoneNumber
    public String getPhone() {
        return phone;
    }

    // Setter for PhoneNumber
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter for AccStatus
    public boolean isAcc() {
        return acc;
    }

    // Setter for AccStatus
    public void setAcc(boolean acc) {
        this.acc = acc;
    }

    // Getter for UserInfo
    public UserInfo getData() {
        return data;
    }

    // Setter for UserInfo
    public void setData(UserInfo data) {
        this.data = data;
    }
}
