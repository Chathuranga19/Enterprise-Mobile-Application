package com.ead.train_management.service;

import com.ead.train_management.models.ReservationModel;
import com.ead.train_management.models.ReservationHandlerModel;
import com.ead.train_management.models.TravelModel;
import com.ead.train_management.models.ViewBookingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * This interface defines services related to booking and reservations
 */
public interface ReservationService {

    // Get booking details for a user
    @GET("api/Reservation/{id}")
    Call<List<ViewBookingModel>> getBooking(@Path("id") String nic);

    // Create a new booking
    @POST("api/Reservation")
    Call<String> createBooking(@Body ReservationModel u);

    // Remove a booking
    @PUT("api/Reservation/{id}")
    Call<String> removeBooking(@Path("id") String id ,@Body ReservationHandlerModel db);

    // Get list of available trains
    @GET("api/Train")
    Call<List<TravelModel>> getTrain();
}
