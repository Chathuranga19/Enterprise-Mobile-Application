package com.ead.train_management.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


  // Reservation model

public class ReservationModel {

     // Use the '@SerializedName' annotation to specify the JSON key for the 'referenceId' field.
     @SerializedName("referenceId")
     @Expose
     private String  rfid;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'travallerName' field.
     @SerializedName("travallerName")
     @Expose
     private String  name;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'travallerProfile' field.
     @SerializedName("travallerProfile")
     @Expose
     private String  tid;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'phoneNumber' field.
     @SerializedName("phoneNumber")
     @Expose
     private String  phone;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'TravelModel' field.
     @SerializedName("TravelModel")
     @Expose
     private String  train;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'noOfPassenger' field.
     @SerializedName("noOfPassenger")
     @Expose
     private int  passno;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'emailAddress' field.
     @SerializedName("emailAddress")
     @Expose
     private String  email;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'reservationDate' field.
     @SerializedName("reservationDate")
     @Expose
     private String  date;

     // Use the '@SerializedName' annotation to specify the JSON key for the 'isCancelled' field.
     @SerializedName("isCancelled")
     @Expose
     private boolean  status;

     // Getter method for accessing the 'referenceId' field.
     public String getRfid() {
          return rfid;
     }

     // Setter method to update the 'referenceId' field.
     public void setRfid(String rfid) {
          this.rfid = rfid;
     }

     // Getter method for accessing the 'travallerName' field.
     public String getName() {
          return name;
     }

     // Setter method to update the 'travallerName' field.
     public void setName(String name) {
          this.name = name;
     }

     // Getter method for accessing the 'travallerProfile' field.
     public String getTid() {
          return tid;
     }

     // Setter method to update the 'travallerProfile' field.
     public void setTid(String tid) {
          this.tid = tid;
     }

     // Getter method for accessing the 'phoneNumber' field.
     public String getPhone() {
          return phone;
     }

     // Setter method to update the 'phoneNumber' field.
     public void setPhone(String phone) {
          this.phone = phone;
     }

     // Getter method for accessing the 'TravelModel' field.
     public String getTrain() {
          return train;
     }

     // Setter method to update the 'TravelModel' field.
     public void setTrain(String train) {
          this.train = train;
     }

     // Getter method for accessing the 'noOfPassenger' field.
     public int getPassno() {
          return passno;
     }

     // Setter method to update the 'noOfPassenger' field.
     public void setPassno(int passno) {
          this.passno = passno;
     }

     // Getter method for accessing the 'emailAddress' field.
     public String getEmail() {
          return email;
     }

     // Setter method to update the 'emailAddress' field.
     public void setEmail(String email) {
          this.email = email;
     }

     // Getter method for accessing the 'reservationDate' field.
     public String getDate() {
          return date;
     }

     // Setter method to update the 'reservationDate' field.
     public void setDate(String date) {
          this.date = date;
     }

     // Getter method for accessing the 'isCancelled' field.
     public boolean isStatus() {
          return status;
     }

     // Setter method to update the 'isCancelled' field.
     public void setStatus(boolean status) {
          this.status = status;
     }
}
