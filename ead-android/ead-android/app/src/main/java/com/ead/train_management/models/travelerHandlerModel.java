package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// Traveler handler model

public class TravelerHandlerModel {

     // Use the '@SerializedName' annotation to specify the JSON key for the 'id' field.
     @SerializedName("id")
     @Expose
     private String id;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'nic' field.
     @SerializedName("nic")
     @Expose
     private String nic;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'firstName' field.
     @SerializedName("firstName")
     @Expose
     private String fname;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'lastName' field.
     @SerializedName("lastName")
     @Expose
     private String lname;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'phoneNumber' field.
     @SerializedName("phoneNumber")
     @Expose
     private String phone;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'accStatus' field.
     @SerializedName("accStatus")
     @Expose
     private boolean acc;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'createdDate' field.
     @SerializedName("createdDate")
     @Expose
     private String date;

     // Getter method for accessing the 'createdDate' field.
     public String getDate() {
          return date;
     }

     // Setter method to update the 'createdDate' field.
     public void setDate(String date) {
          this.date = date;
     }

     // Getter method for accessing the 'id' field.
     public String getId() {
          return id;
     }

     // Setter method to update the 'id' field.
     public void setId(String id) {
          this.id = id;
     }

     // Getter method for accessing the 'nic' field.
     public String getNic() {
          return nic;
     }

     // Setter method to update the 'nic' field.
     public void setNic(String nic) {
          this.nic = nic;
     }

     // Getter method for accessing the 'firstName' field.
     public String getFname() {
          return fname;
     }

     // Setter method to update the 'firstName' field.
     public void setFname(String fname) {
          this.fname = fname;
     }

     // Getter method for accessing the 'lastName' field.
     public String getLname() {
          return lname;
     }

     // Setter method to update the 'lastName' field.
     public void setLname(String lname) {
          this.lname = lname;
     }

     // Getter method for accessing the 'phoneNumber' field.
     public String getPhone() {
          return phone;
     }

     // Setter method to update the 'phoneNumber' field.
     public void setPhone(String phone) {
          this.phone = phone;
     }

     // Getter method for accessing the 'accStatus' field.
     public boolean isAcc() {
          return acc;
     }

     // Setter method to update the 'accStatus' field.
     public void setAcc(boolean acc) {
          this.acc = acc;
     }
}
