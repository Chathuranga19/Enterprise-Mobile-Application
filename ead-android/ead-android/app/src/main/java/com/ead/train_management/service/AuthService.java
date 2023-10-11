package com.ead.train_management.service;

import com.ead.train_management.models.AccStatusModel;
import com.ead.train_management.models.TravelerHandlerModel;
import com.ead.train_management.models.UserAuthModel;
import com.ead.train_management.models.AuthResponseDataModel;
import com.ead.train_management.models.UserManagementModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This interface defines the services related to user authentication and profiles
 */
public interface AuthService {

    // Register new user service
    @POST("api/TravelerProfile")
    Call<TravelerHandlerModel> SignUpUser(@Body UserManagementModel u);

    // Update user profile service
    @POST("api/TravelerProfile/") 
    Call<TravelerHandlerModel> ModifyUserDetails(@Body TravelerHandlerModel u);

    // Login service
    @POST("Login")
    Call<AuthResponseDataModel> Login(@Body UserAuthModel lg);

    // Get user profile service
    @GET("api/TravelerProfile/{id}")
    Call<TravelerHandlerModel> fetchUserDetails(@Path("id") String nic);

    // Disable user account service
    @PUT("api/TravelerProfile/{id}")
    Call<TravelerHandlerModel> UserDisable(@Path("id") String nic, @Body AccStatusModel db);
}



