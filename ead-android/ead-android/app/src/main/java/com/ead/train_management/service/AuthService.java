package com.ead.train_management.service;

import com.ead.train_management.models.accStatusModel;
import com.ead.train_management.models.userAuthModel;
import com.ead.train_management.models.authResponseDataModel;
import com.ead.train_management.models.userManagementModel;
import com.ead.train_management.models.travelerHandlerModel;

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

    // Login service
    @POST("Login")
    Call<authResponseDataModel> Login(@Body userAuthModel lg);

    // Get user profile service
    @GET("api/TravelerProfile/{id}")
    Call<travelerHandlerModel> getUserProfile(@Path("id") String nic);

    // Register new user service
    @POST("api/TravelerProfile")
    Call<travelerHandlerModel> Reg(@Body userManagementModel u);

    // Update user profile service
    @POST("api/TravelerProfile/") 
    Call<travelerHandlerModel> Update(@Body travelerHandlerModel u);

    // Disable user account service
    @PUT("api/TravelerProfile/{id}")
    Call<travelerHandlerModel> Dis(@Path("id") String nic, @Body accStatusModel db);
}



